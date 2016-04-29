package ozden.apps.tools;

import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.web.savedrequest.SavedCookie;
import org.springframework.stereotype.Component;

import ozden.apps.currency.Currency;
import ozden.apps.currency.CurrencyConversion;
import ozden.apps.currency.api.CurrencyHelper;
import ozden.apps.entities.Currencies;
import ozden.apps.entities.NotificationRegistry;
import ozden.apps.repos.CurrenciesRepository;
import ozden.apps.repos.NotificationRegistryRepository;
import scala.collection.mutable.ArrayLike;

@Component
public class TaskScheduler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    final long ONE_HOUR_IN_MILLISECONDS = 1 * 60 * 60 * 1000;
    final long ONE_DAY_IN_MILLISECONDS= 24 * ONE_HOUR_IN_MILLISECONDS;
    final long ONE_WEEK_IN_MILLISECONDS= 7 * ONE_DAY_IN_MILLISECONDS;
    final long ONE_MONTH_IN_MILLISECONDS = 30 * ONE_DAY_IN_MILLISECONDS;
    
    @Autowired
    private NotificationRegistryRepository notificationRegistryRepository;
    
    @Autowired
    private SmtpMailSender sendMail;
    
	@Autowired
	private CurrenciesRepository currenciesRepository;
	
//    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("The time is now " + Calendar.getInstance().getTime().toString());
    }
    
    private String createEmailBody(NotificationRegistry reg, Double currentRate, Date updateTime){
       String body = "";
       body = 
       "<head>"+
       "<style>"+
       "table {"+
       "width:100%;}"+
       "table, th, td {"+
       "border: 1px solid black;"+
       "border-collapse: collapse;}"+
       "th, td {"+
       "padding: 5px;"+
       "text-align: left;}"+
       "</style>"+
       "</head>"+
       "<body>"+
       "<table>" +
        "<tr>"+
        "  <th>Source Currency</th>" +
        "  <th>Destination Currency</th>" +
        "  <th>Notification Period</th>" +
        "  <th>Threshold</th>" +
        "  <th>Threshold Type</th>" +
        "  <th>Current Rate</th>" +
        "  <th>Update Time</th>" +
        "</tr>" +
        "<tr>" +
        "  <td>" + reg.getSrcCurrencyCode()+ "</td>" +
        "  <td>" + reg.getDstCurrencyCode()+ "</td>" +
        "  <td>" + reg.getNoticPeriod() + "</td>" +
        "  <td>" + reg.getThresholdValue().toString() + "</td>" +
        "  <td>" + reg.getThresholdType() + "</td>" +
        "  <td>" + currentRate.toString() + "</td>" +
        "  <td>" + updateTime.toString() + "</td>" +
        "</tr>" +
      "</table>"+
       "</body>";
      return body;
    }
    
    // create HTML mail body
    private String createMultRecEmailBody(ArrayList<NotificationRegistry> lstReg){
        String body = "";
        String recs = "";
        for(NotificationRegistry reg : lstReg){
        	// add each new record as a new row
        	recs += 
		        "<tr>" +
		          "  <td>" + reg.getSrcCurrencyCode()+ "</td>" +
		          "  <td>" + reg.getDstCurrencyCode()+ "</td>" +
		          "  <td>" + reg.getNoticPeriod() + "</td>" +
		          "  <td>" + reg.getThresholdValue().toString() + "</td>" +
		          "  <td>" + reg.getThresholdType() + "</td>" +
		          "  <td>" + reg.getCurrentRate().toString() + "</td>" +
		          "  <td>" + reg.getLastNoticSendDate().toString() + "</td>"+
		        "</tr>";
        }
        body = 
        "<head>"+
        "<style>"+
        "table {"+
        "		width:100%;}"+
        "table, th, td {"+
        "			border: 1px solid black;"+
        "			border-collapse: collapse;}"+
        "th, td {"+
        "		padding: 5px;"+
        "		text-align: left;}"+
        "</style>"+
        "</head>"+
        "<body>"+
        "<table>" +
         "<tr>"+
         "  <th>Source Currency</th>" +
         "  <th>Destination Currency</th>" +
         "  <th>Notification Period</th>" +
         "  <th>Threshold</th>" +
         "  <th>Threshold Type</th>" +
         "  <th>Current Rate</th>" +
         "  <th>Update Time</th>" +
         "</tr>" +
         // addd rows to the table
         recs +
       "</table>"+
        "</body>";
       return body;
     }
    
    // scheduled database checker for notifications 
    @Scheduled(fixedRate = ONE_HOUR_IN_MILLISECONDS)
    public void checkNotifications(){
    	System.out.println("The time is now " + Calendar.getInstance().getTime().toString() + " and checking notifications...");
    	Date nowTime = Calendar.getInstance().getTime();
    	final long ONE_DAY_MILLISECONDS = 24 * 60 * 60 * 1000;
    	// get all active records
    	List<NotificationRegistry> allRecords = notificationRegistryRepository.findByStatus("ACTIVE");
    	if ("Retrieved records : " + allRecords != null){
    		//System.out.println(allRecords);
    		Map<String, ArrayList<NotificationRegistry>> user2Notic = new HashMap<String, ArrayList<NotificationRegistry>>();
        	for (NotificationRegistry reg : allRecords){
        		try {
            		String period = reg.getNoticPeriod();
            		Date lastNoticDate = reg.getLastNoticSendDate();
            		String email = reg.getNotificationEmail();
            		String mailStatus = reg.getEmailSendStatus();
            		Double threshold = reg.getThresholdValue();
            		String srcCur = reg.getSrcCurrencyCode();
            		String dstCur = reg.getDstCurrencyCode();
            		String thresholdType = reg.getThresholdType();
            		
            		boolean periodCondSatisFlag = false;
            		// check the status and period is satisfied or not
            		if ( !mailStatus.equals("SUCCESS") && 
            				("Daily".equals(period) && (nowTime.getTime() - lastNoticDate.getTime()) >= ONE_DAY_MILLISECONDS ) || 
            				("Weekly".equals(period) && (nowTime.getTime() - lastNoticDate.getTime()) >= ONE_WEEK_IN_MILLISECONDS ) ||
            				("Monthly".equals(period) && (nowTime.getTime() - lastNoticDate.getTime()) >= ONE_MONTH_IN_MILLISECONDS )
            				){
            			periodCondSatisFlag = true;
            		}
            		if(periodCondSatisFlag){
            				try {
        						// get the current rate from api
        						CurrencyHelper helper = new CurrencyHelper();
        						CurrencyConversion conv = helper.getCurrencyConversion(srcCur, dstCur, 1);
            					if (threshold == 0.0){
//                					System.out.println("Trying to send email to " + email);
//                					String msg = "You can find the details below.";
//                					msg += this.createEmailBody(reg, conv.getRate(), nowTime);
//            						sendMail.send(email, "Your Currency Notification", msg);
            						// set status as Success
            						reg.setEmailSendStatus("SUCCESS");
            						reg.setLastNoticSendDate(nowTime);
            						reg.setCurrentRate(conv.getRate());
//            						reg.setThresholdType(NotificationRegistry.ThresholdType.NO_THRESHOLD);
//            						notificationRegistryRepository.save(reg);
            						// if the user exists in the map, update his notic. values
            						if (user2Notic.get(reg.getNotificationEmail()) != null){
            							ArrayList<NotificationRegistry> tmp = user2Notic.get(reg.getNotificationEmail());
            							tmp.add(reg);
            							user2Notic.put(reg.getNotificationEmail(), tmp);
            						}
            						else{
            							ArrayList<NotificationRegistry> tmp = new ArrayList<NotificationRegistry>();
            							tmp.add(reg);
            							user2Notic.put(reg.getNotificationEmail(), tmp);
            						}
            					}
            					// we have a threshold value
            					else{
            						boolean emailSenderFlag = false;

            						System.out.println("Current currency conversion " + conv.toString());
            						System.out.println("ThresholdType : " + thresholdType);
            						System.out.println("ThresholdValue : " + threshold);
            						
            						// check threshold conditions
            						if (thresholdType.equals("EQUAL") && conv.getRate() == threshold)
            							emailSenderFlag = true;
            						else if (thresholdType.equals("GREATER_THAN") && conv.getRate() > threshold)
            							emailSenderFlag = true;
            						else if (thresholdType.equals("LESS_THAN") && conv.getRate() < threshold)
            							emailSenderFlag = true;            		
            						
            						// if the threshold condition is met, send email to the user.
            						if(emailSenderFlag){
                    					System.out.println("Threshold condition is satisfied. Trying to send email to " + email);
//                    					String msg = "You can find the details below.";
//                    					msg += this.createEmailBody(reg, conv.getRate(), nowTime);
//                						sendMail.send(email, "Your Currency Notification", msg);
                						// set status as Success
                						reg.setEmailSendStatus("SUCCESS");
                						reg.setLastNoticSendDate(nowTime);
                						reg.setCurrentRate(conv.getRate());
//                						notificationRegistryRepository.save(reg);
                						// if the user exists in the map, update his notic. values
                						if (user2Notic.get(reg.getNotificationEmail()) != null){
                							ArrayList<NotificationRegistry> tmp = user2Notic.get(reg.getNotificationEmail());
                							tmp.add(reg);
                							user2Notic.put(reg.getNotificationEmail(), tmp);
                						}
                						else{
                							ArrayList<NotificationRegistry> tmp = new ArrayList<NotificationRegistry>();
                							tmp.add(reg);
                							user2Notic.put(reg.getNotificationEmail(), tmp);
                						}
            						}
            					}

        					} catch (MessagingException e) {
        						// set status as failed
        						reg.setEmailSendStatus("FAILED");
//        						notificationRegistryRepository.save(reg);
//        						System.out.println("Mail couldn't be sent!");
        						e.printStackTrace();
        					}
            		}
            		else{
            			System.out.println("Condition is not satisfied for the record " + reg.toString());
            		}
				} catch (Exception NullPointerException) {
						System.out.println("One of the record's field is null! Record is " + reg.toString());
				}

        	}
        	System.out.println("USER MAP : " + user2Notic.toString());
        	
        	// try to send email to the users and update the records in database
        	for (Map.Entry<String, ArrayList<NotificationRegistry>> entry : user2Notic.entrySet())
        	{
        		// get records per each key (user)
        		ArrayList<NotificationRegistry> allRecs = entry.getValue();
        		String email = entry.getKey();
        		// create the email body
        		String content = this.createMultRecEmailBody(allRecs);
				System.out.println("Trying to send email to " + email);
				String msg = "Your scheduled notifications are in the following table.";
				msg += content;
				String emailTitle = "Your Currency Notification";
        	    System.out.println("Email message : " + msg);
				try {
					sendMail.send(email, emailTitle, msg);
					// update the records if there is no error
					notificationRegistryRepository.save(allRecs);
				} catch (Exception e) {
					System.err.println("Couldn't send the email to " + email);
					// if the mail is not sent, update status as FAILED and save it to DB.
					for(NotificationRegistry reg : allRecs)
						reg.setEmailSendStatus("FAILED");
					notificationRegistryRepository.save(allRecs);
//					e.printStackTrace();
				}
        	    
        	}
    	}

    }
    
    // scheduled task to save currencies to the table
    @Scheduled(fixedRate = ONE_HOUR_IN_MILLISECONDS)
    public void saveCurrencies2Table(){
    	CurrencyHelper helper = new CurrencyHelper();
		// check database for the records
		List<Currencies> allCurrenciesInTable = null;
		try {
			allCurrenciesInTable = currenciesRepository.findAll();
			System.out.println("Records in the table " + allCurrenciesInTable.toString());
		} catch (Exception e) {
			System.out.println("CAUGHT IT!!!");
//			return;
		}
		 
		List<Currency> currenciesFromService = null;
		List<Currencies> currenciesInTable = null;
		Date nowTime = Calendar.getInstance().getTime();
		// 1 hour in milliseconds
		final long TIME_INTERVAL = 1 * 60 * 60 * 1000;
		// check if we have records or not
		if (allCurrenciesInTable == null || allCurrenciesInTable.isEmpty()){
			System.out.println("Currencies table is empty, will be initialized!");
			currenciesFromService = helper.getAllRates();
			if (currenciesFromService != null ){
				System.out.println("Currencies are obtained from the remote service.");
				currenciesInTable = new ArrayList<Currencies>();
//				System.out.println("Currencies from remote service " + currenciesFromService.toString());
				for (Currency cur : currenciesFromService){
					currenciesInTable.add(new Currencies(cur.getCode(), cur.getName(), cur.getRate(), nowTime));
				}
			}
			// save it to the table
			if (!currenciesInTable.isEmpty()){
				System.out.println("Now currencies from servce will be added to the table.");
//				System.out.println("Currencies for the Currencies table " + currenciesInTable.toString());
//				currenciesRepository.save(currenciesInTable);
				for(Currencies cur : currenciesInTable){
//					System.out.println(cur.toString());
					try {
						if(cur != null)
							currenciesRepository.save(cur);
					} catch (Exception e) {
						System.err.println("ERROR message -> " + e.getMessage() + " - " + cur.toString());
//						e.printStackTrace();
					}

				}
			}
		}
		else{
//			List<Currencies> allCurrenciesInTable = currenciesRepository.findAll();
			if (allCurrenciesInTable != null && !allCurrenciesInTable.isEmpty()){
				// check the last update time
				Currencies sampleRecord = allCurrenciesInTable.get(0);	
				// if the record is older than interval period
				if((nowTime.getTime() - sampleRecord.getLastUpdateTime().getTime()) >= TIME_INTERVAL){
					System.out.println("The table needs to be updated");
					currenciesFromService = helper.getAllRates();
					if (currenciesFromService != null ){
						System.out.println("Currencies are obtained from the remote service.");
						currenciesInTable = new ArrayList<Currencies>();
						for (Currency cur : currenciesFromService){
							currenciesInTable.add(new Currencies(cur.getCode(), cur.getName(), cur.getRate(), nowTime));
						}
					}
					// save it to the table
					if (!currenciesInTable.isEmpty()){
						System.out.println("Now currencies from servce will be added to the table.");
						currenciesRepository.save(currenciesInTable);
					}
				}
			}
			else{
				System.out.println("Table is empty!");
			}
		}
		
	}
    
    
    // everyday at midnight change the email send status for successfully sent emails
    @Scheduled(cron="0 0 0 * * *")
    public void doUpdateStatus(){
    	// get all success records
    	List<NotificationRegistry> allRegs = notificationRegistryRepository.findByEmailSendStatus("SUCCESS");
    	System.out.println(allRegs);
    	for(NotificationRegistry reg : allRegs){
    		reg.setEmailSendStatus("PENDING");
    	}
    	notificationRegistryRepository.save(allRegs);
    }
}

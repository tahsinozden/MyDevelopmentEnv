package ozden.apps.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import ozden.apps.entities.NotificationRegistry;
import ozden.apps.entities.Users;
import ozden.apps.repos.NotificationRegistryRepository;
import ozden.apps.repos.UsersRepository;

@RestController("/notic_reg_serv")
public class CurrencyNotificationRegistryController {
	
	@Autowired
	private NotificationRegistryRepository notificationRegistryRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	private static final Logger log = LoggerFactory.getLogger(CurrencyNotificationRegistryController.class);
	
	@RequestMapping(value="/notic_reg_serv/create", method=RequestMethod.GET)
	public NotificationRegistry createNoticForUser(
			@RequestParam String userName,
			@RequestParam String srcCur,
			@RequestParam String dstCur,
			@RequestParam String noticPeriod,
			@RequestParam(required=false) Double threshold,
			@RequestParam(required=false) String thresholdType,
			@RequestParam(required=false) String notificationEmail,
			@RequestParam String currencyServiceMode){
		
		NotificationRegistry.ThresholdType thrType = NotificationRegistry.ThresholdType.GREATER_THAN;
		if(threshold == null || threshold < 0.0){
			threshold = 0.0;
			thrType = NotificationRegistry.ThresholdType.NO_THRESHOLD;
		}
		
//		System.out.println(NotificationRegistry.ThresholdType.EQUAL.name());
//		System.out.println(NotificationRegistry.ThresholdType.GREATER_THAN.name());
//		System.out.println(NotificationRegistry.ThresholdType.LESS_THAN.name());
		
		// check currency service mode
		if(!("NBP".equals(currencyServiceMode) || "BITPAY".equals(currencyServiceMode))){
			log.error("Invalid currency service mode " + currencyServiceMode);
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid currency service mode " + currencyServiceMode);
		}
		// map the value
		if (thresholdType != null){
			if (thresholdType.equals(NotificationRegistry.ThresholdType.EQUAL.name()))
				thrType = NotificationRegistry.ThresholdType.EQUAL;
			else if (thresholdType.equals(NotificationRegistry.ThresholdType.GREATER_THAN.name()))
				thrType = NotificationRegistry.ThresholdType.GREATER_THAN;
			else if (thresholdType.equals(NotificationRegistry.ThresholdType.LESS_THAN.name()))
				thrType = NotificationRegistry.ThresholdType.LESS_THAN;
			else if (thresholdType.equals(NotificationRegistry.ThresholdType.NO_THRESHOLD.name()))
				thrType = NotificationRegistry.ThresholdType.NO_THRESHOLD;
			else
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, thresholdType + " is not a valid threshold type!");
		}

		String status = "ACTIVE";
		
		if (userName.equals("") ||
				srcCur.equals("") ||
				dstCur.equals("")||
				noticPeriod.equals("")){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Check your request parameters!");
		}
		
		if (notificationEmail == null || notificationEmail.equals(""))
			notificationEmail = userName;
		
		// check user exists or not
		List<Users> user = usersRepository.findByUserName(userName);
		if (user == null || user.isEmpty()){
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found!");
		}
		
		String curServMode = currencyServiceMode;
		NotificationRegistry newReg =  new NotificationRegistry(userName, srcCur, dstCur, 
								status, noticPeriod, threshold, thrType, notificationEmail, currencyServiceMode);
		
		// register new record
		// if the record has the same username, spring updates the record, not create a new one
		notificationRegistryRepository.save(newReg);

		// return newly created record
		return newReg;
	}
	
	@RequestMapping(value="/notic_reg_serv/query")
	public List<NotificationRegistry> getAllNotificationRegistryByUser(@RequestParam String userName){
		if(usersRepository.findByUserName(userName) == null){
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found!");
		}
//		List<NotificationRegistry> lstReg = notificationRegistryRepository.findByUserName(userName);
		List<NotificationRegistry> lstReg = notificationRegistryRepository.findByUserNameAndStatus(userName, "ACTIVE");
		return lstReg;
	}
	
	@RequestMapping(value="/notic_reg_serv/unsubscribe", method=RequestMethod.DELETE)
	public void doUnsubscribeNotification(@RequestParam String userName,
										  @RequestParam Integer[] recID ){
		if (recID.length == 0){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "No recIds! : " + recID.toString());
		}
		// check user exists or not
		List<Users> user = usersRepository.findByUserName(userName);
		if (user == null || user.isEmpty()){
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found!");
		}
		
		// check user has subscription to this currency notic.
		List<NotificationRegistry> lstSubscriptions = new ArrayList<NotificationRegistry>();
		for (Integer id : recID) {
			List<NotificationRegistry> tmp = notificationRegistryRepository.findByRecId(id);
			if (tmp == null || tmp.isEmpty())
					throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User does not have this subscription " + id.toString());
			// get the first element
			lstSubscriptions.add(tmp.get(0));
		}
		
		// update subscriptions as INACTIVE
//		for (NotificationRegistry rec : lstSubscriptions) {
//			rec.setStatus("INACTIVE");
//			notificationRegistryRepository.save(rec);
//		}
		
		// delete related records from DB
		notificationRegistryRepository.delete(lstSubscriptions);
	}
}

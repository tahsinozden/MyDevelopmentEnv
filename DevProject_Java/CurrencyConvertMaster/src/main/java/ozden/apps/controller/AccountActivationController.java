package ozden.apps.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import ozden.apps.entities.AccountActivation;
import ozden.apps.entities.Users;
import ozden.apps.repos.AccountActivationRepository;
import ozden.apps.repos.UsersRepository;
import ozden.apps.tools.EncryptionHelper;
import ozden.apps.tools.SmtpMailSender;

@Controller
@RequestMapping(value="account-activate")
public class AccountActivationController {
	
	private static final Logger log = LoggerFactory.getLogger(AccountActivationController.class);
	
	@Autowired
	private AccountActivationRepository accountActivationRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
    @Autowired
    private SmtpMailSender sendMail;
    
	@RequestMapping(value="by-token/{token}")
	public String doActivisionByToken(@PathVariable String token){
		if ("".equals(token)){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Token is null!");
		}
		
		Date now = Calendar.getInstance().getTime();
		log.debug("Request param. token : " + token);
		List<AccountActivation> allRecs = accountActivationRepository.findByTokenAndEndDateGreaterThan(token, now);
		if(allRecs.isEmpty()){
			// TODO: no records found, direct to a page
			log.warn("No records found for token " + token + " and date " + now.toString());
			return "redirect:/index.html";
		}
		
		AccountActivation targetUserForActivation = allRecs.get(0);
		// get the target user name
		String targetUserName = targetUserForActivation.getUserName();
		
		// check user in database
		List<Users> allUsers = usersRepository.findByUserName(targetUserName);
		if (allUsers.isEmpty()){
			log.warn("User not found!");
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found!");
		}
		
		// get target user
		Users targetUser = allUsers.get(0);
		
		// check if user is already active or not
		if (targetUser.getStatusFlag() == 1){
			// TODO: redirect to a page
			log.warn("User " + targetUserName + " is already active!");
			return "redirect:/index.html";
		}
		else if(targetUser.getStatusFlag() == 0){
			Date activationDate = Calendar.getInstance().getTime();
			Date expirationDate = (new Calendar.Builder()).setDate(2050, 1, 1).build().getTime();
			targetUser.setActivationDate(activationDate);
			targetUser.setExpirationDate(expirationDate);
			targetUser.setStatusFlag(1);
			// save the user info to the database
			usersRepository.save(targetUser);
			log.info("User record is updated in db. " + targetUser.toString());
			// remove the record from database
			accountActivationRepository.delete(targetUserForActivation);
			
			return "redirect:/pages/account-activation-page.html";
		}
		else{
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal logic error!");
		}
	}
	
//	@RequestMapping(value="send-activation-email/{username}")
	@RequestMapping(value="send-activation-email", method=RequestMethod.POST)
	public String sendActivationEmail(@RequestParam String username){
		
		if ("".equals(username)){
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, " Invalid user!");
		}
		
		if (usersRepository.findByUserName(username).isEmpty()){
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, " User not found!");
		}
		
		Date activationDate = Calendar.getInstance().getTime();
		
		// create activation email for the account
		String token = EncryptionHelper.generateEncryptedPassword(username);
		String serviceLink = "http://localhost:8080/account-activate/by-token/" + token;
		String title = "Your Account Activation";
		String msg = "<p>Please click the link below</p><br><a href=\"" + serviceLink + "\">Activate Your Account</a>";
		try {
			log.info("Trying to send email to " + username);
			sendMail.send(username, title, msg);
			
			
			// save matching record to db for activation
			long secondsInDay = 60 * 60 * 24;
			// add one day expiration
			Date actExpDate = new Date(activationDate.getTime() + secondsInDay);
//			Date activationExpDate = (new Calendar.Builder()).setDate(, 1, 1).build().getTime();
			AccountActivation act = new AccountActivation(username, token, activationDate, actExpDate);
			accountActivationRepository.save(act);
			log.info("Activation record is added to database. " + act.toString());
			
		} catch (MessagingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Your email address is not valid!");
		}
//		ResponseTemp res = new ResponseTemp();
//		res.status = "SUCCESS";
//		res.message = "E-mail is sent! Please check your mail box.";
//		return res;
		return "redirect:/index.html";
	}
	
	class ResponseTemp {
		String status;
		String message;
	}
}

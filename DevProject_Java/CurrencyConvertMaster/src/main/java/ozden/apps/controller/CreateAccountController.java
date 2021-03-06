package ozden.apps.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import ozden.apps.entities.AccountActivation;
import ozden.apps.entities.Users;
import ozden.apps.repos.AccountActivationRepository;
import ozden.apps.repos.UsersRepository;
import ozden.apps.tools.EncryptionHelper;
import ozden.apps.tools.SmtpMailSender;


@RestController
public class CreateAccountController {
	@Autowired
	private UsersRepository usersRepository;
	
    @Autowired
    private SmtpMailSender sendMail;
    
	@Autowired
	private AccountActivationRepository accountActivationRepository;
	
	private static final Logger log = LoggerFactory.getLogger(CreateAccountController.class);
	// create account service
	@RequestMapping(value="/create-account", method=RequestMethod.POST)
//	public String doCreateAccount( @RequestParam String username,
//								   @RequestParam String password){
	// since I got parse errors for string, I decided to return a class object, which is directly converted to JSON
	public UserCreate doCreateAccount( @RequestBody UserCreate usr){
		// here I used @RequestBody, because I was getting error while doing request from front-end
		// error is "Required String parameter 'username' is not present"
		// but it already exists in my request
		String username = usr.username;
		String password = usr.password;

		if (username == null || password == null){
			log.warn("password or/and username is not provided!");
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "password or/and username is not provided!");
		}
		
		// check username and password
		if ("".equals(username) || "".equals(password)){
			log.warn("Invalid password or username! " + username + ", " + password);
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid password or username!");
		}
		
		log.info("Requested User Name : " + usr.username );
		// check user exist
		List<Users> users = usersRepository.findByUserName(username);
		if (!users.isEmpty()){
			log.debug("USERS " + users.toString());
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User already exists!");
		}
		
		
        String encryptedPassword = EncryptionHelper.generateEncryptedPassword(password);
        
		Date activationDate = Calendar.getInstance().getTime();
		Date expirationDate = (new Calendar.Builder()).setDate(2050, 1, 1).build().getTime();

		// create user
		Users user = new Users(username, encryptedPassword, activationDate, expirationDate, new Integer(0));
		
		
		// create activation email for the account
		String token = EncryptionHelper.generateEncryptedPassword(username);
		// replace / character, it causes address errors in http address
		token = token.replace("/", "");
		String serviceLink = "http://localhost:8080/account-activate/by-token/" + token;
		String title = "Your Account Activation";
		String msg = "<p>Please click the link below</p><br><a href=\"" + serviceLink + "\">Activate Your Account</a>";
		try {
			log.info("Trying to send email to " + username);
			sendMail.send(username, title, msg);
			
			// save user to the database
			usersRepository.save(user);
			log.info("User is saved to the database " + user.toString());
			
			// redirect to account page 
//			return user.getUserName();
			usr.password = "****";
			
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
		
		return usr;
//		return "redirect:/index.html";
	}
	
	
    @SuppressWarnings("unused")
    private static class UserCreate {
        public String username;
        public String password;
    }
}

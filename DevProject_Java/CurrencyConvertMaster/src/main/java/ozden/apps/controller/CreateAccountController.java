package ozden.apps.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
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

import ozden.apps.entities.Users;
import ozden.apps.repos.UsersRepository;
import ozden.apps.tools.EncryptionHelper;


@RestController
public class CreateAccountController {
	@Autowired
	private UsersRepository usersRepository;
	
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
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "password or/and username is not provided!");
		}
		
		// check username and password
		if ("".equals(username) || "".equals(password)){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid password or username!");
		}
		
		// check user exist
		List<Users> users = usersRepository.findByUserName(username);
		if (!users.isEmpty()){
			System.out.print("USERS " + users.toString());
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User already exists!");
		}
		
		
        String encryptedPassword = EncryptionHelper.generateEncryptedPassword(password);
        
		Date activationDate = Calendar.getInstance().getTime();
		Date expirationDate = (new Calendar.Builder()).setDate(2050, 1, 1).build().getTime();

		// create user
		Users user = new Users(username, encryptedPassword, activationDate, expirationDate, new Integer(1));
		
		// save user to the database
		usersRepository.save(user);
		
		// redirect to account page 
//		return user.getUserName();
		usr.password = "****";
		return usr;
//		return "redirect:/index.html";
	}
	
	
    @SuppressWarnings("unused")
    private static class UserCreate {
        public String username;
        public String password;
    }
}

package ozden.apps.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;

import ozden.apps.repos.UsersRepository;
import ozden.apps.utils.EncryptionHelper;

@Component
public class UserService {
	@Autowired
	UsersRepository usersRepository;
	
	public Boolean login(String userName, String password) throws Exception{

    	
		if(userName.equals("") || password.equals("")){
			throw new Exception("userName or password is empty!");
		}
		
		// check if the user is in DB or not
		List<User> lstUser = usersRepository.findByUserNameAndStatusFlag(userName, 1);
		if (lstUser == null || lstUser.isEmpty()){
			// check if the user if active or not
			lstUser = usersRepository.findByUserNameAndStatusFlag(userName, 0);
			if (lstUser == null || lstUser.isEmpty()){
				throw new Exception("User not found!");
			}
			else{
				throw new Exception("User is IDLE!");
			}
		}
		User currentUser = lstUser.get(0);
		if (!EncryptionHelper.checkPasswordPairEqual(currentUser.getPassword(), password)){
			return false;
		}
		
		// userName and password correct
		return true;
		
	}
	
    public Boolean checkUser(String userName) throws ServletException{
    	// return invalid data exc.
    	if(userName == null || "".equals(userName))
    		throw new ServletException("Invalid username!");
    	
    	// if user not exist
    	if(usersRepository.findByUserName(userName).isEmpty() )
    		return false;
    	else
    		return true;
    }
    
	public User createAccount( User usr) throws ServletException{
		String username = usr.getUserName();
		String password = usr.getPassword();

		if (username == null || password == null){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "password or/and username is not provided!");
		}
		
		// check username and password
		if ("".equals(username) || "".equals(password)){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid password or username!");
		}
		
		// check user exist
		if (checkUser(username)){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User already exists!");
		}
		
		// encrypt the password
        String encryptedPassword = EncryptionHelper.generateEncryptedPassword(password);
        
        // set activation and expiration date
		Date activationDate = Calendar.getInstance().getTime();
		Date expirationDate = (new Calendar.Builder()).setDate(2050, 1, 1).build().getTime();

		// create user
		User user = new User(username, encryptedPassword, activationDate, expirationDate, usr.getStatusFlag());
		
		// save the user to the database
		user = usersRepository.save(user);
		
		// return created user
		return user;
	}
	
    
}

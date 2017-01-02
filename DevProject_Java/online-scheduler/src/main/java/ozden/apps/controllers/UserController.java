package ozden.apps.controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ozden.apps.entities.User;
import ozden.apps.entities.UserService;
import ozden.apps.repos.UsersRepository;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final User usr)
        throws Exception {
    	if (usr == null){
    		throw new ServletException("Provide a user!");
    	}
    	
    	String userName = usr.getUserName();
    	String password = usr.getPassword();
    	
    	// check username and password
    	if (!userService.login(userName, password)){
        	throw new ServletException("User name or password wrong");
    	}

		// TODO: add a new field Role for User entity
        return new LoginResponse(Jwts.builder().setSubject(userName)
            .claim("roles", "user").setIssuedAt(new Date())
            .signWith(SignatureAlgorithm.HS256, "secretkey").compact());
    }

    
    @RequestMapping(value = "check-user", method = RequestMethod.POST)
    public Boolean checkUser(@RequestBody final User usr) throws ServletException{
    	if (usr == null){
    		throw new ServletException("Provide a user!");
    	}
    	
    	String userName = usr.getUserName();
    	// return invalid data exc.
    	return userService.checkUser(userName);
    }
    
	@RequestMapping(value="create-user", method=RequestMethod.POST)
	public User createAccount( @RequestBody User usr) throws ServletException{
		if (usr == null){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User is null!");
		}
		// create an active user
		usr.setStatusFlag(1);
		
		// create user using the service
		User user = userService.createAccount(usr);
		user.setPassword("****");
		return user;
	}
	
    @SuppressWarnings("unused")
    private static class UserLogin {
        public String name;
        public String password;
    }

    @SuppressWarnings("unused")
    private static class LoginResponse {
        public String token;

        public LoginResponse(final String token) {
            this.token = token;
        }
    }
    
    @SuppressWarnings("unused")
    private static class UserCreate {
        public String username;
        public String password;
    }
}

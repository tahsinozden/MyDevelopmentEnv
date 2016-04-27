package ozden.apps.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ozden.apps.entities.Users;
import ozden.apps.repos.UsersRepository;
import ozden.apps.tools.EncryptionHelper;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UsersRepository usersRepository;
    private final Map<String, List<String>> userDb = new HashMap<>();

    public UserController() {
        userDb.put("tom", Arrays.asList("user"));
        userDb.put("sally", Arrays.asList("user", "admin"));
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final UserLogin login)
        throws ServletException {
    	String userName = login.name;
    	String password = login.password;
    	
		if(userName.equals("") || password.equals("")){
			throw new HttpClientErrorException(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		
		List<Users> lstUser = usersRepository.findByUserNameAndStatusFlag(userName, 1);
		if (lstUser == null || lstUser.isEmpty()){
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found!");
		}
		Users currentUser = lstUser.get(0);
		if (!EncryptionHelper.checkPasswordPairEqual(currentUser.getPassword(), password)){
			// redirect is used to redirect request to another controller
			// if redirect is not used, spring boot redirects the request to a mapped controller if exist.
			throw new ServletException("User name or password wrong");
		}
		
//    	if (login.name == null || !userDb.containsKey(login.name)) {
//            throw new ServletException("Invalid login");
//        }
		// TODO: add a new field Role for Users entity
        return new LoginResponse(Jwts.builder().setSubject(userName)
            .claim("roles", "user").setIssuedAt(new Date())
            .signWith(SignatureAlgorithm.HS256, "secretkey").compact());
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
}

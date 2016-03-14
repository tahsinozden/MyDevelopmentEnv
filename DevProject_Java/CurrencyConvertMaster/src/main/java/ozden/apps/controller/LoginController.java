package ozden.apps.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import ozden.apps.repos.UserSessionsRepository;
import ozden.apps.repos.UsersRepository;
import ozden.apps.currency.Currency;
import ozden.apps.currency.CurrencyConversion;
import ozden.apps.currency.api.CurrencyHelper;
import ozden.apps.entities.UserSessions;
import ozden.apps.entities.Users;
@Controller
//@RequestMapping(value="/login")
public class LoginController {
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	UserSessionsRepository userSessionsRepository;
	
	// requests and responses can be injected and used in the controller
	private @Autowired HttpServletRequest request;
	private @Autowired HttpServletResponse response;
//	@RequestMapping(value="/login")
	public List<Users> getUser(){
		return usersRepository.findAll();
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginAuthentication(@RequestParam(value="username") String userName,
			@RequestParam(value="password") String password, RedirectAttributes redirectAttrs){
		System.out.println(this.request.getMethod());
		System.out.println(this.response.getStatus());
		// check input data
		if(userName.equals("") || password.equals("")){
			throw new HttpClientErrorException(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		
		List<Users> lstUser = usersRepository.findByUserNameAndStatusFlag(userName, 1);
		if (lstUser == null || lstUser.isEmpty()){
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found!");
		}
		Users currentUser = lstUser.get(0);
		if (!currentUser.getPassword().equals(password)){
			// redirect is used to redirect request to another controller
			// if redirect is not used, spring boot redirects the request to a static mapped page.
			return "redirect:/index.html";
		}
		Calendar calInst = Calendar.getInstance();
		Date sessionStartTime = calInst.getTime();
		calInst.set(Calendar.MINUTE, calInst.get(Calendar.MINUTE) + 10); // 10 minutes expire time
		Date sessionExpTime = calInst.getTime();
		// TODO: try to implement JWT instead of this solution for web tokens.
		String sessionId = currentUser.getUserName() + "_server_secret_key_" + String.valueOf(sessionExpTime.getTime());
		UserSessions currentUserSession = new UserSessions(sessionId, currentUser.getUserName(), sessionStartTime, sessionExpTime);
		userSessionsRepository.save(currentUserSession);
//		RedirectAttributes redirectAttrs = new RedirectAttributesModelMap();
		redirectAttrs.addAttribute("sessionId", sessionId);
		// TODO: handle send just POST requests
		// redirect to account page
		return "redirect:/account_page?token={sessionId}";
	}
	
}

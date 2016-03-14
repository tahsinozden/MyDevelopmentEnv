package ozden.apps.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import ozden.apps.repos.UserSessionsRepository;
import ozden.apps.entities.UserSessions;

@Controller
public class AccountPageController {
	@Autowired
	UserSessionsRepository userSessionsRepository;
	
	// TODO: handle just POST requests
	@RequestMapping(value = "/account_page")
	public String processAccountPage(@RequestParam(value="token") String token){
		Date now = Calendar.getInstance().getTime();
		System.out.println(token + " " + now.toString());
		List<UserSessions> sessions = userSessionsRepository.findBySessionIdAndSessionExpTimeGreaterThan(token, now);
		if (sessions == null || sessions.isEmpty()) {
			// redirect to login page
			return "redirect:/index.html";
		}
		System.out.println("Welcome " + sessions.get(0).toString());
		return "pages/account_page.html";
	}
}

package ozden.apps.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger log = LoggerFactory.getLogger(AccountPageController.class);
	
	// TODO: handle just POST requests
	@RequestMapping(value = "/account_page", method=RequestMethod.POST)
	public String processAccountPage(@RequestParam(value="token") String token){
		Date now = Calendar.getInstance().getTime();
//		System.out.println(token + " " + now.toString());
		log.debug(token + " " + now.toString());
		List<UserSessions> sessions = userSessionsRepository.findBySessionIdAndSessionExpTimeGreaterThan(token, now);
		if (sessions == null || sessions.isEmpty()) {
			// redirect to login page
			return "redirect:/index.html";
		}
//		System.out.println("Welcome " + sessions.get(0).toString());
		log.debug("Welcome " + sessions.get(0).toString());
		// to direct a static webpage redirect: is used.
		// if it were used like "redirect:/account_page.html" then "/account_page" would be called.
		// in our case pages/account_page.html static page is called.
		return "redirect:pages/account_page.html";
	}
}

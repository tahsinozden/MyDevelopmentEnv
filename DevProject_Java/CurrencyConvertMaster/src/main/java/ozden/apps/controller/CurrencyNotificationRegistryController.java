package ozden.apps.controller;

import java.util.List;

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
	
	@RequestMapping(value="/notic_reg_serv/create", method=RequestMethod.GET)
	public NotificationRegistry createNoticForUser(
			@RequestParam String userName,
			@RequestParam String srcCur,
			@RequestParam String dstCur,
			@RequestParam String noticPeriod,
			@RequestParam(required=false) Double threshold ){
		
		if(threshold == null || threshold < 0.0){
			threshold = 0.0;
		}
		String status = "ACTIVE";
		
		if (userName.equals("") ||
				srcCur.equals("") ||
				dstCur.equals("")||
				noticPeriod.equals("")){
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Check your request parameters!");
		}
		
		// check user exists or not
		List<Users> user = usersRepository.findByUserName(userName);
		if (user == null || user.isEmpty()){
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found!");
		}
		
		NotificationRegistry newReg =  new NotificationRegistry(userName, srcCur, dstCur, status, noticPeriod, threshold);
		
		// register new record
		notificationRegistryRepository.save(newReg);
		
		// return newly created record
		return newReg;
	}
}

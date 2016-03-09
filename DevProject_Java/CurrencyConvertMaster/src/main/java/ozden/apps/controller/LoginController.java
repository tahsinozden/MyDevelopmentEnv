package ozden.apps.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import ozden.apps.repos.UsersRepository;
import ozden.apps.currency.Currency;
import ozden.apps.currency.CurrencyConversion;
import ozden.apps.currency.api.CurrencyHelper;
import ozden.apps.entities.Users;
@RestController
//@RequestMapping(value="/login")
public class LoginController {
	@Autowired
	UsersRepository usersRepository;
	
//	@RequestMapping(value="/login")
	public List<Users> getUser(){
		return usersRepository.findAll();
	}
	
	@RequestMapping(value="/login")
	public String loginAuthentication(@RequestParam(value="username") String userName,
			@RequestParam(value="password") String password){
		// check input data
		if(userName.equals("") || password.equals("")){
			throw new HttpClientErrorException(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		
		List<Users> lstUser = usersRepository.findByUserNameAndStatusFlag(userName, 1);
		if (lstUser == null){
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found!");
		}
		Users currentUser = lstUser.get(0);
		if (!currentUser.getPassword().equals(password)){
			return "redirect:index1.html";
		}
		return "redirect:account_page.html";
	}
	
}

package ozden.apps.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping(value="/login")
	public List<Users> getUser(){
		return usersRepository.findAll();
	}
	
}

package ozden.apps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPage {
//	@RequestMapping("/")
	public String index(){
		return "redirect:/index.html";
	}
}

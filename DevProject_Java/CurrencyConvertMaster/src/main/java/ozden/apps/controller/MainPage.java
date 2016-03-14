package ozden.apps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
// for view mapping @Controller annotation must be used instead of @RestController
// otherwise String value is returned instead of page.
public class MainPage {
	@RequestMapping(value="/", method={RequestMethod.GET, RequestMethod.POST})
	public String index(){
		return "index.html";
	}
}

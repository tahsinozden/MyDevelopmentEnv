package ozden.apps.controller;

import io.jsonwebtoken.Claims;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "role/{role}", method = RequestMethod.GET)
	public Boolean login(@PathVariable final String role,
			final HttpServletRequest request) throws ServletException {
		final Claims claims = (Claims) request.getAttribute("claims");
		System.out.println("ROLE : " + claims.get("roles"));
//		return ((List<String>) claims.get("roles")).contains(role);
		return ((String) claims.get("roles")).contains(role);

	}
	
	@RequestMapping(value="name-srv", method = RequestMethod.POST)
	public String getName(@RequestBody NameSrv srv) throws ServletException{
		if (srv.name == null)
			throw new ServletException("name is not present!");
		return "Your name is " + srv.name;
	}
	
    @SuppressWarnings("unused")
    private static class NameSrv {
        public String name;
    }
}

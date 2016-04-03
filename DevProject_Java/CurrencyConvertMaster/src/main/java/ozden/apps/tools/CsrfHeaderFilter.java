package ozden.apps.tools;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

public class CsrfHeaderFilter extends OncePerRequestFilter {
	  @Override
	  protected void doFilterInternal(HttpServletRequest request,
	      HttpServletResponse response, FilterChain filterChain)
	      throws ServletException, IOException {
	    CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
	        .getName());
	    if (csrf != null) {
	    	System.out.println("CSRF " + csrf);
	    	System.out.println("CSRF not null! " + csrf.toString());
	      Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
	      String token = csrf.getToken();
	    	System.out.println("Token " + token);
//	    	System.out.println("Cookie " + cookie.getValue());
	      if (cookie==null || token!=null && !token.equals(cookie.getValue())) {
	        cookie = new Cookie("XSRF-TOKEN", token);
	        cookie.setPath("/login");
//	        cookie.setPath("/account_page");
	        response.addCookie(cookie);
	      }
	      System.out.println("Cookie " + cookie.getValue());
	    }
	    filterChain.doFilter(request, response);
	  }
	}
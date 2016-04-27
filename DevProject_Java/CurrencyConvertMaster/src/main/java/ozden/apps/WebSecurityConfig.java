package ozden.apps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import ozden.apps.tools.CsrfHeaderFilter;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//        .httpBasic()
//      .and()
//        .authorizeRequests()
//          .antMatchers("/", "/index.html").permitAll()
//          .antMatchers("/login").permitAll()
//          .antMatchers("/create-account").permitAll()
//          .antMatchers("/account_page").permitAll()
//          .antMatchers("/favicon.ico").permitAll()
//          .antMatchers("/*.html").permitAll()
//          .antMatchers("/css/*.css").permitAll()
//          .antMatchers("/js/*.js").permitAll()
//          .antMatchers("/currency_service/currencies").permitAll()
//          .antMatchers("/currency_service/currency").permitAll()
//          .antMatchers("/notic_reg_serv/create").permitAll()
//          .antMatchers("/notic_reg_serv/query").permitAll()
//          .antMatchers("/notic_reg_serv/unsubscribe").permitAll()
//          .antMatchers("/send-email").permitAll()
//          .antMatchers("/pages/*.html").permitAll()
//        .anyRequest().authenticated()
//          .and()
////        .formLogin()
////        	.loginPage("/login")
////        	.permitAll()
////          .and()
//        .logout()
//        .permitAll()
//          .and()
////          .csrf().csrfTokenRepository(csrfTokenRepository())
////          .and()
////          .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
//          .csrf().disable();
    	http.httpBasic().disable().csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("username").password("password").roles("USER");
    }
    
    private CsrfTokenRepository csrfTokenRepository() {
    	  HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    	  repository.setHeaderName("XSRF-TOKEN");
//    	  repository.setHeaderName("X-CSRF-TOKEN");
    	  repository.setSessionAttributeName("_csrf");
    	  return repository;
    	}
}
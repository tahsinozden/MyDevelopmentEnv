package ozden.apps;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.apache.log4j.Logger;


@SpringBootApplication
@ComponentScan(basePackages = "ozden.apps")
//@ComponentScan
//@ComponentScan(basePackages = {"ozden.apps", "ozden.apps.currency.api"})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "ozden.apps")
@EntityScan(basePackages = "ozden.apps")
@EnableScheduling
//@EnableWebMvc

public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
//	static Logger log = Logger.getLogger(Application.class.getName());
	
    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.addUrlPatterns("/notic_reg_serv/*");
//        registrationBean.addUrlPatterns("/pages/*");

        return registrationBean;
    }

    
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
		System.out.println("here it is!");
		log.info("Application started!");
	}
	
	
}

package ozden.apps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineSchedulerApplication {

	// configuration for JWT
    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
//        registrationBean.addUrlPatterns("/api/*");
      registrationBean.addUrlPatterns("/no-api/*");

        return registrationBean;
    }
    
	public static void main(String[] args) {
		SpringApplication.run(OnlineSchedulerApplication.class, args);
	}
}

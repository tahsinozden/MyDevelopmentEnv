package ozden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

	@Autowired 
	IPAddressCheckInterceptor ipAddressCheckInterceptor;
	 
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(ipAddressCheckInterceptor)
			.addPathPatterns("/vote-tables/**/**");
	}
}

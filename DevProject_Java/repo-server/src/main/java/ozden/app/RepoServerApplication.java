package ozden.app;

import java.util.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import ozden.app.entities.User;
import ozden.app.repos.UserRepository;

@SpringBootApplication
public class RepoServerApplication {

	@Bean
	CommandLineRunner cmd(UserRepository ur){
		return args -> {
			String name = "John";
			String surname = "Doe";
			Stream.iterate(0, i -> i + 1)
				.limit(10)
				.forEach(val -> ur.save(new User(name+val, surname+val)));
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(RepoServerApplication.class, args);
	}
}




package ozden.app.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import ozden.app.entities.User;

@RepositoryRestResource(/*path="source"*/)
public interface UserRepository extends JpaRepository<User, Integer> {
	@RestResource(path="by-name")
	List<User> getUserByName(@Param("name") String name);
}

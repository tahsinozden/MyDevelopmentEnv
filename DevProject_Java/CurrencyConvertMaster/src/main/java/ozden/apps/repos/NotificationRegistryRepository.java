package ozden.apps.repos;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import ozden.apps.entities.NotificationRegistry;

@Transactional
public interface NotificationRegistryRepository extends JpaRepository<NotificationRegistry, String>{
	public List<NotificationRegistry> findAll();
	public List<NotificationRegistry> findByUserName(String userName);
	public List<NotificationRegistry> findByUserNameAndStatus(String userName, String status);
}

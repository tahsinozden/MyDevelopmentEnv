package ozden.apps.repos;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import ozden.apps.entities.NotificationRegistry;

@Transactional
public interface NotificationRegistryRepository extends JpaRepository<NotificationRegistry, String>{
	public List<NotificationRegistry> findAll();
	public List<NotificationRegistry> findByRecId(Integer recId);
	public List<NotificationRegistry> findByUserName(String userName);
	public List<NotificationRegistry> findByStatus(String status);
	public List<NotificationRegistry> findByEmailSendStatus(String emailSendStatus);
	public List<NotificationRegistry> findByUserNameAndStatus(String userName, String status);
}

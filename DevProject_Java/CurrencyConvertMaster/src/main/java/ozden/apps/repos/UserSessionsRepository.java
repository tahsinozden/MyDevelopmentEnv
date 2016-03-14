package ozden.apps.repos;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ozden.apps.entities.UserSessions;

@Transactional
public interface UserSessionsRepository extends JpaRepository<UserSessions, String>{
	public List<UserSessions> findAll();
	public List<UserSessions> findBySessionId(String sessionId);
	public List<UserSessions> findByUserName(String userName);
//	@Query("select t from #{#entityName} t where t.session_start_time <= ?1 and session_exp_time > ?1")
	public List<UserSessions> findBySessionIdAndSessionExpTimeGreaterThan(String sessionId, Date time);
	public List<UserSessions> findBySessionIdAndSessionExpTime(String sessionId, Date sessionExpTime);
	public List<UserSessions> findByUserNameAndSessionExpTime(String userName, Date sessionExpTime);
}

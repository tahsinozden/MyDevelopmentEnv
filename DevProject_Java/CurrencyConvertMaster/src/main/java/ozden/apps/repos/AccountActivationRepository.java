package ozden.apps.repos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ozden.apps.entities.AccountActivation;

public interface AccountActivationRepository extends JpaRepository<AccountActivation, String>{
	public List<AccountActivation> findAll();
	public List<AccountActivation> findByUserName(String name);
	public List<AccountActivation> findByToken(String token);
	public List<AccountActivation> findByTokenAndEndDate(String token, Date endDate);
	public List<AccountActivation> findByTokenAndEndDateGreaterThan(String name, Date endDate);
}

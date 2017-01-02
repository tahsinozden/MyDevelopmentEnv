package ozden.apps.repos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ozden.apps.entities.JobSchedule;

public interface JobScheduleRepository extends JpaRepository<JobSchedule, Integer> {
	public List<JobSchedule> findByCreatorUserName(String userName);
	public List<JobSchedule> findByCreatorUserNameAndIsActive(String userName, Boolean isActive);
	public List<JobSchedule> findByIsActiveAndStartDateLessThan(Boolean isActive, Date time);
}

package ozden.apps.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ozden.apps.entities.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {
	public List<Job> findByCreatorUserName(String userName);
}

package ozden.apps.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ozden.apps.entities.TaskExecutionLog;

public interface TaskExecutionLogRepository extends JpaRepository<TaskExecutionLog, Integer> {
	public List<TaskExecutionLog> findByCreatorUserName(String userName);
	public List<TaskExecutionLog> findByTaskId(Integer taskId);
}

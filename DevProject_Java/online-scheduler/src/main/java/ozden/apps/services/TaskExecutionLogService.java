package ozden.apps.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ozden.apps.entities.TaskExecutionLog;
import ozden.apps.repos.TaskExecutionLogRepository;

@Component
public class TaskExecutionLogService {

	@Autowired
	TaskExecutionLogRepository taskExecutionLogRepository;
	
	public List<TaskExecutionLog> getExecutionLogsByUserName(String userName) throws Exception{
		if (userName == null || "".equals(userName)){
			throw new Exception("userName is not provided!");
		}
		
		return taskExecutionLogRepository.findByCreatorUserName(userName);
	}
	
	public List<TaskExecutionLog> getExecutionLogsByTaskId(Integer taskId) throws Exception{
		if (taskId == null ){
			throw new Exception("Invalid task id!!!");
		}
		
		return taskExecutionLogRepository.findByTaskId(taskId);
	}
	
}

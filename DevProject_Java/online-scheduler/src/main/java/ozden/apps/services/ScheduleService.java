package ozden.apps.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ozden.apps.entities.JobSchedule;
import ozden.apps.repos.JobScheduleRepository;
import ozden.apps.repos.TaskExecutionLogRepository;

@Component
public class ScheduleService {
	
	@Autowired
	JobScheduleRepository jobScheduleRepository;
	
	@Autowired
	TaskScheduleService taskScheduleService;
	
	public JobSchedule createJobSchedule(JobSchedule jobSchedule) throws Exception{
		if (jobSchedule == null){
			throw new NullPointerException("Provide a non-null job object.");
		}
		
		if (jobSchedule.getEndDate() == null || jobSchedule.getStartDate() == null){
			throw new Exception("start date or end date is null!");
		}
		
		if (jobSchedule.getEndDate().compareTo(jobSchedule.getStartDate()) <= 0){
			throw new Exception("start data cannot be bigger or equal to end date!");
		}
		
		jobSchedule.setActive(true);
		JobSchedule savedJobSchedule = jobScheduleRepository.save(jobSchedule);
		
		return savedJobSchedule;
	}
	
	public void deleteJobSchedule(Integer id){
		if (id == null){
			throw new NullPointerException("Provide a valid id");
		}
		
		jobScheduleRepository.delete(id);
	}
	
	public JobSchedule getJobScheduleById(Integer id){
		if (id == null){
			throw new NullPointerException("Provide a valid id");
		}
		
		return jobScheduleRepository.findOne(id);
	}
	
	public List<JobSchedule> getAllJobSchedulesByUserName(String userName){
		if (userName == null || "".equals(userName)){
			throw new NullPointerException("Provide a valid username!");
		}
		
		return jobScheduleRepository.findByCreatorUserName(userName);
	}
	
	public List<JobSchedule> getAllActiveJobSchedulesByUserName(String userName){
		if (userName == null || "".equals(userName)){
			throw new NullPointerException("Provide a valid username!");
		}
		
		return jobScheduleRepository.findByCreatorUserNameAndIsActive(userName, true);
	}
	
	public JobSchedule changeJobScheduleStatus(Integer jobScheduleId, Boolean status) throws Exception{
		
		JobSchedule jobSchedule = jobScheduleRepository.findOne(jobScheduleId);
		if (jobSchedule == null){
			throw new Exception("No JobSchedule found with this id: " + jobScheduleId);
		}
		
		jobSchedule.setActive(status);
		jobScheduleRepository.save(jobSchedule);
		return jobSchedule;
	}
	
}

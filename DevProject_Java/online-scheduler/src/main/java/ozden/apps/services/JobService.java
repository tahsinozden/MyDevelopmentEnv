package ozden.apps.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ozden.apps.entities.Job;
import ozden.apps.repos.JobRepository;

@Component
public class JobService {
	
	@Autowired
	JobRepository jobRepository;
	
	public Job createJob(Job job) throws Exception{
		if (job == null){
			throw new NullPointerException("Provide a non-null job object.");
		}
		
		if (job.getName() == null || "".equals(job.getName())){
			throw new Exception("Job name is not provided!");
		}
		
		if (job.getCommand() == null || "".equals(job.getCommand())){
			throw new Exception("Job command is not provided!");
		}
		
		Job savedJob = jobRepository.save(job);
		return savedJob;
	}
	
	public void deleteJob(Integer jobId){
		if (jobId == null){
			throw new NullPointerException("Provide a valid jobId");
		}
		
		jobRepository.delete(jobId);
	}
	
	public Job getJobById(Integer jobId){
		if (jobId == null){
			throw new NullPointerException("Provide a non-null job id.");
		}
		
		return jobRepository.findOne(jobId);
	}
	
	public List<Job> getAllJobsByUserName(String userName){
		if (userName == null || "".equals(userName)){
			throw new NullPointerException("Provide a valid username!");
		}
		
		return jobRepository.findByCreatorUserName(userName);
	}
	
	
	
}

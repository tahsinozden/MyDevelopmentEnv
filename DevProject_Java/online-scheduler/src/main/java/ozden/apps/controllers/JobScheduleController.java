package ozden.apps.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ozden.apps.entities.Job;
import ozden.apps.entities.JobSchedule;
import ozden.apps.entities.TaskExecutionLog;
import ozden.apps.services.JobService;
import ozden.apps.services.ScheduleService;
import ozden.apps.services.TaskExecutionLogService;


@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("api")
public class JobScheduleController {
	
	@Autowired
	JobService jobService;

	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	TaskExecutionLogService taskExecutionLogService;
	
	@RequestMapping(value="job/{jobName}", method=RequestMethod.POST)
	public ResponseEntity<Job> createJob(@RequestBody Job job, @PathVariable("jobName") String jobName) throws Exception{
		
		Job createdJob = jobService.createJob(job);
		return new ResponseEntity<Job>(createdJob, HttpStatus.OK);
	}
	
	@RequestMapping(value="job/{jobId}", method=RequestMethod.DELETE)
	public void deleteJob(@PathVariable("jobId") Integer jobId) throws Exception{
		
		jobService.deleteJob(jobId);
//		return new ResponseEntity<Job>(null, HttpStatus.OK);
	}
	
	@RequestMapping(value="job/{jobId}", method=RequestMethod.GET)
	public ResponseEntity<Job> getJobById(@PathVariable("jobId") Integer jobId) throws Exception{
		
		Job queriedJob = jobService.getJobById(jobId);
		return new ResponseEntity<Job>(queriedJob, HttpStatus.OK);
	}
	
	@RequestMapping(value="job/byuser/{userName}", method=RequestMethod.GET)
	public ResponseEntity<List<Job>> getJobsByUserName(@PathVariable("userName") String userName) throws Exception{
		
		List<Job> queriedJobs = jobService.getAllJobsByUserName(userName);
		return new ResponseEntity<List<Job>>(queriedJobs, HttpStatus.OK);
	}
	
	@RequestMapping(value="schedule/{jobId}", method=RequestMethod.POST)
	public ResponseEntity<JobSchedule> createJobSchedule(@RequestBody JobSchedule jobSchedule, @PathVariable("jobId") Integer jobId) throws Exception{
		
		JobSchedule createdJobSchedule = scheduleService.createJobSchedule(jobSchedule);
		return new ResponseEntity<JobSchedule>(createdJobSchedule, HttpStatus.OK);
	}
	
	@RequestMapping(value="schedule/{jobScheduleId}", method=RequestMethod.DELETE)
	public void deleteJobSchedule(@PathVariable("jobScheduleId") Integer jobScheduleId) throws Exception{
		
		scheduleService.deleteJobSchedule(jobScheduleId);
	}
	
	@RequestMapping(value="schedule/{jobSchedId}", method=RequestMethod.GET)
	public ResponseEntity<JobSchedule> getJobScheduleById(@PathVariable("jobSchedId") Integer jobSchedId) throws Exception{
		
		JobSchedule queriedJobSchedule = scheduleService.getJobScheduleById(jobSchedId);
		return new ResponseEntity<JobSchedule>(queriedJobSchedule, HttpStatus.OK);
	}
	
	@RequestMapping(value="schedule/{jobSchedId}", method=RequestMethod.PUT)
	public ResponseEntity<JobSchedule> setJobScheduleStatus(@PathVariable("jobSchedId") Integer jobSchedId, @RequestParam Boolean status) throws Exception{
		
		JobSchedule queriedJobSchedule = scheduleService.changeJobScheduleStatus(jobSchedId, status);
		return new ResponseEntity<JobSchedule>(queriedJobSchedule, HttpStatus.OK);
	}
	
	@RequestMapping(value="schedule/byuser/{userName}", method=RequestMethod.GET)
	public ResponseEntity<List<JobSchedule>> getJobSchedulesByUserName(@PathVariable("userName") String userName) throws Exception{
		
//		List<JobSchedule> queriedJobSchedules = scheduleService.getAllActiveJobSchedulesByUserName(userName);
		List<JobSchedule> queriedJobSchedules = scheduleService.getAllJobSchedulesByUserName(userName);
		return new ResponseEntity<List<JobSchedule>>(queriedJobSchedules, HttpStatus.OK);
	}
	
	@RequestMapping(value="log/byuser/{userName}", method=RequestMethod.GET)
	public ResponseEntity<List<TaskExecutionLog>> getJobScheduleExecutionLogsByUserName(@PathVariable("userName") String userName) throws Exception{
		
		List<TaskExecutionLog> queriedJobScheduleLogs = taskExecutionLogService.getExecutionLogsByUserName(userName);
		return new ResponseEntity<List<TaskExecutionLog>>(queriedJobScheduleLogs, HttpStatus.OK);
	}
	
	@RequestMapping(value="log/{taskId}", method=RequestMethod.GET)
	public ResponseEntity<List<TaskExecutionLog>> getJobScheduleExecutionLogsByTaskId(@PathVariable("taskId") Integer taskId) throws Exception{
		
		List<TaskExecutionLog> queriedJobScheduleLogs = taskExecutionLogService.getExecutionLogsByTaskId(taskId);
		return new ResponseEntity<List<TaskExecutionLog>>(queriedJobScheduleLogs, HttpStatus.OK);
	}
	
}

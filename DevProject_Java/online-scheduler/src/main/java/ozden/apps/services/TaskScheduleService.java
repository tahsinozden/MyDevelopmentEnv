package ozden.apps.services;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;

import ozden.apps.entities.JobSchedule;
import ozden.apps.entities.TaskExecutionLog;
import ozden.apps.repos.JobScheduleRepository;
import ozden.apps.repos.TaskExecutionLogRepository;

@Component
public class TaskScheduleService {
	
	@Autowired
	JobScheduleRepository jobScheduleRepository;
	
	@Autowired
	TaskExecutionLogRepository taskExecutionLogRepository;
	
	@Scheduled(cron="0 0/1 * * * *")
	public void checkScheduledJobs(){
		Date now = Calendar.getInstance().getTime();
		System.out.println("now : " + now);

		List<JobSchedule> scheds = jobScheduleRepository.findByIsActiveAndStartDateLessThan(true, now);
		for (JobSchedule jbs : scheds){
			// continue if there is no cron expression
			if (jbs.getCronExpression() == null || "".equals(jbs.getCronExpression())){
				System.out.println(jbs + " doesn't have valid cron expression.");
				continue;
			}
			
			Date taskDate = jbs.getStartDate();
			String cron = jbs.getCronExpression();
			
			
			if (!CronSequenceGenerator.isValidExpression(cron)){
				System.err.println("Error while parsing the expression: " + cron);
				// save the execution to the log table with FAIL
				taskExecutionLogRepository.save(new TaskExecutionLog(jbs.getId(), jbs.getName(), now, "FAIL", jbs.getCreatorUserName()));
				continue;
			}
			
			CronSequenceGenerator gen = new CronSequenceGenerator(cron);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.add(Calendar.SECOND, -1);
			Date oneSecondBack = cal.getTime();
			
			
			Date next = gen.next(oneSecondBack);
//			System.out.println("Obj: " + jbs);
//			System.out.println("Next exec. time : " + next);
			// the time can be earlier than the actual schedule time, thats why check it like greater than the value
			if (now.compareTo(taskDate) >= 0){
//				if (now.compareTo(taskDate) == 0){
				// FIXME
				// since there are small differences in milliseconds, I had to check the minutes
				if (now.getMinutes() == taskDate.getMinutes()){
					// save the execution to the log table with SUCCESS
					taskExecutionLogRepository.save(new TaskExecutionLog(jbs.getId(), jbs.getName(), now, "SUCCESS", jbs.getCreatorUserName()));
				}

				jbs.setStartDate(next);
				jobScheduleRepository.saveAndFlush(jbs);
			}
			else {
				// execution time hasn't arrived yet, go on...
				System.out.println("Time hasn't been reached yet, go on...");
			}
			
		}
	}
	
	
	public Date getNextDate(Date last, String cron){
		CronSequenceGenerator g = new CronSequenceGenerator(cron);
		return g.next(last);
	}
	

}

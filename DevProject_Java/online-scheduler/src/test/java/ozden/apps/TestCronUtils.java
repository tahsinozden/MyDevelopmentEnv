package ozden.apps;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;

import ozden.apps.services.TaskScheduleService;

public class TestCronUtils {

//	@Autowired
//	TaskScheduleService taskScheduleService;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	@Ignore
	public void test() {
		//get a predefined instance
		CronDefinition cronDefinition =
		CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);

		//create a parser based on provided definition
		CronParser parser = new CronParser(cronDefinition);
		Cron quartzCron = parser.parse("0 * * 1-3 * ? *");

		//create a descriptor for a specific Locale
		CronDescriptor descriptor = CronDescriptor.instance(Locale.UK);

		//parse some expression and ask descriptor for description
		String description = descriptor.describe(parser.parse("0 0/10 * * * ?"));
		//description will be: "every 45 seconds"
		
		System.out.println(description);
		//validate expression
		quartzCron.validate();

	}
	
	
	@Test
	public void testNextExec(){
		Date now = Calendar.getInstance().getTime();
		TaskScheduleService taskScheduleService = new TaskScheduleService();
		System.out.println("Now : " + now);
		System.out.println("Next : " + taskScheduleService.getNextDate(now, "* */12 * * * *"));
	}
	

}

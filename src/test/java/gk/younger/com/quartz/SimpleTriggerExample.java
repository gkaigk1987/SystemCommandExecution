package gk.younger.com.quartz;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SimpleTriggerExample {
	
	private Scheduler scheduler;
	
	public SimpleTriggerExample() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void run() throws SchedulerException {
		JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("task1", "group1").build();
		SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1","group1")
										.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1)
												.repeatForever()).startAt(DateBuilder.dateOf(16, 32, 0))
																	.endAt(DateBuilder.dateOf(16, 34, 0)).build();
		scheduler.scheduleJob(jobDetail, simpleTrigger);
		scheduler.start();
	}

}

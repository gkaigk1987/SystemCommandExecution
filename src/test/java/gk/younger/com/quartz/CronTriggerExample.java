package gk.younger.com.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class CronTriggerExample {
	
	private Scheduler scheduler;
	
	public CronTriggerExample() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
	public void run(String signal) throws SchedulerException {
//		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(signal+"task1", "group1")
									.usingJobData("signal", signal).build();
		CronTrigger cronTrigger = TriggerBuilder.newTrigger().startNow().withIdentity(signal+"trigger1","group1")
												.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();
		scheduler.scheduleJob(jobDetail, cronTrigger);
		scheduler.start();
	}
	
}

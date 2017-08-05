package gk.younger.com.quartz;

import java.util.List;

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
	
	public void run(List<Integer> list) throws SchedulerException {
		for (Integer integer : list) {
			JobDetail jobDetail = JobBuilder.newJob(MyJob.class).usingJobData("signal", "signal"+integer).withIdentity("task"+integer, "group1").build();
			SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("trigger"+integer,"group1")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1)
							.repeatForever()).startAt(DateBuilder.dateOf(20, 15, 0))
					.endAt(DateBuilder.dateOf(20, 20, 0)).build();
			scheduler.scheduleJob(jobDetail, simpleTrigger);
			if(2 == integer) {
				System.exit(0);
				throw new RuntimeException();
			}
		}
		scheduler.start();
	}

}

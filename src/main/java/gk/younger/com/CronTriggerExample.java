package gk.younger.com;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import gk.younger.com.DatabaseServiceBean;

public class CronTriggerExample {
	
	private Scheduler scheduler;
	
	public CronTriggerExample() {
		try {
			this.scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public CronTriggerExample(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	public void run(List<DatabaseServiceBean> list) throws SchedulerException {
		if(null != list && list.size() > 0) {
			for (DatabaseServiceBean databaseServiceBean : list) {
				JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
							.withIdentity("task-databaseId-"+databaseServiceBean.getDatabaseId(), "group1")
							.usingJobData("testFrequenceMinutes", databaseServiceBean.getTestFrequenceMinutes()).build();
				
				CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger-databaseId-"+databaseServiceBean.getDatabaseId(),"group1")
						.withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 10-20 * * ?")).build();
				scheduler.scheduleJob(jobDetail, cronTrigger);
			}
		}
		scheduler.start();
	}
	
}

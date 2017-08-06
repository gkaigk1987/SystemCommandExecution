package gk.younger.com;

import java.util.List;
import java.util.Map;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WrkExecuteSimpleTrigger {
	
	private static Logger logger = LoggerFactory.getLogger(WrkExecuteSimpleTrigger.class);
	
	private Scheduler scheduler;
	
	public WrkExecuteSimpleTrigger() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public WrkExecuteSimpleTrigger(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	public void run(List<DatabaseServiceBean> list,Map<String, String> configMap) throws SchedulerException {
		if(null != list && list.size() > 0) {
			for (DatabaseServiceBean bean : list) {
				//开始时间
				String testStartTime = bean.getTestStartTime();
				Map<String, Integer> startMap = Util.getHourAndMinute(testStartTime);
				//结束时间
				String testEndTime = bean.getTestEndTime();
				Map<String, Integer> endMap = Util.getHourAndMinute(testEndTime); 
				//间隔时间
				int testFrequenceMinutes = bean.getTestFrequenceMinutes();
				
				String databaseId = bean.getDatabaseId();
				String serviceUrl = bean.getServiceUrl();
				
				String wrkt = configMap.get("wrkt");
				String wrkc = configMap.get("wrkc");
				String wrkd = configMap.get("wrkd");
				String databaseServiceUrl = configMap.get("databaseServiceUrl");
				String executeResponseUploadUrl = configMap.get("executeResponseUploadUrl");
				String universityInstanceCode = configMap.get("universityInstanceCode");
				
				JobDetail jobDetail = JobBuilder.newJob(WrkExecuteJob.class)
						.usingJobData("databaseId", databaseId)
						.usingJobData("serviceUrl", serviceUrl)
						.usingJobData("wrkt", wrkt)
						.usingJobData("wrkc", wrkc)
						.usingJobData("wrkd", wrkd)
						.usingJobData("databaseServiceUrl", databaseServiceUrl)
						.usingJobData("executeResponseUploadUrl", executeResponseUploadUrl)
						.usingJobData("universityInstanceCode", universityInstanceCode)
						.withIdentity("task-databaseId-"+databaseId, "group-1").build();
				
				SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("trigger-databaseId-"+databaseId,"group-1")
								.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(testFrequenceMinutes).repeatForever())
								.startAt(DateBuilder.dateOf(startMap.get("hour"), startMap.get("minute"), 0))
								.endAt(DateBuilder.dateOf(endMap.get("hour"), endMap.get("minute"), 0)).build();
				
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			scheduler.start();
		}else {
			scheduler.shutdown();
			logger.info("远程数据库服务链接获取数据为空！");
		}
	}

}

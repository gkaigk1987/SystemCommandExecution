package gk.younger.com;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WrkExecuteJob implements Job {
	
	private static Logger logger = LoggerFactory.getLogger(WrkExecuteJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String serviceUrl = jobDataMap.getString("serviceUrl");
		String databaseId = jobDataMap.getString("databaseId");
		
//		String wrkt = jobDataMap.getString("wrkt");
//		String wrkc = jobDataMap.getString("wrkc");
//		String wrkd = jobDataMap.getString("wrkd");
//		String databaseServiceUrl = jobDataMap.getString("databaseServiceUrl");
//		String executeResponseUploadUrl = jobDataMap.getString("executeResponseUploadUrl");
//		String universityInstanceCode = jobDataMap.getString("universityInstanceCode");
		
		logger.info("定时任务启动，时间：{}，数据库ID：{}，服务链接：{}。",Util.dateFormat(new Date()),databaseId,serviceUrl);
		SystemCommandRun commandRun = new SystemCommandRun();
		try {
			commandRun.doExecute(jobDataMap,context.getScheduler());
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.error("定时任务执行出错！");
			System.exit(0);
		}
		
	}

}

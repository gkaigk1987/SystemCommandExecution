package gk.younger.com;

import java.io.IOException;
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
		logger.info("定时任务启动，时间：{}，数据库ID：{}，服务链接：{}。",Util.dateFormat(new Date()),databaseId,serviceUrl);
		SystemCommandRun commandRun = new SystemCommandRun();
		try {
			commandRun.doExecute(serviceUrl, databaseId,context.getScheduler());
		} catch (IOException e) {
			logger.error("获取配置文件信息出错！");
			e.printStackTrace();
			System.exit(0);
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.error("定时任务执行出错！");
			System.exit(0);
		}
		
	}

}

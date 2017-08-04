package gk.younger.com;

import java.io.IOException;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

public class WrkExecuteJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String serviceUrl = jobDataMap.getString("serviceUrl");
		String databaseId = jobDataMap.getString("databaseId");
		SystemCommandRun commandRun = new SystemCommandRun();
		try {
			commandRun.doExecute(serviceUrl, databaseId);
		} catch (IOException e) {
			e.printStackTrace();
			try {
				context.getScheduler().shutdown();
			} catch (SchedulerException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}

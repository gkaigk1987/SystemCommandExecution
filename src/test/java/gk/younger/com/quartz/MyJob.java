package gk.younger.com.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {
	
	public MyJob() {
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		int testFrequenceMinutes = dataMap.getInt("testFrequenceMinutes");
		System.out.println("testFrequenceMinutes="+testFrequenceMinutes);
	}

}

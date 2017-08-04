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
		String signal = dataMap.getString("signal");
		ExecuteTask task = new ExecuteTask(signal);
		System.out.println("MyJob execute,signal="+task.exec());
	}

}

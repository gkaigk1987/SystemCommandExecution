package gk.younger.com.quartz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
		System.out.print("signal:"+signal+"---");
		ExecuteTask task = new ExecuteTask(signal);
		task.exec();
//		System.out.println("MyJob execute,signal="+task.exec());
//		System.out.println(task.exec()+":"+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}

}

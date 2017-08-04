package gk.younger.com.quartz;

import java.util.concurrent.Callable;

public class MyTask implements Callable<String> {
	
	private String info;
	
	public MyTask() {
		this.info = "default";
	}
	
	public MyTask(String info) {
		this.info = info;
	}

	@Override
	public String call() throws Exception {
		ExecuteTask eTask = new ExecuteTask();
		return eTask.exec();
	}

}

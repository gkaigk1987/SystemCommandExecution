package gk.younger.com.quartz;

public class ExecuteTask {
	
	private String info;
	
	public ExecuteTask() {
		
	}
	
	public ExecuteTask(String info) {
		this.info = info;
	}
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String exec() {
		System.out.println("执行"+info);
		return getInfo();
	}

}

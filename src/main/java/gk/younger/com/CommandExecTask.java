package gk.younger.com;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Java执行Linux的wrk命令
 * @author gaokai
 *
 */
public class CommandExecTask implements Callable<WrkResponseBean> {
	
	private Map<String, String> properties;
	
	public CommandExecTask() {
		
	}

	public CommandExecTask(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public WrkResponseBean call() throws Exception {
		try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        	//ignore
        }
		SystemCommandRun run = new SystemCommandRun();
		return run.beforeExecute(properties);
	}

}

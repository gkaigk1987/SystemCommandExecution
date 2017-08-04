package gk.younger.com;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * wrk命令执行入口
 * @author gaokai
 *
 */
public class WrkCommandExecute {
	
	private static Logger logger = LoggerFactory.getLogger(WrkCommandExecute.class);

	public static void main(String[] args) {
		SystemCommandRun commandRun = new SystemCommandRun();
		List<DatabaseServiceBean> remoteLocalDatabaseService = null;
		try {
			remoteLocalDatabaseService = commandRun.getRemoteLocalDatabaseService();
		} catch (IOException e) {
			logger.error("获取配置文件信息出错！");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		try {
			List<WrkResponseBean> wrkResponseBeans = commandRun.run(remoteLocalDatabaseService);
			commandRun.uploadExecuteResults(wrkResponseBeans);
		} catch (IOException e) {
			logger.error("获取配置文件信息出错！");
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.error("获取命令执行返回结果WrkResponseBean出错！");
			e.printStackTrace();
		} catch (ExecutionException e) {
			logger.error("获取命令执行返回结果WrkResponseBean出错！");
			e.printStackTrace();
		}
	}

}

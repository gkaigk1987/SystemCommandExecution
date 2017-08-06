package gk.younger.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * wrk命令执行入口
 * @author gaokai
 *
 */
public class WrkCommandExecute {
	
	private static Logger logger = LoggerFactory.getLogger(WrkCommandExecute.class);
	
	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	
	private List<DatabaseServiceBean> serviceBeans = new ArrayList<>();
	
	private boolean serviceChanged = false;
	
	private Scheduler scheduler = null;
	
	public WrkCommandExecute(){
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			logger.error("创建Quartz-Scheduler出错！");
			e.printStackTrace();
			throw new RuntimeException("创建Quartz-Scheduler出错！");
		}
	}
	
	public boolean isServiceChanged() {
		return serviceChanged;
	}

	public void setServiceChanged(boolean serviceChanged) {
		this.serviceChanged = serviceChanged;
	}
	
	public static void main(String[] args) {
		SystemCommandRun commandRun = new SystemCommandRun();
//		List<DatabaseServiceBean> remoteLocalDatabaseService = null;
//		try {
//			remoteLocalDatabaseService = commandRun.getRemoteLocalDatabaseService();
//		} catch (IOException e) {
//			logger.error("获取配置文件信息出错！");
//			logger.error(e.getMessage());
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
		
/*		try {
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
		}*/
		
		WrkCommandExecute execute = new WrkCommandExecute();
		Map<String, String> configMap = execute.getConfigValues();
		execute.getDatabaseServiceScheduledExecutor(commandRun,configMap);
		execute.serviceListener(configMap);
	}
	
	/**
	 * 获取配置文件信息
	 * @return
	 */
	public Map<String, String> getConfigValues() {
		try {
			Properties properties = PropertiesUtil.getUserConfigProperties();
			Map<String, String> configMap = new HashMap<>();
			String wrkt = properties.getProperty("wrk-t");
			if(StringUtils.isEmpty(wrkt)) {
				logger.error("未配置压测连接线程数！");
				throw new RuntimeException();
			}
			configMap.put("wrkt", wrkt);
			
			String wrkc = properties.getProperty("wrk-c");
			if(StringUtils.isEmpty(wrkc)) {
				logger.error("未配置压测连接数！");
				throw new RuntimeException();
			}
			configMap.put("wrkc", wrkc);
			
			String wrkd = properties.getProperty("wrk-d");
			if(StringUtils.isEmpty(wrkd)) {
				logger.error("未配置压测时长！");
				throw new RuntimeException();
			}
			configMap.put("wrkd", wrkd);
			
			String databaseServiceUrl = properties.getProperty("databaseServiceUrl");
			if(StringUtils.isEmpty(databaseServiceUrl)) {
				logger.error("未配置获取数据库服务连接！");
				throw new RuntimeException();
			}
			configMap.put("databaseServiceUrl", databaseServiceUrl);
			
			String executeResponseUploadUrl = properties.getProperty("executeResponseUploadUrl");
			if(StringUtils.isEmpty(executeResponseUploadUrl)) {
				logger.error("未配置数据上传服务连接！");
				throw new RuntimeException();
			}
			configMap.put("executeResponseUploadUrl", executeResponseUploadUrl);
			
			String universityInstanceCode = properties.getProperty("universityInstanceCode");
			if(StringUtils.isEmpty(universityInstanceCode)) {
				logger.error("未配置获取学校实例编号！");
				throw new RuntimeException();
			}
			configMap.put("universityInstanceCode", universityInstanceCode);
			
			return configMap;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("获取配置文件出错，程序退出！");
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 定时任务执行
	 * @param remoteLocalDatabaseService
	 */
	public void scheduleTaskExec(List<DatabaseServiceBean> remoteLocalDatabaseService,Map<String, String> configMap, Scheduler scheduler) {
		try {
			WrkExecuteSimpleTrigger wrkExecuteSimpleTrigger = new WrkExecuteSimpleTrigger(scheduler);
			wrkExecuteSimpleTrigger.run(remoteLocalDatabaseService,configMap);
		} catch (SchedulerException e) {
			logger.error("定时任务执行出错！");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * 定时任务执行(测试)
	 * @param remoteLocalDatabaseService
	 */
	public void scheduleTaskExecForTest(List<DatabaseServiceBean> remoteLocalDatabaseService,Scheduler scheduler) {
		try {
			CronTriggerExample example = new CronTriggerExample(scheduler);
			example.run(remoteLocalDatabaseService);
		} catch (SchedulerException e) {
			logger.error("定时任务执行出错！");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * 原始数据与最新数据库信息比较
	 * @param newBeans
	 * @param originalBeans
	 * @return
	 */
	public boolean serviceCompare(List<DatabaseServiceBean> newBeans, List<DatabaseServiceBean> originalBeans) {
		if(newBeans.size() != originalBeans.size()) {
			return false;
		}else {
			for(int i = 0; i < newBeans.size(); i++) {
				if(!newBeans.get(i).equals(originalBeans.get(i))) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 按固定时间间隔从远程服务获取最新数据库服务
	 * @param commandRun
	 */
	public void getDatabaseServiceScheduledExecutor(SystemCommandRun commandRun,Map<String,String> configMap) {
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				logger.info("获取远程数据库服务定时任务开始执行....");
				try {
					List<DatabaseServiceBean> databaseServices = commandRun.getRemoteLocalDatabaseService(configMap);
					logger.info("获取到远程数据服务：{}.",JSONObject.toJSONString(databaseServices));
					if(serviceBeans == null || serviceBeans.size() == 0) {
						//原始数据库服务为空
						serviceBeans = databaseServices;
						serviceChanged = true;
					}else {
						//原始数据库服务不为空
						if(null == databaseServices || databaseServices.size() == 0) {
							//获取到的最新的数据库服务为空
							serviceBeans = databaseServices;
							serviceChanged = true;
						}else {
							boolean flag = serviceCompare(databaseServices,serviceBeans);
							if(!flag) {
								serviceBeans = databaseServices;
								serviceChanged = true;
							}else {
								serviceChanged = false;
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				logger.info("获取远程数据库服务定时任务开始结束....");
			}
		}, 0, 20,TimeUnit.SECONDS);
	}
	
	/**
	 * 数据库服务变动监控
	 */
	public void serviceListener(Map<String, String> configMap) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("服务信息变动监控启动......");
				while(true) {
					if(isServiceChanged()) {
						setServiceChanged(false);
						//数据发生改变
						logger.info("远程数据发生改变，重启压测定时任务......");
						try {
							if(scheduler.isStarted()) {
								scheduler.shutdown();
								scheduler = StdSchedulerFactory.getDefaultScheduler();
							}
//							scheduleTaskExecForTest(serviceBeans,newscheduler);
							scheduleTaskExec(serviceBeans, configMap, scheduler);
						} catch (SchedulerException e) {
							logger.error("创建Quartz-Scheduler出错！");
							e.printStackTrace();
							throw new RuntimeException("创建Quartz-Scheduler出错！");
						}
					}
					try {
						Thread.sleep(5 * 1000L);
					} catch (Exception e) {
						// ignore
					}
				}
			}
		});
		t.setName("Remote-Database-Service-Changed-Monitor");
		t.start();
	}

}

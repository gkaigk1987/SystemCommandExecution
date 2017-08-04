package gk.younger.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Java执行Linux命令
 * @author gaokai
 *
 */
public class SystemCommandRun {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	private ReentrantLock lock = new ReentrantLock();

	/**
	 * 执行Linux命令
	 * @param command
	 * @return
	 * @throws InterruptedException
	 */
	public String exec(String command) throws InterruptedException {
		String returnString = "";
		Process pro = null;
		Runtime runTime = Runtime.getRuntime();
		if(runTime == null) {
			logger.error("获取Runtime出错！");
		}
		try {
			pro = runTime.exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String line = null;
			while ((line = input.readLine()) != null) {
				returnString = returnString + line + "\n";
			}
			if (pro.waitFor() != 0) {
                if (pro.exitValue() == 1) {
                	//p.exitValue()==0表示正常结束，1：非正常结束  
                	logger.error("命令执行非正常结束!");
                	returnString = "";
                }
            }
			input.close();
		} catch (IOException ex) {
			logger.error("IO异常，读取wrk执行返回内容出错！");
			ex.printStackTrace();
		}
		return StringUtils.isNotEmpty(returnString)? returnString : "";
	} 
	
	/**
	 * 调用wrk命令执行，并处理返回的结果
	 * @param command
	 * @param databaseId
	 * @return
	 */
	public WrkResponseBean beforeExecute(Map<String, String> properties) {
//		lock.lock();
		try {
			String responese = exec(properties.get("command"));
			logger.info("当前执行命令的返回信息为：\n{}",responese);
			if(StringUtils.isNotEmpty(responese)) {
				return dealWrkResponse(responese,properties);
			}
		} catch (InterruptedException e) {
			logger.error("Linux命令执行出错！wrk命令为:{}",properties.get("command"));
			e.printStackTrace();
		} 
//		finally {
//			lock.unlock();
//		}
		return null;
	}
	
	/**
	 * 处理wrk命令执行后返回的内容
	 * @param info
	 */
	public WrkResponseBean dealWrkResponse(String response,Map<String, String> properties) {
		WrkResponseBean responseBean = new WrkResponseBean();
		responseBean.setDatabaseId(properties.get("databaseId"));
		responseBean.setWrkUrl(properties.get("url"));
		responseBean.setWrkThreads(Integer.valueOf(properties.get("wrkt")));
		responseBean.setWrkConnections(Integer.valueOf(properties.get("wrkc")));
		responseBean.setWrkDuration(properties.get("wrkd"));
		
		boolean flag = response.indexOf("unable to connect") >= 0;
		if(flag) {
			//链接拒绝
			responseBean.setSuccFlag("0");
		}else {
			try {
				String[] strs = response.split("\n");
				int len = strs.length;
				if(len < 12) {
					responseBean.setSuccFlag("0");
					return responseBean;
				}
				
				//延迟信息
				String Latency = strs[3].trim();
				String[] LatencyArray = Latency.split("\\s+");
				responseBean.setLatencyAvg(LatencyArray[1]);
				responseBean.setLatencyStdev(LatencyArray[2]);
				responseBean.setLatencyMax(LatencyArray[3]);
				responseBean.setLatencyPNStdev(LatencyArray[4]);
				
				//处理中请求数
				String reqSec = strs[4].trim();
				String[] reqSecArray = reqSec.split("\\s+");
				responseBean.setReqSecAvg(reqSecArray[1]);
				responseBean.setReqSecStdev(reqSecArray[2]);
				responseBean.setReqSecMax(reqSecArray[3]);
				responseBean.setReqSecPNStdev(reqSecArray[4]);
				
				//延迟分布
				String ld50Percent = strs[6].trim();
				String[] ld50PercentArray = ld50Percent.split("\\s+");
				responseBean.setLatencyDis50Percent(ld50PercentArray[1]);
				
				String ld75Percent = strs[7].trim();
				String[] ld75PercentArray = ld75Percent.split("\\s+");
				responseBean.setLatencyDis75Percent(ld75PercentArray[1]);
				
				String ld90Percent = strs[8].trim();
				String[] ld90PercentArray = ld90Percent.split("\\s+");
				responseBean.setLatencyDis90Percent(ld90PercentArray[1]);
				
				String ld99Percent = strs[9].trim();
				String[] ld99PercentArray = ld99Percent.split("\\s+");
				responseBean.setLatencyDis99Percent(ld99PercentArray[1]);
			
				//平均每秒处理完成请求数
				String requestsSec = strs[len - 2].trim();
				String[] requestsSecArray = requestsSec.split("\\s+");
				responseBean.setRequestsSec(requestsSecArray[1]);
				
				//平均每秒读取数据
				String transferSec = strs[len - 1].trim();
				String[] transferSecArray = transferSec.split("\\s+");
				responseBean.setTransferSec(transferSecArray[1]);
				
				responseBean.setSuccFlag("1");
			} catch (Exception e) {
				responseBean.setSuccFlag("0");
				logger.error("处理wrk返回的内容出错！返回内容为：\n{}",response);
				e.printStackTrace();
			}
		}
		return responseBean;
	}
	
	
	/**
	 * 生成Linux执行命令
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private Map<String, String> getCommandProperties(String url,String databaseId) throws IOException {
		Properties properties = PropertiesUtil.getUserConfigProperties();
		Map<String, String> map = new HashMap<>();
		String wrkt = properties.getProperty("wrk-t");//线程数
		if(StringUtils.isEmpty(wrkt)) {
			logger.error("未配置压测连接线程数!");
			throw new RuntimeException();
		}
		map.put("wrkt", wrkt);
		
		String wrkc = properties.getProperty("wrk-c");//连接数
		if(StringUtils.isEmpty(wrkc)) {
			logger.error("未配置压测连接数!");
			throw new RuntimeException();
		}
		map.put("wrkc", wrkc);
		
		String wrkd = properties.getProperty("wrk-d");//测试时间
		if(StringUtils.isEmpty(wrkd)) {
			logger.error("未配置压测时长!");
			throw new RuntimeException();
		}
		map.put("wrkd", wrkd);
		
		String command = "wrk -t" + wrkt + " -c" + wrkc + " -d" + wrkd + " --latency " + url;
		map.put("command", command);
		map.put("url", url);
		map.put("databaseId", databaseId);
		return map;
	}
	
	/**
	 * 获取需要压测的数据库列表信息
	 * @return
	 * @throws IOException
	 */
	public List<DatabaseServiceBean> getRemoteLocalDatabaseService() throws IOException {
		Properties properties = PropertiesUtil.getUserConfigProperties();
		String serviceUrl = properties.getProperty("databaseServiceUrl");
		if(StringUtils.isEmpty(serviceUrl)) {
			logger.error("未配置获取数据库服务连接!");
			return null;
		}
		String universityInstanceCode = properties.getProperty("universityInstanceCode");
		if(StringUtils.isEmpty(universityInstanceCode)) {
			logger.error("未配置获取学校序列号!");
			return null;
		}
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("universityInstanceCode", universityInstanceCode));
		JSONObject doPost = HttpClientUtil.doPost(serviceUrl, formparams);//HttpClient进行post请求
		if(null == doPost || doPost.isEmpty()) {
			logger.error("未获取到数据库服务列表!");
			return null;
		}
		String array = JSONObject.toJSONString(doPost.get("databaseService"));
		List<DatabaseServiceBean> databaseServiceList = JSON.parseArray(array, DatabaseServiceBean.class);
		return databaseServiceList;
	}
	
	/**
	 * 异步执行,将返回的结果处理成需要的模式
	 * @param list
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException
	 */
	public List<WrkResponseBean> run(List<DatabaseServiceBean> list) throws IOException, InterruptedException, ExecutionException {
		List<WrkResponseBean> wrkResponseList = new ArrayList<>();
		if(null != list && list.size() > 0) {
			ExecutorService service = Executors.newFixedThreadPool(5);
			List<Future<WrkResponseBean>> results = new ArrayList<Future<WrkResponseBean>>();
			for(DatabaseServiceBean bean : list) {
				String url = bean.getUrl();
				String databaseId = bean.getId();
				if(StringUtils.isNotEmpty(url)) {
					Map<String, String> properties = getCommandProperties(url,databaseId);
					logger.info("当前执行的wrk命令为：{}",properties.get("command"));
					CommandExecTask task = new CommandExecTask(properties);
					Future<WrkResponseBean> future = service.submit(task);
					results.add(future);
				}
			}
			service.shutdown();
			for (Future<WrkResponseBean> future : results) {
				WrkResponseBean wrkResponseBean = future.get();
				if(wrkResponseBean != null) {
					wrkResponseList.add(wrkResponseBean);
				}
			}
		}else {
			logger.info("远程数据库服务链接获取数据为空！");
		}
		return wrkResponseList;
	}
	
	/**
	 * 将WrkResponseBean转化成http传输参数
	 * @param bean
	 * @return
	 */
	private List<NameValuePair> genPostParams(WrkResponseBean bean) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("databaseId", bean.getDatabaseId()));
		params.add(new BasicNameValuePair("latencyAvg", bean.getLatencyAvg()));
		params.add(new BasicNameValuePair("latencyStdev", bean.getLatencyStdev()));
		params.add(new BasicNameValuePair("latencyMax", bean.getLatencyMax()));
		params.add(new BasicNameValuePair("latencyPNStdev", bean.getLatencyPNStdev()));
		params.add(new BasicNameValuePair("reqSecAvg", bean.getReqSecAvg()));
		params.add(new BasicNameValuePair("reqSecStdev", bean.getReqSecStdev()));
		params.add(new BasicNameValuePair("reqSecMax", bean.getReqSecMax()));
		params.add(new BasicNameValuePair("reqSecPNStdev", bean.getReqSecPNStdev()));
		params.add(new BasicNameValuePair("latencyDis50Percent", bean.getLatencyDis50Percent()));
		params.add(new BasicNameValuePair("latencyDis75Percent", bean.getLatencyDis75Percent()));
		params.add(new BasicNameValuePair("latencyDis90Percent", bean.getLatencyDis90Percent()));
		params.add(new BasicNameValuePair("latencyDis99Percent", bean.getLatencyDis99Percent()));
		params.add(new BasicNameValuePair("requestsSec", bean.getRequestsSec()));
		params.add(new BasicNameValuePair("transferSec", bean.getTransferSec()));
		params.add(new BasicNameValuePair("succFlag", bean.getSuccFlag()));
		params.add(new BasicNameValuePair("wrkThreads", String.valueOf(bean.getWrkThreads())));
		params.add(new BasicNameValuePair("wrkConnections", String.valueOf(bean.getWrkConnections())));
		params.add(new BasicNameValuePair("wrkDuration", bean.getWrkDuration()));
		params.add(new BasicNameValuePair("wrkUrl", bean.getWrkUrl()));
		return params;
	}
	
	/**
	 * 数据上传到终端
	 * @param beans
	 * @throws IOException
	 */
	public void uploadExecuteResults(List<WrkResponseBean> beans) throws IOException {
		if(null != beans && beans.size() > 0) {
			String url = PropertiesUtil.getUserConfigProperties().getProperty("executeResponseUploadUrl");
			ExecutorService service = Executors.newFixedThreadPool(5);
			for (WrkResponseBean wrkResponseBean : beans) {
				service.execute(new Runnable() {
					@Override
					public void run() {
						JSONObject doPost = HttpClientUtil.doPost(url, genPostParams(wrkResponseBean));
						if(doPost.getBooleanValue("succ")) {
							logger.info("执行数据上传成功,数据库ID为{},url为{}",wrkResponseBean.getDatabaseId(),wrkResponseBean.getWrkUrl());
						}else {
							logger.info("执行数据上传失败,数据库ID为{},url为{}",wrkResponseBean.getDatabaseId(),wrkResponseBean.getWrkUrl());
						}
						try {
							Thread.sleep(1 * 1000);
						} catch (InterruptedException e) {
							// ignore
						}
					}
				});
			}
			service.shutdown();
		}
	}
}

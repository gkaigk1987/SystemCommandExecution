package gk.younger.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class CommonTest {

	@Test
	public void test01() {
		StringBuffer sb = new StringBuffer();
		sb.append("Running 30s test @ http://www.hao123.com").append("\n").append("  10 threads and 100 connections")
				.append("\n").append("  Thread Stats   Avg      Stdev     Max   +/- Stdev").append("\n")
				.append("    Latency     1.42s   361.90ms   1.98s    62.50%").append("\n")
				.append("    Req/Sec     4.00      4.27    20.00     78.21%").append("\n")
				.append("  Latency Distribution").append("\n").append("     50%    1.47s").append("\n")
				.append("     75%    1.75s").append("\n").append("     90%    1.84s").append("\n")
				.append("     99%    1.98s").append("\n").append("  626 requests in 30.10s, 333.03MB read").append("\n")
				.append("  Socket errors: connect 0, read 0, write 0, timeout 554").append("\n")
				.append("Requests/sec:     20.80").append("\n").append("Transfer/sec:     11.06MB").append("\n")
				.append("$1");
		System.out.println(sb.toString());
		String[] strs = sb.toString().split("\n");
		WrkResponseBean responseBean = new WrkResponseBean();
		
		String Latency = strs[3].trim();
		String[] LatencyArray = Latency.split("\\s+");
		responseBean.setLatencyAvg(LatencyArray[1]);
		responseBean.setLatencyStdev(LatencyArray[2]);
		responseBean.setLatencyMax(LatencyArray[3]);
		responseBean.setLatencyPNStdev(LatencyArray[4]);
		
		String reqSec = strs[4].trim();
		String[] reqSecArray = reqSec.split("\\s+");
		responseBean.setReqSecAvg(reqSecArray[1]);
		responseBean.setReqSecStdev(reqSecArray[2]);
		responseBean.setReqSecMax(reqSecArray[3]);
		responseBean.setReqSecPNStdev(reqSecArray[4]);
		
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
		
		String requestsSec = strs[12].trim();
		String[] requestsSecArray = requestsSec.split("\\s+");
		responseBean.setRequestsSec(requestsSecArray[1]);
		
		String transferSec = strs[13].trim();
		String[] transferSecArray = transferSec.split("\\s+");
		responseBean.setTransferSec(transferSecArray[1]);
		System.out.println(responseBean);
	}
	
	@Test
	public void test02() throws IOException {
		Properties properties = PropertiesUtil.getConfigProperties();
		System.out.println(properties.getProperty("wrk-t"));
	}
	
	/**
	 * 注：测试有问题
	 */
	@Test
	public void test03() {
		String url = "http://192.168.1.108:8081/tenantDatabaseService/getDatabaseService2";
		JSONObject obj = new JSONObject();
		obj.put("databaseSequence", "gktest20170727");
		
		JSONObject doPost = HttpClientUtil.doPost(url, obj);
		System.out.println(JSONObject.toJSONString(doPost));
	}
	
	@Test
	public void test04() {
		String url = "http://192.168.1.108:8081/tenantDatabaseService/getDatabaseService";
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("databaseSequence", "gktest20170727"));
		
		JSONObject doPost = HttpClientUtil.doPost(url, formparams);
		String array = JSONObject.toJSONString(doPost.get("databaseService"));
		List<DatabaseServiceBean> parseArray = JSON.parseArray(array, DatabaseServiceBean.class);
		for (DatabaseServiceBean databaseServiceBean : parseArray) {
			System.out.println(databaseServiceBean);
		}
	}
	
	@Test
	public void test05() {
		String time = "09:01";
		Map<String, Integer> hourAndMinute = Util.getHourAndMinute(time);
		System.out.println(hourAndMinute.get("hour"));
		System.out.println(hourAndMinute.get("minute"));
	}
	
	@Test
	public void test06() {
		String date = Util.dateFormat(new Date());
		System.out.println(date);
	}
	
	@Test
	public void test07() {
		DatabaseServiceBean bean1 = new DatabaseServiceBean();
		bean1.setDatabaseId("1");
		bean1.setServiceId("1");
		bean1.setServiceUrl("http://www.qq.com/");
		bean1.setTestStartTime("9:00");
		bean1.setTestEndTime("19:00");
		bean1.setTestFrequenceMinutes(5);
		
		DatabaseServiceBean bean2 = new DatabaseServiceBean();
		bean2.setDatabaseId("1");
		bean2.setServiceId("1");
		bean2.setServiceUrl("http://www.qq.com/");
		bean2.setTestStartTime("9:00");
		bean2.setTestEndTime("19:00");
		bean2.setTestFrequenceMinutes(6);
		
		System.out.println(bean1.equals(bean2));
	}

}

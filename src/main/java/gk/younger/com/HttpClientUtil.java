package gk.younger.com;

import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HttpClientUtil {
	
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	/**
	 * httpclient的post方法，一般参数
	 * @param url
	 * @return
	 */
	public static JSONObject doPost(String url,List<NameValuePair> formparams) {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
        	UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            post.setEntity(entity);
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
        	if(e instanceof HttpHostConnectException) {
        		logger.error("远程数据库获取服务关闭，无法获取数据！");
        	}
            throw new RuntimeException(e);
        }
        return response;
	}
	
	/**
	 * httpclient的post方法，json格式参数
	 * 注：该方法测试有问题
	 * @param url
	 * @return
	 */
	public static JSONObject doPost(String url,JSONObject json) {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
        	StringEntity s = new StringEntity(json.toJSONString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
	}
	
	/**
	 * post传入JSON字符串，此方法需要服务端使用@RequestBody String XXX来接收参数
	 * @param url
	 * @param json
	 * @return
	 */
	public static JSONObject doPost(String url,String json) {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
        	StringEntity s = new StringEntity(json, Consts.UTF_8);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
	}
	
	/**
	 * httpclient的get方法
	 * @param url
	 * @return
	 */
	public static JSONObject doGet(String url) {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		JSONObject response = null;
		try {
            HttpResponse res = httpclient.execute(httpGet);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
	}
}

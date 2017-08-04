package gk.younger.com;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 工具类
 * @author Administrator
 *
 */
public class Util {
	
	/**
	 * 处理时间
	 * @param time
	 * @return
	 */
	public static Map<String,Integer> getHourAndMinute(String time) {
		if(StringUtils.isNotEmpty(time)) {
			Map<String, Integer> map = new HashMap<>();
			String[] times = time.split(":");
			try {
				String hour = times[0];
				map.put("hour", Integer.valueOf(hour));
				String minute = times[1];
				map.put("minute", Integer.valueOf(minute));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("时间处理出错！");
			}
			return map;
		}
		return null;
	}

}

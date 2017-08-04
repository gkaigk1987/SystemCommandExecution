package gk.younger.com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	/**
	 * 获取配置文件信息
	 * @return
	 * @throws IOException
	 */
	public static Properties getConfigProperties() throws IOException {
		InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
		Properties properties = new Properties();
		properties.load(inputStream);
		inputStream.close();
		return properties;
	}
	
	/**
	 * 获取用户配置的配置文件信息
	 * @return
	 * @throws IOException
	 */
	public static Properties getUserConfigProperties() throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("./resources/config.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties;
		} catch (FileNotFoundException e) {
			logger.error("未找到配置文件，将使用系统默认配置！");
			return getConfigProperties();
		} finally {
			if(inputStream != null) {
				inputStream.close();
			}
		}
	}

}

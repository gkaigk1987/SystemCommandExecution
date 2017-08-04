package gk.younger.com;

public class DatabaseServiceBean {
	
	private String databaseId;
	
	private String serviceId;
	
	private String serviceUrl;
	
	private String testStartTime;
	
	private String testEndTime;
	
	private int testFrequenceMinutes;

	public String getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getTestStartTime() {
		return testStartTime;
	}

	public void setTestStartTime(String testStartTime) {
		this.testStartTime = testStartTime;
	}

	public String getTestEndTime() {
		return testEndTime;
	}

	public void setTestEndTime(String testEndTime) {
		this.testEndTime = testEndTime;
	}

	public int getTestFrequenceMinutes() {
		return testFrequenceMinutes;
	}

	public void setTestFrequenceMinutes(int testFrequenceMinutes) {
		this.testFrequenceMinutes = testFrequenceMinutes;
	}


}

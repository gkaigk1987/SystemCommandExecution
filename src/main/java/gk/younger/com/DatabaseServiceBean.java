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
	
	@Override
	public int hashCode() {
		return 1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(obj instanceof DatabaseServiceBean) {
			DatabaseServiceBean bean = (DatabaseServiceBean)obj;
			if(!this.getDatabaseId().equals(bean.getDatabaseId())) {
				return false;
			}
			if(!this.getServiceId().equals(bean.getServiceId())) {
				return false;
			}
			if(!this.getServiceUrl().equals(bean.getServiceUrl())) {
				return false;
			}
			if(!this.getTestStartTime().equals(bean.getTestStartTime())) {
				return false;
			}
			if(!this.getTestEndTime().equals(bean.getTestEndTime())) {
				return false;
			}
			if(this.getTestFrequenceMinutes() != bean.getTestFrequenceMinutes()) {
				return false;
			}
			return true;
		}
		
		return false;
	}


}

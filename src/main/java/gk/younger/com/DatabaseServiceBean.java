package gk.younger.com;

public class DatabaseServiceBean {
	
	private String id;
	
	private String serviceId;
	
	private String url;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "DatabaseServiceBean [id=" + id + ", serviceId=" + serviceId + ", url=" + url + "]";
	}

}

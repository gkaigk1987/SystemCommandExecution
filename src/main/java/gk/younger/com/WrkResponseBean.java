package gk.younger.com;

public class WrkResponseBean {
	
	/**
	 * 数据库主键
	 */
	private String databaseId;

	/**
	 * 延迟平均值
	 */
	private String latencyAvg;
	
	/**
	 * 延迟标准差
	 */
	private String latencyStdev;
	
	/**
	 * 延迟最大值
	 */
	private String latencyMax;
	
	/**
	 * 延迟正负一个标准差所占比例
	 */
	private String latencyPNStdev;
	
	/**
	 * 处理中的请求数平均值
	 */
	private String reqSecAvg;
	
	/**
	 * 处理中的请求数标准值
	 */
	private String reqSecStdev;
	
	/**
	 * 处理中的请求数最大值
	 */
	private String reqSecMax;
	
	/**
	 * 处理中的请求数正负一个标准差所占比例
	 */
	private String reqSecPNStdev;
	
	/**
	 * 百分之五十的延迟分布
	 */
	private String latencyDis50Percent;
	
	/**
	 * 百分之七十五的延迟分布
	 */
	private String latencyDis75Percent;
	
	/**
	 * 百分之九十的延迟分布
	 */
	private String latencyDis90Percent;
	
	/**
	 * 百分之九十九的延迟分布
	 */
	private String latencyDis99Percent;
	
	/**
	 * 平均每秒完成的请求个数
	 */
	private String requestsSec;
	
	/**
	 * 平均每秒读取数据大小
	 */
	private String transferSec;
	
	/**
	 * 是否成功
	 */
	private String succFlag;
	
	/**
	 * 线程数
	 */
	private Integer wrkThreads;
	
	/**
	 * 连接数
	 */
	private Integer wrkConnections;
	
	/**
	 * 时长
	 */
	private String wrkDuration;
	
	/**
	 * url
	 */
	private String wrkUrl;
	
	public String getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}

	public String getLatencyAvg() {
		return latencyAvg;
	}

	public void setLatencyAvg(String latencyAvg) {
		this.latencyAvg = latencyAvg;
	}

	public String getLatencyStdev() {
		return latencyStdev;
	}

	public void setLatencyStdev(String latencyStdev) {
		this.latencyStdev = latencyStdev;
	}

	public String getLatencyMax() {
		return latencyMax;
	}

	public void setLatencyMax(String latencyMax) {
		this.latencyMax = latencyMax;
	}

	public String getLatencyPNStdev() {
		return latencyPNStdev;
	}

	public void setLatencyPNStdev(String latencyPNStdev) {
		this.latencyPNStdev = latencyPNStdev;
	}

	public String getReqSecAvg() {
		return reqSecAvg;
	}

	public void setReqSecAvg(String reqSecAvg) {
		this.reqSecAvg = reqSecAvg;
	}

	public String getReqSecStdev() {
		return reqSecStdev;
	}

	public void setReqSecStdev(String reqSecStdev) {
		this.reqSecStdev = reqSecStdev;
	}

	public String getReqSecMax() {
		return reqSecMax;
	}

	public void setReqSecMax(String reqSecMax) {
		this.reqSecMax = reqSecMax;
	}

	public String getReqSecPNStdev() {
		return reqSecPNStdev;
	}

	public void setReqSecPNStdev(String reqSecPNStdev) {
		this.reqSecPNStdev = reqSecPNStdev;
	}

	public String getLatencyDis50Percent() {
		return latencyDis50Percent;
	}

	public void setLatencyDis50Percent(String latencyDis50Percent) {
		this.latencyDis50Percent = latencyDis50Percent;
	}

	public String getLatencyDis75Percent() {
		return latencyDis75Percent;
	}

	public void setLatencyDis75Percent(String latencyDis75Percent) {
		this.latencyDis75Percent = latencyDis75Percent;
	}

	public String getLatencyDis90Percent() {
		return latencyDis90Percent;
	}

	public void setLatencyDis90Percent(String latencyDis90Percent) {
		this.latencyDis90Percent = latencyDis90Percent;
	}

	public String getLatencyDis99Percent() {
		return latencyDis99Percent;
	}

	public void setLatencyDis99Percent(String latencyDis99Percent) {
		this.latencyDis99Percent = latencyDis99Percent;
	}

	public String getRequestsSec() {
		return requestsSec;
	}

	public void setRequestsSec(String requestsSec) {
		this.requestsSec = requestsSec;
	}

	public String getTransferSec() {
		return transferSec;
	}

	public void setTransferSec(String transferSec) {
		this.transferSec = transferSec;
	}
	
	public String getSuccFlag() {
		return succFlag;
	}

	public void setSuccFlag(String succFlag) {
		this.succFlag = succFlag;
	}
	
	public Integer getWrkThreads() {
		return wrkThreads;
	}

	public void setWrkThreads(Integer wrkThreads) {
		this.wrkThreads = wrkThreads;
	}

	public Integer getWrkConnections() {
		return wrkConnections;
	}

	public void setWrkConnections(Integer wrkConnections) {
		this.wrkConnections = wrkConnections;
	}

	public String getWrkDuration() {
		return wrkDuration;
	}

	public void setWrkDuration(String wrkDuration) {
		this.wrkDuration = wrkDuration;
	}

	public String getWrkUrl() {
		return wrkUrl;
	}

	public void setWrkUrl(String wrkUrl) {
		this.wrkUrl = wrkUrl;
	}

	@Override
	public String toString() {
		return "WrkResponseBean [databaseId=" + databaseId + ", latencyAvg=" + latencyAvg + ", latencyStdev="
				+ latencyStdev + ", latencyMax=" + latencyMax + ", latencyPNStdev=" + latencyPNStdev + ", reqSecAvg="
				+ reqSecAvg + ", reqSecStdev=" + reqSecStdev + ", reqSecMax=" + reqSecMax + ", reqSecPNStdev="
				+ reqSecPNStdev + ", latencyDis50Percent=" + latencyDis50Percent + ", latencyDis75Percent="
				+ latencyDis75Percent + ", latencyDis90Percent=" + latencyDis90Percent + ", latencyDis99Percent="
				+ latencyDis99Percent + ", requestsSec=" + requestsSec + ", transferSec=" + transferSec + ", succFlag="
				+ succFlag + ", wrkThreads=" + wrkThreads + ", wrkConnections=" + wrkConnections + ", wrkDuration="
				+ wrkDuration + ", wrkUrl=" + wrkUrl + "]";
	}

	
}

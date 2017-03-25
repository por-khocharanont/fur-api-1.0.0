package th.cu.thesis.fur.api.rest.model;

import java.io.Serializable;

public class ApplicationReportParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6103203539410887430L;
	private String username;
	private String appName;
	private String type;
	private String authenticationType;
	private String status;
	private String periodType;
	private String startDate;
	private String endDate;
	private String requestBy;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAuthenticationType() {
		return authenticationType;
	}
	public void setAuthenticationType(String authenticationType) {
		this.authenticationType = authenticationType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRequestBy() {
		return requestBy;
	}
	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApplicationReportParams [username=");
		builder.append(username);
		builder.append(", appName=");
		builder.append(appName);
		builder.append(", type=");
		builder.append(type);
		builder.append(", authenticationType=");
		builder.append(authenticationType);
		builder.append(", status=");
		builder.append(status);
		builder.append(", periodType=");
		builder.append(periodType);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", requestBy=");
		builder.append(requestBy);
		builder.append("]");
		return builder.toString();
	}

	
}

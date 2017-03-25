package th.cu.thesis.fur.api.model;

import java.util.Date;

public class UserAppRole {

	private String userAppRoleId;
	private String appRoleId;
	private String urId;
	private String userId;
	private String periodType;
	private Date startTime;
	private Date endTime;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;

	public String getUserAppRoleId() {
		return userAppRoleId;
	}

	public void setUserAppRoleId(String userAppRoleId) {
		this.userAppRoleId = userAppRoleId;
	}

	public String getAppRoleId() {
		return appRoleId;
	}

	public void setAppRoleId(String appRoleId) {
		this.appRoleId = appRoleId;
	}

	public String getUrId() {
		return urId;
	}

	public void setUrId(String urId) {
		this.urId = urId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public Date getStartTime() {
		return startTime;
	}

	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserAppRole [userAppRoleId=");
		builder.append(userAppRoleId);
		builder.append(", appRoleId=");
		builder.append(appRoleId);
		builder.append(", urId=");
		builder.append(urId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", periodType=");
		builder.append(periodType);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}

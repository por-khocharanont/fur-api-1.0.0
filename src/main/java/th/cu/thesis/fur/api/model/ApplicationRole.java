package th.cu.thesis.fur.api.model;

import java.util.Date;

public class ApplicationRole {

	private String appRoleId;
	private String appId;
	private String appRoleName;
	private String appRoleDesc;
	private String updatedBy;
	private String createdBy;
	private Date createdDate;
	private Date updatedDate;

	public String getAppRoleId() {
		return appRoleId;
	}

	public void setAppRoleId(String appRoleId) {
		this.appRoleId = appRoleId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppRoleName() {
		return appRoleName;
	}

	public void setAppRoleName(String appRoleName) {
		this.appRoleName = appRoleName;
	}

	public String getAppRoleDesc() {
		return appRoleDesc;
	}

	public void setAppRoleDesc(String appRoleDesc) {
		this.appRoleDesc = appRoleDesc;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApplicationRole [appRoleId=");
		builder.append(appRoleId);
		builder.append(", appId=");
		builder.append(appId);
		builder.append(", appRoleName=");
		builder.append(appRoleName);
		builder.append(", appRoleDesc=");
		builder.append(appRoleDesc);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append("]");
		return builder.toString();
	}

}

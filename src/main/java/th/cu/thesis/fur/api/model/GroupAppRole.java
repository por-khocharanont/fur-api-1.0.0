package th.cu.thesis.fur.api.model;

import java.util.Date;

public class GroupAppRole {

	private String groupAppId;
	private String groupAppName;
	private String groupAppType;
	private String status;
	private String updatedBy;
	private String createdBy;
	private Date createdDate;
	private Date updatedDate;

	public String getGroupAppId() {
		return groupAppId;
	}

	public void setGroupAppId(String groupAppId) {
		this.groupAppId = groupAppId;
	}

	public String getGroupAppName() {
		return groupAppName;
	}

	public void setGroupAppName(String groupAppName) {
		this.groupAppName = groupAppName;
	}

	public String getGroupAppType() {
		return groupAppType;
	}

	public void setGroupAppType(String groupAppType) {
		this.groupAppType = groupAppType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		builder.append("GroupAppRole [groupAppId=");
		builder.append(groupAppId);
		builder.append(", groupAppName=");
		builder.append(groupAppName);
		builder.append(", groupAppType=");
		builder.append(groupAppType);
		builder.append(", status=");
		builder.append(status);
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

package th.cu.thesis.fur.api.model;

import java.util.Date;

public class ApproveType {
	private String approveTypeId;
	private String appId;
	private String type;
	private String approveType;
	private String minimumApprove;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;

	public String getApproveTypeId() {
		return approveTypeId;
	}

	public void setApproveTypeId(String approveTypeId) {
		this.approveTypeId = approveTypeId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getApproveType() {
		return approveType;
	}

	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}

	public String getMinimumApprove() {
		return minimumApprove;
	}

	public void setMinimumApprove(String minimumApprove) {
		this.minimumApprove = minimumApprove;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApproveType [approveTypeId=");
		builder.append(approveTypeId);
		builder.append(", appId=");
		builder.append(appId);
		builder.append(", type=");
		builder.append(type);
		builder.append(", approveType=");
		builder.append(approveType);
		builder.append(", minimumApprove=");
		builder.append(minimumApprove);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append("]");
		return builder.toString();
	}

}

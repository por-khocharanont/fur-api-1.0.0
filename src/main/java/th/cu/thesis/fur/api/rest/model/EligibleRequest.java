package th.cu.thesis.fur.api.rest.model;

public class EligibleRequest {
	
	private String appRoleId;
	private String orgcode;
	private String createdBy;
	private String updatedBy;
	public String getAppRoleId() {
		return appRoleId;
	}
	public void setAppRoleId(String appRoleId) {
		this.appRoleId = appRoleId;
	}
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
		builder.append("EligibleRequest [appRoleId=");
		builder.append(appRoleId);
		builder.append(", orgcode=");
		builder.append(orgcode);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	

}

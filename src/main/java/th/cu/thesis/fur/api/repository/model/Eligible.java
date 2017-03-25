package th.cu.thesis.fur.api.repository.model;

public class Eligible {

	private String eligibleId;
	private String appRoleId;
	private String appRoleName;
	private String orgcode;
	private String orgname;
	private String createdBy;
	private String updatedBy;

	public String getEligibleId() {
		return eligibleId;
	}

	public void setEligibleId(String eligibleId) {
		this.eligibleId = eligibleId;
	}

	public String getAppRoleId() {
		return appRoleId;
	}

	public void setAppRoleId(String appRoleId) {
		this.appRoleId = appRoleId;
	}

	public String getAppRoleName() {
		return appRoleName;
	}

	public void setAppRoleName(String appRoleName) {
		this.appRoleName = appRoleName;
	}

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
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
		builder.append("Eligible [eligibleId=");
		builder.append(eligibleId);
		builder.append(", appRoleId=");
		builder.append(appRoleId);
		builder.append(", appRoleName=");
		builder.append(appRoleName);
		builder.append(", orgcode=");
		builder.append(orgcode);
		builder.append(", orgname=");
		builder.append(orgname);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append("]");
		return builder.toString();
	}

}

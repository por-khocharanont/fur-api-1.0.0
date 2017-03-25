package th.cu.thesis.fur.api.rest.model;

import java.io.Serializable;

public class EligibleGridRequest extends CommonGrid implements Serializable {

	private static final long serialVersionUID = 1L;

	private String orgcode;
	private String orgname;
	private String orgdesc;
	private String applicationId;
	private String applicationName;
	private String applicationRoleId;
	private String applicationRoleName;
	private String applicationType;
	private String orderBy;
	private String neName;

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

	public String getOrgdesc() {
		return orgdesc;
	}

	public void setOrgdesc(String orgdesc) {
		this.orgdesc = orgdesc;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationRoleId() {
		return applicationRoleId;
	}

	public void setApplicationRoleId(String applicationRoleId) {
		this.applicationRoleId = applicationRoleId;
	}

	public String getApplicationRoleName() {
		return applicationRoleName;
	}

	public void setApplicationRoleName(String applicationRoleName) {
		this.applicationRoleName = applicationRoleName;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getNeName() {
		return neName;
	}

	public void setNeName(String neName) {
		this.neName = neName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EligibleGridRequest [orgcode=");
		builder.append(orgcode);
		builder.append(", orgname=");
		builder.append(orgname);
		builder.append(", orgdesc=");
		builder.append(orgdesc);
		builder.append(", applicationId=");
		builder.append(applicationId);
		builder.append(", applicationName=");
		builder.append(applicationName);
		builder.append(", applicationRoleId=");
		builder.append(applicationRoleId);
		builder.append(", applicationRoleName=");
		builder.append(applicationRoleName);
		builder.append(", applicationType=");
		builder.append(applicationType);
		builder.append(", orderBy=");
		builder.append(orderBy);
		builder.append(", neName=");
		builder.append(neName);
		builder.append("]");
		return builder.toString();
	}

}

package th.cu.thesis.fur.api.repository.model;

import java.io.Serializable;

public class EligibleOrgAppRoleApp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 200069668852334795L;
	private String no;
	private String eligibleId;
	private String orgcode;
	private String orgname;
	private String orgdesc;
	private String appId;
	private String appName;
	private String appRoleId;
	private String appRoleName;
	private String neName;
	private String appRoleDesc;
	private String type;
	private String typeText;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getEligibleId() {
		return eligibleId;
	}
	public void setEligibleId(String eligibleId) {
		this.eligibleId = eligibleId;
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
	public String getOrgdesc() {
		return orgdesc;
	}
	public void setOrgdesc(String orgdesc) {
		this.orgdesc = orgdesc;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
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
	public String getNeName() {
		return neName;
	}
	public void setNeName(String neName) {
		this.neName = neName;
	}
	public String getAppRoleDesc() {
		return appRoleDesc;
	}
	public void setAppRoleDesc(String appRoleDesc) {
		this.appRoleDesc = appRoleDesc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeText() {
		return typeText;
	}
	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EligibleOrgAppRoleApp [no=");
		builder.append(no);
		builder.append(", eligibleId=");
		builder.append(eligibleId);
		builder.append(", orgcode=");
		builder.append(orgcode);
		builder.append(", orgname=");
		builder.append(orgname);
		builder.append(", orgdesc=");
		builder.append(orgdesc);
		builder.append(", appId=");
		builder.append(appId);
		builder.append(", appName=");
		builder.append(appName);
		builder.append(", appRoleId=");
		builder.append(appRoleId);
		builder.append(", appRoleName=");
		builder.append(appRoleName);
		builder.append(", neName=");
		builder.append(neName);
		builder.append(", appRoleDesc=");
		builder.append(appRoleDesc);
		builder.append(", type=");
		builder.append(type);
		builder.append(", typeText=");
		builder.append(typeText);
		builder.append("]");
		return builder.toString();
	}
	

}

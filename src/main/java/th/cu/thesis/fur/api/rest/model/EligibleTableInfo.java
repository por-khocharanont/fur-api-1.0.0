package th.cu.thesis.fur.api.rest.model;

import java.util.List;

import th.cu.thesis.fur.api.repository.model.Eligible;

public class EligibleTableInfo {
	private String appId;
	private String orgname;
	private String orgdesc;
	private String orgcode;
	private List<Eligible> eligible;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
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

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public List<Eligible> getEligible() {
		return eligible;
	}

	public void setEligible(List<Eligible> eligible) {
		this.eligible = eligible;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EligibleTableInfo [appId=");
		builder.append(appId);
		builder.append(", orgname=");
		builder.append(orgname);
		builder.append(", orgdesc=");
		builder.append(orgdesc);
		builder.append(", orgcode=");
		builder.append(orgcode);
		builder.append(", eligible=");
		builder.append(eligible);
		builder.append("]");
		return builder.toString();
	}

}
package th.cu.thesis.fur.api.model;

import java.util.Date;
import java.util.List;

public class CustodianTeam {
	private String custodianTeamId;
	private String appId;
	private String teamName;
	private String sequenceTeam;
	private String userType;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;

	private List<CustodianMember> member;

	public String getCustodianTeamId() {
		return custodianTeamId;
	}

	public void setCustodianTeamId(String custodianTeamId) {
		this.custodianTeamId = custodianTeamId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getSequenceTeam() {
		return sequenceTeam;
	}

	public void setSequenceTeam(String sequenceTeam) {
		this.sequenceTeam = sequenceTeam;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public List<CustodianMember> getMember() {
		return member;
	}

	public void setMember(List<CustodianMember> member) {
		this.member = member;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustodianTeam [custodianTeamId=");
		builder.append(custodianTeamId);
		builder.append(", appId=");
		builder.append(appId);
		builder.append(", teamName=");
		builder.append(teamName);
		builder.append(", sequenceTeam=");
		builder.append(sequenceTeam);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", member=");
		builder.append(member);
		builder.append("]");
		return builder.toString();
	}

}

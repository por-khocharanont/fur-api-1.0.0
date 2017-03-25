package th.cu.thesis.fur.api.model;

import java.util.Date;
import java.util.List;

public class AppOwnerTeam {
	private String appOwnerTeamId;
	private String appId;
	private String teamName;
	private String sequenceTeam;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;

	private List<AppOwnerMember> appOwnerMemberList;

	public AppOwnerTeam() {
	}

	public String getAppOwnerTeamId() {
		return appOwnerTeamId;
	}

	public void setAppOwnerTeamId(String appOwnerTeamId) {
		this.appOwnerTeamId = appOwnerTeamId;
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

	public List<AppOwnerMember> getAppOwnerMemberList() {
		return appOwnerMemberList;
	}

	public void setAppOwnerMemberList(List<AppOwnerMember> appOwnerMemberList) {
		this.appOwnerMemberList = appOwnerMemberList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppOwnerTeam [appOwnerTeamId=");
		builder.append(appOwnerTeamId);
		builder.append(", appId=");
		builder.append(appId);
		builder.append(", teamName=");
		builder.append(teamName);
		builder.append(", sequenceTeam=");
		builder.append(sequenceTeam);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", appOwnerMemberList=");
		builder.append(appOwnerMemberList);
		builder.append("]");
		return builder.toString();
	}

}

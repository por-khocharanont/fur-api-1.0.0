package th.cu.thesis.fur.api.model;

import java.io.Serializable;

public class UserCustodian extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738882005484597500L;
	private String custodianId;
	private String appId;
	private String seQuenceUser;
	private String userType;
	private String custodianTeamId;
	private String custodianmemberId;
	private String teamName;
	private String seQuenceTeam;
	public String getCustodianId() {
		return custodianId;
	}
	public void setCustodianId(String custodianId) {
		this.custodianId = custodianId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSeQuenceUser() {
		return seQuenceUser;
	}
	public void setSeQuenceUser(String seQuenceUser) {
		this.seQuenceUser = seQuenceUser;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getCustodianTeamId() {
		return custodianTeamId;
	}
	public void setCustodianTeamId(String custodianTeamId) {
		this.custodianTeamId = custodianTeamId;
	}
	public String getCustodianmemberId() {
		return custodianmemberId;
	}
	public void setCustodianmemberId(String custodianmemberId) {
		this.custodianmemberId = custodianmemberId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getSeQuenceTeam() {
		return seQuenceTeam;
	}
	public void setSeQuenceTeam(String seQuenceTeam) {
		this.seQuenceTeam = seQuenceTeam;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserCustodian [custodianId=");
		builder.append(custodianId);
		builder.append(", appId=");
		builder.append(appId);
		builder.append(", seQuenceUser=");
		builder.append(seQuenceUser);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", custodianTeamId=");
		builder.append(custodianTeamId);
		builder.append(", custodianmemberId=");
		builder.append(custodianmemberId);
		builder.append(", teamName=");
		builder.append(teamName);
		builder.append(", seQuenceTeam=");
		builder.append(seQuenceTeam);
		builder.append("]");
		return builder.toString();
	}
	
}

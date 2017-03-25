package th.cu.thesis.fur.api.model;

import java.io.Serializable;

public class UserAppOwner extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4132727227479676178L;
	
	private String appOwnerId;
	private String appId;
	private String seQuenceUser;
	private String appOwnerMemberId;
	private String appOwnerTeamId;
	private String teamName;
	private String seQuenceTeam;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserAppOwner [appOwnerId=");
		builder.append(appOwnerId);
		builder.append(", appId=");
		builder.append(appId);
		builder.append(", seQuenceUser=");
		builder.append(seQuenceUser);
		builder.append(", appOwnerMemberId=");
		builder.append(appOwnerMemberId);
		builder.append(", appOwnerTeamId=");
		builder.append(appOwnerTeamId);
		builder.append(", teamName=");
		builder.append(teamName);
		builder.append(", seQuenceTeam=");
		builder.append(seQuenceTeam);
		builder.append("]");
		return builder.toString();
	}
	public String getAppOwnerId() {
		return appOwnerId;
	}
	public void setAppOwnerId(String appOwnerId) {
		this.appOwnerId = appOwnerId;
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
	public String getAppOwnerMemberId() {
		return appOwnerMemberId;
	}
	public void setAppOwnerMemberId(String appOwnerMemberId) {
		this.appOwnerMemberId = appOwnerMemberId;
	}
	public String getAppOwnerTeamId() {
		return appOwnerTeamId;
	}
	public void setAppOwnerTeamId(String appOwnerTeamId) {
		this.appOwnerTeamId = appOwnerTeamId;
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
	
}

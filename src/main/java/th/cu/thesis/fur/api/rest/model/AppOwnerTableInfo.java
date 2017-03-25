package th.cu.thesis.fur.api.rest.model;

import java.util.List;

import th.cu.thesis.fur.api.model.AppOwner;
import th.cu.thesis.fur.api.model.AppOwnerMember;
import th.cu.thesis.fur.api.model.AppOwnerTeam;

public class AppOwnerTableInfo {
	private String appOwnerType;
	private String appOwnerApproveType;
	private String appOwnerMinimum;
	private List<AppOwner> appOwnerList;
	private List<AppOwnerTeam> appOwnerTeamList;
	
	public String getAppOwnerType() {
		return appOwnerType;
	}
	public void setAppOwnerType(String appOwnerType) {
		this.appOwnerType = appOwnerType;
	}
	public String getAppOwnerApproveType() {
		return appOwnerApproveType;
	}
	public void setAppOwnerApproveType(String appOwnerApproveType) {
		this.appOwnerApproveType = appOwnerApproveType;
	}
	public String getAppOwnerMinimum() {
		return appOwnerMinimum;
	}
	public void setAppOwnerMinimum(String appOwnerMinimum) {
		this.appOwnerMinimum = appOwnerMinimum;
	}
	public List<AppOwner> getAppOwnerList() {
		return appOwnerList;
	}
	public void setAppOwnerList(List<AppOwner> appOwnerList) {
		this.appOwnerList = appOwnerList;
	}
	public List<AppOwnerTeam> getAppOwnerTeamList() {
		return appOwnerTeamList;
	}
	public void setAppOwnerTeamList(List<AppOwnerTeam> appOwnerTeamList) {
		this.appOwnerTeamList = appOwnerTeamList;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppOwnerTableInfo [appOwnerType=");
		builder.append(appOwnerType);
		builder.append(", appOwnerApproveType=");
		builder.append(appOwnerApproveType);
		builder.append(", appOwnerMinimum=");
		builder.append(appOwnerMinimum);
		builder.append(", appOwnerList=");
		builder.append(appOwnerList);
		builder.append(", appOwnerTeamList=");
		builder.append(appOwnerTeamList);
		builder.append("]");
		return builder.toString();
	}

	
	
}

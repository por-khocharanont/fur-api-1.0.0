package th.cu.thesis.fur.api.model;

import java.util.List;

import th.cu.thesis.fur.api.rest.model.UserInfo;

public class MemberList {
	private List<UserInfo> personList;
	private List<TeamInfo> teamList;

	public MemberList() {
	}

	public MemberList(List<UserInfo> personList, List<TeamInfo> teamList) {
		this.personList = personList;
		this.teamList = teamList;
	}

	public List<UserInfo> getPersonList() {
		return personList;
	}

	public void setPersonList(List<UserInfo> personList) {
		this.personList = personList;
	}

	public List<TeamInfo> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<TeamInfo> teamList) {
		this.teamList = teamList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MemberList [personList=");
		builder.append(personList);
		builder.append(", teamList=");
		builder.append(teamList);
		builder.append("]");
		return builder.toString();
	}

}

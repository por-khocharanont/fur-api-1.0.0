package th.cu.thesis.fur.api.rest.model.om;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NewDataSet")
public class ListUserReOrgResponse {

	
	@XStreamAlias("Permission")
	private Permission permission;
	@XStreamAlias("Table")
	private List<UserReOrg> userReOrg;
	
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public List<UserReOrg> getUserReOrg() {
		return userReOrg;
	}
	public void setUserReOrg(List<UserReOrg> userReOrg) {
		this.userReOrg = userReOrg;
	}
	
	
}

package th.cu.thesis.fur.api.rest.model.om;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("NewDataSet")
public class ListNewUserProfileResponse {
	
	@XStreamAlias("Permission")
	private Permission permission;
	@XStreamAlias("Table")
	private List<NewUserProfile> newUserProfile;
	
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
//	public NewUserProfile getNewUserProfile() {
//		return newUserProfile;
//	}
//	public void setNewUserProfile(NewUserProfile newUserProfile) {
//		this.newUserProfile = newUserProfile;
//	}
//	
	public List<NewUserProfile> getNewUserProfile() {
		return newUserProfile;
	}
	public void setNewUserProfile(List<NewUserProfile> newUserProfile) {
		this.newUserProfile = newUserProfile;
	}
	
	
	

}

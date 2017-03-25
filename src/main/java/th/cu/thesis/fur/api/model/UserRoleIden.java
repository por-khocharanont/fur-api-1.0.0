package th.cu.thesis.fur.api.model;

import java.io.Serializable;


public class UserRoleIden extends User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7859552531211295390L;
	private String roleIdenId;
	private String userType;
	
	public String getRoleIdenId() {
		return roleIdenId;
	}
	public void setRoleIdenId(String roleIdenId) {
		this.roleIdenId = roleIdenId;
	}

	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	

}

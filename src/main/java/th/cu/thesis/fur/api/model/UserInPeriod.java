package th.cu.thesis.fur.api.model;

import java.util.Date;

public class UserInPeriod {

	public  UserInPeriod() {
		
	}
	
	private String userId;
	private String userName;
	private String email;
	private String appId;
	private String appRoleName;
	private String appName;
	private Date startTime;
	private Date endTime;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppRoleName() {
		return appRoleName;
	}
	public void setAppRoleName(String appRoleName) {
		this.appRoleName = appRoleName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	

	
}

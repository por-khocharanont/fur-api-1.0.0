package th.cu.thesis.fur.api.model;


public class NewUser {
	
	public  NewUser() {
		
	}
	
	private String userId;
	private String pincode;
	private String username;
	private String email;
	private String enName;
	private String enSurName;
	private String enFullName;
	private String thName;
	private String thSurName;
	private String thFullName;
	private String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getEnSurName() {
		return enSurName;
	}
	public void setEnSurName(String enSurName) {
		this.enSurName = enSurName;
	}
	public String getEnFullName() {
		return enFullName;
	}
	public void setEnFullName(String enFullName) {
		this.enFullName = enFullName;
	}
	public String getThName() {
		return thName;
	}
	public void setThName(String thName) {
		this.thName = thName;
	}
	public String getThSurName() {
		return thSurName;
	}
	public void setThSurName(String thSurName) {
		this.thSurName = thSurName;
	}
	public String getThFullName() {
		return thFullName;
	}
	public void setThFullName(String thFullName) {
		this.thFullName = thFullName;
	}
	
	

}

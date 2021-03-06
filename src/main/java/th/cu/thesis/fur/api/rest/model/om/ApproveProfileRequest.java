package th.cu.thesis.fur.api.rest.model.om;

public class ApproveProfileRequest {
	private String userName;
	private String ipAddress;
	private String pinCode;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public ApproveProfileRequest(String userName, String ipAddress,
			String pinCode) {
		super();
		this.userName = userName;
		this.ipAddress = ipAddress;
		this.pinCode = pinCode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApproveProfileRequest [userName=");
		builder.append(userName);
		builder.append(", ipAddress=");
		builder.append(ipAddress);
		builder.append(", pinCode=");
		builder.append(pinCode);
		builder.append("]");
		return builder.toString();
	}
	
}

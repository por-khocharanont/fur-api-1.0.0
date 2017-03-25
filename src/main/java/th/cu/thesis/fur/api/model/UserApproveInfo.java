package th.cu.thesis.fur.api.model;

public class UserApproveInfo {

	private String username;
	private String approveStatus;
	private String tokenSerialNumber;
	
	public String getTokenSerialNumber() {
		return tokenSerialNumber;
	}

	public void setTokenSerialNumber(String tokenSerialNumber) {
		this.tokenSerialNumber = tokenSerialNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserApproveInfo [username=");
		builder.append(username);
		builder.append(", approveStatus=");
		builder.append(approveStatus);
		builder.append("]");
		return builder.toString();
	}

}

package th.cu.thesis.fur.api.rest.model;

public class ApplicationGridRequest extends CommonGrid {

	private String appName;
	private String status;
	private String roleName;
	private String authenticationType;
	private String appOwnerName;
	private String custodianName;
	private String applicationType;

	public ApplicationGridRequest() {
		super();
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(String authenticationType) {
		this.authenticationType = authenticationType;
	}

	public String getAppOwnerName() {
		return appOwnerName;
	}

	public void setAppOwnerName(String appOwnerName) {
		this.appOwnerName = appOwnerName;
	}

	public String getCustodianName() {
		return custodianName;
	}

	public void setCustodianName(String custodianName) {
		this.custodianName = custodianName;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApplicationGridRequest [appName=");
		builder.append(appName);
		builder.append(", status=");
		builder.append(status);
		builder.append(", roleName=");
		builder.append(roleName);
		builder.append(", authenticationType=");
		builder.append(authenticationType);
		builder.append(", appOwnerName=");
		builder.append(appOwnerName);
		builder.append(", custodianName=");
		builder.append(custodianName);
		builder.append(", applicationType=");
		builder.append(applicationType);
		builder.append("]");
		return builder.toString();
	}

}

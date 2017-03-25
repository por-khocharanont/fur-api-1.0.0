package th.cu.thesis.fur.api.model;

import java.util.Date;

public class UserRequestInfo extends UR {

	private String requestByFullName;
	private String requestBy;
	private Date requestDate;
	private String urStep;
	private String urApprove;
	private String urReject;
	private String urToken;
	private String urDefault;
	private String subject;

	public UserRequestInfo(){
		super();
	}
	
	public UserRequestInfo(UR ur) {
		super(ur);
	}
	
	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getRequestByFullName() {
		return requestByFullName;
	}

	public void setRequestByFullName(String requestByFullName) {
		this.requestByFullName = requestByFullName;
	}

	public String getRequestBy() {
		return requestBy;
	}

	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}

	public String getUrStep() {
		return urStep;
	}

	public void setUrStep(String urStep) {
		this.urStep = urStep;
	}

	public String getUrApprove() {
		return urApprove;
	}

	public void setUrApprove(String urApprove) {
		this.urApprove = urApprove;
	}

	public String getUrReject() {
		return urReject;
	}

	public void setUrReject(String urReject) {
		this.urReject = urReject;
	}

	public String getUrToken() {
		return urToken;
	}

	public void setUrToken(String urToken) {
		this.urToken = urToken;
	}

	public String getUrDefault() {
		return urDefault;
	}

	public void setUrDefault(String urDefault) {
		this.urDefault = urDefault;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRequestInfo [requestByFullName=");
		builder.append(requestByFullName);
		builder.append(", requestBy=");
		builder.append(requestBy);
		builder.append(", requestDate=");
		builder.append(requestDate);
		builder.append(", urStep=");
		builder.append(urStep);
		builder.append(", urApprove=");
		builder.append(urApprove);
		builder.append(", urReject=");
		builder.append(urReject);
		builder.append(", urToken=");
		builder.append(urToken);
		builder.append(", urDefault=");
		builder.append(urDefault);
		builder.append(", subject=");
		builder.append(subject);
		builder.append("]");
		return builder.toString();
	}
	
	

	

}

package th.cu.thesis.fur.api.model;

import java.util.Date;

public class RequestNo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 499397009193179324L;
	private String requestNo;
	private String urType;
	private String requestList;
	private String requestType;
	private String changeType;
	private String subject;
	private String detail;
	private String groupAppRoleId;
	private String groupAppName;
	private String location;
	private String requestBy;
	private Date requestDate;
	private String username;
	private String enname;
	private String ensurname;
	private String email;
	private String mobile;
	private String phone;
	private String position;
	private String organize;
	private String company;
	private String requestRemark;

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getUrType() {
		return urType;
	}

	public void setUrType(String urType) {
		this.urType = urType;
	}

	public String getRequestList() {
		return requestList;
	}

	public void setRequestList(String requestList) {
		this.requestList = requestList;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getGroupAppRoleId() {
		return groupAppRoleId;
	}

	public void setGroupAppRoleId(String groupAppRoleId) {
		this.groupAppRoleId = groupAppRoleId;
	}

	public String getGroupAppName() {
		return groupAppName;
	}

	public void setGroupAppName(String groupAppName) {
		this.groupAppName = groupAppName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRequestBy() {
		return requestBy;
	}

	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public String getEnsurname() {
		return ensurname;
	}

	public void setEnsurname(String ensurname) {
		this.ensurname = ensurname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getOrganize() {
		return organize;
	}

	public void setOrganize(String organize) {
		this.organize = organize;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRequestRemark() {
		return requestRemark;
	}

	public void setRequestRemark(String requestRemark) {
		this.requestRemark = requestRemark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestNo [requestNo=");
		builder.append(requestNo);
		builder.append(", urType=");
		builder.append(urType);
		builder.append(", requestList=");
		builder.append(requestList);
		builder.append(", requestType=");
		builder.append(requestType);
		builder.append(", changeType=");
		builder.append(changeType);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", detail=");
		builder.append(detail);
		builder.append(", groupAppRoleId=");
		builder.append(groupAppRoleId);
		builder.append(", groupAppName=");
		builder.append(groupAppName);
		builder.append(", location=");
		builder.append(location);
		builder.append(", requestBy=");
		builder.append(requestBy);
		builder.append(", requestDate=");
		builder.append(requestDate);
		builder.append(", username=");
		builder.append(username);
		builder.append(", enname=");
		builder.append(enname);
		builder.append(", ensurname=");
		builder.append(ensurname);
		builder.append(", email=");
		builder.append(email);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", position=");
		builder.append(position);
		builder.append(", organize=");
		builder.append(organize);
		builder.append(", company=");
		builder.append(company);
		builder.append(", requestRemark=");
		builder.append(requestRemark);
		builder.append("]");
		return builder.toString();
	}

}

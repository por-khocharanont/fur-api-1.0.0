package th.cu.thesis.fur.api.model;

import java.util.Date;

public class SystemApproveHistory {

	private String urApproveHistoryId;
	private String urId;
	private String urStepId;
	private String status;
	private Date approveTime;
	private String remark;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	private String pincode;
	private String enname;
	private String ensurname;
	private String username;
	private String email;
	private String phone;
	private String mobile;
	private String teamName;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getUrApproveHistoryId() {
		return urApproveHistoryId;
	}

	public void setUrApproveHistoryId(String urApproveHistoryId) {
		this.urApproveHistoryId = urApproveHistoryId;
	}

	public String getUrId() {
		return urId;
	}

	public void setUrId(String urId) {
		this.urId = urId;
	}

	public String getUrStepId() {
		return urStepId;
	}

	public void setUrStepId(String urStepId) {
		this.urStepId = urStepId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdateBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SystemApproveHistory [urApproveHistoryId=");
		builder.append(urApproveHistoryId);
		builder.append(", urId=");
		builder.append(urId);
		builder.append(", urStepId=");
		builder.append(urStepId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", approveTime=");
		builder.append(approveTime);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", pincode=");
		builder.append(pincode);
		builder.append(", enname=");
		builder.append(enname);
		builder.append(", ensurname=");
		builder.append(ensurname);
		builder.append(", username=");
		builder.append(username);
		builder.append(", email=");
		builder.append(email);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", teamName=");
		builder.append(teamName);
		builder.append("]");
		return builder.toString();
	}

}

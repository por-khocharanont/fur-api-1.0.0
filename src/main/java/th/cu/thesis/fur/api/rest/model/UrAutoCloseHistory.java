package th.cu.thesis.fur.api.rest.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import th.cu.thesis.fur.api.util.DateJsonSerializer;

public class UrAutoCloseHistory {
	
	private String urApproveHistoryId ;
	private String urStepId;
	private String urId;
	private String pincode;
	private String enname;
	private String ensurname;
	private String username;
	private String email;
	private String phone;
	private String mobile;
	private String status;
	private String approveTime;
	private String approveFile;
	private String remark;
	private String createdDate;
	private String createdBy;
	private String updatedDate;
	private String updatedBy;
	private String teamName;
	public String getUrApproveHistoryId() {
		return urApproveHistoryId;
	}
	public void setUrApproveHistoryId(String urApproveHistoryId) {
		this.urApproveHistoryId = urApproveHistoryId;
	}
	public String getUrStepId() {
		return urStepId;
	}
	public void setUrStepId(String urStepId) {
		this.urStepId = urStepId;
	}
	public String getUrId() {
		return urId;
	}
	public void setUrId(String urId) {
		this.urId = urId;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApproveTime() {
		return approveTime;
	}
	@JsonSerialize(using = DateJsonSerializer.class)
	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}
	public String getApproveFile() {
		return approveFile;
	}
	public void setApproveFile(String approveFile) {
		this.approveFile = approveFile;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	@JsonSerialize(using = DateJsonSerializer.class)
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	@JsonSerialize(using = DateJsonSerializer.class)
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	
	

}

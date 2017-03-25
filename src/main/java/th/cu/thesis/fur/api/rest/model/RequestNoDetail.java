package th.cu.thesis.fur.api.rest.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import th.cu.thesis.fur.api.repository.model.UrListUrForUser;
import th.cu.thesis.fur.api.util.DateJsonSerializer;

public class RequestNoDetail {
	
	private String requestNo;
	private String urType;
	private String urTypeText;
	private String requestList;
	private String requestListText;
	private String requestType;
	private String requestTypeText;
	private Date requestDate;
	private String enName;
	private String enSurName;
	private String subject;
	private String detail;
	private String groupAppName;
	private String requestRemark;
	private String username;
	private String email;
	private String phone;
	private String mobile;
	private String position;
	private String orgDesc;
	private String coName;
	private List<UrListUrForUser> listUr;
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
	public String getUrTypeText() {
		return urTypeText;
	}
	public void setUrTypeText(String urTypeText) {
		this.urTypeText = urTypeText;
	}
	public String getRequestList() {
		return requestList;
	}
	public void setRequestList(String requestList) {
		this.requestList = requestList;
	}
	public String getRequestListText() {
		return requestListText;
	}
	public void setRequestListText(String requestListText) {
		this.requestListText = requestListText;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRequestTypeText() {
		return requestTypeText;
	}
	public void setRequestTypeText(String requestTypeText) {
		this.requestTypeText = requestTypeText;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
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
	public String getGroupAppName() {
		return groupAppName;
	}
	public void setGroupAppName(String groupAppName) {
		this.groupAppName = groupAppName;
	}
	public String getRequestRemark() {
		return requestRemark;
	}
	public void setRequestRemark(String requestRemark) {
		this.requestRemark = requestRemark;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getOrgDesc() {
		return orgDesc;
	}
	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}
	public String getCoName() {
		return coName;
	}
	public void setCoName(String coName) {
		this.coName = coName;
	}
	public List<UrListUrForUser> getListUr() {
		return listUr;
	}
	public void setListUr(List<UrListUrForUser> listUr) {
		this.listUr = listUr;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WatchListUserRequestDetail [requestNo=");
		builder.append(requestNo);
		builder.append(", urType=");
		builder.append(urType);
		builder.append(", urTypeText=");
		builder.append(urTypeText);
		builder.append(", requestList=");
		builder.append(requestList);
		builder.append(", requestListText=");
		builder.append(requestListText);
		builder.append(", requestType=");
		builder.append(requestType);
		builder.append(", requestTypeText=");
		builder.append(requestTypeText);
		builder.append(", requestDate=");
		builder.append(requestDate);
		builder.append(", enName=");
		builder.append(enName);
		builder.append(", enSurName=");
		builder.append(enSurName);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", detail=");
		builder.append(detail);
		builder.append(", groupAppName=");
		builder.append(groupAppName);
		builder.append(", requestRemark=");
		builder.append(requestRemark);
		builder.append(", username=");
		builder.append(username);
		builder.append(", email=");
		builder.append(email);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", position=");
		builder.append(position);
		builder.append(", orgDesc=");
		builder.append(orgDesc);
		builder.append(", coName=");
		builder.append(coName);
		builder.append(", listUr=");
		builder.append(listUr);
		builder.append("]");
		return builder.toString();
	}
	
	

}

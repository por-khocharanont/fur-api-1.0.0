package th.cu.thesis.fur.api.model;

import java.util.Date;

public class UR {

	private String urId;
	private String requestNo;
	private String flowId;
	private String appId;
	private String appName;
	private String appRoleId;
	private String appRoleName;
	private String requestType;
	private String periodType;
	private Date endTime;
	private Date startTime;
	private String rolePastId;
	private String rolePast;
	private String status;
	private String remark;
	private String urFile;
	private String updatedBy;
	private String createdBy;
	private Date createdDate;
	private Date updatedDate;
	
	
	public UR(){
		
	}
	
	public UR(UR ur) {
		this.urId = ur.getUrId();
		this.requestNo = ur.getRequestNo();
		this.flowId = ur.getFlowId();
		this.appId = ur.getAppId();
		this.appName = ur.getAppName();
		this.appRoleId = ur.getAppRoleId();
		this.appRoleName = ur.getAppRoleName();
		this.requestType = ur.getRequestType();
		this.periodType = ur.getPeriodType();
		this.endTime = ur.getEndTime();
		this.startTime = ur.getStartTime();
		this.rolePastId = ur.getRolePastId();
		this.rolePast = ur.getRolePast();
		this.status = ur.getStatus();
		this.remark = ur.getRemark();
		this.urFile = ur.getUrFile();
		this.updatedBy = ur.getUpdatedBy();
		this.createdBy = ur.getCreatedBy();
		this.createdDate = ur.getCreatedDate();
		this.updatedDate = ur.getUpdatedDate();
	}
	public String getUrId() {
		return urId;
	}
	public void setUrId(String urId) {
		this.urId = urId;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppRoleId() {
		return appRoleId;
	}
	public void setAppRoleId(String appRoleId) {
		this.appRoleId = appRoleId;
	}
	public String getAppRoleName() {
		return appRoleName;
	}
	public void setAppRoleName(String appRoleName) {
		this.appRoleName = appRoleName;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public Date getEndTime() {
		return endTime;
	}
	
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getRolePastId() {
		return rolePastId;
	}
	public void setRolePastId(String rolePastId) {
		this.rolePastId = rolePastId;
	}
	public String getRolePast() {
		return rolePast;
	}
	public void setRolePast(String rolePast) {
		this.rolePast = rolePast;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUrFile() {
		return urFile;
	}
	public void setUrFile(String urFile) {
		this.urFile = urFile;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UR [urId=");
		builder.append(urId);
		builder.append(", requestNo=");
		builder.append(requestNo);
		builder.append(", flowId=");
		builder.append(flowId);
		builder.append(", appId=");
		builder.append(appId);
		builder.append(", appName=");
		builder.append(appName);
		builder.append(", appRoleId=");
		builder.append(appRoleId);
		builder.append(", appRoleName=");
		builder.append(appRoleName);
		builder.append(", requestType=");
		builder.append(requestType);
		builder.append(", periodType=");
		builder.append(periodType);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", rolePastId=");
		builder.append(rolePastId);
		builder.append(", rolePast=");
		builder.append(rolePast);
		builder.append(", status=");
		builder.append(status);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", urFile=");
		builder.append(urFile);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append("]");
		return builder.toString();
	}
	
}

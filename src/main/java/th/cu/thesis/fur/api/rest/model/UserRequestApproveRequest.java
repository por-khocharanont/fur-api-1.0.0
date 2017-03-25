package th.cu.thesis.fur.api.rest.model;

import java.util.List;

import th.cu.thesis.fur.api.model.FileInfo;
import th.cu.thesis.fur.api.model.UserApproveInfo;
import th.cu.thesis.fur.api.model.UserRequestInfo;

public class UserRequestApproveRequest {

	private List<UserApproveInfo> userApproveList;
	private List<UserRequestInfo> urApproveList;
	private String remark;
	private String approveUR;
	private List<FileInfo> file;
	private String appRoleId;
	private String appRoleName;

	public List<UserApproveInfo> getUserApproveList() {
		return userApproveList;
	}

	public void setUserApproveList(List<UserApproveInfo> userApproveList) {
		this.userApproveList = userApproveList;
	}

	public List<UserRequestInfo> getUrApproveList() {
		return urApproveList;
	}

	public void setUrApproveList(List<UserRequestInfo> urApproveList) {
		this.urApproveList = urApproveList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApproveUR() {
		return approveUR;
	}

	public void setApproveUR(String approveUR) {
		this.approveUR = approveUR;
	}

	public List<FileInfo> getFile() {
		return file;
	}

	public void setFile(List<FileInfo> file) {
		this.file = file;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRequestApproveRequest [userApproveList=");
		builder.append(userApproveList);
		builder.append(", urApproveList=");
		builder.append(urApproveList);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", approveUR=");
		builder.append(approveUR);
		builder.append(", file=");
		builder.append(file);
		builder.append(", appRoleId=");
		builder.append(appRoleId);
		builder.append(", appRoleName=");
		builder.append(appRoleName);
		builder.append("]");
		return builder.toString();
	}

}

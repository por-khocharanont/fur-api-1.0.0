package th.cu.thesis.fur.api.rest.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class UserRequestFromGrid implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1636169599086916997L;
	private String appId;
	private String appRoleId;
	private String application;
	private String remark;
	private String roleapplication;
	private String status;
	private List<String> temfile;
	private List<MultipartFile> upload;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppRoleId() {
		return appRoleId;
	}
	public void setAppRoleId(String appRoleId) {
		this.appRoleId = appRoleId;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRoleapplication() {
		return roleapplication;
	}
	public void setRoleapplication(String roleapplication) {
		this.roleapplication = roleapplication;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getTemfile() {
		return temfile;
	}
	public void setTemfile(List<String> temfile) {
		this.temfile = temfile;
	}
	public List<MultipartFile> getUpload() {
		return upload;
	}
	public void setUpload(List<MultipartFile> upload) {
		this.upload = upload;
	}
	
}

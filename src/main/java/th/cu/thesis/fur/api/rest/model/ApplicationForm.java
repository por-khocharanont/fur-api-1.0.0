package th.cu.thesis.fur.api.rest.model;

import java.util.Arrays;
import java.util.List;

import th.cu.thesis.fur.api.model.ApplicationRole;
import th.cu.thesis.fur.api.model.MemberList;
import th.cu.thesis.fur.api.repository.model.Eligible;

public class ApplicationForm {

	private String appId;
	private String appName;
	private String appInfo;
	private String applicationType;
	private String[] authentication;
	private String status;

	private List<ApplicationRole> roleApplication;

	private List<Eligible> eligible;

	private String appOwnerType;
	private String appOwnerApproveType;
	private String appOwnerMinimum;
	private MemberList appOwnerList;

	private String custodianType;
	private String custodianApproveType;
	private String custodianMinimum;
	private MemberList custodianList;
	private MemberList custodianACCList;
	private MemberList custodianBackOfficeList;
	private MemberList custodianBranchList;
	private MemberList custodianOutletList;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public String getAppOwnerMinimum() {
		return appOwnerMinimum;
	}

	public void setAppOwnerMinimum(String appOwnerMinimum) {
		this.appOwnerMinimum = appOwnerMinimum;
	}

	public String getCustodianMinimum() {
		return custodianMinimum;
	}

	public void setCustodianMinimum(String custodianMinimum) {
		this.custodianMinimum = custodianMinimum;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(String appInfo) {
		this.appInfo = appInfo;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String[] getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String[] authentication) {
		this.authentication = authentication;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ApplicationRole> getRoleApplication() {
		return roleApplication;
	}

	public void setRoleApplication(List<ApplicationRole> roleApplication) {
		this.roleApplication = roleApplication;
	}

	public List<Eligible> getEligible() {
		return eligible;
	}

	public void setEligible(List<Eligible> eligible) {
		this.eligible = eligible;
	}

	public String getAppOwnerType() {
		return appOwnerType;
	}

	public void setAppOwnerType(String appOwnerType) {
		this.appOwnerType = appOwnerType;
	}

	public String getAppOwnerApproveType() {
		return appOwnerApproveType;
	}

	public void setAppOwnerApproveType(String appOwnerApproveType) {
		this.appOwnerApproveType = appOwnerApproveType;
	}

	public MemberList getAppOwnerList() {
		return appOwnerList;
	}

	public void setAppOwnerList(MemberList appOwnerList) {
		this.appOwnerList = appOwnerList;
	}

	public String getCustodianType() {
		return custodianType;
	}

	public void setCustodianType(String custodianType) {
		this.custodianType = custodianType;
	}

	public String getCustodianApproveType() {
		return custodianApproveType;
	}

	public void setCustodianApproveType(String custodianApproveType) {
		this.custodianApproveType = custodianApproveType;
	}

	public MemberList getCustodianList() {
		return custodianList;
	}

	public void setCustodianList(MemberList custodianList) {
		this.custodianList = custodianList;
	}

	public MemberList getCustodianACCList() {
		return custodianACCList;
	}

	public void setCustodianACCList(MemberList custodianACCList) {
		this.custodianACCList = custodianACCList;
	}

	public MemberList getCustodianBackOfficeList() {
		return custodianBackOfficeList;
	}

	public void setCustodianBackOfficeList(MemberList custodianBackOfficeList) {
		this.custodianBackOfficeList = custodianBackOfficeList;
	}

	public MemberList getCustodianBranchList() {
		return custodianBranchList;
	}

	public void setCustodianBranchList(MemberList custodianBranchList) {
		this.custodianBranchList = custodianBranchList;
	}

	public MemberList getCustodianOutletList() {
		return custodianOutletList;
	}

	public void setCustodianOutletList(MemberList custodianOutletList) {
		this.custodianOutletList = custodianOutletList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApplicationForm [appId=");
		builder.append(appId);
		builder.append(", appName=");
		builder.append(appName);
		builder.append(", appInfo=");
		builder.append(appInfo);
		builder.append(", applicationType=");
		builder.append(applicationType);
		builder.append(", authentication=");
		builder.append(Arrays.toString(authentication));
		builder.append(", status=");
		builder.append(status);
		builder.append(", roleApplication=");
		builder.append(roleApplication);
		builder.append(", eligible=");
		builder.append(eligible);
		builder.append(", appOwnerType=");
		builder.append(appOwnerType);
		builder.append(", appOwnerApproveType=");
		builder.append(appOwnerApproveType);
		builder.append(", appOwnerMinimum=");
		builder.append(appOwnerMinimum);
		builder.append(", appOwnerList=");
		builder.append(appOwnerList);
		builder.append(", custodianType=");
		builder.append(custodianType);
		builder.append(", custodianApproveType=");
		builder.append(custodianApproveType);
		builder.append(", custodianMinimum=");
		builder.append(custodianMinimum);
		builder.append(", custodianList=");
		builder.append(custodianList);
		builder.append(", custodianACCList=");
		builder.append(custodianACCList);
		builder.append(", custodianBackOfficeList=");
		builder.append(custodianBackOfficeList);
		builder.append(", custodianBranchList=");
		builder.append(custodianBranchList);
		builder.append(", custodianOutletList=");
		builder.append(custodianOutletList);
		builder.append("]");
		return builder.toString();
	}

}

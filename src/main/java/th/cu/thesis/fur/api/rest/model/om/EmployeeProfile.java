package th.cu.thesis.fur.api.rest.model.om;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Table")
public class EmployeeProfile {
	@XStreamAlias("PIN")
	private String pin;
	@XStreamAlias("USERID")
	private String userName;
	@XStreamAlias("THFIRSTNAME")
	private String thFirstName;
	@XStreamAlias("THLASTNAME")
	private String thLastName;
	@XStreamAlias("ENFIRSTNAME")
	private String enFirstName;
	@XStreamAlias("ENLASTNAME")
	private String enLastName;
	@XStreamAlias("EMAIL")
	private String email;
	@XStreamAlias("EMPLOYEETYPE")
	private String empType;
	@XStreamAlias("EMPLOYEEGROUP")
	private String empGroup;
	@XStreamAlias("POSITIONCODE")
	private String posCode;
	@XStreamAlias("POSITIONDESC")
	private String posDesc;
	@XStreamAlias("ORGID")
	private String orgCode;
	@XStreamAlias("ORGNAME")
	private String orgName;
	@XStreamAlias("ORGDESC")
	private String orgDesc;
	@XStreamAlias("COMPANYCODE")
	private String compCode;
	@XStreamAlias("COMPANYNAME")
	private String compName;
	
	
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getThFirstName() {
		return thFirstName;
	}
	public void setThFirstName(String thFirstName) {
		this.thFirstName = thFirstName;
	}
	public String getThLastName() {
		return thLastName;
	}
	public void setThLastName(String thLastName) {
		this.thLastName = thLastName;
	}
	public String getEnFirstName() {
		return enFirstName;
	}
	public void setEnFirstName(String enFirstName) {
		this.enFirstName = enFirstName;
	}
	public String getEnLastName() {
		return enLastName;
	}
	public void setEnLastName(String enLastName) {
		this.enLastName = enLastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	public String getEmpGroup() {
		return empGroup;
	}
	public void setEmpGroup(String empGroup) {
		this.empGroup = empGroup;
	}
	public String getPosCode() {
		return posCode;
	}
	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}
	public String getPosDesc() {
		return posDesc;
	}
	public void setPosDesc(String posDesc) {
		this.posDesc = posDesc;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgDesc() {
		return orgDesc;
	}
	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}
	public String getCompCode() {
		return compCode;
	}
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("EmployeeProfile [pin=");
		strBuilder.append(pin);
		strBuilder.append(", userName=");
		strBuilder.append(userName);
		strBuilder.append(", email=");
		strBuilder.append(email);
		strBuilder.append(", thFirstName=");
		strBuilder.append(thFirstName);
		strBuilder.append(", thLastName=");
		strBuilder.append(thLastName);
		strBuilder.append(", enFirstName=");
		strBuilder.append(enFirstName);
		strBuilder.append(", enLastName=");
		strBuilder.append(enLastName);
		strBuilder.append(", empType=");
		strBuilder.append(empType);
		strBuilder.append(", empGroup=");
		strBuilder.append(empGroup);
		strBuilder.append(", posCode=");
		strBuilder.append(posCode);
		strBuilder.append(", posDesc=");
		strBuilder.append(posDesc);
		strBuilder.append(", orgCode=");
		strBuilder.append(orgCode);
		strBuilder.append(", orgName=");
		strBuilder.append(orgName);
		strBuilder.append(", orgDesc=");
		strBuilder.append(orgDesc);
		strBuilder.append(", compCode=");
		strBuilder.append(compCode);
		strBuilder.append(", compName=");
		strBuilder.append(compName);
		strBuilder.append("]");		
		
		return strBuilder.toString();
	}
}


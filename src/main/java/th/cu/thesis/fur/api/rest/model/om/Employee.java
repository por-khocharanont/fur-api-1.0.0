package th.cu.thesis.fur.api.rest.model.om;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Table")
public class Employee {

	@XStreamAlias("PIN")
	private String pin;
	@XStreamAlias("USERNAME")
	private String userName;
	@XStreamAlias("EMAIL")
	private String email;
	@XStreamAlias("ENNAME")
	private String enName;
	@XStreamAlias("ENSURNAME")
	private String enSurName;
	@XStreamAlias("ENFULLNAME")
	private String enFullName;
	@XStreamAlias("THNAME")
	private String thName;
	@XStreamAlias("THSURNAME")
	private String thSurName;
	@XStreamAlias("THFULLNAME")
	private String thFullName;
	@XStreamAlias("POSITIONID")
	private String positionId;
	@XStreamAlias("POSITION")
	private String position;
	@XStreamAlias("ORGCODE")
	private String orgCode;
	@XStreamAlias("ORGNAME")
	private String orgName;
	@XStreamAlias("ORGDESC")
	private String orgDesc;
	@XStreamAlias("ORGLEVEL")
	private String orgLevel;
	@XStreamAlias("FCCODE")
	private String fcCode;
	@XStreamAlias("FCNAME")
	private String fcName;
	@XStreamAlias("FCDESC")
	private String fcDesc;
	@XStreamAlias("FCHCODE")
	private String fchCode;
	@XStreamAlias("FCHNAME")
	private String fchName;
	@XStreamAlias("FCHDESC")
	private String fchDesc;
	@XStreamAlias("SCCODE")
	private String scCode;
	@XStreamAlias("SCNAME")
	private String scName;
	@XStreamAlias("SCDESC")
	private String scDesc;
	@XStreamAlias("SCHCODE")
	private String schCode;
	@XStreamAlias("SCHNAME")
	private String schName;	
	@XStreamAlias("SCHDESC")
	private String schDesc;
	@XStreamAlias("DPCODE")
	private String dpCode;
	@XStreamAlias("DPNAME")
	private String dpName;
	@XStreamAlias("DPDESC")
	private String dpDesc;
	@XStreamAlias("DPHCODE")
	private String dphCode;
	@XStreamAlias("DPHNAME")
	private String dphName;
	@XStreamAlias("DPHDESC")
	private String dphDesc;
	@XStreamAlias("BUCODE")
	private String buCode;	
	@XStreamAlias("BUNAME")
	private String buName;
	@XStreamAlias("BUDESC")
	private String buDesc;
	@XStreamAlias("BUHCODE")
	private String buhCode;
	@XStreamAlias("BUHNAME")
	private String buhName;
	@XStreamAlias("BUHDESC")
	private String buhDesc;
	@XStreamAlias("COCODE")
	private String coCode;	
	@XStreamAlias("CONAME")
	private String coName;
	@XStreamAlias("PHONE")
	private String phone;
	@XStreamAlias("MOBILE")
	private String mobile;
	@XStreamAlias("MANAGER_PIN")
	private String managerPin;
	@XStreamAlias("MANAGER_USERNAME")
	private String managerUserName;
	@XStreamAlias("MANAGER_EMAIL")
	private String managerEmail;	
	@XStreamAlias("MANAGER_ENNAME")
	private String managerEnName;
	@XStreamAlias("MANAGER_ENSURNAME")
	private String managerEnSurName;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getEnFullName() {
		return enFullName;
	}
	public void setEnFullName(String enFullName) {
		this.enFullName = enFullName;
	}
	public String getThName() {
		return thName;
	}
	public void setThName(String thName) {
		this.thName = thName;
	}
	public String getThSurName() {
		return thSurName;
	}
	public void setThSurName(String thSurName) {
		this.thSurName = thSurName;
	}
	public String getThFullName() {
		return thFullName;
	}
	public void setThFullName(String thFullName) {
		this.thFullName = thFullName;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	public String getFcCode() {
		return fcCode;
	}
	public void setFcCode(String fcCode) {
		this.fcCode = fcCode;
	}
	public String getFcName() {
		return fcName;
	}
	public void setFcName(String fcName) {
		this.fcName = fcName;
	}
	public String getFcDesc() {
		return fcDesc;
	}
	public void setFcDesc(String fcDesc) {
		this.fcDesc = fcDesc;
	}
	public String getFchCode() {
		return fchCode;
	}
	public void setFchCode(String fchCode) {
		this.fchCode = fchCode;
	}
	public String getFchName() {
		return fchName;
	}
	public void setFchName(String fchName) {
		this.fchName = fchName;
	}
	public String getFchDesc() {
		return fchDesc;
	}
	public void setFchDesc(String fchDesc) {
		this.fchDesc = fchDesc;
	}
	public String getScCode() {
		return scCode;
	}
	public void setScCode(String scCode) {
		this.scCode = scCode;
	}
	public String getScName() {
		return scName;
	}
	public void setScName(String scName) {
		this.scName = scName;
	}
	public String getScDesc() {
		return scDesc;
	}
	public void setScDesc(String scDesc) {
		this.scDesc = scDesc;
	}
	public String getSchCode() {
		return schCode;
	}
	public void setSchCode(String schCode) {
		this.schCode = schCode;
	}
	public String getSchName() {
		return schName;
	}
	public void setSchName(String schName) {
		this.schName = schName;
	}
	public String getSchDesc() {
		return schDesc;
	}
	public void setSchDesc(String schDesc) {
		this.schDesc = schDesc;
	}
	public String getDpCode() {
		return dpCode;
	}
	public void setDpCode(String dpCode) {
		this.dpCode = dpCode;
	}
	public String getDpName() {
		return dpName;
	}
	public void setDpName(String dpName) {
		this.dpName = dpName;
	}
	public String getDpDesc() {
		return dpDesc;
	}
	public void setDpDesc(String dpDesc) {
		this.dpDesc = dpDesc;
	}
	public String getDphCode() {
		return dphCode;
	}
	public void setDphCode(String dphCode) {
		this.dphCode = dphCode;
	}
	public String getDphName() {
		return dphName;
	}
	public void setDphName(String dphName) {
		this.dphName = dphName;
	}
	public String getDphDesc() {
		return dphDesc;
	}
	public void setDphDesc(String dphDesc) {
		this.dphDesc = dphDesc;
	}
	public String getBuCode() {
		return buCode;
	}
	public void setBuCode(String buCode) {
		this.buCode = buCode;
	}
	public String getBuName() {
		return buName;
	}
	public void setBuName(String buName) {
		this.buName = buName;
	}
	public String getBuDesc() {
		return buDesc;
	}
	public void setBuDesc(String buDesc) {
		this.buDesc = buDesc;
	}
	public String getBuhCode() {
		return buhCode;
	}
	public void setBuhCode(String buhCode) {
		this.buhCode = buhCode;
	}
	public String getBuhName() {
		return buhName;
	}
	public void setBuhName(String buhName) {
		this.buhName = buhName;
	}
	public String getBuhDesc() {
		return buhDesc;
	}
	public void setBuhDesc(String buhDesc) {
		this.buhDesc = buhDesc;
	}
	public String getCoCode() {
		return coCode;
	}
	public void setCoCode(String coCode) {
		this.coCode = coCode;
	}
	public String getCoName() {
		return coName;
	}
	public void setCoName(String coName) {
		this.coName = coName;
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
	public String getManagerPin() {
		return managerPin;
	}
	public void setManagerPin(String managerPin) {
		this.managerPin = managerPin;
	}
	public String getManagerUserName() {
		return managerUserName;
	}
	public void setManagerUserName(String managerUserName) {
		this.managerUserName = managerUserName;
	}
	public String getManagerEmail() {
		return managerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	public String getManagerEnName() {
		return managerEnName;
	}
	public void setManagerEnName(String managerEnName) {
		this.managerEnName = managerEnName;
	}
	public String getManagerEnSurName() {
		return managerEnSurName;
	}
	public void setManagerEnSurName(String managerEnSurName) {
		this.managerEnSurName = managerEnSurName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Employee [pin=");
		builder.append(pin);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", enName=");
		builder.append(enName);
		builder.append(", enSurName=");
		builder.append(enSurName);
		builder.append(", enFullName=");
		builder.append(enFullName);
		builder.append(", thName=");
		builder.append(thName);
		builder.append(", thSurName=");
		builder.append(thSurName);
		builder.append(", thFullName=");
		builder.append(thFullName);
		builder.append(", positionId=");
		builder.append(positionId);
		builder.append(", position=");
		builder.append(position);
		builder.append(", orgCode=");
		builder.append(orgCode);
		builder.append(", orgName=");
		builder.append(orgName);
		builder.append(", orgDesc=");
		builder.append(orgDesc);
		builder.append(", orgLevel=");
		builder.append(orgLevel);
		builder.append(", fcCode=");
		builder.append(fcCode);
		builder.append(", fcName=");
		builder.append(fcName);
		builder.append(", fcDesc=");
		builder.append(fcDesc);
		builder.append(", fchCode=");
		builder.append(fchCode);
		builder.append(", fchName=");
		builder.append(fchName);
		builder.append(", fchDesc=");
		builder.append(fchDesc);
		builder.append(", scCode=");
		builder.append(scCode);
		builder.append(", scName=");
		builder.append(scName);
		builder.append(", scDesc=");
		builder.append(scDesc);
		builder.append(", schCode=");
		builder.append(schCode);
		builder.append(", schName=");
		builder.append(schName);
		builder.append(", schDesc=");
		builder.append(schDesc);
		builder.append(", dpCode=");
		builder.append(dpCode);
		builder.append(", dpName=");
		builder.append(dpName);
		builder.append(", dpDesc=");
		builder.append(dpDesc);
		builder.append(", dphCode=");
		builder.append(dphCode);
		builder.append(", dphName=");
		builder.append(dphName);
		builder.append(", dphDesc=");
		builder.append(dphDesc);
		builder.append(", buCode=");
		builder.append(buCode);
		builder.append(", buName=");
		builder.append(buName);
		builder.append(", buDesc=");
		builder.append(buDesc);
		builder.append(", buhCode=");
		builder.append(buhCode);
		builder.append(", buhName=");
		builder.append(buhName);
		builder.append(", buhDesc=");
		builder.append(buhDesc);
		builder.append(", coCode=");
		builder.append(coCode);
		builder.append(", coName=");
		builder.append(coName);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", managerPin=");
		builder.append(managerPin);
		builder.append(", managerUserName=");
		builder.append(managerUserName);
		builder.append(", managerEmail=");
		builder.append(managerEmail);
		builder.append(", managerEnName=");
		builder.append(managerEnName);
		builder.append(", managerEnSurName=");
		builder.append(managerEnSurName);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

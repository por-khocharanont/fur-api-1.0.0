package th.cu.thesis.fur.api.rest.model.om;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Table")
public class UserReOrg {

	@XStreamAlias("PIN")
	private String pin;
	@XStreamAlias("OLDORGCODE")
	private String oldOrgCode;
	@XStreamAlias("NEWORGCODE")
	private String newOrgCode;
	@XStreamAlias("USERNAME")
	private String username;
	@XStreamAlias("EMAIL")
	private String email;
	@XStreamAlias("ENNAME")
	private String enname;
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
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getOldOrgCode() {
		return oldOrgCode;
	}
	public void setOldOrgCode(String oldOrgCode) {
		this.oldOrgCode = oldOrgCode;
	}
	public String getNewOrgCode() {
		return newOrgCode;
	}
	public void setNewOrgCode(String newOrgCode) {
		this.newOrgCode = newOrgCode;
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
	public String getEnname() {
		return enname;
	}
	public void setEnname(String enname) {
		this.enname = enname;
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
	
	
	
	
	
	
	
}

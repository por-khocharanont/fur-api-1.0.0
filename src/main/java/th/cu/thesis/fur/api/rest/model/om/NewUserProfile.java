package th.cu.thesis.fur.api.rest.model.om;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Table")
public class NewUserProfile {
	
	@XStreamAlias("PIN")
	private String pin;
	
	@XStreamAlias("USERNAME")
	private String username;
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
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NewUserProfile [pin=");
		builder.append(pin);
		builder.append(", username=");
		builder.append(username);
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
		builder.append("]");
		return builder.toString();
	}


}

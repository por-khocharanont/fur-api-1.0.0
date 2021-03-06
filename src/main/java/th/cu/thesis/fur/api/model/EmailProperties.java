package th.cu.thesis.fur.api.model;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailProperties implements Serializable {

	private static final long serialVersionUID = 4289683796786032161L;
	
	@Value("${host.email}")
	private String host;
	
	@Value("${port.email}")
	private String port;
	
	@Value("${protocol.email}")
	private String protocol;
	
	@Value("${username.email}")
	private String username;
	
	@Value("${password.email}")
	private String password;
	
	@Value("${emailAddress.email}")
	private String emailAddress;
	
	@Value("${transport.protocol.email}")
	private String transportProtocol;
	
	@Value("${smtp.auth.email}")
	private String smtpAuth;
	
	@Value("${smtp.starttls.enable.email}")
	private String smtpStarttls;
	
	@Value("${smtp.ehlo.email}")
	private String smtpEhlo;
	
	@Value("${debug.email}")
	private String debug;
	
	@Value("${fur.domain}")
	private String domainFur;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getTransportProtocol() {
		return transportProtocol;
	}

	public void setTransportProtocol(String transportProtocol) {
		this.transportProtocol = transportProtocol;
	}

	public String getSmtpAuth() {
		return smtpAuth;
	}

	public void setSmtpAuth(String smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	public String getSmtpStarttls() {
		return smtpStarttls;
	}

	public void setSmtpStarttls(String smtpStarttls) {
		this.smtpStarttls = smtpStarttls;
	}

	public String getSmtpEhlo() {
		return smtpEhlo;
	}

	public void setSmtpEhlo(String smtpEhlo) {
		this.smtpEhlo = smtpEhlo;
	}

	public String getDebug() {
		return debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}

	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmailProperties [host=");
		builder.append(host);
		builder.append(", port=");
		builder.append(port);
		builder.append(", protocol=");
		builder.append(protocol);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", emailAddress=");
		builder.append(emailAddress);
		builder.append(", transportProtocol=");
		builder.append(transportProtocol);
		builder.append(", smtpAuth=");
		builder.append(smtpAuth);
		builder.append(", smtpStarttls=");
		builder.append(smtpStarttls);
		builder.append(", smtpEhlo=");
		builder.append(smtpEhlo);
		builder.append(", debug=");
		builder.append(debug);
		builder.append(", domainFur=");
		builder.append(domainFur);
		builder.append("]");
		return builder.toString();
	}

	public String getDomainFur() {
		return domainFur;
	}

	public void setDomainFur(String domainFur) {
		this.domainFur = domainFur;
	}	

}

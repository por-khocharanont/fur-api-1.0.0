package th.cu.thesis.fur.api.service.exception;

import java.util.ResourceBundle;

import org.springframework.http.HttpStatus;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	int httpStatus = HttpStatus.OK.value();
	MessageInfo messageInfo;
	
	private static ResourceBundle messageResource = ResourceBundle.getBundle("message/message");
	
	public ServiceException(int httpStatus, String code, String message) {
		super();
		this.httpStatus = httpStatus;
		this.messageInfo = new MessageInfo(code, message);
	}
	
	public ServiceException(int httpStatus, String code, String message, Throwable cause) {
		super("httpStatus["+ httpStatus +"]"+ code +":"+ message, cause);
		this.httpStatus = httpStatus;
		this.messageInfo = new MessageInfo(code, message);
	}
	
	public int getHttpStatus() {
		return httpStatus;
	}

	public MessageInfo getMessageInfo() {
		return messageInfo;
	}

	public void setMessageService(MessageInfo messageInfo) {
		this.messageInfo = messageInfo;
	}
	
	public static ServiceException get400BadRequest(String code, String message) {
		return new ServiceException(HttpStatus.BAD_REQUEST.value(), code, message);
	}
	
	public static ServiceException get401Unauthorized(String code) {
		String message = messageResource.getString(code);
		return new ServiceException(HttpStatus.UNAUTHORIZED.value(), code, message);
	}

	public static ServiceException get401Unauthorized() {
		return new ServiceException(HttpStatus.UNAUTHORIZED.value(), "401000", "Unauthorized");
	}
	
	public static ServiceException get404FileNotFound(String code) {
		String message = messageResource.getString(code);
		return new ServiceException(HttpStatus.NOT_FOUND.value(), code, message);
	}
	
	public static ServiceException get404DataNotFound(String code, String message) {
		return new ServiceException(HttpStatus.NOT_FOUND.value(), code, message);
	}
	
	public static ServiceException get405MethodNotAllowed() {
		return new ServiceException(HttpStatus.METHOD_NOT_ALLOWED.value(), "405000", "Method Not Allowed");
	}
	
	public static ServiceException get401Unauthorized(Throwable e) {
		return new ServiceException(HttpStatus.UNAUTHORIZED.value(), "401000", "Unauthorized", e);
	}
	
	public static ServiceException get500DBError(Throwable e) {
		return new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "500001", "DB Error", e);
	}

	public static ServiceException get500SystemError(Throwable e) {
		return new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "500000", "SystemError", e);
	}
	
	public static ServiceException get500SystemError() {
		return new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "500000", "SystemError");
	}
	
}

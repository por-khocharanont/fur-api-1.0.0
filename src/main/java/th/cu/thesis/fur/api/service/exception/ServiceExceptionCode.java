package th.cu.thesis.fur.api.service.exception;

public enum ServiceExceptionCode {
	STATUS_OK("200", "OK", "20000", "SUCCESS"),
	STAUTUS_UNKNOWN_SYNTAX("400", "Bad Request", "40000", "Unknown Syntax"),
	STATUS_MISSING("400", "Bad Request", "40001", "Missing parameter"),
	STATUS_INVALID("400", "Bad Request", "40002", "Invalid parameter"),
	STATUS_DATA_EXISTED("400", "Bad Request", "40003", "Data Existed"),
	STATUS_ACCESS_DENIED("403", "Forbidden", "40300", "Access denied"),
	STATUS_URL_NOT_FOUND("404", "Not Found", "40400", "Url not found"),
	STATUS_DATA_NOT_FOUND("404", "Not Found", "40401", "Data not found"),
	STATUS_METHOD_NOT_ALLOWED("405", "Method Not Allowed", "40500", "Method Not Allowed"),
	STATUS_SYSTEM_ERROR("500", "Internal Server Error", "50000", "System Error"),
	STATUS_UNKNOWN_ERROR("500", "Internal Server Error", "50001", "Unknow Error"),
	STATUS_DB_ERROR("500", "Internal Server Error", "50002", "DB Error"),
	STATUS_CONNECTION_TIMEOUT("500", "Internal Server Error", "50003", "Connection Timeout"),
	STATUS_SYSTEM_BUSY("500", "Internal Server Error", "50004", "System Busy")
	;

	private final String httpCode;
	private final String httpStatusMsg;
	private final String resultCode;
	private final String resultDescription;	
	
	private ServiceExceptionCode(String httpCode, String httpStatusMsg,
			String resultCode, String resultDescription) {
		this.httpCode = httpCode;
		this.httpStatusMsg = httpStatusMsg;
		this.resultCode = resultCode;
		this.resultDescription = resultDescription;
	}

}

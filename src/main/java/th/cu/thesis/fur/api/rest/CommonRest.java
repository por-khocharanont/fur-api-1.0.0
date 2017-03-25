package th.cu.thesis.fur.api.rest;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import th.cu.thesis.fur.api.service.exception.MessageInfo;
import th.cu.thesis.fur.api.service.exception.ServiceException;

@ControllerAdvice
public class CommonRest {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonRest.class);

	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public MessageInfo HandlerServiceException(ServiceException e, HttpServletResponse response) {
		logger.error("{}", e.getMessageInfo());
		if(e.getHttpStatus() == 500) {
			logger.equals(e);
		}
		
		if(e.getMessageInfo().getCode() == "50001") {
			String msg = e.getCause().getMessage();
			logger.equals(e);
		}
		
		response.setStatus(e.getHttpStatus());
		
		return e.getMessageInfo();
	}
}

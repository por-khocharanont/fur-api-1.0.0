package th.cu.thesis.fur.api.rest;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import th.cu.thesis.fur.api.service.exception.MessageInfo;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.DateEditor;

public class AbstractRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractRestController.class);
	
//	 @InitBinder
//     public void binder(WebDataBinder binder) {
//       binder.registerCustomEditor(Date.class, new DateEditor());
//     }
	
	@ExceptionHandler(ServiceException.class)
	public MessageInfo handlerServiceException(ServiceException e, HttpServletResponse response){
		logDetailServiceException(e);
		response.setStatus(e.getHttpStatus());
		return e.getMessageInfo();
	}
	
	private void logDetailServiceException(ServiceException e) {
		MessageInfo messageInfo = e.getMessageInfo();
		if(e.getHttpStatus() == HttpStatus.INTERNAL_SERVER_ERROR.value() && messageInfo != null) {
			// if 500 printStackTrace
			logger.error(e.getMessage(), e);
		} else {
			logger.warn(e.getMessage());
		}
		
		logger.debug(e.getMessage(), e);
	}
	

}

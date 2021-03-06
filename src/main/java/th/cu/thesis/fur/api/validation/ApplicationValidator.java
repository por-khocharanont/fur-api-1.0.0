package th.cu.thesis.fur.api.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.cu.thesis.fur.api.rest.model.ApplicationForm;
import th.cu.thesis.fur.api.rest.model.ApplicationRequest;
import th.cu.thesis.fur.api.service.exception.ServiceException;

public class ApplicationValidator implements Validator<ApplicationRequest> {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationValidator.class);

	@Override
	public void validate(ApplicationRequest param) throws ServiceException {

		ApplicationForm app = param.getApplicationForm();
		
		if ("".equals(app.getAppName())) {
			logger.debug("Invalid parameter appName");
			throw ServiceException.get400BadRequest("40001", "Missing parameter");

		}

		if (app.getAppName() == null) {
			logger.debug("Missing parameter appName");
			throw ServiceException.get400BadRequest("40002", "Invalid parameter");
		}

		if (app.getAuthentication().length == 0) {
			logger.debug("Missing parameter authentication");
			throw ServiceException.get400BadRequest("40001", "Missing parameter");
		}
		
		if (app.getRoleApplication().size() == 0) {
			logger.debug("Missing parameter role application");
			throw ServiceException.get400BadRequest("40001", "Missing parameter");
		}
		
		if (app.getEligible().size() == 0) {
			logger.debug("Missing parameter eligible");
			throw ServiceException.get400BadRequest("40001", "Missing parameter");
		}
		
		if ("".equals(app.getAppOwnerType())) {
			logger.debug("Invalid parameter appOwnerType");
			throw ServiceException.get400BadRequest("40001", "Missing parameter");
		}
		
		if (app.getAppOwnerType() == null) {
			logger.debug("Missing parameter appOwnerType");
			throw ServiceException.get400BadRequest("40002", "Invalid parameter");
		}
		
		if ("".equals(app.getAppOwnerApproveType())) {
			logger.debug("Invalid parameter appOwnerApproveType");
			throw ServiceException.get400BadRequest("40001", "Missing parameter");
		}
		
		if (app.getAppOwnerApproveType() == null) {
			logger.debug("Missing parameter appOwnerApproveType");
			throw ServiceException.get400BadRequest("40002", "Invalid parameter");
		}
		
		//TODO MORE VALIDATE

	}

}

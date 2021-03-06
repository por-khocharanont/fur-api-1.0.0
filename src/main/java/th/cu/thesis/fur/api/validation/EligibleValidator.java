package th.cu.thesis.fur.api.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.cu.thesis.fur.api.rest.model.EligibleRequest;
import th.cu.thesis.fur.api.service.exception.ServiceException;

public class EligibleValidator implements Validator<EligibleRequest>{

	private static final Logger logger = LoggerFactory.getLogger(EligibleValidator.class);
			
	@Override
	public void validate(EligibleRequest param) throws ServiceException {
		
		if ("".equals(param.getAppRoleId())) {
			logger.debug("Invalid parameter roleId");
			throw ServiceException.get400BadRequest("40001", "Missing parameter");

		}

		if (param.getAppRoleId() == null) {
			logger.debug("Missing parameter roleId");
			throw ServiceException.get400BadRequest("40002", "Invalid parameter");
		}
		
		if ("".equals(param.getOrgcode())) {
			logger.debug("Invalid parameter orgCode");
			throw ServiceException.get400BadRequest("40001", "Missing parameter");

		}

		if (param.getOrgcode() == null) {
			logger.debug("Missing parameter orgCode");
			throw ServiceException.get400BadRequest("40002", "Invalid parameter");
		}
		
		if ("".equals(param.getCreatedBy())) {
			logger.debug("Invalid parameter createdBy");
			throw ServiceException.get400BadRequest("40001", "Missing parameter");

		}

		if (param.getCreatedBy() == null) {
			logger.debug("Missing parameter createdBy");
			throw ServiceException.get400BadRequest("40002", "Invalid parameter");
		}
	}

	

}

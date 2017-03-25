package th.cu.thesis.fur.api.service;

import th.cu.thesis.fur.api.rest.model.Batch;
import th.cu.thesis.fur.api.service.exception.ServiceException;

public interface BatchPeriodExpiredService {
	
	Batch periodExpired() throws ServiceException;

}

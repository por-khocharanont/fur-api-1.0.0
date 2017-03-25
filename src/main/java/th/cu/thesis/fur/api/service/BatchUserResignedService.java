package th.cu.thesis.fur.api.service;

import java.util.Date;

import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.rest.model.Batch;
import th.cu.thesis.fur.api.service.exception.ServiceException;

public interface BatchUserResignedService {
	
	
	public Batch updateUserResigned(String date) throws ServiceException;

	public void terminateAllApplication(User user, Date date) throws ServiceException;
	

}

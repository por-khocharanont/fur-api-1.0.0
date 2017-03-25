package th.cu.thesis.fur.api.validation;

import th.cu.thesis.fur.api.service.exception.ServiceException;

public interface Validator<T> {
	
	public void validate(T t) throws ServiceException;

}

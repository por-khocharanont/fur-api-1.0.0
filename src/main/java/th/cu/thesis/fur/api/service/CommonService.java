package th.cu.thesis.fur.api.service;

import java.util.List;

import th.cu.thesis.fur.api.model.RolePermission;
import th.cu.thesis.fur.api.model.UserMenu;
import th.cu.thesis.fur.api.service.exception.ServiceException;

public interface CommonService {
	
	public String getMessage(String code) throws ServiceException;
	
	public UserMenu getUserMenuByUserId(String userId) throws ServiceException;
	
	public UserMenu getUserMenuByUsername(String username) throws ServiceException;
	
	public List<RolePermission> getRoleByUserId(String userId) throws ServiceException;

}

package th.cu.thesis.fur.api.service;

import java.util.List;

import th.cu.thesis.fur.api.model.AuthUser;
import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.rest.model.UserApplicationInfoGridRequest;
import th.cu.thesis.fur.api.rest.model.UserApplicationInfoGridResponse;
import th.cu.thesis.fur.api.rest.model.UserInfo;
import th.cu.thesis.fur.api.rest.model.UserTokenInfoGridResponse;
import th.cu.thesis.fur.api.service.exception.ServiceException;

public interface UserService {
	
	UserInfo authUser(AuthUser authUser) throws ServiceException;
	
	User getUserProfileByUsername(String username) throws ServiceException;
	
	User getUserProfileByUserId(String userId) throws ServiceException;
	
	List<User> getUserProfileList(String username) throws ServiceException;
	
	UserApplicationInfoGridResponse getApplicationByType(String type,UserApplicationInfoGridRequest request) throws ServiceException;
	
	UserTokenInfoGridResponse getApplicationByUserToken(UserApplicationInfoGridRequest request) throws ServiceException;
	
}

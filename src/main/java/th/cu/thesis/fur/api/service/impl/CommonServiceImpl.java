package th.cu.thesis.fur.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import th.cu.thesis.fur.api.filter.RestControllerLoggingFilter;
import th.cu.thesis.fur.api.model.RolePermission;
import th.cu.thesis.fur.api.model.UserMenu;
import th.cu.thesis.fur.api.repository.RolePermissionRepository;
import th.cu.thesis.fur.api.service.CommonService;
import th.cu.thesis.fur.api.service.exception.ServiceException;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	
	private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Override
	public String getMessage(String code) throws ServiceException {
		
		String message = null;
		try {
			
			message = messageSource.getMessage(code, null, Locale.US);
			
		} catch (RuntimeException  e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return message;
	}

	@Override
	public UserMenu getUserMenuByUserId(String userId) throws ServiceException {
		
		UserMenu userMenu = new UserMenu();
		try {
			
			Map<String , RolePermission> userMenuMap =  new HashMap<String, RolePermission>();
			
			//permission for admin
			logger.info(RestControllerLoggingFilter.DATABASE, "getRolePermissionByUserId","userId="+userId);
			List<RolePermission> rolePermissionList = rolePermissionRepository.getRolePermissionByUserId(userId);
			
			
			//permission for user
			if(rolePermissionList == null || rolePermissionList.size() == 0){
				
				logger.info(RestControllerLoggingFilter.DATABASE, "getRolePermissionUser","");
				rolePermissionList = rolePermissionRepository.getRolePermissionUser();
			}
			
			for(RolePermission permission : rolePermissionList){
				
				userMenuMap.put(permission.getMenuCode(), permission);
				
			}
			
			userMenu.setUserMenu(userMenuMap);
			
			
		} catch (RuntimeException e) {
			
			throw ServiceException.get500DBError(e);
		}
		return userMenu;
	}

	@Override
	public UserMenu getUserMenuByUsername(String username) throws ServiceException {
		UserMenu userMenu = new UserMenu();
		try {
			
			Map<String , RolePermission> userMenuMap =  new HashMap<String, RolePermission>();
			
			//permission for admin
			logger.info(RestControllerLoggingFilter.DATABASE, "getRolePermissionByUsername","username="+username);
			List<RolePermission> rolePermissionList = rolePermissionRepository.getRolePermissionByUsername(username);
			
			
			//permission for user
			if(rolePermissionList == null || rolePermissionList.size() == 0){
				
				logger.info(RestControllerLoggingFilter.DATABASE, "getRolePermissionUser","");
				rolePermissionList = rolePermissionRepository.getRolePermissionUser();
			}
			
			for(RolePermission permission : rolePermissionList){
				
				userMenuMap.put(permission.getMenuCode(), permission);
				
			}
			
			userMenu.setUserMenu(userMenuMap);
			
			
		} catch (RuntimeException e) {
			
			throw ServiceException.get500DBError(e);
		}
		
		return userMenu;
		
	}

	@Override
	public List<RolePermission> getRoleByUserId(String userId) throws ServiceException {
		try {
			
			Map<String , RolePermission> userMenuMap =  new HashMap<String, RolePermission>();
			
			//permission for admin
			logger.info(RestControllerLoggingFilter.DATABASE, "getRolePermissionByUsername","userId="+userId);
			List<RolePermission> rolePermissionList = rolePermissionRepository.getRolePermissionByUserId(userId);
			
			//permission for user
			if(rolePermissionList == null || rolePermissionList.size() == 0){
				return null;
			}
			return rolePermissionList;
			
		} catch (RuntimeException e) {
			
			throw ServiceException.get500DBError(e);
		}
	}

}

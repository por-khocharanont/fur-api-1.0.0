package th.cu.thesis.fur.api.service;

import java.util.List;

import th.cu.thesis.fur.api.model.Application;
import th.cu.thesis.fur.api.model.ApplicationAuthentication;
import th.cu.thesis.fur.api.model.ApplicationRole;
import th.cu.thesis.fur.api.rest.model.AppOwnerTableInfo;
import th.cu.thesis.fur.api.rest.model.ApplicationGridRequest;
import th.cu.thesis.fur.api.rest.model.ApplicationGridResponse;
import th.cu.thesis.fur.api.rest.model.CustodianTableInfo;
import th.cu.thesis.fur.api.rest.model.EligibleTableInfo;
import th.cu.thesis.fur.api.service.exception.MessageInfo;
import th.cu.thesis.fur.api.service.exception.ServiceException;

public interface ApplicationService {
	
	public ApplicationGridResponse getSearchApplication(ApplicationGridRequest request) throws ServiceException;
	
	public Application getApplicationById(String appId) throws ServiceException;
	
	public void insertApplication(String request,String userLogin) throws ServiceException;
	
	public void updateApplication(String request,String userLogin) throws ServiceException;
	
	public List<Application> getApplicationByAppName(String appName,String userLogin) throws ServiceException;
	
	public List<Application> checkApplicationName(String appName) throws ServiceException;
	
	public List<ApplicationRole> getApplicationRoleByAppId(String appId) throws ServiceException;
	
	public List<ApplicationAuthentication> getAuthenticationByAppId(String appId) throws ServiceException;
	
	public AppOwnerTableInfo getAppOwnerDetailByAppId(String appId) throws ServiceException;
	
	public CustodianTableInfo getCustodianDetailByAppId(String appId) throws ServiceException;
	
	public MessageInfo deleteApplicationRole(String appId) throws ServiceException;
	
	public ApplicationRole insertApplicationRole(ApplicationRole appRole,String userLogin) throws ServiceException;
	
	public void updateApplicationRole(String appRoleId,ApplicationRole appRole,String userLogin) throws ServiceException;

	public EligibleTableInfo insertEligibleApplication(EligibleTableInfo eligible, String userLogin) throws ServiceException;
	
	public EligibleTableInfo updateEligibleApplication(String appId,EligibleTableInfo eligible, String userLogin) throws ServiceException;

	public void updateAppOwner(String appId,String request,String userLogin) throws ServiceException;
	
	public void updateCustodian(String appId,String request,String userLogin) throws ServiceException;
	
}

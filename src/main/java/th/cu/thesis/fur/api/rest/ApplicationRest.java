package th.cu.thesis.fur.api.rest;

import static th.cu.thesis.fur.api.util.CommonUtil.HEADER_X_ACIM_USER;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.cu.thesis.fur.api.model.Application;
import th.cu.thesis.fur.api.model.ApplicationAuthentication;
import th.cu.thesis.fur.api.model.ApplicationRole;
import th.cu.thesis.fur.api.rest.model.AppOwnerTableInfo;
import th.cu.thesis.fur.api.rest.model.ApplicationGridRequest;
import th.cu.thesis.fur.api.rest.model.ApplicationGridResponse;
import th.cu.thesis.fur.api.rest.model.CustodianTableInfo;
import th.cu.thesis.fur.api.rest.model.EligibleTableInfo;
import th.cu.thesis.fur.api.service.ApplicationService;
import th.cu.thesis.fur.api.service.exception.MessageInfo;
import th.cu.thesis.fur.api.service.exception.ServiceException;

@RestController
@RequestMapping(value="/applications")
public class ApplicationRest extends AbstractRestController{
	
	
	
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping(value="/search",method = RequestMethod.POST)
	public @ResponseBody ApplicationGridResponse getSearhApplication(@RequestBody ApplicationGridRequest request) throws ServiceException {
		System.out.println(request.toString());
		return applicationService.getSearchApplication(request);
	}
	
	@RequestMapping(value="/authentication/{appId}",method = RequestMethod.GET)
	public @ResponseBody List<ApplicationAuthentication> getAuthenticationByAppId(@PathVariable("appId") String appId) throws ServiceException {
		return applicationService.getAuthenticationByAppId(appId);
	}
	
	@RequestMapping(value="/{appId}",method = RequestMethod.GET)
	public @ResponseBody Application getApplicationById(@PathVariable("appId") String appId) throws ServiceException {
		return applicationService.getApplicationById(appId);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody void insertApplication(@RequestBody String request,@RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		applicationService.insertApplication(request,userLogin);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody void updateApplication(@RequestBody String request,@RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		applicationService.updateApplication(request,userLogin);
	}
	
	@RequestMapping(value="/appname",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<Application> getApplicationByAppName(@RequestBody String appName,@RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		return applicationService.getApplicationByAppName(appName,userLogin);
	}
	
	@RequestMapping(value="/appname/check",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<Application> checkApplicationName(@RequestBody String appName) throws ServiceException {
		return applicationService.checkApplicationName(appName);
	}
	
	@RequestMapping(value="/approlelist/{appId}",method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public @ResponseBody List<ApplicationRole> getApplicationRoleByAppId(@PathVariable("appId") String appId) throws ServiceException {
		return applicationService.getApplicationRoleByAppId(appId);
	}
	
	@RequestMapping(value="/appowner/{appId}",method = RequestMethod.GET)
	public @ResponseBody AppOwnerTableInfo getAppOwnerDetailByAppId( @PathVariable("appId") String appId) throws ServiceException {
		return applicationService.getAppOwnerDetailByAppId(appId);
	}
	
	@RequestMapping(value="/custodian/{appId}",method = RequestMethod.GET)
	public @ResponseBody CustodianTableInfo getCustodianDetailByAppId(@PathVariable("appId") String appId) throws ServiceException {
		return applicationService.getCustodianDetailByAppId(appId);
	}
	
	@RequestMapping(value="/approle/{appRoleId}",method = RequestMethod.DELETE)
	public @ResponseBody MessageInfo deleteApplicationRole(@PathVariable("appRoleId") String appRoleId) throws ServiceException {
		return applicationService.deleteApplicationRole(appRoleId);
	}
	
	@RequestMapping(value="/approle",method = RequestMethod.POST)
	public @ResponseBody ApplicationRole insertApplicationRole(@RequestBody ApplicationRole appRole, @RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		return applicationService.insertApplicationRole(appRole,userLogin);
	}
	
	@RequestMapping(value="/approle/{appRoleId}",method = RequestMethod.PUT)
	public @ResponseBody void updateApplication(@PathVariable("appRoleId") String appRoleId,@RequestBody ApplicationRole appRole, @RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		applicationService.updateApplicationRole(appRoleId,appRole,userLogin);
	}
	
	@RequestMapping(value="/eligible",method = RequestMethod.POST)
	public @ResponseBody EligibleTableInfo insertEligibleApplication(@RequestBody EligibleTableInfo eligible, @RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		return applicationService.insertEligibleApplication(eligible,userLogin);
	}
	
	@RequestMapping(value="/eligible/{appId}",method = RequestMethod.PUT)
	public @ResponseBody EligibleTableInfo updateEligibleApplication(@PathVariable("appId") String appId,@RequestBody EligibleTableInfo eligible, @RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		return applicationService.updateEligibleApplication(appId,eligible,userLogin);
	}
	
	@RequestMapping(value="/appowner/{appId}",method = RequestMethod.PUT)
	public @ResponseBody void updateAppOwner(@PathVariable("appId") String appId,@RequestBody String request,@RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		applicationService.updateAppOwner(appId,request,userLogin);
	}
	
	@RequestMapping(value="/custodian/{appId}",method = RequestMethod.PUT)
	public @ResponseBody void updateCustodian(@PathVariable("appId") String appId,@RequestBody String request,@RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		applicationService.updateCustodian(appId,request,userLogin);
	}
	
}

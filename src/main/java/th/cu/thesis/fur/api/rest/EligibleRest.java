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

import th.cu.thesis.fur.api.model.ApplicationRole;
import th.cu.thesis.fur.api.model.Organize;
import th.cu.thesis.fur.api.repository.model.Eligible;
import th.cu.thesis.fur.api.rest.model.EligibleGridRequest;
import th.cu.thesis.fur.api.rest.model.EligibleGridResponse;
import th.cu.thesis.fur.api.rest.model.EligibleRequest;
import th.cu.thesis.fur.api.rest.model.EligibleTableInfo;
import th.cu.thesis.fur.api.service.EligibleService;
import th.cu.thesis.fur.api.service.exception.ServiceException;

@RestController
@RequestMapping("/eligible")
public class EligibleRest {
	
	@Autowired
	private EligibleService eligibleService;
	
	
	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@ResponseBody
	public EligibleGridResponse getEligibleList(@RequestBody EligibleGridRequest request, @RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		
		return eligibleService.findEligibles(request, userLogin);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public void createEligible(@RequestBody EligibleRequest request) throws ServiceException {
		
		eligibleService.createEligible(request);
		
	}
	
	@RequestMapping(value="/list", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public void createEligibleList(@RequestBody List<EligibleRequest> request) throws ServiceException {
		
		eligibleService.createEligibleList(request);
		
	}
	
	@RequestMapping(value="/{eligibleId}", method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public void deletedEligibleById(@PathVariable("eligibleId") String eligibleId) throws ServiceException {
		
		eligibleService.deleteEligibleById(eligibleId);
		
	}
	
	@RequestMapping(value="/orgcode", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@ResponseBody
	public List<Organize> getOrganizeByOrgCode(@RequestBody String orgCode) throws ServiceException {
		
		return eligibleService.findOrganizeByOrgCode(orgCode);
		
	}
	
	@RequestMapping(value="/orgname", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@ResponseBody
	public List<Organize> getOrganizeByOrgName(@RequestBody String orgName) throws ServiceException {
		
		return eligibleService.findOrganizeByOrgName(orgName);
		
	}
	
	@RequestMapping(value="/orgdesc", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@ResponseBody
	public List<Organize> getOrganizeByOrgDesc(@RequestBody String orgDesc) throws ServiceException {
		
		return eligibleService.findOrganizeByOrgDesc(orgDesc);
		
	}

	@RequestMapping(value="/approlelist",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@ResponseBody
	public List<Eligible> getEligibleByAppRoleList(@RequestBody List<ApplicationRole> appRoleList)throws ServiceException {
		
		return eligibleService.getEligibleByAppRoleList(appRoleList);
	}
	
	@RequestMapping(value="/approlelist/{appId}",method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@ResponseBody
	public List<EligibleTableInfo> getEligibleByAppRoleListInOrganize(@PathVariable("appId") String appId)throws ServiceException {
		
		return eligibleService.getEligibleByAppId(appId);
	}


	
	
	
	


}

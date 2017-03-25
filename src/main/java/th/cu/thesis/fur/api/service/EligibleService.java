package th.cu.thesis.fur.api.service;

import java.util.List;

import th.cu.thesis.fur.api.model.ApplicationRole;
import th.cu.thesis.fur.api.model.Organize;
import th.cu.thesis.fur.api.repository.model.Eligible;
import th.cu.thesis.fur.api.repository.model.EligibleOrgAppRoleApp;
import th.cu.thesis.fur.api.rest.model.EligibleGridRequest;
import th.cu.thesis.fur.api.rest.model.EligibleGridResponse;
import th.cu.thesis.fur.api.rest.model.EligibleRequest;
import th.cu.thesis.fur.api.rest.model.EligibleTableInfo;
import th.cu.thesis.fur.api.service.exception.ServiceException;

public interface EligibleService {

	public EligibleGridResponse findEligibles(EligibleGridRequest request, String userLogin) throws ServiceException;
	
	public void createEligible(EligibleRequest request) throws ServiceException;
	
	public void createEligibleList(List<EligibleRequest> request) throws ServiceException;
	
	public void deleteEligibleById(String eligibleId) throws ServiceException;
	
	public List<Organize> findOrganizeByOrgCode(String orgCode) throws ServiceException;
	
	public List<Organize> findOrganizeByOrgName(String orgName) throws ServiceException;
	
	public List<Organize> findOrganizeByOrgDesc(String orgDesc) throws ServiceException;
	 
	public List<Eligible> getEligibleByAppRoleList(List<ApplicationRole> appRoleList) throws ServiceException;
	
	public List<EligibleTableInfo> getEligibleByAppId(String appId) throws ServiceException;
}

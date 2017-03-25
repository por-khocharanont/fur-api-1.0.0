package th.cu.thesis.fur.api.service;

import th.cu.thesis.fur.api.rest.model.om.EmployeeProfile;
import th.cu.thesis.fur.api.rest.model.om.ListEmpOmResponse;
import th.cu.thesis.fur.api.rest.model.om.ListNewUserProfileResponse;
import th.cu.thesis.fur.api.rest.model.om.ListOrgAllResponse;
import th.cu.thesis.fur.api.rest.model.om.ListOrgTypeAllResponse;
import th.cu.thesis.fur.api.rest.model.om.ListResignedUserProfileResponse;
import th.cu.thesis.fur.api.rest.model.om.ListUserChangeResponse;
import th.cu.thesis.fur.api.rest.model.om.ListUserReOrgResponse;



public interface OmWebService {
	
	public EmployeeProfile getApproverProfile(String pinCode) throws Exception;
	
	public ListNewUserProfileResponse getUserNew(String date) throws Exception;
	
	public ListOrgAllResponse getOrganize() throws Exception;
	
	public ListEmpOmResponse getAllUserProfile() throws Exception;
	
	public ListResignedUserProfileResponse getUserResignedResponse(String date) throws Exception;
	
	public ListUserChangeResponse getUserChange(String date)throws Exception;
	
	public ListOrgTypeAllResponse getUserTypeByOrgCode()throws Exception;
	
	public ListUserReOrgResponse getUserReOrg(String date) throws Exception;
	
	
}

package th.cu.thesis.fur.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.axiom.om.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.cu.thesis.fur.api.filter.RestControllerLoggingFilter;
import th.cu.thesis.fur.api.model.ApplicationRole;
import th.cu.thesis.fur.api.model.Organize;
import th.cu.thesis.fur.api.model.UserMenu;
import th.cu.thesis.fur.api.repository.ApplicationRepository;
import th.cu.thesis.fur.api.repository.EligibleRepository;
import th.cu.thesis.fur.api.repository.model.Eligible;
import th.cu.thesis.fur.api.repository.model.EligibleOrgAppRoleApp;
import th.cu.thesis.fur.api.rest.model.EligibleGridRequest;
import th.cu.thesis.fur.api.rest.model.EligibleGridResponse;
import th.cu.thesis.fur.api.rest.model.EligibleRequest;
import th.cu.thesis.fur.api.rest.model.EligibleTableInfo;
import th.cu.thesis.fur.api.service.CommonService;
import th.cu.thesis.fur.api.service.EligibleService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;
import th.cu.thesis.fur.api.validation.EligibleValidator;

@Service("eligibleService")
public class EligibleServiceImpl implements EligibleService {

	private static final Logger logger = LoggerFactory.getLogger(EligibleServiceImpl.class);
	
	private static final String ROLE_ADMIN_TELECOM = "ADMIN-TELECOM";
	
	private static final String APP_TYPE_TELECOM = "2";

	@Autowired
	private EligibleRepository eligibleRepository;

	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private CommonService commonService;

	private EligibleValidator eligibleValidator = new EligibleValidator();

	@Override
	public EligibleGridResponse findEligibles(EligibleGridRequest request, String userLogin) throws ServiceException {

		EligibleGridResponse response = new EligibleGridResponse();

		try {
			
			//find Role Code = ADMIN-TELECOM
			UserMenu userMenu = commonService.getUserMenuByUsername(userLogin);
			
			String roleCode = userMenu.getRoleCode();

			String orgCode = request.getOrgcode();
			String orgName = request.getOrgname();
			String orgDesc = request.getOrgdesc();	
			String appId = request.getApplicationId();
			String appName = request.getApplicationName();
			String appRoleId = request.getApplicationRoleId();
			String appRoleName = request.getApplicationRoleName();
			String appType = request.getApplicationType();
			
			orgCode = CommonUtil.toSqlLikeFormat(orgCode);
			orgName = CommonUtil.toSqlLikeFormat(orgName);
			orgDesc = CommonUtil.toSqlLikeFormat(orgDesc);
			appName = CommonUtil.toSqlLikeFormat(appName);
			
			if(roleCode.equals(ROLE_ADMIN_TELECOM)){
				appType = APP_TYPE_TELECOM;
			}

			String orderBy = "";
			if (request.getSidx().equalsIgnoreCase("ORGCODE") || request.getSidx().equalsIgnoreCase("ORGNAME")
					|| request.getSidx().equalsIgnoreCase("ORGDESC"))
				orderBy = "org." + request.getSidx();

			if (request.getSidx().equalsIgnoreCase("APP_NAME"))
				orderBy = "ap." + request.getSidx();

			if (request.getSidx().equalsIgnoreCase("APP_ROLE_NAME"))
				orderBy = "ar." + request.getSidx();

			orderBy += " " + request.getSord();

			// set page, size
			int page = request.getPage();
			int size = request.getRows();
			int startRow = ((page - 1) * size) + 1;
			int endRow = 0;

			// count Total Eligible
			logger.info(RestControllerLoggingFilter.DATABASE, "CountTotalEligible","orgCode="+orgCode+", orgName="+orgName+", orgDesc="+orgDesc+", appId="+appId+", appName="+appName+", appType="+appType+", appRoleId="+appRoleId+", appRoleName="+appRoleName);
			int totalRecord = eligibleRepository.countTotalEligible(orgCode, orgName, orgDesc, appId, appName, appType, appRoleId, appRoleName);
			logger.info(RestControllerLoggingFilter.DATABASE, "CountTotalEligible","totalRecord="+totalRecord);

			if (totalRecord == size) {
				endRow = totalRecord;
			} else {
				endRow = (startRow + size) - 1;
			}

			// find Eligible List with startRow, endRow
			logger.info(RestControllerLoggingFilter.DATABASE, "FindEligibleList","orgCode="+orgCode+", orgName="+orgName+", orgDesc="+orgDesc+", appId="+appId+", appName="+appName+", appType="+appType+", appRoleId="+appRoleId+", appRoleName="+appRoleName+", startRow="+startRow+", endRow="+endRow+", orderBy="+orderBy);
			List<EligibleOrgAppRoleApp> eligibleList = eligibleRepository.findEligibleList(orgCode, orgName, orgDesc,
					appId, appName, appType, appRoleId, appRoleName, startRow, endRow, orderBy);
			logger.info(RestControllerLoggingFilter.DATABASE, "FindEligibleList","eligible Lists Size="+eligibleList.size());
			
			for(EligibleOrgAppRoleApp eligible : eligibleList){
				
				String appTyp = eligible.getType();
				String appTypeText = commonService.getMessage("app.type."+appTyp);
				eligible.setTypeText(appTypeText);
			}

			response.setRows(eligibleList);
			response.setRecords(totalRecord);
			response.setPage(page);

			int total = 0;
			if (totalRecord % size == 0) {
				total = totalRecord / size;
			} else {
				total = (totalRecord / size) + 1;
			}

			response.setTotal(total);

		} catch (Exception e) {
			
			throw ServiceException.get500DBError(e);
		}

		return response;
	}

	@Override
	public void createEligible(EligibleRequest request) throws ServiceException {

		try {

			// validate
			eligibleValidator.validate(request);

			String appRoleId = request.getAppRoleId();
			String orgCode = request.getOrgcode();
			String createdBy = request.getCreatedBy();
			String updatedBy = request.getUpdatedBy();

			// check Organize by orgCode
			logger.info(RestControllerLoggingFilter.DATABASE, "CountOrganizeByOrgCode","orgCode="+orgCode);
			Integer countOrganize = eligibleRepository.countOrganizeByOrgCode(orgCode);
			if (countOrganize == 0) {

				throw ServiceException.get404DataNotFound("40401", "Data not found");

			}

			// check App_Role by app_role_id
			logger.info(RestControllerLoggingFilter.DATABASE, "CountApplicationRoleByAppRoleId","appRoleId="+appRoleId);
			Integer countAppRole = applicationRepository.countApplicationRoleByAppRoleId(appRoleId);
			if (countAppRole == 0) {

				throw ServiceException.get404DataNotFound("40401", "Data not found");

			}

			// check Eligible exist
			logger.info(RestControllerLoggingFilter.DATABASE, "Check eligible exist","appRoleId="+appRoleId+", orgCode="+orgCode);
			Integer eligibleExist = eligibleRepository.countByAppRleIdAndOrgCode(appRoleId, orgCode);

			if (eligibleExist > 0) {

				throw ServiceException.get400BadRequest("40003", "Data Existed");

			}

			// Insert Eligible
			Eligible eligible = new Eligible();
			eligible.setEligibleId(CommonUtil.generateUUID());
			eligible.setAppRoleId(appRoleId);
			eligible.setOrgcode(orgCode);
			eligible.setCreatedBy(createdBy);
			eligible.setUpdatedBy(updatedBy);

			logger.info(RestControllerLoggingFilter.DATABASE, "Create eligible ",eligible);
			Integer result = eligibleRepository.create(eligible);

		} catch (RuntimeException e) {

			throw ServiceException.get500DBError(e);
		}

	}

	@Override
	public void createEligibleList(List<EligibleRequest> request) throws ServiceException {

		try {

			for (EligibleRequest re : request) {

				// validate
				eligibleValidator.validate(re);

				String appRoleId = re.getAppRoleId();
				String orgCode = re.getOrgcode();
				String createdBy = re.getCreatedBy();
				String updatedBy = re.getUpdatedBy();

				// check Organize by orgCode
				logger.info(RestControllerLoggingFilter.DATABASE, "CountOrganizeByOrgCode","orgCode="+orgCode);
				Integer countOrganize = eligibleRepository.countOrganizeByOrgCode(orgCode);
				if (countOrganize == 0) {

					throw ServiceException.get404DataNotFound("40401", "Data not found");

				}

				// check App_Role by app_role_id
				logger.info(RestControllerLoggingFilter.DATABASE, "CountApplicationRoleByAppRoleId","appRoleId="+appRoleId);
				Integer countAppRole = applicationRepository.countApplicationRoleByAppRoleId(appRoleId);
				if (countAppRole == 0) {

					throw ServiceException.get404DataNotFound("40401", "Data not found");

				}

				// check Eligible exist
				logger.info(RestControllerLoggingFilter.DATABASE, "Check eligible exist","appRoleId="+appRoleId+", orgCode="+orgCode);
				Integer eligibleExist = eligibleRepository.countByAppRleIdAndOrgCode(appRoleId, orgCode);

				if (eligibleExist == 0) {

					// Insert Eligible
					Eligible eligible = new Eligible();
					eligible.setEligibleId(CommonUtil.generateUUID());
					eligible.setAppRoleId(appRoleId);
					eligible.setOrgcode(orgCode);
					eligible.setCreatedBy(createdBy);
					eligible.setUpdatedBy(updatedBy);

					logger.info(RestControllerLoggingFilter.DATABASE, "Create eligible ",eligible);
					Integer result = eligibleRepository.create(eligible);

				}

			}

		} catch (RuntimeException e) {

			throw ServiceException.get500DBError(e);
		}

	}

	@Override
	public void deleteEligibleById(String eligibleId) throws ServiceException {

		try {

			logger.info(RestControllerLoggingFilter.DATABASE, "Delete eligible","eligibleId="+eligibleId);
			Integer result = eligibleRepository.deleteById(eligibleId);

		} catch (RuntimeException e) {

			throw ServiceException.get500DBError(e);
		}

	}

	@Override
	public List<Organize> findOrganizeByOrgCode(String orgCode) throws ServiceException {

		List<Organize> organizeList = new ArrayList<Organize>();

		try {

			logger.info(RestControllerLoggingFilter.DATABASE, "findOrganizeByOrgCode","orgCode="+orgCode);
			organizeList = eligibleRepository.findOrganizeByOrgCode(orgCode);

		} catch (RuntimeException e) {
			
			throw ServiceException.get500SystemError(e);
		}

		return organizeList;
	}

	@Override
	public List<Organize> findOrganizeByOrgName(String orgName) throws ServiceException {

		List<Organize> organizeList = new ArrayList<Organize>();

		try {

			logger.info(RestControllerLoggingFilter.DATABASE, "findOrganizeByOrgName","orgName="+orgName);
			organizeList = eligibleRepository.findOrganizeByOrgName(orgName);

		} catch (RuntimeException e) {
			
			throw ServiceException.get500SystemError(e);
		}

		return organizeList;
	}

	@Override
	public List<Organize> findOrganizeByOrgDesc(String orgDesc) throws ServiceException {

		List<Organize> organizeList = new ArrayList<Organize>();

		try {

			logger.info(RestControllerLoggingFilter.DATABASE, "findOrganizeByOrgDesc","orgDesc="+orgDesc);
			organizeList = eligibleRepository.findOrganizeByOrgDesc(orgDesc);

		} catch (RuntimeException e) {
			
			throw ServiceException.get500SystemError(e);
		}

		return organizeList;
	}

//	@Override
//	public List<EligibleOrgAppRoleApp> getListEligiblebyAppNameStd(String appName, String userName)
//			throws ServiceException {
//		// TODO Auto-generated method stub
//		return eligibleRepository.getListEligiblebyAppNameStd(appName, userName);
//	}
//
//	@Override
//	public List<EligibleOrgAppRoleApp> getListEligibilebyAppNameSpc(String appName, String userName) {
//		// TODO Auto-generated method stub
//		return eligibleRepository.getListEligibilebyAppNameSpc(appName, userName);
//	}
//
//	@Override
//	public List<EligibleOrgAppRoleApp> getEligibleListRoleAppDropdownByAppId(String appId) throws ServiceException {
//		// TODO Auto-generated method stub
//		return eligibleRepository.getEligibleListRoleAppByAppId(appId);
//	}
//
//	@Override
//	public EligibleGridResponse getEligibleListRoleAppGridByAppId(EligibleGridRequest eligibleRequest)
//			throws ServiceException {
//		String appId = eligibleRequest.getApplicationId();
//		int page = eligibleRequest.getPage();
//		int size = eligibleRequest.getRows();
//		int startRow = ((page - 1) * size) + 1;
//		int endRow = 0;
//		Integer totalRecord = eligibleRepository.getCountEligibleListRoleAppByAppId(appId);
//		EligibleGridResponse eligibleResponse = new EligibleGridResponse();
//		if (totalRecord == size) {
//			endRow = totalRecord;
//		} else {
//			endRow = (startRow + size) - 1;
//		}
//		try {
//			List<EligibleOrgAppRoleApp> eligibleList = eligibleRepository
//					.getEligibleListRoleAppByAppId(eligibleRequest.getApplicationId());
//			eligibleResponse.setRows(eligibleList);
//			eligibleResponse.setPage(page);
//			eligibleResponse.setRecords(totalRecord);
//			int total = 0;
//			if (totalRecord % size == 0) {
//				total = totalRecord / size;
//			} else {
//				total = (totalRecord / size) + 1;
//			}
//
//			eligibleResponse.setTotal(total);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// TODO Auto-generated method stub
//		return eligibleResponse;
//	}

	@Override
	public List<Eligible> getEligibleByAppRoleList(List<ApplicationRole> appRoleList) throws ServiceException {
		List<Eligible> response = new ArrayList<Eligible>();
		for (ApplicationRole appRole : appRoleList) {
			List<Eligible> eligibleList = eligibleRepository.getEligibleByAppRoleId(appRole.getAppRoleId());
			for (Eligible eligible : eligibleList) {
				response.add(eligible);
			}
		}
		return response;
	}

	@Override
	public List<EligibleTableInfo> getEligibleByAppId(String appId) throws ServiceException {
		List<EligibleTableInfo> response = new ArrayList<EligibleTableInfo>();
		List<Organize> orgList = eligibleRepository.getOrganizeListByAppId(appId);
		for (Organize organize : orgList) {
			EligibleTableInfo eligibleTableInfo = new EligibleTableInfo();
			String orgCode = organize.getOrgcode();
			List<Eligible> eligible = eligibleRepository.getAppRoleNameByAppIdAndOrgcode(appId, orgCode);
			eligibleTableInfo.setOrgname(organize.getOrgname());
			eligibleTableInfo.setOrgdesc(organize.getOrgdesc());
			eligibleTableInfo.setOrgcode(orgCode);
			eligibleTableInfo.setEligible(eligible);
			response.add(eligibleTableInfo);
		}
		return response;

	}

}

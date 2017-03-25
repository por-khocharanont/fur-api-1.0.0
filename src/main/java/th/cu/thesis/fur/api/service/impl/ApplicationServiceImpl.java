package th.cu.thesis.fur.api.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import oracle.net.aso.a;
import th.cu.thesis.fur.api.filter.RestControllerLoggingFilter;
import th.cu.thesis.fur.api.model.AppOwner;
import th.cu.thesis.fur.api.model.AppOwnerMember;
import th.cu.thesis.fur.api.model.AppOwnerTeam;
import th.cu.thesis.fur.api.model.Application;
import th.cu.thesis.fur.api.model.ApplicationAuthentication;
import th.cu.thesis.fur.api.model.ApplicationRole;
import th.cu.thesis.fur.api.model.ApproveType;
import th.cu.thesis.fur.api.model.Custodian;
import th.cu.thesis.fur.api.model.CustodianMember;
import th.cu.thesis.fur.api.model.CustodianTeam;
import th.cu.thesis.fur.api.model.FileInfo;
import th.cu.thesis.fur.api.model.GroupAppRole;
import th.cu.thesis.fur.api.model.TeamInfo;
import th.cu.thesis.fur.api.model.UR;
import th.cu.thesis.fur.api.model.UserAppRole;
import th.cu.thesis.fur.api.model.UserMenu;
import th.cu.thesis.fur.api.repository.AppOwnerRepository;
import th.cu.thesis.fur.api.repository.ApplicationAuthenticationRepository;
import th.cu.thesis.fur.api.repository.ApplicationRepository;
import th.cu.thesis.fur.api.repository.ApplicationRoleRepository;
import th.cu.thesis.fur.api.repository.ApproveTypeRepository;
import th.cu.thesis.fur.api.repository.CustodianRepository;
import th.cu.thesis.fur.api.repository.EligibleRepository;
import th.cu.thesis.fur.api.repository.GroupAppRoleRepository;
import th.cu.thesis.fur.api.repository.UserAppRoleRepository;
import th.cu.thesis.fur.api.repository.UserRepository;
import th.cu.thesis.fur.api.repository.UserRequestRepository;
import th.cu.thesis.fur.api.repository.model.Eligible;
import th.cu.thesis.fur.api.rest.model.AppOwnerTableInfo;
import th.cu.thesis.fur.api.rest.model.ApplicationForm;
import th.cu.thesis.fur.api.rest.model.ApplicationGridRequest;
import th.cu.thesis.fur.api.rest.model.ApplicationGridResponse;
import th.cu.thesis.fur.api.rest.model.ApplicationRequest;
import th.cu.thesis.fur.api.rest.model.CustodianTableInfo;
import th.cu.thesis.fur.api.rest.model.EligibleTableInfo;
import th.cu.thesis.fur.api.rest.model.UserInfo;
import th.cu.thesis.fur.api.service.ApplicationService;
import th.cu.thesis.fur.api.service.CommonService;
import th.cu.thesis.fur.api.service.exception.MessageInfo;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private ApplicationRoleRepository applicationRoleRepository;

	@Autowired
	private EligibleRepository eligibleRepository;

	@Autowired
	private AppOwnerRepository appOwnerRepository;

	@Autowired
	private CustodianRepository custodianRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApproveTypeRepository approveTypeRepository;
	
	@Autowired
	private GroupAppRoleRepository groupAppRoleRepository;
	
	@Autowired UserRequestRepository userRequestRepository;
	
	@Autowired UserAppRoleRepository userAppRoleRepository;
	
	@Autowired
	private ApplicationAuthenticationRepository applicationAuthenticationRepository;
	
	@Autowired
	private CommonService commonService;

	private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);
	
	private static final String PERSON = "person";
	private static final String TEAM = "team";
	private static final String USER_TYPE_ALL = "0";
	private static final String USER_TYPE_BACK_OFFICE = "1";
	private static final String USER_TYPE_ACC = "2";
	private static final String USER_TYPE_BRANCH = "3";
	private static final String USER_TYPE_OUTLET = "4";
	private static final String APPROVE_TYPE_ALL = "1";
	private static final String APPROVE_TYPE_MINIMUM = "2";
	private static final String APPROVE_TYPE_SEQUENCE = "3";
	private static final String APPROVE_TYPE_SEQUENCE_TEAM = "4";
	private static final String APPROVE_TYPE_PARALLEL_TEAM = "5";
	
	private static final String APPROVE_APP_OWNER_TYPE ="1";
	private static final String APPROVE_CUSTODIAN_TYPE ="2";
	
	private static final String UR_ON_PROCESS_STATUS = "1";
	
	private static final String ROLE_ADMIN_TELECOM = "ADMIN-TELECOM";
	private static final String APP_TYPE_TELECOM = "2";
	
	private static final String CODE_90014 = "90014";
	private static final String CODE_90026 = "90026";
	private static final String CODE_90027 = "90027";
	private static final String CODE_90028 = "90028";
	
	private static final String ROLE_DEFAULT = "Default";
	
	@Override
	public ApplicationGridResponse getSearchApplication(ApplicationGridRequest request) throws ServiceException {
		ApplicationGridResponse applicationGridResponse = new ApplicationGridResponse();
		String appName = request.getAppName();
		String status = request.getStatus();
		String roleName = request.getRoleName();
		String authenticationType = request.getAuthenticationType();
		String appOwnerName = request.getAppOwnerName();
		String custodianName = request.getCustodianName();
		String applicationType = request.getApplicationType();
		String sidx = request.getSidx();
		String sord = request.getSord();
		appName = CommonUtil.toSqlLikeFormat(appName);
		roleName = CommonUtil.toSqlLikeFormat(roleName);
		appOwnerName = CommonUtil.toSqlLikeFormat(appOwnerName);
		custodianName = CommonUtil.toSqlLikeFormat(custodianName);
		
		int page = request.getPage();
		int size = request.getRows();
		int startRow = ((page - 1) * size) + 1;
		int endRow = 0;
		
		try {
			logger.info(RestControllerLoggingFilter.DATABASE, "getCountTotalRecord","appName="+appName+", status="+status+",roleName="+roleName+", authenticationType="+authenticationType
					+", appOwnerName"+appOwnerName+", custodianName="+custodianName+", applicationType="+applicationType);
			int totalRecord = applicationRepository.getCountTotalRecord(appName, status,roleName,authenticationType,appOwnerName,custodianName,applicationType);
			logger.info(RestControllerLoggingFilter.DATABASE, "getCountTotalRecord","totalRecord="+totalRecord);
			
			if (totalRecord == size) {
				endRow = totalRecord;
			} else {
				endRow = (startRow + size) - 1;
			}

			logger.info(RestControllerLoggingFilter.DATABASE, "getSearchApplication","sidx="+sidx+", sord="+sord+", appName="+appName+", status="+status+",roleName="+roleName+", authenticationType="+authenticationType
					+", appOwnerName"+appOwnerName+", custodianName="+custodianName+", applicationType="+applicationType+", startRow="+startRow+", endRow="+endRow);
			List<Application> applicationList = applicationRepository.getSearchApplication(sidx,sord,appName, status,roleName,authenticationType,appOwnerName,custodianName,applicationType,startRow,endRow);
			logger.info(RestControllerLoggingFilter.DATABASE, "getSearchApplication",applicationList);
			
			for(Application app : applicationList){
				String statusText = commonService.getMessage("app.status."+app.getStatus());
				app.setStatusText(statusText);
			}
			applicationGridResponse.setRecords(totalRecord);
			applicationGridResponse.setRows(applicationList);
			applicationGridResponse.setPage(request.getPage());

			int total = 0;
			if (totalRecord % size == 0) {
				total = totalRecord / size;
			} else {
				total = (totalRecord / size) + 1;
			}

			applicationGridResponse.setTotal(total);
		}
		catch(RuntimeException e){
			throw ServiceException.get500DBError(e);
		}
		
		return applicationGridResponse;
	}

	@Override
	public Application getApplicationById(String appId) throws ServiceException {
		Application app = new Application();
		
		logger.info(RestControllerLoggingFilter.DATABASE, "getApplicationById","appId="+appId);
		app = applicationRepository.getApplicationById(appId);
		logger.info(RestControllerLoggingFilter.DATABASE, "getApplicationById",app);
		
		return applicationRepository.getApplicationById(appId);
	}

	@Override
	@Transactional(rollbackFor=ServiceException.class)
	public void updateApplication(String request,String userLogin) throws ServiceException {

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		ApplicationRequest appRequest = gson.fromJson(request, ApplicationRequest.class);
		Application app = new Application();
		
		ApplicationForm appForm = appRequest.getApplicationForm();
		app.setAppId(appForm.getAppId());
		
		
		try{
			
			logger.info(RestControllerLoggingFilter.DATABASE, "getApplicationById","appId="+appForm.getAppId());
			Application appInfo = applicationRepository.getApplicationById(appForm.getAppId());
			logger.info(RestControllerLoggingFilter.DATABASE, "getApplicationById",appInfo);
			
			Type fileCollectionType  = new TypeToken<List<FileInfo>>() {}.getType();
			
			String oldFileStr = appInfo.getAppFile();
			
			
			List<FileInfo> appFiles = new ArrayList<FileInfo>();
			List<FileInfo> oldFileList = gson.fromJson(oldFileStr, fileCollectionType);
			List<FileInfo> newFileList = appRequest.getFiles();
			List<String> deleteFileNameList = appRequest.getDeleteFileNameList();
			
			for(FileInfo appFile : oldFileList){
				if(deleteFileNameList.indexOf(appFile.getFileName()) == -1){
					appFiles.add(appFile);
				}
			}
			
			for(FileInfo appFile : newFileList){
				appFiles.add(appFile);
			}
			
			String appFile = gson.toJson(appFiles);
			
			app.setAppName(appForm.getAppName());
			app.setAppInfo(appForm.getAppInfo());
			app.setStatus(appForm.getStatus());
			app.setType(appForm.getApplicationType());
			
			app.setAppFile(appFile);
			app.setUpdatedBy(userLogin);
			
			logger.info(RestControllerLoggingFilter.DATABASE, "updateApplication",app);
			applicationRepository.updateApplication(app);
			
			logger.info(RestControllerLoggingFilter.DATABASE, "deleteByAppId","appId="+app.getAppId());
			applicationAuthenticationRepository.deleteByAppId(app.getAppId());
			
			for (String authen : appForm.getAuthentication()) {
				ApplicationAuthentication appAuthen = new ApplicationAuthentication();
				appAuthen.setAppAuthenId(CommonUtil.generateUUID());
				appAuthen.setAppId(app.getAppId());
				appAuthen.setAuthenTypeName(authen);
				appAuthen.setCreatedBy(userLogin);
				appAuthen.setUpdatedBy(userLogin);
				logger.info(RestControllerLoggingFilter.DATABASE, "create",appAuthen);
				applicationAuthenticationRepository.create(appAuthen);
			}
						
		}catch(RuntimeException e){
			throw ServiceException.get500SystemError(e);
		}
	};
	
	@Override
	@Transactional(rollbackFor=ServiceException.class)
	public void insertApplication(String request,String userLogin) throws ServiceException {

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		try{
			
			ApplicationRequest appRequest = gson.fromJson(request, ApplicationRequest.class);
			Application app = new Application();
			// applicationValidator.validate(request);
			
			List<FileInfo> appFiles = appRequest.getFiles();
	
			String appFile = gson.toJson(appFiles);
	
			ApplicationForm appForm = appRequest.getApplicationForm();
			
			app.setAppId(CommonUtil.generateUUID());
			app.setAppName(appForm.getAppName());
			app.setAppInfo(appForm.getAppInfo());
			app.setStatus(appForm.getStatus());
			app.setType(appForm.getApplicationType());
			
			app.setAppFile(appFile);
			app.setCreatedBy(userLogin);
			app.setUpdatedBy(userLogin);
			logger.info(RestControllerLoggingFilter.DATABASE, "createApplication",app);
			applicationRepository.createApplication(app);
	
			
			//insert app owner approve type
			ApproveType approveType = new ApproveType();
			approveType.setApproveTypeId(CommonUtil.generateUUID());
			approveType.setAppId(app.getAppId());
			approveType.setType(APPROVE_APP_OWNER_TYPE);
			approveType.setApproveType(appForm.getAppOwnerApproveType());
			approveType.setUpdatedBy(userLogin);
			approveType.setCreatedBy(userLogin);
			if (APPROVE_TYPE_MINIMUM.equals(appForm.getAppOwnerApproveType())) {
				approveType.setMinimumApprove(appForm.getAppOwnerMinimum());
			}
			
			logger.info(RestControllerLoggingFilter.DATABASE, "create",approveType);
			approveTypeRepository.create(approveType);
			
			//insert custodian approve type
			approveType = new ApproveType();
			approveType.setApproveTypeId(CommonUtil.generateUUID());
			approveType.setAppId(app.getAppId());
			approveType.setType(APPROVE_CUSTODIAN_TYPE);
			approveType.setApproveType(appForm.getCustodianApproveType());
			approveType.setUpdatedBy(userLogin);
			approveType.setCreatedBy(userLogin);
			if (APPROVE_TYPE_MINIMUM.equals(appForm.getCustodianApproveType())) {
				approveType.setMinimumApprove(appForm.getCustodianMinimum());
			}
			logger.info(RestControllerLoggingFilter.DATABASE, "create",approveType);
			approveTypeRepository.create(approveType);
			
			// insert APP_AUTHEN
			for (String authen : appForm.getAuthentication()) {
				ApplicationAuthentication appAuthen = new ApplicationAuthentication();
				appAuthen.setAppAuthenId(CommonUtil.generateUUID());
				appAuthen.setAppId(app.getAppId());
				appAuthen.setAuthenTypeName(authen);
				appAuthen.setCreatedBy(userLogin);
				appAuthen.setUpdatedBy(userLogin);
				logger.info(RestControllerLoggingFilter.DATABASE, "create",appAuthen);
				applicationAuthenticationRepository.create(appAuthen);
			}
	
			// insert APP_ROLE
			for (ApplicationRole appRole : appForm.getRoleApplication()) {
				appRole.setAppRoleId(CommonUtil.generateUUID());
				appRole.setAppId(app.getAppId());
				appRole.setUpdatedBy(userLogin);
				appRole.setCreatedBy(userLogin);
				logger.info(RestControllerLoggingFilter.DATABASE, "create",appRole);
				applicationRoleRepository.create(appRole);
	
			}
	
			// insert ELIGIBLE
			for (Eligible eligible : appForm.getEligible()) {
				eligible.setEligibleId(CommonUtil.generateUUID());
	
				for (ApplicationRole appRole : appForm.getRoleApplication()) {
					if (appRole.getAppRoleName().equals(eligible.getAppRoleName())) {
						eligible.setAppRoleId(appRole.getAppRoleId());
					}
				}
				eligible.setUpdatedBy(userLogin);
				eligible.setCreatedBy(userLogin);
				logger.info(RestControllerLoggingFilter.DATABASE, "create",eligible);
				eligibleRepository.create(eligible);
			}
	
			// insert APP_OWNER
			if (PERSON.equals(appForm.getAppOwnerType())) {
				List<UserInfo> personList = appForm.getAppOwnerList().getPersonList();
				int seq = 1;
				for (UserInfo person : personList) {
					AppOwner appOwner = new AppOwner();
					appOwner.setAppOwnerId(CommonUtil.generateUUID());
					appOwner.setAppId(app.getAppId());
					appOwner.setUpdatedBy(userLogin);
					appOwner.setCreatedBy(userLogin);
					
					appOwner.setUserId(person.getUserId());
					appOwner.setSequenceUser(String.valueOf(seq));
					seq++;
					logger.info(RestControllerLoggingFilter.DATABASE, "createAppOwner",appOwner);
					appOwnerRepository.createAppOwner(appOwner);
				}
			}
	
			if (TEAM.equals(appForm.getAppOwnerType())) {
				List<TeamInfo> teamList = appForm.getAppOwnerList().getTeamList();
				int seq = 1;
				for (TeamInfo team : teamList) {
					AppOwnerTeam appOwnerTeam = new AppOwnerTeam();
					appOwnerTeam.setAppOwnerTeamId(CommonUtil.generateUUID());
					appOwnerTeam.setAppId(app.getAppId());
					appOwnerTeam.setTeamName(team.getName());
					appOwnerTeam.setSequenceTeam(String.valueOf(seq));
					appOwnerTeam.setUpdatedBy(userLogin);
					appOwnerTeam.setCreatedBy(userLogin);
					seq++;
					logger.info(RestControllerLoggingFilter.DATABASE, "createAppOwnerTeam",appOwnerTeam);
					appOwnerRepository.createAppOwnerTeam(appOwnerTeam);
					List<UserInfo> personList = team.getMember();
					for (UserInfo person : personList) {
						AppOwnerMember appOwnerMember = new AppOwnerMember();
						appOwnerMember.setAppOwnerMemberId(CommonUtil.generateUUID());
						appOwnerMember.setAppOwnerTeamId(appOwnerTeam.getAppOwnerTeamId());
						
						appOwnerMember.setUserId(person.getUserId());
						appOwnerMember.setUpdatedBy(userLogin);
						appOwnerMember.setCreatedBy(userLogin);
						logger.info(RestControllerLoggingFilter.DATABASE, "createAppOwnerMember",appOwnerMember);
						appOwnerRepository.createAppOwnerMember(appOwnerMember);
					}
				}
			}
	
			// insert CUSTODIAN
			if (PERSON.equals(appForm.getCustodianType())) {
				
				System.out.println(appForm);
				
				List<List<UserInfo>> list = new ArrayList<List<UserInfo>>();
	
				List<UserInfo> personList = new ArrayList<UserInfo>();
				personList = appForm.getCustodianList().getPersonList();
				list.add(personList);
				personList = appForm.getCustodianBackOfficeList().getPersonList();
				list.add(personList);
				personList = appForm.getCustodianACCList().getPersonList();
				list.add(personList);
				personList = appForm.getCustodianBranchList().getPersonList();
				list.add(personList);
				personList = appForm.getCustodianOutletList().getPersonList();
				list.add(personList);
	
				int userType = 0;
				for (List<UserInfo> cusList : list) {
					int seq = 1;
					for (UserInfo person : cusList) {
						Custodian custodian = new Custodian();
						custodian.setCustodianId(CommonUtil.generateUUID());
						custodian.setAppId(app.getAppId());
						
						custodian.setUserId(person.getUserId());
						custodian.setSequenceUser(String.valueOf(seq));
	
						if (USER_TYPE_ALL.equals(String.valueOf(userType))) {
							custodian.setUserType(USER_TYPE_ALL);
						}
						if (USER_TYPE_BACK_OFFICE.equals(String.valueOf(userType))) {
							custodian.setUserType(USER_TYPE_BACK_OFFICE);
						}
						if (USER_TYPE_ACC.equals(String.valueOf(userType))) {
							custodian.setUserType(USER_TYPE_ACC);
						}
						if (USER_TYPE_BRANCH.equals(String.valueOf(userType))) {
							custodian.setUserType(USER_TYPE_BRANCH);
						}
						if (USER_TYPE_OUTLET.equals(String.valueOf(userType))) {
							custodian.setUserType(USER_TYPE_OUTLET);
						}
	
						custodian.setCreatedBy(userLogin);
						custodian.setUpdatedBy(userLogin);
	
						seq++;
						System.out.println(custodian);
						logger.info(RestControllerLoggingFilter.DATABASE, "createCustodian",custodian);
						custodianRepository.createCustodian(custodian);
					}
	
					userType++;
				}
	
			}
	
			if (TEAM.equals(appForm.getCustodianType())) {
				List<List<TeamInfo>> list = new ArrayList<List<TeamInfo>>();
	
				List<TeamInfo> teamList = new ArrayList<TeamInfo>();
				teamList = appForm.getCustodianList().getTeamList();
				list.add(teamList);
				teamList = appForm.getCustodianBackOfficeList().getTeamList();
				list.add(teamList);
				teamList = appForm.getCustodianACCList().getTeamList();
				list.add(teamList);
				teamList = appForm.getCustodianBranchList().getTeamList();
				list.add(teamList);
				teamList = appForm.getCustodianOutletList().getTeamList();
				list.add(teamList);
	
				int userType = 0;
				for (List<TeamInfo> cusTeamList : list) {
					int seq = 1;
					for (TeamInfo team : cusTeamList) {
						
						CustodianTeam custodianTeam = new CustodianTeam();
						custodianTeam.setCustodianTeamId(CommonUtil.generateUUID());
						custodianTeam.setAppId(app.getAppId());
						custodianTeam.setTeamName(team.getName());
						custodianTeam.setSequenceTeam(String.valueOf(seq));
						custodianTeam.setUpdatedBy(userLogin);
						custodianTeam.setCreatedBy(userLogin);
						if (USER_TYPE_ALL.equals(String.valueOf(userType))) {
							custodianTeam.setUserType(USER_TYPE_ALL);
						}
						if (USER_TYPE_BACK_OFFICE.equals(String.valueOf(userType))) {
							custodianTeam.setUserType(USER_TYPE_BACK_OFFICE);
						}
						if (USER_TYPE_ACC.equals(String.valueOf(userType))) {
							custodianTeam.setUserType(USER_TYPE_ACC);
						}
						if (USER_TYPE_BRANCH.equals(String.valueOf(userType))) {
							custodianTeam.setUserType(USER_TYPE_BRANCH);
						}
						if (USER_TYPE_OUTLET.equals(String.valueOf(userType))) {
							custodianTeam.setUserType(USER_TYPE_OUTLET);
						}
						seq++;
						logger.info(RestControllerLoggingFilter.DATABASE, "createCustodianTeam",custodianTeam);
						custodianRepository.createCustodianTeam(custodianTeam);
	
						List<UserInfo> personList = team.getMember();
						for (UserInfo person : personList) {
							CustodianMember custodianMember = new CustodianMember();
							custodianMember.setCustodianMemberId(CommonUtil.generateUUID());
							custodianMember.setCustodianTeamId(custodianTeam.getCustodianTeamId());
							
							custodianMember.setUserId(person.getUserId());
							custodianMember.setUpdatedBy(userLogin);
							custodianMember.setCreatedBy(userLogin);
							logger.info(RestControllerLoggingFilter.DATABASE, "createCustodianMember",custodianMember);
							custodianRepository.createCustodianMember(custodianMember);
						}
					}
					userType++;
				}
	
			}
		}
		catch(RuntimeException e){
			throw ServiceException.get500SystemError(e);
		}

	}

	@Override
	public List<Application> getApplicationByAppName(String appName,String userLogin) throws ServiceException {

		UserMenu userMenu = commonService.getUserMenuByUsername(userLogin);
		
		String roleCode = userMenu.getRoleCode();
		
		List<Application> applicationList = new ArrayList<Application>();

		appName = CommonUtil.toSqlLikeFormat(appName);
		try {
			if(roleCode.equals(ROLE_ADMIN_TELECOM)){
				String type = APP_TYPE_TELECOM; 
				logger.info(RestControllerLoggingFilter.DATABASE, "findByAppNameAndType","appName="+appName+" ,type="+type);
				applicationList = applicationRepository.findByAppNameAndType(appName, type);
				logger.info(RestControllerLoggingFilter.DATABASE, "findByAppNameAndType",applicationList);
			}
			

			logger.info(RestControllerLoggingFilter.DATABASE, "findByAppName","appName="+appName);
			applicationList = applicationRepository.findByAppName(appName);
			logger.info(RestControllerLoggingFilter.DATABASE, "findByAppName",applicationList);

		} catch (RuntimeException e) {
			throw ServiceException.get500DBError(e);
		}

		return applicationList;
	}
	
	@Override
	public List<Application> checkApplicationName(String appName) throws ServiceException {

		List<Application> applicationList = new ArrayList<Application>();

		try {
			logger.info(RestControllerLoggingFilter.DATABASE, "getApplicationByAppName","appName="+appName);
			applicationList = applicationRepository.getApplicationByAppName(appName);
			logger.info(RestControllerLoggingFilter.DATABASE, "getApplicationByAppName",applicationList);

		} catch (RuntimeException e) {
			throw ServiceException.get500DBError(e);
		}

		return applicationList;
	}

	@Override
	public List<ApplicationRole> getApplicationRoleByAppId(String appId) throws ServiceException {

		List<ApplicationRole> appRoleList = new ArrayList<ApplicationRole>();
		try {

			logger.info(RestControllerLoggingFilter.DATABASE, "findApplicationRoleByAppId","appId="+appId);
			List<ApplicationRole> appRoleListTemp = applicationRepository.findApplicationRoleByAppId(appId);
			logger.info(RestControllerLoggingFilter.DATABASE, "findApplicationRoleByAppId",appRoleListTemp);
			
			for(ApplicationRole appRole : appRoleListTemp){
				if(ROLE_DEFAULT.equals(appRole.getAppRoleName())){
					appRoleList.add(appRole);
				}
			}
			
			for(ApplicationRole appRole : appRoleListTemp){
				if(!ROLE_DEFAULT.equals(appRole.getAppRoleName())){
					appRoleList.add(appRole);
				}
			}


		} catch (RuntimeException e) {
			throw ServiceException.get500SystemError(e);
		}
		return appRoleList;
	}

	@Override
	public List<ApplicationAuthentication> getAuthenticationByAppId(String appId) throws ServiceException {
		List<ApplicationAuthentication> authentications = new ArrayList<ApplicationAuthentication>();
		try {
			logger.info(RestControllerLoggingFilter.DATABASE, "getAppAuthenByAppId","appId="+appId);
			authentications = applicationAuthenticationRepository.getAppAuthenByAppId(appId);
			logger.info(RestControllerLoggingFilter.DATABASE, "getAppAuthenByAppId",authentications);
		} catch (RuntimeException e) {
			throw ServiceException.get500SystemError(e);
		}
		return authentications;
	}

	@Override
	public AppOwnerTableInfo getAppOwnerDetailByAppId(String appId) throws ServiceException {
		AppOwnerTableInfo response = new AppOwnerTableInfo();
		logger.info(RestControllerLoggingFilter.DATABASE, "getApproveTypeByAppIdAndType","appId="+appId+" ,approveAppOwnerType="+APPROVE_APP_OWNER_TYPE);
		ApproveType approveType = approveTypeRepository.getApproveTypeByAppIdAndType(appId,APPROVE_APP_OWNER_TYPE);
		logger.info(RestControllerLoggingFilter.DATABASE, "getApproveTypeByAppIdAndType",approveType);
		response.setAppOwnerApproveType(approveType.getApproveType());
		
		String approve = response.getAppOwnerApproveType();
		if(APPROVE_TYPE_ALL.equals(approve) || 
				APPROVE_TYPE_SEQUENCE.equals(approve) || 
				APPROVE_TYPE_MINIMUM.equals(approve)){
			response.setAppOwnerType(PERSON);
			logger.info(RestControllerLoggingFilter.DATABASE, "getAppOwnerList","appId="+appId);
			List<AppOwner> appOwnerList = appOwnerRepository.getAppOwnerList(appId);
			logger.info(RestControllerLoggingFilter.DATABASE, "getAppOwnerList",appOwnerList);
			response.setAppOwnerList(appOwnerList);
		}
		
		if(APPROVE_TYPE_MINIMUM.equals(approve)){
			response.setAppOwnerMinimum(approveType.getMinimumApprove());
		}
		
		if(APPROVE_TYPE_SEQUENCE_TEAM.equals(approve) || APPROVE_TYPE_PARALLEL_TEAM.equals(approve)){
			response.setAppOwnerType(TEAM);
			logger.info(RestControllerLoggingFilter.DATABASE, "getAppOwnerTeamList","appId="+appId);
			List<AppOwnerTeam> appOwnerTeamList = appOwnerRepository.getAppOwnerTeamList(appId);
			logger.info(RestControllerLoggingFilter.DATABASE, "getAppOwnerTeamList",appOwnerTeamList);
			
			for(AppOwnerTeam appOwnerTeam : appOwnerTeamList){
				String teamId = appOwnerTeam.getAppOwnerTeamId();
				logger.info(RestControllerLoggingFilter.DATABASE, "getAppOwnerMemberList","teamId="+teamId);
				List<AppOwnerMember> appOwnerMember = appOwnerRepository.getAppOwnerMemberList(teamId);
				logger.info(RestControllerLoggingFilter.DATABASE, "getAppOwnerMemberList",appOwnerMember);
				appOwnerTeam.setAppOwnerMemberList(appOwnerMember);
			}
			response.setAppOwnerTeamList(appOwnerTeamList);
			
		}
		return response;
	}

	@Override
	public CustodianTableInfo getCustodianDetailByAppId(String appId) throws ServiceException {
		CustodianTableInfo response = new CustodianTableInfo();
		
		logger.info(RestControllerLoggingFilter.DATABASE, "getApproveTypeByAppIdAndType","appId="+appId+" ,approveCustodianType="+APPROVE_CUSTODIAN_TYPE);
		ApproveType approveType = approveTypeRepository.getApproveTypeByAppIdAndType(appId,APPROVE_CUSTODIAN_TYPE);
		logger.info(RestControllerLoggingFilter.DATABASE, "getApproveTypeByAppIdAndType",approveType);
		response.setCustodianApproveType(approveType.getApproveType());
		String approve = approveType.getApproveType();
		
		if(APPROVE_TYPE_ALL.equals(approve) || 
				APPROVE_TYPE_SEQUENCE.equals(approve) || APPROVE_TYPE_MINIMUM.equals(approve))
		{
			response.setCustodianType(PERSON);
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianList","appId="+appId+" ,userType="+USER_TYPE_ALL);
			List<Custodian> custodianList = custodianRepository.getCustodianList(appId, USER_TYPE_ALL);
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianList",custodianList);
			response.setCustodianList(custodianList);
			
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianList","appId="+appId+" ,userType="+USER_TYPE_ACC);
			List<Custodian> custodianACCList = custodianRepository.getCustodianList(appId, USER_TYPE_ACC);
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianList",custodianACCList);
			response.setCustodianACCList(custodianACCList);
			
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianList","appId="+appId+" ,userType="+USER_TYPE_BACK_OFFICE);
			List<Custodian> custodianBackOfficeList = custodianRepository.getCustodianList(appId, USER_TYPE_BACK_OFFICE);
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianList",custodianBackOfficeList);
			response.setCustodianBackOfficeList(custodianBackOfficeList);
			
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianList","appId="+appId+" ,userType="+USER_TYPE_BRANCH);
			List<Custodian> custodianBranchList = custodianRepository.getCustodianList(appId, USER_TYPE_BRANCH);
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianList",custodianBranchList);
			response.setCustodianBranchList(custodianBranchList);
			
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianList","appId="+appId+" ,userType="+USER_TYPE_OUTLET);
			List<Custodian> custodianOutletList = custodianRepository.getCustodianList(appId, USER_TYPE_OUTLET);
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianList",custodianOutletList);
			response.setCustodianOutletList(custodianOutletList);
		}
		if(APPROVE_TYPE_MINIMUM.equals(approve)){
			response.setCustodianMinimum(approveType.getMinimumApprove());
		}
		
		if(APPROVE_TYPE_SEQUENCE_TEAM.equals(approve) || APPROVE_TYPE_PARALLEL_TEAM.equals(approve)){
			response.setCustodianType(TEAM);
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianTeamList","appId="+appId+" ,userType="+USER_TYPE_ALL);
			List<CustodianTeam> custodianTeamList = custodianRepository.getCustodianTeamList(appId, USER_TYPE_ALL);
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianTeamList",custodianTeamList);
			//Default
			for(CustodianTeam custodianTeam : custodianTeamList){
				String teamId = custodianTeam.getCustodianTeamId();
				logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianMemberList","teamId="+teamId);
				List<CustodianMember> custodianMember = custodianRepository.getCustodianMemberList(teamId);
				logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianMemberList",custodianMember);
				custodianTeam.setMember(custodianMember);
			}
			response.setCustodianTeamList(custodianTeamList);
			
			//Back Office
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianTeamList","appId="+appId+" ,userType="+USER_TYPE_BACK_OFFICE);
			List<CustodianTeam> custodianBackOfficeTeamList = custodianRepository.getCustodianTeamList(appId, USER_TYPE_BACK_OFFICE);
			logger.info(RestControllerLoggingFilter.DATABASE, "getCustodianTeamList",custodianBackOfficeTeamList);
			for(CustodianTeam custodianTeam : custodianBackOfficeTeamList){
				String teamId = custodianTeam.getCustodianTeamId();
				List<CustodianMember> custodianMember = custodianRepository.getCustodianMemberList(teamId);
				custodianTeam.setMember(custodianMember);
			}
			response.setCustodianBackOfficeTeamList(custodianBackOfficeTeamList);	
			
			//ACC
			List<CustodianTeam> custodianACCTeamList = custodianRepository.getCustodianTeamList(appId, USER_TYPE_ACC);
			
			for(CustodianTeam custodianTeam : custodianACCTeamList){
				String teamId = custodianTeam.getCustodianTeamId();
				List<CustodianMember> custodianMember = custodianRepository.getCustodianMemberList(teamId);
				custodianTeam.setMember(custodianMember);
			}
			response.setCustodianACCTeamList(custodianACCTeamList);	
			
			//Branch
			List<CustodianTeam> custodianBranchTeamList = custodianRepository.getCustodianTeamList(appId, USER_TYPE_BRANCH);
			
			for(CustodianTeam custodianTeam : custodianBranchTeamList){
				String teamId = custodianTeam.getCustodianTeamId();
				List<CustodianMember> custodianMember = custodianRepository.getCustodianMemberList(teamId);
				custodianTeam.setMember(custodianMember);
			}
			response.setCustodianBranchTeamList(custodianBranchTeamList);	
			
			//Outlet
			List<CustodianTeam> custodianOutletTeamList = custodianRepository.getCustodianTeamList(appId, USER_TYPE_OUTLET);
			
			for(CustodianTeam custodianTeam : custodianOutletTeamList){
				String teamId = custodianTeam.getCustodianTeamId();
				List<CustodianMember> custodianMember = custodianRepository.getCustodianMemberList(teamId);
				custodianTeam.setMember(custodianMember);
			}
			response.setCustodianOutletTeamList(custodianOutletTeamList);	
		}
		return response;
	}

	@Override
	public MessageInfo deleteApplicationRole(String appRoleId) throws ServiceException {
		Boolean canDelete = true;
		
		MessageInfo response = null;
		try{
			List<Eligible> eligible = eligibleRepository.getEligibleByAppRoleId(appRoleId);
			List<GroupAppRole>  groupAppRole = groupAppRoleRepository.selectGroupAppRoleByAppRoleId(appRoleId);
			String status = UR_ON_PROCESS_STATUS;
			List<UR> ur = userRequestRepository.selectUrByAppRoleIdAndStatus(appRoleId, status);
			List<UserAppRole> userAppRole = userAppRoleRepository.selectUserAppRoleByAppRoleId(appRoleId);
			
			if(eligible.size()!=0){
				canDelete = false;
				response = new MessageInfo(CODE_90014,commonService.getMessage(CODE_90014));
			}
			else if(groupAppRole.size()!=0){
				canDelete = false;
				response = new MessageInfo(CODE_90026,commonService.getMessage(CODE_90026));
			}
			
			else if(ur.size()!=0){
				canDelete = false;
				response = new MessageInfo(CODE_90027,commonService.getMessage(CODE_90027));
			}
			else if(userAppRole.size()!=0){
				canDelete = false;
				response = new MessageInfo(CODE_90028,commonService.getMessage(CODE_90028));
			}
			
			if(canDelete){
				applicationRoleRepository.deleteById(appRoleId);
			}
			
			return response;
		}catch (RuntimeException e) {
			throw ServiceException.get500SystemError(e);
		}
	}

	@Override
	public ApplicationRole insertApplicationRole(ApplicationRole appRole,String userLogin) throws ServiceException {
		appRole.setAppRoleId(CommonUtil.generateUUID());
		appRole.setCreatedBy(userLogin);
		appRole.setUpdatedBy(userLogin);
		try{
			applicationRoleRepository.create(appRole);
		}catch( RuntimeException e){
			throw ServiceException.get500SystemError(e);
		}
		
		return appRole;
	}

	@Override
	public void updateApplicationRole(String appRoleId, ApplicationRole appRole, String userLogin) throws ServiceException {
		try{
			appRole.setUpdatedBy(userLogin);
			appRole.setAppRoleId(appRoleId);
			applicationRoleRepository.updateById(appRole);
		}catch( RuntimeException e){
			throw ServiceException.get500SystemError(e);
		}
		
	}

	@Override
	public EligibleTableInfo insertEligibleApplication(EligibleTableInfo eligibleInfo, String userLogin) throws ServiceException {
		
		String appId = eligibleInfo.getAppId();
		
		for(Eligible eligible : eligibleInfo.getEligible()){
			String appRoleName = eligible.getAppRoleName();
			ApplicationRole appRole = applicationRoleRepository.selectByAppIdAndAppRoleName(appId, appRoleName);
			eligible.setEligibleId(CommonUtil.generateUUID());
			eligible.setAppRoleId(appRole.getAppRoleId());
			eligible.setUpdatedBy(userLogin);
			eligible.setCreatedBy(userLogin);
			eligibleRepository.create(eligible);
		}
		
		return eligibleInfo;
	}

	@Override
	public EligibleTableInfo updateEligibleApplication(String appId,EligibleTableInfo eligibleInfo, String userLogin) throws ServiceException {
		String orgCode = eligibleInfo.getOrgcode();
		eligibleRepository.deleteByAppIdAndOrgCode(appId,orgCode);
		
		for(Eligible eligible : eligibleInfo.getEligible()){
			String appRoleName = eligible.getAppRoleName();
			ApplicationRole appRole = applicationRoleRepository.selectByAppIdAndAppRoleName(appId, appRoleName);
			eligible.setEligibleId(CommonUtil.generateUUID());
			eligible.setAppRoleId(appRole.getAppRoleId());
			eligible.setUpdatedBy(userLogin);
			eligible.setCreatedBy(userLogin);
			eligibleRepository.create(eligible);
		}
		
		return eligibleInfo;
	}

	@Override
	public void updateAppOwner(String appId,String request,String userLogin) throws ServiceException {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		ApplicationForm appForm = gson.fromJson(request, ApplicationForm.class);
		
		//clear old approve Type
		String type = APPROVE_APP_OWNER_TYPE;
		approveTypeRepository.deleteByAppIdAndType(appId,type);
		
		//insert app owner approve type
		ApproveType approveType = new ApproveType();
		approveType.setApproveTypeId(CommonUtil.generateUUID());
		approveType.setAppId(appId);
		approveType.setType(APPROVE_APP_OWNER_TYPE);
		approveType.setApproveType(appForm.getAppOwnerApproveType());
		approveType.setUpdatedBy(userLogin);
		approveType.setCreatedBy(userLogin);
		if (APPROVE_TYPE_MINIMUM.equals(appForm.getAppOwnerApproveType())) {
			approveType.setMinimumApprove(appForm.getAppOwnerMinimum());
		}
		approveTypeRepository.create(approveType);
		
		String appOwnerType = appForm.getAppOwnerType();
		
		//clear old data before insert
		appOwnerRepository.deleteAppOwnerByAppId(appId);
		appOwnerRepository.deleteAppOwnerMemberByAppId(appId);
		appOwnerRepository.deleteAppOwnerTeamByAppId(appId);
		
		// insert APP_OWNER
		if (PERSON.equals(appOwnerType)) {
			List<UserInfo> personList = appForm.getAppOwnerList().getPersonList();
			int seq = 1;
			for (UserInfo person : personList) {
				AppOwner appOwner = new AppOwner();
				appOwner.setAppOwnerId(CommonUtil.generateUUID());
				appOwner.setAppId(appId);
				appOwner.setUpdatedBy(userLogin);
				appOwner.setCreatedBy(userLogin);
				
				appOwner.setUserId(person.getUserId());
				appOwner.setSequenceUser(String.valueOf(seq));
				seq++;
				appOwnerRepository.createAppOwner(appOwner);
			}
		}

		if (TEAM.equals(appOwnerType)) {
			
			List<TeamInfo> teamList = appForm.getAppOwnerList().getTeamList();
			int seq = 1;
			for (TeamInfo team : teamList) {
				AppOwnerTeam appOwnerTeam = new AppOwnerTeam();
				appOwnerTeam.setAppOwnerTeamId(CommonUtil.generateUUID());
				appOwnerTeam.setAppId(appId);
				appOwnerTeam.setTeamName(team.getName());
				appOwnerTeam.setSequenceTeam(String.valueOf(seq));
				appOwnerTeam.setUpdatedBy(userLogin);
				appOwnerTeam.setCreatedBy(userLogin);
				seq++;
				appOwnerRepository.createAppOwnerTeam(appOwnerTeam);
				List<UserInfo> personList = team.getMember();
				for (UserInfo person : personList) {
					AppOwnerMember appOwnerMember = new AppOwnerMember();
					appOwnerMember.setAppOwnerMemberId(CommonUtil.generateUUID());
					appOwnerMember.setAppOwnerTeamId(appOwnerTeam.getAppOwnerTeamId());
					
					appOwnerMember.setUserId(person.getUserId());
					appOwnerMember.setUpdatedBy(userLogin);
					appOwnerMember.setCreatedBy(userLogin);
					appOwnerRepository.createAppOwnerMember(appOwnerMember);
				}
			}
		}
		
	}

	
	@Override
	public void updateCustodian(String appId, String request, String userLogin) throws ServiceException {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		ApplicationForm appForm = gson.fromJson(request, ApplicationForm.class);
		
		//clear old approve Type
		String type = APPROVE_CUSTODIAN_TYPE;
		approveTypeRepository.deleteByAppIdAndType(appId,type);
		
		//insert app owner approve type
		ApproveType approveType = new ApproveType();
		approveType.setApproveTypeId(CommonUtil.generateUUID());
		approveType.setAppId(appId);
		approveType.setType(APPROVE_CUSTODIAN_TYPE);
		approveType.setApproveType(appForm.getCustodianApproveType());
		approveType.setUpdatedBy(userLogin);
		approveType.setCreatedBy(userLogin);
		if (APPROVE_TYPE_MINIMUM.equals(appForm.getCustodianApproveType())) {
			approveType.setMinimumApprove(appForm.getCustodianMinimum());
		}
		approveTypeRepository.create(approveType);
		
		//delete old data
		custodianRepository.deleteCustodianByAppId(appId);
		custodianRepository.deleteCustodianMemberByAppId(appId);
		custodianRepository.deleteCustodianTeamByAppId(appId);
		
		// insert CUSTODIAN
		if (PERSON.equals(appForm.getCustodianType())) {
			List<List<UserInfo>> list = new ArrayList<List<UserInfo>>();

			List<UserInfo> personList = new ArrayList<UserInfo>();
			personList = appForm.getCustodianList().getPersonList();
			list.add(personList);
			personList = appForm.getCustodianBackOfficeList().getPersonList();
			list.add(personList);
			personList = appForm.getCustodianACCList().getPersonList();
			list.add(personList);
			personList = appForm.getCustodianBranchList().getPersonList();
			list.add(personList);
			personList = appForm.getCustodianOutletList().getPersonList();
			list.add(personList);

			int userType = 0;
			for (List<UserInfo> cusList : list) {
				int seq = 1;
				for (UserInfo person : cusList) {
					Custodian custodian = new Custodian();
					custodian.setCustodianId(CommonUtil.generateUUID());
					custodian.setAppId(appId);
					
					custodian.setUserId(person.getUserId());
					custodian.setSequenceUser(String.valueOf(seq));

					if (USER_TYPE_ALL.equals(String.valueOf(userType))) {
						custodian.setUserType(USER_TYPE_ALL);
					}
					if (USER_TYPE_BACK_OFFICE.equals(String.valueOf(userType))) {
						custodian.setUserType(USER_TYPE_BACK_OFFICE);
					}
					if (USER_TYPE_ACC.equals(String.valueOf(userType))) {
						custodian.setUserType(USER_TYPE_ACC);
					}
					if (USER_TYPE_BRANCH.equals(String.valueOf(userType))) {
						custodian.setUserType(USER_TYPE_BRANCH);
					}
					if (USER_TYPE_OUTLET.equals(String.valueOf(userType))) {
						custodian.setUserType(USER_TYPE_OUTLET);
					}

					custodian.setCreatedBy(userLogin);
					custodian.setUpdatedBy(userLogin);

					seq++;
					custodianRepository.createCustodian(custodian);
				}

				userType++;
			}

		}

		if (TEAM.equals(appForm.getCustodianType())) {
			List<List<TeamInfo>> list = new ArrayList<List<TeamInfo>>();

			List<TeamInfo> teamList = new ArrayList<TeamInfo>();
			teamList = appForm.getCustodianList().getTeamList();
			list.add(teamList);
			teamList = appForm.getCustodianBackOfficeList().getTeamList();
			list.add(teamList);
			teamList = appForm.getCustodianACCList().getTeamList();
			list.add(teamList);
			teamList = appForm.getCustodianBranchList().getTeamList();
			list.add(teamList);
			teamList = appForm.getCustodianOutletList().getTeamList();
			list.add(teamList);

			int userType = 0;
			for (List<TeamInfo> cusTeamList : list) {
				int seq = 1;
				for (TeamInfo team : cusTeamList) {
					
					CustodianTeam custodianTeam = new CustodianTeam();
					custodianTeam.setCustodianTeamId(CommonUtil.generateUUID());
					custodianTeam.setAppId(appId);
					custodianTeam.setTeamName(team.getName());
					custodianTeam.setSequenceTeam(String.valueOf(seq));
					custodianTeam.setUpdatedBy(userLogin);
					custodianTeam.setCreatedBy(userLogin);
					if (USER_TYPE_ALL.equals(String.valueOf(userType))) {
						custodianTeam.setUserType(USER_TYPE_ALL);
					}
					if (USER_TYPE_BACK_OFFICE.equals(String.valueOf(userType))) {
						custodianTeam.setUserType(USER_TYPE_BACK_OFFICE);
					}
					if (USER_TYPE_ACC.equals(String.valueOf(userType))) {
						custodianTeam.setUserType(USER_TYPE_ACC);
					}
					if (USER_TYPE_BRANCH.equals(String.valueOf(userType))) {
						custodianTeam.setUserType(USER_TYPE_BRANCH);
					}
					if (USER_TYPE_OUTLET.equals(String.valueOf(userType))) {
						custodianTeam.setUserType(USER_TYPE_OUTLET);
					}
					seq++;
					custodianRepository.createCustodianTeam(custodianTeam);

					List<UserInfo> personList = team.getMember();
					for (UserInfo person : personList) {
						CustodianMember custodianMember = new CustodianMember();
						custodianMember.setCustodianMemberId(CommonUtil.generateUUID());
						custodianMember.setCustodianTeamId(custodianTeam.getCustodianTeamId());
						
						custodianMember.setUserId(person.getUserId());
						custodianMember.setUpdatedBy(userLogin);
						custodianMember.setCreatedBy(userLogin);
						custodianRepository.createCustodianMember(custodianMember);
					}
				}
				userType++;
			}
		}
	}

}

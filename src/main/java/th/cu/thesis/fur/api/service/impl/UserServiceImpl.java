package th.cu.thesis.fur.api.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import th.cu.thesis.fur.api.model.AuthUser;
import th.cu.thesis.fur.api.model.Organize;
import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.model.UserAppRole;
import th.cu.thesis.fur.api.model.UserToken;
import th.cu.thesis.fur.api.repository.EligibleRepository;
import th.cu.thesis.fur.api.repository.UserAppRoleRepository;
import th.cu.thesis.fur.api.repository.UserRepository;
import th.cu.thesis.fur.api.repository.model.AppRoleApplication;
import th.cu.thesis.fur.api.rest.model.UserApplicationInfoGridRequest;
import th.cu.thesis.fur.api.rest.model.UserApplicationInfoGridResponse;
import th.cu.thesis.fur.api.rest.model.UserInfo;
import th.cu.thesis.fur.api.rest.model.UserTokenInfoGridResponse;
import th.cu.thesis.fur.api.service.CommonService;
import th.cu.thesis.fur.api.service.UserService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.service.ws.client.AuthenticateServiceStub;
import th.cu.thesis.fur.api.service.ws.client.AuthenticateServiceStub.Authenticate;
import th.cu.thesis.fur.api.service.ws.client.AuthenticateServiceStub.AuthenticateResponse;
import th.cu.thesis.fur.api.service.ws.client.AuthenticateServiceStub.ProjectQuery;
import th.cu.thesis.fur.api.service.ws.client.AuthenticateServiceStub.ServiceResponse;
import th.cu.thesis.fur.api.service.ws.client.AuthenticateServiceStub.UserAuthenticate;
import th.cu.thesis.fur.api.util.CommonUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	AuthenticateServiceStub authenticateServiceStub;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EligibleRepository eligibleRepository;

	@Autowired 
	private UserAppRoleRepository userAppRoleRepository;
	
	@Autowired
	private CommonService commonService;

	@Value("${fur.project.code}")
	private String projectCode;

	@Value("${app.env}")
	private String env;

	@Value("${ldap.target.endpoint}")
	private String targetEndpointLdap;

	private static final String APP_ENV_PROD = "prod";
	private static final String APP_ENV_IOT = "iot";
	private static final Integer AUTH_SERVICE_SUCCESS_CODE = 0;
	
	@PostConstruct
	void init() throws AxisFault {
		this.authenticateServiceStub = new AuthenticateServiceStub(targetEndpointLdap);
	}

	@Override
	public UserInfo authUser(AuthUser authUser) throws ServiceException {

		String username = authUser.getUsername();
		String password = authUser.getPassword();

		if (!APP_ENV_PROD.equals(env) || !APP_ENV_IOT.equals(env)) {
			return this.getUserInfo(username);
		}

		UserInfo userInfo = null;
		try {

			UserAuthenticate userAuth = new UserAuthenticate();
			userAuth.setUsername(username.toLowerCase());
			userAuth.setPassword(password);

			ProjectQuery projectQuery = new ProjectQuery();
			projectQuery.setProjectCode(projectCode);
			projectQuery.setProjectLevel(1);

			Authenticate auth = new Authenticate();
			auth.setUser(userAuth);
			auth.setProjectCode(projectQuery);

			AuthenticateResponse authResponse = authenticateServiceStub.authenticate(auth);
			ServiceResponse serviceResponse = authResponse.getAuthenticateResult();

			int authcode = serviceResponse.getCode();
			String message = serviceResponse.getMessage();
			if (!AUTH_SERVICE_SUCCESS_CODE.equals(authcode)) {
				logger.error(authcode + ":" + message);
				throw ServiceException.get401Unauthorized();
			}

			userInfo = this.getUserInfo(username);

		} catch (RemoteException e) {
			throw ServiceException.get500SystemError(e);
		}
		
		return userInfo;

	}

	public User getUserProfileByUsername(String username) throws ServiceException {
		User emp = userRepository.getUserProfileByUsername(username);
		if (emp == null) {
			throw ServiceException.get404FileNotFound("404001");
		}
		return emp;
	}
	
	@Override
	public User getUserProfileByUserId(String userId) throws ServiceException {
		User emp = userRepository.getUserProfileByUserId(userId);
		if (emp == null) {
			throw ServiceException.get404FileNotFound("404001");
		}
		return emp;
	}


	public List<User> getUserProfileList(String username) throws ServiceException {
		username = CommonUtil.toSqlLikeFormat(username);
		List<User> emp = userRepository.getUserProfileList(username);
		return emp;
	}
	
	private UserInfo getUserInfo(String username) throws ServiceException {

		User emp = this.getUserProfileByUsername(username);

		if (emp == null) {
			throw ServiceException.get401Unauthorized("401001");
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(emp.getUsername());
		userInfo.setFirstName(emp.getEnname());
		userInfo.setLastName(emp.getEnsurname());
		userInfo.setEmail(emp.getEmail());
		userInfo.setUserId(emp.getUserId());
		
		
		//find orgType
		List<Organize> org = eligibleRepository.findOrganizeByOrgCode(emp.getOrgcode());
		userInfo.setOrgType(org.get(0).getOrgType());
		
		return userInfo;
	}

	@Override
	public UserApplicationInfoGridResponse getApplicationByType(String type, UserApplicationInfoGridRequest request)
			throws ServiceException {
		UserApplicationInfoGridResponse userApplicationInfoGridResponse = new UserApplicationInfoGridResponse();
		String appName = request.getAppName();
		String username = request.getUsername();
		String authorizationType = request.getAuthorizationType();
		String applicationType = request.getApplicationType();
		String sidx = request.getSidx();
		String sord = request.getSord();
		appName = CommonUtil.toSqlLikeFormat(appName);
		int page = request.getPage();
		int size = request.getRows();
		int startRow = ((page - 1) * size) + 1;
		int endRow = 0;
		
		try {
			Integer totalRecord = 0;
			if ("standard".equals(type)) {
				totalRecord = userRepository.getCountStandardApplicationTotalRecord(appName, username,authorizationType, applicationType);
			} else if ("special".equals(type)) {
				totalRecord = userRepository.getCountSpecialApplicationTotalRecord(appName, username, authorizationType, applicationType);
			} else {
				throw ServiceException.get405MethodNotAllowed();
			}

			if (totalRecord == size) {
				endRow = totalRecord;
			} else {
				endRow = (startRow + size) - 1;
			}
			List<AppRoleApplication> appRoleApplicationLists = new ArrayList<AppRoleApplication>();
			if ("standard".equals(type)) {
				appRoleApplicationLists = userRepository.getStandardApplication(appName, username, startRow, endRow,
						authorizationType, applicationType,sidx,sord);
			} else if ("special".equals(type)) {
				appRoleApplicationLists = userRepository.getSpecialApplication(appName, username, startRow, endRow, 
						authorizationType, applicationType,sidx,sord);
			}

			int idx = 0;
			for (AppRoleApplication appRoleApplication : appRoleApplicationLists) {
				Integer status = userRepository.checkStatusApplication(appRoleApplication.getAppName(),appRoleApplication.getAppRoleName(),username);
				String authorizationText = "";
				String periodTypeText = "";
				
				User user = userRepository.getUserProfileByUsername(username);
				String userId = user.getUserId();
				String appRoleId = appRoleApplication.getAppRoleId();
				
				UserAppRole userAppRole = userAppRoleRepository.selectUserAppRoleByAppRoleIdAndUserId(appRoleId, userId);
				
				if(userAppRole != null){
					appRoleApplication.setPeriodType(userAppRole.getPeriodType());
					appRoleApplication.setStartTime(userAppRole.getStartTime());
					appRoleApplication.setEndTime(userAppRole.getEndTime());
					appRoleApplication.setUrId(userAppRole.getUrId());
				}
				
				if(appRoleApplication.getPeriodType()!=null){
					periodTypeText = commonService.getMessage("ur.period.type."+appRoleApplication.getPeriodType());
				}
				appRoleApplicationLists.get(idx).setPeriodType(periodTypeText);
				
				if(status>0 || "special".equals(type)){
					authorizationText = commonService.getMessage("app.authorization."+CommonUtil.APPLICATION_INFO_AUTHORIZE_ALLOWED);
				}
				else{
					authorizationText = commonService.getMessage("app.authorization."+CommonUtil.APPLICATION_INFO_AUTHORIZE_NOT_ALLOWED);
					appRoleApplicationLists.get(idx).setUrId(null);
					appRoleApplicationLists.get(idx).setPeriodType(null);
					appRoleApplicationLists.get(idx).setStartTime(null);
					appRoleApplicationLists.get(idx).setEndTime(null);
				}
				
				appRoleApplicationLists.get(idx).setAuthorization(authorizationText);
				idx++;
			}
			

			userApplicationInfoGridResponse.setRecords(totalRecord);
			userApplicationInfoGridResponse.setRows(appRoleApplicationLists);
			userApplicationInfoGridResponse.setPage(request.getPage());

			int total = 0;
			if (totalRecord % size == 0) {
				total = totalRecord / size;
			} else {
				total = (totalRecord / size) + 1;
			}

			userApplicationInfoGridResponse.setTotal(total);

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
		
		return userApplicationInfoGridResponse;
	}

	@Override
	public UserTokenInfoGridResponse getApplicationByUserToken(UserApplicationInfoGridRequest request)
			throws ServiceException {
		UserTokenInfoGridResponse userTokenInfoGridResponse = new UserTokenInfoGridResponse();
		String username = request.getUsername();
		int page = request.getPage();
		int size = request.getRows();
		int startRow = ((page - 1) * size) + 1;
		int endRow = 0;

		try{
		Integer totalRecord = 0;
		totalRecord = userRepository.getCountTokenApplicationTotalRecord(username);
		
		if (totalRecord == size) {
			endRow = totalRecord;
		} else {
			endRow = (startRow + size) - 1;
		}

		List<UserToken> userTokens = new ArrayList<UserToken>();
		userTokens = userRepository.getTokenApplication(username);

		userTokenInfoGridResponse.setRecords(totalRecord);
		userTokenInfoGridResponse.setRows(userTokens);
		userTokenInfoGridResponse.setPage(request.getPage());

		int total = 0;
		if (totalRecord % size == 0) {
			total = totalRecord / size;
		} else {
			total = (totalRecord / size) + 1;
		}

		userTokenInfoGridResponse.setTotal(total);
		}catch(RuntimeException e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
		return userTokenInfoGridResponse;
	}

}

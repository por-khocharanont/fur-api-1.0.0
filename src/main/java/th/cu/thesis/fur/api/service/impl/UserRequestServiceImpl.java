package th.cu.thesis.fur.api.service.impl;

import static th.cu.thesis.fur.api.util.CommonUtil.getGson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import th.cu.thesis.fur.api.model.Application;
import th.cu.thesis.fur.api.model.ApplicationAuthentication;
import th.cu.thesis.fur.api.model.ApplicationRole;
import th.cu.thesis.fur.api.model.RequestNo;
import th.cu.thesis.fur.api.model.UR;
import th.cu.thesis.fur.api.model.UrApproveHistory;
import th.cu.thesis.fur.api.model.UrDetail;
import th.cu.thesis.fur.api.model.UrForUser;
import th.cu.thesis.fur.api.model.UrStep;
import th.cu.thesis.fur.api.model.UrStepApprove;
import th.cu.thesis.fur.api.model.UrStepDetail;
import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.model.UserAppOwner;
import th.cu.thesis.fur.api.model.UserAppRole;
import th.cu.thesis.fur.api.model.UserApproveInfo;
import th.cu.thesis.fur.api.model.UserCustodian;
import th.cu.thesis.fur.api.model.UserRequestInfo;
import th.cu.thesis.fur.api.model.UserRoleIden;
import th.cu.thesis.fur.api.model.UserToken;
import th.cu.thesis.fur.api.repository.ApplicationAuthenticationRepository;
import th.cu.thesis.fur.api.repository.ApplicationRepository;
import th.cu.thesis.fur.api.repository.ApplicationRoleRepository;
import th.cu.thesis.fur.api.repository.UserAppRoleRepository;
import th.cu.thesis.fur.api.repository.UserRepository;
import th.cu.thesis.fur.api.repository.UserRequestApproveHistoryRepository;
import th.cu.thesis.fur.api.repository.UserRequestForUserRepository;
import th.cu.thesis.fur.api.repository.UserRequestRepository;
import th.cu.thesis.fur.api.repository.UserRequestStepApproveRepository;
import th.cu.thesis.fur.api.repository.UserTokenRepository;
import th.cu.thesis.fur.api.repository.model.AppFile;
import th.cu.thesis.fur.api.repository.model.UrListUrForUser;
import th.cu.thesis.fur.api.rest.model.BaseGridResponse;
import th.cu.thesis.fur.api.rest.model.DataUserRequestValidate;
import th.cu.thesis.fur.api.rest.model.RequestNoDetail;
import th.cu.thesis.fur.api.rest.model.UserRequestApproveRequest;
import th.cu.thesis.fur.api.rest.model.UserRequestDetail;
import th.cu.thesis.fur.api.rest.model.UserRequestFromGrid;
import th.cu.thesis.fur.api.rest.model.UserRequestListUser;
import th.cu.thesis.fur.api.rest.model.om.EmployeeProfile;
import th.cu.thesis.fur.api.service.CommonService;
import th.cu.thesis.fur.api.service.EmailUtilService;
import th.cu.thesis.fur.api.service.OmWebService;
import th.cu.thesis.fur.api.service.UserRequestService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;

@Service("userrequestService")
public class UserRequestServiceImpl implements UserRequestService {

	private static final Logger logger = LoggerFactory.getLogger(UserRequestServiceImpl.class);
	
	@Autowired
	UserRequestRepository userRequestRepository;
	
	@Autowired
	OmWebService omWebService;
	
	@Autowired
	UserAppRoleRepository userAppRoleRepository;
	
	@Autowired
	ApplicationRepository applicationRepository;
	
	@Autowired
	ApplicationRoleRepository applicationRoleRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRequestApproveHistoryRepository userRequestApproveHistoryRepository;

	@Autowired
	UserRequestStepApproveRepository userRequestStepApproveRepository;

	@Autowired
	UserRequestForUserRepository userRequestForUserRepository;
	
	@Autowired
	UserTokenRepository userTokenRepository;

	@Autowired
	ApplicationAuthenticationRepository applicationAuthenticationRepository;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	EmailUtilService emailUtilService;

	
	static final String APPROVE_TYPE_ALL = "1";
	static final String APPROVE_TYPE_MINIMUM = "2";
	static final String APPROVE_TYPE_SEQUENCE = "3";
	static final String APPROVE_TYPE_SEQUENCE_TEAM = "4";
	static final String APPROVE_TYPE_PARALLEL = "5";
	static final String UR_STEP_WAITING_FOR_CUSTODIAN = "5";
	static final String UR_STEP_WAITING_FOR_CLOSE = "6";

	static final String VALUE_0 = "0";
	static final String VALUE_1 = "1";

	static final String FLOW_TERMINATE_F017 = "F017";
	static final String FLOW_TERMINATE_F018 = "F018";
	static final String FLOW_TERMINATE_F019 = "F019";
	static final String FLOW_TERMINATE_F020 = "F020";
	static final String FLOW_TERMINATE_F021 = "F021";
	static final String FLOW_TERMINATE_F022 = "F022";
	static final String FLOW_TERMINATE_F023 = "F023";
	static final String FLOW_TERMINATE_F024 = "F024";
	static final String FLOW_TERMINATE_F025 = "F025";
	static final String FLOW_TERMINATE_F026 = "F026";
	static final String FLOW_TERMINATE_F027 = "F027";
	static final String FLOW_TERMINATE_F028 = "F028";

	static final String AUTHEN_TYPE_NAME_TOKEN = "1";

	static final String ROLE_DEFAULT = "Default";

	static final String APPROVE_APPROVE = "1";
	static final String APPROVE_REJECT = "2";

	static final String PROCESS_STATUS = "1";
	static final String COMPLETE_STATUS = "2";
	static final String REJECT_STATUS = "3";

	static final String REQUEST_TYPE_NEW = "1";
	static final String REQUEST_TYPE_TERMINATE = "2";
	static final String REQUEST_TYPE_CHANGE = "3";

	static final String CHANGE_ROLE = "CH1R1";
	
	static final String TEMPLATE_EMAIL_USERREQUEST ="email/requestNoTemplate";
	static final String REJECT_HEADER_EMAIL = "[ACIM] Reject UR : ";
	static final String APPROVE_HEADER_EMAIL = "[ACIM] UR : ";

	@Override
	@Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
	public boolean approveUrByType(UserRequestApproveRequest request, String userLogin) throws ServiceException {

		boolean checkUR = true;
		String username = userLogin;

		List<UserRequestInfo> userReqInfoList = request.getUrApproveList();
		List<UserRequestInfo> userReqInfotempList = new ArrayList<UserRequestInfo>();
		
 		for (UserRequestInfo userReqInfo : userReqInfoList) {
			String urId = userReqInfo.getUrId();
			UrStep urStep = userRequestRepository.getUrStepIdProcessByUrId(urId);

			userReqInfo = userRequestRepository.getURAndRequestNoByUrId(urId);
			userReqInfotempList.add(userReqInfo);
			String approveType = urStep.getApproveType();
			String urStepId = urStep.getUrStepId();
			Integer urStepApproveList = userRequestRepository.getCountUrApproveByUsernameAndType(approveType, username,
					urStepId);
			if (urStepApproveList == 0) {
				checkUR = false;
				break;
			}

		}
 		
 		//after upadate value
 		userReqInfoList = userReqInfotempList;

		if (checkUR) {

			for (UserRequestInfo userReqInfo : userReqInfoList) {
				try {
					UrApproveHistory urApproveHistory = new UrApproveHistory();
					urApproveHistory.setUrApproveHistoryId(CommonUtil.generateUUID());

					String urId = userReqInfo.getUrId();
					UrStep urStep = userRequestRepository.getUrStepIdProcessByUrId(urId);
					urApproveHistory.setUrStepId(urStep.getUrStepId());
					urApproveHistory.setUrId(urId);

					UrStepApprove urStepApprove = userRequestStepApproveRepository
							.getUrStepApproveByUrStepIdAndUsername(urStep.getUrStepId(), username);
					urApproveHistory.setPincode(urStepApprove.getPincode());
					urApproveHistory.setEnname(urStepApprove.getEnname());
					urApproveHistory.setEnsurname(urStepApprove.getEnsurname());
					urApproveHistory.setUsername(urStepApprove.getUsername());
					urApproveHistory.setEmail(urStepApprove.getEmail());
					urApproveHistory.setPhone(urStepApprove.getPhone());
					urApproveHistory.setMobile(urStepApprove.getMobile());
					urApproveHistory.setTeamName(urStepApprove.getTeamName());

					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

					String appFile = gson.toJson(request.getFile());

					urApproveHistory.setApproveFile(appFile);
					urApproveHistory.setRemark(request.getRemark());
					urApproveHistory.setUpdatedBy(userLogin);
					urApproveHistory.setCreatedBy(userLogin);

					
					//
					List<UserApproveInfo> userApproveInfoList = request.getUserApproveList();
					
					String checkAllApproveStatus = APPROVE_REJECT;
					for(UserApproveInfo userApproveInfo : userApproveInfoList){
						
						if (APPROVE_APPROVE.equals(userApproveInfo.getApproveStatus())) {
							
							checkAllApproveStatus = APPROVE_APPROVE;
							
							if(userApproveInfo.getTokenSerialNumber()!=null){
								String tokenSerialNumber = userApproveInfo.getTokenSerialNumber();
								userRequestForUserRepository.updateTokenSerialNumberAllByAppTokenAndUsername(tokenSerialNumber,userApproveInfo.getUsername(), userLogin);
							}
						}
						
						if (APPROVE_REJECT.equals(userApproveInfo.getApproveStatus())) {
							
							String urStepStatus = commonService.getMessage("ur.step.reject." + urStep.getUrStep());
							userRequestForUserRepository.updateUrForUserStatusByUrIdAndUsername(userApproveInfo.getUsername(),urStepStatus, REJECT_STATUS, userLogin,
									urId);
						}
						
					}
					
					if(APPROVE_REJECT.equals(checkAllApproveStatus)){
						userRequestRepository.updateUrStepStatusByUrStepId(REJECT_STATUS, userLogin,urStep.getUrStepId());
						userRequestRepository.updateUrStatusByUrId(REJECT_STATUS, userLogin, urId);
						//insert reject history
						urApproveHistory.setStatus(APPROVE_REJECT);
						userRequestApproveHistoryRepository.create(urApproveHistory);
						
						//email reject
						Context variable = generateContextEmailByUrId(urId);
						String header = REJECT_HEADER_EMAIL + userReqInfo.getUrId();
						User user = userRepository.getUserProfileByUsername(userReqInfo.getRequestBy());
						String receiver = user.getEmail();
						try{
							emailUtilService.send(receiver, header, TEMPLATE_EMAIL_USERREQUEST, variable);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					else{
						//insert approve history
						urApproveHistory.setStatus(APPROVE_APPROVE);
						userRequestApproveHistoryRepository.create(urApproveHistory);
						
						ApplicationRole appRole = applicationRoleRepository.selectByAppRoleId(request.getAppRoleId());
						
						if(appRole != null){
							String appRoleId = appRole.getAppRoleId();
							String appRoleName = appRole.getAppRoleName();
							userRequestRepository.updateAppRoleIdAndAppRoleName(urId,appRoleId,appRoleName,userLogin);
						}
						
						if (this.isCompleteApproveByStep(urStep.getUrStepId(), urStep.getApproveType())) {
							userRequestRepository.updateUrStepStatusByUrStepId(COMPLETE_STATUS, userLogin,
									urStep.getUrStepId());

							if (UR_STEP_WAITING_FOR_CUSTODIAN.equals(urStep.getUrStep())) {
								
								UR ur = userRequestRepository.getUrByUrId(urId);

								List<UrForUser> urForUserList = userRequestRepository.getUrForUserByUrIdAndUrStatus(urId, PROCESS_STATUS);

								for (UrForUser urForUser : urForUserList) {

									if (REQUEST_TYPE_NEW.equals(ur.getRequestType())) {

										userAppRoleRepository.deleteUserAppRoleByAppRoleIdAndUserId(ur.getAppRoleId(),
												urForUser.getUserId());

										UserAppRole userAppRole = new UserAppRole();
										userAppRole.setUserAppRoleId(CommonUtil.generateUUID());
										userAppRole.setAppRoleId(ur.getAppRoleId());
										userAppRole.setUrId(urId);

										User userInfo = userRepository.getUserProfileByUsername(urForUser.getUsername());
										userAppRole.setUserId(userInfo.getUserId());

										userAppRole.setPeriodType(ur.getPeriodType());
										userAppRole.setStartTime(ur.getStartTime());
										userAppRole.setEndTime(ur.getEndTime());

										userAppRole.setCreatedBy(userLogin);
										userAppRole.setUpdatedBy(userLogin);

										userAppRoleRepository.insertUserAppRole(userAppRole);
										
										List<ApplicationAuthentication> authenList = applicationAuthenticationRepository.getAppAuthenByAppId(ur.getAppRoleId());
										for (ApplicationAuthentication authen : authenList) {
											if (AUTHEN_TYPE_NAME_TOKEN.equals(authen.getAuthenTypeName())) {
												UserToken userToken = new UserToken();
												userToken.setUserTokenId(CommonUtil.generateUUID());
												userToken.setUserId(userInfo.getUserId());
												userToken.setSerialNumber(urForUser.getTokenSerialNumber());
												userToken.setUrId(urForUser.getUrId());
												userToken.setCreatedBy(userLogin);
												userToken.setUpdatedBy(userLogin);
												userTokenRepository.createUserToken(userToken);
											}
										}
										
										

									}

									if (REQUEST_TYPE_TERMINATE.equals(ur.getRequestType())) {
										userAppRoleRepository.deleteUserAppRoleByAppRoleIdAndUserId(ur.getAppRoleId(),
												urForUser.getUserId());
									}

									if (REQUEST_TYPE_CHANGE.equals(ur.getRequestType())) {

										UserAppRole userAppRole = new UserAppRole();
										userAppRole.setAppRoleId(ur.getAppRoleId());
										userAppRole.setUrId(urId);

										User userInfo = userRepository.getUserProfileByUsername(urForUser.getUsername());
										userAppRole.setUserId(userInfo.getUserId());

										userAppRole.setPeriodType(ur.getPeriodType());
										userAppRole.setStartTime(ur.getStartTime());
										userAppRole.setEndTime(ur.getEndTime());

										userAppRole.setCreatedBy(userLogin);
										userAppRole.setUpdatedBy(userLogin);

										RequestNo requestNo = userRequestRepository.getRequestNoByUrId(urId);
										String[] changeTypeList = requestNo.getChangeType().split(",");

										String appRoleIdForUpdate = ur.getAppRoleId();

										for (String changeType : changeTypeList) {
											if (CHANGE_ROLE.equals(changeType)) {
												appRoleIdForUpdate = ur.getRolePastId();
											}
										}

										// insert
										if (!isUserAppRoleExist(appRoleIdForUpdate, urForUser.getUserId())) {
											userAppRole.setUserAppRoleId(CommonUtil.generateUUID());
											userAppRoleRepository.insertUserAppRole(userAppRole);
										}
										// update
										else {
											if (isUserAppRoleExist(userAppRole.getAppRoleId(), urForUser.getUserId())) {
												userAppRoleRepository.deleteUserAppRoleByAppRoleIdAndUserId(
														userAppRole.getAppRoleId(), urForUser.getUserId());
											}
											userAppRoleRepository.updateUserAppRole(userAppRole, appRoleIdForUpdate);
										}

									}
								}
							} // end ur waiting for custodian

							Boolean isCompleteSequence = this.isCompleteSequenceUrStepByUrId(urId,urStep.getSeQuence());

							if (isCompleteSequence) {
								userRequestRepository.updateUrStatusByUrId(COMPLETE_STATUS, userLogin, urId);
								userRequestForUserRepository.updateUrForUserStatusByUrId(null, COMPLETE_STATUS,userLogin, urId);
							} else {
								UrStep urStepNext = userRequestRepository.getNextSequenceUrStepByUrStepIdProcess(urStep.getUrStepId());
								
								userRequestRepository.updateUrStepStatusByUrStepId(PROCESS_STATUS, userLogin,urStepNext.getUrStepId());
								
								Set<String> mailsTo = new HashSet<String>();
								List<UrStepApprove> urStepApproveList = new ArrayList<UrStepApprove>();
								
								if(APPROVE_TYPE_ALL.equals(urStepNext.getApproveType()) || 
										APPROVE_TYPE_MINIMUM.equals(urStepNext.getApproveType()) ||
										APPROVE_TYPE_PARALLEL.equals(urStepNext.getApproveType())){
									urStepApproveList = userRequestStepApproveRepository.getListUrStepApproveByUrStepId(urStepNext.getUrStepId());
									for(UrStepApprove approved : urStepApproveList){
										try {
											mailsTo.add(new String(approved.getEmail().getBytes(),"UTF-8"));
										} catch (UnsupportedEncodingException e) {
											e.printStackTrace();
										}
									}
								}
								
								if(APPROVE_TYPE_SEQUENCE.equals(urStepNext.getApproveType()) || APPROVE_TYPE_SEQUENCE_TEAM.equals(urStepNext.getApproveType())){
									urStepApproveList = userRequestStepApproveRepository.getListUrStepApproveByUrStepIdAndSequence(urStepNext.getUrStepId(),"1");
									for(UrStepApprove approved : urStepApproveList){
										try {
											mailsTo.add(new String(approved.getEmail().getBytes(),"UTF-8"));
										} catch (UnsupportedEncodingException e) {
											e.printStackTrace();
										}
									}
								}
								
								Context variable = generateContextEmailByUrId(urId);
								String header = APPROVE_HEADER_EMAIL + userReqInfo.getUrId();
								User user = userRepository.getUserProfileByUsername(userReqInfo.getRequestBy());
								try{
									emailUtilService.send(mailsTo, header, TEMPLATE_EMAIL_USERREQUEST, variable);
								}catch(Exception e){
									e.printStackTrace();
								}
								
							}
						} // end if complete
						else{ //else approve not yet
							List<UrStepApprove> urStepApproveList = new ArrayList<UrStepApprove>();
							if(APPROVE_TYPE_SEQUENCE.equals(urStep.getApproveType()) || APPROVE_TYPE_SEQUENCE_TEAM.equals(urStep.getApproveType())){
								Integer countApproved = userRequestRepository.checkCountUrStepApproveHistory(urStep.getUrStepId());
								String sequenceApprove = String.valueOf(countApproved+1);
								urStepApproveList = userRequestStepApproveRepository.getListUrStepApproveByUrStepIdAndSequence(urStep.getUrStepId(),sequenceApprove);
								
								Set<String> mailsTo = new HashSet<String>();
								for(UrStepApprove approved : urStepApproveList){
									try {
										mailsTo.add(new String(approved.getEmail().getBytes(),"UTF-8"));
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
								}
								//email next sequence
								Context variable = generateContextEmailByUrId(urId);
								String header = APPROVE_HEADER_EMAIL + userReqInfo.getUrId();
								User user = userRepository.getUserProfileByUsername(userReqInfo.getRequestBy());
								try{
									emailUtilService.send(mailsTo, header, TEMPLATE_EMAIL_USERREQUEST, variable);
								}catch(Exception e){
									e.printStackTrace();
								}
								
							}
						}	
						
						
					}
					

				} catch (RuntimeException e) {
					e.printStackTrace();
					throw ServiceException.get500DBError(e);
				}
			}
		}
		return checkUR;
	}
	
	@Override
	@Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
	public boolean approveAllUrByType(UserRequestApproveRequest request, String userLogin) throws ServiceException {

		boolean checkUR = true;
		String username = userLogin;

		List<UserRequestInfo> userReqInfoList = request.getUrApproveList();

		for (UserRequestInfo userReqInfo : userReqInfoList) {
			String urId = userReqInfo.getUrId();
			UrStep urStep = userRequestRepository.getUrStepIdProcessByUrId(urId);
			
			userReqInfo = userRequestRepository.getURAndRequestNoByUrId(urId);

			String approveType = urStep.getApproveType();
			String urStepId = urStep.getUrStepId();
			Integer urStepApproveList = userRequestRepository.getCountUrApproveByUsernameAndType(approveType, username,
					urStepId);
			if (urStepApproveList == 0) {
				checkUR = false;
				break;
			}

		}

		if (checkUR) {

			for (UserRequestInfo userReqInfo : userReqInfoList) {
				try {
					UrApproveHistory urApproveHistory = new UrApproveHistory();
					urApproveHistory.setUrApproveHistoryId(CommonUtil.generateUUID());

					String urId = userReqInfo.getUrId();
					UrStep urStep = userRequestRepository.getUrStepIdProcessByUrId(urId);
					urApproveHistory.setUrStepId(urStep.getUrStepId());
					urApproveHistory.setUrId(urId);

					UrStepApprove urStepApprove = userRequestStepApproveRepository.getUrStepApproveByUrStepIdAndUsername(urStep.getUrStepId(), username);
					urApproveHistory.setPincode(urStepApprove.getPincode());
					urApproveHistory.setEnname(urStepApprove.getEnname());
					urApproveHistory.setEnsurname(urStepApprove.getEnsurname());
					urApproveHistory.setUsername(urStepApprove.getUsername());
					urApproveHistory.setEmail(urStepApprove.getEmail());
					urApproveHistory.setPhone(urStepApprove.getPhone());
					urApproveHistory.setStatus(request.getApproveUR());
					urApproveHistory.setMobile(urStepApprove.getMobile());
					urApproveHistory.setTeamName(urStepApprove.getTeamName());

					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

					String appFile = gson.toJson(request.getFile());

					urApproveHistory.setApproveFile(appFile);
					urApproveHistory.setRemark(request.getRemark());
					urApproveHistory.setUpdatedBy(userLogin);
					urApproveHistory.setCreatedBy(userLogin);
					userRequestApproveHistoryRepository.create(urApproveHistory);

					if (APPROVE_REJECT.equals(request.getApproveUR())) {

						String urStepStatus = commonService.getMessage("ur.step.reject." + urStep.getUrStep());
						userRequestRepository.updateUrStatusByUrId(REJECT_STATUS, userLogin, urId);
						userRequestForUserRepository.updateUrForUserStatusByUrId(urStepStatus, REJECT_STATUS, userLogin,
								urId);
						userRequestRepository.updateUrStepStatusByUrStepId(REJECT_STATUS, userLogin,
								urStep.getUrStepId());
						
						//email reject
						Context variable = generateContextEmailByUrId(urId);
						String header = REJECT_HEADER_EMAIL + userReqInfo.getUrId();
						User user = userRepository.getUserProfileByUsername(userReqInfo.getRequestBy());
						String receiver = user.getEmail();
						try{
							emailUtilService.send(receiver, header, TEMPLATE_EMAIL_USERREQUEST, variable);
						}catch(Exception e){
							e.printStackTrace();
						}
						
					}

					if (APPROVE_APPROVE.equals(request.getApproveUR())) {
						
						if (this.isCompleteApproveByStep(urStep.getUrStepId(), urStep.getApproveType())) {
							userRequestRepository.updateUrStepStatusByUrStepId(COMPLETE_STATUS, userLogin,
									urStep.getUrStepId());

							if (UR_STEP_WAITING_FOR_CUSTODIAN.equals(urStep.getUrStep())) {
								UR ur = userRequestRepository.getUrByUrId(urId);

								List<UrForUser> urForUserList = userRequestRepository.getUrForUserByUrIdAndUrStatus(urId, PROCESS_STATUS);

								for (UrForUser urForUser : urForUserList) {

									if (REQUEST_TYPE_NEW.equals(ur.getRequestType())) {

										userAppRoleRepository.deleteUserAppRoleByAppRoleIdAndUserId(ur.getAppRoleId(),
												urForUser.getUserId());

										UserAppRole userAppRole = new UserAppRole();
										userAppRole.setUserAppRoleId(CommonUtil.generateUUID());
										userAppRole.setAppRoleId(ur.getAppRoleId());
										userAppRole.setUrId(urId);

										User userInfo = userRepository
												.getUserProfileByUsername(urForUser.getUsername());
										userAppRole.setUserId(userInfo.getUserId());

										userAppRole.setPeriodType(ur.getPeriodType());
										userAppRole.setStartTime(ur.getStartTime());
										userAppRole.setEndTime(ur.getEndTime());

										userAppRole.setCreatedBy(userLogin);
										userAppRole.setUpdatedBy(userLogin);

										userAppRoleRepository.insertUserAppRole(userAppRole);
										
										List<ApplicationAuthentication> authenList = applicationAuthenticationRepository.getAppAuthenByAppId(ur.getAppRoleId());
										for (ApplicationAuthentication authen : authenList) {
											if (AUTHEN_TYPE_NAME_TOKEN.equals(authen.getAuthenTypeName())) {
												UserToken userToken = new UserToken();
												userToken.setUserTokenId(CommonUtil.generateUUID());
												userToken.setUserId(userInfo.getUserId());
												userToken.setSerialNumber(urForUser.getTokenSerialNumber());
												userToken.setUrId(urForUser.getUrId());
												userToken.setCreatedBy(userLogin);
												userToken.setUpdatedBy(userLogin);
												userTokenRepository.createUserToken(userToken);
											}
										}

									}

									if (REQUEST_TYPE_TERMINATE.equals(ur.getRequestType())) {
										userAppRoleRepository.deleteUserAppRoleByAppRoleIdAndUserId(ur.getAppRoleId(),
												urForUser.getUserId());
									}

									if (REQUEST_TYPE_CHANGE.equals(ur.getRequestType())) {

										UserAppRole userAppRole = new UserAppRole();
										userAppRole.setAppRoleId(ur.getAppRoleId());
										userAppRole.setUrId(urId);

										User userInfo = userRepository
												.getUserProfileByUsername(urForUser.getUsername());
										userAppRole.setUserId(userInfo.getUserId());

										userAppRole.setPeriodType(ur.getPeriodType());
										userAppRole.setStartTime(ur.getStartTime());
										userAppRole.setEndTime(ur.getEndTime());

										userAppRole.setCreatedBy(userLogin);
										userAppRole.setUpdatedBy(userLogin);

										RequestNo requestNo = userRequestRepository.getRequestNoByUrId(urId);
										String[] changeTypeList = requestNo.getChangeType().split(",");

										String appRoleIdForUpdate = ur.getAppRoleId();

										for (String changeType : changeTypeList) {
											if (CHANGE_ROLE.equals(changeType)) {
												appRoleIdForUpdate = ur.getRolePastId();
											}
										}

										// insert
										if (!isUserAppRoleExist(appRoleIdForUpdate, urForUser.getUserId())) {
											userAppRole.setUserAppRoleId(CommonUtil.generateUUID());
											userAppRoleRepository.insertUserAppRole(userAppRole);
										}
										// update
										else {
											if (!isUserAppRoleExist(userAppRole.getAppRoleId(), urForUser.getUserId())) {
												userAppRoleRepository.deleteUserAppRoleByAppRoleIdAndUserId(
														userAppRole.getAppRoleId(), urForUser.getUserId());
											}
											userAppRoleRepository.updateUserAppRole(userAppRole, appRoleIdForUpdate);
										}

									}
								}
							} // end ur waiting for custodian

							Boolean isCompleteSequence = this.isCompleteSequenceUrStepByUrId(urId,urStep.getSeQuence());

							if (isCompleteSequence) {
								userRequestRepository.updateUrStatusByUrId(COMPLETE_STATUS, userLogin, urId);
								userRequestForUserRepository.updateUrForUserStatusByUrId(null, COMPLETE_STATUS,userLogin, urId);
								
							} else {
								UrStep urStepNext = userRequestRepository.getNextSequenceUrStepByUrStepIdProcess(urStep.getUrStepId());
								userRequestRepository.updateUrStepStatusByUrStepId(PROCESS_STATUS, userLogin,urStepNext.getUrStepId());

								Set<String> mailsTo = new HashSet<String>();
								List<UrStepApprove> urStepApproveList = new ArrayList<UrStepApprove>();
								
								if(APPROVE_TYPE_ALL.equals(urStepNext.getApproveType()) || 
										APPROVE_TYPE_MINIMUM.equals(urStepNext.getApproveType()) ||
										APPROVE_TYPE_PARALLEL.equals(urStepNext.getApproveType())){
									urStepApproveList = userRequestStepApproveRepository.getListUrStepApproveByUrStepId(urStepNext.getUrStepId());
									for(UrStepApprove approved : urStepApproveList){
										try {
											mailsTo.add(new String(approved.getEmail().getBytes(),"UTF-8"));
										} catch (UnsupportedEncodingException e) {
											e.printStackTrace();
										}
									}
								}
								
								if(APPROVE_TYPE_SEQUENCE.equals(urStepNext.getApproveType()) || APPROVE_TYPE_SEQUENCE_TEAM.equals(urStepNext.getApproveType())){
									urStepApproveList = userRequestStepApproveRepository.getListUrStepApproveByUrStepIdAndSequence(urStepNext.getUrStepId(),"1");
									for(UrStepApprove approved : urStepApproveList){
										try {
											mailsTo.add(new String(approved.getEmail().getBytes(),"UTF-8"));
										} catch (UnsupportedEncodingException e) {
											e.printStackTrace();
										}
									}
								}
								
								Context variable = generateContextEmailByUrId(urId);
								String header = APPROVE_HEADER_EMAIL + userReqInfo.getUrId();
								User user = userRepository.getUserProfileByUsername(userReqInfo.getRequestBy());
								try{
									emailUtilService.send(mailsTo, header, TEMPLATE_EMAIL_USERREQUEST, variable);
								}catch(Exception e){
									e.printStackTrace();
								}
								
								
							}
						} // end if complete ur step approve
						else{ //else approve not yet
							List<UrStepApprove> urStepApproveList = new ArrayList<UrStepApprove>();
							if(APPROVE_TYPE_SEQUENCE.equals(urStep.getApproveType()) || APPROVE_TYPE_SEQUENCE_TEAM.equals(urStep.getApproveType())){
								Integer countApproved = userRequestRepository.checkCountUrStepApproveHistory(urStep.getUrStepId());
								String sequenceApprove = String.valueOf(countApproved+1);
								urStepApproveList = userRequestStepApproveRepository.getListUrStepApproveByUrStepIdAndSequence(urStep.getUrStepId(),sequenceApprove);
								
								Set<String> mailsTo = new HashSet<String>();
								for(UrStepApprove approved : urStepApproveList){
									try {
										mailsTo.add(new String(approved.getEmail().getBytes(),"UTF-8"));
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
								}
								//email next sequence
								Context variable = generateContextEmailByUrId(urId);
								String header = APPROVE_HEADER_EMAIL + userReqInfo.getRequestNo();
								User user = userRepository.getUserProfileByUsername(userReqInfo.getRequestBy());
								try{
									emailUtilService.send(mailsTo, header, TEMPLATE_EMAIL_USERREQUEST, variable);
								}catch(Exception e){
									e.printStackTrace();
								}
								
							}
						}

					}

				} catch (RuntimeException e) {
					throw ServiceException.get500DBError(e);
				}
			}
		}
		return checkUR;
	}

	private boolean isUserAppRoleExist(String appRoleId, String userId) {
		UserAppRole userAppRole = userAppRoleRepository.selectUserAppRoleByAppRoleIdAndUserId(appRoleId, userId);
		return userAppRole != null;

	}

	private boolean isCompleteSequenceUrStepByUrId(String urId, String sequence) {
		int countUrStep = userRequestRepository.checkCountUrStepByUrId(urId);
		int sequenceNum = Integer.parseInt(sequence);
		return countUrStep == sequenceNum;
	}

	private boolean isCompleteApproveByStep(String urStepId, String approveType) {

		int countApproveHistory = userRequestRepository.checkCountUrStepApproveHistory(urStepId);
		int countApprove = userRequestRepository.checkCountUrStepApprove(approveType, urStepId);

		return countApproveHistory == countApprove;

	}
	
	@Override
	public RequestNoDetail getRequestNoDetail(String requestNo) throws ServiceException {
		RequestNoDetail requestNoDetail = new RequestNoDetail();
		try {
			
			RequestNo request = userRequestRepository.getRequestNoByRequestNo(requestNo);
			
			User userProfile = userRepository.getUserProfileByUsername(request.getUsername());
			
			
			requestNoDetail.setRequestNo(request.getRequestNo());
			requestNoDetail.setUrType(request.getUrType());
			requestNoDetail.setUrTypeText(commonService.getMessage("ur.type."+request.getUrType()));
			requestNoDetail.setRequestList(request.getRequestList());
			requestNoDetail.setRequestListText(commonService.getMessage("request.list."+request.getRequestList()));
			requestNoDetail.setRequestType(request.getRequestType());
			requestNoDetail.setRequestTypeText(commonService.getMessage("ur.request.type."+request.getRequestType()));
			requestNoDetail.setRequestDate(request.getRequestDate());
			requestNoDetail.setEnName(request.getEnname());
			requestNoDetail.setEnSurName(request.getEnsurname());
			requestNoDetail.setSubject(request.getSubject());
			requestNoDetail.setDetail(request.getDetail());
			//requestNoDetail.setGroupAppName();
			requestNoDetail.setRequestRemark(request.getRequestRemark());
			
			requestNoDetail.setUsername(userProfile.getUsername());
			requestNoDetail.setEmail(userProfile.getEmail());
			requestNoDetail.setPhone(userProfile.getPhone());
			requestNoDetail.setMobile(userProfile.getMobile());
			requestNoDetail.setPosition(userProfile.getPosition());
			requestNoDetail.setOrgDesc(userProfile.getOrgdesc());
			requestNoDetail.setCoName(userProfile.getConame());
			
			
			List<UR> urList = userRequestRepository.getUrByRequestNo(requestNo);
			
			List<UrListUrForUser> urListUrForUserList = new ArrayList<UrListUrForUser>();
			for(UR ur : urList){
				
				UrListUrForUser urListUrForUser = new UrListUrForUser();
				 
				String urId = ur.getUrId();
				String urStatus = ur.getStatus();
				String urType = ur.getRequestType();
				String period = ur.getPeriodType();
				
				urListUrForUser.setRequestTypeText(commonService.getMessage("ur.request.type."+urType));
				urListUrForUser.setPeriodTypeText(commonService.getMessage("ur.period.type."+period));
				
				List<UrForUser> urForUserList = userRequestRepository.getUrForUserByUrId(urId);
				urListUrForUser.setUrForUserList(urForUserList);
				
				if(urStatus.equals(PROCESS_STATUS)){
					UrStep urStep = userRequestRepository.getUrStepByUrIdAndStatusProcess(urId);
					
					if(urStep != null){
						
						String urStepKey =  urStep.getUrStep();
						//ur.setStatus(urStepKey);
						
						urListUrForUser.setStatusText(commonService.getMessage("ur.step."+urStepKey));
						
						BeanUtils.copyProperties(ur, urListUrForUser);
						
						urListUrForUserList.add(urListUrForUser);
					}
					
					
				}else{
					urListUrForUser.setStatusText(commonService.getMessage("ur.status."+urStatus));
					
					BeanUtils.copyProperties(ur, urListUrForUser);
					
					urListUrForUserList.add(urListUrForUser);
				}
				
				
			}
			
			requestNoDetail.setListUr(urListUrForUserList);
			
			
			
			
		} catch (RuntimeException e) {
			throw ServiceException.get500DBError(e);
		}
		
		return requestNoDetail;
	}

	@Override
	public UserRequestDetail getUrDetail(String urId, String userLogin) throws ServiceException {

			
		UserRequestDetail userRequestDetail = new UserRequestDetail();
		try{
		// REQUEST_NO
		RequestNo requestNo = userRequestRepository.getRequestNoByUrId(urId);
		UR ur = userRequestRepository.getUrByUrId(urId);

		User user = userRepository.getUserProfileByPin(requestNo.getRequestBy());
		requestNo.setRequestBy(user.getEnfullname());
		userRequestDetail.setRequestNo(requestNo);

		// UR
		UrDetail urDetail = new UrDetail(ur);
		String requestTypeText = commonService.getMessage("ur.request.type." + urDetail.getRequestType());
		urDetail.setRequestType(requestTypeText);
		String periodTypeText = commonService.getMessage("ur.period.type." + urDetail.getPeriodType());
		urDetail.setPeriodType(periodTypeText);

		List<UrForUser> urForUserList = userRequestForUserRepository.getUrForUserByUrId(urId);
		urDetail.setUrForUserList(urForUserList);
		userRequestDetail.setUrDetail(urDetail);

		urDetail.setUrApprove(VALUE_0);
		urDetail.setUrReject(VALUE_0);
		urDetail.setUrToken(VALUE_0);
		urDetail.setUrDefault(VALUE_0);
		
		List<ApplicationAuthentication> authenList = applicationAuthenticationRepository.getAppAuthenByAppId(urDetail.getAppId());
		for (ApplicationAuthentication authen : authenList) {
			if (AUTHEN_TYPE_NAME_TOKEN.equals(authen.getAuthenTypeName())) {
				urDetail.setUrToken(VALUE_1);
			}
		}
		
		// UR_STEP
		List<UrStep> urStepList = userRequestRepository.getUrStepByUrId(urId);

		List<UrStepDetail> urStepDetailList = new ArrayList<UrStepDetail>();
		for (UrStep urStep : urStepList) {
			UrStepDetail urStepDetail = new UrStepDetail(urStep);

			List<UrStepApprove> urStepApproveList = userRequestStepApproveRepository.getListUrStepApproveByUrStepId(urStep.getUrStepId());
			List<UrApproveHistory> urApproveHistoryList = userRequestApproveHistoryRepository.getListUrApproveHistoryByUrStepId(urStep.getUrStepId());

			for (UrApproveHistory urApproveHistory : urApproveHistoryList) {
				String status = commonService.getMessage("ur.approve.history.status." + urApproveHistory.getStatus());
				urApproveHistory.setStatus(status);
			}

			String flowNodeName = commonService.getMessage("flow.node.code." + urStep.getUrStep());
			urStepDetail.setUrStepText(flowNodeName);
			
			String flowName = commonService.getMessage("flow.node." + urStep.getUrStep());
			urStepDetail.setFlowText(flowName);
			
			urStepDetail.setUrStepApproveList(urStepApproveList);
			urStepDetail.setUrApproveHistoryList(urApproveHistoryList);

			urStepDetailList.add(urStepDetail);

		}

		// check can approve

		
		String currentStep = userRequestRepository.checkUrCurrentStepByUrIdAndStatus(urId,PROCESS_STATUS);
		
		if(currentStep!=null){//process
			userRequestDetail.setCurrentStep(currentStep);
		}
		else{
			//reject
			currentStep = userRequestRepository.checkUrCurrentStepByUrIdAndStatus(urId,REJECT_STATUS);
			userRequestDetail.setCurrentStep(currentStep);
			if(currentStep==null){
				//waiting for close
				userRequestDetail.setCurrentStep(UR_STEP_WAITING_FOR_CLOSE);
			}
		}
		
		UrStep urStep = userRequestRepository.getUrStepIdProcessByUrId(urId);
		
		
		Integer canApprove = 0;
		if(urStep!=null){
			canApprove = userRequestRepository.getCountUrApproveByUsernameAndType(urStep.getApproveType(),userLogin, urStep.getUrStepId());
		}

		
//		UrStep urStepCanApprove = userRequestRepository.checkUrStepCanApproveByUsernameAndApproveType(userLogin,urId,PROCESS_STATUS,urStep.getApproveType());
		
		if (canApprove != 0) {
			urDetail.setUrApprove(VALUE_1);
			urDetail.setUrReject(VALUE_1);

			if (UR_STEP_WAITING_FOR_CLOSE.equals(urStep.getUrStep())) {
				urDetail.setUrReject(VALUE_0);
			}

			if (FLOW_TERMINATE_F017.equals(urDetail.getFlowId())
					|| FLOW_TERMINATE_F018.equals(urDetail.getFlowId())
					|| FLOW_TERMINATE_F019.equals(urDetail.getFlowId())
					|| FLOW_TERMINATE_F020.equals(urDetail.getFlowId())
					|| FLOW_TERMINATE_F021.equals(urDetail.getFlowId())
					|| FLOW_TERMINATE_F022.equals(urDetail.getFlowId())
					|| FLOW_TERMINATE_F023.equals(urDetail.getFlowId())
					|| FLOW_TERMINATE_F024.equals(urDetail.getFlowId())
					|| FLOW_TERMINATE_F025.equals(urDetail.getFlowId())
					|| FLOW_TERMINATE_F026.equals(urDetail.getFlowId())
					|| FLOW_TERMINATE_F027.equals(urDetail.getFlowId())
					|| FLOW_TERMINATE_F028.equals(urDetail.getFlowId())) {
				urDetail.setUrReject(VALUE_0);
			}
			
			if (UR_STEP_WAITING_FOR_CUSTODIAN.equals(urStep.getUrStep())) {
				if (urDetail.getAppRoleName().equals(ROLE_DEFAULT)) {
					urDetail.setUrDefault(VALUE_1);
				}
			}

		}
		// end check
		
		userRequestDetail.setUrStepList(urStepDetailList);
		} catch (RuntimeException e) {
			throw ServiceException.get500DBError(e);
		}
		return userRequestDetail;
	}

	
	@Override
	public List<AppFile> getPathfileByAppId(String appId) throws ServiceException {
		try{
			String appFileJson = userRequestRepository.getAppFileByAppId(appId);
			List<AppFile> appFileList = getGson().fromJson(appFileJson, new TypeToken<List<AppFile>>(){}.getType());
			return appFileList;
		}catch(Exception e){
			throw ServiceException.get500SystemError(e);
		}
		
	}

	@Override
	public List<Map<String, String>> validAlreadyRoleApp(DataUserRequestValidate dataUserRequestValidate) {
		// TODO Auto-generated method stub
		List<String> lsuser = dataUserRequestValidate.getLsuser();
		List<String> lsapp = dataUserRequestValidate.getLsapp();
		List<Map<String, String>> usererror = new ArrayList<Map<String,String>>();
		for(String appRoleId :lsapp ){
			for(String userId: lsuser){
				Map<String, String> rs = userRequestRepository.validAlreadyRoleApp(appRoleId, userId);
				if(rs!=null){
					usererror.add(rs);
				}
			}
			
		}
		return usererror;
	}

	@Override
	public List<Map<String, String>> validURisQueued(DataUserRequestValidate dataUserRequestValidate) {
		// TODO Auto-generated method stub
		List<String> lsuser = dataUserRequestValidate.getLsuser();
		List<String> lsapp = dataUserRequestValidate.getLsapp();
		List<Map<String, String>> usererror = new ArrayList<Map<String,String>>();
		for(String appRoleId :lsapp ){
			for(String userId: lsuser){
				Map<String, String> rs=userRequestRepository.validURisQueued(appRoleId,userId);
				if(rs!=null){
					usererror.add(rs);
				}
			}
			
		}
		return usererror;
	}
	
	@Override
	public DataUserRequestValidate validbothAlreadyQueued(DataUserRequestValidate dataUserRequestValidate) {
		// TODO Auto-generated method stub
		List<String> lsuser = dataUserRequestValidate.getLsuser();
		List<String> lsapp = dataUserRequestValidate.getLsapp();
		String queued = dataUserRequestValidate.getStatusQueued();
		String already = dataUserRequestValidate.getStatusAlready();
		List<Map<String, Object>> userUr = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> errors = new ArrayList<Map<String,Object>>();
		if(StringUtils.isNoneBlank(queued)&&StringUtils.isNoneBlank(already)){
			for(String appRoleId :lsapp ){
				for(String userId: lsuser){
					Map<String, Object> rs=userRequestRepository.validbothAleadyQueued(userId, appRoleId);
					if(queued.equals(rs.get("queued").toString())&&already.equals(rs.get("already").toString())){
						userUr.add(rs);
					}else{
						errors.add(rs);
					}
					
				}
				
			}
		}
		dataUserRequestValidate.setOplsbothAlreadyQueued(userUr);
		dataUserRequestValidate.setErrorsbothAlreadyQueued(errors);
		return dataUserRequestValidate;
	}

	@Override
	public String getAuthenTypeByAppId(String appId) {
		// TODO Auto-generated method stub
		return  String.valueOf(userRequestRepository.getAuthenTypeByAppId(appId));
	}

	@Override
	public List<Map<String, EmployeeProfile>> getListApproverManager(
			List<UserRequestListUser> request) {
		List<Map<String, EmployeeProfile>> listuser = new ArrayList<Map<String,EmployeeProfile>>();
		for(UserRequestListUser user :request){
			Map<String, EmployeeProfile> map = new HashMap<String, EmployeeProfile>();
			String pincode = this.getPincodeByUserIdorUsername(String.valueOf(user.getUserId()), null);
				EmployeeProfile employee = new EmployeeProfile();
				try {
					employee = omWebService.getApproverProfile(pincode);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map.put(pincode, employee);
				listuser.add(map);
			}
		
		
		// TODO Auto-generated method stub
		return listuser;
	}


	@Override
	public String getPincodeByUserIdorUsername(String userId, String username) {
		// TODO Auto-generated method stub
		return userRequestRepository.getPincodeByUserIdorUsername(userId, username);
	}

	@Override
	@Transactional(rollbackFor=ServiceException.class,propagation = Propagation.REQUIRED)
	public String subMitforCommitdata(DataUserRequestValidate dataUserRequestValidate,Map<String, String> dataFormApp,List<UserRequestFromGrid> requestFromGrids) throws ServiceException  {
		try{ 
				logger.info("############ START CREATE REQUEST NO ###############");
				Integer countQueueError=0;
				if(!dataUserRequestValidate.getErrorsbothAlreadyQueued().isEmpty()){
					 countQueueError = dataUserRequestValidate.getErrorsbothAlreadyQueued().size();
				}
				Integer countUrAll = dataUserRequestValidate.getLsapp().size()*dataUserRequestValidate.getLsuser().size(); 
				if ((countUrAll-countQueueError)<=0){
					logger.error("############ BREAK CREATE REQUEST NO ############### : ALL_UR - URERROR {}",countUrAll-countQueueError);
					throw ServiceException.get500SystemError();
				}
		
				String useridRequestBy = dataFormApp.get("userId");
				String usertypeValue = dataFormApp.get("usertypeValue");
				String requestByValue = dataFormApp.get("requestByValue");
				String requestTypeValue = dataFormApp.get("requestTypeValue");
				String subject = dataFormApp.get("subject");
				String detail = dataFormApp.get("detail");
				String periodtype = dataFormApp.get("periodtype");
				String dteStart = dataFormApp.get("dteStart");
				String dteTo = dataFormApp.get("dteTo");
				String remarkError ="";
				if(dataFormApp.get("queued")!=null){
					remarkError = dataFormApp.get("queued");
				}
				if(dataFormApp.get("alreadyApp")!=null){
					remarkError = remarkError+dataFormApp.get("alreadyApp");
				}
				Set<String> mailsTo = new HashSet<String>();
				final String userIdfirst = (String)dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("userId");
				User userCloseUr = userRequestRepository.getUserByUserId(useridRequestBy);
				User firstUser= userRequestRepository.getUserByUserId(userIdfirst);
				EmployeeProfile manager;
				User managerUser;
				try {
					manager = omWebService.getApproverProfile(firstUser.getPincode());
					managerUser = userRequestRepository.getUserByUsername(manager.getUserName());
				} catch (Exception e1) {
					logger.error("############ BREAK CREATE REQUEST NO ############### : PROBLEM CALL SERVICE GET MANAGER APPROVER AT USERFIRST : {} ",firstUser.getPincode());
					throw ServiceException.get500SystemError(e1);
				} // service Om
			
				// Step InsertUserRequestNo
				RequestNo requestNo = new RequestNo();
				Integer sequence = userRequestRepository.getSequenceRequestNo();
				String idRequestNo = CommonUtil.genRequestNo(sequence);
				Integer IndexRun=1;
				requestNo.setRequestNo(idRequestNo);
				requestNo.setUrType(usertypeValue);
				requestNo.setRequestList(requestByValue);
				requestNo.setRequestType(requestTypeValue);
				requestNo.setSubject(subject);
				requestNo.setDetail(detail);
				requestNo.setRequestBy(userCloseUr.getPincode()); 
				requestNo.setUsername(userCloseUr.getUsername());
				requestNo.setEnname(userCloseUr.getEnname());
				requestNo.setEnsurname(userCloseUr.getEnsurname());
				requestNo.setEmail(userCloseUr.getEmail());
				requestNo.setMobile(userCloseUr.getMobile());
				requestNo.setPhone(userCloseUr.getPhone());
				requestNo.setPosition(userCloseUr.getPosition());
				requestNo.setOrganize(userCloseUr.getOrgcode());
				requestNo.setCompany(userCloseUr.getCocode());
				if(remarkError.length()>4000){
					remarkError.substring(0, 3999);
					requestNo.setRequestRemark(remarkError);
				}else{
					requestNo.setRequestRemark(remarkError);
				}
				requestNo.setCreatedBy(userCloseUr.getUsername());
				requestNo.setUpdatedBy(userCloseUr.getUsername());
				userRequestRepository.insertRequestNo(requestNo);
				logger.info("CREATED REQUEST NO : {} || {}",idRequestNo,requestNo);
				
				// using in Generate ContextVariable Email
				dataFormApp.put("requestNo", idRequestNo);
				
				logger.info("######### STARTING CREATE UR #########");
				
				String orgCode = userRequestRepository.getOrgCodeByUserId((String)dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("userId"));
				logger.info("GET FIRST ORGCODE BY USER_ID: {} || {}",orgCode,dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("userId"));
				
				final String appRoleIdfirst = (String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appRoleId");
				String appRoleIdold = (String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appRoleId");
				
				String userType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(orgCode);
				logger.info("GET FIRST USERTYPE BY ORGCODE: {} || {}",userType,orgCode);
				
				//value 0 standard app // 1 special app
				Integer standOrSpecialApp = userRequestRepository.getTypeStandOrSpecialApp(orgCode, appRoleIdold);
				
				logger.info("APPLICATION : SPECIAL=1,STANDARD=0 || {}",standOrSpecialApp);
				
				String flowId = selectFlowIdByTypeNew(standOrSpecialApp,userType);
				logger.info("GET FLOW_ID {} , APPLICATION SPC,STD: {} || USERTYPE: {}",flowId,standOrSpecialApp,userType);
				List<Map<String, String>> flowIds = userRequestRepository.getListNodeFlowConfig(flowId);
				logger.info("OBJ_FLOW : {} , APP_ROLE_ID {} ",flowIds,appRoleIdold);
				
				
				UserRequestFromGrid rowData = (UserRequestFromGrid) CollectionUtils.find(requestFromGrids, new Predicate(){
					@Override
					public boolean evaluate(Object arg0) {
						UserRequestFromGrid queuedCheckdata = (UserRequestFromGrid)arg0;
						// TODO Auto-generated method stub
						return queuedCheckdata.getAppRoleId().equals(appRoleIdfirst);
					}
					
				});
				
				UR ur = null;
				String idur = null;
				// request for yourself , INDIVIDUAL
				if("1".equals(requestByValue)){
					logger.info("######### INSERT INDIVIDUAL  #########");
					/* MAKE MODEL UR , FIRST UR INSERT */ 
					ur = new UR();
					idur = CommonUtil.genUrNo(idRequestNo, IndexRun++);
					ur.setUrId(idur);
					ur.setRequestNo(idRequestNo);
					ur.setAppId((String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appId"));
					ur.setAppName((String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appName"));
					ur.setAppRoleId((String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appRoleId"));
					ur.setAppRoleName((String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appRolename"));
					ur.setRequestType(requestTypeValue);
					ur.setFlowId(flowId);
					ur.setStatus("1");
					ur.setRemark((String) rowData.getRemark());
					if("1".equals(periodtype)){
						ur.setPeriodType(periodtype);
					}else if("2".equals(periodtype)){
						ur.setPeriodType(periodtype);
						ur.setStartTime(CommonUtil.textToDate(dteStart));
						ur.setEndTime(CommonUtil.textToDate(dteTo));
					}else{
						logger.error("######### BREAK INVALID PARAMETER : PERIODTYPE {} #########",periodtype);
						throw ServiceException.get500SystemError();
					}
					List<Map<String,String>> listfilename = new ArrayList<Map<String,String>>();
					for(String namefile :rowData.getTemfile()){
						Map<String,String> filename = new HashMap<String,String>();
						filename.put("fileName", namefile);
						filename.put("filePath","/"+idRequestNo+"/"+idur+"/"+namefile);
						listfilename.add(filename);
					}
					ur.setUrFile(CommonUtil.getGson().toJson(listfilename));
					ur.setCreatedBy(userCloseUr.getUsername());
					ur.setUpdatedBy(userCloseUr.getUsername());
					userRequestRepository.insertUr(ur);
					logger.info("MODEL UR : {}",ur);
					/* PLEASE READ BELOWN  */
					//FOR LOOP  this LIST_DATA from API_validate_data , MAP (USER_ID : APP_ROLE_ID) 
					//Example Data 1:Record (appId,appRoleId,appName,appRolename,userId,username,queued,already) 
					//sort data By Collections.sort (do that in process controller before pass to api) with appRoleId ,that do it because do it for support CREATE UR GROUP
					for(final Map<String,Object> datas : dataUserRequestValidate.getOplsbothAlreadyQueued()){
						List<UserAppOwner> listApproversforAppOwner = new ArrayList<UserAppOwner>();
						List<UserCustodian> listApproversforCustodian = new ArrayList<UserCustodian>();
						List<UserRoleIden> roleIdens = new ArrayList<UserRoleIden>();
						String appRoleId = (String) datas.get("appRoleId");
						
						/* if appRoleId changed. program work on else */
						if(appRoleIdold.equals(appRoleId)){
							if(flowIds.isEmpty()){
								logger.error("######### BREAK CAUSE FLOW WAS NULL #########");
								throw ServiceException.get500SystemError();
							}else{
								for(Map<String, String> flow :flowIds){
									if("2".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE MANAGER ####### ");
										UrStep urStep = new UrStep();
										String urStepId=CommonUtil.generateUUID();
										urStep.setUrStepId(urStepId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType("2");
										urStep.setMinimumApprove("1");
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
											if(StringUtils.isNotBlank(manager.getEmail())&&StringUtils.isNotEmpty(manager.getEmail())){
												mailsTo.add(new String(manager.getEmail().getBytes(),"UTF-8"));
											}
											
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										
										UrStepApprove urStepApprove = new UrStepApprove();
										urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
										urStepApprove.setUrStepId(urStepId);
										urStepApprove.setPincode(manager.getPin());
										urStepApprove.setUsername(manager.getUserName());
										urStepApprove.setEmail(manager.getEmail());
										urStepApprove.setEnname(manager.getEnFirstName());
										urStepApprove.setEnsurname(manager.getEnLastName());
										urStepApprove.setMobile(managerUser.getMobile());
										urStepApprove.setPhone(managerUser.getPhone());
										urStepApprove.setPincode(manager.getPin());
										urStepApprove.setCreatedBy(userCloseUr.getUsername());
										urStepApprove.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStepApprove(urStepApprove);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
										logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
									}else if("3".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE ROLEIDEN ####### ");
										roleIdens = userRequestRepository.getListRoleIdenByUsertype(userType);
										logger.info("OBJ_LIST_ROLEIDEN : {} , BY USERTYPE : {}",roleIdens,idur);
										if(roleIdens.isEmpty()){
											roleIdens = userRequestRepository.getListRoleIdenBytypeZero();
											logger.info("OBJ_LIST_ROLEIDEN : {} , BY DEFAULT ",roleIdens);
										}
										if(roleIdens.isEmpty()){
											logger.error("########## BREAK ROLE IDEN NULL ############ BY USERTYPE : {}",userType);
											throw ServiceException.get500SystemError();
											//throwns roleIdens is null
										}else{  
											UrStep urStep = new UrStep();
											String urStepId=CommonUtil.generateUUID();
											urStep.setUrStepId(urStepId);
											urStep.setUrId(idur);
											urStep.setUrStep(flow.get("flowNodeCode"));
											urStep.setSeQuence(flow.get("seQuence"));
											urStep.setApproveType("2");
											urStep.setMinimumApprove("1");
											if("1".equals(flow.get("seQuence"))){
												urStep.setStatus("1");
												for(UserRoleIden roleIden :roleIdens){
													if(StringUtils.isNotBlank(roleIden.getEmail())&&StringUtils.isNotEmpty(roleIden.getEmail())){
														mailsTo.add(new String(roleIden.getEmail().getBytes(),"UTF-8"));
													}
												}
											}else{
												urStep.setStatus("0");
											}
											urStep.setCreatedBy(userCloseUr.getUsername());
											urStep.setUpdatedBy(userCloseUr.getUsername());
											logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
											userRequestRepository.insertUrStep(urStep);
												for(UserRoleIden roleIden :roleIdens){
													UrStepApprove urStepApprove = new UrStepApprove();
													urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
													urStepApprove.setUrStepId(urStepId);
													urStepApprove.setUsername(roleIden.getUsername());
													urStepApprove.setEmail(roleIden.getEmail());
													urStepApprove.setEnname(roleIden.getEnname());
													urStepApprove.setEnsurname(roleIden.getEnsurname());
													urStepApprove.setMobile(roleIden.getMobile());
													urStepApprove.setPhone(roleIden.getPhone());
													urStepApprove.setPincode(roleIden.getPincode());
													urStepApprove.setCreatedBy(userCloseUr.getUsername());
													urStepApprove.setUpdatedBy(userCloseUr.getUsername());
													userRequestRepository.insertUrStepApprove(urStepApprove);
													logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
												}
										}
									}else if("4".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE APP_OWNER ####### ");
										Integer type_Approver_forAppOwner = userRequestRepository.getTypeApproverforAppOwner(appRoleId);
										listApproversforAppOwner = selectApproverAppownerByTypeApprover(type_Approver_forAppOwner,appRoleId);
										logger.info("OBJ_LIST_APPOWNER : {} , BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forAppOwner,appRoleId);
										if(listApproversforAppOwner.isEmpty()){
											logger.error("########## LIST_APPOWNER IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forAppOwner,appRoleId);
											throw ServiceException.get500SystemError();
											//throwns AppOwnerApprovers is null
										}else{
											Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "1");
											UrStep urStep = new UrStep();
											String urStepId=CommonUtil.generateUUID();
											urStep.setUrStepId(urStepId);
											urStep.setUrId(idur);
											urStep.setUrStep(flow.get("flowNodeCode"));
											urStep.setSeQuence(flow.get("seQuence"));
											urStep.setApproveType(typeApproveandminimum.get("approveType"));
											urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
											if("1".equals(flow.get("seQuence"))){
												urStep.setStatus("1");
											}else{
												urStep.setStatus("0");
											}
											urStep.setCreatedBy(userCloseUr.getUsername());
											urStep.setUpdatedBy(userCloseUr.getUsername());
											userRequestRepository.insertUrStep(urStep);
											logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
												for(UserAppOwner userAppOwner :listApproversforAppOwner){
													UrStepApprove urStepApprove = new UrStepApprove();
													urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
													urStepApprove.setUrStepId(urStepId);
													urStepApprove.setUsername(userAppOwner.getUsername());
													urStepApprove.setEmail(userAppOwner.getEmail());
													urStepApprove.setEnname(userAppOwner.getEnname());
													urStepApprove.setEnsurname(userAppOwner.getEnsurname());
													urStepApprove.setMobile(userAppOwner.getMobile());
													urStepApprove.setPhone(userAppOwner.getPhone());
													urStepApprove.setPincode(userAppOwner.getPincode());
													urStepApprove.setCreatedBy(userCloseUr.getUsername());
													urStepApprove.setUpdatedBy(userCloseUr.getUsername());
													urStepApprove.setSequence(userAppOwner.getSeQuenceUser());
													urStepApprove.setTeamName(userAppOwner.getTeamName());
													urStepApprove.setTeamSequence(userAppOwner.getSeQuenceTeam());
													userRequestRepository.insertUrStepApprove(urStepApprove);
													logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
												}
										}	
									}else if("5".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE CUSTODIAN ####### ");
										Integer type_Approver_forCustodian = userRequestRepository.getTypeApproverforCustodian(appRoleId);
										listApproversforCustodian = selectApproverCustodianByType(type_Approver_forCustodian,appRoleId,userType);
										logger.info("OBJ_LIST_CUSTODIAN : {} , BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forCustodian,appRoleId);
										if(listApproversforCustodian.isEmpty()){
											logger.error("########## LIST_CUSTODIAN IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forCustodian,appRoleId);
											throw ServiceException.get500SystemError();
										}else{
										Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "2");
										UrStep urStep = new UrStep();
										String urStepId=CommonUtil.generateUUID();
										urStep.setUrStepId(urStepId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType(typeApproveandminimum.get("approveType"));
										urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
											for(UserCustodian userCustodian :listApproversforCustodian){
												UrStepApprove urStepApprove = new UrStepApprove();
												urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
												urStepApprove.setUrStepId(urStepId);
												urStepApprove.setUsername(userCustodian.getUsername());
												urStepApprove.setEmail(userCustodian.getEmail());
												urStepApprove.setEnname(userCustodian.getEnname());
												urStepApprove.setEnsurname(userCustodian.getEnsurname());
												urStepApprove.setMobile(userCustodian.getMobile());
												urStepApprove.setPhone(userCustodian.getPhone());
												urStepApprove.setPincode(userCustodian.getPincode());
												urStepApprove.setCreatedBy(userCloseUr.getUsername());
												urStepApprove.setUpdatedBy(userCloseUr.getUsername());
												urStepApprove.setSequence(userCustodian.getSeQuenceUser());
												urStepApprove.setTeamName(userCustodian.getTeamName());
												urStepApprove.setTeamSequence(userCustodian.getSeQuenceTeam());
												userRequestRepository.insertUrStepApprove(urStepApprove);
												logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
											}
										}
									}else if("6".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE CLOSE ####### ");
										UrStep urStep = new UrStep();
										String idStpId = CommonUtil.generateUUID();
										urStep.setUrStepId(idStpId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType("2");
										urStep.setMinimumApprove("1");
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
										for(Map<String,Object> datas2 : dataUserRequestValidate.getOplsbothAlreadyQueued()){
												
												if(datas2.get("appRoleId").equals(appRoleIdold)){
													User user = userRequestRepository.getUserByUserId((String) datas2.get("userId"));
													UrStepApprove urStepApprove = new UrStepApprove();
													urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
													urStepApprove.setUrStepId(idStpId);
													urStepApprove.setUsername(user.getUsername());
													urStepApprove.setEmail(user.getEmail());
													urStepApprove.setEnname(user.getEnname());
													urStepApprove.setEnsurname(user.getEnsurname());
													urStepApprove.setMobile(user.getMobile());
													urStepApprove.setPhone(user.getPhone());
													urStepApprove.setPincode(user.getPincode());
													urStepApprove.setCreatedBy(userCloseUr.getUsername());
													urStepApprove.setUpdatedBy(userCloseUr.getUsername());
													userRequestRepository.insertUrStepApprove(urStepApprove);
													logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
				 									UrForUser urForUser = new UrForUser(user);
													urForUser.setUrForUserId(CommonUtil.generateUUID());
													urForUser.setUrId(idur);
													urForUser.setUrStatus("1");
													urForUser.setCreatedBy(userCloseUr.getUsername());
													urForUser.setUpdatedBy(userCloseUr.getUsername());
													userRequestRepository.insertUrforUser(urForUser);
													logger.info("OBJ_UR_FOR_USER: {} , BY UR_ID : {} , USERNAME :{}",urForUser,idur,user.getUsername());
												}
											
										}
										
									}else{
										throw ServiceException.get500SystemError();
									}
								} //for loop map
							} // else flowIds
						}else{
							logger.info("########## END CREATE UR_ID:{} ##############",idur);
							
							logger.info("######### STARTING CREATE UR #########");
							appRoleIdold = (String) datas.get("appRoleId");
							final String appRoleId2 = appRoleIdold;
							standOrSpecialApp = userRequestRepository.getTypeStandOrSpecialApp(orgCode, appRoleIdold);
							logger.info("APPLICATION : SPECIAL=1,STANDARD=0 || {}",standOrSpecialApp);
							flowId = selectFlowIdByTypeNew(standOrSpecialApp,userType);
							logger.info("GET FLOW_ID {} , APPLICATION SPC,STD: {} || USERTYPE: {}",flowId,standOrSpecialApp,userType);
							flowIds = userRequestRepository.getListNodeFlowConfig(flowId);
							logger.info("OBJ_FLOW : {} , APP_ROLE_ID {} ",flowIds,appRoleIdold);
							UserRequestFromGrid rowData2 = (UserRequestFromGrid) CollectionUtils.find(requestFromGrids, new Predicate(){
								@Override
								public boolean evaluate(Object arg0) {
									UserRequestFromGrid queuedCheckdata = (UserRequestFromGrid)arg0;
									return queuedCheckdata.getAppRoleId().equals(appRoleId2);
								}
								
							});
							// InsertUr
							ur = new UR();
							idur = CommonUtil.genUrNo(idRequestNo, IndexRun++);
							ur.setUrId(idur);
							ur.setRequestNo(idRequestNo);
							ur.setAppId((String) datas.get("appId"));
							ur.setAppName((String) datas.get("appName"));
							ur.setAppRoleId((String) datas.get("appRoleId"));
							ur.setAppRoleName((String) datas.get("appRolename"));
							ur.setRequestType(requestTypeValue);
							ur.setFlowId(flowId);
							ur.setStatus("1");
							ur.setRemark((String) rowData2.getRemark());
							if("1".equals(periodtype)){
								ur.setPeriodType(periodtype);
							}else if("2".equals(periodtype)){
								ur.setPeriodType(periodtype);
								ur.setStartTime(CommonUtil.textToDate(dteStart));
								ur.setEndTime(CommonUtil.textToDate(dteTo));
							}else{
								logger.error("######### BREAK INVALID PARAMETER : PERIODTYPE {} #########",periodtype);
								throw ServiceException.get500SystemError();
								// thrown datainput invaild
							}
							listfilename = new ArrayList<Map<String,String>>();
							for(String namefile :rowData2.getTemfile()){
								Map<String,String> filename = new HashMap<String,String>();
								filename.put("fileName", namefile);
								filename.put("filePath","/"+idRequestNo+"/"+idur+"/"+namefile);
								listfilename.add(filename);
							}
							ur.setUrFile(CommonUtil.getGson().toJson(listfilename));
							ur.setCreatedBy(userCloseUr.getUsername());
							ur.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUr(ur);
							logger.info("MODEL UR : {}",ur);
							if(flowIds.isEmpty()){
								logger.error("######### BREAK CAUSE FLOW WAS NULL #########");
								throw ServiceException.get500SystemError();
								//throwns flowIds is null
							}else{
								for(Map<String, String> flow :flowIds){
									if("2".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE MANAGER ####### ");
										UrStep urStep = new UrStep();
										String urStepId=CommonUtil.generateUUID();
										urStep.setUrStepId(urStepId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType("2");
										urStep.setMinimumApprove("1");
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
											if(StringUtils.isNotBlank(manager.getEmail())&&StringUtils.isNotEmpty(manager.getEmail())){
												mailsTo.add(new String(manager.getEmail().getBytes(),"UTF-8"));
											}
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
										UrStepApprove urStepApprove = new UrStepApprove();
										urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
										urStepApprove.setUrStepId(urStepId);
										urStepApprove.setPincode(manager.getPin());
										urStepApprove.setUsername(manager.getUserName());
										urStepApprove.setEmail(manager.getEmail());
										urStepApprove.setEnname(manager.getEnFirstName());
										urStepApprove.setEnsurname(manager.getEnLastName());
										urStepApprove.setMobile(managerUser.getMobile());
										urStepApprove.setPhone(managerUser.getPhone());
										urStepApprove.setPincode(manager.getPin());
										urStepApprove.setCreatedBy(userCloseUr.getUsername());
										urStepApprove.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStepApprove(urStepApprove);
										logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
									}else if("3".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE ROLEIDEN ####### ");
										roleIdens = userRequestRepository.getListRoleIdenByUsertype(userType);
										logger.info("OBJ_LIST_ROLEIDEN : {} , BY USERTYPE : {}",roleIdens,idur);
										if(roleIdens.isEmpty()){
												roleIdens = userRequestRepository.getListRoleIdenBytypeZero();
												logger.info("OBJ_LIST_ROLEIDEN : {} , BY DEFAULT ",roleIdens);
										}
										if(roleIdens.isEmpty()){
											logger.error("########## BREAK ROLE IDEN NULL ############ BY USERTYPE : {}",userType);
											throw ServiceException.get500SystemError();
											//throwns roleIdens is null
										}else{  
										UrStep urStep = new UrStep();
										String urStepId=CommonUtil.generateUUID();
										urStep.setUrStepId(urStepId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType("2");
										urStep.setMinimumApprove("1");
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
											for(UserRoleIden roleIden :roleIdens){
												if(StringUtils.isNotBlank(roleIden.getEmail())&&StringUtils.isNotEmpty(roleIden.getEmail())){
													mailsTo.add(new String(roleIden.getEmail().getBytes(),"UTF-8"));
												}
											}
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
											for(UserRoleIden roleIden :roleIdens){
												UrStepApprove urStepApprove = new UrStepApprove();
												urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
												urStepApprove.setUrStepId(urStepId);
												urStepApprove.setUsername(roleIden.getUsername());
												urStepApprove.setEmail(roleIden.getEmail());
												urStepApprove.setEnname(roleIden.getEnname());
												urStepApprove.setEnsurname(roleIden.getEnsurname());
												urStepApprove.setMobile(roleIden.getMobile());
												urStepApprove.setPhone(roleIden.getPhone());
												urStepApprove.setPincode(roleIden.getPincode());
												urStepApprove.setCreatedBy(userCloseUr.getUsername());
												urStepApprove.setUpdatedBy(userCloseUr.getUsername());
												userRequestRepository.insertUrStepApprove(urStepApprove);
												logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
											}
										}
									}else if("4".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE APP_OWNER ####### ");
										Integer type_Approver_forAppOwner = userRequestRepository.getTypeApproverforAppOwner(appRoleId);
										listApproversforAppOwner = selectApproverAppownerByTypeApprover(type_Approver_forAppOwner,appRoleId);
										logger.info("OBJ_LIST_APPOWNER : {} , BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forAppOwner,appRoleId);
										if(listApproversforAppOwner.isEmpty()){
											logger.error("########## LIST_APPOWNER IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forAppOwner,appRoleId);
											throw ServiceException.get500SystemError();
											//throwns AppOwnerApprovers is null
										}else{
										Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "1");
										UrStep urStep = new UrStep();
										String urStepId=CommonUtil.generateUUID();
										urStep.setUrStepId(urStepId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType(typeApproveandminimum.get("approveType"));
										urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
											for(UserAppOwner userAppOwner :listApproversforAppOwner){
												UrStepApprove urStepApprove = new UrStepApprove();
												urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
												urStepApprove.setUrStepId(urStepId);
												urStepApprove.setUsername(userAppOwner.getUsername());
												urStepApprove.setEmail(userAppOwner.getEmail());
												urStepApprove.setEnname(userAppOwner.getEnname());
												urStepApprove.setEnsurname(userAppOwner.getEnsurname());
												urStepApprove.setMobile(userAppOwner.getMobile());
												urStepApprove.setPhone(userAppOwner.getPhone());
												urStepApprove.setPincode(userAppOwner.getPincode());
												urStepApprove.setCreatedBy(userCloseUr.getUsername());
												urStepApprove.setUpdatedBy(userCloseUr.getUsername());
												urStepApprove.setSequence(userAppOwner.getSeQuenceUser());
												urStepApprove.setTeamName(userAppOwner.getTeamName());
												urStepApprove.setTeamSequence(userAppOwner.getSeQuenceTeam());
												userRequestRepository.insertUrStepApprove(urStepApprove);
												logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
											}
										}	
									}else if("5".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE CUSTODIAN ####### ");
										Integer type_Approver_forCustodian = userRequestRepository.getTypeApproverforCustodian(appRoleId);
										listApproversforCustodian = selectApproverCustodianByType(type_Approver_forCustodian,appRoleId,userType);
										logger.info("OBJ_LIST_CUSTODIAN : {} , BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forCustodian,appRoleId);
										if(listApproversforCustodian.isEmpty()){
											logger.error("########## LIST_CUSTODIAN IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forCustodian,appRoleId);
											throw ServiceException.get500SystemError();
											//throwns CustodianApprovers is null
										}else{
										Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "2");
										UrStep urStep = new UrStep();
										String urStepId=CommonUtil.generateUUID();
										urStep.setUrStepId(urStepId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType(typeApproveandminimum.get("approveType"));
										urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
											for(UserCustodian userCustodian :listApproversforCustodian){
												UrStepApprove urStepApprove = new UrStepApprove();
												urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
												urStepApprove.setUrStepId(urStepId);
												urStepApprove.setUsername(userCustodian.getUsername());
												urStepApprove.setEmail(userCustodian.getEmail());
												urStepApprove.setEnname(userCustodian.getEnname());
												urStepApprove.setEnsurname(userCustodian.getEnsurname());
												urStepApprove.setMobile(userCustodian.getMobile());
												urStepApprove.setPhone(userCustodian.getPhone());
												urStepApprove.setPincode(userCustodian.getPincode());
												urStepApprove.setCreatedBy(userCloseUr.getUsername());
												urStepApprove.setUpdatedBy(userCloseUr.getUsername());
												urStepApprove.setSequence(userCustodian.getSeQuenceUser());
												urStepApprove.setTeamName(userCustodian.getTeamName());
												urStepApprove.setTeamSequence(userCustodian.getSeQuenceTeam());
												userRequestRepository.insertUrStepApprove(urStepApprove);
												logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
											}
										}
									}else if("6".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE CLOSE ####### ");
										UrStep urStep = new UrStep();
										String idStpId = CommonUtil.generateUUID();
										urStep.setUrStepId(idStpId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType("2");
										urStep.setMinimumApprove("1");
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
										for(Map<String,Object> datas2 : dataUserRequestValidate.getOplsbothAlreadyQueued()){
												
												if(datas2.get("appRoleId").equals(appRoleIdold)){
													User user = userRequestRepository.getUserByUserId((String) datas2.get("userId"));
													UrStepApprove urStepApprove = new UrStepApprove();
													urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
													urStepApprove.setUrStepId(idStpId);
													urStepApprove.setUsername(userCloseUr.getUsername());
													urStepApprove.setEmail(userCloseUr.getEmail());
													urStepApprove.setEnname(userCloseUr.getEnname());
													urStepApprove.setEnsurname(userCloseUr.getEnsurname());
													urStepApprove.setMobile(userCloseUr.getMobile());
													urStepApprove.setPhone(userCloseUr.getPhone());
													urStepApprove.setPincode(userCloseUr.getPincode());
													urStepApprove.setCreatedBy(userCloseUr.getUsername());
													urStepApprove.setUpdatedBy(userCloseUr.getUsername());
													userRequestRepository.insertUrStepApprove(urStepApprove);
				 									UrForUser urForUser = new UrForUser(user);
													urForUser.setUrForUserId(CommonUtil.generateUUID());
													urForUser.setUrId(idur);
													urForUser.setUrStatus("1");
													urForUser.setCreatedBy(userCloseUr.getUsername());
													urForUser.setUpdatedBy(userCloseUr.getUsername());
													userRequestRepository.insertUrforUser(urForUser);
													logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
													logger.info("OBJ_UR_FOR_USER: {} , BY UR_ID : {} , USERNAME :{}",urForUser,idur,user.getUsername());
												}
											
										}
										
									}else{
										throw ServiceException.get500SystemError();
										//thrown invalid FLOW_NODE_CODE
									}
								} //for loop map
							} // else flowIds
		
						}
						logger.info("########## END CREATE UR_ID:{} ##############",idur);
					}
				}else if("2".equals(requestByValue)){
					// GROUP
					logger.info("######### INSERT GROUP  #########");
					appRoleIdold="";
					for(final Map<String,Object> datas : dataUserRequestValidate.getOplsbothAlreadyQueued()){
						List<UserAppOwner> listApproversforAppOwner = new ArrayList<UserAppOwner>();
						List<UserCustodian> listApproversforCustodian = new ArrayList<UserCustodian>();
						List<UserRoleIden> roleIdens = new ArrayList<UserRoleIden>();
						String appRoleId = (String) datas.get("appRoleId");
						if(!appRoleIdold.equals(appRoleId)){
							appRoleIdold = (String) datas.get("appRoleId");
							final String appRoleId2 = (String) datas.get("appRoleId");
							standOrSpecialApp = userRequestRepository.getTypeStandOrSpecialApp(orgCode, appRoleIdold);
							logger.info("APPLICATION : SPECIAL=1,STANDARD=0 || {}",standOrSpecialApp);
							flowId = selectFlowIdByTypeNew(standOrSpecialApp,userType);
							logger.info("GET FLOW_ID {} , APPLICATION SPC,STD: {} || USERTYPE: {}",flowId,standOrSpecialApp,userType);
							flowIds = userRequestRepository.getListNodeFlowConfig(flowId);
							logger.info("OBJ_FLOW : {} , APP_ROLE_ID {} ",flowIds,appRoleIdold);
							UserRequestFromGrid rowData2 = (UserRequestFromGrid) CollectionUtils.find(requestFromGrids, new Predicate(){
								@Override
								public boolean evaluate(Object arg0) {
									UserRequestFromGrid queuedCheckdata = (UserRequestFromGrid)arg0;
									return queuedCheckdata.getAppRoleId().equals(appRoleId2);
								}
							});
							// InsertUr
							ur = new UR();
							idur = CommonUtil.genUrNo(idRequestNo, IndexRun++);
							ur.setUrId(idur);
							ur.setRequestNo(idRequestNo);
							ur.setAppId((String) datas.get("appId"));
							ur.setAppName((String) datas.get("appName"));
							ur.setAppRoleId((String) datas.get("appRoleId"));
							ur.setAppRoleName((String) datas.get("appRolename"));
							ur.setRequestType(requestTypeValue);
							ur.setFlowId(flowId);
							ur.setStatus("1");
							ur.setRemark((String) rowData2.getRemark());
							if("1".equals(periodtype)){
								ur.setPeriodType(periodtype);
							}else if("2".equals(periodtype)){
								ur.setPeriodType(periodtype);
								ur.setStartTime(CommonUtil.textToDate(dteStart));
								ur.setEndTime(CommonUtil.textToDate(dteTo));
							}else{
								logger.error("######### BREAK INVALID PARAMETER : PERIODTYPE {} #########",periodtype);
								throw ServiceException.get500SystemError();
								// thrown datainput invaild
							}
							List<Map<String,String>> listfilename = new ArrayList<Map<String,String>>();
							for(String namefile :rowData2.getTemfile()){
								Map<String,String> filename = new HashMap<String,String>();
								filename.put("fileName", namefile);
								filename.put("filePath","/"+idRequestNo+"/"+idur+"/"+namefile);
								listfilename.add(filename);
							}
							ur.setUrFile(CommonUtil.getGson().toJson(listfilename));
							ur.setCreatedBy(userCloseUr.getUsername());
							ur.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUr(ur);
							logger.info("MODEL UR : {}",ur);
							
							if(flowIds.isEmpty()){
								logger.error("######### BREAK CAUSE FLOW WAS NULL #########");
								throw ServiceException.get500SystemError();
								//throwns flowIds is null
							}else{
								for(Map<String, String> flow :flowIds){
									if("2".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE MANAGER ####### ");
										UrStep urStep = new UrStep();
										String urStepId=CommonUtil.generateUUID();
										urStep.setUrStepId(urStepId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType("2");
										urStep.setMinimumApprove("1");
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
											if(StringUtils.isNotBlank(manager.getEmail())&&StringUtils.isNotEmpty(manager.getEmail())){
												mailsTo.add(new String(manager.getEmail().getBytes(),"UTF-8"));
											}
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
										
										UrStepApprove urStepApprove = new UrStepApprove();
										urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
										urStepApprove.setUrStepId(urStepId);
										urStepApprove.setPincode(manager.getPin());
										urStepApprove.setUsername(manager.getUserName());
										urStepApprove.setEmail(manager.getEmail());
										urStepApprove.setEnname(manager.getEnFirstName());
										urStepApprove.setEnsurname(manager.getEnLastName());
										urStepApprove.setMobile(managerUser.getMobile());
										urStepApprove.setPhone(managerUser.getPhone());
										urStepApprove.setPincode(manager.getPin());
										urStepApprove.setCreatedBy(userCloseUr.getUsername());
										urStepApprove.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStepApprove(urStepApprove);
										logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
										
									}else if("3".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE ROLEIDEN ####### ");
										roleIdens = userRequestRepository.getListRoleIdenByUsertype(userType);
										logger.info("OBJ_LIST_ROLEIDEN : {} , BY USERTYPE : {}",roleIdens,idur);
										if(roleIdens.isEmpty()){
											roleIdens = userRequestRepository.getListRoleIdenBytypeZero();
											logger.info("OBJ_LIST_ROLEIDEN : {} , BY DEFAULT ",roleIdens);
										}
										if(roleIdens.isEmpty()){
											logger.error("########## BREAK ROLE IDEN NULL ############ BY USERTYPE : {}",userType);
											throw ServiceException.get500SystemError();
											//throwns roleIdens is null
										}else{  
										UrStep urStep = new UrStep();
										String urStepId=CommonUtil.generateUUID();
										urStep.setUrStepId(urStepId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType("2");
										urStep.setMinimumApprove("1");
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
											for(UserRoleIden roleIden :roleIdens){
												if(StringUtils.isNotBlank(roleIden.getEmail())&&StringUtils.isNotEmpty(roleIden.getEmail())){
													mailsTo.add(new String(roleIden.getEmail().getBytes(),"UTF-8"));
												}
											}
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
											for(UserRoleIden roleIden :roleIdens){
												UrStepApprove urStepApprove = new UrStepApprove();
												urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
												urStepApprove.setUrStepId(urStepId);
												urStepApprove.setUsername(roleIden.getUsername());
												urStepApprove.setEmail(roleIden.getEmail());
												urStepApprove.setEnname(roleIden.getEnname());
												urStepApprove.setEnsurname(roleIden.getEnsurname());
												urStepApprove.setMobile(roleIden.getMobile());
												urStepApprove.setPhone(roleIden.getPhone());
												urStepApprove.setPincode(roleIden.getPincode());
												urStepApprove.setCreatedBy(userCloseUr.getUsername());
												urStepApprove.setUpdatedBy(userCloseUr.getUsername());
												userRequestRepository.insertUrStepApprove(urStepApprove);
												logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
											}
										}
									}else if("4".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE APP_OWNER ####### ");
										Integer type_Approver_forAppOwner = userRequestRepository.getTypeApproverforAppOwner(appRoleId);
										listApproversforAppOwner = selectApproverAppownerByTypeApprover(type_Approver_forAppOwner,appRoleId);
										logger.info("OBJ_LIST_APPOWNER : {} , BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forAppOwner,appRoleId);
										if(listApproversforAppOwner.isEmpty()){
											logger.error("########## LIST_APPOWNER IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forAppOwner,appRoleId);
											throw ServiceException.get500SystemError();
											//throwns AppOwnerApprovers is null
										}else{
										
										Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "1");
										UrStep urStep = new UrStep();
										String urStepId=CommonUtil.generateUUID();
										urStep.setUrStepId(urStepId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType(typeApproveandminimum.get("approveType"));
										urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
											for(UserAppOwner userAppOwner :listApproversforAppOwner){
												UrStepApprove urStepApprove = new UrStepApprove();
												urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
												urStepApprove.setUrStepId(urStepId);
												urStepApprove.setUsername(userAppOwner.getUsername());
												urStepApprove.setEmail(userAppOwner.getEmail());
												urStepApprove.setEnname(userAppOwner.getEnname());
												urStepApprove.setEnsurname(userAppOwner.getEnsurname());
												urStepApprove.setMobile(userAppOwner.getMobile());
												urStepApprove.setPhone(userAppOwner.getPhone());
												urStepApprove.setPincode(userAppOwner.getPincode());
												urStepApprove.setCreatedBy(userCloseUr.getUsername());
												urStepApprove.setUpdatedBy(userCloseUr.getUsername());
												urStepApprove.setSequence(userAppOwner.getSeQuenceUser());
												urStepApprove.setTeamName(userAppOwner.getTeamName());
												urStepApprove.setTeamSequence(userAppOwner.getSeQuenceTeam());
												userRequestRepository.insertUrStepApprove(urStepApprove);
												logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
											}
										}	
									}else if("5".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE CUSTODIAN ####### ");
										Integer type_Approver_forCustodian = userRequestRepository.getTypeApproverforCustodian(appRoleId);
										listApproversforCustodian = selectApproverCustodianByType(type_Approver_forCustodian,appRoleId,userType);
										logger.info("OBJ_LIST_CUSTODIAN : {} , BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forCustodian,appRoleId);
										if(listApproversforCustodian.isEmpty()){
											logger.error("########## LIST_CUSTODIAN IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forCustodian,appRoleId);
											throw ServiceException.get500SystemError();
											//throwns CustodianApprovers is null
										}else{
										Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "2");
										UrStep urStep = new UrStep();
										String urStepId=CommonUtil.generateUUID();
										urStep.setUrStepId(urStepId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType(typeApproveandminimum.get("approveType"));
										urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
											for(UserCustodian userCustodian :listApproversforCustodian){
												UrStepApprove urStepApprove = new UrStepApprove();
												urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
												urStepApprove.setUrStepId(urStepId);
												urStepApprove.setUsername(userCustodian.getUsername());
												urStepApprove.setEmail(userCustodian.getEmail());
												urStepApprove.setEnname(userCustodian.getEnname());
												urStepApprove.setEnsurname(userCustodian.getEnsurname());
												urStepApprove.setMobile(userCustodian.getMobile());
												urStepApprove.setPhone(userCustodian.getPhone());
												urStepApprove.setPincode(userCustodian.getPincode());
												urStepApprove.setCreatedBy(userCloseUr.getUsername());
												urStepApprove.setUpdatedBy(userCloseUr.getUsername());
												urStepApprove.setSequence(userCustodian.getSeQuenceUser());
												urStepApprove.setTeamName(userCustodian.getTeamName());
												urStepApprove.setTeamSequence(userCustodian.getSeQuenceTeam());
												userRequestRepository.insertUrStepApprove(urStepApprove);
												logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
											}
										}
									}else if("6".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE CLOSE ####### ");
										UrStep urStep = new UrStep();
										String idStpId = CommonUtil.generateUUID();
										urStep.setUrStepId(idStpId);
										urStep.setUrId(idur);
										urStep.setUrStep(flow.get("flowNodeCode"));
										urStep.setSeQuence(flow.get("seQuence"));
										urStep.setApproveType("2");
										urStep.setMinimumApprove("1");
										if("1".equals(flow.get("seQuence"))){
											urStep.setStatus("1");
										}else{
											urStep.setStatus("0");
										}
										urStep.setCreatedBy(userCloseUr.getUsername());
										urStep.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStep(urStep);
										UrStepApprove urStepApprove = new UrStepApprove();
										urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
										urStepApprove.setUrStepId(idStpId);
										urStepApprove.setUsername(userCloseUr.getUsername());
										urStepApprove.setEmail(userCloseUr.getEmail());
										urStepApprove.setEnname(userCloseUr.getEnname());
										urStepApprove.setEnsurname(userCloseUr.getEnsurname());
										urStepApprove.setMobile(userCloseUr.getMobile());
										urStepApprove.setPhone(userCloseUr.getPhone());
										urStepApprove.setPincode(userCloseUr.getPincode());
										urStepApprove.setCreatedBy(userCloseUr.getUsername());
										urStepApprove.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStepApprove(urStepApprove);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
										for(Map<String,Object> datas2 : dataUserRequestValidate.getOplsbothAlreadyQueued()){
												if(datas2.get("appRoleId").equals(appRoleIdold)){
													User user = userRequestRepository.getUserByUserId((String) datas2.get("userId"));
				 									UrForUser urForUser = new UrForUser(user);
													urForUser.setUrForUserId(CommonUtil.generateUUID());
													urForUser.setUrId(idur);
													urForUser.setUrStatus("1");
													urForUser.setCreatedBy(userCloseUr.getUsername());
													urForUser.setUpdatedBy(userCloseUr.getUsername());
													userRequestRepository.insertUrforUser(urForUser);
													logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
													logger.info("OBJ_UR_FOR_USER: {} , BY UR_ID : {} , USERNAME :{}",urForUser,idur,user.getUsername());
												}
										}
										logger.info("########## END CREATE UR_ID:{} ##############",idur);
									}else{
										throw ServiceException.get500SystemError();
										//thrown invalid FLOW_NODE_CODE
									}
								} //for loop map
							} // else flowIds
						} // if primary (equal appRoleId)
					}
					
				}
				
				Context variable = generateContextVariableRequestNo(dataFormApp);
				String header ="[ACIM] User Request :"+dataFormApp.get("requestNo");
				try{
					emailUtilService.send(mailsTo, header, TEMPLATE_EMAIL_USERREQUEST, variable);
				}catch(Exception e){
					e.printStackTrace();
				}
				logger.info("############ END CREATE REQUEST NO ############### response :: {}",idRequestNo);
				return idRequestNo;
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}

	public List<UserCustodian> selectApproverCustodianByType(Integer type_Approver_forCustodian, String appRoleId,String orgType) throws ServiceException {
		// TODO Auto-generated method stub
		List<UserCustodian> users = null;
		if(type_Approver_forCustodian != null){
			 users = new ArrayList<UserCustodian>();
			if(type_Approver_forCustodian > 0 && type_Approver_forCustodian <= 3){
				users = userRequestRepository.getListCustodianByOrgtype(orgType,appRoleId);
				if(users.isEmpty()){
					users = userRequestRepository.getListCustodianByOrgtypeZero(appRoleId);
				}
				return users;
			}else if(type_Approver_forCustodian > 3 && type_Approver_forCustodian <= 5){
				users = userRequestRepository.getListCustodianTeamByOrgtype(orgType,appRoleId);
				if(users.isEmpty()){
					users = userRequestRepository.getListCustodianTeamByOrgtypeZero(appRoleId);
				}
//				users = userRequestRepository.getListAppOwnerTeamApprover(appRoleId);
				return users;
			}else{
				//type_Approver invalid value
			}
		}else{
			// thrown type_Approver is null
		}
		
		return users;
	}

	private List<UserAppOwner> selectApproverAppownerByTypeApprover(Integer type_Approver,String appRoleId){
		List<UserAppOwner> users = new ArrayList<UserAppOwner>();
		if(type_Approver!=null){
			 users = new ArrayList<UserAppOwner>();
			if(type_Approver>0&&type_Approver<=3){
				users = userRequestRepository.getListAppOwnerApprover(appRoleId);
				return users;
			}else if(type_Approver>3&&type_Approver<=5){
				users = userRequestRepository.getListAppOwnerTeamApprover(appRoleId);
				return users;
			}else{
				//type_Approver invalid value
			}
		}else{
			// thrown type_Approver is null
		}
		return users;
		
	}
	
	private String selectFlowIdByTypeNew(Integer standOrSpecialApp,String userType){
		String flowId = null;
			if(standOrSpecialApp==0)//standard app
			{
				if("1".equals(userType)){
					flowId="F001";
				}else if("2".equals(userType)){
					flowId="F002";
				}else if("3".equals(userType)){
					flowId="F003";
				}else if("4".equals(userType)){
					flowId="F004";
				}else{
					//thrown
				}
			}else if(standOrSpecialApp==1){ // special app
				
				if("1".equals(userType)){
					flowId="F005";
				}else if("2".equals(userType)){
					flowId="F006";
				}else if("3".equals(userType)){
					flowId="F007";
				}else if("4".equals(userType)){
					flowId="F008";
				}else{
					//thrown
				}
			}else{
				//thrown
			}
			
		return flowId;
	}
	
	private String selectFlowIdByTypeChg(String userType){
		String flowId = null;
			// special app
				
				if("1".equals(userType)){
					flowId="F009";
				}else if("2".equals(userType)){
					flowId="F010";
				}else if("3".equals(userType)){
					flowId="F011";
				}else if("4".equals(userType)){
					flowId="F012";
				}else{
					//thrown
				}

			
		return flowId;
	}
	
	public String selectFlowIdByTypeTer(String userType) throws ServiceException{
			// special app
			String flowId = null;
				if("1".equals(userType)){
					flowId="F013";
				}else if("2".equals(userType)){
					flowId="F014";
				}else if("3".equals(userType)){
					flowId="F015";
				}else if("4".equals(userType)){
					flowId="F016";
				}else{
					//thrown
				}

			
		return flowId;
	}
	
	@Override
	public List<Map<String, Object>> getSaveDatasNewGrid(String idRequestNo) {
		return userRequestRepository.getDatasSaveNewByidRequestNo(idRequestNo);
	}

	@Override
	public List<Map<String, String>> checkApproverSamePerson(List<String> users) {
		List<Map<String,String>> listUserNotfoundErrors = new ArrayList<Map<String,String>>();
		List<Map<String,String>> listUserfounded = new ArrayList<Map<String,String>>();
		
		List<Map<String,String>> listManagerError = new ArrayList<Map<String,String>>();
		List<Map<String,String>> listManagerfounded = new ArrayList<Map<String,String>>();
		
		List<Map<String,String>> listOrgCodeError = new ArrayList<Map<String,String>>();
		List<Map<String,String>> listOrgCodefounded = new ArrayList<Map<String,String>>();
		
		List<Map<String,String>> listOrgTypeError = new ArrayList<Map<String,String>>();
		List<Map<String,String>> listOrgTypefounded = new ArrayList<Map<String,String>>();
		
		Map<String,String> dataFirstUser = userRequestRepository.getResultCheckGroupUserByUser(users.get(0));
		
		for(String user:users){
			Map<String,String> resultUser = new HashMap<String, String>();
			Map<String,String> datas = userRequestRepository.getResultCheckGroupUserByUser(user);
			if("NULL".equals(datas.get("chkUser"))){
				resultUser.put("userName", datas.get("userName"));
				resultUser.put("detail", "");
				resultUser.put("reason", "ไม่พบข้อมูล user กรุณาอัพโหลดไฟล์ใหม่");
				listUserNotfoundErrors.add(resultUser);
			}else{
				resultUser.put("userName", datas.get("userName"));
				resultUser.put("detail", datas.get("CHKUSER")); // enname + lastname
				listUserfounded.add(resultUser);
			}
		}
		if(listUserNotfoundErrors.size()!=0){
			listUserNotfoundErrors.addAll(listUserfounded);
			return listUserNotfoundErrors;
		}else{
			for(String user:users){
				Map<String,String> resultUser = new HashMap<String, String>();
				Map<String,String> datas = userRequestRepository.getResultCheckGroupUserByUser(user);
				 if("NULL".equals(datas.get("chkOrgCode"))){
					 resultUser.put("userName", datas.get("userName"));
					 resultUser.put("detail", "");
					 resultUser.put("reason", "Organization ของ user ไม่ตรงกัน กรุณาอัพโหลดไฟล์ใหม่");
						listOrgCodeError.add(resultUser);
				 }else if(!dataFirstUser.get("chkOrgCode").equals(datas.get("chkOrgCode"))){
					 resultUser.put("userName", datas.get("userName"));
					 resultUser.put("detail",datas.get("chkOrgCode"));
					 resultUser.put("reason", "Organization ของ user ไม่ตรงกัน กรุณาอัพโหลดไฟล์ใหม่");
						listOrgCodeError.add(resultUser);
				 }else{
					resultUser.put("userName", datas.get("userName"));
					resultUser.put("detail",datas.get("chkOrgCode")); // OrgDesctption
					listOrgCodefounded.add(resultUser);
				 }
			}
		}
		if(listOrgCodeError.size()!=0){
			listOrgCodeError.addAll(listOrgCodefounded);
			return listOrgCodeError;
		}else{
			for(String user:users){
				Map<String,String> resultUser = new HashMap<String, String>();
				Map<String,String> datas = userRequestRepository.getResultCheckGroupUserByUser(user);
				if("NULL".equals(datas.get("chkOrgtype"))){
					resultUser.put("userName", datas.get("userName"));
					resultUser.put("detail", "");
					resultUser.put("reason", "User Type ของ user ไม่ตรงกัน กรุณาอัพโหลดไฟล์ใหม่ ");
					listOrgTypeError.add(resultUser);
				}else if(!dataFirstUser.get("chkOrgtype").equals(datas.get("chkOrgtype"))){
					 resultUser.put("userName", datas.get("userName"));
					 resultUser.put("detail", datas.get("chkOrgtype")); //UR TYPE
					 resultUser.put("reason", "User Type ของ user ไม่ตรงกัน กรุณาอัพโหลดไฟล์ใหม่ ");
					 listOrgTypeError.add(resultUser);
				 }else{
					resultUser.put("userName", datas.get("userName"));
					resultUser.put("detail",datas.get("chkOrgtype")); //OrgDesc
					listOrgTypefounded.add(resultUser);
				 }
			}
		}
		if(listOrgTypeError.size()!=0){
			listOrgTypeError.addAll(listOrgTypefounded);
			return listOrgTypeError;
		}else{
			try {
				String pinCodeFirst = userRequestRepository.getPincodeByUserIdorUsername(null,users.get(0));
				EmployeeProfile approverFirst = omWebService.getApproverProfile(pinCodeFirst);
				for(String user:users){
					Map<String,String> resultUser = new HashMap<String, String>();
					String pinCode = userRequestRepository.getPincodeByUserIdorUsername(null,user);
					EmployeeProfile approver = omWebService.getApproverProfile(pinCode);
					if(approver==null){
						resultUser.put("userName", user);
						resultUser.put("detail", "-");
						resultUser.put("reason", "Approve Manager ของ user ไม่ตรงกัน กรุณาอัพโหลดไฟล์ใหม่");
						listManagerError.add(resultUser);
					}else{
						if(!approverFirst.getPin().equals(approver.getPin())){
							resultUser.put("userName", user);
							resultUser.put("detail", approver.getEnFirstName() + " "+approver.getEnLastName());
							resultUser.put("reason", "Approve Manager ของ user ไม่ตรงกัน กรุณาอัพโหลดไฟล์ใหม่");
							listManagerError.add(resultUser);
						}else{
							resultUser.put("userName", user);
							resultUser.put("detail",approver.getEnFirstName() + " "+approver.getEnLastName());
							listManagerfounded.add(resultUser);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(listManagerError.size()!=0){
			listManagerError.addAll(listManagerfounded);
			return listManagerError;
		}else{
			List<Map<String,String>> userList = new ArrayList<Map<String,String>>();
			for(String user:users){
				userList.add(userRequestRepository.getUserByUsername2(user));
			}
			return userList;
		}
	}

	@Override
	public List<Map<String, String>> listAppChangeByappName(
			Map<String, String> request) {
		// TODO Auto-generated method stub
		return userRequestRepository.getlistAppChangeByappName(request);
	}

	@Override
	public BaseGridResponse listAppChangeGridByappId(Map<String, Object> request) {
		BaseGridResponse gridResponse = new BaseGridResponse();
		Integer page = Integer.parseInt(request.get("page").toString()) ;
		Integer size = Integer.parseInt(request.get("rows").toString());
		int startRow = ((page - 1) * size) + 1;
		int endRow = 0;
//		Integer totalRecord = userRequestRepository.getCountlistAppChangeByappName(request);
		Integer totalRecord = 0;
		if (totalRecord == size) {
			endRow = totalRecord;
		} else {
			endRow = (startRow + size) - 1;
		}
		request.put("startRow", startRow);
		request.put("endRow", endRow);
		List<Map<String,Object>> listDatas = new ArrayList<Map<String,Object>>();
		listDatas = userRequestRepository.getlistAppChangeByAppId(request);
		gridResponse.setPage(page);
		gridResponse.setRows(listDatas);
		gridResponse.setRecords(totalRecord);
		int total = 0;
		if (totalRecord % size == 0) {
			total = totalRecord / size;
		} else {
			total = (totalRecord / size) + 1;
		}
		gridResponse.setTotal(total);
		
		return gridResponse;
	}

	@Override
	public List<Map<String, Object>> listChgAppComboBox(
			Map<String, Object> request) {
		String appRoleId = (String) request.get("appRoleId");
		String type = (String) request.get("type");
		String username = (String) request.get("username");
		return userRequestRepository.getlistChgAppComboBox(appRoleId,username,type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String subMitforCommitdataChg(DataUserRequestValidate dataUserRequestValidate , Map<String,String> dataFormApp,List<Map<String, Object>> listDataChg ) throws ServiceException {
		logger.info("############ START CHANGE REQUEST NO ###############");
		Integer countError=0;
		if(!dataUserRequestValidate.getErrorsbothAlreadyQueued().isEmpty()){
			countError = dataUserRequestValidate.getErrorsbothAlreadyQueued().size();
		}
		Integer countUrAll = dataUserRequestValidate.getLsapp().size()*dataUserRequestValidate.getLsuser().size(); 
		if ((countUrAll-countError)<=0){
			logger.error("############ BREAK CHANGE REQUEST NO ############### : ALL_UR - URERROR {}",countUrAll-countError);
			throw ServiceException.get500SystemError();
			//thrown
		}
//		if(){ 
//			
//		} if Model is null  and manager isn't same the person then thrown. this get model Manager if manager is same the person
		String usertypeValue = dataFormApp.get("usertypeValue");
		String requestByValue = dataFormApp.get("requestByValue");
		String requestTypeValue = dataFormApp.get("requestTypeValue");
		String subject = dataFormApp.get("subject");
		String detail = dataFormApp.get("detail");
		String periodtype = dataFormApp.get("periodtype");
		String dteStart = dataFormApp.get("dteStart");
		String dteTo = dataFormApp.get("dteTo");
		final String chgRole = dataFormApp.get("CH1R1");
		String chgPeriod = dataFormApp.get("CH1P1");
		String remarkError=null;
		if(dataFormApp.get("queued")!=null){
			 remarkError = dataFormApp.get("queued");
		}
		Set<String> mailsTo = new HashSet<String>();
		final String userIdfirst = (String)dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("userId");
		User userCloseUr = userRequestRepository.getUserByUserId(userIdfirst);
		User firstUser= userCloseUr;
		EmployeeProfile manager;
		User managerUser;
		try {
			manager = omWebService.getApproverProfile(firstUser.getPincode());
			managerUser = userRequestRepository.getUserByUsername(manager.getUserName());
		} catch (Exception e1) {
			logger.error("############ BREAK CHANGE REQUEST NO ############### : PROBLEM CALL SERVICE GET MANAGER APPROVER AT USERFIRST : {} ",firstUser.getPincode());
			throw ServiceException.get500SystemError(e1);
		} // service Om
		
		// Step InsertUserRequestNo
		RequestNo requestNo = new RequestNo();
		Integer sequence = userRequestRepository.getSequenceRequestNo();
		String idRequestNo = CommonUtil.genRequestNo(sequence);
		dataFormApp.put("requestNo",idRequestNo);
		Integer IndexRun=1;
		requestNo.setRequestNo(idRequestNo);
		requestNo.setUrType(usertypeValue);
		requestNo.setRequestList(requestByValue);
		requestNo.setRequestType(requestTypeValue);
		requestNo.setSubject(subject);
		requestNo.setDetail(detail);
		if(chgRole.equals("CH1R1")&&chgPeriod.equals("CH1P1")){
			List<String> changtype = new ArrayList<String>();
			changtype.add("CH1R1");
			changtype.add("CH1P1");
			requestNo.setChangeType(CommonUtil.getGson().toJson(changtype));
		}else if(chgRole.equals("CH1R1")){
			List<String> changtype = new ArrayList<String>();
			changtype.add("CH1R1");
			requestNo.setChangeType(CommonUtil.getGson().toJson(changtype));
		}else if(chgPeriod.equals("CH1P1")){
			List<String> changtype = new ArrayList<String>();
			changtype.add("CH1P1");
			requestNo.setChangeType(CommonUtil.getGson().toJson(changtype));
		}
		requestNo.setRequestBy(userCloseUr.getPincode());  
		requestNo.setUsername(userCloseUr.getUsername());
		requestNo.setEnname(userCloseUr.getEnname());
		requestNo.setEnsurname(userCloseUr.getEnsurname());
		requestNo.setEmail(userCloseUr.getEmail());
		requestNo.setMobile(userCloseUr.getMobile());
		requestNo.setPhone(userCloseUr.getPhone());
		requestNo.setPosition(userCloseUr.getPosition());
		requestNo.setOrganize(userCloseUr.getOrgcode());
		requestNo.setCompany(userCloseUr.getCocode());
		requestNo.setRequestRemark(remarkError);
		requestNo.setCreatedBy(userCloseUr.getUsername());
		requestNo.setUpdatedBy(userCloseUr.getUsername());
		userRequestRepository.insertRequestNo(requestNo);
		logger.info("CHANGE REQUEST NO : {} || {}",idRequestNo,requestNo);
		logger.info("######### STARTING TERMINATE UR #########");
		String orgCode = userRequestRepository.getOrgCodeByUserId(userIdfirst);
		String appRoleIdOld = (String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appRoleId");
		logger.info("GET  ORGCODE BY USER_ID: {} || {}",orgCode,dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("userId"));
		final String appRoleIdfirst = appRoleIdOld;
		String userType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(orgCode);
		logger.info("GET  USERTYPE BY ORGCODE: {} || {}",userType,orgCode);
		String flowId = selectFlowIdByTypeChg(userType);
		logger.info("GET FLOW_ID {} , APPLICATION SPC,STD: {} || USERTYPE: {}",flowId,userType);
		List<Map<String, String>> flowIds = userRequestRepository.getListNodeFlowConfig(flowId);
		logger.info("OBJ_FLOW : {} , APP_ROLE_ID {} ",flowIds,appRoleIdOld);
		
		Map<String, Object> detailChg = (Map<String, Object>) CollectionUtils.find(listDataChg, new Predicate(){
			@Override
			public boolean evaluate(Object arg0) {
				Map<String, Object> queuedCheckdata = (Map<String, Object>)arg0;
				return queuedCheckdata.get("appRoleIdOld").equals(appRoleIdfirst);
			}
			
		});
		
		// InsertUr
		UR ur = new UR();
		String idur = CommonUtil.genUrNo(idRequestNo, IndexRun++);
		ur.setUrId(idur);
		ur.setRequestNo(idRequestNo);
		ur.setAppId((String) detailChg.get("appId"));
		ur.setAppName((String)detailChg.get("application"));
		if(chgRole.equals("CH1R1")&&chgPeriod.equals("CH1P1")){
			ur.setAppRoleId((String)detailChg.get("appRoleIdNew"));
			ur.setAppRoleName((String)detailChg.get("roleNewapplication"));
			ur.setRolePastId((String)detailChg.get("appRoleIdOld"));
			ur.setRolePast((String)detailChg.get("roleOldapplication"));
			if("1".equals(periodtype)){
				ur.setPeriodType(periodtype);
			}else if("2".equals(periodtype)){
				ur.setPeriodType(periodtype);
				ur.setStartTime(CommonUtil.textToDate(dteStart));
				ur.setEndTime(CommonUtil.textToDate(dteTo));
			}else{
				logger.error("######### BREAK INVALID PARAMETER : PERIODTYPE {} #########",periodtype);
				throw ServiceException.get500SystemError();
			}
		}else if(chgRole.equals("CH1R1")){
			ur.setAppRoleId((String) detailChg.get("appRoleIdNew"));
			ur.setAppRoleName((String) detailChg.get("roleNewapplication"));
			ur.setRolePastId((String) detailChg.get("appRoleIdOld"));
			ur.setRolePast((String) detailChg.get("roleOldapplication"));
			UserAppRole userAppRole = userAppRoleRepository.selectUserAppRoleByAppRoleIdAndUserId((String) detailChg.get("appRoleIdOld"), userIdfirst);
			ur.setPeriodType(userAppRole.getPeriodType());
			ur.setStartTime(userAppRole.getStartTime());
			ur.setEndTime(userAppRole.getEndTime());
		}else if(chgPeriod.equals("CH1P1")){
			ur.setAppRoleId((String) detailChg.get("appRoleIdOld"));
			ur.setAppRoleName((String) detailChg.get("roleOldapplication"));
			ur.setRolePastId((String) detailChg.get("appRoleIdOld"));
			ur.setRolePast((String) detailChg.get("roleOldapplication"));
			if("1".equals(periodtype)){
				ur.setPeriodType(periodtype);
			}else if("2".equals(periodtype)){
				ur.setPeriodType(periodtype);
				ur.setStartTime(CommonUtil.textToDate(dteStart));
				ur.setEndTime(CommonUtil.textToDate(dteTo));
			}else{
				logger.error("######### BREAK INVALID PARAMETER : PERIODTYPE {} #########",periodtype);
				throw ServiceException.get500SystemError();
			}
		}else if("".equals(chgPeriod)&&"".equals(chgRole)){
			ur.setAppRoleId((String) detailChg.get("appRoleIdOld"));
			ur.setAppRoleName((String) detailChg.get("roleOldapplication"));
			ur.setRolePastId((String) detailChg.get("appRoleIdOld"));
			ur.setRolePast((String) detailChg.get("roleOldapplication"));
			UserAppRole userAppRole = userAppRoleRepository.selectUserAppRoleByAppRoleIdAndUserId((String) detailChg.get("appRoleIdOld"),userIdfirst);
			ur.setPeriodType(userAppRole.getPeriodType());
			ur.setStartTime(userAppRole.getStartTime());
			ur.setEndTime(userAppRole.getEndTime());
		}
		ur.setRequestType(requestTypeValue);
		ur.setFlowId(flowId);
		ur.setStatus("1");
		ur.setRemark(detailChg.get("remark").toString());
		ur.setCreatedBy(userCloseUr.getUsername());
		ur.setUpdatedBy(userCloseUr.getUsername());
		userRequestRepository.insertUr(ur);
		logger.info("MODEL UR : {}",ur);
		
		for(final Map<String,Object> datas : dataUserRequestValidate.getOplsbothAlreadyQueued()){
			List<UserAppOwner> listApproversforAppOwner = new ArrayList<UserAppOwner>();
			List<UserCustodian> listApproversforCustodian = new ArrayList<UserCustodian>();
			List<UserRoleIden> roleIdens = new ArrayList<UserRoleIden>();
			final String appRoleId = (String) datas.get("appRoleId");
			if(appRoleIdOld.equals(appRoleId)){
				if(flowIds.isEmpty()){
					logger.error("######### BREAK CAUSE FLOW WAS NULL #########");
					throw ServiceException.get500SystemError();
					//throwns flowIds is null
				}else{
					for(Map<String, String> flow :flowIds){
						if("2".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE MANAGER ####### ");
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType("2");
							urStep.setMinimumApprove("1");
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
								if(StringUtils.isNotBlank(manager.getEmail())&&StringUtils.isNotEmpty(manager.getEmail())){
									try {
										mailsTo.add(new String(manager.getEmail().getBytes(),"UTF-8"));
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
								}
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
							UrStepApprove urStepApprove = new UrStepApprove();
							urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
							urStepApprove.setUrStepId(urStepId);
							urStepApprove.setPincode(manager.getPin());
							urStepApprove.setUsername(manager.getUserName());
							urStepApprove.setEmail(manager.getEmail());
							urStepApprove.setEnname(manager.getEnFirstName());
							urStepApprove.setEnsurname(manager.getEnLastName());
							urStepApprove.setMobile(managerUser.getMobile());
							urStepApprove.setPhone(managerUser.getPhone());
							urStepApprove.setPincode(manager.getPin());
							urStepApprove.setCreatedBy(userCloseUr.getUsername());
							urStepApprove.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStepApprove(urStepApprove);
							logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
						}else if("3".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE ROLEIDEN ####### ");
							roleIdens = userRequestRepository.getListRoleIdenByUsertype(userType);
							logger.info("OBJ_LIST_ROLEIDEN : {} , BY USERTYPE : {}",roleIdens,idur);
							if(roleIdens.isEmpty()){
								roleIdens = userRequestRepository.getListRoleIdenBytypeZero();
								logger.info("OBJ_LIST_ROLEIDEN : {} , BY DEFAULT ",roleIdens);
							}
							if(roleIdens.isEmpty()){
								logger.error("########## BREAK ROLE IDEN NULL ############ BY USERTYPE : {}",userType);
								throw ServiceException.get500SystemError();
								//throwns roleIdens is null
							}else{  
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType("2");
							urStep.setMinimumApprove("1");
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
								for(UserRoleIden roleIden :roleIdens){
									if(StringUtils.isNotBlank(roleIden.getEmail())&&StringUtils.isNotEmpty(roleIden.getEmail())){
										try {
											mailsTo.add(new String(roleIden.getEmail().getBytes(),"UTF-8"));
										} catch (UnsupportedEncodingException e) {
											e.printStackTrace();
										}
									}
								}
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
								for(UserRoleIden roleIden :roleIdens){
									UrStepApprove urStepApprove = new UrStepApprove();
									urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
									urStepApprove.setUrStepId(urStepId);
									urStepApprove.setUsername(roleIden.getUsername());
									urStepApprove.setEmail(roleIden.getEmail());
									urStepApprove.setEnname(roleIden.getEnname());
									urStepApprove.setEnsurname(roleIden.getEnsurname());
									urStepApprove.setMobile(roleIden.getMobile());
									urStepApprove.setPhone(roleIden.getPhone());
									urStepApprove.setPincode(roleIden.getPincode());
									urStepApprove.setCreatedBy(userCloseUr.getUsername());
									urStepApprove.setUpdatedBy(userCloseUr.getUsername());
									userRequestRepository.insertUrStepApprove(urStepApprove);
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
								}
							}
						}else if("4".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE APP_OWNER ####### ");
							Integer type_Approver_forAppOwner = userRequestRepository.getTypeApproverforAppOwner(appRoleId);
							listApproversforAppOwner = selectApproverAppownerByTypeApprover(type_Approver_forAppOwner,appRoleId);
							logger.info("OBJ_LIST_APPOWNER : {} , BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forAppOwner,appRoleId);
							if(listApproversforAppOwner.isEmpty()){
								logger.error("########## LIST_APPOWNER IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forAppOwner,appRoleId);
								throw ServiceException.get500SystemError();
							}else{
							
							Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "1");
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType(typeApproveandminimum.get("approveType"));
							urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
								for(UserAppOwner userAppOwner :listApproversforAppOwner){
									UrStepApprove urStepApprove = new UrStepApprove();
									urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
									urStepApprove.setUrStepId(urStepId);
									urStepApprove.setUsername(userAppOwner.getUsername());
									urStepApprove.setEmail(userAppOwner.getEmail());
									urStepApprove.setEnname(userAppOwner.getEnname());
									urStepApprove.setEnsurname(userAppOwner.getEnsurname());
									urStepApprove.setMobile(userAppOwner.getMobile());
									urStepApprove.setPhone(userAppOwner.getPhone());
									urStepApprove.setPincode(userAppOwner.getPincode());
									urStepApprove.setCreatedBy(userCloseUr.getUsername());
									urStepApprove.setUpdatedBy(userCloseUr.getUsername());
									urStepApprove.setSequence(userAppOwner.getSeQuenceUser());
									urStepApprove.setTeamName(userAppOwner.getTeamName());
									urStepApprove.setTeamSequence(userAppOwner.getSeQuenceTeam());
									userRequestRepository.insertUrStepApprove(urStepApprove);
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
								}
							}	
						}else if("5".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE CUSTODIAN ####### ");
							Integer type_Approver_forCustodian = userRequestRepository.getTypeApproverforCustodian(appRoleId);
							listApproversforCustodian = selectApproverCustodianByType(type_Approver_forCustodian,appRoleId,userType);
							logger.info("OBJ_LIST_CUSTODIAN : {} , BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forCustodian,appRoleId);
							if(listApproversforCustodian.isEmpty()){
								logger.error("########## LIST_CUSTODIAN IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forCustodian,appRoleId);
								throw ServiceException.get500SystemError();
							}else{
							Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "2");
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType(typeApproveandminimum.get("approveType"));
							urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
								for(UserCustodian userCustodian :listApproversforCustodian){
									UrStepApprove urStepApprove = new UrStepApprove();
									urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
									urStepApprove.setUrStepId(urStepId);
									urStepApprove.setUsername(userCustodian.getUsername());
									urStepApprove.setEmail(userCustodian.getEmail());
									urStepApprove.setEnname(userCustodian.getEnname());
									urStepApprove.setEnsurname(userCustodian.getEnsurname());
									urStepApprove.setMobile(userCustodian.getMobile());
									urStepApprove.setPhone(userCustodian.getPhone());
									urStepApprove.setPincode(userCustodian.getPincode());
									urStepApprove.setCreatedBy(userCloseUr.getUsername());
									urStepApprove.setUpdatedBy(userCloseUr.getUsername());
									urStepApprove.setSequence(userCustodian.getSeQuenceUser());
									urStepApprove.setTeamName(userCustodian.getTeamName());
									urStepApprove.setTeamSequence(userCustodian.getSeQuenceTeam());
									userRequestRepository.insertUrStepApprove(urStepApprove);
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
								}
							}
						}else if("6".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE CLOSE ####### ");
							UrStep urStep = new UrStep();
							String idStpId = CommonUtil.generateUUID();
							urStep.setUrStepId(idStpId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType("2");
							urStep.setMinimumApprove("1");
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
							for(Map<String,Object> datas2 : dataUserRequestValidate.getOplsbothAlreadyQueued()){
									
									if(datas2.get("appRoleId").equals(appRoleIdOld)){
										UrStepApprove urStepApprove = new UrStepApprove();
										urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
										urStepApprove.setUrStepId(idStpId);
										urStepApprove.setUsername(userCloseUr.getUsername());
										urStepApprove.setEmail(userCloseUr.getEmail());
										urStepApprove.setEnname(userCloseUr.getEnname());
										urStepApprove.setEnsurname(userCloseUr.getEnsurname());
										urStepApprove.setMobile(userCloseUr.getMobile());
										urStepApprove.setPhone(userCloseUr.getPhone());
										urStepApprove.setPincode(userCloseUr.getPincode());
										urStepApprove.setCreatedBy(userCloseUr.getUsername());
										urStepApprove.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStepApprove(urStepApprove);
	 									UrForUser urForUser = new UrForUser(firstUser);
										urForUser.setUrForUserId(CommonUtil.generateUUID());
										urForUser.setUrId(idur);
										urForUser.setUrStatus("1");
										urForUser.setCreatedBy(userCloseUr.getUsername());
										urForUser.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrforUser(urForUser);
										logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
										logger.info("OBJ_UR_FOR_USER: {} , BY UR_ID : {} , USERNAME :{}",urForUser,idur,firstUser.getUsername());
									}
								
							}
							
						}else{
							throw ServiceException.get500SystemError();
						}
						
			
					} //for loop map
				} // else flowIds
			}else{
				logger.info("########## END CHANGE UR_ID:{} ##############",idur);
				
				logger.info("######### STARTING CHANGE UR #########");
				appRoleIdOld = (String) datas.get("appRoleId");
				userType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(orgCode);
				logger.info("GET  USERTYPE BY ORGCODE: {} || {}",userType,orgCode);
				final String appRoleIdOld2 = appRoleIdOld;
				Map<String, Object> detailChg2 = (Map<String, Object>) CollectionUtils.find(listDataChg, new Predicate(){
					@Override
					public boolean evaluate(Object arg0) {
						Map<String, Object> queuedCheckdata = (Map<String, Object>)arg0;
						return queuedCheckdata.get("appRoleIdOld").equals(appRoleIdOld2);
					}
				});
				// InsertUr
				ur = new UR();
				idur = CommonUtil.genUrNo(idRequestNo, IndexRun++);
				ur.setUrId(idur);
				ur.setRequestNo(idRequestNo);
				ur.setAppId((String) detailChg2.get("appId"));
				ur.setAppName((String)detailChg2.get("application"));
				if(chgRole.equals("CH1R1")&&chgPeriod.equals("CH1P1")){
					ur.setAppRoleId((String)detailChg2.get("appRoleIdNew"));
					ur.setAppRoleName((String)detailChg2.get("roleNewapplication"));
					ur.setRolePastId((String)detailChg2.get("appRoleIdOld"));
					ur.setRolePast((String)detailChg2.get("roleOldapplication"));
					if("1".equals(periodtype)){
						ur.setPeriodType(periodtype);
					}else if("2".equals(periodtype)){
						ur.setPeriodType(periodtype);
						ur.setStartTime(CommonUtil.textToDate(dteStart));
						ur.setEndTime(CommonUtil.textToDate(dteTo));
					}else{
						logger.error("######### BREAK INVALID PARAMETER : PERIODTYPE {} #########",periodtype);
						throw ServiceException.get500SystemError();
					}
				}else if(chgRole.equals("CH1R1")){
					ur.setAppRoleId((String) detailChg2.get("appRoleIdNew"));
					ur.setAppRoleName((String) detailChg2.get("roleNewapplication"));
					ur.setRolePastId((String) detailChg2.get("appRoleIdOld"));
					ur.setRolePast((String) detailChg2.get("roleOldapplication"));
					UserAppRole userAppRole = userAppRoleRepository.selectUserAppRoleByAppRoleIdAndUserId((String) detailChg2.get("appRoleIdOld"), userIdfirst);
					ur.setPeriodType(userAppRole.getPeriodType());
					ur.setStartTime(userAppRole.getStartTime());
					ur.setEndTime(userAppRole.getEndTime());
				}else if(chgPeriod.equals("CH1P1")){
					ur.setAppRoleId((String) detailChg2.get("appRoleIdOld"));
					ur.setAppRoleName((String) detailChg2.get("roleOldapplication"));
					ur.setRolePastId((String) detailChg2.get("appRoleIdOld"));
					ur.setRolePast((String) detailChg2.get("roleOldapplication"));
					if("1".equals(periodtype)){
						ur.setPeriodType(periodtype);
					}else if("2".equals(periodtype)){
						ur.setPeriodType(periodtype);
						ur.setStartTime(CommonUtil.textToDate(dteStart));
						ur.setEndTime(CommonUtil.textToDate(dteTo));
					}else{
						logger.error("######### BREAK INVALID PARAMETER : PERIODTYPE {} #########",periodtype);
						throw ServiceException.get500SystemError();
					}
				}else if("".equals(chgPeriod)&&"".equals(chgRole)){
					ur.setAppRoleId((String) detailChg2.get("appRoleIdOld"));
					ur.setAppRoleName((String) detailChg2.get("roleOldapplication"));
					ur.setRolePastId((String) detailChg2.get("appRoleIdOld"));
					ur.setRolePast((String) detailChg2.get("roleOldapplication"));
					UserAppRole userAppRole = userAppRoleRepository.selectUserAppRoleByAppRoleIdAndUserId((String) detailChg2.get("appRoleIdOld"),userIdfirst);
					ur.setPeriodType(userAppRole.getPeriodType());
					ur.setStartTime(userAppRole.getStartTime());
					ur.setEndTime(userAppRole.getEndTime());
				}
				ur.setRequestType(requestTypeValue);
				ur.setFlowId(flowId);
				ur.setStatus("1");
				ur.setRemark(detailChg.get("remark").toString());
				ur.setCreatedBy(userCloseUr.getUsername());
				ur.setUpdatedBy(userCloseUr.getUsername());
				userRequestRepository.insertUr(ur);
				logger.info("MODEL UR : {}",ur);
				if(flowIds.isEmpty()){
					logger.info("OBJ_FLOW : {} , APP_ROLE_ID {} ",flowIds);
					throw ServiceException.get500SystemError();
				}else{
					for(Map<String, String> flow :flowIds){
						if("2".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE MANAGER ####### ");
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType("2");
							urStep.setMinimumApprove("1");
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
								if(StringUtils.isNotBlank(manager.getEmail())&&StringUtils.isNotEmpty(manager.getEmail())){
									try {
										mailsTo.add(new String(manager.getEmail().getBytes(),"UTF-8"));
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
								}
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
							UrStepApprove urStepApprove = new UrStepApprove();
							urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
							urStepApprove.setUrStepId(urStepId);
							urStepApprove.setPincode(manager.getPin());
							urStepApprove.setUsername(manager.getUserName());
							urStepApprove.setEmail(manager.getEmail());
							urStepApprove.setEnname(manager.getEnFirstName());
							urStepApprove.setEnsurname(manager.getEnLastName());
							urStepApprove.setMobile(managerUser.getMobile());
							urStepApprove.setPhone(managerUser.getPhone());
							urStepApprove.setPincode(manager.getPin());
							urStepApprove.setCreatedBy(userCloseUr.getUsername());
							urStepApprove.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStepApprove(urStepApprove);
							logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
						}else if("3".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE ROLEIDEN ####### ");
							roleIdens = userRequestRepository.getListRoleIdenByUsertype(userType);
							logger.info("OBJ_LIST_ROLEIDEN : {} , BY USERTYPE : {}",roleIdens,idur);
							if(roleIdens.isEmpty()){
								roleIdens = userRequestRepository.getListRoleIdenBytypeZero();
								logger.info("OBJ_LIST_ROLEIDEN : {} , BY DEFAULT ",roleIdens);
							}
							if(roleIdens.isEmpty()){
								logger.error("########## BREAK ROLE IDEN NULL ############ BY USERTYPE : {}",userType);
								throw ServiceException.get500SystemError();
							}else{  
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType("2");
							urStep.setMinimumApprove("1");
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
								for(UserRoleIden roleIden :roleIdens){
									if(StringUtils.isNotBlank(roleIden.getEmail())&&StringUtils.isNotEmpty(roleIden.getEmail())){
										try {
											mailsTo.add(new String(roleIden.getEmail().getBytes(),"UTF-8"));
										} catch (UnsupportedEncodingException e) {
											e.printStackTrace();
										}
									}
								}
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
								for(UserRoleIden roleIden :roleIdens){
									UrStepApprove urStepApprove = new UrStepApprove();
									urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
									urStepApprove.setUrStepId(urStepId);
									urStepApprove.setUsername(roleIden.getUsername());
									urStepApprove.setEmail(roleIden.getEmail());
									urStepApprove.setEnname(roleIden.getEnname());
									urStepApprove.setEnsurname(roleIden.getEnsurname());
									urStepApprove.setMobile(roleIden.getMobile());
									urStepApprove.setPhone(roleIden.getPhone());
									urStepApprove.setPincode(roleIden.getPincode());
									urStepApprove.setCreatedBy(userCloseUr.getUsername());
									urStepApprove.setUpdatedBy(userCloseUr.getUsername());
									userRequestRepository.insertUrStepApprove(urStepApprove);
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
								}
							}
						}else if("4".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE APP_OWNER ####### ");
							Integer type_Approver_forAppOwner = userRequestRepository.getTypeApproverforAppOwner(appRoleId);
							listApproversforAppOwner = selectApproverAppownerByTypeApprover(type_Approver_forAppOwner,appRoleId);
							logger.info("OBJ_LIST_APPOWNER : {} , BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forAppOwner,appRoleId);
							if(listApproversforAppOwner.isEmpty()){
								logger.error("########## LIST_APPOWNER IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forAppOwner,appRoleId);
								throw ServiceException.get500SystemError();
							}else{
							
							Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "1");
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType(typeApproveandminimum.get("approveType"));
							urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
								for(UserAppOwner userAppOwner :listApproversforAppOwner){
									UrStepApprove urStepApprove = new UrStepApprove();
									urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
									urStepApprove.setUrStepId(urStepId);
									urStepApprove.setUsername(userAppOwner.getUsername());
									urStepApprove.setEmail(userAppOwner.getEmail());
									urStepApprove.setEnname(userAppOwner.getEnname());
									urStepApprove.setEnsurname(userAppOwner.getEnsurname());
									urStepApprove.setMobile(userAppOwner.getMobile());
									urStepApprove.setPhone(userAppOwner.getPhone());
									urStepApprove.setPincode(userAppOwner.getPincode());
									urStepApprove.setCreatedBy(userCloseUr.getUsername());
									urStepApprove.setUpdatedBy(userCloseUr.getUsername());
									urStepApprove.setSequence(userAppOwner.getSeQuenceUser());
									urStepApprove.setTeamName(userAppOwner.getTeamName());
									urStepApprove.setTeamSequence(userAppOwner.getSeQuenceTeam());
									userRequestRepository.insertUrStepApprove(urStepApprove);
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
								}
							}	
						}else if("5".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE CUSTODIAN ####### ");
							Integer type_Approver_forCustodian = userRequestRepository.getTypeApproverforCustodian(appRoleId);
							listApproversforCustodian = selectApproverCustodianByType(type_Approver_forCustodian,appRoleId,userType);
							logger.info("OBJ_LIST_CUSTODIAN : {} , BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forCustodian,appRoleId);
							if(listApproversforCustodian.isEmpty()){
								logger.error("########## LIST_CUSTODIAN IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forCustodian,appRoleId);
								throw ServiceException.get500SystemError();
							}else{
							Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "2");
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType(typeApproveandminimum.get("approveType"));
							urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
								for(UserCustodian userCustodian :listApproversforCustodian){
									UrStepApprove urStepApprove = new UrStepApprove();
									urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
									urStepApprove.setUrStepId(urStepId);
									urStepApprove.setUsername(userCustodian.getUsername());
									urStepApprove.setEmail(userCustodian.getEmail());
									urStepApprove.setEnname(userCustodian.getEnname());
									urStepApprove.setEnsurname(userCustodian.getEnsurname());
									urStepApprove.setMobile(userCustodian.getMobile());
									urStepApprove.setPhone(userCustodian.getPhone());
									urStepApprove.setPincode(userCustodian.getPincode());
									urStepApprove.setCreatedBy(userCloseUr.getUsername());
									urStepApprove.setUpdatedBy(userCloseUr.getUsername());
									urStepApprove.setSequence(userCustodian.getSeQuenceUser());
									urStepApprove.setTeamName(userCustodian.getTeamName());
									urStepApprove.setTeamSequence(userCustodian.getSeQuenceTeam());
									userRequestRepository.insertUrStepApprove(urStepApprove);
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
								}
							}
						}else if("6".equals(flow.get("flowNodeCode"))){

							logger.info("######## @NODE CLOSE ####### ");
							UrStep urStep = new UrStep();
							String idStpId = CommonUtil.generateUUID();
							urStep.setUrStepId(idStpId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType("2");
							urStep.setMinimumApprove("1");
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
							for(Map<String,Object> datas2 : dataUserRequestValidate.getOplsbothAlreadyQueued()){
									if(datas2.get("appRoleId").equals(appRoleIdOld)){
										User user = userRequestRepository.getUserByUserId((String) datas2.get("userId"));
										UrStepApprove urStepApprove = new UrStepApprove();
										urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
										urStepApprove.setUrStepId(idStpId);
										urStepApprove.setUsername(userCloseUr.getUsername());
										urStepApprove.setEmail(userCloseUr.getEmail());
										urStepApprove.setEnname(userCloseUr.getEnname());
										urStepApprove.setEnsurname(userCloseUr.getEnsurname());
										urStepApprove.setMobile(userCloseUr.getMobile());
										urStepApprove.setPhone(userCloseUr.getPhone());
										urStepApprove.setPincode(userCloseUr.getPincode());
										urStepApprove.setCreatedBy(userCloseUr.getUsername());
										urStepApprove.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStepApprove(urStepApprove);
	 									UrForUser urForUser = new UrForUser(user);
										urForUser.setUrForUserId(CommonUtil.generateUUID());
										urForUser.setUrId(idur);
										urForUser.setUrStatus("1");
										urForUser.setCreatedBy(userCloseUr.getUsername());
										urForUser.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrforUser(urForUser);
										logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
										logger.info("OBJ_UR_FOR_USER: {} , BY UR_ID : {} , USERNAME :{}",urForUser,idur,user.getUsername());
									}
							}
							
						}else{
							throw ServiceException.get500SystemError();
						}
					} //for loop map
				} // else flowIds
				logger.info("########## END CHANGE UR_ID:{} ##############",idur);
			}
		}
		Context variable = generateContextVariableRequestNo(dataFormApp);
		String header ="[ACIM] User Request :"+dataFormApp.get("requestNo");
		try{
			emailUtilService.send(mailsTo, header, TEMPLATE_EMAIL_USERREQUEST, variable);
		}catch(Exception e){
			logger.error("###### EMAIL FIAL ###### CAUSE :{}",e);
			e.printStackTrace();
		}
		
		logger.info("############ END CHANGE REQUEST NO ############### response :: {}",idRequestNo);
		return idRequestNo;
	}

	@Override
	public List<Application> getListappBytype(Map<String, String> request) throws ServiceException {
		try{
			String appName = CommonUtil.toSqlLikeFormat(request.get("appName"));
			return applicationRepository.findByAppNameAndType(appName, request.get("Type"));
		}catch(Exception e){
			throw ServiceException.get500SystemError(e);
		}
	}

	@Override
	public BaseGridResponse getListAppRoleGridByappIdAndType(Map<String, Object> request)throws ServiceException {
		try{
			BaseGridResponse gridResponse = new BaseGridResponse();
			String appId = (String) request.get("appId");
			String type = (String) request.get("type");
			Integer page = Integer.parseInt(request.get("page").toString()) ;
			Integer size = Integer.parseInt(request.get("rows").toString());
			int startRow = ((page - 1) * size) + 1;
			int endRow = 0;
			Integer totalRecord=0;
			
			List<Map<String,Object>> listDatas = new ArrayList<Map<String,Object>>();
			listDatas = userRequestRepository.getListAppAndAppRoleByAppIdAndType(appId, type);
			
			if(listDatas.size()>0){
				totalRecord = (Integer) listDatas.get(0).get("COUNT_APP");
			}
			
			if (totalRecord == size) {
				endRow = totalRecord;
			} else {
				endRow = (startRow + size) - 1;
			}
			request.put("startRow", startRow);
			request.put("endRow", endRow);
			gridResponse.setPage(page);
			gridResponse.setRows(listDatas);
			gridResponse.setRecords(totalRecord);
			int total = 0;
			if (totalRecord % size == 0) {
				total = totalRecord / size;
			} else {
				total = (totalRecord / size) + 1;
			}
			gridResponse.setTotal(total);
			
			return gridResponse;
		}catch(Exception e){
			throw ServiceException.get500SystemError(e);
		}
	}

	@Override
	public String subMitforCommitdataTer(DataUserRequestValidate dataUserRequestValidate,Map<String, String> dataFormApp,List<Map<String, Object>> listDataTer) throws ServiceException {
		logger.info("############ START TERMINATE REQUEST NO ###############");
		Integer countError=0;
		if(!dataUserRequestValidate.getErrorsbothAlreadyQueued().isEmpty()){
			countError = dataUserRequestValidate.getErrorsbothAlreadyQueued().size();
		}
		Integer countUrAll = dataUserRequestValidate.getLsapp().size()*dataUserRequestValidate.getLsuser().size(); 
		if ((countUrAll-countError)<=0){
			logger.error("############ BREAK TERMINATE REQUEST NO ############### : ALL_UR - URERROR {}",countUrAll-countError);
			throw ServiceException.get500SystemError();
			//thrown
		}
	
//		} if Model is null  and manager isn't same the person then thrown. this get model Manager if manager is same the person
		String useridRequestBy = (String) dataFormApp.get("userId");
		String usertypeValue = (String) dataFormApp.get("usertypeValue");
		String requestByValue = (String) dataFormApp.get("requestByValue");
		String requestTypeValue = (String) dataFormApp.get("requestTypeValue");
		String subject =  (String) dataFormApp.get("subject");
		String detail =  (String) dataFormApp.get("detail");
		String remarkError = null;
		if(dataFormApp.get("errorterminate")!=null){
			remarkError = dataFormApp.get("errorterminate");
		}
		final String userIdfirst = (String)dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("userId");
		User userCloseUr = userRequestRepository.getUserByUserId(useridRequestBy);
		User firstUser= userRequestRepository.getUserByUserId(userIdfirst);
		EmployeeProfile manager;
		User managerUser;
		try {
			manager = omWebService.getApproverProfile(firstUser.getPincode());
			managerUser = userRequestRepository.getUserByUsername(manager.getUserName());
		} catch (Exception e1) {
			logger.error("############ BREAK TERMINATE REQUEST NO ############### : PROBLEM CALL SERVICE GET MANAGER APPROVER AT USERFIRST : {} ",firstUser.getPincode());
			throw ServiceException.get500SystemError(e1);
		} // service Om
		Set<String> mailsTo = new HashSet<String>();
		
		// Step InsertUserRequestNo
		RequestNo requestNo = new RequestNo();
		Integer sequence = userRequestRepository.getSequenceRequestNo();
		String idRequestNo = CommonUtil.genRequestNo(sequence);
		Integer IndexRun=1;
		dataFormApp.put("requestNo",idRequestNo);
		requestNo.setRequestNo(idRequestNo);
		requestNo.setUrType(usertypeValue);
		requestNo.setRequestList(requestByValue);
		requestNo.setRequestType(requestTypeValue);
		requestNo.setSubject(subject);
		requestNo.setDetail(detail);
		requestNo.setRequestBy(userCloseUr.getPincode());  
		requestNo.setUsername(userCloseUr.getUsername());
		requestNo.setEnname(userCloseUr.getEnname());
		requestNo.setEnsurname(userCloseUr.getEnsurname());
		requestNo.setEmail(userCloseUr.getEmail());
		requestNo.setMobile(userCloseUr.getMobile());
		requestNo.setPhone(userCloseUr.getPhone());
		requestNo.setPosition(userCloseUr.getPosition());
		requestNo.setOrganize(userCloseUr.getOrgcode());
		requestNo.setCompany(userCloseUr.getCocode());
		requestNo.setRequestRemark(remarkError);
		requestNo.setCreatedBy(userCloseUr.getUsername());
		requestNo.setUpdatedBy(userCloseUr.getUsername());
		userRequestRepository.insertRequestNo(requestNo);
		logger.info("TERMINATE REQUEST NO : {} || {}",idRequestNo,requestNo);
		logger.info("######### STARTING TERMINATE UR #########");
		
		String orgCode = userRequestRepository.getOrgCodeByUserId((String)dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("userId"));
		logger.info("GET FIRST ORGCODE BY USER_ID: {} || {}",orgCode,dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("userId"));
		final String appRoleIdfirst = (String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appRoleId");
		String appIdold = (String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appId");
		String userType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(orgCode);
		logger.info("GET FIRST USERTYPE BY ORGCODE: {} || {}",userType,orgCode);
		String flowId = selectFlowIdByTypeTer(userType);
		logger.info("GET FLOW_ID {} , USERTYPE: {}",flowId,userType);
		List<Map<String, String>> flowIds = userRequestRepository.getListNodeFlowConfig(flowId);
		logger.info("OBJ_FLOW : {} , APP_ROLE_ID {} ",flowIds,appRoleIdfirst);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> rowData = (Map<String, Object>) CollectionUtils.find(dataUserRequestValidate.getOplsbothAlreadyQueued(), new Predicate(){
			@Override
			public boolean evaluate(Object arg0) {
				Map<String, Object> queuedCheckdata = (Map<String, Object>)arg0;
				return queuedCheckdata.get("userId").equals(userIdfirst)&&queuedCheckdata.get("appRoleId").equals(appRoleIdfirst);
			}
			
		});
		
		// InsertUr
		UR ur = new UR();
		String idur = CommonUtil.genUrNo(idRequestNo, IndexRun++);
		ur.setUrId(idur);
		ur.setRequestNo(idRequestNo);
		ur.setAppId((String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appId"));
		ur.setAppName((String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appName"));
		ur.setAppRoleId((String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appRoleId"));
		ur.setAppRoleName((String) dataUserRequestValidate.getOplsbothAlreadyQueued().get(0).get("appRolename"));
		ur.setRequestType(requestTypeValue);
		ur.setFlowId(flowId);
		ur.setStatus("1");
		ur.setRemark((String) rowData.get("remark"));
		ur.setCreatedBy(userCloseUr.getUsername());
		ur.setUpdatedBy(userCloseUr.getUsername());
		userRequestRepository.insertUr(ur);
		logger.info("MODEL UR : {}",ur);
		
		for(final Map<String,Object> datas : dataUserRequestValidate.getOplsbothAlreadyQueued()){
			List<UserAppOwner> listApproversforAppOwner = new ArrayList<UserAppOwner>();
			List<UserCustodian> listApproversforCustodian = new ArrayList<UserCustodian>();
			List<UserRoleIden> roleIdens = new ArrayList<UserRoleIden>();
			String appRoleId = (String) datas.get("appRoleId");
			String appId = (String) datas.get("appId");
			if(appIdold.equals(appId)){
				if(flowIds.isEmpty()){
					logger.error("######### BREAK CAUSE FLOW WAS NULL #########");
					throw ServiceException.get500SystemError();
				}else{
					for(Map<String, String> flow :flowIds){
						if("2".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE MANAGER ####### ");
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType("2");
							urStep.setMinimumApprove("1");
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
								if(StringUtils.isNotBlank(manager.getEmail())&&StringUtils.isNotEmpty(manager.getEmail())){
									try {
										mailsTo.add(new String(manager.getEmail().getBytes(),"UTF-8"));
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
								}
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
							UrStepApprove urStepApprove = new UrStepApprove();
							urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
							urStepApprove.setUrStepId(urStepId);
							urStepApprove.setPincode(manager.getPin());
							urStepApprove.setUsername(manager.getUserName());
							urStepApprove.setEmail(manager.getEmail());
							urStepApprove.setEnname(manager.getEnFirstName());
							urStepApprove.setEnsurname(manager.getEnLastName());
							urStepApprove.setMobile(managerUser.getMobile());
							urStepApprove.setPhone(managerUser.getPhone());
							urStepApprove.setPincode(manager.getPin());
							urStepApprove.setCreatedBy(userCloseUr.getUsername());
							urStepApprove.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStepApprove(urStepApprove);
							logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
						}else if("3".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE ROLEIDEN ####### ");
							roleIdens = userRequestRepository.getListRoleIdenByUsertype(userType);
							logger.info("OBJ_LIST_ROLEIDEN : {} , BY USERTYPE : {}",roleIdens,idur);
							if(roleIdens.isEmpty()){
								roleIdens = userRequestRepository.getListRoleIdenBytypeZero();
								logger.info("OBJ_LIST_ROLEIDEN : {} , BY DEFAULT ",roleIdens);
							}
							if(roleIdens.isEmpty()){
								logger.error("########## BREAK ROLE IDEN NULL ############ BY USERTYPE : {}",userType);
								throw ServiceException.get500SystemError();
							}else{  
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType("2");
							urStep.setMinimumApprove("1");
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
								for(UserRoleIden roleIden :roleIdens){
									if(StringUtils.isNotBlank(roleIden.getEmail())&&StringUtils.isNotEmpty(roleIden.getEmail())){
										try {
											mailsTo.add(new String(roleIden.getEmail().getBytes(),"UTF-8"));
										} catch (UnsupportedEncodingException e) {
											e.printStackTrace();
										}
									}
								}
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
								for(UserRoleIden roleIden :roleIdens){
									UrStepApprove urStepApprove = new UrStepApprove();
									urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
									urStepApprove.setUrStepId(urStepId);
									urStepApprove.setUsername(roleIden.getUsername());
									urStepApprove.setEmail(roleIden.getEmail());
									urStepApprove.setEnname(roleIden.getEnname());
									urStepApprove.setEnsurname(roleIden.getEnsurname());
									urStepApprove.setMobile(roleIden.getMobile());
									urStepApprove.setPhone(roleIden.getPhone());
									urStepApprove.setPincode(roleIden.getPincode());
									urStepApprove.setCreatedBy(userCloseUr.getUsername());
									urStepApprove.setUpdatedBy(userCloseUr.getUsername());
									userRequestRepository.insertUrStepApprove(urStepApprove);
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
								}
							}
						}else if("4".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE APP_OWNER ####### ");
							Integer type_Approver_forAppOwner = userRequestRepository.getTypeApproverforAppOwner(appRoleId);
							listApproversforAppOwner = selectApproverAppownerByTypeApprover(type_Approver_forAppOwner,appRoleId);
							logger.info("OBJ_LIST_APPOWNER : {} , BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forAppOwner,appRoleId);
							if(listApproversforAppOwner.isEmpty()){
								logger.error("########## LIST_APPOWNER IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forAppOwner,appRoleId);
								throw ServiceException.get500SystemError();
							}else{
							
							Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "1");
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType(typeApproveandminimum.get("approveType"));
							urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
								for(UserAppOwner userAppOwner :listApproversforAppOwner){
									UrStepApprove urStepApprove = new UrStepApprove();
									urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
									urStepApprove.setUrStepId(urStepId);
									urStepApprove.setUsername(userAppOwner.getUsername());
									urStepApprove.setEmail(userAppOwner.getEmail());
									urStepApprove.setEnname(userAppOwner.getEnname());
									urStepApprove.setEnsurname(userAppOwner.getEnsurname());
									urStepApprove.setMobile(userAppOwner.getMobile());
									urStepApprove.setPhone(userAppOwner.getPhone());
									urStepApprove.setPincode(userAppOwner.getPincode());
									urStepApprove.setCreatedBy(userCloseUr.getUsername());
									urStepApprove.setUpdatedBy(userCloseUr.getUsername());
									urStepApprove.setSequence(userAppOwner.getSeQuenceUser());
									urStepApprove.setTeamName(userAppOwner.getTeamName());
									urStepApprove.setTeamSequence(userAppOwner.getSeQuenceTeam());
									userRequestRepository.insertUrStepApprove(urStepApprove);
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
								}
							}	
						}else if("5".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE CUSTODIAN ####### ");
							Integer type_Approver_forCustodian = userRequestRepository.getTypeApproverforCustodian(appRoleId);
							listApproversforCustodian = selectApproverCustodianByType(type_Approver_forCustodian,appRoleId,userType);
							logger.info("OBJ_LIST_CUSTODIAN : {} , BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forCustodian,appRoleId);
							if(listApproversforCustodian.isEmpty()){
								logger.error("########## LIST_CUSTODIAN IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forCustodian,appRoleId);
								throw ServiceException.get500SystemError();
							}else{
							Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "2");
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType(typeApproveandminimum.get("approveType"));
							urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
								for(UserCustodian userCustodian :listApproversforCustodian){
									UrStepApprove urStepApprove = new UrStepApprove();
									urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
									urStepApprove.setUrStepId(urStepId);
									urStepApprove.setUsername(userCustodian.getUsername());
									urStepApprove.setEmail(userCustodian.getEmail());
									urStepApprove.setEnname(userCustodian.getEnname());
									urStepApprove.setEnsurname(userCustodian.getEnsurname());
									urStepApprove.setMobile(userCustodian.getMobile());
									urStepApprove.setPhone(userCustodian.getPhone());
									urStepApprove.setPincode(userCustodian.getPincode());
									urStepApprove.setCreatedBy(userCloseUr.getUsername());
									urStepApprove.setUpdatedBy(userCloseUr.getUsername());
									urStepApprove.setSequence(userCustodian.getSeQuenceUser());
									urStepApprove.setTeamName(userCustodian.getTeamName());
									urStepApprove.setTeamSequence(userCustodian.getSeQuenceTeam());
									userRequestRepository.insertUrStepApprove(urStepApprove);
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
								}
							}
						}else if("6".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE CUSTODIAN ####### ");
							UrStep urStep = new UrStep();
							String idStpId = CommonUtil.generateUUID();
							urStep.setUrStepId(idStpId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType("2");
							urStep.setMinimumApprove("1");
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
							for(Map<String,Object> datas2 : dataUserRequestValidate.getOplsbothAlreadyQueued()){
									
									if(datas2.get("appId").equals(appIdold)){
										User user = userRequestRepository.getUserByUserId((String) datas2.get("userId"));
										UrStepApprove urStepApprove = new UrStepApprove();
										urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
										urStepApprove.setUrStepId(idStpId);
										urStepApprove.setUsername(user.getUsername());
										urStepApprove.setEmail(user.getEmail());
										urStepApprove.setEnname(user.getEnname());
										urStepApprove.setEnsurname(user.getEnsurname());
										urStepApprove.setMobile(user.getMobile());
										urStepApprove.setPhone(user.getPhone());
										urStepApprove.setPincode(user.getPincode());
										urStepApprove.setCreatedBy(userCloseUr.getUsername());
										urStepApprove.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStepApprove(urStepApprove);
										logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
	 									UrForUser urForUser = new UrForUser(user);
										urForUser.setUrForUserId(CommonUtil.generateUUID());
										urForUser.setUrId(idur);
										urForUser.setUrStatus("1");
										urForUser.setCreatedBy(userCloseUr.getUsername());
										urForUser.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrforUser(urForUser);
										logger.info("OBJ_UR_FOR_USER: {} , BY UR_ID : {} , USERNAME :{}",urForUser,idur,user.getUsername());
									}
								
							}
							
						}else{
							throw ServiceException.get500SystemError();
						}
					} //for loop map
				} // else flowIds
			}else{
				logger.info("########## END TERMINATE UR_ID:{} ##############",idur);
				
				logger.info("############ START TERMINATE UR_ID ###############");
				appIdold = (String) datas.get("appId");
				userType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(orgCode);
				logger.info("GET  USERTYPE BY ORGCODE: {} || {}",userType,orgCode);
				flowId = selectFlowIdByTypeTer(userType);
				logger.info("GET FLOW_ID {} , || USERTYPE: {}",flowId,userType);
				flowIds = userRequestRepository.getListNodeFlowConfig(flowId);
				@SuppressWarnings("unchecked")
				Map<String, Object> rowData2 = (Map<String, Object>) CollectionUtils.find(dataUserRequestValidate.getOplsbothAlreadyQueued(), new Predicate(){
					@Override
					public boolean evaluate(Object arg0) {
						Map<String, Object> queuedCheckdata = (Map<String, Object>)arg0;
						return queuedCheckdata.get("userId").equals(datas.get("userId"))&&queuedCheckdata.get("appRoleId").equals(datas.get("appRoleId"));
					}
					
				});
				// InsertUr
				ur = new UR();
				idur = CommonUtil.genUrNo(idRequestNo, IndexRun++);
				ur.setUrId(idur);
				ur.setRequestNo(idRequestNo);
				ur.setAppId((String) datas.get("appId"));
				ur.setAppName((String) datas.get("appName"));
				ur.setAppRoleId((String) datas.get("appRoleId"));
				ur.setAppRoleName((String) datas.get("appRolename"));
				ur.setRequestType(requestTypeValue);
				ur.setFlowId(flowId);
				ur.setStatus("1");
				ur.setRemark((String) rowData2.get("remark"));
				ur.setCreatedBy(userCloseUr.getUsername());
				ur.setUpdatedBy(userCloseUr.getUsername());
				userRequestRepository.insertUr(ur);
				logger.info("MODEL UR : {}",ur);
				if(flowIds.isEmpty()){
					logger.error("######### BREAK CAUSE FLOW WAS NULL #########");
					throw ServiceException.get500SystemError();
				}else{
					for(Map<String, String> flow :flowIds){
						if("2".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE MANAGER ####### ");
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType("2");
							urStep.setMinimumApprove("1");
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
								if(StringUtils.isNotBlank(manager.getEmail())&&StringUtils.isNotEmpty(manager.getEmail())){
									try {
										mailsTo.add(new String(manager.getEmail().getBytes(),"UTF-8"));
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
								}
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
							UrStepApprove urStepApprove = new UrStepApprove();
							urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
							urStepApprove.setUrStepId(urStepId);
							urStepApprove.setPincode(manager.getPin());
							urStepApprove.setUsername(manager.getUserName());
							urStepApprove.setEmail(manager.getEmail());
							urStepApprove.setEnname(manager.getEnFirstName());
							urStepApprove.setEnsurname(manager.getEnLastName());
							urStepApprove.setMobile(managerUser.getMobile());
							urStepApprove.setPhone(managerUser.getPhone());
							urStepApprove.setPincode(manager.getPin());
							urStepApprove.setCreatedBy(userCloseUr.getUsername());
							urStepApprove.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStepApprove(urStepApprove);
							logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
						}else if("3".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE ROLEIDEN ####### ");
							roleIdens = userRequestRepository.getListRoleIdenByUsertype(userType);
							logger.info("OBJ_LIST_ROLEIDEN : {} , BY USERTYPE : {}",roleIdens,idur);
							if(roleIdens.isEmpty()){
								roleIdens = userRequestRepository.getListRoleIdenBytypeZero();
								logger.info("OBJ_LIST_ROLEIDEN : {} , BY DEFAULT ",roleIdens);
							}
							if(roleIdens.isEmpty()){
								logger.error("########## BREAK ROLE IDEN NULL ############ BY USERTYPE : {}",userType);
								throw ServiceException.get500SystemError();
							}else{  
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType("2");
							urStep.setMinimumApprove("1");
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
								for(UserRoleIden roleIden :roleIdens){
									if(StringUtils.isNotBlank(roleIden.getEmail())&&StringUtils.isNotEmpty(roleIden.getEmail())){
										try {
											mailsTo.add(new String(roleIden.getEmail().getBytes(),"UTF-8"));
										} catch (UnsupportedEncodingException e) {
											e.printStackTrace();
										}
									}
								}
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
								for(UserRoleIden roleIden :roleIdens){
									UrStepApprove urStepApprove = new UrStepApprove();
									urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
									urStepApprove.setUrStepId(urStepId);
									urStepApprove.setUsername(roleIden.getUsername());
									urStepApprove.setEmail(roleIden.getEmail());
									urStepApprove.setEnname(roleIden.getEnname());
									urStepApprove.setEnsurname(roleIden.getEnsurname());
									urStepApprove.setMobile(roleIden.getMobile());
									urStepApprove.setPhone(roleIden.getPhone());
									urStepApprove.setPincode(roleIden.getPincode());
									urStepApprove.setCreatedBy(userCloseUr.getUsername());
									urStepApprove.setUpdatedBy(userCloseUr.getUsername());
									userRequestRepository.insertUrStepApprove(urStepApprove);
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
								}
							}
						}else if("4".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE APP_OWNER ####### ");
							Integer type_Approver_forAppOwner = userRequestRepository.getTypeApproverforAppOwner(appRoleId);
							listApproversforAppOwner = selectApproverAppownerByTypeApprover(type_Approver_forAppOwner,appRoleId);
							logger.info("OBJ_LIST_APPOWNER : {} , BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forAppOwner,appRoleId);
							if(listApproversforAppOwner.isEmpty()){
								logger.error("########## LIST_APPOWNER IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forAppOwner,appRoleId);
								throw ServiceException.get500SystemError();
							}else{
							
							Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "1");
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType(typeApproveandminimum.get("approveType"));
							urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
								for(UserAppOwner userAppOwner :listApproversforAppOwner){
									UrStepApprove urStepApprove = new UrStepApprove();
									urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
									urStepApprove.setUrStepId(urStepId);
									urStepApprove.setUsername(userAppOwner.getUsername());
									urStepApprove.setEmail(userAppOwner.getEmail());
									urStepApprove.setEnname(userAppOwner.getEnname());
									urStepApprove.setEnsurname(userAppOwner.getEnsurname());
									urStepApprove.setMobile(userAppOwner.getMobile());
									urStepApprove.setPhone(userAppOwner.getPhone());
									urStepApprove.setPincode(userAppOwner.getPincode());
									urStepApprove.setCreatedBy(userCloseUr.getUsername());
									urStepApprove.setUpdatedBy(userCloseUr.getUsername());
									urStepApprove.setSequence(userAppOwner.getSeQuenceUser());
									urStepApprove.setTeamName(userAppOwner.getTeamName());
									urStepApprove.setTeamSequence(userAppOwner.getSeQuenceTeam());
									userRequestRepository.insertUrStepApprove(urStepApprove);
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
								}
							}	
						}else if("5".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE CUSTODIAN ####### ");
							Integer type_Approver_forCustodian = userRequestRepository.getTypeApproverforCustodian(appRoleId);
							listApproversforCustodian = selectApproverCustodianByType(type_Approver_forCustodian,appRoleId,userType);
							logger.info("OBJ_LIST_CUSTODIAN : {} , BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {}",listApproversforAppOwner,type_Approver_forCustodian,appRoleId);
							if(listApproversforCustodian.isEmpty()){
								logger.error("########## LIST_CUSTODIAN IS NULL ############ BY TYPE_APPROVER_FOR_APPOWNER : {} , APPROLEID : {}",type_Approver_forCustodian,appRoleId);
								throw ServiceException.get500SystemError();
							}else{
							Map<String,String> typeApproveandminimum=userRequestRepository.getTypeApproverAndMinimumByappRoleIdandType(appRoleId, "2");
							UrStep urStep = new UrStep();
							String urStepId=CommonUtil.generateUUID();
							urStep.setUrStepId(urStepId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType(typeApproveandminimum.get("approveType"));
							urStep.setMinimumApprove(typeApproveandminimum.get("minimum"));
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
								for(UserCustodian userCustodian :listApproversforCustodian){
									UrStepApprove urStepApprove = new UrStepApprove();
									urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
									urStepApprove.setUrStepId(urStepId);
									urStepApprove.setUsername(userCustodian.getUsername());
									urStepApprove.setEmail(userCustodian.getEmail());
									urStepApprove.setEnname(userCustodian.getEnname());
									urStepApprove.setEnsurname(userCustodian.getEnsurname());
									urStepApprove.setMobile(userCustodian.getMobile());
									urStepApprove.setPhone(userCustodian.getPhone());
									urStepApprove.setPincode(userCustodian.getPincode());
									urStepApprove.setCreatedBy(userCloseUr.getUsername());
									urStepApprove.setUpdatedBy(userCloseUr.getUsername());
									urStepApprove.setSequence(userCustodian.getSeQuenceUser());
									urStepApprove.setTeamName(userCustodian.getTeamName());
									urStepApprove.setTeamSequence(userCustodian.getSeQuenceTeam());
									userRequestRepository.insertUrStepApprove(urStepApprove);
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
								}
							}
						}else if("6".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE CLOSE ####### ");
							UrStep urStep = new UrStep();
							String idStpId = CommonUtil.generateUUID();
							urStep.setUrStepId(idStpId);
							urStep.setUrId(idur);
							urStep.setUrStep(flow.get("flowNodeCode"));
							urStep.setSeQuence(flow.get("seQuence"));
							urStep.setApproveType("2");
							urStep.setMinimumApprove("1");
							if("1".equals(flow.get("seQuence"))){
								urStep.setStatus("1");
							}else{
								urStep.setStatus("0");
							}
							urStep.setCreatedBy(userCloseUr.getUsername());
							urStep.setUpdatedBy(userCloseUr.getUsername());
							userRequestRepository.insertUrStep(urStep);
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {}",urStep,idur);
							for(Map<String,Object> datas2 : dataUserRequestValidate.getOplsbothAlreadyQueued()){
									
									if(datas2.get("appId").equals(appIdold)){
										User user = userRequestRepository.getUserByUserId((String) datas2.get("userId"));
										UrStepApprove urStepApprove = new UrStepApprove();
										urStepApprove.setUrstepapproveId(CommonUtil.generateUUID());
										urStepApprove.setUrStepId(idStpId);
										urStepApprove.setUsername(userCloseUr.getUsername());
										urStepApprove.setEmail(userCloseUr.getEmail());
										urStepApprove.setEnname(userCloseUr.getEnname());
										urStepApprove.setEnsurname(userCloseUr.getEnsurname());
										urStepApprove.setMobile(userCloseUr.getMobile());
										urStepApprove.setPhone(userCloseUr.getPhone());
										urStepApprove.setPincode(userCloseUr.getPincode());
										urStepApprove.setCreatedBy(userCloseUr.getUsername());
										urStepApprove.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrStepApprove(urStepApprove);
	 									UrForUser urForUser = new UrForUser(user);
										urForUser.setUrForUserId(CommonUtil.generateUUID());
										urForUser.setUrId(idur);
										urForUser.setUrStatus("1");
										urForUser.setCreatedBy(userCloseUr.getUsername());
										urForUser.setUpdatedBy(userCloseUr.getUsername());
										userRequestRepository.insertUrforUser(urForUser);
										logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {}",urStepApprove,idur);
										logger.info("OBJ_UR_FOR_USER: {} , BY UR_ID : {} , USERNAME :{}",urForUser,idur,user.getUsername());
									}
								
							}
							
						}else{
							throw ServiceException.get500SystemError();
						}
					} //for loop map
				} // else flowIds
				
			}
		}
		Context variable = generateContextVariableRequestNo(dataFormApp);
		String header ="[ACIM] User Request :"+dataFormApp.get("requestNo");
		try{
			emailUtilService.send(mailsTo, header, TEMPLATE_EMAIL_USERREQUEST, variable);
		}catch(Exception e){
			logger.error("###### EMAIL FIAL ###### CAUSE :{}",e);
			e.printStackTrace();
		}
		logger.info("########## END TERMINATE UR_ID:{} ##############",idur);
		return idRequestNo;
	}

	@Override
	public List<Map<String, Object>> getListDatasProfileByappRoleIds(List<String> appRoleIds) {
		return userRequestRepository.getAppDetailGroupByApproleIds(appRoleIds);
	}

	// NEW GRID STD
	@Override
	public List<Map<String, Object>> getListEligiblebyAppNameStd(
			Map<String, Object> request) {
		String appName = (String) request.get("appName");
		if(StringUtils.isNotBlank(appName)&&StringUtils.isNoneEmpty(appName)){
			appName = CommonUtil.toSqlLikeFormat(appName);
		}
		String username = (String) request.get("username");
		String type = (String) request.get("type");
		return userRequestRepository.getListAppStdEligiblebyAppName(appName,username,type);
	}

	@Override
	public BaseGridResponse listAppEligibleStdGridByAppId(
			Map<String, Object> request) {
		BaseGridResponse gridResponse = new BaseGridResponse();
		String appId = (String) request.get("appId");
		String username = (String) request.get("username");
		String type = (String) request.get("type");
		Integer page = Integer.parseInt(request.get("page").toString()) ;
		Integer size = Integer.parseInt(request.get("rows").toString());
		int startRow = ((page - 1) * size) + 1;
		int endRow = 0;
		Integer totalRecord=0;
		
		List<Map<String,Object>> listDatas = new ArrayList<Map<String,Object>>();
		listDatas = userRequestRepository.getListAppStdGridEligibleByAppId(appId,username,type);
		
		if(listDatas.size()>0){
			totalRecord = (Integer) listDatas.get(0).get("COUNT_APP");
		}
		
		if (totalRecord == size) {
			endRow = totalRecord;
		} else {
			endRow = (startRow + size) - 1;
		}
		request.put("startRow", startRow);
		request.put("endRow", endRow);
		gridResponse.setPage(page);
		gridResponse.setRows(listDatas);
		gridResponse.setRecords(totalRecord);
		int total = 0;
		if (totalRecord % size == 0) {
			total = totalRecord / size;
		} else {
			total = (totalRecord / size) + 1;
		}
		gridResponse.setTotal(total);
		
		return gridResponse;
	}
	
	@Override
	public BaseGridResponse listAppEligibleStdGridByAppName(
			Map<String, Object> request) {
		BaseGridResponse gridResponse = new BaseGridResponse();
		String appName = (String) request.get("appName");
		if(StringUtils.isNotBlank(appName)&&StringUtils.isNoneEmpty(appName)){
			appName = CommonUtil.toSqlLikeFormat(appName);
		}
		String username = (String) request.get("username");
		String type = (String) request.get("type");
		Integer page = Integer.parseInt(request.get("page").toString()) ;
		Integer size = Integer.parseInt(request.get("rows").toString());
		Integer startRow = ((page - 1) * size) + 1;
		Integer endRow = 0;
		Integer totalRecord=userRequestRepository.getCountAppStdGridEligiblebyAppName(appName, username, type);
		if (totalRecord == size) {
			endRow = totalRecord;
		} else {
			endRow = (startRow + size) - 1;
		}
		List<Map<String,Object>> listDatas = userRequestRepository.getListAppStdGridEligiblebyAppName(appName,username,type,startRow,endRow);
		gridResponse.setPage(page);
		gridResponse.setRows(listDatas);
		gridResponse.setRecords(totalRecord);
		int total = 0;
		if (totalRecord % size == 0) {
			total = totalRecord / size;
		} else {
			total = (totalRecord / size) + 1;
		}
		gridResponse.setTotal(total);
		
		return gridResponse;
	}
	
	// NEW GRID SPC
	@Override
	public List<Map<String, Object>> listappEligibleListSpcByAppname(
			Map<String, Object> request) {
		String appName = (String) request.get("appName");
		if(StringUtils.isNotBlank(appName)&&StringUtils.isNoneEmpty(appName)){
			appName = CommonUtil.toSqlLikeFormat(appName);
		}
		String type = (String) request.get("type");
		return userRequestRepository.getListAppSpcEligibilebyAppName(appName,type);
	}

	@Override
	public BaseGridResponse listAppSpcGridEligibleByAppId(Map<String, Object> request) {
		BaseGridResponse gridResponse = new BaseGridResponse();
		String appId = (String) request.get("appId");
		String type = (String) request.get("type");
		Integer page = Integer.parseInt(request.get("page").toString()) ;
		Integer size = Integer.parseInt(request.get("rows").toString());
		int startRow = ((page - 1) * size) + 1;
		int endRow = 0;
		Integer totalRecord=0;
		
		List<Map<String,Object>> listDatas = new ArrayList<Map<String,Object>>();
		listDatas = userRequestRepository.getListAppSpcGridEligibleByAppId(appId,type);
		
		if(listDatas.size()>0){
			totalRecord = (Integer) listDatas.get(0).get("COUNT_APP");
		}
		
		if (totalRecord == size) {
			endRow = totalRecord;
		} else {
			endRow = (startRow + size) - 1;
		}
		request.put("startRow", startRow);
		request.put("endRow", endRow);
		gridResponse.setPage(page);
		gridResponse.setRows(listDatas);
		gridResponse.setRecords(totalRecord);
		int total = 0;
		if (totalRecord % size == 0) {
			total = totalRecord / size;
		} else {
			total = (totalRecord / size) + 1;
		}
		gridResponse.setTotal(total);
		
		return gridResponse;
	}
	
	@Override
	public BaseGridResponse listAppSpcGridEligibleByAppName(Map<String, Object> request) {
		BaseGridResponse gridResponse = new BaseGridResponse();
		String appName = (String) request.get("appName");
		if(StringUtils.isNotBlank(appName)&&StringUtils.isNoneEmpty(appName)){
			appName = CommonUtil.toSqlLikeFormat(appName);
		}
		String username = (String) request.get("username");
		String type = (String) request.get("type");
		Integer page = Integer.parseInt(request.get("page").toString()) ;
		Integer size = Integer.parseInt(request.get("rows").toString());
		int startRow = ((page - 1) * size) + 1;
		int endRow = 0;
		Integer totalRecord=userRequestRepository.getCountAppSpcGridEligibleByAppName(appName,username,type);
		if (totalRecord == size) {
			endRow = totalRecord;
		} else {
			endRow = (startRow + size) - 1;
		}
		List<Map<String,Object>> listDatas = userRequestRepository.getListAppSpcGridEligibleByAppName(appName,username,type,startRow,endRow);
		gridResponse.setPage(page);
		gridResponse.setRows(listDatas);
		gridResponse.setRecords(totalRecord);
		int total = 0;
		if (totalRecord % size == 0) {
			total = totalRecord / size;
		} else {
			total = (totalRecord / size) + 1;
		}
		gridResponse.setTotal(total);
		
		return gridResponse;
	}
	
	public Context generateContextEmailByUrId (String urId) throws ServiceException{
		Context context = new Context();
		String date = DateFormatUtils.format(Calendar.getInstance().getTime(), "dd/MM/yyyy");
		
		RequestNo requestNo = userRequestRepository.getRequestNoByUrId(urId);
		User user = userRepository.getUserProfileByPin(requestNo.getRequestBy());
		String userrequestNo=  requestNo.getRequestNo();
		String urType = commonService.getMessage("ur.type."+requestNo.getUrType());
		String requestType= commonService.getMessage("ur.request.type."+requestNo.getRequestType());
		String requestUser= commonService.getMessage("request.list."+requestNo.getRequestList());
		
		String nameUser= user.getEnfullname();
		String subject= requestNo.getSubject();
		String detail= requestNo.getDetail();
		context.setVariable("date",date);
		context.setVariable("userrequestNo",userrequestNo);
		context.setVariable("urType",urType);
		context.setVariable("requestType",requestType);
		context.setVariable("requestUser",requestUser);
		context.setVariable("nameUser",nameUser);
		context.setVariable("subject",subject);
		context.setVariable("detail",detail);
		return context;
		
	}
	
	public Context generateContextVariableRequestNo (Map<String,String> data){
		Context context = new Context();
		String date = DateFormatUtils.format(Calendar.getInstance().getTime(), "dd/MM/yyyy");
		String userrequestNo=data.get("requestNo");
		String urType=data.get("usertype");
		String requestType=data.get("requestType");
		String requestUser=data.get("requestBy");
		String nameUser=data.get("name");
		String subject=data.get("subject");
		String detail=data.get("detail");
		context.setVariable("date",date);
		context.setVariable("userrequestNo",userrequestNo);
		context.setVariable("urType",urType);
		context.setVariable("requestType",requestType);
		context.setVariable("requestUser",requestUser);
		context.setVariable("nameUser",nameUser);
		context.setVariable("subject",subject);
		context.setVariable("detail",detail);
		return context;
		
	}

	@Override
	public BaseGridResponse listAppChangeGridByappName(
			Map<String, Object> request) {
		BaseGridResponse gridResponse = new BaseGridResponse();
		Integer page = Integer.parseInt(request.get("page").toString()) ;
		Integer size = Integer.parseInt(request.get("rows").toString());
		int startRow = ((page - 1) * size) + 1;
		int endRow = 0;
		String appName = (String) request.get("appName");
		if(StringUtils.isNotBlank(appName)&&StringUtils.isNoneEmpty(appName)){
			appName = CommonUtil.toSqlLikeFormat(appName);
		}
		String username = (String) request.get("username");
		String type = (String) request.get("type");
		Integer totalRecord = userRequestRepository.getCountlistAppChangeByappName(appName,username,type);
		if (totalRecord == size) {
			endRow = totalRecord;
		} else {
			endRow = (startRow + size) - 1;
		}
		request.put("startRow", startRow);
		request.put("endRow", endRow);
		List<Map<String,Object>> listDatas = new ArrayList<Map<String,Object>>();
		listDatas = userRequestRepository.getlistAppChangeByAppNameTypeandUsername(appName,username,type,startRow,endRow);
		gridResponse.setPage(page);
		gridResponse.setRows(listDatas);
		gridResponse.setRecords(totalRecord);
		int total = 0;
		if (totalRecord % size == 0) {
			total = totalRecord / size;
		} else {
			total = (totalRecord / size) + 1;
		}
		gridResponse.setTotal(total);
		
		return gridResponse;
	}
	
	@Override
	public BaseGridResponse listAppTerAuthorGridByappName(Map<String, Object> request) {
		BaseGridResponse gridResponse = new BaseGridResponse();
		Integer page = Integer.parseInt(request.get("page").toString()) ;
		Integer size = Integer.parseInt(request.get("rows").toString());
		int startRow = ((page - 1) * size) + 1;
		int endRow = 0;
		String appName = (String) request.get("appName");
		if(StringUtils.isNotBlank(appName)&&StringUtils.isNoneEmpty(appName)){
			appName = CommonUtil.toSqlLikeFormat(appName);
		}
		String username = (String) request.get("username");
		String type = (String) request.get("type");
		Integer totalRecord = userRequestRepository.getCountlistTerAuthorByappName(appName,username,type);
		if (totalRecord == size) {
			endRow = totalRecord;
		} else {
			endRow = (startRow + size) - 1;
		}
		request.put("startRow", startRow);
		request.put("endRow", endRow);
		List<Map<String,Object>> listDatas = new ArrayList<Map<String,Object>>();
		listDatas = userRequestRepository.getlistAppTerAuthorByAppNameTypeandUsername(appName,username,type,startRow,endRow);
		gridResponse.setPage(page);
		gridResponse.setRows(listDatas);
		gridResponse.setRecords(totalRecord);
		int total = 0;
		if (totalRecord % size == 0) {
			total = totalRecord / size;
		} else {
			total = (totalRecord / size) + 1;
		}
		gridResponse.setTotal(total);
		
		return gridResponse;
	}
	@Override
	public BaseGridResponse getListAppRoleGridByappNameAndType(Map<String, Object> request) throws ServiceException{
		try{
			BaseGridResponse gridResponse = new BaseGridResponse();
			String appName = (String) request.get("appName");
			if(StringUtils.isNotBlank(appName)&&StringUtils.isNoneEmpty(appName)){
				appName = CommonUtil.toSqlLikeFormat(appName);
			}
			String type = (String) request.get("type");
			Integer page = Integer.parseInt(request.get("page").toString()) ;
			Integer size = Integer.parseInt(request.get("rows").toString());
			Integer startRow = ((page - 1) * size) + 1;
			Integer endRow = 0;
			Integer totalRecord = userRequestRepository.getCountListAppAndAppRoleByAppNameAndType(appName,type);
			if (totalRecord == size) {
				endRow = totalRecord;
			} else {
				endRow = (startRow + size) - 1;
			}
			 List<Map<String,Object>> listDatas = userRequestRepository.getListAppAndAppRoleByAppNameAndType(appName, type,startRow,endRow);
			gridResponse.setPage(page);
			gridResponse.setRows(listDatas);
			gridResponse.setRecords(totalRecord);
			int total = 0;
			if (totalRecord % size == 0) {
				total = totalRecord / size;
			} else {
				total = (totalRecord / size) + 1;
			}
			gridResponse.setTotal(total);
			
			return gridResponse;
		}catch(Exception e){
			throw ServiceException.get500SystemError(e);
		}
	}

	@Override
	public List<UrForUser> reloadTokenUrForUser(List<UrForUser> urForUserList,String userLogin) throws ServiceException {

		for(UrForUser urForUser : urForUserList){
			String userId = urForUser.getUserId();
		}
		
		return null;
	}



}

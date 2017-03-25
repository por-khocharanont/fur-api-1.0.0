package th.cu.thesis.fur.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import th.cu.thesis.fur.api.model.RequestNo;
import th.cu.thesis.fur.api.model.UR;
import th.cu.thesis.fur.api.model.UrForUser;
import th.cu.thesis.fur.api.model.UrStep;
import th.cu.thesis.fur.api.model.UrStepApprove;
import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.model.UserCustodian;
import th.cu.thesis.fur.api.model.UserExpireTerminate;
import th.cu.thesis.fur.api.model.UserInPeriod;
import th.cu.thesis.fur.api.repository.BatchPeriodExpiredRepository;
import th.cu.thesis.fur.api.repository.BatchUserReOrgRepository;
import th.cu.thesis.fur.api.repository.UserRequestRepository;
import th.cu.thesis.fur.api.rest.model.Batch;
import th.cu.thesis.fur.api.service.BatchPeriodExpiredService;
import th.cu.thesis.fur.api.service.EmailUtilService;
import th.cu.thesis.fur.api.service.UserRequestService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;

@Service("batchPeriodExpiredService")
public class BatchPeriodExpiredServiceImpl implements  BatchPeriodExpiredService {
	
	@Autowired
	BatchPeriodExpiredRepository batchPeriodExpiredRepository;
	
	@Autowired
	UserRequestService userRequestService;
	
	@Autowired
	UserRequestRepository userRequestRepository;
	
	@Autowired
	EmailUtilService emailUtilService;
	
	@Autowired
	BatchUserReOrgRepository batchUserReOrgRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(BatchPeriodExpiredServiceImpl.class);
	
	static final String TEMPLATE_EMAIL_EXPIREDALERT ="email/ExpiredAlertTemplate";
	static final String TEMPLATE_EMAIL_USERREQUEST ="email/requestNoTemplate";
	@Override
	@Transactional(rollbackFor=ServiceException.class,propagation=Propagation.REQUIRED)
	public Batch periodExpired() throws ServiceException {
		Set<String> mails = new HashSet<String>();
		Batch batch = null;
		final String batchName = "Batch-Expired";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String date = formatter.format(new Date());
		logger.info("###### Start {} : {} ######",batchName,date);
		try {
		//Case Send mail 3day & 10day before expire
			 List<UserInPeriod> userInPeriod = batchPeriodExpiredRepository.selectUserInPeriod();
			 logger.info("OBJ_UserInPeriod : {} , {} ",userInPeriod,batchName);
				for(UserInPeriod userInPeriodList : userInPeriod){	
					try{
						Context variable = new Context();
						String start = DateFormatUtils.format(userInPeriodList.getStartTime(), "dd/MM/yyyy");
						String end = DateFormatUtils.format(userInPeriodList.getEndTime(), "dd/MM/yyyy");
						String header ="แจ้งเตือน Application ที่ใกล้หมดอายุการใช้งาน";
						variable.setVariable("username",userInPeriodList.getUserName());
						variable.setVariable("application",userInPeriodList.getAppName());
						variable.setVariable("roleApplication",userInPeriodList.getAppRoleName());
						variable.setVariable("start",start);
						variable.setVariable("end",end);
						emailUtilService.send(userInPeriodList.getEmail(), header, TEMPLATE_EMAIL_EXPIREDALERT, variable);
						logger.info("subject: {},To : {} ,{}",header,userInPeriodList.getEmail(),batchName);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			//Case Expired
			 List<UserExpireTerminate> userExpireTerminate = batchPeriodExpiredRepository.selectExpireToGenTerminate();
			 logger.info("OBJ_UserExpireTerminate : {} , {} ",userExpireTerminate,batchName);
				//Step1 GenUrTerminate
			if(userExpireTerminate!=null){
				String idRequestNo;
				String updateBy = "SYSTEM-ADMIN";
				List<UserCustodian> listApproversforCustodian;	
				for(UserExpireTerminate userExpireTerminateList : userExpireTerminate){
					String userId = userExpireTerminateList.getUserId();
					logger.info("getUserByUserId userId:{} , {} ",userId,batchName);
					User user = userRequestRepository.getUserByUserId(userId);
					List<String> types = batchPeriodExpiredRepository.listTypeApp(userId);
					logger.info("User's type applications, {} ,username:{},date:{} {}",types,user.getUsername(),date,batchName);
					for(String type: types){
						RequestNo requestNo = new RequestNo();
						Integer sequence = userRequestRepository.getSequenceRequestNo();
						idRequestNo = CommonUtil.genRequestNo(sequence);
						requestNo.setRequestNo(idRequestNo);
						requestNo.setUrType(type);
						requestNo.setRequestList("1");
						requestNo.setRequestType("2");
						requestNo.setSubject("UR Termiante Expired By System");
						requestNo.setDetail("System Auto Gen UR Terminate Expired Application");
						requestNo.setRequestBy("SYSTEM_ADMIN");  
						requestNo.setUsername("");
						requestNo.setEnname("");
						requestNo.setEnsurname("");
						requestNo.setEmail("");
						requestNo.setMobile("");
						requestNo.setPhone("");
						requestNo.setPosition("");
						requestNo.setOrganize("");
						requestNo.setCompany("");
						requestNo.setRequestRemark("");
						requestNo.setCreatedBy(updateBy);
						requestNo.setUpdatedBy(updateBy);
						logger.info("CREATE REQUEST NO : {} , {} , {}",idRequestNo,requestNo,batchName);
						String orgCode = userRequestRepository.getOrgCodeByUserId(userId);
						String userType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(orgCode);
						logger.info("selectFlowIdBatchExpiredByTypeTer orgCode : {} ,userType : {} , {}",orgCode,userType,batchName);
						String flowId = selectFlowIdBatchExpiredByTypeTer(userType);
						List<Map<String, String>> flowIds = userRequestRepository.getListNodeFlowConfig(flowId);
						logger.info("OBJ_FLOW : {} , {} ",flowIds,batchName);
						List<String> appRoleIds = batchPeriodExpiredRepository.listRoleApplication(userId, type);
						logger.info("listRoleApplication : {} By userId:{},type:{} , {} ",appRoleIds,userId,type,batchName);
						Integer indexRun = 0;
						for(String appRoleId : appRoleIds){
							logger.info("getDetailAppByApprole By appRoleId:{} , {} ",appRoleId,batchName);
							Map<String,Object> detailApp = userRequestRepository.getDetailAppByApprole(appRoleId);
							UR ur = new UR();
							String idur = CommonUtil.genUrNo(idRequestNo, ++indexRun);
							ur.setUrId(idur);
							ur.setRequestNo(idRequestNo);
							ur.setAppId((String)detailApp.get("appId"));
							ur.setAppName((String) detailApp.get("appName"));
							ur.setAppRoleId((String) detailApp.get("appRoleId"));
							ur.setAppRoleName((String) detailApp.get("appRoleName"));
							ur.setRequestType("2");
							ur.setFlowId(flowId);
							ur.setStatus("1");
							ur.setRemark("UR Termiante Expired By System");
							ur.setUrFile("");
							ur.setCreatedBy(updateBy);
							ur.setUpdatedBy(updateBy);
							userRequestRepository.insertUr(ur);
							logger.info("MODEL UR : {} , {} ",ur,"Batch-Resign");
							if(flowIds.isEmpty()){
								logger.error("######### BREAK CAUSE FLOW WAS NULL #########");
								throw ServiceException.get500SystemError();
							}else{
								for(Map<String, String> flow :flowIds){
									if("5".equals(flow.get("flowNodeCode"))){
										logger.info("######## @NODE CUSTODIAN ####### ");
										Integer type_Approver_forCustodian = userRequestRepository.getTypeApproverforCustodian(appRoleId);
										listApproversforCustodian = userRequestService.selectApproverCustodianByType(type_Approver_forCustodian,appRoleId,orgCode);
										logger.info("OBJ_LIST_CUSTODIAN : {} , BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {}",listApproversforCustodian,type_Approver_forCustodian,appRoleId);
										if(listApproversforCustodian.isEmpty()){
											logger.error("########## LIST_CUSTODIAN IS NULL ############ BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {} , {}",type_Approver_forCustodian,appRoleId,batchName);
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
										urStep.setCreatedBy(updateBy);
										urStep.setUpdatedBy(updateBy);
										userRequestRepository.insertUrStep(urStep);
										logger.info("OBJ_UR_STEP : {} , BY UR_ID : {} , {}",urStep,idur,batchName);
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
												urStepApprove.setCreatedBy(updateBy);
												urStepApprove.setUpdatedBy(updateBy);
												urStepApprove.setSequence(userCustodian.getSeQuenceUser());
												urStepApprove.setTeamName(userCustodian.getTeamName());
												urStepApprove.setTeamSequence(userCustodian.getSeQuenceTeam());
												userRequestRepository.insertUrStepApprove(urStepApprove);
												logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {} , {}",urStepApprove,idur,batchName);
												try {
													mails.add(new String(userCustodian.getEmail().getBytes(),"UTF-8"));
												} catch (UnsupportedEncodingException e) {
													e.printStackTrace();
												}
											}
											UrForUser urForUser = new UrForUser(user);
											urForUser.setUrForUserId(CommonUtil.generateUUID());
											urForUser.setUrId(idur);
											urForUser.setUrStatus("1");
											urForUser.setCreatedBy(updateBy);
											urForUser.setUpdatedBy(updateBy);
											userRequestRepository.insertUrforUser(urForUser);
											logger.info("OBJ_UR_FOR_USER: {} , BY UR_ID : {} , USERNAME :{} ,{}",urForUser,idur,user.getUsername(),batchName);
											listApproversforCustodian=null;
										}
									}
								}
							}
						}
						try{
							Context variable = new Context();
							String header ="[ACIM] UR Termiante Expired By System";
							String dateMail = DateFormatUtils.format(Calendar.getInstance().getTime(), "dd/MM/yyyy");
							variable.setVariable("date",dateMail);
							variable.setVariable("userrequestNo",idRequestNo);
							variable.setVariable("urType",userType);
							variable.setVariable("requestType","2");
							variable.setVariable("requestUser",updateBy);
							variable.setVariable("nameUser",user.getEnfullname());
							variable.setVariable("subject","UR Termiante Expired By System");
							variable.setVariable("detail","System Auto Gen UR Terminate Expired Application");
							emailUtilService.send(mails, header, TEMPLATE_EMAIL_USERREQUEST, variable);
							logger.info("subject: {},To : {},username: {} ,{}",header,mails,user.getUsername(),batchName);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					 
				}
				batch = new Batch();
				batch.setBatchId(CommonUtil.generateUUID());
				batch.setBatchName("Batch-Expired");
				batch.setStatus("200"); 
				batch.setMessage("Sucess");
				batch.setRunTime(new Date());
				batch.setCreatedBy("SYSTEM-ADMIN");
				batch.setUpdatedBy("SYSTEM-ADMIN");
				batch.setCreatedDate(new Date());
				batch.setUpdatedDate(new Date());
				batchUserReOrgRepository.insertBatch(batch);
				logger.info("###### Completed process  {} , {} ######",batchName,date);
			}else{
				batch = new Batch();
				batch.setBatchId(CommonUtil.generateUUID());
				batch.setBatchName("Batch-Expired");
				batch.setStatus("200"); 
				batch.setMessage("Sucess");
				batch.setRunTime(new Date());
				batch.setCreatedBy("SYSTEM-ADMIN");
				batch.setUpdatedBy("SYSTEM-ADMIN");
				batch.setCreatedDate(new Date());
				batch.setUpdatedDate(new Date());
				batchUserReOrgRepository.insertBatch(batch);
				logger.info("###### Not found data from OM , {} ######",batchName);
				logger.info("###### Completed process  {} , {} ######",batchName,date);
				
			}
			
		}catch (Exception e) {
			batch = new Batch();
			batch.setStatus("201"); 
			batch.setMessage("Fail");
			batch.setBatchId(CommonUtil.generateUUID());
			batch.setBatchName("Batch-Expired");
			batch.setRunTime(new Date());
			batch.setCreatedBy("SYSTEM-ADMIN");
			batch.setUpdatedBy("SYSTEM-ADMIN");
			batch.setCreatedDate(new Date());
			batch.setUpdatedDate(new Date());
			batchUserReOrgRepository.insertBatch(batch);
			logger.error("###### Processed fail {} , {} ######",batchName,date);
			throw ServiceException.get500SystemError(e);
		}
		
		return batch;
	}
	
	public String selectFlowIdBatchExpiredByTypeTer(String userType) throws ServiceException{
		// special app
		String flowId = null;
			if("1".equals(userType)){
				flowId="F021";
			}else if("2".equals(userType)){
				flowId="F022";
			}else if("3".equals(userType)){
				flowId="F023";
			}else if("4".equals(userType)){
				flowId="F024";
			}else{
				//thrown
			}

		
	return flowId;
	}

}

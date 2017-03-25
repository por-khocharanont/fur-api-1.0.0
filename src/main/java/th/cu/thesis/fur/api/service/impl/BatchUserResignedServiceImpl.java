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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.thymeleaf.context.Context;

import th.cu.thesis.fur.api.model.RequestNo;
import th.cu.thesis.fur.api.model.UR;
import th.cu.thesis.fur.api.model.UrForUser;
import th.cu.thesis.fur.api.model.UrStep;
import th.cu.thesis.fur.api.model.UrStepApprove;
import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.model.UserCustodian;
import th.cu.thesis.fur.api.repository.BatchUserReOrgRepository;
import th.cu.thesis.fur.api.repository.BatchUserResignRepository;
import th.cu.thesis.fur.api.repository.UserRequestRepository;
import th.cu.thesis.fur.api.rest.model.Batch;
import th.cu.thesis.fur.api.rest.model.om.ResignedUserProfile;
import th.cu.thesis.fur.api.service.BatchUserResignedService;
import th.cu.thesis.fur.api.service.EmailUtilService;
import th.cu.thesis.fur.api.service.OmWebService;
import th.cu.thesis.fur.api.service.UserRequestService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;

@Service("batchUserResignedService")
public class BatchUserResignedServiceImpl implements BatchUserResignedService{
	
	private static final Logger logger = LoggerFactory.getLogger(BatchUserResignedServiceImpl.class);
	
	@Autowired
	OmWebService omWebService;
	
	@Autowired
	BatchUserResignRepository batchUserResignRepository;
	
	@Autowired
	BatchUserReOrgRepository batchUserReOrgRepository;
	
	@Autowired
	UserRequestRepository userRequestRepository;
	
	@Autowired
	UserRequestService userRequestService;
	
	@Autowired
	EmailUtilService emailUtilService;
	
//	@Autowired
//	TransactionTemplate transactionTemplate;
	
	@Autowired
	DataSourceTransactionManager transactionManager;
	
	static final String TEMPLATE_EMAIL_USERREQUEST ="email/requestNoTemplate";
	static final String TEMPLATE_EMAIL_ALERTRESIGN ="email/ResignedAlertTemplate";
	
	@Override
	public Batch updateUserResigned(String date) throws ServiceException { 
		Batch batch = null;
		final String batchName = "Batch-Resign";
		try {
		    if(StringUtils.isEmpty(date)||StringUtils.isBlank(date)){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				date = formatter.format(new Date());
			}
		    logger.info("###### Start {} : {} ######",batchName,date);
			//2015-06-16		
			List<ResignedUserProfile> userResign = omWebService.getUserResignedResponse(date).getResignedUserProfile();
			if(userResign == null){
				batch = new Batch();
				batch.setBatchId(CommonUtil.generateUUID());
				batch.setBatchName("Batch-Resign");
				batch.setStatus("200"); 
				batch.setMessage("Sucess");
				batch.setRunTime(new Date());
				batch.setCreatedBy("SYSTEM-ADMIN");
				batch.setUpdatedBy("SYSTEM-ADMIN");
				batch.setCreatedDate(new Date());
				batch.setUpdatedDate(new Date());
				batchUserReOrgRepository.insertBatch(batch);
				logger.info("Not found data from OM , {}",batchName);
				logger.info("###### End {} , {} ######",batchName,date);
			}else{
				for(ResignedUserProfile resignedUserProfile : userResign){
					logger.info("getUserByUsername , username:{} , {}",resignedUserProfile.getUsername(),batchName);
			    	final User user = userRequestRepository.getUserByUsername(resignedUserProfile.getUsername());
					final String date2 = date;
					if(user==null){
						logger.info("Cann't resign. because user not found in database {} ,username : {}",batchName,resignedUserProfile.getUsername());
					}else {
						final String userId = user.getUserId();
						final String pinCode = user.getPincode();
						logger.info("Terminate applications, {} , Username : {}",batchName,user.getUsername());
						TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
						transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
						transactionTemplate.execute(new TransactionCallbackWithoutResult() {
							@Override
							protected void doInTransactionWithoutResult(TransactionStatus transaction) {
								try {
								//Step 1 : Cancel UR
								logger.info("UpdateUrReject, pinCode:{},  {}",pinCode,batchName);
								batchUserResignRepository.UpdateUrReject(pinCode,"SYSTEM-ADMIN");
								logger.info("UpdateUrForUserReject, pinCode:{},  {}",pinCode,batchName);
								batchUserResignRepository.UpdateUrForUserReject(pinCode,"SYSTEM-ADMIN");
								//Step 2 : Terminate UR		
								terminateAllApplication(user,CommonUtil.textToDateForBatch(date2));
								//Step 3 : create data use in email
								
								//getEmailAdmin
								List<String> emailsAdmin =  batchUserResignRepository.listEmailAdminAcim();
								logger.info("Emails , {} , Username : {} , {}",emailsAdmin,user.getUsername(),batchName);
								//User's approve this UR list.
								List<String> approverUr = batchUserResignRepository.findUrApproverByUserResign(pinCode);
								logger.info("User's approve this Ur List, {} , Username : {} , {}",approverUr,user.getUsername(),batchName);
								Set<String> approverUrAll = new HashSet<String>();
								for(String approverUrList : approverUr){
									approverUrAll.add(new String(approverUrList.getBytes(),"UTF-8"));
								}
								
								//check user is RoleIden 
								List<String> roleIdenApprover = batchUserResignRepository.findRoleIdenByUserResign(userId);
								logger.info("Check user is RoleIden , {} , Username : {} , {}",!roleIdenApprover.isEmpty(),user.getUsername(),batchName);
								//User's was list application name   ส่งเมล
								Set<String> approverAppAll = new HashSet<String>();
								List<String> approverAppByAppOwner = batchUserResignRepository.findAppOwnerByUserResign(userId);
								List<String> approverAppByAppOwnerTeam = batchUserResignRepository.findAppOwnerTeamByUserResign(userId);	
								List<String> approverAppByCustodian = batchUserResignRepository.findCustodianByUserResign(userId);
								List<String> approverAppByCustodianTeam = batchUserResignRepository.findCustodianTeamByUserResign(userId);
								logger.info("Get applications that user is AppOwner , {} , Username : {} , {}",approverAppByAppOwner,user.getUsername(),batchName);
								logger.info("Get applications that user is AppOwnerTeam , {} , Username : {} , {}",approverAppByAppOwnerTeam,user.getUsername(),batchName);
								logger.info("Get applications that user is Custodian , {} , Username : {} , {}",approverAppByCustodian,user.getUsername(),batchName);
								logger.info("Get applications that user is CustodianTeam , {} , Username : {} , {}",approverAppByCustodianTeam,user.getUsername(),batchName);
								for(String approverAppByAppOwnerList : approverAppByAppOwner){
									approverAppAll.add(new String(approverAppByAppOwnerList.getBytes(),"UTF-8"));
								}
								
								for(String approverAppByAppOwnerTeamList : approverAppByAppOwnerTeam){
									approverAppAll.add(new String(approverAppByAppOwnerTeamList.getBytes(),"UTF-8"));
								}
								
								for(String approverAppByCustodianList : approverAppByCustodian){
									approverAppAll.add(new String(approverAppByCustodianList.getBytes(),"UTF-8"));
								}
								
								for(String approverAppByCustodianTeamList : approverAppByCustodianTeam){
									approverAppAll.add(new String(approverAppByCustodianTeamList.getBytes(),"UTF-8"));
								}
								
								//Step 4: Delete in table
								logger.info("deleteApproverByUserResign, pinCode:{},  {}",pinCode,batchName);
								batchUserResignRepository.deleteApproverByUserResign(pinCode); 
								logger.info("deleteUserUrApprover, pinCode:{},  {}",pinCode,batchName);
								batchUserResignRepository.deleteUserUrApprover(pinCode);
								
								logger.info("deleteUserAppOwner, pinCode:{},  {}",pinCode,batchName);
								batchUserResignRepository.deleteUserAppOwner(pinCode);
								logger.info("deleteUserAppOwnerMember, userId:{},  {}",userId,batchName);
								batchUserResignRepository.deleteUserAppOwnerMember(userId);
								
								logger.info("deleteUserCustodian, userId:{},  {}",userId,batchName);
								batchUserResignRepository.deleteUserCustodian(userId);
								logger.info("deleteUserCustodianMember, userId:{},  {}",userId,batchName);
								batchUserResignRepository.deleteUserCustodianMember(userId);
								
								logger.info("deleteUserRoleIden, userId:{},  {}",userId,batchName);
								batchUserResignRepository.deleteUserRoleIden(userId);
								logger.info("deleteUserToken, userId:{},  {}",userId,batchName);
								batchUserResignRepository.deleteUserToken(userId);
								
								logger.info("deleteUserAcimRole, userId:{},  {}",userId,batchName);
								batchUserResignRepository.deleteUserAcimRole(userId);
								logger.info("deleteUserAppRole, userId:{},  {}",userId,batchName);
								batchUserResignRepository.deleteUserAppRole(userId);
								logger.info("deleteUserResign, pinCode:{},  {}",pinCode,batchName);
								batchUserResignRepository.deleteUserResign(pinCode);
								
								logger.info("Removed user on tables, Username : {} , {}",user.getUsername(),batchName);
								
								if(!approverAppAll.isEmpty()||!approverUr.isEmpty()||!roleIdenApprover.isEmpty()){
									Context variable = new Context();
									String header ="[ACIM] แจ้งเตือน Approver ลาออก";
									try{
										variable.setVariable("username",user.getUsername());
										variable.setVariable("applications",approverAppAll);
										variable.setVariable("urs",approverUr);
										variable.setVariable("isRoleIden",roleIdenApprover); 
										emailUtilService.send(emailsAdmin, header, TEMPLATE_EMAIL_ALERTRESIGN, variable);
										logger.info("subject: {},To : {},username: {} ,{}",header,emailsAdmin,user.getUsername(),"Batch-Resign");
									}catch(Exception e){
										e.printStackTrace();
									}
								}
									
								} catch (Exception e1) {
									try {
										transaction.setRollbackOnly();
										throw ServiceException.get500SystemError(e1);
									} catch (ServiceException e) {
										e.printStackTrace();
									}
								}
							}
						});
					}
				}
				batch = new Batch();
				batch.setBatchId(CommonUtil.generateUUID());
				batch.setBatchName("Batch-Resign");
				batch.setStatus("200"); 
				batch.setMessage("Sucess");
				batch.setRunTime(new Date());
				batch.setCreatedBy("SYSTEM-ADMIN");
				batch.setUpdatedBy("SYSTEM-ADMIN");
				batch.setCreatedDate(new Date());
				batch.setUpdatedDate(new Date());
				batchUserReOrgRepository.insertBatch(batch);
				logger.info("###### Completed process  {} , {} ######",batchName,date);
			}
		} catch (Exception e) {
			batch = new Batch();
			batch.setBatchId(CommonUtil.generateUUID());
			batch.setBatchName("Batch-Resign");
			batch.setStatus("201"); 
			batch.setMessage("Fail");
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
	
	@Override
	public void terminateAllApplication(User user,Date date) throws ServiceException {
		Set<String> mails = new HashSet<String>();
		String updateBy = "SYSTEM-ADMIN";
		List<UserCustodian> listApproversforCustodian;	
		String orgCode = user.getOrgcode();
		String userName = user.getUsername();
		String idRequestNo;
		List<String> types = batchUserResignRepository.listTypeApplication(userName, date);
		logger.info("User's type applications, {} ,username:{},date:{} ","Batch-Resign",userName,date);
		for(String type:types){
			RequestNo requestNo = new RequestNo();
			Integer sequence = userRequestRepository.getSequenceRequestNo();
			idRequestNo = CommonUtil.genRequestNo(sequence);
			requestNo.setRequestNo(idRequestNo);
			requestNo.setRequestList("1");
			requestNo.setRequestType("2");
			requestNo.setUrType(type);
			requestNo.setSubject("UR Termiante Resign By System");
			requestNo.setDetail("System Auto Gen UR Terminate Resign Application");
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
			logger.info("CREATE REQUEST NO : {} , {} , {}",idRequestNo,requestNo,"Batch-Resign");
			String userType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(orgCode);
			String flowId = selectFlowIdBatchReSignByTypeTer(userType);
			List<Map<String, String>> flowIds = userRequestRepository.getListNodeFlowConfig(flowId);
			logger.info("OBJ_FLOW : {} , {} ",flowIds,"Batch-Resign");
			Integer indexRun = 0;
			List<String> appRoles = batchUserResignRepository.allApplicationByUser(user,type,date);
			logger.info("list approle : {} , {} ",appRoles,"Batch-Resign");
			for(String appRoleId : appRoles){
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
				ur.setRemark("UR Termiante Resign By System");
				ur.setUrFile("");
				ur.setCreatedBy(updateBy);
				ur.setUpdatedBy(updateBy);
				userRequestRepository.insertUr(ur);
				logger.info("MODEL UR : {} , {} ",ur,"Batch-Resign");
				if(flowIds.isEmpty()){
					logger.error("######### BREAK CAUSE FLOW WAS NULL ######### ,{}","Batch-Resign");
					throw ServiceException.get500SystemError();
				}else{
					for(Map<String, String> flow :flowIds){
						if("5".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE CUSTODIAN ####### ,{}","Batch-Resign");
							Integer type_Approver_forCustodian = userRequestRepository.getTypeApproverforCustodian(appRoleId);
							listApproversforCustodian = userRequestService.selectApproverCustodianByType(type_Approver_forCustodian,appRoleId,orgCode);
							logger.info("OBJ_LIST_CUSTODIAN : {} , BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {} ,{}",listApproversforCustodian,type_Approver_forCustodian,appRoleId,"Batch-Resign");
							if(listApproversforCustodian.isEmpty()){
								logger.error("########## LIST_CUSTODIAN IS NULL ############ BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {} , {}",type_Approver_forCustodian,appRoleId,"Batch-Resign");
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
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {} , {}",urStep,idur,"Batch-Resign");
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
									try {
										mails.add(new String(userCustodian.getEmail().getBytes(),"UTF-8"));
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {} , {}",urStepApprove,idur,"Batch-Resign");
								}
								UrForUser urForUser = new UrForUser(user);
								urForUser.setUrForUserId(CommonUtil.generateUUID());
								urForUser.setUrId(idur);
								urForUser.setUrStatus("1");
								urForUser.setCreatedBy(updateBy);
								urForUser.setUpdatedBy(updateBy);
								userRequestRepository.insertUrforUser(urForUser);
								logger.info("OBJ_UR_FOR_USER: {} , BY UR_ID : {} , USERNAME :{} ,{}",urForUser,idur,user.getUsername(),"Batch-Resign");
								listApproversforCustodian=null;
							}
						}
					}
				}
			}
			requestNo.setUrType(userType); 
			userRequestRepository.insertRequestNo(requestNo);
			Context variable = new Context();
			String header ="[ACIM] User Request :"+idRequestNo;
			try{
				String dateMail = DateFormatUtils.format(Calendar.getInstance().getTime(), "dd/MM/yyyy");
				variable.setVariable("date",dateMail);
				variable.setVariable("userrequestNo",idRequestNo);
				variable.setVariable("urType",userType);
				variable.setVariable("requestType","2");
				variable.setVariable("requestUser",updateBy);
				variable.setVariable("nameUser",user.getEnfullname());
				variable.setVariable("subject","[ACIM] UR Termiante Resign By System");
				variable.setVariable("detail","");
				emailUtilService.send(mails, header, TEMPLATE_EMAIL_USERREQUEST, variable);
				logger.info("subject: {},To : {},username: {} ,{}",header,mails,user.getUsername(),"Batch-Resign");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public String selectFlowIdBatchReSignByTypeTer(String userType) throws ServiceException{
		// special app
		String flowId = null;
			if("1".equals(userType)){
				flowId="F017";
			}else if("2".equals(userType)){
				flowId="F018";
			}else if("3".equals(userType)){
				flowId="F019";
			}else if("4".equals(userType)){
				flowId="F020";
			}else{
				//thrown
			}

		
	return flowId;
	}
	


}

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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import th.cu.thesis.fur.api.model.OrgFromAcim;
import th.cu.thesis.fur.api.model.RequestNo;
import th.cu.thesis.fur.api.model.UR;
import th.cu.thesis.fur.api.model.UrForUser;
import th.cu.thesis.fur.api.model.UrStep;
import th.cu.thesis.fur.api.model.UrStepApprove;
import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.model.UserCustodian;
import th.cu.thesis.fur.api.repository.BatchUserReOrgRepository;
import th.cu.thesis.fur.api.repository.UserRequestRepository;
import th.cu.thesis.fur.api.rest.model.Batch;
import th.cu.thesis.fur.api.rest.model.om.ListUserReOrgResponse;
import th.cu.thesis.fur.api.rest.model.om.UserReOrg;
import th.cu.thesis.fur.api.service.BatchUserReorgService;
import th.cu.thesis.fur.api.service.EmailUtilService;
import th.cu.thesis.fur.api.service.OmWebService;
import th.cu.thesis.fur.api.service.UserRequestService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;

@Service("batchUserReOrgService")
public class BatchUserReOrgServiceImpl implements BatchUserReorgService{
	@Autowired
	OmWebService omWebService;
	@Autowired
	UserRequestRepository userRequestRepository;
	@Autowired
	UserRequestService userRequestService;
	@Autowired
	BatchUserReOrgRepository batchUserReOrgRepository;
	@Autowired
	EmailUtilService emailUtilService;
	private static final Logger logger = LoggerFactory.getLogger(BatchUserReOrgServiceImpl.class);
	static final String TEMPLATE_EMAIL_USERREQUEST ="email/requestNoTemplate";
	
	@Override
	@Transactional(rollbackFor=ServiceException.class,propagation=Propagation.REQUIRED)
	public Batch userReOrg(String dateTime) throws ServiceException { 
		
		Batch batch ;
		final String batchName = "Batch-Reorg";
		try{
			if(StringUtils.isEmpty(dateTime)||StringUtils.isBlank(dateTime)){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				dateTime = formatter.format(new Date());
			}
			logger.info("###### Start {} : {} ######",batchName,dateTime);
			ListUserReOrgResponse res = omWebService.getUserReOrg(dateTime);
			Date date = CommonUtil.textToDateForBatch(dateTime);
			if(res == null){
				batch = new Batch();
				batch.setBatchId(CommonUtil.generateUUID());
				batch.setBatchName("Batch-Reorg");
				batch.setStatus("201"); 
				batch.setMessage("Fail");
				batch.setRunTime(new Date());
				batch.setCreatedBy("SYSTEM-ADMIN");
				batch.setUpdatedBy("SYSTEM-ADMIN");
				batch.setCreatedDate(new Date());
				batch.setUpdatedDate(new Date());
				batchUserReOrgRepository.insertBatch(batch);
				logger.info("Not found data from OM , {}",batchName);
				logger.info("###### Completed process  {} , {} ######",batchName,dateTime);
			}else{
				List<UserReOrg> userReorgs = res.getUserReOrg();
				if(userReorgs != null){
					for(UserReOrg userReOrg : userReorgs ){
						logger.info("selectOrg NewOrgCode: {} , {} ",userReOrg.getNewOrgCode(),batchName);
						OrgFromAcim orgFromAcim = batchUserReOrgRepository.selectOrg(userReOrg.getNewOrgCode());
						logger.info("OBJ_OrgFromAcim : {} , {} ",orgFromAcim,batchName);
						String pin = userReOrg.getPin();
						String oldOrgCode = userReOrg.getOldOrgCode();
						String newOrgCode = orgFromAcim.getOrgcode();
						String orgName = orgFromAcim.getOrgname();
						String orgDesc = orgFromAcim.getOrgdesc();
						String orgLevel = orgFromAcim.getOrglevel();
						logger.info("getUserByUsername useruame: {} , {} ",userReOrg.getUsername(),batchName);
						User user = userRequestRepository.getUserByUsername(userReOrg.getUsername());
						logger.info("OBJ_User : {} , {} ",user,batchName);
						logger.info("getOrgtypeOrUserTypeByOrgcode Oldorgcode : {} , {} ",oldOrgCode,batchName);
						String oldOrgType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(oldOrgCode);
						logger.info("getOrgtypeOrUserTypeByOrgcode newOrgCode : {} , {} ",newOrgCode,batchName);
						String orgType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(newOrgCode);
						logger.info("OldOrgType : {} ,NewOrgType : {} ,{} {} ",oldOrgType,orgType,userReOrg.getUsername(),batchName);
						if(user == null){
							logger.info("Not found user in database ,username : {} , {}",userReOrg.getUsername(),batchName);
						}else if(StringUtils.isBlank(oldOrgType) || StringUtils.isEmpty(oldOrgType)){
							logger.info("Old Orgtype is null : {} , {}",userReOrg.getUsername(),batchName);
						}else if(StringUtils.isBlank(orgType) || StringUtils.isEmpty(orgType)){
							logger.info("New Orgtype is null : {} , {}",userReOrg.getUsername(),batchName);
						}else if (oldOrgCode.equals(newOrgCode)){
							logger.info("New or Old Orgtype is same : {} , {}",userReOrg.getUsername(),batchName);
						}else{
							clearStatusUrProcess(user);
							logger.info("Clear URs on flow and reject URs that User requested Ur : {} , {}",userReOrg.getUsername(),batchName);
							if(oldOrgType.equals(orgType)){
								terminateSameOrgType(user,newOrgCode,date);
								logger.info("Terminate old application don't have in new oraganize : {} , {}",userReOrg.getUsername(),batchName);
							}else{
								terminateAllApplication(user,date);
								logger.info("Terminate all application: {} , {}",userReOrg.getUsername(),batchName);
							}
							batchUserReOrgRepository.UpdateOrgcode(pin, oldOrgCode, newOrgCode, orgName, orgDesc, orgLevel);
							logger.info("Update organize user on table user : {} , {}",userReOrg.getUsername(),batchName);
						}
					}
					batch = new Batch();
					batch.setBatchId(CommonUtil.generateUUID());
					batch.setBatchName("Batch-Reorg");
					batch.setStatus("200"); 
					batch.setMessage("Sucess");
					batch.setRunTime(new Date());
					batch.setCreatedBy("SYSTEM-ADMIN");
					batch.setUpdatedBy("SYSTEM-ADMIN");
					batch.setCreatedDate(new Date());
					batch.setUpdatedDate(new Date());
					batchUserReOrgRepository.insertBatch(batch);
					logger.info("###### Completed process  {} , {} ######",batchName,dateTime);
				}else{
					batch = new Batch();
					batch.setBatchId(CommonUtil.generateUUID());
					batch.setBatchName("Batch-Reorg");
					batch.setStatus("200"); 
					batch.setMessage("Sucess");
					batch.setRunTime(new Date());
					batch.setCreatedBy("SYSTEM-ADMIN");
					batch.setUpdatedBy("SYSTEM-ADMIN");
					batch.setCreatedDate(new Date());
					batch.setUpdatedDate(new Date());
					batchUserReOrgRepository.insertBatch(batch);
					logger.info("Not found data from OM , {}",batchName);
					logger.info("###### Completed process  {} , {} ######",batchName,dateTime);
				}
			}
		}catch(Exception e){
			batch = new Batch();
			batch.setBatchId(CommonUtil.generateUUID());
			batch.setBatchName("Batch-Reorg");
			batch.setStatus("201"); 
			batch.setMessage("Fail");
			batch.setRunTime(new Date());
			batch.setCreatedBy("SYSTEM-ADMIN");
			batch.setUpdatedBy("SYSTEM-ADMIN");
			batch.setCreatedDate(new Date());
			batch.setUpdatedDate(new Date());
			batchUserReOrgRepository.insertBatch(batch);
			logger.info("###### Processed fail {} , {} ######",batchName,dateTime);
			throw ServiceException.get500SystemError(e);
		}
		return batch;
	}

	@Override
	public void terminateSameOrgType(User user,String newOrgCode,Date date)throws ServiceException {
		Set<String> mails = new HashSet<String>();
		String updateBy = "SYSTEM-ADMIN";
		List<UserCustodian> listApproversforCustodian;	
		String orgCode = user.getOrgcode();
		String userName = user.getUsername();
		String idRequestNo;
		List<String> types = batchUserReOrgRepository.listTypeApplicationWhenChangeOraganize(newOrgCode, userName, date);
		logger.info("User's type applications By NewOrgCode:{} , {} ,username:{},date:{} ",newOrgCode,"Batch-Reorg",userName,date);
		for(String type : types){
			RequestNo requestNo = new RequestNo();
			Integer sequence = userRequestRepository.getSequenceRequestNo();
			idRequestNo = CommonUtil.genRequestNo(sequence);
			requestNo.setRequestNo(idRequestNo);
			requestNo.setUrType(type);
			requestNo.setRequestList("1");
			requestNo.setRequestType("2");
			requestNo.setSubject("UR Termiante Re-Org By System");
			requestNo.setDetail("System Auto Gen UR Terminate Re-Org Application");
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
			requestNo.setUrType(type); 
			userRequestRepository.insertRequestNo(requestNo);
			logger.info("CREATE REQUEST NO : {} , {} , {}",idRequestNo,requestNo,"Batch-Reorg");
			String userType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(orgCode);
			String flowId = selectFlowIdBatchReOrgByTypeTer(userType);
			List<Map<String, String>> flowIds = userRequestRepository.getListNodeFlowConfig(flowId);
			logger.info("OBJ_FLOW : {} , {} ",flowIds,"Batch-Reorg");
			Integer indexRun = 0;
			logger.info("List different application WhenChangeOraganize newOrgCode:{}, userName:{}, type:{}, date:{}, {} ",newOrgCode,userName,type,date,"Batch-Reorg");
			List<String> appRoles = batchUserReOrgRepository.DifferentListApplicationWhenChangeOraganize(newOrgCode,userName,type,date);
			logger.info("list approle : {} , {} ",appRoles,"Batch-Reorg");
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
				ur.setRemark("UR Termiante Re-Org By System");
				ur.setUrFile("");
				ur.setCreatedBy(updateBy);
				ur.setUpdatedBy(updateBy);
				userRequestRepository.insertUr(ur);
				logger.info("MODEL UR : {} , {} ",ur,"Batch-Reorg");
				if(flowIds.isEmpty()){
					logger.error("######### BREAK CAUSE FLOW WAS NULL #########");
					throw ServiceException.get500SystemError();
				}else{
					for(Map<String, String> flow :flowIds){
						if("5".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE CUSTODIAN ####### ");
							Integer type_Approver_forCustodian = userRequestRepository.getTypeApproverforCustodian(appRoleId);
							listApproversforCustodian = userRequestService.selectApproverCustodianByType(type_Approver_forCustodian,appRoleId,orgCode);
							logger.info("OBJ_LIST_CUSTODIAN : {} , BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {} , {}",type_Approver_forCustodian,type_Approver_forCustodian,appRoleId,"Batch-Reorg");
							if(listApproversforCustodian.isEmpty()){
								logger.error("########## LIST_CUSTODIAN IS NULL ############ BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {} , {}",type_Approver_forCustodian,appRoleId,"Batch-Reorg");
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
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {} , {}",urStep,idur,"Batch-Reorg");
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
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {} , {}",urStepApprove,idur,"Batch-Reorg");
								}
								UrForUser urForUser = new UrForUser(user);
								urForUser.setUrForUserId(CommonUtil.generateUUID());
								urForUser.setUrId(idur);
								urForUser.setUrStatus("1");
								urForUser.setCreatedBy(updateBy);
								urForUser.setUpdatedBy(updateBy);
								userRequestRepository.insertUrforUser(urForUser);
								logger.info("OBJ_UR_FOR_USER: {} , BY UR_ID : {} , USERNAME :{} ,{}",urForUser,idur,user.getUsername(),"Batch-Reorg");
								listApproversforCustodian=null;
							}
						}
					}
				}
			}
			
			
			
			try{
				Context variable = new Context();
				String header ="[ACIM] User Request :"+idRequestNo;
				variable.setVariable("date",date);
				variable.setVariable("userrequestNo",idRequestNo);
				variable.setVariable("urType",userType);
				variable.setVariable("requestType","2");
				variable.setVariable("requestUser",updateBy);
				variable.setVariable("nameUser",user.getEnfullname());
				variable.setVariable("subject","[ACIM] UR Termiante Re-Org By System");
				variable.setVariable("detail","");
				emailUtilService.send(mails, header, TEMPLATE_EMAIL_USERREQUEST, variable);
				logger.info("subject: {},To : {},username: {} ,{}",header,mails,user.getUsername(),"Batch-ReOrg");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}
	
	public String selectFlowIdBatchReOrgByTypeTer(String userType) throws ServiceException{
		// special app
		String flowId = null;
			if("1".equals(userType)){
				flowId="F025";
			}else if("2".equals(userType)){
				flowId="F026";
			}else if("3".equals(userType)){
				flowId="F027";
			}else if("4".equals(userType)){
				flowId="F028";
			}else{
				//thrown
			}

		
	return flowId;
	}

	@Override
	public void clearStatusUrProcess(User user) throws ServiceException {
		batchUserReOrgRepository.clearUrForUserStatusOnProcessByUser(user);
		batchUserReOrgRepository.clearUrStatusOnProcessByUser(user);
		batchUserReOrgRepository.closeSetRejectURGroup(user);
	}

	@Override
	public void terminateAllApplication(User user,Date date) throws ServiceException {
		Set<String> mails = new HashSet<String>();
		String updateBy = "SYSTEM-ADMIN";
		List<UserCustodian> listApproversforCustodian;	
		String orgCode = user.getOrgcode();
		String userName = user.getUsername();
		String idRequestNo;
		List<String> types = batchUserReOrgRepository.listTypeApplicationWhenChangeOrgtype(userName, date);
		logger.info("User's type applications By OldOrgCode:{} , {} ,username:{},date:{} ",orgCode,"Batch-Reorg",userName,date);
		for(String type:types){
			RequestNo requestNo = new RequestNo();
			Integer sequence = userRequestRepository.getSequenceRequestNo();
			idRequestNo = CommonUtil.genRequestNo(sequence);
			requestNo.setRequestNo(idRequestNo);
			requestNo.setRequestList("1");
			requestNo.setRequestType("2");
			requestNo.setUrType(type);
			requestNo.setSubject("UR Termiante Re-Org By System");
			requestNo.setDetail("System Auto Gen UR Terminate Re-Org Application");
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
			logger.info("CREATE REQUEST NO : {} , {} , {}",idRequestNo,requestNo,"Batch-Reorg");
			String userType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(orgCode);
			String flowId = selectFlowIdBatchReOrgByTypeTer(userType);
			List<Map<String, String>> flowIds = userRequestRepository.getListNodeFlowConfig(flowId);
			logger.info("OBJ_FLOW : {} , {} ",flowIds,"Batch-Reorg");
			Integer indexRun = 0;
			List<String> appRoles = batchUserReOrgRepository.allApplicationByUser(user,type,date);
			logger.info("list approle : {} , {} ",appRoles,"Batch-Reorg");
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
				ur.setRemark("UR Termiante Re-Org By System");
				ur.setUrFile("");
				ur.setCreatedBy(updateBy);
				ur.setUpdatedBy(updateBy);
				userRequestRepository.insertUr(ur);
				logger.info("MODEL UR : {} , {} ",ur,"Batch-Reorg");
				if(flowIds.isEmpty()){
					logger.error("######### BREAK CAUSE FLOW WAS NULL #########");
					throw ServiceException.get500SystemError();
				}else{
					for(Map<String, String> flow :flowIds){
						if("5".equals(flow.get("flowNodeCode"))){
							logger.info("######## @NODE CUSTODIAN ####### ");
							Integer type_Approver_forCustodian = userRequestRepository.getTypeApproverforCustodian(appRoleId);
							listApproversforCustodian = userRequestService.selectApproverCustodianByType(type_Approver_forCustodian,appRoleId,orgCode);
							logger.info("OBJ_LIST_CUSTODIAN : {} , BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {} , {}",type_Approver_forCustodian,type_Approver_forCustodian,appRoleId,"Batch-Reorg");
							if(listApproversforCustodian.isEmpty()){
								logger.error("########## LIST_CUSTODIAN IS NULL ############ BY TYPE_APPROVER_FOR_CUSTODIAN : {} , APPROLEID : {} , {}",type_Approver_forCustodian,appRoleId,"Batch-Reorg");
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
							logger.info("OBJ_UR_STEP : {} , BY UR_ID : {} , {}",urStep,idur,"Batch-Reorg");
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
									logger.info("OBJ_URSTEPAPPROVE: {} , BY UR_ID : {} , {}",urStepApprove,idur,"Batch-Reorg");
								}
								UrForUser urForUser = new UrForUser(user);
								urForUser.setUrForUserId(CommonUtil.generateUUID());
								urForUser.setUrId(idur);
								urForUser.setUrStatus("1");
								urForUser.setCreatedBy(updateBy);
								urForUser.setUpdatedBy(updateBy);
								userRequestRepository.insertUrforUser(urForUser);
								logger.info("OBJ_UR_FOR_USER: {} , BY UR_ID : {} , USERNAME :{} ,{}",urForUser,idur,user.getUsername(),"Batch-Reorg");
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
				variable.setVariable("subject","[ACIM] UR Termiante Re-Org By System");
				variable.setVariable("detail","");
				emailUtilService.send(mails, header, TEMPLATE_EMAIL_USERREQUEST, variable);
				logger.info("subject: {},To : {},username: {} ,{}",header,mails,user.getUsername(),"Batch-ReOrg");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
				
	}
}

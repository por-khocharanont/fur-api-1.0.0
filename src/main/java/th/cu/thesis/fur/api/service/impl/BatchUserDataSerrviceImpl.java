package th.cu.thesis.fur.api.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.repository.BatchUserDataServiceRepository;
import th.cu.thesis.fur.api.repository.BatchUserReOrgRepository;
import th.cu.thesis.fur.api.repository.UserRequestRepository;
import th.cu.thesis.fur.api.rest.model.Batch;
import th.cu.thesis.fur.api.rest.model.om.Employee;
import th.cu.thesis.fur.api.rest.model.om.ListEmpOmResponse;
import th.cu.thesis.fur.api.service.BatchUserDataService;
import th.cu.thesis.fur.api.service.OmWebService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;

@Service("batchUserDataService")
public class BatchUserDataSerrviceImpl implements  BatchUserDataService {
	
	@Autowired
	OmWebService omWebService;
	
	@Autowired
	UserRequestRepository userRequestRepository;
	
	@Autowired
	BatchUserDataServiceRepository batchUserDataServiceRepository;
	
	@Autowired
	BatchUserReOrgRepository batchUserReOrgRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(BatchUserAllProfileServiceImpl.class);
	
	@Override
	public Batch insertUserProfile() throws ServiceException {
		Batch batch;
		String createdBy = "SYSTEM-ADMIN";
		String batchName = "Batch-Inituser";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String date = formatter.format(new Date());
		String status = "1"; //Active
		logger.info("###### Start {} : {} ######",batchName,date);
		try {
			ListEmpOmResponse res = omWebService.getAllUserProfile();
			if(res != null){
				List<Employee> employees = res.getEmpList();
				logger.info("List Employee : {} , {} , {}",employees,batchName,date);
				if(employees!=null){
					for(Employee employee : employees  ){
							logger.info("Obj Employee : {} , {} , {}",employee,batchName,date);
							logger.info("getUserByUsername username : {} , {} , {}",employee.getUserName(),batchName,date);
							User isUser = userRequestRepository.getUserByUsername(employee.getUserName());
						if (isUser == null){
							User user = new User();
							user.setUserId(CommonUtil.generateUUID());
							user.setPincode(employee.getPin());
							user.setUsername(employee.getUserName());
							user.setEmail(employee.getEmail());
							user.setEnname(employee.getEnName());
							user.setEnsurname(employee.getEnSurName());
							user.setEnfullname(employee.getEnFullName());
							user.setThname(employee.getThName());
							user.setThsurname(employee.getThSurName());
							user.setThfullname(employee.getThFullName());
							user.setOrgcode(employee.getOrgCode());
							user.setOrgname(employee.getOrgName());
							user.setOrgdesc(employee.getOrgDesc());
							user.setOrglevel(employee.getOrgLevel());
							user.setFccode(employee.getFcCode());
							user.setFcname(employee.getFcName());
							user.setFcdesc(employee.getFcDesc());
							user.setFchcode(employee.getFchCode());
							user.setFchname(employee.getFchName());
							user.setFchdesc(employee.getFchDesc());
							user.setSccode(employee.getScCode());
							user.setScname(employee.getScName());
							user.setScdesc(employee.getScDesc());
							user.setSchcode(employee.getSchCode());
							user.setSchname(employee.getSchName());
							user.setSchdesc(employee.getSchDesc());
							user.setDpcode(employee.getDpCode());
							user.setDpname(employee.getDpName());
							user.setDpdesc(employee.getDpDesc());
							user.setDphcode(employee.getDphCode());
							user.setDphname(employee.getDphName());
							user.setDphdesc(employee.getDphDesc());
							user.setBucode(employee.getBuCode());
							user.setBuname(employee.getBuName());
							user.setBudesc(employee.getBuDesc());
							user.setBuhcode(employee.getBuhCode());
							user.setBuhname(employee.getBuhName());
							user.setBuhdesc(employee.getBuhDesc());
							user.setCocode(employee.getCoCode());
							user.setConame(employee.getCoName());
							user.setPhone(employee.getPhone());
							user.setMobile(employee.getMobile());
							user.setPositionId(employee.getPositionId());
							user.setPosition(employee.getPosition());
							user.setManagerPin(employee.getManagerPin());
							user.setManagerUsername(employee.getManagerUserName());
							user.setManagerEmail(employee.getManagerEmail());
							user.setManagerEnname(employee.getManagerEnName());
							user.setManagerEnsurname(employee.getManagerEnSurName());
							user.setStatus(status);
							user.setCreatedBy(createdBy);
							user.setUpdatedBy(createdBy);
							logger.info("Obj User : {} , {} , {}",user,batchName,date);
							batchUserDataServiceRepository.insertUser(user);
						}else{
							logger.info("exist user on table User : {} , {} , {}",isUser,batchName,date);
						}
					}
					batch = new Batch();
					batch.setBatchId(CommonUtil.generateUUID());
					batch.setBatchName("Batch-Inituser");
					batch.setStatus("200"); 
					batch.setMessage("Sucess");
					batch.setRunTime(new Date());
					batch.setCreatedBy("SYSTEM-ADMIN");
					batch.setUpdatedBy("SYSTEM-ADMIN");
					batch.setCreatedDate(new Date());
					batch.setUpdatedDate(new Date());
					logger.info("###### Completed Process {} , {} ######",batchName,date);
				}else{
					batch = new Batch();
					batch.setBatchId(CommonUtil.generateUUID());
					batch.setBatchName("Batch-Inituser");
					batch.setStatus("200"); 
					batch.setMessage("Sucess");
					batch.setRunTime(new Date());
					batch.setCreatedBy("SYSTEM-ADMIN");
					batch.setUpdatedBy("SYSTEM-ADMIN");
					batch.setCreatedDate(new Date());
					batch.setUpdatedDate(new Date());
					logger.info("Not found data from OM , {}",batchName);
					logger.info("###### Completed Process {} , {} ######",batchName,date);
				}
			}else{
				batch = new Batch();
				batch.setBatchId(CommonUtil.generateUUID());
				batch.setBatchName("Batch-Inituser");
				batch.setStatus("200"); 
				batch.setMessage("Sucess");
				batch.setRunTime(new Date());
				batch.setCreatedBy("SYSTEM-ADMIN");
				batch.setUpdatedBy("SYSTEM-ADMIN");
				batch.setCreatedDate(new Date());
				batch.setUpdatedDate(new Date());
				logger.info("Not found data from OM , {}",batchName);
				logger.info("###### Completed Process {} , {} ######",batchName,date);
			}		
			
		} catch (Exception e) {
			batch = new Batch();
			batch.setBatchId(CommonUtil.generateUUID());
			batch.setBatchName("Batch-Inituser");
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

}

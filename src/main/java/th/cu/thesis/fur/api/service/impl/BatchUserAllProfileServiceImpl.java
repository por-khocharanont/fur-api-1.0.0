package th.cu.thesis.fur.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.cu.thesis.fur.api.repository.BatchUpdateAllUserRepository;
import th.cu.thesis.fur.api.repository.BatchUserReOrgRepository;
import th.cu.thesis.fur.api.rest.model.Batch;
import th.cu.thesis.fur.api.rest.model.om.Employee;
import th.cu.thesis.fur.api.rest.model.om.ListEmpOmResponse;
import th.cu.thesis.fur.api.service.BatchUserAllProfileService;
import th.cu.thesis.fur.api.service.OmWebService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;

@Service("batchUserAllProfileService")
public class BatchUserAllProfileServiceImpl implements BatchUserAllProfileService{
	
	private static final Logger logger = LoggerFactory.getLogger(BatchUserAllProfileServiceImpl.class);

	@Autowired
	OmWebService omWebService;
	
	@Autowired
	BatchUpdateAllUserRepository batchUpdateAllUserRepository;
	
	@Autowired
	BatchUserReOrgRepository batchUserReOrgRepository;
	
	@Override
	public Batch updatetAllUserProfile() throws ServiceException {
		
		Batch batch;
		String batchName = "Batch-UpdateProfileUser";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String date = formatter.format(new Date());
		try {
			logger.info("###### Start {} : {} ######",batchName,date);
			ListEmpOmResponse res = omWebService.getAllUserProfile();
			if(res == null){
				batch = new Batch();
				batch.setBatchId(CommonUtil.generateUUID());
				batch.setBatchName("Batch-UpdateProfileUser");
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
				List<Employee> employees = res.getEmpList();
				logger.info("Obj list employee : {} , {} , {}",employees,batchName,date);
				if(employees!=null){
					for(Employee emp : employees ){
						logger.info("Obj employee : {} , {} , {}",emp,batchName,date);
						Employee employee = new Employee();
						employee.setPin(emp.getPin());
						employee.setUserName(emp.getUserName());
						employee.setEmail(emp.getEmail());
						employee.setEnName(emp.getEnName());
						employee.setEnSurName(emp.getEnSurName());
						employee.setEnFullName(emp.getEnFullName());
						employee.setThName(emp.getThName());
						employee.setThSurName(emp.getThSurName());
						employee.setOrgCode(emp.getOrgCode());
						employee.setOrgName(emp.getOrgName());
						employee.setOrgDesc(emp.getOrgDesc());
						employee.setOrgLevel(emp.getOrgLevel());
						employee.setFcCode(emp.getFcCode());
						employee.setFcName(emp.getFcName());
						employee.setFcDesc(emp.getFcDesc());
						employee.setFchCode(emp.getFchCode());
						employee.setFchName(emp.getFchName());
						employee.setFchDesc(emp.getFchDesc());
						employee.setScCode(emp.getScCode());
						employee.setScName(emp.getScName());
						employee.setScDesc(emp.getScDesc());
						employee.setSchCode(emp.getSchCode());
						employee.setSchName(emp.getSchName());
						employee.setSchDesc(emp.getSchDesc());
						employee.setDpCode(emp.getDpCode());
						employee.setDpDesc(emp.getDpDesc());
						employee.setDpName(emp.getDpName());
						employee.setDphCode(emp.getDphCode());
						employee.setDphName(emp.getDphName());
						employee.setDphDesc(emp.getDphDesc());
						employee.setBuCode(emp.getBuCode());
						employee.setBuName(emp.getBuName());
						employee.setBuDesc(emp.getBuDesc());
						employee.setBuhCode(emp.getBuhCode());
						employee.setBuhName(emp.getBuhName());
						employee.setBuhDesc(emp.getBuhDesc());
						employee.setCoCode(emp.getCoCode());
						employee.setCoName(emp.getCoName());
						employee.setPhone(emp.getPhone());
						employee.setMobile(emp.getMobile());
						employee.setPositionId(emp.getPositionId());
						employee.setPosition(emp.getPosition());
						employee.setManagerPin(emp.getManagerPin());
						employee.setManagerUserName(emp.getManagerUserName());
						employee.setManagerEmail(emp.getManagerEmail());
						employee.setManagerEnName(emp.getManagerEnName());
						employee.setManagerEnSurName(emp.getManagerEnSurName());
						employee.setThFullName(emp.getThFullName());
						batchUpdateAllUserRepository.UpdateAllUser(employee);
						logger.info("Update employee : {} , {} ",employee,batchName);
					}
				}else{
					logger.info("Not found data from OM , {}",batchName);
				}
				
				batch = new Batch();
				batch.setBatchId(CommonUtil.generateUUID());
				batch.setBatchName("Batch-UpdateProfileUser");
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
			batch.setBatchName("Batch-UpdateProfileUser");
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

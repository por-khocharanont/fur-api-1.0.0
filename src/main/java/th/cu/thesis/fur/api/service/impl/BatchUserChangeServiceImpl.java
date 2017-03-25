package th.cu.thesis.fur.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.cu.thesis.fur.api.repository.BatchUserChangeRespository;
import th.cu.thesis.fur.api.repository.BatchUserReOrgRepository;
import th.cu.thesis.fur.api.rest.model.Batch;
import th.cu.thesis.fur.api.rest.model.om.ListUserChangeResponse;
import th.cu.thesis.fur.api.rest.model.om.UserChange;
import th.cu.thesis.fur.api.service.BatchUserChangeService;
import th.cu.thesis.fur.api.service.OmWebService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;

@Service("batchUserChangeServiceImpl")
public class BatchUserChangeServiceImpl implements BatchUserChangeService{
	
	private static final Logger logger = LoggerFactory.getLogger(BatchUserChangeServiceImpl.class);
	
	@Autowired
	OmWebService omWebService;
	
	@Autowired
	BatchUserChangeRespository batchUserChangeRespository;
	
	@Autowired
	BatchUserReOrgRepository batchUserReOrgRepository;
	
	@Override
	public Batch updateUserChange(String date) throws ServiceException { 
		
		Batch batch;
		String batchName = "Batch-UpdateChangeProfile";
		try {
			if(StringUtils.isEmpty(date)||StringUtils.isBlank(date)){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				date = formatter.format(new Date());
			}
			logger.info("###### Start {} : {} ######",batchName,date);
			ListUserChangeResponse listUserChange = omWebService.getUserChange(date);
			if ( listUserChange != null ){
				List<UserChange> usersChange = listUserChange.getUserChange();
				logger.info("getUserChange usersChange : {} , {} , {}",usersChange,batchName,date);
				if(usersChange != null){
					for(UserChange userChange : usersChange ){
						String oldpin = userChange.getOldpin();
						String pincode = userChange.getPin();
						if(StringUtils.isEmpty(oldpin)&&StringUtils.isBlank(oldpin)&&StringUtils.isEmpty(pincode)&&StringUtils.isBlank(pincode)){
							logger.info(" Update user : {} at pincode to new pincode : {} >> {} : {} {}",userChange.getUsername(),oldpin,pincode,batchName,date);
							batchUserChangeRespository.UpdatePincode(pincode, oldpin);
						}else{
							logger.info(" Founded some user's {} pincode is blank or null oldpin{} newpin{} : {} {}",userChange.getUsername(),oldpin,pincode,batchName,date);
						}
					}
					batch = new Batch();
					batch.setBatchId(CommonUtil.generateUUID());
					batch.setBatchName("Batch-UpdateChangeProfile");
					batch.setStatus("200"); 
					batch.setMessage("Sucess");
					batch.setRunTime(new Date());
					batch.setCreatedBy("SYSTEM-ADMIN");
					batch.setUpdatedBy("SYSTEM-ADMIN");
					batch.setCreatedDate(new Date());
					batch.setUpdatedDate(new Date());
					batchUserReOrgRepository.insertBatch(batch);
					logger.info("###### Completed Process {} : {} ######",batchName,date);
				}else{
					batch = new Batch();
					batch.setBatchId(CommonUtil.generateUUID());
					batch.setBatchName("Batch-UpdateChangeProfile");
					batch.setStatus("200"); 
					batch.setMessage("Sucess");
					batch.setRunTime(new Date());
					batch.setCreatedBy("SYSTEM-ADMIN");
					batch.setUpdatedBy("SYSTEM-ADMIN");
					batch.setCreatedDate(new Date());
					batch.setUpdatedDate(new Date());
					batchUserReOrgRepository.insertBatch(batch);
					logger.info("Don't have data from Om {} : {}",batchName,date);
					logger.info("###### Completed Process {} : {} ######",batchName,date);
				}
				
				
			}else{
				batch = new Batch();
				batch.setBatchId(CommonUtil.generateUUID());
				batch.setBatchName("Batch-UpdateChangeProfile");
				batch.setStatus("200"); 
				batch.setMessage("Sucess");
				batch.setRunTime(new Date());
				batch.setCreatedBy("SYSTEM-ADMIN");
				batch.setUpdatedBy("SYSTEM-ADMIN");
				batch.setCreatedDate(new Date());
				batch.setUpdatedDate(new Date());
				batchUserReOrgRepository.insertBatch(batch);
				logger.info("Don't have data from Om {} : {}",batchName,date);
				logger.info("###### Completed Process {} : {} ######",batchName,date);
				
			}
			 
		} catch (Exception e) {
			batch = new Batch();
			batch.setBatchId(CommonUtil.generateUUID());
			batch.setBatchName("Batch-UpdateChangeProfile");
			batch.setStatus("201"); 
			batch.setMessage("Fail");
			batch.setRunTime(new Date());
			batch.setCreatedBy("SYSTEM-ADMIN");
			batch.setUpdatedBy("SYSTEM-ADMIN");
			batch.setCreatedDate(new Date());
			batch.setUpdatedDate(new Date());
			batchUserReOrgRepository.insertBatch(batch);
			logger.error("###### Processed fail {} , {} ######",batchName,date);
			e.printStackTrace();
		}
		
		return batch;
	}

}

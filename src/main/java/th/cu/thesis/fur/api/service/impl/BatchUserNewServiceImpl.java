package th.cu.thesis.fur.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.cu.thesis.fur.api.model.NewUser;
import th.cu.thesis.fur.api.model.UserFromAcim;
import th.cu.thesis.fur.api.repository.BatchUserReOrgRepository;
import th.cu.thesis.fur.api.repository.BatchUserRepository;
import th.cu.thesis.fur.api.rest.model.Batch;
import th.cu.thesis.fur.api.rest.model.om.ListNewUserProfileResponse;
import th.cu.thesis.fur.api.rest.model.om.NewUserProfile;
import th.cu.thesis.fur.api.service.BatchUserNewService;
import th.cu.thesis.fur.api.service.OmWebService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;


@Service("batchUserNewService")
public class BatchUserNewServiceImpl implements BatchUserNewService{
	
	private static final Logger logger = LoggerFactory.getLogger(BatchUserNewServiceImpl.class);
	
	@Autowired
	OmWebService omWebService;
	
	@Autowired
	BatchUserRepository batchUserRepository;
	
	@Autowired
	BatchUserReOrgRepository batchUserReOrgRepository;
	
	@Override
	public Batch insertNewUserProfile(String date) throws ServiceException {
		Batch batch;
		final String batchName = "Batch-NewEmployee";
		try {
			String status = "1";
			if(StringUtils.isEmpty(date)||StringUtils.isBlank(date)){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				date = formatter.format(new Date());
			}
			logger.info("###### Start {} : {} ######",batchName,date);
			ListNewUserProfileResponse res = omWebService.getUserNew(date);
			if (res != null){
				List<NewUserProfile> newUserProfiles = res.getNewUserProfile();
				logger.info("Obj newUserProfiles {} , {} : {}",newUserProfiles,batchName,date);
				if(newUserProfiles!=null){
					for(NewUserProfile newUserProfile : newUserProfiles ){
						logger.info("selectUser pincode {} , {} : {}",newUserProfile.getPin(),batchName,date);
						 UserFromAcim userFromAcim =  batchUserRepository.selectUser(newUserProfile.getPin());
						if(userFromAcim == null){
							//insert
							NewUser newUser = new NewUser();
							newUser.setUserId(CommonUtil.generateUUID());
							newUser.setPincode(newUserProfile.getPin());
							newUser.setEmail(newUserProfile.getEmail());
							newUser.setEnFullName(newUserProfile.getEnFullName());
							newUser.setEnName(newUserProfile.getEnName());
							newUser.setEnSurName(newUserProfile.getEnSurName());
							newUser.setThFullName(newUserProfile.getThFullName());
							newUser.setThName(newUserProfile.getThName());
							newUser.setThSurName(newUserProfile.getThSurName());
							newUser.setUsername(newUserProfile.getUsername());
							newUser.setStatus(status);
							batchUserRepository.InsertNewUser(newUser);
							logger.info("Insert data new employee {} : {} ",userFromAcim,batchName);
						}else{ 
							logger.info("Found this pincode : {}  in table user , {} ",newUserProfile.getPin(),batchName);
						}
					}
					batch = new Batch();
					batch.setBatchId(CommonUtil.generateUUID());
					batch.setBatchName("Batch-NewEmployee");
					batch.setStatus("200"); 
					batch.setMessage("Sucess");
					batch.setRunTime(new Date());
					batch.setCreatedBy("SYSTEM-ADMIN");
					batch.setUpdatedBy("SYSTEM-ADMIN");
					batch.setCreatedDate(new Date());
					batch.setUpdatedDate(new Date());
					logger.info("###### Completed process {} : {} ######",batchName,date);
				}else{
					batch = new Batch();
					batch.setBatchId(CommonUtil.generateUUID());
					batch.setBatchName("Batch-NewEmployee");
					batch.setStatus("200"); 
					batch.setMessage("Sucess");
					batch.setRunTime(new Date());
					batch.setCreatedBy("SYSTEM-ADMIN");
					batch.setUpdatedBy("SYSTEM-ADMIN");
					batch.setCreatedDate(new Date());
					batch.setUpdatedDate(new Date());
					logger.info("Don't user from Om {} : {} ",batchName,date);
					logger.info("Completed process {} : {}",batchName,date);
				}
				
			}else{
				batch = new Batch();
				batch.setBatchId(CommonUtil.generateUUID());
				batch.setBatchName("Batch-NewEmployee");
				batch.setStatus("200"); 
				batch.setMessage("Sucess");
				batch.setRunTime(new Date());
				batch.setCreatedBy("SYSTEM-ADMIN");
				batch.setUpdatedBy("SYSTEM-ADMIN");
				batch.setCreatedDate(new Date());
				batch.setUpdatedDate(new Date());
				batchUserReOrgRepository.insertBatch(batch);
				logger.info("Don't user from Om {} : {}",batchName,date);
				logger.info("Completed process {} : {}",batchName,date);
			}
	
		} catch (Exception e) {
			batch = new Batch();
			batch.setBatchId(CommonUtil.generateUUID());
			batch.setBatchName("Batch-NewEmployee");
			batch.setStatus("201"); 
			batch.setMessage("Fail");
			batch.setRunTime(new Date());
			batch.setCreatedBy("SYSTEM-ADMIN");
			batch.setUpdatedBy("SYSTEM-ADMIN");
			batch.setCreatedDate(new Date());
			batch.setUpdatedDate(new Date());
			batchUserReOrgRepository.insertBatch(batch);
			logger.error("Processed fail {} , {}",batchName,date);
			throw ServiceException.get500SystemError(e);
		}
		
		return batch;
	}

}

package th.cu.thesis.fur.api.schedul.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import th.cu.thesis.fur.api.service.BatchAutoCloseService;
import th.cu.thesis.fur.api.service.BatchOrgUpdateService;
import th.cu.thesis.fur.api.service.BatchPeriodExpiredService;
import th.cu.thesis.fur.api.service.BatchUserAllProfileService;
import th.cu.thesis.fur.api.service.BatchUserChangeService;
import th.cu.thesis.fur.api.service.BatchUserDataService;
import th.cu.thesis.fur.api.service.BatchUserNewService;
import th.cu.thesis.fur.api.service.BatchUserReorgService;
import th.cu.thesis.fur.api.service.BatchUserResignedService;
import th.cu.thesis.fur.api.service.exception.ServiceException;

@Component
public class BatchAcimMain {

	@Autowired
	private BatchAutoCloseService batchAutoCloseService;
	
	@Autowired
	private BatchOrgUpdateService batchOrgUpdateService; 
	
	@Autowired
	private BatchPeriodExpiredService batchPeriodExpiredService;
	
	@Autowired
	private BatchUserResignedService batchUserResignedService; 	 
	
	@Autowired
	private BatchUserNewService batchUserNewService;
	
	@Autowired
	private BatchUserAllProfileService batchUserAllProfileService;
	
	@Autowired
	private BatchUserChangeService batchUserChangeService;
	
	@Autowired
	private BatchUserDataService batchUserDataService;
	
	@Autowired
	private BatchUserReorgService batchUserReorgService;
	
	@Scheduled(cron = "${batch.run.time}")
	public void resignUserProfile() throws ServiceException {	
		 batchUserResignedService.updateUserResigned(null); 
	}
	
	@Scheduled(cron = "${batch.run.time}")
	public void newUserProfile() throws ServiceException { 
		 batchUserNewService.insertNewUserProfile(null);
	}
	
	@Scheduled(cron = "${batch.run.time}")
	public void changeUserTemp() throws ServiceException {
		 batchUserChangeService.updateUserChange(null); 
	}
	
	@Scheduled(cron = "${batch.run.time}")
	public void runBatchReOrg() throws ServiceException {
		 batchUserReorgService.userReOrg(null); 
	}
	
	@Scheduled(cron = "${batch.run.time}")
	public void updateUserAllProfile() throws ServiceException {
		 batchUserAllProfileService.updatetAllUserProfile(); 
	}
	
//	@Scheduled(cron = "${batch.run.time}")
	public void insertUserProfile() throws ServiceException {
		 batchUserDataService.insertUserProfile();
	}
	
	@Scheduled(cron = "${batch.run.time}")
	public void periodExpired() throws ServiceException {
		 batchPeriodExpiredService.periodExpired(); 
	}
	
	@Scheduled(cron = "${batch.run.time}")
	public void autoUrClose() throws ServiceException {
		 batchAutoCloseService.autoCloseUr();
	}
	
	@Scheduled(cron = "${batch.run.time}")
	public void updateOrgProfile() throws ServiceException {
		 batchOrgUpdateService.updateOrg();
	}

}

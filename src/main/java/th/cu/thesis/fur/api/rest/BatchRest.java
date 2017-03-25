package th.cu.thesis.fur.api.rest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.cu.thesis.fur.api.rest.model.Batch;
import th.cu.thesis.fur.api.service.BatchAutoCloseService;
import th.cu.thesis.fur.api.service.BatchOrgUpdateService;
import th.cu.thesis.fur.api.service.BatchPeriodExpiredService;
import th.cu.thesis.fur.api.service.BatchUserAllProfileService;
import th.cu.thesis.fur.api.service.BatchUserChangeService;
import th.cu.thesis.fur.api.service.BatchUserDataService;
import th.cu.thesis.fur.api.service.BatchUserNewService;
import th.cu.thesis.fur.api.service.BatchUserReorgService;
import th.cu.thesis.fur.api.service.BatchUserResignedService;
import th.cu.thesis.fur.api.service.OmWebService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.MailSenderUtil;


@RestController
@RequestMapping(value="/batch")
public class BatchRest {
	
	
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
	
	
	@RequestMapping(value="/resign/user/{date}",method = RequestMethod.GET)
	public @ResponseBody Batch resignUserProfile(@PathVariable("date") String date) throws ServiceException {	
		return batchUserResignedService.updateUserResigned(date); 
	}
	
	@RequestMapping(value="/new/user/{date}",method = RequestMethod.GET)
	public @ResponseBody Batch newUserProfile(@PathVariable("date") String date) throws ServiceException { 
		return batchUserNewService.insertNewUserProfile(date);
	}
	
	@RequestMapping(value="/change/user/{date}",method = RequestMethod.GET)
	public @ResponseBody Batch changeUserTemp(@PathVariable("date") String date) throws ServiceException {
		return batchUserChangeService.updateUserChange(date); 
	}
	
	@RequestMapping(value="/run/btreOrg/{date}",method = RequestMethod.GET)
	public @ResponseBody Batch runBatchReOrg(@PathVariable("date") String date) throws ServiceException {
		return batchUserReorgService.userReOrg(date); 
	}
	
	@RequestMapping(value="/update/user",method = RequestMethod.GET)
	public @ResponseBody Batch updateUserAllProfile() throws ServiceException {
		return batchUserAllProfileService.updatetAllUserProfile(); 
	}
	
	@RequestMapping(value="/data/user",method = RequestMethod.GET)
	public @ResponseBody Batch insertUserProfile() throws ServiceException {
		return batchUserDataService.insertUserProfile();
	}
	
	@RequestMapping(value="/app/expired",method = RequestMethod.GET)
	public @ResponseBody Batch periodExpired() throws ServiceException {
		return batchPeriodExpiredService.periodExpired(); 
	}
	
	@RequestMapping(value="/ur/close",method = RequestMethod.GET)
	public @ResponseBody Batch autoUrClose() throws ServiceException {
		return batchAutoCloseService.autoCloseUr();
	}
	
	@RequestMapping(value="/update/org",method = RequestMethod.GET)
	public @ResponseBody Batch updateOrgProfile() throws ServiceException {
		return batchOrgUpdateService.updateOrg();
	}
	
	@RequestMapping(value="/test/mail",method = RequestMethod.GET)
	public @ResponseBody Batch testMail() throws ServiceException { 
		
		  try {
			MailSenderUtil mail = new MailSenderUtil("127.0.0.1");
			
			// mail.send("abc@hotmail.com", "abc@hotmail.com", "test subject", "test detail<P>eee");
			
			List<String> to = Arrays.asList("a@hotmail.com", "b@hotmail.com");
			mail.send("abc@hotmail.com", to, "test subject", "test detail<P>eee");
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  
		
		return null; 
	}

	
}

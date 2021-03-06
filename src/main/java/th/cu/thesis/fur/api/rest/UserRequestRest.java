package th.cu.thesis.fur.api.rest;

import static th.cu.thesis.fur.api.util.CommonUtil.HEADER_X_ACIM_USER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.reflect.TypeToken;

import th.cu.thesis.fur.api.model.Application;
import th.cu.thesis.fur.api.model.UrForUser;
import th.cu.thesis.fur.api.repository.model.AppFile;
import th.cu.thesis.fur.api.rest.model.BaseGridResponse;
import th.cu.thesis.fur.api.rest.model.DataUserRequestValidate;
import th.cu.thesis.fur.api.rest.model.RequestNoDetail;
import th.cu.thesis.fur.api.rest.model.UserRequestApproveRequest;
import th.cu.thesis.fur.api.rest.model.UserRequestDetail;
import th.cu.thesis.fur.api.rest.model.UserRequestFromGrid;
import th.cu.thesis.fur.api.service.UserRequestService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;

@RestController
@RequestMapping("/userrequest")
public class UserRequestRest extends AbstractRestController{
	
	private static final Logger logger = LoggerFactory.getLogger(UserRequestRest.class);
	
	@Autowired
	UserRequestService userRequestService;
	
	@RequestMapping(value = "/requestno/detail/{requestNo}", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public @ResponseBody RequestNoDetail getRequestNoDetail(@PathVariable("requestNo") String requestNo) throws ServiceException{
		
		return userRequestService.getRequestNoDetail(requestNo);
		
	}
	
	@RequestMapping(value ="/ur/checktoken" , method = RequestMethod.POST)
	public @ResponseBody List<UrForUser> reloadTokenUrForUser(@RequestBody List<UrForUser> urForUserList,@RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		return userRequestService.reloadTokenUrForUser(urForUserList, userLogin);
	}
	
	@RequestMapping(value ="/ur/detail/{urId}" , method = RequestMethod.GET)
	public @ResponseBody UserRequestDetail getUrDetail(@PathVariable String urId,@RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		return userRequestService.getUrDetail(urId, userLogin);
	}
	
	@RequestMapping(value = "/approve", method = RequestMethod.POST)
	public @ResponseBody Boolean approveAllUrByType(@RequestBody UserRequestApproveRequest request,@RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		return userRequestService.approveAllUrByType(request, userLogin);
	}
	
	@RequestMapping(value = "/approve/ur", method = RequestMethod.POST)
	public @ResponseBody Boolean approveUrByType(@RequestBody UserRequestApproveRequest request,@RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		return userRequestService.approveUrByType(request, userLogin);
	}
	
	@RequestMapping(value="/app/files", method = RequestMethod.GET)
	public @ResponseBody List<AppFile> getPathfileByAppId(@RequestParam(value="appId") String appId) throws ServiceException{
		try{
			return userRequestService.getPathfileByAppId(appId);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/listAppByNameAndType", method = RequestMethod.POST)
	public @ResponseBody List<Application> getListappBytype(@RequestBody Map<String, String> request) throws ServiceException{
		try{
			return userRequestService.getListappBytype(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/listAppRoleGridByappIdAndType", method = RequestMethod.POST)
	public @ResponseBody BaseGridResponse getListAppRoleGridByappIdAndType(@RequestBody Map<String, Object> request) throws ServiceException{
		try{
			return userRequestService.getListAppRoleGridByappIdAndType(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/listAppRoleGridByappNameAndType", method = RequestMethod.POST)
	public @ResponseBody BaseGridResponse getListAppRoleGridByappNameAndType(@RequestBody Map<String, Object> request) throws ServiceException{
		try{
			return userRequestService.getListAppRoleGridByappNameAndType(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/app/authenType", method = RequestMethod.GET)
	public @ResponseBody String getAuthenTypeByAppId(@RequestParam(value="appId") String appId) throws ServiceException{
		try{
			return userRequestService.getAuthenTypeByAppId(appId);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/validpreview",method= RequestMethod.POST)
	public @ResponseBody DataUserRequestValidate validPreview (@RequestBody DataUserRequestValidate request) throws ServiceException{
		try{
			request.setErrorListalreadyApp(validAlreadyRoleApp(request));
			request.setErrorListQueueduser(validQueuedUser(request));
			request = userRequestService.validbothAlreadyQueued(request); 
			return request;
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
//	@RequestMapping(value="/bothAlreadyQueued",method= RequestMethod.POST)
//	public @ResponseBody List<Map<String, Object>> validbothAlreadyQueued (@RequestBody DataUserRequestValidate request){
//		return userRequestService.validbothAlreadyQueued(request); 
//	}
	
	@RequestMapping(value="/aleadyroleapp",method= RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> validAlreadyRoleApp (@RequestBody DataUserRequestValidate request) throws ServiceException{
		try{
			return userRequestService.validAlreadyRoleApp(request); 
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/QueuedUser",method= RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> validQueuedUser (@RequestBody DataUserRequestValidate request) throws ServiceException{
		try{
			return userRequestService.validURisQueued(request); 
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/commitDataAndEmail", method = RequestMethod.POST,consumes = {"multipart/form-data"})
	public @ResponseBody String subMitforCommitdata(HttpServletRequest request) throws ServiceException{
		try{
			String idRequestNo = "";
			Map<String, String> dataFormApp = new HashMap<String, String>();
			DataUserRequestValidate dataUserRequestValidate = CommonUtil.getGson().fromJson(request.getParameter("dataUserRequestValidate"), DataUserRequestValidate.class);
			dataFormApp = CommonUtil.getGson().fromJson(request.getParameter("dataFormApp").toString(), Map.class);
			if("1".equals(dataFormApp.get("requestTypeValue"))){
				List<UserRequestFromGrid> requestFromGrids = CommonUtil.getGson().fromJson(request.getParameter("requestFromGrids"),new TypeToken<List<UserRequestFromGrid>>() {}.getType());
				idRequestNo = userRequestService.subMitforCommitdata(dataUserRequestValidate, dataFormApp, requestFromGrids);
			}else if("3".equals(dataFormApp.get("requestTypeValue"))){
				List<Map<String,Object>> listDataChg = CommonUtil.getGson().fromJson(request.getParameter("listapp"),new TypeToken<List<Map<String,Object>>>() {}.getType());
				idRequestNo = userRequestService.subMitforCommitdataChg(dataUserRequestValidate,dataFormApp,listDataChg);
			}else if("2".equals(dataFormApp.get("requestTypeValue"))){
				List<Map<String,Object>> listDataChg = CommonUtil.getGson().fromJson(request.getParameter("listapp"),new TypeToken<List<Map<String,Object>>>() {}.getType());
				idRequestNo = userRequestService.subMitforCommitdataTer(dataUserRequestValidate,dataFormApp,listDataChg);
			}
			return idRequestNo;
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
		
	}
	
	@RequestMapping(value="/listurfromSubmit", method = RequestMethod.GET) 
	public @ResponseBody List<Map<String,Object>> listurfromSubmit(@RequestParam(value="idRequestNo") String idRequestNo) throws Exception{
		try{
			return userRequestService.getSaveDatasNewGrid(idRequestNo);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/checkApproverSamePerson", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> checkApproverSamePerson(@RequestBody List<String> request) throws ServiceException{
		try{
			return userRequestService.checkApproverSamePerson(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/listAppChangeByappName", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> listAppChangeByappName(@RequestBody Map<String, String> request) throws ServiceException{
		try{
			return userRequestService.listAppChangeByappName(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/listAppChangeGridByappId", method = RequestMethod.POST)
	public @ResponseBody BaseGridResponse listAppChangeGridByappId(@RequestBody Map<String, Object> request) throws ServiceException{
		try{
			return userRequestService.listAppChangeGridByappId(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/listAppChangeGridByappName", method = RequestMethod.POST)
	public @ResponseBody BaseGridResponse listAppChangeGridByappName(@RequestBody Map<String, Object> request) throws ServiceException{
		try{
			return userRequestService.listAppChangeGridByappName(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/listApp/terauthor/gridByappName", method = RequestMethod.POST)
	public @ResponseBody BaseGridResponse getlistAppTerAuthorGridByappName(@RequestBody Map<String, Object> request) throws ServiceException{
		try{
			return userRequestService.listAppTerAuthorGridByappName(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/listChgAppComboBox", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> listChgAppComboBox(@RequestBody Map<String, Object> request) throws ServiceException{
		try{
			return userRequestService.listChgAppComboBox(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/profile/listdata", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> listChgAppComboBox(@RequestBody List<String> appRoleIds) throws ServiceException{
		try{
			return userRequestService.getListDatasProfileByappRoleIds(appRoleIds);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	// NEW GRID STD
	@RequestMapping(value="/listappEligibleListStdByAppname", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> listappEligibleListStdByAppname(@RequestBody Map<String, Object> request) throws ServiceException{
		try{
			return userRequestService.getListEligiblebyAppNameStd(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/listAppEligibleStdGridByAppId", method = RequestMethod.POST)
	public @ResponseBody BaseGridResponse listAppEligibleStdGridByAppId(@RequestBody Map<String, Object> request) throws ServiceException{
		try{
			return userRequestService.listAppEligibleStdGridByAppId(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/listAppEligibleStdGridByAppName", method = RequestMethod.POST)
	public @ResponseBody BaseGridResponse listAppEligibleStdGridByAppName(@RequestBody Map<String, Object> request) throws ServiceException{
		try{
			return userRequestService.listAppEligibleStdGridByAppName(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	// NEW GRID SPC
	@RequestMapping(value="/listappEligibleListSpcByAppname", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> listappEligibleListSpcByAppname(@RequestBody Map<String, Object> request) throws ServiceException{
		try{
			return userRequestService.listappEligibleListSpcByAppname(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/listAppSpcGridEligibleByAppId", method = RequestMethod.POST)
	public @ResponseBody BaseGridResponse listAppSpcGridEligibleByAppId(@RequestBody Map<String, Object> request) throws ServiceException{
		try{
			return userRequestService.listAppSpcGridEligibleByAppId(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value="/listAppSpcGridEligibleByAppName", method = RequestMethod.POST)
	public @ResponseBody BaseGridResponse listAppSpcGridEligibleByAppName(@RequestBody Map<String, Object> request) throws ServiceException{
		try{
			return userRequestService.listAppSpcGridEligibleByAppName(request);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
}

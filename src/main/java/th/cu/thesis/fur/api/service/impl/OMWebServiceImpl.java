package th.cu.thesis.fur.api.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HttpTransportProperties.Authenticator;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import th.cu.thesis.fur.api.rest.model.om.ApproveProfileResponse;
import th.cu.thesis.fur.api.rest.model.om.EmployeeProfile;
import th.cu.thesis.fur.api.rest.model.om.ListEmpOmResponse;
import th.cu.thesis.fur.api.rest.model.om.ListNewUserProfileResponse;
import th.cu.thesis.fur.api.rest.model.om.ListOrgAllResponse;
import th.cu.thesis.fur.api.rest.model.om.ListOrgTypeAllResponse;
import th.cu.thesis.fur.api.rest.model.om.ListResignedUserProfileResponse;
import th.cu.thesis.fur.api.rest.model.om.ListUserChangeResponse;
import th.cu.thesis.fur.api.rest.model.om.ListUserReOrgResponse;
import th.cu.thesis.fur.api.service.OmWebService;
import th.cu.thesis.fur.api.service.ws.authen.AuthenService;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetApproverProfile;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetApproverProfileResponse;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllEmpProfileAndMgr;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllEmpProfileAndMgrResponse;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllOrganizeByOrgType;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllOrganizeByOrgTypeResponse;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpChangedStatusByDate;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpChangedStatusByDateResponse;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpNewStatusByDate;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpNewStatusByDateResponse;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpReOrgStatusByDate;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpReOrgStatusByDateResponse;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpResignedByDate;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpResignedByDateResponse;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrganize;
import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrganizeResponse;

import com.thoughtworks.xstream.XStream;

@Service("omWebService")
public class OMWebServiceImpl implements OmWebService {
	
	@Autowired
	private AuthenService authenService;
	
	@Value("${om.code}")
	private String omCode;
	
	@Value("${om.target.endpoint}")
	private String targetEndpoint;
	
	@Value("${string.length}")
	private Integer strLen;
	
	@Value("${app.env}")
	private String env;

	private String pathModulresign = "resign_user";
	private String pathModulAlluser = "all_user";
	private String pathModulChangUser = "change_user";
	private String pathModulInsertOrg = "insert_org";
	private String pathModulNewUser = "new_user";
	private String pathModulReOrg = "reorg_user";
	private String pathModulUpdateOrgtype = "update_org_type";
	@Override
	public EmployeeProfile getApproverProfile(String pinCode) throws Exception {
		EmployeeProfile employeeProfile = new EmployeeProfile();
		try {
			WS_OM_OMServiceStub serviceOm = new WS_OM_OMServiceStub(targetEndpoint);
			Authenticator auth = new Authenticator();				
			authenService.setAuthenOm(auth, serviceOm);
			OM_WS_GetApproverProfile req = new OM_WS_GetApproverProfile();
			OM_WS_GetApproverProfileResponse rep = new OM_WS_GetApproverProfileResponse();
			req.setOmCode(omCode);
			req.setPin(pinCode);	
			rep = serviceOm.oM_WS_GetApproverProfile(req);
			String xml = rep.getOM_WS_GetApproverProfileResult();
			XStream xstream = new XStream();		
			xstream.processAnnotations(ApproveProfileResponse.class);
			employeeProfile = ((ApproveProfileResponse) xstream.fromXML(xml)).getEmpPro();
			
		}catch(Exception e){
			if(e instanceof AxisFault){
				throw new AxisFault(e.getMessage());
			}
		}
		// TODO Auto-generated method stub
		return employeeProfile;
	}
	
	
	@Override
	public ListNewUserProfileResponse getUserNew(String date) throws Exception {
	
		ListNewUserProfileResponse listNew = new ListNewUserProfileResponse();
		
		try {
			WS_OM_OMServiceStub serviceOm = new WS_OM_OMServiceStub(targetEndpoint);
			
			Authenticator auth = new Authenticator();				
			authenService.setAuthenOm(auth, serviceOm);
			
			OM_WS_ListEmpNewStatusByDate  req = new OM_WS_ListEmpNewStatusByDate();
			OM_WS_ListEmpNewStatusByDateResponse rep = new OM_WS_ListEmpNewStatusByDateResponse();
				
			req.setOmCode(omCode);
			req.setDateNew(date);
				
			rep = serviceOm.oM_WS_ListEmpNewStatusByDate(req);
			
			String xml = rep.getOM_WS_ListEmpNewStatusByDateResult();
			
			System.out.println("###################"); 
			
			writeDataLogforBatch(pathModulNewUser, xml);
			
			System.out.println("###################"); 
			XStream xstream = new XStream();		
		
			xstream.processAnnotations(ListNewUserProfileResponse.class);
			xstream.addImplicitCollection(ListNewUserProfileResponse.class, "newUserProfile");
			listNew = (ListNewUserProfileResponse)xstream.fromXML(xml);
				
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof AxisFault){
				throw new AxisFault(e.getMessage());
			}
		}		
		
		return listNew;
	}
	
	
	@Override
	public ListOrgAllResponse getOrganize() throws Exception {
		
		ListOrgAllResponse listAllOrg = new ListOrgAllResponse(); 
		try {
			
		WS_OM_OMServiceStub serviceOm = new WS_OM_OMServiceStub(targetEndpoint);
		Authenticator auth = new Authenticator();				
		authenService.setAuthenOm(auth, serviceOm);
		
		OM_WS_ListOrganize req = new OM_WS_ListOrganize();
		OM_WS_ListOrganizeResponse rep = new OM_WS_ListOrganizeResponse();
			
		req.setOmCode(omCode);
		
		rep = serviceOm.oM_WS_ListOrganize(req);
		String xml = rep.getOM_WS_ListOrganizeResult();
		
		writeDataLogforBatch(pathModulInsertOrg, xml);
		
		XStream xstream = new XStream();		
			
		xstream.processAnnotations(ListOrgAllResponse.class);
		xstream.addImplicitCollection(ListOrgAllResponse.class, "orgProfile");
		listAllOrg = (ListOrgAllResponse)xstream.fromXML(xml);
				
		//	OM_WS_ListAllOrganizeByOrgType req2 = new OM_WS_ListAllOrganizeByOrgType();
		//	OM_WS_ListAllOrganizeByOrgTypeResponse rep2 = new OM_WS_ListAllOrganizeByOrgTypeResponse();
		
	} catch (Exception e) {
		e.printStackTrace();
		if(e instanceof AxisFault){
			throw new AxisFault(e.getMessage());
		}
	}		
		
		return listAllOrg;
	}
	
	@Override
	public ListOrgTypeAllResponse getUserTypeByOrgCode() throws Exception {
		
		ListOrgTypeAllResponse orgTypeList = new ListOrgTypeAllResponse();
		
		try{
			
			WS_OM_OMServiceStub serviceOm = new WS_OM_OMServiceStub(targetEndpoint);
			Authenticator auth = new Authenticator();				
			authenService.setAuthenOm(auth, serviceOm);
			
			OM_WS_ListAllOrganizeByOrgType req = new OM_WS_ListAllOrganizeByOrgType();
			OM_WS_ListAllOrganizeByOrgTypeResponse rep = new OM_WS_ListAllOrganizeByOrgTypeResponse();
			
			req.setOmCode(omCode);
			
			rep = serviceOm.oM_WS_ListAllOrganizeByOrgType(req);
			
			String xml = rep.getOM_WS_ListAllOrganizeByOrgTypeResult();
			
			System.out.println("###################"); 
			
			writeDataLogforBatch(pathModulUpdateOrgtype, xml);
			
			System.out.println("###################"); 
			
			XStream xstream = new XStream();		
			
			xstream.processAnnotations(ListOrgTypeAllResponse.class);
			xstream.addImplicitCollection(ListOrgTypeAllResponse.class, "orgTypeList");
			orgTypeList = (ListOrgTypeAllResponse)xstream.fromXML(xml);
			
			
		}catch (Exception e) {
			e.printStackTrace();
			if(e instanceof AxisFault){
				throw new AxisFault(e.getMessage());
			}
		}
		
		
		return orgTypeList;
	}
	
	
	
	@Override
	public ListEmpOmResponse getAllUserProfile() throws Exception {
		
		ListEmpOmResponse listEmpOm = new ListEmpOmResponse();
		
		try {
			
			WS_OM_OMServiceStub serviceOm = new WS_OM_OMServiceStub(targetEndpoint);
			Authenticator auth = new Authenticator();				
			authenService.setAuthenOm(auth, serviceOm);
			
			OM_WS_ListAllEmpProfileAndMgr req = new OM_WS_ListAllEmpProfileAndMgr();
			OM_WS_ListAllEmpProfileAndMgrResponse rep = new OM_WS_ListAllEmpProfileAndMgrResponse();
		
			req.setOmCode(omCode);
			rep = serviceOm.oM_WS_ListAllEmpProfileAndMgr(req);
		
			String xml = rep.getOM_WS_ListAllEmpProfileAndMgrResult();
			
			System.out.println("###################"); 
			writeDataLogforBatch(pathModulAlluser, xml);
			System.out.println("###################");
			
			XStream xstream = new XStream();		
			
			xstream.processAnnotations(ListEmpOmResponse.class);
			xstream.addImplicitCollection(ListEmpOmResponse.class, "empList");
			listEmpOm = (ListEmpOmResponse)xstream.fromXML(xml);
			
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof AxisFault){
				throw new AxisFault(e.getMessage());
			}
		}		
		
		return listEmpOm;
	}
	
	@Override
	public ListResignedUserProfileResponse getUserResignedResponse(String date) throws Exception {
		
		ListResignedUserProfileResponse listResignedUser = new ListResignedUserProfileResponse();
		
		try {
			
			WS_OM_OMServiceStub serviceOm = new WS_OM_OMServiceStub(targetEndpoint);
			Authenticator auth = new Authenticator();				
			authenService.setAuthenOm(auth, serviceOm);
			
			OM_WS_ListEmpResignedByDate req = new OM_WS_ListEmpResignedByDate();
			OM_WS_ListEmpResignedByDateResponse rep = new OM_WS_ListEmpResignedByDateResponse();
			
			req.setOmCode(omCode);
			req.setResignedDate(date);
			
			rep = serviceOm.oM_WS_ListEmpResignedByDate(req);
		
			String xml = rep.getOM_WS_ListEmpResignedByDateResult();
			writeDataLogforBatch(pathModulresign, xml);
			XStream xstream = new XStream();		
			
			xstream.processAnnotations(ListResignedUserProfileResponse.class);
			xstream.addImplicitCollection(ListResignedUserProfileResponse.class, "resignedUserProfile");
			listResignedUser = (ListResignedUserProfileResponse)xstream.fromXML(xml);
			
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof AxisFault){
				throw new AxisFault(e.getMessage());
			}
		}		
		
		
		
		
		return listResignedUser;
	}
	
	@Override
	public ListUserChangeResponse getUserChange(String date) throws Exception {
		
		ListUserChangeResponse listUserChange = new ListUserChangeResponse();
	
		try {
			
			
			WS_OM_OMServiceStub serviceOm = new WS_OM_OMServiceStub(targetEndpoint);
			Authenticator auth = new Authenticator();				
			authenService.setAuthenOm(auth, serviceOm);
			
		
			//OM_WS_ListEmpReOrgStatusByDate
			//OM_WS_ListEmpReOrgStatusByDateResponse
			
			OM_WS_ListEmpChangedStatusByDate req = new OM_WS_ListEmpChangedStatusByDate();
			OM_WS_ListEmpChangedStatusByDateResponse rep = new OM_WS_ListEmpChangedStatusByDateResponse();
			
			req.setOmCode(omCode);
			req.setChangedDate(date);
			rep = serviceOm.oM_WS_ListEmpChangedStatusByDate(req);
			
			String xml = rep.getOM_WS_ListEmpChangedStatusByDateResult();
			
			
			
			System.out.println("###################"); 
			writeDataLogforBatch(pathModulChangUser,xml);
			System.out.println("###################");
			
			XStream xstream = new XStream();		
			
			xstream.processAnnotations(ListUserChangeResponse.class);
			xstream.addImplicitCollection(ListUserChangeResponse.class, "userChange");
			listUserChange = (ListUserChangeResponse)xstream.fromXML(xml);
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof AxisFault){
				throw new AxisFault(e.getMessage());
			}
		}		
		
		return listUserChange ;
	}
	
	@Override
	public ListUserReOrgResponse getUserReOrg(String date) throws Exception {
		
		ListUserReOrgResponse listUserReOrg = new ListUserReOrgResponse();
	
		try {
			
			
			WS_OM_OMServiceStub serviceOm = new WS_OM_OMServiceStub(targetEndpoint);
			Authenticator auth = new Authenticator();				
			authenService.setAuthenOm(auth, serviceOm);
			
		
			//OM_WS_ListEmpReOrgStatusByDate
			//OM_WS_ListEmpReOrgStatusByDateResponse
			
			OM_WS_ListEmpReOrgStatusByDate req = new OM_WS_ListEmpReOrgStatusByDate();
			OM_WS_ListEmpReOrgStatusByDateResponse rep = new OM_WS_ListEmpReOrgStatusByDateResponse();
			
			req.setOmCode(omCode);
			req.setDateReOrg(date);
			rep = serviceOm.oM_WS_ListEmpReOrgStatusByDate(req);
			
			String xml = rep.getOM_WS_ListEmpReOrgStatusByDateResult();
	
			
			
			System.out.println("###################"); 
			
			writeDataLogforBatch(pathModulReOrg, xml);
			
			System.out.println("###################");
			
			XStream xstream = new XStream();		
			
			xstream.processAnnotations(ListUserReOrgResponse.class);
			xstream.addImplicitCollection(ListUserReOrgResponse.class, "userReOrg");
			listUserReOrg = (ListUserReOrgResponse)xstream.fromXML(xml);
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof AxisFault){
				throw new AxisFault(e.getMessage());
			}
		}		
		
		return listUserReOrg ;
	}
	
	
	private void writeDataLogforBatch(String module,String xml){
		String patternDay = "yyyyMMdd";
		String date = DateFormatUtils.format(Calendar.getInstance().getTime(), patternDay);
		String patternTime = "HHmmss";
		String time = DateFormatUtils.format(Calendar.getInstance().getTime(), patternTime);
		String pathPrimary = "D:\\ACIM-II\\Batch\\app\\om\\log\\"+module;
		Path pathModule = Paths.get(pathPrimary);
		Path pathHours = Paths.get(pathPrimary+"\\"+date);
		Path newFile = Paths.get(pathPrimary+"\\"+date+"\\"+time+".txt");
		
		try {
			if(!Files.exists(pathModule)){
				Files.createDirectory(pathModule);
			}
			if(!Files.exists(pathHours)){
				Files.createDirectory(pathHours);
			}
			if(!Files.exists(pathModule)){
				Files.createDirectory(pathModule);
			}
			if(!Files.exists(newFile)){
				Files.createFile(newFile);
			}
			if(Files.exists(newFile)&&Files.exists(pathModule)&&Files.exists(pathHours)){
				try(BufferedWriter writer = Files.newBufferedWriter(
			            newFile, Charset.defaultCharset())){
			    	writer.append(xml);
			    	writer.flush();
			    }catch(IOException exception){
			    	exception.printStackTrace();
			    }
			}
		}catch(FileAlreadyExistsException e){
			e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

}

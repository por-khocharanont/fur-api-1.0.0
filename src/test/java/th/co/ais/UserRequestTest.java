package th.co.ais;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;

import th.cu.thesis.fur.api.model.OrgFromAcim;
import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.repository.BatchUserReOrgRepository;
import th.cu.thesis.fur.api.repository.RolePermissionRepository;
import th.cu.thesis.fur.api.repository.UserRequestRepository;
import th.cu.thesis.fur.api.rest.model.om.ListUserReOrgResponse;
import th.cu.thesis.fur.api.rest.model.om.UserReOrg;
import th.cu.thesis.fur.api.service.BatchUserReorgService;
import th.cu.thesis.fur.api.service.EmailUtilService;
import th.cu.thesis.fur.api.service.OmWebService;
import th.cu.thesis.fur.api.service.UserRequestService;
import th.cu.thesis.fur.api.util.CommonUtil;

import com.mchange.v2.c3p0.ComboPooledDataSource;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/WEB-INF/spring/appServlet/servlet-context.xml"})
public class UserRequestTest {
	
	
//	ApplicationContext ac;
	@Autowired
	JavaMailSender  mailSender;
	@Autowired
	ComboPooledDataSource dataSource;
	@Autowired
	TemplateEngine templateEngine;
	@Autowired
	RolePermissionRepository rolePermissionRepository;
	@Autowired
	UserRequestRepository userRequestRepository;
	@Autowired
	UserRequestService userRequestService;
	@Autowired
	OmWebService omWebService;
	@Autowired
	EmailUtilService emailUtilService;
	@Autowired
	BatchUserReOrgRepository batchUserReOrgRepository;
	@Autowired
	BatchUserReorgService batchUserReorgService;
//	@Before
//	public void setUp()
//	{
//		try{
//			ac = new FileSystemXmlApplicationContext("classpath:/WEB-INF/spring/appServlet/servlet-context.xml");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void test1() throws ServiceException{
//		// Prepare the evaluation context
//		try{
//			    final Context ctx = new Context();
//			    ctx.setVariable("name", "POND");
//		//	    ctx.setVariable("subscriptionDate", new Date());
//		//	    ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
//		//	    ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML
//		
//			    // Prepare message using a Spring helper
//			    final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
//			    final MimeMessageHelper message =
//			        new MimeMessageHelper(mimeMessage, false, "UTF-8"); // true = multipart
//			    message.setSubject("Example HTML email with inline image");
//			    message.setFrom("thymeleaf@example.com");
//			    message.setTo("test@ais.com");
//		
//			    // Create the HTML body using Thymeleaf
//			    final String htmlContent = templateEngine.process("email/templateExampleEmail", ctx);
//			    message.setText(htmlContent, true); // true = isHtml
//		
//			    // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
//		//	    final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
//		//	    message.addInline(imageResourceName, imageSource, imageContentType);
//		
//			    // Send mail
//			    this.mailSender.send(mimeMessage);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void Test2 () throws SQLException{
//		try{
//		Connection connection = dataSource.getConnection();
////		Statement stmt = connection.createStatement();
//		String sql ="SELECT * FROM [APP] WHERE APP_NAME LIKE ?";
//		PreparedStatement ps = connection.prepareStatement(sql);
//		ps.setString(1, "%APP[_]%");
//		
//		ResultSet rs = ps.executeQuery();
////		ResultSet rs = stmt.executeQuery("SELECT * FROM UR");
//		while (rs.next()) {
//			  String ss = rs.getString("APP_NAME");
//			  System.out.println(ss + "\n");
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
////		String pp = null;
////		try {
////			 pp = new String("Pond".getBytes(),"UTF-8");
////		} catch (UnsupportedEncodingException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		System.out.println(pp);
//	}
	
//	@Test
//	public void Test3 (){
//		try{
//		RolePermission rolePer = new RolePermission();
//		rolePer.setRolePermissionId(CommonUtil.generateUUID());
//		rolePer.setRoleCode("ADMIN-REPORT");
//		rolePer.setMenuCode("ADMIN");
//		rolePer.setEnable("0");
//		rolePer.setActionPermission("{\"ADD\":\"0\",\"EDIT\":\"0\",\"DEL\":\"0\"}");
//		rolePer.setCreatedBy("adminacim");
//		rolePer.setUpdatedBy("adminacim");
//		rolePer.setCreatedDate(new Date());
//		rolePer.setUpdatedDate(new Date());
//		rolePermissionRepository.insertRolePermissionUser(rolePer);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	@Test
	public void Test4 (){
		try{
			String strdate = "2015-06-16";
			ListUserReOrgResponse res = omWebService.getUserReOrg(strdate);
			Date date = CommonUtil.textToDateForBatch(strdate);
			
			for(UserReOrg userReOrg : res.getUserReOrg() ){
				
				OrgFromAcim orgFromAcim = new OrgFromAcim();
				orgFromAcim = batchUserReOrgRepository.selectOrg(userReOrg.getNewOrgCode());
				
				String pin = userReOrg.getPin();
				String oldOrgCode = userReOrg.getOldOrgCode();
				String newOrgCode = orgFromAcim.getOrgcode();
				String orgName = orgFromAcim.getOrgname();
				String orgDesc = orgFromAcim.getOrgdesc();
				String orgLevel = orgFromAcim.getOrglevel();
				User user = userRequestRepository.getUserByUsername(userReOrg.getUsername());
				String oldOrgType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(oldOrgCode);
				String orgType = userRequestRepository.getOrgtypeOrUserTypeByOrgcode(newOrgCode);
				if(user==null){
				}else if(StringUtils.isBlank(oldOrgType)||StringUtils.isEmpty(oldOrgType)){
				}else if(StringUtils.isBlank(orgType)||StringUtils.isEmpty(orgType)){
				}else{
					batchUserReorgService.clearStatusUrProcess(user);
					if(oldOrgType.equals(orgType)){
						batchUserReorgService.terminateSameOrgType(user,newOrgCode,date);
					}else{
						batchUserReorgService.terminateAllApplication(user,date);
					}
					batchUserReOrgRepository.UpdateOrgcode(pin, oldOrgCode, newOrgCode, orgName, orgDesc, orgLevel);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
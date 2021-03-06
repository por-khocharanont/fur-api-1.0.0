package th.cu.thesis.fur.api.service.ws.authen;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties.Authenticator;
import org.apache.commons.httpclient.auth.AuthPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub;


@Service("authenService")
public class AuthenService {
	
//	private final Logger logger = LoggerFactory.getLogger(AuthenService.class);
//	
//	@Autowired
//	LogUtils logUtils;
	
	@Value("${om.url}")
	private String omUrl;
	
	@Value("${om.username}")
	private String omUserName;
	
	@Value("${om.password}")
	private String omPassWord;
	
	@Value("${om.port}")
	private Integer omPort;
	
	@Value("${om.timeout}")
	private Integer omTimeOut;
	
	@Value("${om.domain}")
	private String omDomain;	
	
	public void setAuthenOm(Authenticator auth, WS_OM_OMServiceStub wsOmServiceStub) {
//		AuthPolicy.registerAuthScheme(AuthPolicy.NTLM, JCIFS_NTLMScheme.class);		
		AuthPolicy.registerAuthScheme(AuthPolicy.NTLM, NEW_NTLMScheme.class);
		
		auth.setUsername(omUserName);
		auth.setPassword(omPassWord);
		auth.setDomain(omDomain);
		auth.setHost(omUrl);
		auth.setPort(omPort);
		
//		logger.info(logUtils.logOld("", "", LogStatus.I, "omDomain = {}"), omDomain);
//		logger.info(logUtils.logOld("", "", LogStatus.I, "omUrl = {}"), omUrl);
//		logger.info(logUtils.logOld("", "", LogStatus.I, "omPort = {}"), omPort);
//		logger.info(logUtils.logOld("", "", LogStatus.I, "omTimeOut = {}"), omTimeOut * 1000);
			
		wsOmServiceStub._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
		wsOmServiceStub._getServiceClient().getOptions().setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
		wsOmServiceStub._getServiceClient().getOptions().setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Boolean.TRUE);
		wsOmServiceStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(omTimeOut * 1000);
	}
}

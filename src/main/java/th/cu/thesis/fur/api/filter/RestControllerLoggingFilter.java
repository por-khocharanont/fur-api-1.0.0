package th.cu.thesis.fur.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import th.cu.thesis.fur.api.enums.URLService;
import th.cu.thesis.fur.api.util.CommonUtil;

public class RestControllerLoggingFilter extends OncePerRequestFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(RestControllerLoggingFilter.class);
	
	private static final String LOG_RERQUEST_FORMAT = "API_REQUEST|{} {}, HEADERS:{}, PARAMS:{}, BODY:{}";
	private static final String LOG_RESPONSE_FORMAT = "API_RESPONSE|{} {}, HEADERS:{}, PARAMS:{}, BODY:{}";
	public static final String LOG_INPUT = "[{}]: {}";
	public static final String DATABASE = "[DATABASE] [{}]: {}";
	
	public static final String X_ORDER_REF_NAME = "x-orderRef";
	public static final String X_ACIM_USER = "x-acim-user";
	
	private static final String DEFAULT_ENCODING = "UTF-8";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		request = new RestRequestWrapper(request);
		response = new RestResponseWrapper(response);
		
		
		String acimUser = request.getHeader(X_ACIM_USER);
		String xOrderRef = request.getHeader(X_ORDER_REF_NAME);
		String headers = CommonUtil.getRequestHeadersInfo(request);
		String uri = request.getRequestURI();
		String method = request.getMethod();
		String params = CommonUtil.getRequestParameters(request);
		String body = IOUtils.toString(request.getInputStream(), DEFAULT_ENCODING);
		
		URLService apiService = URLService.getPageByUri(uri);
		
		if(apiService != null) {
			MDC.put("page", apiService.getPage());
		}else{
			MDC.put("page", "home");
		}
			
		MDC.put(X_ACIM_USER, acimUser);
		MDC.put(X_ORDER_REF_NAME, xOrderRef);
		
		logger.info(LOG_RERQUEST_FORMAT,method, uri, headers, params, body);
		
		filterChain.doFilter(request, response);
		
		RestResponseWrapper res = (RestResponseWrapper) response;
		
		// set x-OrderRef response header from request
		response.setHeader(X_ORDER_REF_NAME, xOrderRef);
		
		String resheaders = CommonUtil.getResponseHeadersInfo(res);
		String resBody = new String(res.toByteArray(), DEFAULT_ENCODING);
		
		logger.info(LOG_RESPONSE_FORMAT,method, uri, resheaders, "", resBody );
		
		
		
	}

}

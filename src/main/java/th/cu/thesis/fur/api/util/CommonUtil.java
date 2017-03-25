package th.cu.thesis.fur.api.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommonUtil {
	
	//user profile form
	public static final String APPLICATION_INFO_AUTHORIZE_ALL = "0";
	public static final String APPLICATION_INFO_AUTHORIZE_ALLOWED = "1";
	public static final String APPLICATION_INFO_AUTHORIZE_NOT_ALLOWED = "2";
	
	public static final String APPLICATION_TYPE_ALL = "0";
	public static final String APPLICATION_TYPE_APPLICATION = "1";
	public static final String APPLICATION_TYPE_TELECOM = "2";
	
	public static final String HEADER_X_ACIM_USER = "x-acim-user";
	public static final String HEADER_X_ACIM_CLIENT = "x-acim-client";
	
	public static final String LOG_SIZE_1 = "1";
	
	private static final Gson gson = new GsonBuilder().create();
	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
	private static final SimpleDateFormat formatterbatch = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	public static String toSqlLikeFormat(String param) {
		param = StringUtils.replace(param, "_", "[_]");
		param = StringUtils.replace(param, "%", "[%]");
		if(StringUtils.isNotBlank(param)){
			return "%"+param+"%";
		}
		return "%%";
	}
	
	public static Gson getGson() {
		return gson;
	}
	
	public static String genRequestNo (Integer requestNo){
		String pattern = "yyyyMMdd";
		String dt = DateFormatUtils.format(Calendar.getInstance().getTime(), pattern);
		return dt + String.format("%05d", requestNo);
	}
	
	public static String genUrNo (String noPrimaryRequestNo,Integer value){
		noPrimaryRequestNo = noPrimaryRequestNo + "-" +String.format("%03d", value);
		return noPrimaryRequestNo;
	}
	
	public static Date textToDate (String datetime){
		try{
			return formatter.parse(datetime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public static Date textToDateForBatch (String datetime){
		try{
			return formatterbatch.parse(datetime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getRequestHeadersInfo(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();
		ArrayList<String> headerKey = new ArrayList<>(Arrays.asList(HEADER_X_ACIM_USER, HEADER_X_ACIM_CLIENT, "x-orderref", "content-type", "host"));
		//List<String> headerKey = Arrays.asList(HEADER_X_ACIM_USER, HEADER_X_ACIM_CLIENT, "x-orderref", "content-type", "host");

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			
			if(headerKey.contains(key.toLowerCase())){
				map.put(key, value);
			}
			
		}

		return map.toString();
	}
	
	public static String getRequestParameters(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> paramNames = request.getParameterNames();
		if (paramNames != null) {
			while (paramNames.hasMoreElements()) {
				String key = (String) paramNames.nextElement();
				String value = request.getParameter(key);
				map.put(key, value);
			}
		}

		return map.toString();
	}
	
	public static String getResponseHeadersInfo(HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int status = response.getStatus();
		map.put("status code", status +" "+ HttpStatus.valueOf(status).getReasonPhrase());
		map.put("content-type", response.getContentType() +";charset="+ response.getCharacterEncoding());
		
		Collection<String> headerNames = response.getHeaderNames();
		for (String name : headerNames) {
			String headerVal = response.getHeader(name);
			map.put(name, headerVal);
		}

		return map.toString();
	}
	
	public static Date getDateForSearchAtEndDate (Date now){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DATE, 1);
		calendar.add(Calendar.SECOND, -1);
		return calendar.getTime();
	}
	
}

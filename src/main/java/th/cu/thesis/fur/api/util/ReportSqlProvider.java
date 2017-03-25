package th.cu.thesis.fur.api.util;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ReportSqlProvider {
	
	///Search Report Application
	public String countSearchReportApplication (Map<String, Object> params){
		String appName = (String) params.get("appName");
		String authenType = (String) params.get("authenType");
		String type = (String) params.get("type");
		String status = (String) params.get("status");
		String periodType = (String) params.get("periodType");
		String username = (String) params.get("username");
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT COUNT(*) ");
		sqlBuilder.append("		FROM(  ");
		sqlBuilder.append("			SELECT   ");
		sqlBuilder.append(" 					ROW_NUMBER() OVER(ORDER BY APP.APP_NAME,APP_ROLE.APP_ROLE_NAME) ROWNUM ");
		sqlBuilder.append(" 				  ,APP.APP_ID ");
		sqlBuilder.append(" 				  ,APP.APP_NAME ");
		sqlBuilder.append(" 				  ,APP_ROLE.APP_ROLE_ID ");
		sqlBuilder.append(" 				  ,APP_ROLE.APP_ROLE_NAME ");
		sqlBuilder.append(" 				  ,USER_APP_ROLE.UR_ID ");
		sqlBuilder.append(" 				  ,(CASE WHEN APP.TYPE = '1' THEN 'Application' WHEN APP.TYPE = '2' THEN 'Telecom' END ) TYPE ");
		sqlBuilder.append(" 				  ,(CASE WHEN APP.STATUS = '1' THEN 'Active' WHEN APP.STATUS = '2' THEN 'InActive' END )  STATUS ");
		sqlBuilder.append(" 				  ,(CASE WHEN USER_APP_ROLE.PERIOD_TYPE =1 THEN 'PERMANENT' ");
//		if(StringUtils.isNotEmpty(periodType)&&StringUtils.isNotBlank(periodType)){
//			if("2".equals("periodType")){
			sqlBuilder.append(" WHEN USER_APP_ROLE.PERIOD_TYPE =2 THEN 'Temporary'  ");
//			}
//		}
		sqlBuilder.append("   END) PERIOD_TYPE  ");		
		sqlBuilder.append(" 				  ,[USER].USERNAME ");
		sqlBuilder.append("					  ,CONVERT(varchar(20),USER_APP_ROLE.START_TIME,103) START_TIME");
		sqlBuilder.append("					  ,CONVERT(varchar(20),USER_APP_ROLE.END_TIME,103) END_TIME");
		sqlBuilder.append(" 			FROM APP ");
		sqlBuilder.append(" 			INNER JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID  ");
		if(StringUtils.isNotEmpty(appName)&&StringUtils.isNotBlank(appName)){
			sqlBuilder.append(" 			AND APP.APP_NAME LIKE #{appName}  ");
		}
		if(StringUtils.isNotEmpty(type)&&StringUtils.isNotBlank(type)){
			sqlBuilder.append(" 			AND APP.TYPE =#{type} ");
		}
		if(StringUtils.isNotEmpty(status)&&StringUtils.isNotBlank(status)){
			sqlBuilder.append(" 			AND APP.STATUS=#{status} ");
		}
		sqlBuilder.append(" 			INNER JOIN APP_AUTHEN ON APP_AUTHEN.APP_ID = APP.APP_ID ");
		if(StringUtils.isNotEmpty(authenType)&&StringUtils.isNotBlank(authenType)){
			sqlBuilder.append(" 			AND APP_AUTHEN.AUTHEN_TYPE_NAME = #{authenType} ");	
		}
		sqlBuilder.append(" 			INNER JOIN USER_APP_ROLE ON  APP_ROLE.APP_ROLE_ID = USER_APP_ROLE.APP_ROLE_ID ");
		if(StringUtils.isNotEmpty(periodType)&&StringUtils.isNotBlank(periodType)){
		sqlBuilder.append(" 			AND USER_APP_ROLE.PERIOD_TYPE = #{periodType} ");
			if("2".equals(periodType)){
				sqlBuilder.append(" 			AND USER_APP_ROLE.START_TIME BETWEEN #{startDate} AND #{endDate} ");
			}
		}
		sqlBuilder.append(" 			INNER JOIN [USER] ON [USER].USER_ID = USER_APP_ROLE.USER_ID ");
		if(StringUtils.isNotEmpty(username)&&StringUtils.isNotBlank(username)){
			sqlBuilder.append("  AND [USER].USERNAME LIKE #{username} ");
		}
		sqlBuilder.append("GROUP BY APP.APP_ID ,APP.APP_NAME ,APP_ROLE.APP_ROLE_ID 	,APP_ROLE.APP_ROLE_NAME,[USER].USERNAME 	,APP.TYPE,APP.STATUS,USER_APP_ROLE.PERIOD_TYPE ,USER_APP_ROLE.END_TIME ,USER_APP_ROLE.START_TIME,USER_APP_ROLE.UR_ID  ");
		sqlBuilder.append(" 			)  ");
		sqlBuilder.append(" 		DATA ");
		return sqlBuilder.toString();
	}
	public String searchReportApplication (Map<String, Object> params){
		String appName = (String) params.get("appName");
		String authenType = (String) params.get("authenType");
		String type = (String) params.get("type");
		String status = (String) params.get("status");
		String periodType = (String) params.get("periodType");
		String username = (String) params.get("username");
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT * ");
		sqlBuilder.append("		FROM(  ");
		sqlBuilder.append("			SELECT   ");
		sqlBuilder.append(" 					ROW_NUMBER() OVER(ORDER BY APP.APP_NAME,APP_ROLE.APP_ROLE_NAME) ROWNUM ");
		sqlBuilder.append(" 				  ,APP.APP_ID ");
		sqlBuilder.append(" 				  ,APP.APP_NAME ");
		sqlBuilder.append(" 				  ,APP_ROLE.APP_ROLE_ID ");
		sqlBuilder.append(" 				  ,APP_ROLE.APP_ROLE_NAME ");
		sqlBuilder.append(" 				  ,USER_APP_ROLE.UR_ID ");
		sqlBuilder.append(" 				  ,(CASE WHEN APP.TYPE = '1' THEN 'Application' WHEN APP.TYPE = '2' THEN 'Telecom' END ) TYPE ");
		sqlBuilder.append(" 				  ,(CASE WHEN APP.STATUS = '1' THEN 'Active' WHEN APP.STATUS = '2' THEN 'InActive' END )  STATUS ");
		sqlBuilder.append(" 				  ,(CASE WHEN USER_APP_ROLE.PERIOD_TYPE =1 THEN 'Permanent' ");
//		if(StringUtils.isNotEmpty(periodType)&&StringUtils.isNotBlank(periodType)){
//			if("2".equals("periodType")){
			sqlBuilder.append(" WHEN USER_APP_ROLE.PERIOD_TYPE =2 THEN 'Temporary' ");
//			}
//		}
		sqlBuilder.append("   END) PERIOD_TYPE  ");		
		sqlBuilder.append(" 				  ,[USER].USERNAME ");
		sqlBuilder.append("					  ,CONVERT(varchar(20),USER_APP_ROLE.START_TIME,103) START_TIME");
		sqlBuilder.append("					  ,CONVERT(varchar(20),USER_APP_ROLE.END_TIME,103) END_TIME");
		sqlBuilder.append(" 			FROM APP ");
		sqlBuilder.append(" 			INNER JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID  ");
		if(StringUtils.isNotEmpty(appName)&&StringUtils.isNotBlank(appName)){
			sqlBuilder.append(" 			AND APP.APP_NAME LIKE #{appName}  ");
		}
		if(StringUtils.isNotEmpty(type)&&StringUtils.isNotBlank(type)){
			sqlBuilder.append(" 			AND APP.TYPE =#{type} ");
		}
		if(StringUtils.isNotEmpty(status)&&StringUtils.isNotBlank(status)){
			sqlBuilder.append(" 			AND APP.STATUS=#{status} ");
		}
		sqlBuilder.append(" 			INNER JOIN APP_AUTHEN ON APP_AUTHEN.APP_ID = APP.APP_ID ");
		if(StringUtils.isNotEmpty(authenType)&&StringUtils.isNotBlank(authenType)){
			sqlBuilder.append(" 			AND APP_AUTHEN.AUTHEN_TYPE_NAME = #{authenType} ");	
		}
		sqlBuilder.append(" 			INNER JOIN USER_APP_ROLE ON  APP_ROLE.APP_ROLE_ID = USER_APP_ROLE.APP_ROLE_ID ");
		if(StringUtils.isNotEmpty(periodType)&&StringUtils.isNotBlank(periodType)){
		sqlBuilder.append(" 			AND USER_APP_ROLE.PERIOD_TYPE = #{periodType} ");
			if("2".equals(periodType)){
				sqlBuilder.append(" 			AND USER_APP_ROLE.START_TIME BETWEEN #{startDate} AND #{endDate} ");
			}
		}
		sqlBuilder.append(" 			INNER JOIN [USER] ON [USER].USER_ID = USER_APP_ROLE.USER_ID ");
		if(StringUtils.isNotEmpty(username)&&StringUtils.isNotBlank(username)){
			sqlBuilder.append("  AND [USER].USERNAME LIKE #{username} ");
		}
		sqlBuilder.append("GROUP BY APP.APP_ID ,APP.APP_NAME ,APP_ROLE.APP_ROLE_ID 	,APP_ROLE.APP_ROLE_NAME,[USER].USERNAME 	,APP.TYPE,APP.STATUS,USER_APP_ROLE.PERIOD_TYPE ,USER_APP_ROLE.END_TIME ,USER_APP_ROLE.START_TIME ,USER_APP_ROLE.UR_ID ");
		sqlBuilder.append(" 			)  ");
		sqlBuilder.append(" 		DATA WHERE DATA.ROWNUM BETWEEN #{startRow} AND #{endRow} ");
		return sqlBuilder.toString();
	}
	
	public String countSearchReportUr (Map<String, Object> params){
		String requestNo =(String) params.get("requestNo");
		String urNo =(String) params.get("urNo");
		String requestType =(String) params.get("requestType");
		String urStatus =(String) params.get("urStatus");
		String urNode =(String) params.get("urNode");
		String urFlow =(String) params.get("urFlow");
		String userList =(String) params.get("userList");
		String appName =(String) params.get("appName");
		String type =(String) params.get("type");
		String userType =(String) params.get("userType");
		String authorUser =(String) params.get("authorUser");
		String requestUrBy = (String) params.get("requestUrBy");
		Date startDate = (Date) params.get("startDate");
		Date endDate = (Date) params.get("endDate");
		Date startDateUFU = (Date) params.get("startDateUFU");
		Date endDateUFU = (Date) params.get("endDateUFU");
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT COUNT(*) FROM ( ");
		sqlBuilder.append("SELECT REQUEST_NO.REQUEST_NO,UR.UR_ID,REQUEST_NO.USERNAME,REQUEST_NO.CREATED_DATE ");
		sqlBuilder.append("FROM REQUEST_NO ");
		sqlBuilder.append(" INNER JOIN UR ON REQUEST_NO.REQUEST_NO = UR.REQUEST_NO ");
		if(StringUtils.isNotEmpty(requestNo)&&StringUtils.isNotBlank(requestNo)){
			sqlBuilder.append(" AND REQUEST_NO.REQUEST_NO LIKE #{requestNo} ");
		}
		if(StringUtils.isNotEmpty(urFlow)&&StringUtils.isNotBlank(urFlow)){
			sqlBuilder.append(" 	AND UR.FLOW_ID= #{urFlow} ");
		}
		if(StringUtils.isNotEmpty(urNo)&&StringUtils.isNotBlank(urNo)){
			sqlBuilder.append(" 	AND UR.UR_ID LIKE #{urNo} ");
		}
		if(StringUtils.isNotEmpty(appName)&&StringUtils.isNotBlank(appName)){
			sqlBuilder.append(" 	AND UR.APP_NAME LIKE #{appName} ");
		}
		if(StringUtils.isNotEmpty(requestType)&&StringUtils.isNotBlank(requestType)){
			sqlBuilder.append(" 	AND UR.REQUEST_TYPE = #{requestType} ");
		}
		if(StringUtils.isNotEmpty(urStatus)&&StringUtils.isNotBlank(urStatus)){
			sqlBuilder.append(" 	 AND UR.STATUS = #{urStatus} ");
		}
		if(StringUtils.isNotEmpty(requestUrBy)&&StringUtils.isNotBlank(requestUrBy)){
			sqlBuilder.append(" 	AND REQUEST_NO.USERNAME LIKE #{requestUrBy} ");
		}
		if(startDate!=null&&endDate!=null){
			sqlBuilder.append(" 	AND CAST(REQUEST_NO.REQUEST_DATE AS DATE) >= #{startDate}  AND CAST(REQUEST_NO.REQUEST_DATE AS DATE) < #{endDate} ");
		}
		sqlBuilder.append(" INNER JOIN APP ON UR.APP_ID=APP.APP_ID ");
		if(StringUtils.isNotEmpty(type)&&StringUtils.isNotBlank(type)){
			sqlBuilder.append(" 		AND APP.TYPE = #{type} ");
		}
		if(StringUtils.isNotEmpty(appName)&&StringUtils.isNotBlank(appName)){
			sqlBuilder.append(" 	AND APP.APP_NAME LIKE #{appName} ");
		}
		sqlBuilder.append(" INNER JOIN UR_STEP ON UR_STEP.UR_ID = UR.UR_ID  "); //AND UR_STEP.STATUS=1
		if(StringUtils.isNotEmpty(urNode)&&StringUtils.isNotBlank(urNode)){
			sqlBuilder.append(" 	AND UR_STEP.UR_STEP= #{urNode} ");
		}
		sqlBuilder.append(" INNER JOIN UR_FOR_USER ON UR_FOR_USER.UR_ID = UR.UR_ID ");
		if(StringUtils.isNotEmpty(userList)&&StringUtils.isNotBlank(userList)){
			sqlBuilder.append(" 	AND UR_FOR_USER.USERNAME LIKE #{userList} ");
		}
		sqlBuilder.append(" INNER JOIN ORGANIZE ON UR_FOR_USER.ORGCODE = ORGANIZE.ORGCODE  ");
		if(StringUtils.isNotEmpty(userType)&&StringUtils.isNotBlank(userType)){
			sqlBuilder.append(" 	AND ORGANIZE.ORG_TYPE= #{userType} ");
		}
		sqlBuilder.append(" GROUP BY REQUEST_NO.REQUEST_NO,UR.UR_ID,REQUEST_NO.USERNAME,REQUEST_NO.CREATED_DATE ");
		sqlBuilder.append(" ) DATA ");
		return sqlBuilder.toString();
	}
	
	public String searchReportUr (Map<String, Object> params){
		String requestNo =(String) params.get("requestNo");
		String urNo =(String) params.get("urNo");
		String requestType =(String) params.get("requestType");
		String urStatus =(String) params.get("urStatus");
		String urNode =(String) params.get("urNode");
		String urFlow =(String) params.get("urFlow");
		String userList =(String) params.get("userList");
		String appName =(String) params.get("appName");
		String type =(String) params.get("type");
		String userType =(String) params.get("userType");
		String authorUser =(String) params.get("authorUser");
		String requestUrBy = (String) params.get("requestUrBy");
		Date startDate = (Date) params.get("startDate");
		Date endDate = (Date) params.get("endDate");
		Date startDateUFU = (Date) params.get("startDateUFU");
		Date endDateUFU = (Date) params.get("endDateUFU");
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT * FROM ( ");
		sqlBuilder.append("SELECT ROW_NUMBER() OVER(ORDER BY REQUEST_NO.REQUEST_NO) ROWNUM ");
		sqlBuilder.append("		,REQUEST_NO.REQUEST_NO ");
		sqlBuilder.append("		,UR.UR_ID ");
		sqlBuilder.append("		,REQUEST_NO.CREATED_DATE REQUEST_DATE");
		sqlBuilder.append("		,REQUEST_NO.USERNAME REQUEST_BY ");
		sqlBuilder.append("FROM REQUEST_NO ");
		sqlBuilder.append(" INNER JOIN UR ON REQUEST_NO.REQUEST_NO = UR.REQUEST_NO ");
		if(StringUtils.isNotEmpty(requestNo)&&StringUtils.isNotBlank(requestNo)){
			sqlBuilder.append(" AND REQUEST_NO.REQUEST_NO LIKE #{requestNo} ");
		}
		if(StringUtils.isNotEmpty(urFlow)&&StringUtils.isNotBlank(urFlow)){
			sqlBuilder.append(" 	AND UR.FLOW_ID= #{urFlow} ");
		}
		if(StringUtils.isNotEmpty(urNo)&&StringUtils.isNotBlank(urNo)){
			sqlBuilder.append(" 	AND UR.UR_ID LIKE #{urNo} ");
		}
		if(StringUtils.isNotEmpty(appName)&&StringUtils.isNotBlank(appName)){
			sqlBuilder.append(" 	AND UR.APP_NAME LIKE #{appName} ");
		}
		if(StringUtils.isNotEmpty(requestType)&&StringUtils.isNotBlank(requestType)){
			sqlBuilder.append(" 	AND UR.REQUEST_TYPE = #{requestType} ");
		}
		if(StringUtils.isNotEmpty(urStatus)&&StringUtils.isNotBlank(urStatus)){
			sqlBuilder.append(" 	 AND UR.STATUS = #{urStatus} ");
		}
		if(StringUtils.isNotEmpty(requestUrBy)&&StringUtils.isNotBlank(requestUrBy)){
			sqlBuilder.append(" 	AND REQUEST_NO.USERNAME LIKE #{requestUrBy} ");
		}
		if(startDate!=null&&endDate!=null){
			sqlBuilder.append(" 	AND CAST(REQUEST_NO.REQUEST_DATE AS DATE) >= #{startDate}  AND CAST(REQUEST_NO.REQUEST_DATE AS DATE) < #{endDate} ");
		}
		sqlBuilder.append(" INNER JOIN APP ON UR.APP_ID=APP.APP_ID ");
		if(StringUtils.isNotEmpty(type)&&StringUtils.isNotBlank(type)){
			sqlBuilder.append(" 		AND APP.TYPE = #{type} ");
		}
		if(StringUtils.isNotEmpty(appName)&&StringUtils.isNotBlank(appName)){
			sqlBuilder.append(" 	AND APP.APP_NAME LIKE #{appName} ");
		}
		sqlBuilder.append(" INNER JOIN UR_STEP ON UR_STEP.UR_ID = UR.UR_ID  "); //AND UR_STEP.STATUS=1
		if(StringUtils.isNotEmpty(urNode)&&StringUtils.isNotBlank(urNode)){
			sqlBuilder.append(" 	AND UR_STEP.UR_STEP= #{urNode} ");
		}
		sqlBuilder.append(" INNER JOIN UR_FOR_USER ON UR_FOR_USER.UR_ID = UR.UR_ID ");
		if(StringUtils.isNotEmpty(userList)&&StringUtils.isNotBlank(userList)){
			sqlBuilder.append(" 	AND UR_FOR_USER.USERNAME LIKE #{userList} ");
		}
		sqlBuilder.append(" INNER JOIN ORGANIZE ON UR_FOR_USER.ORGCODE = ORGANIZE.ORGCODE  ");
		if(StringUtils.isNotEmpty(userType)&&StringUtils.isNotBlank(userType)){
			sqlBuilder.append(" 	AND ORGANIZE.ORG_TYPE= #{userType} ");
		}
		sqlBuilder.append(" GROUP BY REQUEST_NO.REQUEST_NO,UR.UR_ID,REQUEST_NO.USERNAME,REQUEST_NO.CREATED_DATE ");
		sqlBuilder.append(" 					) DATAS WHERE DATAS.ROWNUM BETWEEN #{startRow} AND #{endRow} ");
		return sqlBuilder.toString();
	}
	//Search Eligible
	public String countSearchEligible(Map<String, Object> params){
		String orgCode = (String) params.get("orgCode");
		String orgDesc =(String) params.get("orgDesc");
		String type = (String)params.get("type");
		String appName = (String)params.get("appName");
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT COUNT(*) ");
		sqlBuilder.append("FROM [ELIGIBLE] ELI ");
		sqlBuilder.append("INNER JOIN [ORGANIZE] ORG ON ELI.ORGCODE = ORG.ORGCODE  ");
		if(StringUtils.isNotEmpty(orgCode)&&StringUtils.isNotBlank(orgCode)){
			sqlBuilder.append(" AND ORG.ORGCODE LIKE #{orgCode} ");
		}
		if(StringUtils.isNotEmpty(orgDesc)&&StringUtils.isNotBlank(orgDesc)){
			sqlBuilder.append(" AND ORG.ORGDESC LIKE #{orgDesc} ");
		}
		sqlBuilder.append("INNER JOIN [APP_ROLE] ROLE ON ELI.APP_ROLE_ID = ROLE.APP_ROLE_ID ");
		sqlBuilder.append("INNER JOIN [APP] APP ON APP.APP_ID = ROLE.APP_ID ");
		if(StringUtils.isNotEmpty(appName)&&StringUtils.isNotBlank(appName)){
			sqlBuilder.append(" AND APP.APP_NAME LIKE #{appName} ");
		}
		if(StringUtils.isNotEmpty(type)&&StringUtils.isNotBlank(type)){
			sqlBuilder.append(" AND APP.TYPE = #{type} ");
		}
		return sqlBuilder.toString();
	}
	
	public String searchEligible(Map<String, Object> params){
		String orgCode = (String) params.get("orgCode");
		String orgDesc =(String) params.get("orgDesc");
		String type = (String)params.get("type");
		String appName = (String)params.get("appName");
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT * FROM ( ");
		sqlBuilder.append("SELECT ROW_NUMBER() OVER(ORDER BY ORG.ORGCODE ASC,APP.APP_NAME ASC,ROLE.APP_ROLE_NAME ASC) ROWNUM ,ELI.ELIGIBLE_ID,ORG.ORGCODE,ORG.ORGNAME,ORG.ORGDESC,APP.APP_ID,APP.APP_NAME,ROLE.APP_ROLE_ID,ROLE.APP_ROLE_NAME ");
		sqlBuilder.append("FROM [ELIGIBLE] ELI ");
		sqlBuilder.append("INNER JOIN [ORGANIZE] ORG ON ELI.ORGCODE = ORG.ORGCODE  ");
		if(StringUtils.isNotEmpty(orgCode)&&StringUtils.isNotBlank(orgCode)){
			sqlBuilder.append(" AND ORG.ORGCODE LIKE #{orgCode} ");
		}
		if(StringUtils.isNotEmpty(orgDesc)&&StringUtils.isNotBlank(orgDesc)){
			sqlBuilder.append(" AND ORG.ORGDESC LIKE #{orgDesc} ");
		}
		sqlBuilder.append("INNER JOIN [APP_ROLE] ROLE ON ELI.APP_ROLE_ID = ROLE.APP_ROLE_ID ");
		sqlBuilder.append("INNER JOIN [APP] APP ON APP.APP_ID = ROLE.APP_ID ");
		if(StringUtils.isNotEmpty(appName)&&StringUtils.isNotBlank(appName)){
			sqlBuilder.append(" AND APP.APP_NAME LIKE #{appName} ");
		}
		if(StringUtils.isNotEmpty(type)&&StringUtils.isNotBlank(type)){
			sqlBuilder.append(" AND APP.TYPE = #{type} ");
		}
		sqlBuilder.append(" 					) DATAS WHERE DATAS.ROWNUM BETWEEN #{startRow} AND #{endRow} ");
		return sqlBuilder.toString();
	}
	
	public String getFlows (){
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT FLOW_ID,FLOW_NAME ");
		sqlBuilder.append("FROM [FLOW] ");
		return sqlBuilder.toString();
	}
}

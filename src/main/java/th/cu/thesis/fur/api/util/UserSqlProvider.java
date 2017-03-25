package th.cu.thesis.fur.api.util;

import java.util.Map;

public class UserSqlProvider {

	private static final String APP_NAME = "appName";
	private static final String APP_ROLE_NAME = "appRoleName";
	
	private static final String SORT_BY_APP_ROLE_NAME = "APPROLE.APP_ROLE_NAME";
	private static final String SORT_BY_APP_NAME = "APP.APP_NAME";
	private static final String ACTIVE_STATUS = "1";
	
	public String selectCountStandardApplication(Map<String, Object> params) {
		String authorizationType = (String) params.get("authorizationType");
		String applicationType = (String) params.get("applicationType");

		String sql = "SELECT COUNT(*) FROM ELIGIBLE ELI "
				+ "JOIN [APP_ROLE] APPROLE ON APPROLE.APP_ROLE_ID = ELI.APP_ROLE_ID "
				+ "JOIN [APP] APP ON APP.APP_ID = APPROLE.APP_ID "
				+ "WHERE ELI.ORGCODE IN (SELECT ORGCODE FROM [USER] WHERE USERNAME = #{username}) "
				+ "AND APP.APP_NAME LIKE #{appName} "
				+ "AND APP.STATUS = "+ ACTIVE_STATUS;
	
		
		if (!CommonUtil.APPLICATION_TYPE_ALL.equals(applicationType)) {
				sql += " AND TYPE = #{applicationType} ";
		}
		
		if (!CommonUtil.APPLICATION_INFO_AUTHORIZE_ALL.equals(authorizationType)) {
			if (CommonUtil.APPLICATION_INFO_AUTHORIZE_ALLOWED.equals(authorizationType)) {
				sql += "AND ELI.APP_ROLE_ID IN ( SELECT APP_ROLE_ID FROM USER_APP_ROLE WHERE "
						+ "USER_ID IN ( SELECT USER_ID FROM [USER] WHERE USERNAME = #{username} ) "
						+ ")";
			} else if (CommonUtil.APPLICATION_INFO_AUTHORIZE_NOT_ALLOWED.equals(authorizationType)) {
				sql += "AND ELI.APP_ROLE_ID NOT IN ( SELECT APP_ROLE_ID FROM USER_APP_ROLE WHERE "
						+ "USER_ID IN ( SELECT USER_ID FROM [USER] WHERE USERNAME = #{username} ) "
						+ ")";
			} 
		}

		return sql;
	}
	
	public String selectStandardApplication(Map<String, Object> params) {

		String authorizationType = (String) params.get("authorizationType");
		String applicationType = (String) params.get("applicationType");
//		String sord = (String) params.get("sord");
//		String sidx = (String) params.get("sidx");
//		
//		String sordBy = "";
//		if(APP_NAME.equals(sidx)){
//			sordBy = SORT_BY_APP_NAME;
//		}
//		else if(APP_ROLE_NAME.equals(sidx)){
//			sordBy = SORT_BY_APP_ROLE_NAME;
//		}
//		else{
//			sordBy = SORT_BY_APP_NAME;
//		}
		
		String sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY APP.APP_NAME ASC,APPROLE.APP_ROLE_NAME ASC) AS RNUM,"
				+ "APP.APP_NAME,APPROLE.APP_ROLE_ID,APPROLE.APP_ROLE_NAME "
				+ "FROM ELIGIBLE ELI "
				+ "JOIN [APP_ROLE] APPROLE ON APPROLE.APP_ROLE_ID = ELI.APP_ROLE_ID "
				+ "JOIN [APP] APP ON APP.APP_ID = APPROLE.APP_ID "
				+ "WHERE ELI.ORGCODE IN (SELECT ORGCODE FROM [USER] WHERE USERNAME = #{username} ) "
				+ "AND APP.APP_NAME LIKE #{appName} "
				+ "AND APP.STATUS = " + ACTIVE_STATUS;
		
		if (!CommonUtil.APPLICATION_TYPE_ALL.equals(applicationType)) {
			sql += " AND TYPE = '" + applicationType + "' ";
		}
		
		if (!CommonUtil.APPLICATION_INFO_AUTHORIZE_ALL.equals(authorizationType)) {
			if (CommonUtil.APPLICATION_INFO_AUTHORIZE_ALLOWED.equals(authorizationType)) {
				sql += "AND ELI.APP_ROLE_ID IN ( SELECT APP_ROLE_ID FROM USER_APP_ROLE WHERE "
						+ "USER_ID IN ( SELECT USER_ID FROM [USER] WHERE USERNAME = #{username} ) "
						+ ")";
			} else if (CommonUtil.APPLICATION_INFO_AUTHORIZE_NOT_ALLOWED.equals(authorizationType)) {
				sql += "AND ELI.APP_ROLE_ID NOT IN ( SELECT APP_ROLE_ID FROM USER_APP_ROLE WHERE "
						+ "USER_ID IN ( SELECT USER_ID FROM [USER] WHERE USERNAME = #{username} ) "
						+ ")";
			} 
		}

		sql += ") RESULT WHERE RESULT.RNUM BETWEEN #{startRow} AND #{endRow} ORDER BY RESULT.APP_NAME";
		return sql;
	}
	
//	public String selectCountStandardApplication(Map<String, Object> params) {
//		String authorizationType = (String) params.get("authorizationType");
//		String applicationType = (String) params.get("applicationType");
//
//		String sql = "SELECT COUNT(*) FROM [APP] APP "
//				+ "JOIN APP_ROLE APPROLE ON APP.APP_ID = APPROLE.APP_ID "
//				+ "LEFT OUTER JOIN USER_APP_ROLE UAR ON UAR.APP_ROLE_ID = APPROLE.APP_ROLE_ID "
//				+ "WHERE APPROLE.APP_ROLE_ID IN "
//				+ "( SELECT APP_ROLE_ID FROM ELIGIBLE ELI WHERE ORGCODE IN "
//				+ "( SELECT ORGCODE FROM [USER] WHERE USERNAME = #{username}) "
//				+ ") AND APP_NAME LIKE  #{appName} ";
//	
//		
//		if (!CommonUtil.APPLICATION_TYPE_ALL.equals(applicationType)) {
//				sql += "AND TYPE = #{applicationType} ";
//		}
//		
//		if (!CommonUtil.APPLICATION_INFO_AUTHORIZE_ALL.equals(authorizationType)) {
//			if (CommonUtil.APPLICATION_INFO_AUTHORIZE_ALLOWED.equals(authorizationType)) {
//				sql += "AND APPROLE.APP_ROLE_ID IN ( SELECT APP_ROLE_ID FROM USER_APP_ROLE )";
//			} else if (CommonUtil.APPLICATION_INFO_AUTHORIZE_NOT_ALLOWED.equals(authorizationType)) {
//
//				sql += "AND APPROLE.APP_ROLE_ID NOT IN ( SELECT APP_ROLE_ID FROM USER_APP_ROLE )";
//			} 
//
//		}
//
//		return sql;
//	}

//	public String selectStandardApplication(Map<String, Object> params) {
//
//		String username = (String) params.get("username");
//		String appName = (String) params.get("appName");
//		String authorizationType = (String) params.get("authorizationType");
//		String applicationType = (String) params.get("applicationType");
//		int startRow = (int) params.get("startRow");
//		int endRow = (int) params.get("endRow");
//		String sord = (String) params.get("sord");
//		String sidx = (String) params.get("sidx");
//		
//		String sordBy = "";
//		if(APP_NAME.equals(sidx)){
//			sordBy = SORT_BY_APP_NAME;
//		}
//		else if(APP_ROLE_NAME.equals(sidx)){
//			sordBy = SORT_BY_APP_ROLE_NAME;
//		}
//		else{
//			sordBy = SORT_BY_APP_NAME;
//		}
//		
//		String sql = "SELECT RESULT.APP_NAME,RESULT.APP_ROLE_ID,RESULT.APP_ROLE_NAME,RESULT.UR_ID,RESULT.PERIOD_TYPE,RESULT.START_TIME,RESULT.END_TIME FROM "
//				+ "(SELECT ROW_NUMBER() OVER(ORDER BY "+ sordBy+" "+ sord +") AS RNUM,APP.APP_NAME,APPROLE.APP_ROLE_ID,APPROLE.APP_ROLE_NAME,UAR.UR_ID,UAR.PERIOD_TYPE,UAR.START_TIME,UAR.END_TIME FROM [APP] APP "
//				+ "JOIN APP_ROLE APPROLE ON APP.APP_ID = APPROLE.APP_ID "
//				+ "LEFT OUTER JOIN USER_APP_ROLE UAR ON UAR.APP_ROLE_ID = APPROLE.APP_ROLE_ID "
//				+ "WHERE APPROLE.APP_ROLE_ID IN "
//				+ "( SELECT APP_ROLE_ID FROM ELIGIBLE ELI WHERE ORGCODE IN "
//				+ "( SELECT ORGCODE FROM [USER] WHERE USERNAME = '" + username + "') "
//				+ ") AND APP_NAME LIKE  '" + appName + "' ";
//		
//		
//		if (!CommonUtil.APPLICATION_TYPE_ALL.equals(applicationType)) {
//			sql += "AND TYPE = '" + applicationType + "' ";
//		}
//		
//		if (!CommonUtil.APPLICATION_INFO_AUTHORIZE_ALL.equals(authorizationType)) {
//			if (CommonUtil.APPLICATION_INFO_AUTHORIZE_ALLOWED.equals(authorizationType)) {
//				sql += "AND APPROLE.APP_ROLE_ID IN ( SELECT APP_ROLE_ID FROM USER_APP_ROLE WHERE "
//						+ "USER_ID IN ( SELECT USER_ID FROM [USER] WHERE USERNAME = #{username} ))";
//			} else if (CommonUtil.APPLICATION_INFO_AUTHORIZE_NOT_ALLOWED.equals(authorizationType)) {
//				sql += "AND APPROLE.APP_ROLE_ID NOT IN ( SELECT APP_ROLE_ID FROM USER_APP_ROLE WHERE "
//						+ "USER_ID IN ( SELECT USER_ID FROM [USER] WHERE USERNAME = #{username} ))";
//			} 
//		}
//
//		sql += ") RESULT WHERE RESULT.RNUM BETWEEN " + startRow + " AND " + endRow;
//		return sql;
//	}

	public String selectCountSpecialApplication(Map<String, Object> params) {
		String authorizationType = (String) params.get("authorizationType");
		String applicationType = (String) params.get("applicationType");

		String sql = "SELECT COUNT(*) FROM USER_APP_ROLE  UAR "
				+ "JOIN [APP_ROLE] APPROLE ON APPROLE.APP_ROLE_ID = UAR.APP_ROLE_ID "
				+ "JOIN [APP] APP ON APP.APP_ID = APPROLE.APP_ID "
				+ "WHERE USER_ID IN ( SELECT USER_ID FROM [USER] WHERE USERNAME = #{username}) "
				+ "AND UAR.APP_ROLE_ID NOT IN ( SELECT APP_ROLE_ID FROM ELIGIBLE ) "
				+ "AND APP.APP_NAME LIKE #{appName} ";
				
		if (!CommonUtil.APPLICATION_TYPE_ALL.equals(applicationType)) {
			sql += "AND TYPE = '" + applicationType + "' ";
		}
		
		if (!CommonUtil.APPLICATION_INFO_AUTHORIZE_ALL.equals(authorizationType)) {
			if (CommonUtil.APPLICATION_INFO_AUTHORIZE_ALLOWED.equals(authorizationType)) {
				sql += "AND UAR.APP_ROLE_ID IN ( SELECT APP_ROLE_ID FROM USER_APP_ROLE)";
			} else if (CommonUtil.APPLICATION_INFO_AUTHORIZE_NOT_ALLOWED.equals(authorizationType)) {
				sql += "AND UAR.APP_ROLE_ID NOT IN ( SELECT APP_ROLE_ID FROM USER_APP_ROLE)";
			} 
		}
		
		return sql;
	}

	public String selectSpecialApplication(Map<String, Object> params) {
		String authorizationType = (String) params.get("authorizationType");
		String applicationType = (String) params.get("applicationType");
		
		String sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY APP.APP_NAME ASC,APP_ROLE_NAME ASC) AS RNUM"
				+ ",APP.APP_NAME,APPROLE.APP_ROLE_NAME,UAR.PERIOD_TYPE,UAR.START_TIME,UAR.END_TIME,UAR.UR_ID "
				+ "FROM USER_APP_ROLE UAR "
				+ "JOIN [APP_ROLE] APPROLE ON APPROLE.APP_ROLE_ID = UAR.APP_ROLE_ID "
				+ "JOIN [APP] APP ON APP.APP_ID = APPROLE.APP_ID "
				+ "WHERE USER_ID IN ( SELECT USER_ID FROM [USER] WHERE USERNAME = #{username}) "
				+ "AND UAR.APP_ROLE_ID NOT IN ( SELECT APP_ROLE_ID FROM ELIGIBLE ) "
				+ "AND APP.APP_NAME LIKE #{appName} ";
		
		if(!CommonUtil.APPLICATION_TYPE_ALL.equals(applicationType)) {
			sql += "AND TYPE = '" + applicationType + "' ";
		}
		
		if (!CommonUtil.APPLICATION_INFO_AUTHORIZE_ALL.equals(authorizationType)) {
			if (CommonUtil.APPLICATION_INFO_AUTHORIZE_ALLOWED.equals(authorizationType)) {
				sql += "AND UAR.APP_ROLE_ID IN ( SELECT APP_ROLE_ID FROM USER_APP_ROLE)";
			} else if (CommonUtil.APPLICATION_INFO_AUTHORIZE_NOT_ALLOWED.equals(authorizationType)) {
				sql += "AND UAR.APP_ROLE_ID NOT IN ( SELECT APP_ROLE_ID FROM USER_APP_ROLE)";
			} 
		}
		
		sql += ") RESULT WHERE RESULT.RNUM BETWEEN #{startRow} AND #{endRow}";
		return sql;
	}

}

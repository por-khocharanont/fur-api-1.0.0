package th.cu.thesis.fur.api.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import th.cu.thesis.fur.api.model.RequestNo;
import th.cu.thesis.fur.api.model.UR;
import th.cu.thesis.fur.api.model.UrForUser;
import th.cu.thesis.fur.api.model.UrStep;
import th.cu.thesis.fur.api.model.UrStepApprove;



public class UserRequestSqlProvider {
	
	static final String STATUS_PROCESS = "1";
	static final String APPROVE_TYPE_ALL = "1";
	static final String APPROVE_TYPE_MINIMUM = "2";
	static final String APPROVE_TYPE_SEQUENCE = "3";
	static final String APPROVE_TYPE_SEQUENCE_TEAM = "4";
	static final String APPROVE_TYPE_PARALLEL = "5";
	static final String STATUS_ALL = "0";
	
	public String getCountURApproved(Map<String,Object> params){
		
		Date startDate = (Date) params.get("startDate");
		Date endDate = (Date) params.get("endDate");
		
		String sql = "SELECT COUNT(*) FROM [UR] UR "
				+ "JOIN REQUEST_NO REQ ON UR.REQUEST_NO = REQ.REQUEST_NO "
				+ "JOIN [USER] USR ON USR.PINCODE = REQ.REQUEST_BY "
				+ "WHERE UR_ID IN ( SELECT UR_ID FROM UR_APPROVE_HISTORY WHERE USERNAME = #{username} ) "
				+ "AND UR.UR_ID LIKE #{urId} "
				+ "AND UR.REQUEST_NO LIKE #{requestNo} ";
		
		if(startDate != null ){
			sql += "AND REQ.REQUEST_DATE >= #{startDate} ";
		}
		
		if(endDate != null ){
			sql += "AND REQ.REQUEST_DATE <= #{endDate} ";
		}
		
		return sql;
	};
	
	public String getURApproved(Map<String,Object> params){
		
		Date startDate = (Date) params.get("startDate");
		Date endDate = (Date) params.get("endDate");
		String orderBy = (String) params.get("orderBy");
		
		String sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY "+ orderBy +" , UR.UR_ID ASC) AS RNUM,"
				+ "UR.REQUEST_NO,UR.UR_ID,UR.APP_NAME,UR.APP_ROLE_NAME,"
				+ "REQ.REQUEST_DATE,USR.ENFULLNAME AS REQUEST_BY,UR.STATUS "
				+ "FROM [UR] UR "
				+ "JOIN REQUEST_NO REQ ON UR.REQUEST_NO = REQ.REQUEST_NO "
				+ "JOIN [USER] USR ON USR.PINCODE = REQ.REQUEST_BY "
				+ "WHERE UR_ID IN ( SELECT UR_ID FROM UR_APPROVE_HISTORY WHERE USERNAME = #{username} ) "
				+ "AND UR.UR_ID LIKE #{urId} "
				+ "AND UR.REQUEST_NO LIKE #{requestNo} ";
		
		if(startDate != null ){
			sql += "AND REQ.REQUEST_DATE >= #{startDate} ";
		}
		
		if(endDate != null ){
			sql += "AND REQ.REQUEST_DATE <= #{endDate} ";
		}
		
		sql += ") RESULT WHERE RESULT.RNUM BETWEEN #{startRow} AND #{endRow}";
		
		return sql;
	};
	
	public String checkUrStepCanApproveByUsernameAndApproveType(Map<String,Object> params){
		
		String approveType = (String) params.get("approveType");
		
		String sql ="SELECT * FROM UR_STEP WHERE "
				+ "UR_STEP_ID IN (SELECT UR_STEP_ID FROM UR_STEP_APPROVE WHERE USERNAME = #{username} "
				+ "AND UR_STEP_ID IN (SELECT UR_STEP_ID FROM UR_STEP WHERE UR_ID = #{urId} AND STATUS = #{status})) "
				+ "AND UR_STEP_ID NOT IN (SELECT UR_STEP_ID FROM UR_APPROVE_HISTORY WHERE USERNAME = #{username}) ";
		
		if(APPROVE_TYPE_SEQUENCE_TEAM.equals(approveType)){
			   sql 	+= "AND TEAM_NAME NOT IN (SELECT TEAM_NAME FROM UR_APPROVE_HISTORY WHERE UR_STEP_ID IN "
					+ "(SELECT UR_STEP_ID FROM UR_STEP WHERE UR_ID = #{urId} AND STATUS = #{status})"
					+ ") AND USERNAME = #{username})";
		}
		return sql;
	}
	
	public String getCountURByUrStepIdList(Map<String,Object> params){
		String urStepIdList = (String) params.get("urStepIdList");
		String sql = "SELECT COUNT(*) "
				+ "FROM UR UR "
				+ "JOIN REQUEST_NO REQ ON REQ.REQUEST_NO = UR.REQUEST_NO "
				+ "JOIN [USER] USR ON USR.PINCODE = REQ.REQUEST_BY "
				+ "JOIN UR_STEP URSTEP ON URSTEP.UR_ID = UR.UR_ID "
				+ "WHERE URSTEP.UR_STEP_ID IN ("+ urStepIdList +")";
		
		return sql;
	}
	
	
	public String getURByUrStepIdList(Map<String,Object> params){
		String urStepIdList = (String) params.get("urStepIdList");
		String orderBy = (String) params.get("orderBy");
		
		
		String sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY "+ orderBy +" , UR.UR_ID ASC) AS ROWNUMBER"
				+ ",UR.APP_NAME,UR.UR_ID,UR.REQUEST_NO,REQ.SUBJECT"
				+ ",USR.USERNAME AS REQUEST_BY"
				+ ",REQ.REQUEST_DATE"
				+ ",URSTEP.UR_STEP "
				+ ",UR.FLOW_ID "
				+ ",UR.APP_ROLE_NAME "
				+ ",UR.APP_ID "
				+ "FROM UR UR "
				+ "JOIN REQUEST_NO REQ ON REQ.REQUEST_NO = UR.REQUEST_NO "
				+ "JOIN [USER] USR ON USR.PINCODE = REQ.REQUEST_BY "
				+ "JOIN UR_STEP URSTEP ON URSTEP.UR_ID = UR.UR_ID "
				+ "WHERE URSTEP.UR_STEP_ID IN ("+ urStepIdList +") ) RESULT "
				+ "WHERE RESULT.ROWNUMBER BETWEEN #{startRow} AND #{endRow}";
		return sql;
	}
	
	public String checkCountUrStepApprove(Map<String,Object> params){
		
		String approveType = (String) params.get("approveType");
		String sql = "";
		
		if(APPROVE_TYPE_ALL.equals(approveType)){
			sql = "SELECT COUNT(*) FROM UR_STEP_APPROVE WHERE UR_STEP_ID = #{urStepId}";
		}
		
		if(APPROVE_TYPE_MINIMUM.equals(approveType)){
			sql = "SELECT MINIMUM_APPROVE FROM UR_STEP WHERE UR_STEP_ID = #{urStepId}";
		}
		
		if(APPROVE_TYPE_SEQUENCE.equals(approveType)){
			sql = "SELECT COUNT(*) FROM UR_STEP_APPROVE WHERE UR_STEP_ID = #{urStepId}";
		}
		
		if(APPROVE_TYPE_SEQUENCE_TEAM.equals(approveType) || APPROVE_TYPE_PARALLEL.equals(approveType)){
			sql = "SELECT COUNT(DISTINCT(SEQUENCE_TEAM)) FROM UR_STEP_APPROVE WHERE UR_STEP_ID = #{urStepId}";
		}

		return sql;
	}
	
	public String getURStepIdProcess(Map<String, Object> params){
		
		Date startDate = (Date) params.get("startDate");
		Date endDate = (Date) params.get("endDate");
		String urStepType = (String) params.get("urStepType");
		
		String sql = "SELECT URSTEP.UR_STEP_ID,APPROVE_TYPE FROM UR UR "
				+ "JOIN REQUEST_NO REQNO ON UR.REQUEST_NO = REQNO.REQUEST_NO "
				+ "JOIN UR_STEP URSTEP ON URSTEP.UR_ID = UR.UR_ID "
				+ "AND URSTEP.STATUS = '" +STATUS_PROCESS+ "'"
				+ "AND UR.UR_ID LIKE #{urId} "
				+ "AND UR.REQUEST_NO LIKE #{requestNo} ";
				
		if(startDate != null ){
			sql += "AND REQNO.REQUEST_DATE >= #{startDate} ";
		}
		
		if(endDate != null ){
			sql += "AND REQNO.REQUEST_DATE <= #{endDate} ";
		}
		
		if(!STATUS_ALL.equals(urStepType)){
			sql += "AND URSTEP.UR_STEP = #{urStepType} ";
		}
		
		return sql;
	}
	
	public String getCountUrApproveByUsernameAndType(Map<String, Object> params){
		String approveType = (String) params.get("approveType");
		String sql = "SELECT COUNT(*) FROM UR_STEP_APPROVE WHERE "
				+ "UR_STEP_ID = #{urStepId} "
				+ "AND USERNAME = #{username} ";

		if(APPROVE_TYPE_ALL.equals(approveType) || APPROVE_TYPE_MINIMUM.equals(approveType)){
			sql += "AND UR_STEP_ID NOT IN "
					+ "(SELECT UR_STEP_ID FROM UR_APPROVE_HISTORY WHERE USERNAME = #{username})";
		}
		
		else if(APPROVE_TYPE_SEQUENCE.equals(approveType)){
			sql += "AND UR_STEP_ID NOT IN "
					+ "(SELECT UR_STEP_ID FROM UR_APPROVE_HISTORY WHERE USERNAME = #{username} ) "
					+ "AND [SEQUENCE] = (SELECT COUNT(*)+1 FROM UR_APPROVE_HISTORY "
					+ "WHERE UR_STEP_ID = #{urStepId} )";
		}
		else if(APPROVE_TYPE_SEQUENCE_TEAM.equals(approveType)){
			sql += "AND UR_STEP_ID NOT IN "
					+ "(SELECT UR_STEP_ID FROM UR_APPROVE_HISTORY WHERE USERNAME = #{username} ) "
					+ "AND [SEQUENCE_TEAM] = (SELECT COUNT(*)+1 FROM UR_APPROVE_HISTORY "
					+ "WHERE UR_STEP_ID = #{urStepId} )";
		}
		else if(APPROVE_TYPE_PARALLEL.equals(approveType)){
			sql += "AND UPPER(TEAM_NAME) NOT IN "
					+ "(SELECT UPPER(TEAM_NAME) FROM UR_APPROVE_HISTORY "
					+ "WHERE UR_STEP_ID = #{urStepId})";
		}
		return sql;
	}
	
	public String validbothAleadyQueued(Map<String, Object> params){
		StringBuilder sqlBuilder = new StringBuilder();
		try{
			String userId = (String) params.get("userId");
			String appRoleId = (String) params.get("appRoleId");
			sqlBuilder.append("SELECT OUTPUT.USER_ID ");
			sqlBuilder.append("		,OUTPUT.USERNAME ");
			sqlBuilder.append("		,OUTPUT.APP_ROLE_ID ");
			sqlBuilder.append("		,OUTPUT.APP_NAME ");
			sqlBuilder.append("		,OUTPUT.APP_ROLE_NAME ");
			sqlBuilder.append("		,OUTPUT.QUEUED ");
			sqlBuilder.append("		,OUTPUT.ALREADY ");
			sqlBuilder.append("		,OUTPUT.APP_ID ");
			sqlBuilder.append("FROM ( ");
			sqlBuilder.append("				SELECT *  ");
			sqlBuilder.append("				FROM ( ");
			sqlBuilder.append("							SELECT '"+userId+"' USER_ID ");
			sqlBuilder.append("						, (SELECT USERNAME FROM [USER] WHERE [USER].USER_ID = #{userId}) USERNAME ");
			sqlBuilder.append("						, '"+appRoleId+"' APPROLEID  ");
			sqlBuilder.append("						, (CASE WHEN EXISTS (SELECT * ");
			sqlBuilder.append("							FROM  UR_FOR_USER UFU  ");
			sqlBuilder.append("							INNER JOIN  UR ON UFU.UR_ID = UR.UR_ID  ");
			sqlBuilder.append("							INNER JOIN  [USER] USR ON USR.PINCODE = UFU.PINCODE  ");
			sqlBuilder.append("							WHERE USR.USER_ID = #{userId}  AND UR.APP_ROLE_ID= #{appRoleId} AND UFU.UR_STATUS=1) THEN 'Y' ELSE 'N' END ) QUEUED ");
			sqlBuilder.append("						, (CASE WHEN EXISTS (SELECT * ");
			sqlBuilder.append("							FROM  USER_APP_ROLE UAR  ");
			sqlBuilder.append("							INNER JOIN  APP_ROLE AR ON UAR.APP_ROLE_ID=AR.APP_ROLE_ID ");
			sqlBuilder.append("							INNER JOIN  APP AP ON AR.APP_ID = AP.APP_ID  ");
			sqlBuilder.append("							INNER JOIN  [USER] USR ON USR.USER_ID = UAR.USER_ID ");
			sqlBuilder.append("							WHERE USR.USER_ID = #{userId}  AND UAR.APP_ROLE_ID = #{appRoleId} ) THEN 'Y' ELSE 'N' END ) ALREADY  ");
			sqlBuilder.append("				) DATAS ");
			sqlBuilder.append("				INNER JOIN (SELECT APP_ROLE.APP_ID,APP_ROLE.APP_ROLE_ID,APP.APP_NAME,APP_ROLE.APP_ROLE_NAME  ");
			sqlBuilder.append("								FROM [APP] INNER JOIN [APP_ROLE] ON APP.APP_ID = APP_ROLE.APP_ID AND APP_ROLE.APP_ROLE_ID = #{appRoleId} ");
			sqlBuilder.append("								WHERE STATUS = '1' ) APPNAME ON APPNAME.APP_ROLE_ID = DATAS.APPROLEID ) OUTPUT ");
			
		}catch(NullPointerException e){
			sqlBuilder.append(" SELECT '' USER_ID,'' APP_ROLE_ID,'N' QUEUED,'N' ALREADY ");
			e.printStackTrace();
		}

		return sqlBuilder.toString();
	} 
	public String validAlreadyRoleApp(Map<String, Object> params){
		String appRoleId = (String) params.get("appRoleId");
		String userId = (String) params.get("userId");
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT USR.USER_ID,USR.USERNAME,AR.APP_ROLE_NAME,AP.APP_NAME,UAR.APP_ROLE_ID  ");
		sqlBuilder.append("FROM  USER_APP_ROLE UAR ");
		sqlBuilder.append("INNER JOIN  APP_ROLE AR ON UAR.APP_ROLE_ID=AR.APP_ROLE_ID ");
		sqlBuilder.append("INNER JOIN  APP AP ON AR.APP_ID = AP.APP_ID ");
		sqlBuilder.append("INNER JOIN  [USER] USR ON USR.USER_ID = UAR.USER_ID ");
		sqlBuilder.append("WHERE USR.USER_ID = #{userId} AND UAR.APP_ROLE_ID = #{appRoleId} ");
		return sqlBuilder.toString();
	}
	public String validURisQueued(Map<String, Object> params){
		String appRoleId = (String) params.get("appRoleId");
		String userId = (String) params.get("userId");
		StringBuilder sqlBuilder = new StringBuilder() ;
		sqlBuilder.append("SELECT USR.USER_ID,UFU.USERNAME,UR.APP_NAME,UR.APP_ROLE_NAME,UR.APP_ROLE_ID ");
		sqlBuilder.append("FROM  UR_FOR_USER UFU ");
		sqlBuilder.append("INNER JOIN  UR ON UFU.UR_ID = UR.UR_ID ");
		sqlBuilder.append("INNER JOIN  [USER] USR ON USR.PINCODE = UFU.PINCODE ");
		sqlBuilder.append("WHERE USR.USER_ID = #{userId} AND UR.APP_ROLE_ID=#{appRoleId} AND UFU.UR_STATUS=1 ");
		return sqlBuilder.toString();
	}
//	public String validbothAlreadyQueued(Map<String, Object> params){
//		String appRoleId = (String) params.get("appRoleId");
//		String userId = (String) params.get("userId");
//		StringBuilder sqlBuilder = new StringBuilder() ;
//		sqlBuilder.append("SELECT  ");
//		sqlBuilder.append("		USERNAME ");
//		sqlBuilder.append("		,APP_ROLE_NAME ");
//		sqlBuilder.append("		,APP_NAME ");
//		sqlBuilder.append("		,USERNAME_ALRDY ");
//		sqlBuilder.append("		,APP_NAME_ALRDY ");
//		sqlBuilder.append("		,APP_ROLE_NAME_ALRDY ");
//		sqlBuilder.append("FROM(	SELECT USR.USERNAME,AR.APP_ROLE_NAME,AP.APP_NAME ");
//		sqlBuilder.append("		FROM  USER_APP_ROLE UAR ");
//		sqlBuilder.append("		INNER JOIN  APP_ROLE AR ON UAR.APP_ROLE_ID=AR.APP_ROLE_ID ");
//		sqlBuilder.append("		INNER JOIN  APP AP ON AR.APP_ID = AP.APP_ID ");
//		sqlBuilder.append("		INNER JOIN  [USER] USR ON USR.USER_ID=UAR.USER_ID  ");
//		sqlBuilder.append("		WHERE CONVERT(INTEGER, USR.USER_ID) = #{userId} AND UAR.APP_ROLE_ID = #{appRoleId} ) Already ");
//		sqlBuilder.append("INNER JOIN ( ");
//		sqlBuilder.append("		SELECT USR.USERNAME USERNAME_ALRDY,UR.APP_NAME APP_NAME_ALRDY,UR.APP_ROLE_NAME APP_ROLE_NAME_ALRDY ");
//		sqlBuilder.append("		FROM  UR_FOR_USER UFU ");
//		sqlBuilder.append("		INNER JOIN  UR ON UFU.UR_ID = UR.UR_ID   ");
//		sqlBuilder.append("		INNER JOIN  [USER] USR ON USR.USER_ID=UFU.USER_ID ");
//		sqlBuilder.append("		WHERE CONVERT(INTEGER, USR.USER_ID) = #{userId} AND UR.APP_ROLE_ID= #{appRoleId} AND UR.STATUS=1  ");
//		sqlBuilder.append("		) Queued ON Already.USERNAME = Queued.USERNAME_ALRDY ");
//		return sqlBuilder.toString();
//	
//	}
	
	public String getPincodeByUserIdorUsername (Map<String, Object> params){
		StringBuilder sqlBuilder = new StringBuilder() ;
		String userId = (String)params.get("userId") ;
		String username = (String) params.get("username");
		sqlBuilder.append("SELECT PINCODE ");
		sqlBuilder.append("FROM  [USER] ");
		sqlBuilder.append("WHERE 1=1 ");
		if(userId!=null){
			sqlBuilder.append("AND [USER].USER_ID = "+userId+" ");
		}
		if(StringUtils.isNotEmpty(username)){
			sqlBuilder.append("AND [USER].USERNAME = #{username} ");
		}
		return sqlBuilder.toString();
	}
	
	public String getOrgCodeByUserId (Map<String, Object> params){
		String userId = (String)params.get("userId") ;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT ORGCODE ");
		stringBuilder.append("FROM  [USER] ");
		stringBuilder.append("WHERE ");
		stringBuilder.append("USER_ID = #{userId}");
		return stringBuilder.toString();
	}
	
	public String getOrgtypeOrUserTypeByOrgcode (Map<String, Object> params){
		String orgCode = (String) params.get("orgCode");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT ORG_TYPE as 'UserType' ");
		stringBuilder.append("FROM ORGANIZE ");
		stringBuilder.append("WHERE ORGCODE = #{orgCode} ");
		return stringBuilder.toString();
	}
	
	public String getTypeStandOrSpecialApp (Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		String orgCode = (String) params.get("orgCode");
		String appRoleId = (String) params.get("appRoleId");
		stringBuilder.append("SELECT 'TypeStandOrSpecial' = CASE WHEN result.RESULTDATA >=1 THEN 0 ");
		stringBuilder.append("									 WHEN result.RESULTDATA = 0 THEN 1 ");
		stringBuilder.append("									 END ");
		stringBuilder.append("FROM(SELECT COUNT(*) as RESULTDATA ");
		stringBuilder.append("		FROM ELIGIBLE ");
		stringBuilder.append("		WHERE ORGCODE = #{orgCode} ");
		stringBuilder.append("		AND APP_ROLE_ID = #{appRoleId}  ) result ");
		return stringBuilder.toString();
		
	}
	public String getListRoleIdenByUsertype(Map<String, Object> params){
		String userType = (String) params.get("userType");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * ");
		stringBuilder.append("FROM [USER] USR ");
		stringBuilder.append("INNER JOIN ( SELECT ROLE_IDEN_ID,USER_ID,USER_TYPE ");
		stringBuilder.append("FROM ROLE_IDEN ");
		stringBuilder.append("WHERE USER_TYPE = #{userType} ) ROL ");
		stringBuilder.append("ON  USR.USER_ID = ROL.USER_ID ");
		return stringBuilder.toString();
	}
	
	public String getListRoleIdenBytypeZero(Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * ");
		stringBuilder.append("FROM [USER] USR ");
		stringBuilder.append("INNER JOIN ( SELECT ROLE_IDEN_ID,USER_ID,USER_TYPE ");
		stringBuilder.append("FROM ROLE_IDEN ");
		stringBuilder.append("WHERE USER_TYPE = 0 ) ROL ");
		stringBuilder.append("ON  USR.USER_ID = ROL.USER_ID ");
		return stringBuilder.toString();
	}
	
	public String getTypeApproverforAppOwner(Map<String, Object> params){
		String appRoleId = (String) params.get("appRoleId");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT APPROVE_TYPE ");
		stringBuilder.append("FROM APPROVE_TYPE ");
		stringBuilder.append("WHERE APP_ID = (SELECT APP_ID ");
		stringBuilder.append("						FROM APP_ROLE ");
		stringBuilder.append("						WHERE APP_ROLE_ID =  #{appRoleId}) AND TYPE = 1 ");
		return stringBuilder.toString();
	}
	
	public String getListAppOwnerApprover(Map<String, Object> params){
		String appRoleId = (String) params.get("appRoleId");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * ");
		stringBuilder.append("FROM [USER] USR ");
		stringBuilder.append("INNER JOIN (SELECT USER_ID,SEQUENCE_USER ");
		stringBuilder.append("				FROM APP_OWNER ");
		stringBuilder.append("				WHERE APP_ID = (SELECT APP_ID ");
		stringBuilder.append("										FROM APP_ROLE  ");
		stringBuilder.append("										WHERE APP_ROLE_ID =  #{appRoleId}) ) AOS ");
		stringBuilder.append("				ON USR.USER_ID = AOS.USER_ID ");
		stringBuilder.append("ORDER BY AOS.SEQUENCE_USER ASC,USR.USER_ID ASC ");
		return stringBuilder.toString();
	}
	
	public String getListAppOwnerTeamApprover(Map<String, Object> params){
		String appRoleId = (String) params.get("appRoleId");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * ");
		stringBuilder.append("FROM [USER] USR ");
		stringBuilder.append("INNER JOIN (SELECT USER_ID,SEQUENCE_TEAM,AOT.TEAM_NAME ");
		stringBuilder.append("							FROM APP_OWNER_MEMBER ");
		stringBuilder.append("							INNER JOIN (SELECT APP_OWNER_TEAM_ID,SEQUENCE_TEAM,TEAM_NAME  ");
		stringBuilder.append("							FROM APP_OWNER_TEAM  ");
		stringBuilder.append("							WHERE APP_ID = (SELECT APP_ID FROM APP_ROLE WHERE APP_ROLE_ID =  #{appRoleId}) ");
		stringBuilder.append("							) AOT  ");
		stringBuilder.append("							ON AOT.APP_OWNER_TEAM_ID = APP_OWNER_MEMBER.APP_OWNER_TEAM_ID ");
		stringBuilder.append("				) LISTAOT ON USR.USER_ID = LISTAOT.USER_ID ");
		stringBuilder.append("ORDER BY LISTAOT.SEQUENCE_TEAM ASC,USR.USER_ID ASC ");
		return stringBuilder.toString();
	}
	
	public String getTypeApproverforCustodian(Map<String, Object> params){
		String appRoleId = (String) params.get("appRoleId");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT APPROVE_TYPE ");
		stringBuilder.append("FROM APPROVE_TYPE ");
		stringBuilder.append("WHERE APP_ID = (SELECT APP_ID ");
		stringBuilder.append("						FROM APP_ROLE ");
		stringBuilder.append("						WHERE APP_ROLE_ID =  #{appRoleId}) AND TYPE = 2 ");
		return stringBuilder.toString();
	}
	
	public String getTypeApproverAndMinimumByappRoleIdandType(Map<String, Object> params){
		String appRoleId = (String) params.get("appRoleId");
		String type = (String)params.get("type");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT APPROVE_TYPE ,MINIMUM_APPROVE ");
		stringBuilder.append("FROM APPROVE_TYPE ");
		stringBuilder.append("WHERE APP_ID = (SELECT APP_ID ");
		stringBuilder.append("						FROM APP_ROLE ");
		stringBuilder.append("						WHERE APP_ROLE_ID =  #{appRoleId}) AND TYPE = #{type} ");
		return stringBuilder.toString();
	}
	
	public String getListCustodianByOrgtype(Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * ");
		stringBuilder.append("FROM [USER] USR ");
		stringBuilder.append("INNER JOIN (SELECT USER_ID,SEQUENCE_USER ");
		stringBuilder.append("				FROM CUSTODIAN ");
		stringBuilder.append("				WHERE USER_TYPE = #{orgType}");
		stringBuilder.append("				AND APP_ID = (SELECT APP_ID FROM APP_ROLE WHERE APP_ROLE_ID =  #{appRoleId}) ) CUS ");
		stringBuilder.append("				ON USR.USER_ID = CUS.USER_ID  ");
		stringBuilder.append("ORDER BY CUS.SEQUENCE_USER ASC,USR.USER_ID ASC ");
		return stringBuilder.toString();
	}
	
	public String getListCustodianByOrgtypeZero(Map<String, Object> params){
		String appRoleId = (String) params.get("appRoleId");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * ");
		stringBuilder.append("FROM [USER] USR ");
		stringBuilder.append("INNER JOIN (SELECT USER_ID,SEQUENCE_USER ");
		stringBuilder.append("				FROM CUSTODIAN ");
		stringBuilder.append("				WHERE APP_ID = (SELECT APP_ID ");
		stringBuilder.append("										FROM APP_ROLE  ");
		stringBuilder.append("										WHERE APP_ROLE_ID =  #{appRoleId}) AND USER_TYPE = 0 ) CUS ");
		stringBuilder.append("				ON USR.USER_ID = CUS.USER_ID ");
		stringBuilder.append("ORDER BY CUS.SEQUENCE_USER ASC,USR.USER_ID ASC ");
		return stringBuilder.toString();
	}
	
	public String getListCustodianTeamByOrgtype(Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * ");
		stringBuilder.append("FROM [USER] USR ");
		stringBuilder.append("INNER JOIN (SELECT USER_ID,SEQUENCE_TEAM,CUT.TEAM_NAME ");
		stringBuilder.append("							FROM CUSTODIAN_MEMBER ");
		stringBuilder.append("							INNER JOIN (SELECT CUSTODIAN_TEAM_ID,SEQUENCE_TEAM,TEAM_NAME  ");
		stringBuilder.append("							FROM CUSTODIAN_TEAM  ");
		stringBuilder.append("							WHERE USER_TYPE = #{orgType}  ");
		stringBuilder.append("							AND  APP_ID = (SELECT APP_ID FROM APP_ROLE WHERE APP_ROLE_ID =  #{appRoleId}) ");
		stringBuilder.append("							) CUT ON CUT.CUSTODIAN_TEAM_ID = CUSTODIAN_MEMBER.CUSTODIAN_TEAM_ID ");
		stringBuilder.append("				) LISTCUT ON USR.USER_ID = LISTCUT.USER_ID ");
		stringBuilder.append("ORDER BY LISTCUT.SEQUENCE_TEAM ASC,USR.USER_ID ASC ");
		return stringBuilder.toString();
	}
	
	public String getListCustodianTeamByOrgtypeZero(Map<String, Object> params){
		String appRoleId = (String) params.get("appRoleId");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * ");
		stringBuilder.append("FROM [USER] USR ");
		stringBuilder.append("INNER JOIN (SELECT USER_ID,SEQUENCE_TEAM,TEAM_NAME ");
		stringBuilder.append("							FROM CUSTODIAN_MEMBER ");
		stringBuilder.append("							INNER JOIN (SELECT CUSTODIAN_TEAM_ID,SEQUENCE_TEAM,TEAM_NAME  ");
		stringBuilder.append("							FROM CUSTODIAN_TEAM  ");
		stringBuilder.append("							WHERE USER_TYPE =0 AND APP_ID = (SELECT APP_ID FROM APP_ROLE WHERE APP_ROLE_ID = #{appRoleId}) ");
		stringBuilder.append("							) CUT ON CUT.CUSTODIAN_TEAM_ID = CUSTODIAN_MEMBER.CUSTODIAN_TEAM_ID ");
		stringBuilder.append("				) LISTCUT ON USR.USER_ID = LISTCUT.USER_ID ");
		stringBuilder.append("ORDER BY LISTCUT.SEQUENCE_TEAM ASC,USR.USER_ID ASC ");
		return stringBuilder.toString();
	}
	
	public String getListNodeFlowConfig(Map<String, Object> params){
		String flowId = (String) params.get("flowId");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT FLOW_NODE_CODE,SEQUENCE ");
		stringBuilder.append("FROM FLOW_CONFIG ");
		stringBuilder.append("WHERE FLOW_ID = #{flowId} ");
		stringBuilder.append("ORDER BY SEQUENCE ASC ");
		return stringBuilder.toString();
	}
	
	public String getUserByUserId(Map<String, Object> params){
		String userId = (String) params.get("userId");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * ");
		stringBuilder.append("FROM [USER] ");
		stringBuilder.append("WHERE  USER_ID = #{userId} ");
		return stringBuilder.toString();
	}
	
	public String getUserByUsername(Map<String, Object> params){
		String userName = (String) params.get("userName");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT [USER].*,ENNAME+ENSURNAME name ");
		stringBuilder.append("FROM [USER] ");
		stringBuilder.append("WHERE USERNAME = #{userName} ");
		return stringBuilder.toString();
	}
	public String getUserByUsername2(Map<String, Object> params){
		String userName = (String) params.get("userName");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT [USER].*,(ENNAME+' '+ENSURNAME) name ");
		stringBuilder.append("FROM [USER] ");
		stringBuilder.append("WHERE USERNAME = #{userName} ");
		return stringBuilder.toString();
	}
	public String getDetailAppByApprole(Map<String, Object> params){
		String appRoleId = (String) params.get("appRoleId");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT APP.APP_ID,APP.APP_NAME,APP_ROLE.APP_ROLE_ID,APP_ROLE.APP_ROLE_NAME ,APP.APP_FILE,'Incomplete' STATUS ");
		stringBuilder.append("FROM APP ");
		stringBuilder.append("INNER JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID ");
		stringBuilder.append("WHERE APP_ROLE.APP_ROLE_ID = #{appRoleId} ");
		return stringBuilder.toString();
		
	}
	
	public String insertRequestNo (Map<String, Object> params){
		RequestNo requestNo = (RequestNo)params.get("requestNo");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("INSERT INTO [REQUEST_NO] ");
		stringBuilder.append("           ([REQUEST_NO] ");
		stringBuilder.append("           ,[UR_TYPE] ");
		stringBuilder.append("           ,[REQUEST_LIST] ");
		stringBuilder.append("           ,[REQUEST_TYPE] ");
		stringBuilder.append("           ,[CHANGE_TYPE] ");
		stringBuilder.append("           ,[SUBJECT] ");
		stringBuilder.append("           ,[DETAIL] ");
		stringBuilder.append("           ,[LOCATION] ");
		stringBuilder.append("           ,[REQUEST_BY] ");
		stringBuilder.append("           ,[REQUEST_DATE] ");
		stringBuilder.append("           ,[USERNAME] ");
		stringBuilder.append("           ,[ENNAME] ");
		stringBuilder.append("           ,[ENSURNAME] ");
		stringBuilder.append("           ,[EMAIL] ");
		stringBuilder.append("           ,[MOBILE] ");
		stringBuilder.append("           ,[PHONE] ");
		stringBuilder.append("           ,[POSITION] ");
		stringBuilder.append("           ,[ORGANIZE] ");
		stringBuilder.append("           ,[COMPANY] ");
		stringBuilder.append("           ,[REQUEST_REMARK] ");
		stringBuilder.append("           ,[GROUP_APP_ROLE_ID] ");
		stringBuilder.append("           ,[GROUP_APP_NAME] ");
		stringBuilder.append("           ,[CREATED_DATE] ");
		stringBuilder.append("          ,[CREATED_BY] ");
		stringBuilder.append("           ,[UPDATED_DATE] ");
		stringBuilder.append("           ,[UPDATED_BY]) ");
		stringBuilder.append("     VALUES ");
		stringBuilder.append("           (#{requestNo.requestNo, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.urType, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.requestList, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.requestType, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.changeType, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.subject, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.detail, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.location, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.requestBy, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,CURRENT_TIMESTAMP ");
		stringBuilder.append("           ,#{requestNo.username, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.enname, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.ensurname, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.email, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.mobile, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.phone, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.position, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.organize, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.company, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.requestRemark, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.groupAppRoleId, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{requestNo.groupAppName, jdbcType=VARCHAR} ");
		stringBuilder.append("            ,CURRENT_TIMESTAMP ");
		stringBuilder.append("           ,#{requestNo.createdBy, jdbcType=VARCHAR} ");
		stringBuilder.append("           ,CURRENT_TIMESTAMP ");
		stringBuilder.append("           ,#{requestNo.updatedBy, jdbcType=VARCHAR} ) ");
		stringBuilder.append(" ");
		return stringBuilder.toString();
	}
	
	public String insertUr (Map<String, Object> params){
		UR ur = (UR)params.get("ur");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("INSERT INTO [UR] ");
		stringBuilder.append("           ([UR_ID] ");
		stringBuilder.append("           ,[REQUEST_NO] ");
		stringBuilder.append("           ,[FLOW_ID] ");
		stringBuilder.append("           ,[APP_ID] ");
		stringBuilder.append("           ,[APP_NAME] ");
		stringBuilder.append("           ,[APP_ROLE_ID] ");
		stringBuilder.append("           ,[APP_ROLE_NAME] ");
		stringBuilder.append("           ,[REQUEST_TYPE] ");
		stringBuilder.append("           ,[PERIOD_TYPE] ");
		stringBuilder.append("           ,[END_TIME] ");
		stringBuilder.append("           ,[START_TIME] ");
		stringBuilder.append("           ,[ROLE_PAST_ID] ");
		stringBuilder.append("           ,[ROLE_PAST] ");
		stringBuilder.append("           ,[STATUS] ");
		stringBuilder.append("           ,[REMARK] ");
		stringBuilder.append("           ,[UR_FILE] ");
		stringBuilder.append("           ,[CREATED_DATE] ");
		stringBuilder.append("           ,[CREATED_BY] ");
		stringBuilder.append("           ,[UPDATED_DATE] ");
		stringBuilder.append("           ,[UPDATED_BY]) ");
		stringBuilder.append("     VALUES ");
		stringBuilder.append("           (#{ur.urId , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.requestNo , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.flowId , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.appId , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.appName , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.appRoleId , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.appRoleName , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.requestType , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.periodType , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.endTime, jdbcType=TIMESTAMP} ");
		stringBuilder.append("           ,#{ur.startTime, jdbcType=TIMESTAMP} ");
		stringBuilder.append("           ,#{ur.rolePastId , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.rolePast , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.status , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.remark , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{ur.urFile , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,CURRENT_TIMESTAMP ");
		stringBuilder.append("           ,#{ur.createdBy , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,CURRENT_TIMESTAMP ");
		stringBuilder.append("           ,#{ur.updatedBy , jdbcType=VARCHAR}) ");
		return stringBuilder.toString();
	}
	
	public String insertUrforUser (Map<String, Object> params){
		UrForUser urforuser = (UrForUser)params.get("urforuser");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("INSERT INTO [UR_FOR_USER] ");
		stringBuilder.append("           ([UR_FOR_USER_ID] ");
		stringBuilder.append("           ,[UR_ID] ");
		stringBuilder.append("           ,[PINCODE] ");
		stringBuilder.append("           ,[ENNAME] ");
		stringBuilder.append("           ,[ENSURNAME] ");
		stringBuilder.append("           ,[USERNAME] ");
		stringBuilder.append("           ,[EMAIL] ");
		stringBuilder.append("           ,[PHONE] ");
		stringBuilder.append("           ,[MOBILE] ");
		stringBuilder.append("           ,[TOKEN_SERIAL_NUMBER] ");
		stringBuilder.append("           ,[UR_STATUS] ");
		stringBuilder.append("           ,[UR_STEP] ");
		stringBuilder.append("           ,[CREATED_DATE] ");
		stringBuilder.append("           ,[CREATED_BY] ");
		stringBuilder.append("           ,[UPDATED_DATE] ");
		stringBuilder.append("           ,[UPDATED_BY] ");
		stringBuilder.append("           ,[POSITION] ");
		stringBuilder.append("           ,[ENFULLNAME] ");
		stringBuilder.append("           ,[THNAME] ");
		stringBuilder.append("           ,[THSURNAME] ");
		stringBuilder.append("           ,[THFULLNAME] ");
		stringBuilder.append("           ,[ORGCODE] ");
		stringBuilder.append("           ,[ORGNAME] ");
		stringBuilder.append("           ,[ORGDESC] ");
		stringBuilder.append("           ,[ORGLEVEL] ");
		stringBuilder.append("           ,[FCCODE] ");
		stringBuilder.append("           ,[FCNAME] ");
		stringBuilder.append("           ,[FCDESC] ");
		stringBuilder.append("           ,[FCHCODE] ");
		stringBuilder.append("           ,[FCHNAME] ");
		stringBuilder.append("           ,[FCHDESC] ");
		stringBuilder.append("           ,[SCCODE] ");
		stringBuilder.append("           ,[SCNAME] ");
		stringBuilder.append("           ,[SCDESC] ");
		stringBuilder.append("           ,[SCHCODE] ");
		stringBuilder.append("           ,[SCHNAME] ");
		stringBuilder.append("           ,[SCHDESC] ");
		stringBuilder.append("           ,[DPCODE] ");
		stringBuilder.append("           ,[DPNAME] ");
		stringBuilder.append("           ,[DPDESC] ");
		stringBuilder.append("           ,[DPHCODE] ");
		stringBuilder.append("           ,[DPHNAME] ");
		stringBuilder.append("           ,[DPHDESC] ");
		stringBuilder.append("           ,[BUCODE] ");
		stringBuilder.append("           ,[BUNAME] ");
		stringBuilder.append("           ,[BUDESC] ");
		stringBuilder.append("           ,[BUHCODE] ");
		stringBuilder.append("           ,[BUHNAME] ");
		stringBuilder.append("           ,[BUHDESC] ");
		stringBuilder.append("           ,[COCODE] ");
		stringBuilder.append("           ,[CONAME] ");
		stringBuilder.append("           ,[POSITION_ID] ");
		stringBuilder.append("           ,[MANAGER_PIN] ");
		stringBuilder.append("           ,[MANAGER_USERNAME] ");
		stringBuilder.append("           ,[MANAGER_EMAIL] ");
		stringBuilder.append("           ,[MANAGER_ENNAME] ");
		stringBuilder.append("           ,[MANAGER_ENSURNAME]) ");
		stringBuilder.append("     VALUES ");
		stringBuilder.append("           (#{urforuser.urForUserId , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.urId , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.pincode , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.enname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.ensurname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.username , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.email , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.phone , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.mobile , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.tokenSerialNumber , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.urStatus , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.urStep , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,CURRENT_TIMESTAMP ");
		stringBuilder.append("           ,#{urforuser.createdBy , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,CURRENT_TIMESTAMP ");
		stringBuilder.append("           ,#{urforuser.updatedBy , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.position , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.enfullname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.thname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.thsurname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.thfullname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.orgcode , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.orgname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.orgdesc , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.orglevel , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.fccode , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.fcname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.fcdesc , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.fchcode , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.fchname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.fchdesc , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.sccode , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.scname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.scdesc , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.schcode , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.schname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.schdesc , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.dpcode , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.dpname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.dpdesc , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.dphcode , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.dphname , jdbcType=VARCHAR}  ");
		stringBuilder.append("           ,#{urforuser.dphdesc , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.bucode , jdbcType=VARCHAR}  ");
		stringBuilder.append("           ,#{urforuser.buname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.budesc , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.buhcode , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.buhname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.buhdesc , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.cocode , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.coname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.positionId , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.managerPin , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.managerUsername , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.managerEmail , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.managerEnname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urforuser.managerEnsurname , jdbcType=VARCHAR}) ");
		return stringBuilder.toString();
	}
	public String insertUrStepApprove (Map<String, Object> params){
		UrStepApprove urStepApprove = (UrStepApprove)params.get("urStepApprove");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("INSERT INTO [UR_STEP_APPROVE] ");
		stringBuilder.append("           ([UR_STEP_APPROVE_ID] ");
		stringBuilder.append("           ,[UR_STEP_ID] ");
		stringBuilder.append("           ,[PINCODE] ");
		stringBuilder.append("           ,[ENNAME] ");
		stringBuilder.append("           ,[ENSURNAME] ");
		stringBuilder.append("           ,[USERNAME] ");
		stringBuilder.append("           ,[EMAIL] ");
		stringBuilder.append("           ,[PHONE] ");
		stringBuilder.append("           ,[MOBILE] ");
		stringBuilder.append("           ,[SEQUENCE] ");
		stringBuilder.append("           ,[TEAM_NAME] ");
		stringBuilder.append("           ,[SEQUENCE_TEAM] ");
		stringBuilder.append("           ,[CREATED_DATE] ");
		stringBuilder.append("           ,[CREATED_BY] ");
		stringBuilder.append("           ,[UPDATED_DATE] ");
		stringBuilder.append("           ,[UPDATED_BY]) ");
		stringBuilder.append("     VALUES ");
		stringBuilder.append("           (#{urStepApprove.urstepapproveId , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStepApprove.urStepId , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStepApprove.pincode , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStepApprove.enname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStepApprove.ensurname , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStepApprove.username , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStepApprove.email , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStepApprove.phone , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStepApprove.mobile , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStepApprove.sequence , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStepApprove.teamName , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStepApprove.teamSequence , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,CURRENT_TIMESTAMP ");
		stringBuilder.append("           ,#{urStepApprove.createdBy , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,CURRENT_TIMESTAMP ");
		stringBuilder.append("           ,#{urStepApprove.updatedBy , jdbcType=VARCHAR} ) ");
		return stringBuilder.toString();
	}	
	
	public String insertUrStep (Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		UrStep urStep = (UrStep) params.get("urStep");
		stringBuilder.append("INSERT INTO [UR_STEP] ");
		stringBuilder.append("           ([UR_STEP_ID] ");
		stringBuilder.append("           ,[UR_ID] ");
		stringBuilder.append("           ,[UR_STEP] ");
		stringBuilder.append("           ,[APPROVE_TYPE] ");
		stringBuilder.append("           ,[MINIMUM_APPROVE] ");
		stringBuilder.append("           ,[SEQUENCE] ");
		stringBuilder.append("           ,[STATUS] ");
		stringBuilder.append("           ,[CREATED_DATE] ");
		stringBuilder.append("           ,[CREATED_BY] ");
		stringBuilder.append("           ,[UPDATED_DATE] ");
		stringBuilder.append("           ,[UPDATED_BY]) ");
		stringBuilder.append("     VALUES ");
		stringBuilder.append("           (#{urStep.urStepId , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStep.urId , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStep.urStep , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStep.approveType , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStep.minimumApprove , jdbcType=VARCHAR}  ");
		stringBuilder.append("           ,#{urStep.seQuence , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,#{urStep.status , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,CURRENT_TIMESTAMP ");
		stringBuilder.append("           ,#{urStep.createdBy , jdbcType=VARCHAR} ");
		stringBuilder.append("           ,CURRENT_TIMESTAMP ");
		stringBuilder.append("           ,#{urStep.updatedBy , jdbcType=VARCHAR}) ");
		return stringBuilder.toString();
	}

	public String getDatasSaveNewByidRequestNo(Map<String, Object> params){
		String idRequestNo = (String) params.get("idRequestNo");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT ABC.* ");
		stringBuilder.append(",STUFF (( ");
		stringBuilder.append("		SELECT ','+UR_FOR_USER.USERNAME ");
		stringBuilder.append("		FROM UR_FOR_USER");
		stringBuilder.append("		WHERE UR_FOR_USER.UR_ID=ABC.UR_ID ");
		stringBuilder.append("		GROUP BY UR_FOR_USER.USERNAME FOR XML PATH ('')),1,1,'') USERLIST ");
		stringBuilder.append("FROM (SELECT UR.UR_ID ");
		stringBuilder.append("		,REQUEST_NO.REQUEST_NO ");
		stringBuilder.append("		,(REQUEST_NO.ENNAME+' '+REQUEST_NO.ENSURNAME) NAME ");
		stringBuilder.append("		,REQUEST_NO.SUBJECT ");
		stringBuilder.append("		,REQUEST_NO.DETAIL  ");
		stringBuilder.append("		,UR.APP_ID ");
		stringBuilder.append("		,UR.APP_ROLE_ID  ");
		stringBuilder.append("		,UR.APP_NAME ");
		stringBuilder.append("		,UR.APP_ROLE_NAME ");
		stringBuilder.append("		,UR.ROLE_PAST_ID ");
		stringBuilder.append("		,UR.ROLE_PAST ");
		stringBuilder.append("		,REQUEST_NO.REQUEST_TYPE ");
		stringBuilder.append("		,REQUEST_NO.CHANGE_TYPE ");
		stringBuilder.append("		,CONVERT(VARCHAR(25),REQUEST_NO.REQUEST_DATE,103)+' '+CONVERT(VARCHAR(25),REQUEST_NO.REQUEST_DATE,108) REQUEST_DATE ");
		stringBuilder.append("		,REQUEST_NO.REQUEST_REMARK ");
		stringBuilder.append("		,(CASE WHEN UR.PERIOD_TYPE =1 THEN 'Permanent' WHEN UR.PERIOD_TYPE = 2 THEN 'Temporary' END)PERIOD_TYPE ");
		stringBuilder.append("		, CONVERT(VARCHAR(25),UR.START_TIME,105) START_TIME  ");
		stringBuilder.append("		, CONVERT(VARCHAR(25),UR.END_TIME,105) END_TIME ");
		stringBuilder.append("		, CONVERT(VARCHAR(25),REQUEST_NO.CREATED_DATE,105) CREATED_DATE ");
		stringBuilder.append("		,(CASE WHEN REQUEST_NO.REQUEST_LIST = 1 THEN 'Individual' WHEN REQUEST_NO.REQUEST_LIST = 2 THEN 'Group User' END)REQUESTBY ");
		stringBuilder.append("		,(CASE WHEN REQUEST_NO.UR_TYPE = 1 THEN 'Application' WHEN REQUEST_NO.UR_TYPE = 6 THEN 'Telecom' END) UR_TYPE  ");
		stringBuilder.append("		,(CASE WHEN REQUEST_NO.REQUEST_TYPE =1 THEN 'NEW' WHEN REQUEST_NO.REQUEST_TYPE = 3 THEN 'Change' WHEN REQUEST_NO.REQUEST_TYPE = 2 THEN 'Terminate' END) REQUESTUSER ");
		stringBuilder.append("		FROM REQUEST_NO ");
		stringBuilder.append("		INNER JOIN UR ON REQUEST_NO.REQUEST_NO=UR.REQUEST_NO ");
		stringBuilder.append("		INNER JOIN UR_FOR_USER UFU ON UFU.UR_ID=UR.UR_ID ");
		stringBuilder.append("		WHERE REQUEST_NO.REQUEST_NO= #{idRequestNo} ");
		stringBuilder.append("      GROUP BY REQUEST_NO.CHANGE_TYPE,REQUEST_NO.REQUEST_TYPE,UR.ROLE_PAST,UR.ROLE_PAST_ID,REQUEST_NO.REQUEST_DATE,UR.UR_ID,REQUEST_NO.REQUEST_TYPE,REQUEST_NO.UR_TYPE,REQUEST_NO.REQUEST_LIST,REQUEST_NO.REQUEST_NO,REQUEST_NO.ENNAME,REQUEST_NO.ENSURNAME,REQUEST_NO.SUBJECT,REQUEST_NO.DETAIL,UR.APP_ID,UR.APP_ROLE_ID,UR.APP_NAME,UR.APP_ROLE_NAME,REQUEST_NO.REQUEST_REMARK,UR.PERIOD_TYPE,UR.START_TIME,UR.END_TIME,REQUEST_NO.CREATED_DATE ");
		stringBuilder.append("		) ABC ");
		stringBuilder.append("ORDER BY ABC.UR_ID ASC");
		return stringBuilder.toString();
	}
	
	public String getResultCheckGroupUserByUser (Map<String, Object> params){
		String userName = (String) params.get("userName");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT  ");
		stringBuilder.append("	'"+userName+"' USERNAME ");
		stringBuilder.append("		,(CASE WHEN NOT EXISTS (SELECT * FROM [USER] WHERE USERNAME = #{userName}) THEN 'NULL' ELSE (SELECT ENFULLNAME FROM [USER] WHERE USERNAME = #{userName}) END) CHKUSER ");
		stringBuilder.append("		,(CASE WHEN NOT EXISTS (SELECT ORGCODE FROM [USER] WHERE USERNAME = #{userName}) THEN 'NULL' ELSE (SELECT ORGDESC FROM [USER] WHERE USERNAME = #{userName}) END) CHKORGCODE ");
		stringBuilder.append("		,(CASE WHEN NOT EXISTS (SELECT ORG_TYPE FROM ORGANIZE WHERE ORGCODE = (SELECT ORGCODE FROM [USER] WHERE USERNAME = #{userName})) THEN 'NULL' ELSE (SELECT CASE WHEN ORG_TYPE=1 THEN 'Back Office' WHEN ORG_TYPE=2 THEN 'ACC' WHEN ORG_TYPE=3 THEN 'Branch' WHEN ORG_TYPE=4 THEN 'Outlet' END FROM ORGANIZE WHERE ORGCODE = (SELECT ORGCODE FROM [USER] WHERE USERNAME = #{userName})) END) CHKORGTYPE ");
		return stringBuilder.toString();
	}
	
	public String getlistAppChangeByappName (Map<String, Object> params){
		Map<String, String> parameters = (Map<String, String>) params.get("params");
		StringBuilder stringBuilder = new StringBuilder();
		String appName =CommonUtil.toSqlLikeFormat(parameters.get("appName"));
		String type = parameters.get("Type");
		String userName = parameters.get("username");
		stringBuilder.append("SELECT APP.APP_ID,APP.APP_NAME  ");
		stringBuilder.append("FROM APP  ");
		stringBuilder.append("JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID  ");
		stringBuilder.append("AND NOT UPPER(APP_ROLE.APP_ROLE_NAME) = 'DEFAULT' ");
		stringBuilder.append("JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID=APP_ROLE.APP_ROLE_ID  ");
		stringBuilder.append("JOIN [USER] ON [USER].USER_ID = USER_APP_ROLE.USER_ID AND [USER].USERNAME = '"+userName+"'  ");
		stringBuilder.append("WHERE TYPE = '"+type+"' AND APP_NAME LIKE '"+appName+"'  ");
		stringBuilder.append("GROUP BY APP.APP_ID,APP.APP_NAME  ");
		return stringBuilder.toString();
		
	}
	

	
	public String getlistAppChangeByAppId(Map<String, Object> params){
		Map<String, Object> parameters = (Map<String, Object>) params.get("datas");
		String type = (String) parameters.get("type");
		String appId = (String) parameters.get("appId");
		String userName = (String)parameters.get("username");
		int startRow = (int)parameters.get("startRow");
		int endRow = (int)parameters.get("endRow");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT *  ");
		stringBuilder.append("FROM( ");
		stringBuilder.append("SELECT ROW_NUMBER() OVER(ORDER BY APP.APP_ID) ROWNUM,APP_ROLE.APP_ROLE_ID ,APP_ROLE.APP_ID ,APP.APP_NAME APPNAMEOLD,APP_ROLE.APP_ROLE_NAME APPROLENAMEOLD ");
		stringBuilder.append("FROM APP  ");
		stringBuilder.append("JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID  ");
		stringBuilder.append("AND NOT UPPER(APP_ROLE.APP_ROLE_NAME) = 'DEFAULT' ");
		stringBuilder.append("JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID=APP_ROLE.APP_ROLE_ID   ");
		stringBuilder.append("JOIN [USER] ON [USER].USER_ID = USER_APP_ROLE.USER_ID AND [USER].USERNAME = '"+userName+"'   ");
		stringBuilder.append("WHERE TYPE = '"+type+"' AND APP.APP_ID = '"+appId+"'   ");
		stringBuilder.append("				) DATAS WHERE DATAS.ROWNUM BETWEEN "+startRow+" AND "+endRow+" ");
		return stringBuilder.toString();
	}
	
	public String getlistChgAppComboBox (Map<String, Object> params){
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT APP_ROLE.APP_ROLE_ID,APP_ROLE.APP_ID,APP.APP_NAME,APP_ROLE.APP_ROLE_NAME ");
		stringBuilder.append("FROM APP ");
		stringBuilder.append("JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID  ");
		stringBuilder.append("WHERE TYPE = #{type} ");
		stringBuilder.append("AND APP_ROLE.APP_ID = (SELECT APP_ROLE.APP_ID FROM APP INNER JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID AND APP_ROLE.APP_ROLE_ID=#{appRoleId})	 ");
		stringBuilder.append("AND NOT UPPER(APP_ROLE.APP_ROLE_NAME) = 'DEFAULT' ");
		stringBuilder.append("AND APP_ROLE_ID NOT IN (SELECT APP_ROLE.APP_ROLE_ID ");
		stringBuilder.append("				FROM APP ");
		stringBuilder.append("				JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID AND APP_ROLE.APP_ID = (SELECT APP_ROLE.APP_ID FROM APP INNER JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID AND APP_ROLE.APP_ROLE_ID=#{appRoleId}) ");
		stringBuilder.append("				JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID=APP_ROLE.APP_ROLE_ID ");
		stringBuilder.append("				JOIN [USER] ON [USER].USER_ID = USER_APP_ROLE.USER_ID AND [USER].USERNAME = #{username} ");
		stringBuilder.append("				WHERE TYPE = #{type}) ");
		stringBuilder.append("ORDER BY APP.APP_NAME ASC ,APP_ROLE.APP_ROLE_NAME ASC ");
		return stringBuilder.toString();
	}
	
	public String getListAppAndAppRoleByAppIdAndType (){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT APP_ROLE.APP_ID,APP_ROLE.APP_ROLE_ID,APP.APP_NAME,APP_ROLE.APP_ROLE_NAME,APP_ROLE.APP_ROLE_DESC ");
		stringBuilder.append(",(SELECT COUNT(*) FROM [APP] ");
		stringBuilder.append("	INNER JOIN [APP_ROLE] ON APP.APP_ID = APP_ROLE.APP_ID AND  APP_ROLE.APP_ID = #{appId} ");
		stringBuilder.append("	WHERE STATUS = '1' AND TYPE = #{type} ) COUNT_APP ");
		stringBuilder.append("FROM [APP] ");
		stringBuilder.append("INNER JOIN [APP_ROLE] ON APP.APP_ID = APP_ROLE.APP_ID AND  APP_ROLE.APP_ID = #{appId} ");
		stringBuilder.append("WHERE STATUS = '1' AND TYPE = #{type} ");
		return stringBuilder.toString();
	}
	public String getCountListAppAndAppRoleByAppNameAndType (Map<String, Object> params){
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT COUNT(*) ");
		stringBuilder.append("FROM [APP] ");
		stringBuilder.append("INNER JOIN [APP_ROLE] ON APP.APP_ID = APP_ROLE.APP_ID ");
		if(StringUtils.isNotBlank(params.get("appName").toString())&&StringUtils.isNoneEmpty(params.get("appName").toString())){
			stringBuilder.append(" AND  APP.APP_NAME LIKE #{appName} ");
		}
		stringBuilder.append("WHERE STATUS = '1' AND TYPE = #{type} ");
		return stringBuilder.toString();
	}
	public String getListAppAndAppRoleByAppNameAndType (Map<String, Object> params){
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" SELECT * FROM ( ");
		stringBuilder.append("SELECT ROW_NUMBER() OVER(ORDER BY APP.APP_NAME ASC,APP_ROLE.APP_ROLE_NAME ASC) ROWNUM,APP_ROLE.APP_ID,APP.APP_INFO,APP_ROLE.APP_ROLE_ID,APP.APP_NAME,APP_ROLE.APP_ROLE_NAME,APP_ROLE.APP_ROLE_DESC ");
		stringBuilder.append("FROM [APP] ");
		stringBuilder.append("INNER JOIN [APP_ROLE] ON APP.APP_ID = APP_ROLE.APP_ID ");
		if(StringUtils.isNotBlank(params.get("appName").toString())&&StringUtils.isNoneEmpty(params.get("appName").toString())){
			stringBuilder.append(" AND  APP.APP_NAME LIKE #{appName} ");
		}
		stringBuilder.append("WHERE STATUS = '1' AND TYPE = #{type} ");
		stringBuilder.append(" ) DATA WHERE DATA.ROWNUM BETWEEN #{startRow} AND #{endRow}  ");
		return stringBuilder.toString();
	}
	
	public String getAppDetailGroupByApproleIds(Map<String, Object> params){
		List<String> appRoleId = (List<String>) params.get("appRoleIds");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT APP.APP_ID,APP.APP_NAME,APP_ROLE.APP_ROLE_ID,APP_ROLE.APP_ROLE_NAME ,APP.APP_FILE, (CASE WHEN APP.APP_FILE = '[]' THEN 'Complete' ELSE 'Incomplete' END) STATUS ");
		stringBuilder.append("FROM APP ");
		stringBuilder.append("INNER JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID ");
		stringBuilder.append("WHERE APP_ROLE.APP_ROLE_ID IN ( ");
		for (String i : appRoleId) {
			stringBuilder.append(" '"+ i + "',");
	    }
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		stringBuilder.append(" )");
		return stringBuilder.toString();
		
	}
	

	public String getListAppStdEligiblebyAppName (Map<String, Object> params){
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT APP_NAME,APP.APP_ID ");
		sqlBuilder.append("FROM APP ");
		sqlBuilder.append("INNER JOIN ELIGIBLE ELIG ON ELIG.ORGCODE = ( SELECT [USER].ORGCODE FROM [USER] WHERE [USER].USERNAME=#{username, jdbcType=VARCHAR})  ");
		sqlBuilder.append("AND APP.TYPE=#{type} AND APP.STATUS=1 AND APP.APP_NAME LIKE #{appName, jdbcType=VARCHAR} ");
		sqlBuilder.append("INNER JOIN APP_ROLE ROL ON APP.APP_ID = ROL.APP_ID AND ELIG.APP_ROLE_ID = ROL.APP_ROLE_ID ");
		sqlBuilder.append("GROUP BY APP_NAME,APP.APP_ID ");
		return sqlBuilder.toString();
	}
	
	public String getListAppStdGridEligibleByAppId (Map<String, Object> params){
		StringBuilder sqlBuilder = new StringBuilder();
		if(StringUtils.isNotBlank(params.get("appId").toString())&&StringUtils.isNotBlank(params.get("type").toString())){
			sqlBuilder.append("SELECT APP1.[APP_ID],ROL1.[APP_ROLE_ID],ROL1.[APP_ROLE_NAME],APP1.[APP_NAME],ROL1.[APP_ROLE_DESC] ");
			sqlBuilder.append("		,( SELECT COUNT(*) ");
			sqlBuilder.append("		FROM  [APP] APP1  ");
			sqlBuilder.append("		INNER JOIN  [APP_ROLE] ROL1 ON APP1.[APP_ID] = ROL1.[APP_ID] AND APP1.[STATUS]=1 AND APP1.TYPE = #{type} AND APP1.[APP_ID] = #{appId} ");
			sqlBuilder.append("		INNER JOIN  [ELIGIBLE] ELIG1 ON  ELIG1.[APP_ROLE_ID] = ROL1.[APP_ROLE_ID] AND ELIG1.ORGCODE = ( SELECT [USER].ORGCODE FROM [USER] WHERE [USER].USERNAME= #{username})  ");
			sqlBuilder.append("		) COUNT_APP ");
			sqlBuilder.append("FROM  [APP] APP1  ");
			sqlBuilder.append("INNER JOIN  [APP_ROLE] ROL1 ON APP1.[APP_ID] = ROL1.[APP_ID] AND APP1.[STATUS]=1 AND APP1.TYPE = #{type} AND APP1.[APP_ID] = #{appId} ");
			sqlBuilder.append("INNER JOIN  [ELIGIBLE] ELIG1 ON  ELIG1.[APP_ROLE_ID] = ROL1.[APP_ROLE_ID] AND ELIG1.ORGCODE = ( SELECT [USER].ORGCODE FROM [USER] WHERE [USER].USERNAME=#{username}) ");

		}else{
			sqlBuilder.append("SELECT APP1.[APP_ID],ROL1.[APP_ROLE_ID],ROL1.[APP_ROLE_NAME],APP1.[APP_NAME],ROL1.[APP_ROLE_DESC] ");
			sqlBuilder.append("		,( SELECT COUNT(*) ");
			sqlBuilder.append("		FROM  [APP] APP1  ");
			sqlBuilder.append("		INNER JOIN  [APP_ROLE] ROL1 ON APP1.[APP_ID] = ROL1.[APP_ID] AND APP1.[STATUS]=1 ");
			sqlBuilder.append("		INNER JOIN  [ELIGIBLE] ELIG1 ON  ELIG1.[APP_ROLE_ID] = ROL1.[APP_ROLE_ID] AND ELIG1.ORGCODE = ( SELECT [USER].ORGCODE FROM [USER] WHERE [USER].USERNAME= #{username})  ");
			sqlBuilder.append("		) COUNT_APP ");
			sqlBuilder.append("FROM  [APP] APP1  ");
			sqlBuilder.append("INNER JOIN  [APP_ROLE] ROL1 ON APP1.[APP_ID] = ROL1.[APP_ID] AND APP1.[STATUS]=1  ");
			sqlBuilder.append("INNER JOIN  [ELIGIBLE] ELIG1 ON  ELIG1.[APP_ROLE_ID] = ROL1.[APP_ROLE_ID] AND ELIG1.ORGCODE = ( SELECT [USER].ORGCODE FROM [USER] WHERE [USER].USERNAME=#{username}) ");
		}
		return sqlBuilder.toString();
	}
	public String getCountAppStdGridEligiblebyAppName (Map<String, Object> params){
		StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("SELECT ");
			sqlBuilder.append("COUNT(*) ");
			sqlBuilder.append("FROM  [APP] APP1  ");
			sqlBuilder.append("		INNER JOIN  [APP_ROLE] ROL1 ON APP1.[APP_ID] = ROL1.[APP_ID] AND APP1.[STATUS]=1  ");
			if(StringUtils.isNotBlank(params.get("type").toString())){
				sqlBuilder.append(" AND APP1.TYPE = #{type} ");
			}
			if(StringUtils.isNotBlank(params.get("appName").toString())){
				sqlBuilder.append(" AND APP1.[APP_NAME] LIKE #{appName} ");
			}
			sqlBuilder.append("		INNER JOIN  [ELIGIBLE] ELIG1 ON  ELIG1.[APP_ROLE_ID] = ROL1.[APP_ROLE_ID]  ");
			if(StringUtils.isNotBlank(params.get("username").toString())){
				sqlBuilder.append("  AND ELIG1.ORGCODE = ( SELECT [USER].ORGCODE FROM [USER] WHERE [USER].USERNAME= #{username}) ");
			}
		
		return sqlBuilder.toString();
	}
	public String getListAppStdGridEligiblebyAppName (Map<String, Object> params){
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(" SELECT * FROM ( ");
			sqlBuilder.append("SELECT ROW_NUMBER() OVER(ORDER BY APP1.APP_NAME ASC,ROL1.[APP_ROLE_NAME] ASC) ROWNUM,APP1.[APP_ID],ROL1.[APP_ROLE_ID],ROL1.[APP_ROLE_NAME],APP1.[APP_NAME],ROL1.[APP_ROLE_DESC],APP1.APP_INFO ");
			sqlBuilder.append("		FROM  [APP] APP1  ");
			sqlBuilder.append("		INNER JOIN  [APP_ROLE] ROL1 ON APP1.[APP_ID] = ROL1.[APP_ID] AND APP1.[STATUS]=1  ");
			if(StringUtils.isNotBlank(params.get("type").toString())){
				sqlBuilder.append(" AND APP1.TYPE = #{type} ");
			}
			if(StringUtils.isNotBlank(params.get("appName").toString())){
				sqlBuilder.append(" AND APP1.[APP_NAME] LIKE '%'+#{appName}+'%' ");
			}
			sqlBuilder.append("		INNER JOIN  [ELIGIBLE] ELIG1 ON  ELIG1.[APP_ROLE_ID] = ROL1.[APP_ROLE_ID]  ");
			if(StringUtils.isNotBlank(params.get("username").toString())){
				sqlBuilder.append("  AND ELIG1.ORGCODE = ( SELECT [USER].ORGCODE FROM [USER] WHERE [USER].USERNAME= #{username}) ");
			}
			sqlBuilder.append(" ) DATA WHERE DATA.ROWNUM BETWEEN #{startRow} AND #{endRow}  ");

		return sqlBuilder.toString();
	}
	
	
	
	public String getAppListSpcEligibilebyAppName (Map<String, Object> params){
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT APP_NAME,APP.APP_ID ");
		sqlBuilder.append("FROM APP ");
		sqlBuilder.append("INNER JOIN APP_ROLE ROL ON APP.APP_ID = ROL.APP_ID ");
		sqlBuilder.append("AND APP.TYPE=#{type} AND APP.STATUS=1 AND APP.APP_NAME LIKE #{appName, jdbcType=VARCHAR} ");
		sqlBuilder.append("GROUP BY APP_NAME,APP.APP_ID ");
		sqlBuilder.append("ORDER BY APP_NAME ASC ");
		return sqlBuilder.toString();
	}
	
	public String getListAppSpcGridEligibleByAppId (Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" SELECT APP1.[APP_ID],ROL1.[APP_ROLE_ID],ROL1.[APP_ROLE_NAME],APP1.[APP_NAME],ROL1.[APP_ROLE_DESC] ");
		stringBuilder.append("		,( SELECT COUNT(*) ");
		stringBuilder.append("		FROM  [APP] APP1  ");
		stringBuilder.append(" 		INNER JOIN  [APP_ROLE] ROL1 ON APP1.[APP_ID] = ROL1.[APP_ID] AND APP1.[STATUS]=1 AND APP1.TYPE = #{type} ");
		stringBuilder.append(" 		) COUNT_APP ");
		stringBuilder.append("FROM  [APP] APP1  ");
		stringBuilder.append("INNER JOIN  [APP_ROLE] ROL1 ON APP1.[APP_ID] = ROL1.[APP_ID] AND APP1.[STATUS]=1 AND APP1.TYPE = #{type} ");
		if(StringUtils.isNotBlank(params.get("appId").toString())){
			stringBuilder.append(" AND APP1.[APP_ID] = #{appId} ");
		}
		stringBuilder.append("INNER JOIN  [ELIGIBLE] ELIG1 ON  ELIG1.[APP_ROLE_ID] = ROL1.[APP_ROLE_ID] ");
		return stringBuilder.toString();
	}
	public String getCountAppSpcGridEligibleByAppName (Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" SELECT COUNT(*) ");
		stringBuilder.append("FROM  [APP] APP1  ");
		stringBuilder.append("INNER JOIN  [APP_ROLE] ROL1 ON APP1.[APP_ID] = ROL1.[APP_ID] AND APP1.[STATUS]=1 ");
		if(StringUtils.isNotBlank(params.get("type").toString())&&StringUtils.isNoneEmpty(params.get("type").toString())){
			stringBuilder.append(" AND APP1.TYPE = #{type} ");
		}
		if(StringUtils.isNotBlank(params.get("appName").toString())&&StringUtils.isNoneEmpty(params.get("appName").toString())){
			stringBuilder.append(" AND APP1.[APP_NAME] LIKE #{appName} ");
		}
		return stringBuilder.toString();
	}
	
	public String getListAppSpcGridEligibleByAppName (Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" SELECT * FROM ( ");
		stringBuilder.append(" SELECT ROW_NUMBER() OVER(ORDER BY APP1.APP_NAME,ROL1.[APP_ROLE_NAME] ASC) ROWNUM ,APP1.[APP_ID],ROL1.[APP_ROLE_ID],ROL1.[APP_ROLE_NAME],APP1.[APP_NAME],ROL1.[APP_ROLE_DESC],APP1.[APP_INFO] ");
		stringBuilder.append("FROM  [APP] APP1  ");
		stringBuilder.append("INNER JOIN  [APP_ROLE] ROL1 ON APP1.[APP_ID] = ROL1.[APP_ID] AND APP1.[STATUS]=1 ");
		if(StringUtils.isNotBlank(params.get("type").toString())&&StringUtils.isNoneEmpty(params.get("type").toString())){
			stringBuilder.append(" AND APP1.TYPE = #{type} ");
		}
		if(StringUtils.isNotBlank(params.get("appName").toString())){
			stringBuilder.append(" AND APP1.[APP_NAME] LIKE #{appName} ");
		}
		stringBuilder.append(") DATA WHERE DATA.ROWNUM BETWEEN #{startRow} AND #{endRow} ");

		return stringBuilder.toString();
	}
	
	// Grid Change UR
	public String getCountlistAppChangeByappName (Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		String appName = (String) params.get("appName");
		stringBuilder.append("SELECT COUNT(*) COUNTROW ");
		stringBuilder.append("FROM APP  ");
		stringBuilder.append("JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID AND NOT UPPER(APP_ROLE.APP_ROLE_NAME) = 'DEFAULT' ");
		stringBuilder.append("JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID=APP_ROLE.APP_ROLE_ID   ");
		stringBuilder.append("JOIN [USER] ON [USER].USER_ID = USER_APP_ROLE.USER_ID AND [USER].USERNAME = #{username}   ");
		stringBuilder.append("WHERE TYPE = #{type} ");
		if(StringUtils.isNotBlank(appName)){
			stringBuilder.append(" AND APP.APP_NAME LIKE #{appName} ");
		}
		return stringBuilder.toString();
	}
	
	public String getlistAppChangeByAppNameTypeandUsername(Map<String, Object> params){
		String appName = (String) params.get("appName");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT *  ");
		stringBuilder.append("FROM( ");
		stringBuilder.append("SELECT ROW_NUMBER() OVER(ORDER BY APP.APP_NAME ASC,APP_ROLE.APP_ROLE_NAME ASC) ROWNUM,APP_ROLE.APP_ROLE_ID ,APP_ROLE.APP_ID ,APP.APP_INFO,APP.APP_NAME APPNAMEOLD,APP_ROLE.APP_ROLE_NAME APPROLENAMEOLD,APP_ROLE.APP_ROLE_DESC ");
		stringBuilder.append("FROM APP  ");
		stringBuilder.append("JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID  ");
		stringBuilder.append("AND NOT UPPER(APP_ROLE.APP_ROLE_NAME) = 'DEFAULT' ");
		stringBuilder.append("JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID=APP_ROLE.APP_ROLE_ID   ");
		stringBuilder.append("JOIN [USER] ON [USER].USER_ID = USER_APP_ROLE.USER_ID AND [USER].USERNAME = #{username}   ");
		stringBuilder.append("WHERE TYPE = #{type}  ");
		if(StringUtils.isNotBlank(appName)){
			stringBuilder.append(" AND APP.APP_NAME LIKE #{appName} ");
		}
		stringBuilder.append("				) DATAS WHERE DATAS.ROWNUM BETWEEN #{startRow} AND #{endRow} ");
		return stringBuilder.toString();
	}
	
	// Grid Ter Author UR
		public String getCountlistTerAuthorByappName (Map<String, Object> params){
			StringBuilder stringBuilder = new StringBuilder();
			String appName = (String) params.get("appName");
			stringBuilder.append("SELECT COUNT(*) COUNTROW ");
			stringBuilder.append("FROM APP  ");
			stringBuilder.append("JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID ");
			stringBuilder.append("JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID=APP_ROLE.APP_ROLE_ID   ");
			stringBuilder.append("JOIN [USER] ON [USER].USER_ID = USER_APP_ROLE.USER_ID AND [USER].USERNAME = #{username}   ");
			stringBuilder.append("WHERE TYPE = #{type} ");
			if(StringUtils.isNotBlank(appName)){
				stringBuilder.append(" AND APP.APP_NAME LIKE #{appName} ");
			}
			return stringBuilder.toString();
		}
		public String getlistAppTerAuthorByAppNameTypeandUsername(Map<String, Object> params){
			String appName = (String) params.get("appName");
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("SELECT *  ");
			stringBuilder.append("FROM( ");
			stringBuilder.append("SELECT ROW_NUMBER() OVER(ORDER BY APP.APP_NAME ASC,APP_ROLE.APP_ROLE_NAME ASC) ROWNUM,APP_ROLE.APP_ROLE_ID ,APP_ROLE.APP_ID ,APP.APP_INFO,APP.APP_NAME ,APP_ROLE.APP_ROLE_NAME ,APP_ROLE.APP_ROLE_DESC ");
			stringBuilder.append("FROM APP  ");
			stringBuilder.append("JOIN APP_ROLE ON APP.APP_ID = APP_ROLE.APP_ID  ");
			stringBuilder.append("JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID=APP_ROLE.APP_ROLE_ID   ");
			stringBuilder.append("JOIN [USER] ON [USER].USER_ID = USER_APP_ROLE.USER_ID AND [USER].USERNAME = #{username}   ");
			stringBuilder.append("WHERE TYPE = #{type}  ");
			if(StringUtils.isNotBlank(appName)){
				stringBuilder.append(" AND APP.APP_NAME LIKE #{appName} ");
			}
			stringBuilder.append("				) DATAS WHERE DATAS.ROWNUM BETWEEN #{startRow} AND #{endRow} ");
			stringBuilder.append("	ORDER BY DATAS.APP_NAME ASC,DATAS.APP_ROLE_NAME ASC ");
			return stringBuilder.toString();
		}
}

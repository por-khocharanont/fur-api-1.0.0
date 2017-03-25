package th.cu.thesis.fur.api.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.RequestNo;
import th.cu.thesis.fur.api.model.UR;
import th.cu.thesis.fur.api.model.UrForUser;
import th.cu.thesis.fur.api.model.UrStep;
import th.cu.thesis.fur.api.model.UrStepApprove;
import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.model.UserAppOwner;
import th.cu.thesis.fur.api.model.UserCustodian;
import th.cu.thesis.fur.api.model.UserRequestInfo;
import th.cu.thesis.fur.api.model.UserRoleIden;
import th.cu.thesis.fur.api.util.UserRequestSqlProvider;

public interface UserRequestRepository {
	
	static final String SQL_SELECT_UR_REQUEST_NO_BY_UR_ID = "SELECT UR.UR_ID,UR.REQUEST_NO,REQ.SUBJECT"
			+ ",USR.USERNAME AS REQUEST_BY"
			+ ",REQ.REQUEST_DATE"
			+ ",UR.FLOW_ID "
			+ ",UR.APP_ROLE_NAME "
			+ ",UR.APP_ID "
			+ "FROM UR UR "
			+ "JOIN REQUEST_NO REQ ON REQ.REQUEST_NO = UR.REQUEST_NO "
			+ "JOIN [USER] USR ON USR.PINCODE = REQ.REQUEST_BY "
			+ "WHERE UR.UR_ID = #{urId}";
	
	static final String SQL_SELECT_CHECK_UR_STEP_CAN_APPROVE_BY_USERNAME = "SELECT * FROM UR_STEP WHERE "
			+ "UR_STEP_ID IN (SELECT UR_STEP_ID FROM UR_STEP_APPROVE WHERE USERNAME = #{username}) "
			+ "AND UR_STEP_ID NOT IN (SELECT UR_STEP_ID FROM UR_APPROVE_HISTORY WHERE USERNAME = #{username}) "
			+ "AND UR_STEP_ID IN ( SELECT UR_STEP_ID FROM UR_STEP_APPROVE WHERE UR_STEP_ID IN "
			+ "(SELECT UR_STEP_ID FROM UR_STEP WHERE UR_ID = #{urId} AND STATUS = #{status}) AND "
			+ "TEAM_NAME NOT IN (SELECT TEAM_NAME FROM UR_APPROVE_HISTORY WHERE UR_STEP_ID IN "
			+ "(SELECT UR_STEP_ID FROM UR_STEP WHERE UR_ID = #{urId} AND STATUS = #{status})"
			+ ") AND USERNAME = #{username})";

	
	static final String SQL_SELECT_CURRENT_UR_STEP_BY_UR_ID_AND_STATUS = "SELECT UR_STEP FROM UR_STEP WHERE UR_ID = #{urId} AND STATUS = #{status}";
	
	static final String SELECT_UR_STEP_BY_UR_ID = "SELECT * FROM UR_STEP WHERE UR_ID = #{urId} ORDER BY UR_STEP ASC";
	
	static final String SELECT_NEXT_SEQUENCE_UR_STEP_BY_UR_STEP_ID_PROCESS = "SELECT * FROM UR_STEP "
			+ "WHERE UR_ID = (SELECT UR_ID FROM UR_STEP WHERE UR_STEP_ID = #{urStepId}) "
			+ "AND SEQUENCE = ( SELECT SEQUENCE+1 FROM UR_STEP WHERE UR_STEP_ID = #{urStepId})";
	
	static final String SELECT_COUNT_UR_STEP_BY_UR_ID = "SELECT COUNT(*) FROM UR_STEP WHERE UR_ID = #{urId}";
	
	
	static final String SELECT_REQUEST_NO_BY_UR_ID = "SELECT * FROM REQUEST_NO WHERE REQUEST_NO IN ("
			+ "SELECT REQUEST_NO FROM UR WHERE UR_ID = #{urId})";
	
	static final String SELECT_UR_FOR_USER_BY_UR_ID_AND_UR_STATUS = "SELECT * FROM UR_FOR_USER WHERE UR_ID = #{urId} AND UR_STATUS = #{urStatus}";
	
	static final String SELECT_UR_BY_UR_ID = "SELECT * FROM UR WHERE UR_ID = #{urId}";
	
	static final String SELECT_COUNT_UR_APPROVE_HISTORY_BY_UR_STEP_ID = "SELECT COUNT(*) FROM UR_APPROVE_HISTORY WHERE UR_STEP_ID = #{urStepId}";
	
	static final String UPDATE_APP_ROLE_ID_AND_APP_ROLE_NAME_BY_UR_ID = "UPDATE UR SET APP_ROLE_NAME = #{appRoleName}"
			+ ",APP_ROLE_ID = #{appRoleId}"
			+ ",UPDATED_DATE = GETDATE()"
			+ ",UPDATED_BY = #{updatedBy} "
			+ " WHERE UR_ID = #{urId}";
	
	static final String UPDATE_UR_STATUS_BY_UR_ID = "UPDATE UR SET STATUS = #{status}"
			+ ",UPDATED_DATE = GETDATE()"
			+ ",UPDATED_BY = #{updatedBy} "
			+ " WHERE UR_ID = #{urId}";
	
	static final String UPDATE_UR_STEP_STATUS_BY_UR_STEP_ID = "UPDATE UR_STEP SET STATUS = #{status}"
			+ ",UPDATED_DATE = GETDATE()"
			+ ",UPDATED_BY = #{updatedBy} "
			+ " WHERE UR_STEP_ID = #{urStepId}";
	
	static final String SELECT_UR_STEP_ID_PROCESS_BY_UR_ID = "SELECT * FROM [UR_STEP] WHERE STATUS ='1' AND UR_ID = #{urId}";
	
	static final String SELECT_COUNT_UR_TOKEN_BY_UR_ID_AND_UR_STEP = "SELECT COUNT(*) FROM UR WHERE UR_ID = #{urId} AND UR_ID IN ( SELECT UR_ID FROM UR_STEP WHERE UR_ID = #{urId} AND UR_STEP = #{urStep})";
	
	static final String SQL_SELECT_REQUEST_NO_BY_REQUEST_NO_AND_USERNAME = "SELECT req.[REQUEST_NO], req.[REQUEST_DATE], req.[UR_TYPE] "+
			", req.[REQUEST_LIST], req.[ENNAME], req.[ENSURNAME] "+
			", req.[REQUEST_TYPE], req.[SUBJECT], req.[DETAIL], req.[GROUP_APP_NAME], req.[REQUEST_REMARK] "+
			"FROM [REQUEST_NO] req "+
			"WHERE req.[REQUEST_NO] = #{requestNo, jdbcType=VARCHAR} "+
			"AND req.[USERNAME] = #{username, jdbcType=VARCHAR} ";

static final String SQL_SELECT_REQUEST_NO_BY_REQUEST_NO = "SELECT req.[REQUEST_NO], req.[REQUEST_DATE], req.[UR_TYPE], req.[USERNAME] "+
", req.[REQUEST_LIST], req.[ENNAME], req.[ENSURNAME] "+
", req.[REQUEST_TYPE], req.[SUBJECT], req.[DETAIL], req.[GROUP_APP_NAME], req.[REQUEST_REMARK] "+
"FROM [REQUEST_NO] req "+
"WHERE req.[REQUEST_NO] = #{requestNo, jdbcType=VARCHAR} ";

static final String SQL_SELECT_UR_BY_REQUEST_NO = "SELECT * FROM [UR] ur WHERE ur.[REQUEST_NO] = #{requestNo, jdbcType=VARCHAR} ";

static final String SQL_SELECT_UR_FOR_USER_BY_UR_ID = "SELECT * FROM [UR_FOR_USER] urFor WHERE urFor.[UR_ID] =  #{urId, jdbcType=VARCHAR} ";

static final String SQL_SELECT_UR_STEP_BY_UR_ID_AND_STATUS_PROCESS = "SELECT * FROM [UR_STEP] urStep WHERE urStep.UR_ID = #{urId, jdbcType=VARCHAR} AND urStep.STATUS = '1' ";	

	
	static final String SELECT_APP_FILE_BY_APP_ID = "SELECT APP_FILE  FROM [APP] WHERE APP_ID = #{appId}";
	static final String SELECT_AUTHENTYPE_BY_APP_ID = "SELECT COUNT(*) FROM APP_AUTHEN WHERE APP_AUTHEN.APP_ID =#{appId} AND AUTHEN_TYPE_NAME = 1";

	static final String SQL_SELECT_UR_BY_APP_ROLE_ID_AND_STATUS = "SELECT * FROM [UR] WHERE APP_ROLE_ID = #{appRoleId} AND STATUS = #{status}";
	
	static final String SQL_SELECT_SEQUENCE_NO_REQUESTNO = "SELECT NEXT VALUE FOR requestNo";
	
	@Select(SELECT_APP_FILE_BY_APP_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public String getAppFileByAppId(@Param("appId") String appId);
	
	@Select(SELECT_AUTHENTYPE_BY_APP_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public String getAuthenTypeByAppId(@Param("appId")  String appId);
	
	@SelectProvider(type = UserRequestSqlProvider.class, method = "validAlreadyRoleApp")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "userId", column = "USER_ID" ,javaType=String.class)
		,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
		,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
		,@Result(property = "appname", column = "APP_NAME" ,javaType=String.class)
		,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	})
	public Map<String,String> validAlreadyRoleApp(@Param("appRoleId") String appRoleId,@Param("userId") String userId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "validURisQueued")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "userId", column = "USER_ID" ,javaType=String.class) 
	,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "appname", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	})
	public Map<String,String> validURisQueued (@Param("appRoleId") String appRoleId,@Param("userId") String userId);
	
//	@SelectProvider(type=UserRequestSqlProvider.class,method = "validbothAlreadyQueued")
//	@Options(statementType = StatementType.CALLABLE,  useCache = true)
//	@Results({ @Result(property = "username", column = "USERNAME" ,javaType=String.class)
//	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
//	,@Result(property = "appname", column = "APP_NAME" ,javaType=String.class)
//	,@Result(property = "usernamealrdy", column = "USER_ID_ALRDY" ,javaType=String.class)
//	,@Result(property = "appnamealrdy", column = "APP_NAME_ALRDY" ,javaType=String.class)
//	,@Result(property = "appRoleNamealrdy", column = "APP_ROLE_NAME_ALRDY" ,javaType=String.class)
//	})
//	public Map<String,String> validbothAlreadyQueued (@Param("appRoleId") String appRoleId,@Param("userId") String userId);
	@Select(SQL_SELECT_UR_BY_APP_ROLE_ID_AND_STATUS)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<UR> selectUrByAppRoleIdAndStatus(@Param("appRoleId") String appRoleId,@Param("status") String status) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getPincodeByUserIdorUsername")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public String getPincodeByUserIdorUsername(@Param("userId")String userId,@Param("username")String username);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getOrgCodeByUserId")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public String getOrgCodeByUserId (@Param("userId")String userId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getOrgtypeOrUserTypeByOrgcode")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public String getOrgtypeOrUserTypeByOrgcode (@Param("orgCode")String orgCode);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getTypeStandOrSpecialApp")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer getTypeStandOrSpecialApp (@Param("orgCode")String orgCode,@Param("appRoleId") String appRoleId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListRoleIdenByUsertype")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "roleIdenId", column = "ROLE_IDEN_ID" ,javaType=String.class)
	,@Result(property = "userType", column = "USER_TYPE" ,javaType=String.class)
	,@Result(property = "userId", column = "USER_ID" ,javaType=String.class)
	,@Result(property = "enname", column = "ENNAME" ,javaType=String.class)
	,@Result(property = "ensurname", column = "ENSURNAME" ,javaType=String.class)
	,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "email", column = "EMAIL" ,javaType=String.class)
	,@Result(property = "phone", column = "PHONE" ,javaType=String.class)
	,@Result(property = "mobile", column = "MOBILE" ,javaType=String.class)
//	,@Result(property = "seQuenceUser", column = "SEQUENCE_USER" ,javaType=String.class)
	,@Result(property = "createdDate", column = "CREATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "createdBy", column = "CREATED_BY" ,javaType=String.class)
	,@Result(property = "updatedDate", column = "UPDATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "updatedBy", column = "UPDATED_BY" ,javaType=String.class)
	})
	public List<UserRoleIden> getListRoleIdenByUsertype(@Param("userType")String userType);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListRoleIdenBytypeZero")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "roleIdenId", column = "ROLE_IDEN_ID" ,javaType=String.class)
	,@Result(property = "userType", column = "USER_TYPE" ,javaType=String.class)
	,@Result(property = "userId", column = "USER_ID" ,javaType=String.class)
	,@Result(property = "enname", column = "PINCODE" ,javaType=String.class)
	,@Result(property = "ensurname", column = "ENSURNAME" ,javaType=String.class)
	,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "email", column = "EMAIL" ,javaType=String.class)
	,@Result(property = "phone", column = "PHONE" ,javaType=String.class)
	,@Result(property = "mobile", column = "MOBILE" ,javaType=String.class)
//	,@Result(property = "seQuenceUser", column = "SEQUENCE_USER" ,javaType=String.class)
	,@Result(property = "createdDate", column = "CREATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "createdBy", column = "CREATED_BY" ,javaType=String.class)
	,@Result(property = "updatedDate", column = "UPDATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "updatedBy", column = "UPDATED_BY" ,javaType=String.class)
	})
	public List<UserRoleIden> getListRoleIdenBytypeZero();
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getTypeApproverforAppOwner")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer getTypeApproverforAppOwner (@Param("appRoleId") String appRoleId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListAppOwnerApprover")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "userId", column = "USER_ID" ,javaType=String.class)
	,@Result(property = "pincode", column = "PINCODE" ,javaType=String.class)
	,@Result(property = "enname", column = "ENNAME" ,javaType=String.class)
	,@Result(property = "ensurname", column = "ENSURNAME" ,javaType=String.class)
	,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "email", column = "EMAIL" ,javaType=String.class)
	,@Result(property = "phone", column = "PHONE" ,javaType=String.class)
	,@Result(property = "mobile", column = "MOBILE" ,javaType=String.class)
	,@Result(property = "seQuenceUser", column = "SEQUENCE_USER" ,javaType=String.class)
	,@Result(property = "createdDate", column = "CREATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "createdBy", column = "CREATED_BY" ,javaType=String.class)
	,@Result(property = "updatedDate", column = "UPDATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "updatedBy", column = "UPDATED_BY" ,javaType=String.class)
	})
	public List<UserAppOwner> getListAppOwnerApprover (@Param("appRoleId") String appRoleId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListAppOwnerTeamApprover")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "userId", column = "USER_ID" ,javaType=String.class)
	,@Result(property = "pincode", column = "PINCODE" ,javaType=String.class)
	,@Result(property = "enname", column = "ENNAME" ,javaType=String.class)
	,@Result(property = "ensurname", column = "ENSURNAME" ,javaType=String.class)
	,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "email", column = "EMAIL" ,javaType=String.class)
	,@Result(property = "phone", column = "PHONE" ,javaType=String.class)
	,@Result(property = "mobile", column = "MOBILE" ,javaType=String.class)
	,@Result(property = "teamName", column = "TEAM_NAME" ,javaType=String.class)
	,@Result(property = "seQuenceTeam", column = "SEQUENCE_TEAM" ,javaType=String.class)
	,@Result(property = "createdDate", column = "CREATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "createdBy", column = "CREATED_BY" ,javaType=String.class)
	,@Result(property = "updatedDate", column = "UPDATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "updatedBy", column = "UPDATED_BY" ,javaType=String.class)
	})
	public List<UserAppOwner> getListAppOwnerTeamApprover(@Param("appRoleId") String appRoleId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getTypeApproverforCustodian")
	public Integer getTypeApproverforCustodian (@Param("appRoleId") String appRoleId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListCustodianByOrgtype")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "userId", column = "USER_ID" ,javaType=String.class)
	,@Result(property = "pincode", column = "PINCODE" ,javaType=String.class)
	,@Result(property = "enname", column = "ENNAME" ,javaType=String.class)
	,@Result(property = "ensurname", column = "ENSURNAME" ,javaType=String.class)
	,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "email", column = "EMAIL" ,javaType=String.class)
	,@Result(property = "phone", column = "PHONE" ,javaType=String.class)
	,@Result(property = "mobile", column = "MOBILE" ,javaType=String.class)
	,@Result(property = "seQuenceUser", column = "SEQUENCE_USER" ,javaType=String.class)
	,@Result(property = "createdDate", column = "CREATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "createdBy", column = "CREATED_BY" ,javaType=String.class)
	,@Result(property = "updatedDate", column = "UPDATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "updatedBy", column = "UPDATED_BY" ,javaType=String.class)
	})
	public List<UserCustodian> getListCustodianByOrgtype(@Param("orgType") String orgType,@Param("appRoleId") String appRoleId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListCustodianByOrgtypeZero")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "userId", column = "USER_ID" ,javaType=String.class)
	,@Result(property = "pincode", column = "PINCODE" ,javaType=String.class)
	,@Result(property = "enname", column = "ENNAME" ,javaType=String.class)
	,@Result(property = "ensurname", column = "ENSURNAME" ,javaType=String.class)
	,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "email", column = "EMAIL" ,javaType=String.class)
	,@Result(property = "phone", column = "PHONE" ,javaType=String.class)
	,@Result(property = "mobile", column = "MOBILE" ,javaType=String.class)
	,@Result(property = "seQuenceUser", column = "SEQUENCE_USER" ,javaType=String.class)
	,@Result(property = "createdDate", column = "CREATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "createdBy", column = "CREATED_BY" ,javaType=String.class)
	,@Result(property = "updatedDate", column = "UPDATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "updatedBy", column = "UPDATED_BY" ,javaType=String.class)
	})
	public List<UserCustodian> getListCustodianByOrgtypeZero(@Param("appRoleId") String appRoleId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListCustodianTeamByOrgtype")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "userId", column = "USER_ID" ,javaType=String.class)
	,@Result(property = "pincode", column = "PINCODE" ,javaType=String.class)
	,@Result(property = "enname", column = "ENNAME" ,javaType=String.class)
	,@Result(property = "ensurname", column = "ENSURNAME" ,javaType=String.class)
	,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "email", column = "EMAIL" ,javaType=String.class)
	,@Result(property = "phone", column = "PHONE" ,javaType=String.class)
	,@Result(property = "mobile", column = "MOBILE" ,javaType=String.class)
	,@Result(property = "seQuenceTeam", column = "SEQUENCE_TEAM" ,javaType=String.class)
	,@Result(property = "teamName", column = "TEAM_NAME" ,javaType=String.class)
	,@Result(property = "createdDate", column = "CREATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "createdBy", column = "CREATED_BY" ,javaType=String.class)
	,@Result(property = "updatedDate", column = "UPDATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "updatedBy", column = "UPDATED_BY" ,javaType=String.class)
	})
	public List<UserCustodian> getListCustodianTeamByOrgtype(@Param("orgType") String orgType,@Param("appRoleId") String appRoleId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListCustodianTeamByOrgtypeZero")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "userId", column = "USER_ID" ,javaType=String.class)
	,@Result(property = "pincode", column = "PINCODE" ,javaType=String.class)
	,@Result(property = "enname", column = "ENNAME" ,javaType=String.class)
	,@Result(property = "ensurname", column = "ENSURNAME" ,javaType=String.class)
	,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "email", column = "EMAIL" ,javaType=String.class)
	,@Result(property = "phone", column = "PHONE" ,javaType=String.class)
	,@Result(property = "mobile", column = "MOBILE" ,javaType=String.class)
	,@Result(property = "seQuenceTeam", column = "SEQUENCE_TEAM" ,javaType=String.class)
	,@Result(property = "teamName", column = "TEAM_NAME" ,javaType=String.class)
	,@Result(property = "createdDate", column = "CREATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "createdBy", column = "CREATED_BY" ,javaType=String.class)
	,@Result(property = "updatedDate", column = "UPDATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "updatedBy", column = "UPDATED_BY" ,javaType=String.class)
	})
	public List<UserCustodian> getListCustodianTeamByOrgtypeZero(@Param("appRoleId") String appRoleId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListNodeFlowConfig")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "flowNodeCode", column = "FLOW_NODE_CODE" ,javaType=String.class)
	,@Result(property = "seQuence", column = "SEQUENCE" ,javaType=String.class)
	})
	public List<Map<String,String>> getListNodeFlowConfig (@Param("flowId") String flowId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getUserByUserId")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	public User getUserByUserId (@Param("userId") String object);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getUserByUsername")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "userId", column = "USER_ID" ,javaType=String.class)
	,@Result(property = "pincode", column = "PINCODE" ,javaType=String.class)
	,@Result(property = "enname", column = "ENNAME" ,javaType=String.class)
	,@Result(property = "ensurname", column = "ENSURNAME" ,javaType=String.class)
	,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "email", column = "EMAIL" ,javaType=String.class)
	,@Result(property = "phone", column = "PHONE" ,javaType=String.class)
	,@Result(property = "mobile", column = "MOBILE" ,javaType=String.class)
	,@Result(property = "createdDate", column = "CREATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "createdBy", column = "CREATED_BY" ,javaType=String.class)
	,@Result(property = "updatedDate", column = "UPDATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "updatedBy", column = "UPDATED_BY" ,javaType=String.class)
	})
	public User getUserByUsername (@Param("userName") String userName);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getUserByUsername2")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "userId", column = "USER_ID" ,javaType=String.class)
	,@Result(property = "pincode", column = "PINCODE" ,javaType=String.class)
	,@Result(property = "enname", column = "ENNAME" ,javaType=String.class)
	,@Result(property = "ensurname", column = "ENSURNAME" ,javaType=String.class)
	,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "email", column = "EMAIL" ,javaType=String.class)
	,@Result(property = "phone", column = "PHONE" ,javaType=String.class)
	,@Result(property = "mobile", column = "MOBILE" ,javaType=String.class)
	,@Result(property = "createdDate", column = "CREATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "createdBy", column = "CREATED_BY" ,javaType=String.class)
	,@Result(property = "updatedDate", column = "UPDATED_DATE" ,javaType=java.sql.Date.class)
	,@Result(property = "updatedBy", column = "UPDATED_BY" ,javaType=String.class)
	})
	public Map<String,String> getUserByUsername2 (@Param("userName") String userName);
	
	@InsertProvider(type=UserRequestSqlProvider.class,method="insertRequestNo")
	public void insertRequestNo(@Param("requestNo") RequestNo requestNo);
	
	@InsertProvider(type=UserRequestSqlProvider.class,method="insertUr")
	public void insertUr(@Param("ur") UR ur);
	
	@InsertProvider(type=UserRequestSqlProvider.class,method="insertUrforUser")
	public void insertUrforUser(@Param("urforuser") UrForUser urforuser);
	
	@InsertProvider(type=UserRequestSqlProvider.class,method="insertUrStep")
	public void insertUrStep(@Param("urStep") UrStep urStep);
	
	@InsertProvider(type=UserRequestSqlProvider.class,method="insertUrStepApprove")
	public void insertUrStepApprove(@Param("urStepApprove") UrStepApprove urStepApprove);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getDetailAppByApprole")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appName", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "listFile", column = "APP_FILE" ,javaType=String.class)
	,@Result(property = "status", column = "STATUS" ,javaType=String.class)
	})
	public Map<String,Object> getDetailAppByApprole(@Param("appRoleId") String appRoleId);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getTypeApproverAndMinimumByappRoleIdandType")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "approveType", column = "APPROVE_TYPE" ,javaType=String.class)
	,@Result(property = "minimum", column = "MINIMUM_APPROVE" ,javaType=String.class)
	})
	public Map<String,String> getTypeApproverAndMinimumByappRoleIdandType(@Param("appRoleId") String appRoleId,@Param("type") String type);

	@SelectProvider(type=UserRequestSqlProvider.class,method = "getURStepIdProcess")
	public List<UrStep> getURStepIdProcess(@Param("username") String username 
			,@Param("urStepType") String urStepType
			,@Param("urId") String urId
			,@Param("requestNo") String requestNo
			,@Param("startDate") Date startDate
			,@Param("endDate") Date endDate) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getCountUrApproveByUsernameAndType")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer getCountUrApproveByUsernameAndType(@Param("approveType") String approveType,@Param("username") String username,@Param("urStepId") String urStepId) throws RuntimeException;

	@SelectProvider(type=UserRequestSqlProvider.class,method ="getCountURByUrStepIdList") 
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer getCountURByUrStepIdList(@Param("urStepIdList") String urStepIdList,@Param("startRow") int startRow,@Param("endRow") int endRow) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method ="getURByUrStepIdList") 
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<UserRequestInfo> getURByUrStepIdList(@Param("urStepIdList") String urStepIdList,@Param("startRow") int startRow,@Param("endRow") int endRow,@Param("orderBy") String orderBy) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method ="getURApproved") 
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<UserRequestInfo> getURApproved(@Param("username") String username 
			,@Param("urStepType") String urStepType
			,@Param("urId") String urId
			,@Param("requestNo") String requestNo
			,@Param("startDate") Date startDate
			,@Param("endDate") Date endDate
			,@Param("startRow") int startRow
			,@Param("endRow") int endRow
			,@Param("orderBy") String orderBy) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method ="getCountURApproved") 
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer getCountURApproved(@Param("username") String username 
			,@Param("urStepType") String urStepType
			,@Param("urId") String urId
			,@Param("requestNo") String requestNo
			,@Param("startDate") Date startDate
			,@Param("endDate") Date endDate) throws RuntimeException;
	
	@Select(SQL_SELECT_UR_REQUEST_NO_BY_UR_ID)
	public UserRequestInfo getURAndRequestNoByUrId(@Param("urId") String urId) throws RuntimeException;
	
	
	
	@Select(SELECT_COUNT_UR_TOKEN_BY_UR_ID_AND_UR_STEP)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer getCountUrTokenByUrIdAndUrStep(@Param("urId") String urId,@Param("urStep") String urStep) throws RuntimeException;

	@Select(SELECT_UR_STEP_ID_PROCESS_BY_UR_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public UrStep getUrStepIdProcessByUrId(@Param("urId") String urId) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getDatasSaveNewByidRequestNo")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "urId", column = "UR_ID" ,javaType=String.class)
	,@Result(property = "requestNo", column = "REQUEST_NO" ,javaType=String.class)
	,@Result(property = "urType", column = "UR_TYPE" ,javaType=String.class)
	,@Result(property = "name", column = "NAME" ,javaType=String.class)
	,@Result(property = "subject", column = "SUBJECT" ,javaType=String.class)
	,@Result(property = "detail", column = "DETAIL" ,javaType=String.class)
	,@Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "requestUser", column = "REQUESTUSER" ,javaType=String.class)
	,@Result(property = "appName", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "userlist", column = "USERLIST" ,javaType=String.class)
	,@Result(property = "createdDate", column = "CREATED_DATE" ,javaType=String.class)
	,@Result(property = "requestBy", column = "REQUESTBY" ,javaType=String.class)
	,@Result(property = "periodType", column = "PERIOD_TYPE" ,javaType=String.class)
	,@Result(property = "startTime", column = "START_TIME" ,javaType=String.class)
	,@Result(property = "endTime", column = "END_TIME" ,javaType=String.class)
	,@Result(property = "requestRemark", column = "REQUEST_REMARK" ,javaType=String.class)
	,@Result(property = "requestDate", column = "REQUEST_DATE" ,javaType=String.class)
	,@Result(property = "rolePastId", column = "ROLE_PAST_ID" ,javaType=String.class)
	,@Result(property = "rolePastName", column = "ROLE_PAST" ,javaType=String.class)
	,@Result(property = "requestTypeValue", column = "REQUEST_TYPE" ,javaType=String.class)
	,@Result(property = "changeType", column = "CHANGE_TYPE" ,javaType=String.class)
	})
	public List<Map<String, Object>> getDatasSaveNewByidRequestNo(@Param("idRequestNo") String idRequestNo) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getResultCheckGroupUserByUser")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "userName", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "chkUser", column = "CHKUSER" ,javaType=String.class)
	,@Result(property = "chkOrgCode", column = "CHKORGCODE" ,javaType=String.class)
	,@Result(property = "chkOrgtype", column = "CHKORGTYPE" ,javaType=String.class)
	})
	public Map<String, String> getResultCheckGroupUserByUser(@Param("userName") String userName) throws RuntimeException;
	

	@SelectProvider(type=UserRequestSqlProvider.class,method = "getlistAppChangeByappName")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appName", column = "APP_NAME" ,javaType=String.class)
	})
	public List<Map<String, String>> getlistAppChangeByappName(@Param("params") Map<String, String> request) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getCountlistAppChangeByappName")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer getCountlistAppChangeByappName(@Param("appName") String appId,@Param("username") String username,@Param("type") String type) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getCountlistTerAuthorByappName")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer getCountlistTerAuthorByappName(@Param("appName") String appId,@Param("username") String username,@Param("type") String type) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getlistAppChangeByAppId")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appIdOld", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appRoleIdOld", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "napplication", column = "APPNAMEOLD" ,javaType=String.class)
	,@Result(property = "nappRoleOld", column = "APPROLENAMEOLD" ,javaType=String.class)
	})
	public List<Map<String, Object>> getlistAppChangeByAppId(@Param("datas") Map<String, Object> request) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getlistChgAppComboBox")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "napplication", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "nappRole", column = "APP_ROLE_NAME" ,javaType=String.class)
	})
	public List<Map<String, Object>> getlistChgAppComboBox(@Param("appRoleId") String appId,@Param("username") String username,@Param("type") String type) throws RuntimeException;

	@Update(UPDATE_APP_ROLE_ID_AND_APP_ROLE_NAME_BY_UR_ID)
	public void updateAppRoleIdAndAppRoleName(@Param("urId") String urId,@Param("appRoleId") String appRoleId,@Param("appRoleName") String appRoleName,@Param("updatedBy") String updatedBy) throws RuntimeException;

	@Update(UPDATE_UR_STATUS_BY_UR_ID)
	public void updateUrStatusByUrId(@Param("status") String status,@Param("updatedBy") String updatedBy,@Param("urId") String urId) throws RuntimeException;

	
	@Update(UPDATE_UR_STEP_STATUS_BY_UR_STEP_ID)
	public void updateUrStepStatusByUrStepId(@Param("status") String status,@Param("updatedBy") String updatedBy,@Param("urStepId") String urStepId) throws RuntimeException;

	@Select(SELECT_COUNT_UR_APPROVE_HISTORY_BY_UR_STEP_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer checkCountUrStepApproveHistory(@Param("urStepId") String urStepId) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "checkCountUrStepApprove")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer checkCountUrStepApprove(@Param("approveType") String approveType,@Param("urStepId") String urStepId) throws RuntimeException;
	
	@Select(SELECT_COUNT_UR_STEP_BY_UR_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer checkCountUrStepByUrId(@Param("urId") String urId) throws RuntimeException;
	
	@Select(SELECT_UR_BY_UR_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public UR getUrByUrId(@Param("urId") String urId) throws RuntimeException;
	
	@Select(SELECT_UR_FOR_USER_BY_UR_ID_AND_UR_STATUS)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<UrForUser> getUrForUserByUrIdAndUrStatus(@Param("urId") String urId,@Param("urStatus") String urStatus) throws RuntimeException;
	
	@Select(SELECT_REQUEST_NO_BY_UR_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public RequestNo getRequestNoByUrId(@Param("urId") String urId) throws RuntimeException;
	
	@Select(SELECT_NEXT_SEQUENCE_UR_STEP_BY_UR_STEP_ID_PROCESS)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public UrStep getNextSequenceUrStepByUrStepIdProcess(@Param("urStepId") String urStepId) throws RuntimeException;
	
	@Select(SQL_SELECT_REQUEST_NO_BY_REQUEST_NO_AND_USERNAME)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public RequestNo getRequestNoByRequestNoAndUsername(@Param("requestNo") String requestNo, @Param("username") String username) throws RuntimeException;
	
	@Select(SQL_SELECT_REQUEST_NO_BY_REQUEST_NO)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public RequestNo getRequestNoByRequestNo(@Param("requestNo") String requestNo) throws RuntimeException;

	@Select(SQL_SELECT_UR_BY_REQUEST_NO)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<UR> getUrByRequestNo(@Param("requestNo") String requestNo) throws RuntimeException;
	
	@Select(SQL_SELECT_UR_FOR_USER_BY_UR_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<UrForUser> getUrForUserByUrId(@Param("urId") String urId) throws RuntimeException;
	
	@Select(SQL_SELECT_UR_STEP_BY_UR_ID_AND_STATUS_PROCESS)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public UrStep getUrStepByUrIdAndStatusProcess(@Param("urId") String urId) throws RuntimeException;

	@Select(SELECT_UR_STEP_BY_UR_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<UrStep> getUrStepByUrId(@Param("urId") String urId) throws RuntimeException;
	

	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListAppAndAppRoleByAppIdAndType")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class) 
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "appname", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleDesc", column = "APP_ROLE_DESC" ,javaType=String.class)
	})
	public List<Map<String,Object>> getListAppAndAppRoleByAppIdAndType(@Param("appId") String appId,@Param("type") String type) throws RuntimeException;
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "validbothAleadyQueued")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "userId", column = "USER_ID" ,javaType=String.class) 
	,@Result(property = "username", column = "USERNAME" ,javaType=String.class)
	,@Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "appName", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRolename", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "queued", column = "QUEUED" ,javaType=String.class)
	,@Result(property = "already", column = "ALREADY" ,javaType=String.class)
	})
	public Map<String, Object> validbothAleadyQueued(@Param("userId") String userId,@Param("appRoleId") String appRoleId) throws RuntimeException;

//	@Select(SQL_SELECT_CHECK_UR_STEP_CAN_APPROVE_BY_USERNAME)
//	@Options(statementType = StatementType.CALLABLE, useCache = true)
//	public UrStep checkUrStepCanApproveByUsername(@Param("username") String username,@Param("urId") String urId,@Param("status") String status) throws RuntimeException;
//	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "checkUrStepCanApproveByUsernameAndApproveType")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public UrStep checkUrStepCanApproveByUsernameAndApproveType(@Param("username") String username,@Param("urId") String urId,@Param("status") String status,@Param("approveType") String approveType) throws RuntimeException;
	
	
	@Select(SQL_SELECT_CURRENT_UR_STEP_BY_UR_ID_AND_STATUS)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public String checkUrCurrentStepByUrIdAndStatus(@Param("urId") String urId,@Param("status") String status) throws RuntimeException;
	
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getAppDetailGroupByApproleIds")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "application", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "roleapplication", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "listFile", column = "APP_FILE" ,javaType=String.class)
	,@Result(property = "status", column = "STATUS" ,javaType=String.class)
	})
	public List<Map<String, Object>> getAppDetailGroupByApproleIds(@Param("appRoleIds") List<String> appRoleIds);
	

	//NEW GRID ADD STD
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListAppStdEligiblebyAppName")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appName", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "appRoleDesc", column = "APP_ROLE_DESC" ,javaType=String.class)
	})
	public List<Map<String, Object>> getListAppStdEligiblebyAppName(@Param("appName") String appId,@Param("username") String username,@Param("type") String type);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListAppStdGridEligibleByAppId")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appName", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "appRoleDesc", column = "APP_ROLE_DESC" ,javaType=String.class)
	})
	public List<Map<String, Object>> getListAppStdGridEligibleByAppId(@Param("appId") String appId,@Param("username") String username,@Param("type") String type);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getCountAppStdGridEligiblebyAppName")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	public Integer getCountAppStdGridEligiblebyAppName(@Param("appName") String appId,@Param("username") String username,@Param("type") String type);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListAppStdGridEligiblebyAppName")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appName", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "appInfo", column = "APP_INFO" ,javaType=String.class)
	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "appRoleDesc", column = "APP_ROLE_DESC" ,javaType=String.class)
	})
	public List<Map<String, Object>> getListAppStdGridEligiblebyAppName(@Param("appName") String appId,@Param("username") String username,@Param("type") String type,@Param("startRow") Integer startRow,@Param("endRow") Integer endRow);
	//NEW GRID ADD SPC
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getAppListSpcEligibilebyAppName")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appName", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "appRoleDesc", column = "APP_ROLE_DESC" ,javaType=String.class)
	})
	public List<Map<String, Object>> getListAppSpcEligibilebyAppName(@Param("appName") String appId,@Param("type") String type);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListAppSpcGridEligibleByAppId")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appName", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "appRoleDesc", column = "APP_ROLE_DESC" ,javaType=String.class)
	})
	public List<Map<String, Object>> getListAppSpcGridEligibleByAppId(@Param("appId") String appId,@Param("type") String type);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getCountAppSpcGridEligibleByAppName")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	public Integer getCountAppSpcGridEligibleByAppName(@Param("appName") String appId,@Param("username") String username,@Param("type") String type);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListAppSpcGridEligibleByAppName")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appName", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "appInfo", column = "APP_INFO" ,javaType=String.class)
	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "appRoleDesc", column = "APP_ROLE_DESC" ,javaType=String.class)
	})
	public List<Map<String, Object>> getListAppSpcGridEligibleByAppName(@Param("appName") String appId,@Param("username") String username,@Param("type") String type,@Param("startRow") Integer startRow,@Param("endRow") Integer endRow);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getlistAppChangeByAppNameTypeandUsername")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appIdOld", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appRoleIdOld", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "nappInfo", column = "APP_INFO" ,javaType=String.class)
	,@Result(property = "napplication", column = "APPNAMEOLD" ,javaType=String.class)
	,@Result(property = "nappRoleOld", column = "APPROLENAMEOLD" ,javaType=String.class)
	,@Result(property = "nappRoleDesc", column = "APP_ROLE_DESC" ,javaType=String.class)
	})
	public List<Map<String, Object>> getlistAppChangeByAppNameTypeandUsername(@Param("appName") String appId,@Param("username") String username,@Param("type") String type,@Param("startRow") Integer startRow,@Param("endRow") Integer endRow);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getlistAppTerAuthorByAppNameTypeandUsername")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "appInfo", column = "APP_INFO" ,javaType=String.class)
	,@Result(property = "application", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "appRoleDesc", column = "APP_ROLE_DESC" ,javaType=String.class)
	})
	public List<Map<String, Object>> getlistAppTerAuthorByAppNameTypeandUsername(@Param("appName") String appId,@Param("username") String username,@Param("type") String type,@Param("startRow") Integer startRow,@Param("endRow") Integer endRow);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getCountListAppAndAppRoleByAppNameAndType")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	public Integer getCountListAppAndAppRoleByAppNameAndType(@Param("appName")String appName,@Param("type") String type);
	
	@SelectProvider(type=UserRequestSqlProvider.class,method = "getListAppAndAppRoleByAppNameAndType")
	@Options(statementType = StatementType.CALLABLE,  useCache = true)
	@Results({ @Result(property = "appId", column = "APP_ID" ,javaType=String.class) 
	,@Result(property = "appInfo", column = "APP_INFO" ,javaType=String.class)
	,@Result(property = "appRoleId", column = "APP_ROLE_ID" ,javaType=String.class)
	,@Result(property = "appRoleName", column = "APP_ROLE_NAME" ,javaType=String.class)
	,@Result(property = "appname", column = "APP_NAME" ,javaType=String.class)
	,@Result(property = "appRoleDesc", column = "APP_ROLE_DESC" ,javaType=String.class)
	})
	public List<Map<String, Object>> getListAppAndAppRoleByAppNameAndType(@Param("appName")String appName,@Param("type") String type,@Param("startRow") Integer startRow,@Param("endRow") Integer endRow);
	
	
	@Select(SQL_SELECT_SEQUENCE_NO_REQUESTNO)
	public Integer getSequenceRequestNo();

}

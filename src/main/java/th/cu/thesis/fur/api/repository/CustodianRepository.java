package th.cu.thesis.fur.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.AppOwner;
import th.cu.thesis.fur.api.model.AppOwnerMember;
import th.cu.thesis.fur.api.model.AppOwnerTeam;
import th.cu.thesis.fur.api.model.Custodian;
import th.cu.thesis.fur.api.model.CustodianMember;
import th.cu.thesis.fur.api.model.CustodianTeam;

public interface CustodianRepository {
	
	static final String SQL_DELETE_CUSTODIAN_MEMBER_BY_APP_ID = "DELETE FROM [CUSTODIAN_MEMBER] WHERE CUSTODIAN_TEAM_ID IN (SELECT CUSTODIAN_TEAM_ID FROM [CUSTODIAN_TEAM] WHERE APP_ID = #{appId})";
	
	static final String SQL_DELETE_CUSTODIAN_TEAM_BY_APP_ID = "DELETE FROM [CUSTODIAN_TEAM] WHERE APP_ID = #{appId}";
	
	static final String SQL_DELETE_CUSTODIAN_BY_APP_ID = "DELETE FROM [CUSTODIAN] WHERE APP_ID = #{appId}";
	
	static final String SQL_SELECT_CUSTODIAN_BY_APP_ID_AND_USER_TYPE = "SELECT CUS.*,USR.USERNAME,USR.ENFULLNAME FROM [CUSTODIAN] CUS "
			+ "JOIN [USER] USR ON CUS.USER_ID = USR.USER_ID "
			+ "WHERE CUS.APP_ID = #{appId} "
			+ "AND CUS.USER_TYPE = #{userType} ORDER BY CUS.SEQUENCE_USER ASC";
	
	static final String SQL_SELECT_CUSTODIAN_TEAM_BY_APP_ID_AND_USER_TYPE = "SELECT * FROM [CUSTODIAN_TEAM] "
			+ "WHERE APP_ID = #{appId} "
			+ "AND USER_TYPE = #{userType} ORDER BY [SEQUENCE_TEAM] ASC";
	
	static final String SQL_SELECT_CUSTODIAN_MEMBER_BY_TEAM_ID = "SELECT CUSMEMBER.*,USR.USERNAME,USR.ENFULLNAME FROM [CUSTODIAN_MEMBER] CUSMEMBER "
			+ "JOIN [USER] USR ON CUSMEMBER.USER_ID = USR.USER_ID "
			+ "WHERE CUSMEMBER.CUSTODIAN_TEAM_ID = #{teamId}";
	
	static final String SQL_INSERT_CUSTODIAN = "INSERT INTO [CUSTODIAN] ([CUSTODIAN_ID],[USER_ID],[APP_ID],[SEQUENCE_USER],[USER_TYPE],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY])"
			+ " VALUES"
			+ " (#{custodianId}"
			+ " ,#{userId}"
			+ " ,#{appId}"
			+ " ,#{sequenceUser}"
			+ " ,#{userType}"
			+ " ,GETDATE()"
			+ " ,#{createdBy}"
			+ " ,GETDATE()"
			+ " ,#{updatedBy})";
	
	static final String SQL_INSERT_CUSTODIAN_TEAM = "INSERT INTO [CUSTODIAN_TEAM] ([CUSTODIAN_TEAM_ID],[APP_ID],[TEAM_NAME],[SEQUENCE_TEAM],[USER_TYPE],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY])"
			+ " VALUES"
			+ " (#{custodianTeamId}"
			+ " ,#{appId}"
			+ " ,#{teamName}"
			+ " ,#{sequenceTeam}"
			+ " ,#{userType}"
			+ " ,GETDATE()"
			+ " ,#{createdBy}"
			+ " ,GETDATE()"
			+ " ,#{updatedBy})";
	
	static final String SQL_INSERT_CUSTODIAN_MEMBER = "INSERT INTO [CUSTODIAN_MEMBER] ([CUSTODIAN_MEMBER_ID],[CUSTODIAN_TEAM_ID],[USER_ID],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY])"+
			" VALUES"+
			" (#{custodianMemberId}"+
			" ,#{custodianTeamId}"+
			" ,#{userId}"+
			" ,GETDATE()"+
			" ,#{createdBy}"+
			" ,GETDATE()"+
			" ,#{updatedBy})";
	
	@Delete(SQL_DELETE_CUSTODIAN_MEMBER_BY_APP_ID)
	public void deleteCustodianMemberByAppId(@Param("appId") String appId) throws RuntimeException;
	
	@Delete(SQL_DELETE_CUSTODIAN_TEAM_BY_APP_ID)
	public void deleteCustodianTeamByAppId(@Param("appId") String appId) throws RuntimeException;
	
	@Delete(SQL_DELETE_CUSTODIAN_BY_APP_ID)
	public void deleteCustodianByAppId(@Param("appId") String appId) throws RuntimeException;
	
	@Select(SQL_SELECT_CUSTODIAN_BY_APP_ID_AND_USER_TYPE)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<Custodian> getCustodianList(@Param("appId") String appId,@Param("userType") String userType) throws RuntimeException;
	
	@Select(SQL_SELECT_CUSTODIAN_TEAM_BY_APP_ID_AND_USER_TYPE)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<CustodianTeam> getCustodianTeamList(@Param("appId") String appId,@Param("userType") String userType) throws RuntimeException;
	
	@Select(SQL_SELECT_CUSTODIAN_MEMBER_BY_TEAM_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<CustodianMember> getCustodianMemberList(@Param("teamId") String teamId) throws RuntimeException;
	
	@Insert(SQL_INSERT_CUSTODIAN)
	public Integer createCustodian(Custodian custodian) throws RuntimeException;
	
	@Insert(SQL_INSERT_CUSTODIAN_TEAM)
	public Integer createCustodianTeam(CustodianTeam custodianTeam) throws RuntimeException;
	
	@Insert(SQL_INSERT_CUSTODIAN_MEMBER)
	public Integer createCustodianMember(CustodianMember custodianMember) throws RuntimeException;
	
}


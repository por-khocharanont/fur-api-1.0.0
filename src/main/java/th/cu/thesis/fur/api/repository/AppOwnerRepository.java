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

public interface AppOwnerRepository {
	
	static final String SQL_DELETE_APP_OWNER_MEMBER_BY_APP_ID = "DELETE FROM [APP_OWNER_MEMBER] WHERE APP_OWNER_TEAM_ID IN (SELECT APP_OWNER_TEAM_ID FROM [APP_OWNER_TEAM] WHERE APP_ID = #{appId})";
	
	static final String SQL_DELETE_APP_OWNER_TEAM_BY_APP_ID = "DELETE FROM [APP_OWNER_TEAM] WHERE APP_ID = #{appId}";
	
	static final String SQL_DELETE_APP_OWNER_BY_APP_ID = "DELETE FROM [APP_OWNER] WHERE APP_ID = #{appId}";
	
	static final String SQL_SELECT_APP_OWNER_BY_APP_ID = "SELECT OWNER.*,USR.USERNAME,USR.ENFULLNAME FROM [APP_OWNER] OWNER "
			+ "JOIN [USER] USR ON OWNER.USER_ID = USR.USER_ID "
			+ "WHERE OWNER.APP_ID = #{appId} ORDER BY OWNER.SEQUENCE_USER ASC";
	static final String SQL_SELECT_APP_OWNER_TEAM_BY_APP_ID = "SELECT * FROM [APP_OWNER_TEAM] WHERE APP_ID = #{appId} ORDER BY [SEQUENCE_TEAM] ASC";
	static final String SQL_SELECT_APP_OWNER_MEMBER_BY_TEAM_ID = "SELECT AOM.*,USR.USERNAME,USR.ENFULLNAME FROM [APP_OWNER_MEMBER] AOM "
			+ "JOIN [USER] USR ON AOM.USER_ID = USR.USER_ID "
			+ "WHERE AOM.APP_OWNER_TEAM_ID = #{teamId}";
	
	static final String SQL_INSERT_APP_OWNER = "INSERT INTO [APP_OWNER] ([APP_OWNER_ID],[APP_ID],[USER_ID],[SEQUENCE_USER],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY])"+
			" VALUES"+
			" (#{appOwnerId}"+
			" ,#{appId}"+
			" ,#{userId}"+
			" ,#{sequenceUser}"+
			" ,GETDATE()"+
			" ,#{createdBy}"+
			" ,GETDATE()"+
			" ,#{updatedBy})";
	
	static final String SQL_INSERT_APP_OWNER_TEAM = "INSERT INTO [APP_OWNER_TEAM] ([APP_OWNER_TEAM_ID],[APP_ID],[TEAM_NAME],[SEQUENCE_TEAM],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY])"+
			" VALUES"+
			" (#{appOwnerTeamId}"+
			" ,#{appId}"+
			" ,#{teamName}"+
			" ,#{sequenceTeam}"+
			" ,GETDATE()"+
			" ,#{createdBy}"+
			" ,GETDATE()"+
			" ,#{updatedBy})";
	
	static final String SQL_INSERT_APP_OWNER_MEMBER = "INSERT INTO [APP_OWNER_MEMBER] ([APP_OWNER_MEMBER_ID],[APP_OWNER_TEAM_ID],[USER_ID],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY])"+
			" VALUES"+
			" (#{appOwnerMemberId}"+
			" ,#{appOwnerTeamId}"+
			" ,#{userId}"+
			" ,GETDATE()"+
			" ,#{createdBy}"+
			" ,GETDATE()"+
			" ,#{updatedBy})";
	
	
	@Delete(SQL_DELETE_APP_OWNER_MEMBER_BY_APP_ID)
	public void deleteAppOwnerMemberByAppId(@Param("appId") String appId) throws RuntimeException;
	
	@Delete(SQL_DELETE_APP_OWNER_TEAM_BY_APP_ID)
	public void deleteAppOwnerTeamByAppId(@Param("appId") String appId) throws RuntimeException;
	
	@Delete(SQL_DELETE_APP_OWNER_BY_APP_ID)
	public void deleteAppOwnerByAppId(@Param("appId") String appId) throws RuntimeException;
	
	@Select(SQL_SELECT_APP_OWNER_BY_APP_ID)
	@Options(statementType = StatementType.CALLABLE)
	public List<AppOwner> getAppOwnerList(@Param("appId") String appId) throws RuntimeException;
	
	@Select(SQL_SELECT_APP_OWNER_TEAM_BY_APP_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<AppOwnerTeam> getAppOwnerTeamList(@Param("appId") String appId) throws RuntimeException;
	
	@Select(SQL_SELECT_APP_OWNER_MEMBER_BY_TEAM_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<AppOwnerMember> getAppOwnerMemberList(@Param("teamId") String teamId) throws RuntimeException;
	
	@Insert(SQL_INSERT_APP_OWNER)
	public Integer createAppOwner(AppOwner appOwner) throws RuntimeException;
	
	@Insert(SQL_INSERT_APP_OWNER_TEAM)
	public Integer createAppOwnerTeam(AppOwnerTeam appOwnerTeam) throws RuntimeException;
	
	@Insert(SQL_INSERT_APP_OWNER_MEMBER)
	public Integer createAppOwnerMember(AppOwnerMember appOwnerMember) throws RuntimeException;
	
}


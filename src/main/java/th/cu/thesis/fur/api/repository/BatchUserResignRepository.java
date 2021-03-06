package th.cu.thesis.fur.api.repository;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.model.UserResignTermainate;
import th.cu.thesis.fur.api.util.BatchUserReOrgProvider;
import th.cu.thesis.fur.api.util.BatchUserReSignProvider;

public interface BatchUserResignRepository {
	
	static final String SQL_SEARCH_USER_APP_ROLE_BY_USER_ID = "SELECT USER_APP_ROLE_ID,APP_ROLE_ID,USER_ID FROM [USER_APP_ROLE] WHERE USER_ID = #{userId}";
	
	static final String SQL_SEARCH_USER_ID_BY_PINCODE = "SELECT USER_ID FROM [USER] WHERE PINCODE = #{pin}";
	
	static final String SQL_SEARCH_USERNAME_BY_PINCODE = "SELECT USERNAME FROM [USER] WHERE PINCODE = #{pin}";
	
	static final String SQL_DELETE_ROLE_IDEN_BY_USER_RESIGN = "DELETE FROM [ROLE_IDEN] WHERE USER_ID = #{userId}";
			
	static final String SQL_DELETE_APP_OWNER_BY_USER_RESIGN = "DELETE FROM [APP_OWNER] WHERE USER_ID = #{userId}";
			
	static final String SQL_DELETE_CUSTODIAN_BY_USER_RESIGN = "DELETE FROM [CUSTODIAN] WHERE USER_ID = #{userId}";
			
	static final String SQL_DELETE_APP_OWNER_MEMBER_BY_USER_RESIGN = "DELETE FROM [APP_OWNER_MEMBER] WHERE USER_ID = #{userId}";
			
	static final String SQL_DELETE_CUSTODIAN_MEMBER_BY_USER_RESIGN = "DELETE FROM [CUSTODIAN_MEMBER] WHERE USER_ID = #{userId}";
			
	static final String SQL_DELETE_UR_APPROVER_BY_USER_RESIGN = "DELETE FROM [UR_STEP_APPROVE] WHERE PINCODE = #{pin}";
		
	static final String SQL_DELETE_USER_TOKEN_BY_USER_RESIGN = "DELETE FROM [USER_TOKEN] WHERE USER_ID = #{userId}";
			
	static final String SQL_DELETE_USER_APP_ROLE_BY_USER_RESIGN = "DELETE FROM [USER_APP_ROLE] WHERE USER_ID = #{userId}";
			
	static final String SQL_DELETE_ACIM_ROLE_BY_USER_RESIGN = "DELETE FROM [ACIM_ROLE] WHERE USER_ID = #{userId}";
			
	static final String SQL_DELETE_USER_BY_USER_RESIGN = "DELETE FROM [USER] WHERE PINCODE = #{pin}";
	
	static final String SQL_SEARCH_APP_OWNER_BY_USER_RESIGN = "SELECT APP_ID FROM [APP_OWNER] WHERE USER_ID = #{userId}";
	
	static final String SQL_SEARCH_CUSTODIAN_BY_USER_RESIGN = "SELECT APP_ID FROM [CUSTODIAN] WHERE USER_ID = #{userId}";
	
	static final String SQL_UPDATE_UR_BY_USER_RESIGN = "UPDATE UR SET "
			+ "STATUS = '3'  "
			+ ",UPDATED_DATE = GETDATE() "
			+ ",UPDATED_BY = #{updateBy} "
			+ "WHERE REQUEST_NO IN "
			+ "(SELECT REQUEST_NO FROM REQUEST_NO " 
			+ "WHERE REQUEST_BY = #{pin}) and STATUS = '1'";
	
	static final String SQL_UPDATE_UR_FOR_USER_BY_USER_RESIGN = "UPDATE UR_FOR_USER SET "
			+ "UR_STATUS = '3'  "
			+ ",UPDATED_DATE = GETDATE() "
			+ ",UPDATED_BY = #{updateBy} "
			+ "WHERE UR_STATUS = '1' and "
			+ "PINCODE = #{pin} ";
	
	static final String SQL_FIND_UR_APPROVER_BY_USER_RESIGN = "SELECT UR.UR_ID "
			+ "FROM UR_STEP_APPROVE USA "
			+ "INNER JOIN UR_STEP US ON US.UR_STEP_ID = USA.UR_STEP_ID "
			+ "INNER JOIN UR UR ON UR.UR_ID = US.UR_ID "
			+ "WHERE USA.PINCODE = #{pin} AND US.STATUS IN ('0','1')  ";
	
	static final String SQL_DELETE_UR_STEP_APPROVER_BY_USER_RESIGN = "DELETE FROM UR_STEP_APPROVE "
			+ "WHERE UR_STEP_APPROVE.UR_STEP_ID IN ( "
			+ "SELECT US.UR_STEP_ID "
			+ "FROM UR_STEP_APPROVE USA "
			+ "INNER JOIN UR_STEP US ON US.UR_STEP_ID = USA.UR_STEP_ID "
			+ "INNER JOIN UR UR ON UR.UR_ID = US.UR_ID "
			+ "WHERE USA.PINCODE = #{pin} AND US.STATUS IN ('0','1')   ) ";
		
	static final String SQL_SEARCH_ROLE_IDEN_BY_USER_RESIGN = "SELECT USER_ID FROM ROLE_IDEN "
			+ "WHERE USER_ID = #{userId} ";
	
	static final String SQL_SEARCH_APPOWNER_APP_BY_USER_RESIGN = "SELECT APP_NAME FROM APP "
			+ "WHERE APP_ID IN ( "
			+ "SELECT APP_ID FROM APP_OWNER WHERE "
			+ "USER_ID = #{userId} ) ";
	
	static final String SQL_SEARCH_APPOWNER_TEAM_APP_BY_USER_RESIGN = " SELECT APP_NAME FROM APP "
			+ "WHERE APP_ID IN ( "
			+ "SELECT APP_ID FROM APP_OWNER_TEAM "
			+ "WHERE APP_OWNER_TEAM_ID IN ( "
			+ "SELECT APP_OWNER_TEAM_ID FROM APP_OWNER_MEMBER "
			+ "WHERE USER_ID = #{userId} ) ) ";
		
	static final String SQL_SEARCH_CUSTODIAN_APP_BY_USER_RESIGN = "SELECT APP_NAME FROM APP "
			+ "WHERE APP_ID IN ( "
			+ "SELECT APP_ID FROM CUSTODIAN WHERE "
			+ "USER_ID = #{userId} ) ";
	
	static final String SQL_SEARCH_CUSTODIAN__APP_TEAM_BY_USER_RESIGN = " SELECT APP_NAME FROM APP "
			+ "WHERE APP_ID IN ( "
			+ "SELECT APP_ID FROM CUSTODIAN_TEAM "
			+ "WHERE CUSTODIAN_TEAM_ID IN ( "
			+ "SELECT CUSTODIAN_TEAM_ID FROM CUSTODIAN_MEMBER "
			+ "WHERE USER_ID = #{userId} ) ) ";
	
	@Select(SQL_SEARCH_CUSTODIAN__APP_TEAM_BY_USER_RESIGN)
	public List<String> findCustodianTeamByUserResign(@Param("userId") String userId) throws RuntimeException;
	
	@Select(SQL_SEARCH_CUSTODIAN_APP_BY_USER_RESIGN)
	public List<String> findCustodianByUserResign(@Param("userId") String userId) throws RuntimeException;
	
	@Select(SQL_SEARCH_APPOWNER_TEAM_APP_BY_USER_RESIGN)
	public List<String> findAppOwnerTeamByUserResign(@Param("userId") String userId) throws RuntimeException;
	
	@Select(SQL_SEARCH_APPOWNER_APP_BY_USER_RESIGN)
	public List<String> findAppOwnerByUserResign(@Param("userId") String userId) throws RuntimeException;
	
	@Select(SQL_SEARCH_ROLE_IDEN_BY_USER_RESIGN)
	public List<String> findRoleIdenByUserResign(@Param("userId") String userId) throws RuntimeException;
	
	@Select(SQL_FIND_UR_APPROVER_BY_USER_RESIGN)
	public List<String> findUrApproverByUserResign(@Param("pin") String pin) throws RuntimeException;
	
	@Delete(SQL_DELETE_UR_STEP_APPROVER_BY_USER_RESIGN)
	public void deleteApproverByUserResign(@Param("pin") String pin) throws RuntimeException;
	
	@Select(SQL_SEARCH_USER_APP_ROLE_BY_USER_ID)
	public List<UserResignTermainate> findUserAppRoleByUserId(@Param("userId") String userId) throws RuntimeException;
	
	@Select(SQL_SEARCH_USER_ID_BY_PINCODE)
	public String findUserIdByPin(@Param("pin") String pin) throws RuntimeException;
	
	@Select(SQL_SEARCH_USERNAME_BY_PINCODE)
	public String findUsernameByPin(@Param("pin") String pin) throws RuntimeException;
		
	@Delete(SQL_DELETE_ROLE_IDEN_BY_USER_RESIGN)
	public void deleteUserRoleIden(@Param("userId") String userId) throws RuntimeException;
		
	@Delete(SQL_DELETE_APP_OWNER_BY_USER_RESIGN)
	public void deleteUserAppOwner(@Param("userId") String userId) throws RuntimeException;
		
	@Delete(SQL_DELETE_CUSTODIAN_BY_USER_RESIGN)
	public void deleteUserCustodian(@Param("userId") String userId) throws RuntimeException;
		
	@Delete(SQL_DELETE_APP_OWNER_MEMBER_BY_USER_RESIGN)
	public void deleteUserAppOwnerMember(@Param("userId") String userId) throws RuntimeException;
		
	@Delete(SQL_DELETE_CUSTODIAN_MEMBER_BY_USER_RESIGN)
	public void deleteUserCustodianMember(@Param("userId") String userId) throws RuntimeException;
	
	@Delete(SQL_DELETE_UR_APPROVER_BY_USER_RESIGN)
	public void deleteUserUrApprover(@Param("pin") String pin) throws RuntimeException;
		
	@Delete(SQL_DELETE_USER_TOKEN_BY_USER_RESIGN)
	public void deleteUserToken(@Param("userId") String userId) throws RuntimeException;
		
	@Delete(SQL_DELETE_USER_APP_ROLE_BY_USER_RESIGN)
	public void deleteUserAppRole(@Param("userId") String userId) throws RuntimeException;
	
	@Delete(SQL_DELETE_ACIM_ROLE_BY_USER_RESIGN)
	public void deleteUserAcimRole(@Param("userId") String userId) throws RuntimeException;
	
	@Delete(SQL_DELETE_USER_BY_USER_RESIGN)
	public void deleteUserResign(@Param("pin") String pin) throws RuntimeException;
	
	@Update(SQL_UPDATE_UR_BY_USER_RESIGN)
	public Integer UpdateUrReject(@Param("pin") String pin,@Param("updateBy") String updateBy) throws RuntimeException;
	
	@Update(SQL_UPDATE_UR_FOR_USER_BY_USER_RESIGN)
	public Integer UpdateUrForUserReject(@Param("pin") String pin,@Param("updateBy") String updateBy) throws RuntimeException;
	
	@SelectProvider(type=BatchUserReSignProvider.class,method ="listEmailAdminAcim") 
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<String> listEmailAdminAcim();
	
	@SelectProvider(type=BatchUserReSignProvider.class,method ="listTypeApplication") 
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<String> listTypeApplication(@Param("userName")String userName,@Param("date")Date date);
	
	@SelectProvider(type=BatchUserReSignProvider.class,method ="allApplicationByUser") 
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<String> allApplicationByUser(@Param("user") User user,@Param("type")String type, @Param("date") Date date);
	
}

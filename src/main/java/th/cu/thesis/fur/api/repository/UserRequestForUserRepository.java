package th.cu.thesis.fur.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.UrForUser;

public interface UserRequestForUserRepository {
	
	static final String UR_STATUS_REJECT = "3"; 
	static final String UR_STATUS_PROCESS = "1"; 
	static final String AUTOEN_TYPE_TOKEN = "1";
	
	static final String UPDATE_TOKEN_SERIAL_NUMBER_BY_UR_ID_AND_USERNAME = "UPDATE UR_FOR_USER SET TOKEN_SERIAL_NUMBER = #{tokenSerialNumber}"
			+ ",UPDATED_DATE = GETDATE()"
			+ ",UPDATED_BY = #{updatedBy} "
			+ " WHERE UR_ID = #{urId} "
			+ " AND USERNAME = #{username}"
			+ " AND UR_STATUS != '" + UR_STATUS_REJECT + "'";
	
	static final String UPDATE_UR_FOR_USER_STATUS_BY_UR_ID = "UPDATE UR_FOR_USER SET UR_STEP = #{urStep}"
			+ ",UR_STATUS = #{status}"
			+ ",UPDATED_DATE = GETDATE()"
			+ ",UPDATED_BY = #{updatedBy} "
			+ " WHERE UR_ID = #{urId}"
			+ " AND UR_STATUS != '" + UR_STATUS_REJECT + "'";
	
	static final String UPDATE_TOKEN_SERIAL_NUMBER_ALL_BY_APP_TOKEN_AND_USERNAME = "UPDATE UR_FOR_USER SET TOKEN_SERIAL_NUMBER = #{tokenSerialNumber}"
			+ ",UPDATED_DATE = GETDATE()"
			+ ",UPDATED_BY = #{updatedBy} "
			+ "WHERE UR_ID IN "
			+ "( SELECT UR_ID FROM UR WHERE APP_ID IN (SELECT APP_ID FROM [APP_AUTHEN] WHERE AUTHEN_TYPE_NAME = '" + AUTOEN_TYPE_TOKEN +"') )"
			+ "AND UR_STATUS = '" + UR_STATUS_PROCESS + "'"
			+ "AND USERNAME = '#{username}'";
	
	
	static final String SELECT_UR_FOR_USER_BY_UR_ID = "SELECT * FROM UR_FOR_USER WHERE UR_ID = #{urId}";
	
	static final String SELECT_UR_FOR_USER_BY_UR_ID_AND_USERNAME = "UPDATE UR_FOR_USER SET UR_STEP = #{urStep}"
			+ ",UR_STATUS = #{status}"
			+ ",UPDATED_DATE = GETDATE()"
			+ ",UPDATED_BY = #{updatedBy} "
			+ " WHERE UR_ID = #{urId} "
			+ " AND USERNAME = #{username}"
			+ " AND UR_STATUS != '" + UR_STATUS_REJECT + "'";
	
	
	
	@Update(UPDATE_TOKEN_SERIAL_NUMBER_BY_UR_ID_AND_USERNAME)
	public void updateTokenSerialNumber(@Param("tokenSerialNumber") String tokenSerialNumber,@Param("urId") String urId,@Param("username") String username,@Param("updatedBy") String updatedBy) throws RuntimeException;

	@Update(UPDATE_TOKEN_SERIAL_NUMBER_ALL_BY_APP_TOKEN_AND_USERNAME)
	public void updateTokenSerialNumberAllByAppTokenAndUsername(@Param("tokenSerialNumber") String tokenSerialNumber,@Param("username") String username,@Param("updatedBy") String updatedBy) throws RuntimeException;
	
	@Update(UPDATE_UR_FOR_USER_STATUS_BY_UR_ID)
	public void updateUrForUserStatusByUrId(@Param("urStep") String urStep,@Param("status") String status,@Param("updatedBy") String updatedBy,@Param("urId") String urId) throws RuntimeException;

	@Update(SELECT_UR_FOR_USER_BY_UR_ID_AND_USERNAME)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public void updateUrForUserStatusByUrIdAndUsername(@Param("username") String username,@Param("urStep") String urStep,@Param("status") String status,@Param("updatedBy") String updatedBy,@Param("urId") String urId) throws RuntimeException;

	
	@Select(SELECT_UR_FOR_USER_BY_UR_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<UrForUser> getUrForUserByUrId(@Param("urId") String urId) throws RuntimeException;
}

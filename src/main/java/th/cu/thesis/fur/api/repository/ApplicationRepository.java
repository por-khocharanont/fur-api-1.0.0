package th.cu.thesis.fur.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.Application;
import th.cu.thesis.fur.api.model.ApplicationRole;
import th.cu.thesis.fur.api.util.ApplicationSqlProvider;

public interface ApplicationRepository {
	
	static final String SQL_UPDATE_APP_FILE_BY_APP_ID = "UPDATE FROM [APP] SET APP_FILE=#{appFile} WHERE APP_ID = #{appId}";
	static final String SQL_SELECT_APPLICATION_BY_ID = "SELECT * FROM [APP] WHERE APP_ID = #{appId}";
	
	static final String SQL_SELECT_LIKE_BY_APPNAME = "SELECT * FROM [APP] WHERE APP_NAME LIKE #{appName} AND STATUS = '1' ";

	static final String SQL_SELECT_BY_APPNAME = "SELECT * FROM [APP] WHERE APP_NAME = #{appName}";
	
	static final String SQL_SELECT_APPLICATION_ROLE_BY_APPID = "SELECT * FROM [APP_ROLE] WHERE APP_ID = #{appId} ORDER BY CREATED_DATE ASC,APP_ROLE_NAME ASC";
	
	static final String SQL_SELECT_APPLICATION_ROLE_BY_APPROLEID = "SELECT * FROM [APP_ROLE] WHERE APP_ROLE_ID = #{appRoleId}";
	
	static final String SQL_SELECT_COUNT_APPLICATION_ROLE_BY_APPROLEID = "SELECT COUNT(*) FROM [APP_ROLE] WHERE APP_ROLE_ID = #{appRoleId}";
	
	static final String SQL_UPDATE_APPLICATION = "UPDATE [APP] SET APP_NAME = #{appName}"
			+ ",APP_INFO=#{appInfo}"
			+ ",STATUS=#{status}"
			+ ",TYPE=#{type}"
			+ ",UPDATED_BY=#{updatedBy}"
			+ ",UPDATED_DATE=GETDATE()"
			+ ",APP_FILE=#{appFile} WHERE APP_ID = #{appId}";
	
	static final String SQL_INSERT_APPLICATION = "INSERT INTO [APP] ([APP_ID],[APP_NAME],[APP_INFO]"
			+ ",[STATUS],[TYPE],[APP_FILE],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY])"
			+ " VALUES"
			+ " (#{appId}"
			+ " ,#{appName}"
			+ " ,#{appInfo}"
			+ " ,#{status}"
			+ " ,#{type}"
			+ " ,#{appFile}"
			+ " ,GETDATE()"
			+ " ,#{createdBy}"
			+ " ,GETDATE()"
			+ " ,#{updatedBy})";
	
	@SelectProvider(type = ApplicationSqlProvider.class, method = "selectSearchApplication")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<Application> getSearchApplication(@Param("sidx")String sidx,@Param("sord")String sord,@Param("appName") String appName,@Param("status") String status,@Param("roleName") String roleName,@Param("authenticationType") String authenticationType,@Param("appOwnerName") String appOwnerName,@Param("custodianName") String custodianName,@Param("applicationType") String applicationType,@Param("startRow") int startRow,@Param("endRow") int endRow) throws RuntimeException;

	@SelectProvider(type = ApplicationSqlProvider.class, method = "selectCountSearchApplication")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public int getCountTotalRecord(@Param("appName") String appName,@Param("status") String status,@Param("roleName") String roleName,@Param("authenticationType") String authenticationType,@Param("appOwnerName") String appOwnerName,@Param("custodianName") String custodianName,@Param("applicationType") String applicationType) throws RuntimeException;
	
	@Select(SQL_SELECT_APPLICATION_BY_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Application getApplicationById(@Param("appId") String appId) throws RuntimeException;
	
	@Select(SQL_SELECT_LIKE_BY_APPNAME)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<Application> findByAppName(@Param("appName") String appName) throws RuntimeException;
	
	@Select(SQL_SELECT_BY_APPNAME)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<Application> getApplicationByAppName(@Param("appName") String appName) throws RuntimeException;
	
	@SelectProvider(type = ApplicationSqlProvider.class, method = "findByAppNameAndType")
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<Application> findByAppNameAndType(@Param("appName") String appName,@Param("type") String type) throws RuntimeException;
	
	@Insert(SQL_INSERT_APPLICATION)
	public Integer createApplication(Application app) throws RuntimeException;
	
	@Select(SQL_SELECT_APPLICATION_ROLE_BY_APPID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<ApplicationRole> findApplicationRoleByAppId(@Param("appId") String appId) throws RuntimeException;
	
	@Select(SQL_SELECT_APPLICATION_ROLE_BY_APPROLEID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<ApplicationRole> findApplicationRoleByAppRoleId(@Param("appRoleId") String appRoleId) throws RuntimeException;
	
	@Select(SQL_SELECT_COUNT_APPLICATION_ROLE_BY_APPROLEID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer countApplicationRoleByAppRoleId(@Param("appRoleId") String appRoleId) throws RuntimeException;
	
	@Update(SQL_UPDATE_APPLICATION)
	public Integer updateApplication(Application app) throws RuntimeException;
	
	@Update(SQL_UPDATE_APP_FILE_BY_APP_ID)
	public Integer updateAppFileByAppId(@Param("appId") String appId,@Param("appFile") String appFile,@Param("updatedBy") String updatedBy) throws RuntimeException;
}


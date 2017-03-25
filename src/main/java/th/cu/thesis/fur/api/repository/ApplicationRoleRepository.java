package th.cu.thesis.fur.api.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.ApplicationRole;

public interface ApplicationRoleRepository {
	static final String SQL_INSERT = "INSERT INTO [APP_ROLE] ([APP_ROLE_ID],[APP_ID],[APP_ROLE_NAME],[APP_ROLE_DESC],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY])"+
			" VALUES"+
			" (#{appRoleId}"+
			" ,#{appId}"+
			" ,#{appRoleName}"+
			" ,#{appRoleDesc}"+
			" ,GETDATE()"+
			" ,#{createdBy}"+
			" ,GETDATE()"+
			" ,#{updatedBy})";
	
	static final String SQL_SELECT_BY_APP_ROLE_ID = "SELECT * FROM [APP_ROLE] WHERE APP_ROLE_ID = #{appRoleId}";
	
	static final String SQL_UPDATE = "UPDATE [APP_ROLE] SET [APP_ROLE_NAME]=#{appRoleName}"
			+ ",[APP_ROLE_DESC]=#{appRoleDesc}"
			+ ",[UPDATED_DATE]=GETDATE()"
			+ ",[UPDATED_BY]=#{updatedBy}"
			+ " WHERE [APP_ROLE_ID] = #{appRoleId}";
	
	static final String SQL_DELETE_BY_ID = "DELETE FROM [APP_ROLE] WHERE [APP_ROLE_ID] = #{appRoleId}";
	
	static final String SQL_SELECT_BY_APP_ID_AND_APP_ROLE_NAME = "SELECT * FROM [APP_ROLE] WHERE APP_ID = #{appId} AND APP_ROLE_NAME = #{appRoleName}";
	
	@Insert(SQL_INSERT)
	public Integer create(ApplicationRole appRole) throws RuntimeException;
	
	@Delete(SQL_DELETE_BY_ID)
	public Integer deleteById(@Param("appRoleId") String appRoleId) throws RuntimeException;
	
	@Update(SQL_UPDATE)
	public void updateById(ApplicationRole appRole) throws RuntimeException;
	
	@Select(SQL_SELECT_BY_APP_ID_AND_APP_ROLE_NAME)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public ApplicationRole selectByAppIdAndAppRoleName(@Param("appId") String appId,@Param("appRoleName") String appRoleName) throws RuntimeException;
	
	@Select(SQL_SELECT_BY_APP_ROLE_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public ApplicationRole selectByAppRoleId(@Param("appRoleId") String appRoleId) throws RuntimeException;
	
}


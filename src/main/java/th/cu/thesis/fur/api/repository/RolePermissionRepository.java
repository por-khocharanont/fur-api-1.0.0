package th.cu.thesis.fur.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.RolePermission;

public interface RolePermissionRepository {
	
	static final String SQL_SELECT_ROLEPERMISSION_BY_USERID = "SELECT rolePerm.[ROLE_CODE],rolePerm.[MENU_CODE], rolePerm.[ENABLE], rolePerm.ACTION_PERMISSION  "
															+ "FROM ACIM_ROLE acimRole "
															+ "INNER JOIN ROLE_PERMISSION rolePerm "
															+ "ON rolePerm.[ROLE_CODE] = acimRole.[ROLE_CODE] "
															+ "WHERE acimRole.[USER_ID] = #{userId, jdbcType=VARCHAR} ";
	
	static final String SQL_SELECT_ROLEPERMISSION_BY_USERNAME = "SELECT rolePerm.[ROLE_CODE],rolePerm.[MENU_CODE], rolePerm.[ENABLE], rolePerm.ACTION_PERMISSION  "
															+ "FROM ACIM_ROLE acimRole "
															+ "INNER JOIN ROLE_PERMISSION rolePerm "
															+ "ON rolePerm.[ROLE_CODE] = acimRole.[ROLE_CODE] "
															+ "WHERE acimRole.[USER_ID] = (SELECT [USER_ID] FROM [USER] WHERE [USERNAME] = #{username, jdbcType=VARCHAR} ) ";
	
	static final String SQL_SELECT_ROLEPERMISSION_USER = "SELECT rolePerm.[ROLE_CODE],rolePerm.[MENU_CODE], rolePerm.[ENABLE], rolePerm.ACTION_PERMISSION  "
															+ "FROM ACIM_ROLE acimRole "
															+ "INNER JOIN ROLE_PERMISSION rolePerm "
															+ "ON rolePerm.[ROLE_CODE] = acimRole.[ROLE_CODE] "
															+ "WHERE acimRole.[ROLE_CODE] = 'USER' ";
	
	static final String SQL_INSERT_ROLEPERMISION = "INSERT INTO [ROLE_PERMISSION]"
	           +"([ROLE_PERMISSION_ID]"
	           +",[ROLE_CODE]"
	           +",[MENU_CODE]"
	           +",[ENABLE]"
	           +",[ACTION_PERMISSION]"
	           +",[CREATED_DATE]"
	           +",[CREATED_BY]"
	           +",[UPDATED_DATE]"
	           +",[UPDATED_BY]) VALUES"
	           +"(#{rolePer.rolePermissionId}"
	           +",#{rolePer.roleCode}"
	           +",#{rolePer.menuCode}"
	           +",#{rolePer.enable}"
	           +",#{rolePer.actionPermission}"
	           +",#{rolePer.createdDate}"
	           +",#{rolePer.createdBy}"
	           +",#{rolePer.updatedDate}"
	           +",#{rolePer.updatedBy})";
	
	@Select(SQL_SELECT_ROLEPERMISSION_BY_USERID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<RolePermission> getRolePermissionByUserId(@Param("userId") String userId) throws RuntimeException;
	
	@Select(SQL_SELECT_ROLEPERMISSION_BY_USERNAME)
	public List<RolePermission> getRolePermissionByUsername(@Param("username") String username) throws RuntimeException;
	
	@Select(SQL_SELECT_ROLEPERMISSION_USER)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<RolePermission> getRolePermissionUser() throws RuntimeException;
	
	@Select(SQL_INSERT_ROLEPERMISION)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public void insertRolePermissionUser(@Param("rolePer") RolePermission rolePer) throws RuntimeException;
}

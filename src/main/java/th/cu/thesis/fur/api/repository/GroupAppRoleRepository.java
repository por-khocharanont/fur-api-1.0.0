package th.cu.thesis.fur.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.GroupAppRole;

public interface GroupAppRoleRepository {
	
	static final String SQL_SELECT_GROUP_APP_ROLE_BY_APP_ROLE_ID = "SELECT * FROM GROUP_APP_ROLE WHERE APP_ROLE_ID = #{appRoleId}";

	@Select(SQL_SELECT_GROUP_APP_ROLE_BY_APP_ROLE_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<GroupAppRole> selectGroupAppRoleByAppRoleId(@Param("appRoleId") String appRoleId) throws RuntimeException;
}

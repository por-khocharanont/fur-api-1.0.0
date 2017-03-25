package th.cu.thesis.fur.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.ApplicationAuthentication;

public interface ApplicationAuthenticationRepository {
	static final String SQL_INSERT = "INSERT INTO [APP_AUTHEN] ([APP_AUTHEN_ID],[APP_ID],[AUTHEN_TYPE_NAME],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY])"+
			" VALUES"+
			" (#{appAuthenId}"+
			" ,#{appId}"+
			" ,#{authenTypeName}"+
			" ,GETDATE()"+
			" ,#{createdBy}"+
			" ,GETDATE()"+
			" ,#{updatedBy})";
	
	static final String SQL_DELETE_BY_APP_ID = "DELETE FROM APP_AUTHEN WHERE APP_ID = #{appId}";
	
	static final String SQL_SELECT_APPLICATION_AUTHENTICATION_BY_APP_ID = "SELECT * FROM [APP_AUTHEN] WHERE APP_ID = #{appId}";
	
	@Insert(SQL_INSERT)
	public Integer create(ApplicationAuthentication appAuthen) throws RuntimeException;
	
	@Select(SQL_SELECT_APPLICATION_AUTHENTICATION_BY_APP_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<ApplicationAuthentication> getAppAuthenByAppId(String appId) throws RuntimeException;
	
	@Delete(SQL_DELETE_BY_APP_ID)
	public Integer deleteByAppId(String appId) throws RuntimeException;
}


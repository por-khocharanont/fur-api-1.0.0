package th.cu.thesis.fur.api.repository;


import org.apache.ibatis.annotations.Insert;

import th.cu.thesis.fur.api.model.UserToken;
public interface UserTokenRepository {

	static final String SQL_INSERT_USER_TOKEN = "INSERT INTO [USER_TOKEN] ([USER_TOKEN_ID],[USER_ID],[SERIAL_NUMBER]"
			+ ",[UR_ID],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY])"
			+ " VALUES"
			+ " (#{userTokenId}"
			+ " ,#{userId}"
			+ " ,#{serialNumber}"
			+ " ,#{urId}"
			+ " ,GETDATE()"
			+ " ,#{createdBy}"
			+ " ,GETDATE()"
			+ " ,#{updatedBy})";
	
	@Insert(SQL_INSERT_USER_TOKEN)
	public Integer createUserToken(UserToken userToken) throws RuntimeException;
	
}

	
	


package th.cu.thesis.fur.api.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import th.cu.thesis.fur.api.model.NewUser;
import th.cu.thesis.fur.api.model.OrgFromAcim;
import th.cu.thesis.fur.api.model.UserFromAcim;
import th.cu.thesis.fur.api.rest.model.om.ListNewUserProfileResponse;
import th.cu.thesis.fur.api.rest.model.om.NewUserProfile;

public interface BatchUserRepository {
	
	
	static final String SQL_SELECT_USER = "SELECT * FROM [USER] WHERE PINCODE = #{pin}";
	
	static final String SQL_INSERT_NEW_USER = "INSERT INTO [USER] "
			+ "(USER_ID,PINCODE,STATUS,USERNAME,EMAIL,ENNAME,ENSURNAME,ENFULLNAME,THNAME,THSURNAME,THFULLNAME,CREATED_DATE,CREATED_BY,UPDATED_DATE,UPDATED_BY) "
			+ " VALUES"
			+ " (#{userId}"
			+ " ,#{pincode}"
			+ " ,#{status}"
			+ " ,#{username}"
			+ " ,#{email}"
			+ " ,#{enName}"
			+ " ,#{enSurName}"
			+ " ,#{enFullName}"
			+ " ,#{thName}"
			+ " ,#{thSurName}"
			+ " ,#{thFullName}"
			+ " ,GETDATE() "
			+ " ,'SYSTEM-ADMIN'"
			+ " ,GETDATE()"
			+ " ,'SYSTEM-ADMIN') ";

	@Select(SQL_SELECT_USER)
	//public Integer findUrClose()  throws RuntimeException;
	public UserFromAcim selectUser(@Param("pin") String pin);
	
	@Insert(SQL_INSERT_NEW_USER)
	//public Integer findUrClose()  throws RuntimeException;
	public Integer InsertNewUser(NewUser newUser);
	

}

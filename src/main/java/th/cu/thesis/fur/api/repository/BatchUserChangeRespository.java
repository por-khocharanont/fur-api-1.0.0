package th.cu.thesis.fur.api.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import th.cu.thesis.fur.api.model.NewUser;
import th.cu.thesis.fur.api.model.UserFromAcim;

public interface BatchUserChangeRespository {

	
	static final String SQL_UPDATE_PINCODE = "UPDATE [USER] SET "
			+ "PINCODE = #{pincode} "
			+ "WHERE PINCODE = #{oldpin}";


	
	@Update(SQL_UPDATE_PINCODE)
	//public Integer findUrClose()  throws RuntimeException;
	public Integer UpdatePincode(@Param("pincode") String pincode, @Param("oldpin") String oldpin);
	
	
}

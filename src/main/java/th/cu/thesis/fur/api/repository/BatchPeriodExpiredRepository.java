package th.cu.thesis.fur.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.UserExpireTerminate;
import th.cu.thesis.fur.api.model.UserInPeriod;
import th.cu.thesis.fur.api.util.BatchPeriodExpiredProvider;

public interface BatchPeriodExpiredRepository {
	
	static final String SQL_SELECT_USER_IN_PERIOD = "  SELECT [USER].USER_ID,USERNAME,EMAIL,APP.APP_ID,APP_ROLE_NAME,APP_NAME "
			+ ",START_TIME,END_TIME "
			+ "FROM USER_APP_ROLE "
			+ "inner join [USER] on [USER].USER_ID  = USER_APP_ROLE.USER_ID "
			+ "inner join APP_ROLE on APP_ROLE.APP_ROLE_ID = USER_APP_ROLE.APP_ROLE_ID "
			+ "inner join APP on APP.APP_ID = APP_ROLE.APP_ID "
			+ "where DATEDIFF(DAY,END_TIME,GETDATE()) in (3,10) and PERIOD_TYPE = '2' " ;

	static final String SQL_SELECT_EXPIRE_TOGEN_TERMINATE = "SELECT USER_APP_ROLE_ID,APP_ROLE_ID,USER_ID FROM USER_APP_ROLE "
			+ "WHERE DATEDIFF(DAY,END_TIME,GETDATE()) <= 0 AND PERIOD_TYPE = '2' " ;
	
	
	//test USER APP ROLE
//	static final String SQL_SELECT_EXPIRE_TOGEN_TERMINATE = "SELECT USER_APP_ROLE_ID,APP_ROLE_ID,USER_ID FROM USER_APP_ROLE ";
			
	

	static final String SQL_DELETE_EXPIRE = "DELETE USER_APP_ROLE WHERE DATEDIFF(DAY,GETDATE(),END_TIME) < 0";
  	
		
	@Select(SQL_SELECT_USER_IN_PERIOD)
	//public Integer findUrClose()  throws RuntimeException;
	public List<UserInPeriod> selectUserInPeriod();
	
	@SelectProvider(type=BatchPeriodExpiredProvider.class,method ="selectExpireToGenTerminate") 
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<UserExpireTerminate> selectExpireToGenTerminate();
	
	@Delete(SQL_DELETE_EXPIRE)
	//public Integer findUrClose()  throws RuntimeException;
	public Integer deleteExpire();
	
	@SelectProvider(type=BatchPeriodExpiredProvider.class,method ="listTypeApp") 
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<String> listTypeApp(@Param("userId") String userId);
	
	@SelectProvider(type=BatchPeriodExpiredProvider.class,method ="listRoleApplication") 
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<String> listRoleApplication(@Param("userId") String userId,@Param("type") String type);
	
//	@SelectProvider(type=BatchPeriodExpiredProvider.class,method ="allApplicationByUser") 
//	@Options(statementType = StatementType.CALLABLE, useCache = true)
//	public List<String> allApplicationByUser(@Param("user") User user,@Param("type")String type, @Param("date") Date date);
	

}

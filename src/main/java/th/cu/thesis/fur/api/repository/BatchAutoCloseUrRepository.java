package th.cu.thesis.fur.api.repository;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import th.cu.thesis.fur.api.model.Application;
import th.cu.thesis.fur.api.model.SystemApproveHistory;
import th.cu.thesis.fur.api.model.UrClose;

public interface BatchAutoCloseUrRepository {

	static final String SQL_SELECT_USER_BY_USERNAME = "SELECT * FROM [USER] WHERE USERNAME = #{username} ";
	
	
	//static final String SQL_SELECT_UR_CLOSE = "SELECT COUNT(*) FROM [UR] ";
	
	static final String SQL_SELECT_UR_CLOSE = "SELECT ur.UR_ID , UR_STEP.UR_STEP_ID "
			+"FROM [UR_STEP] "
			+"inner join UR on UR.UR_ID = UR_STEP.UR_ID "
			+"Where UR_STEP = #{urStep} and UR_STEP.STATUS = #{urStepStatus} and datediff(day,UR_STEP.updated_date,Getdate())>= #{urDay} and ur.STATUS = #{urStatus}";
	
	static final String SQL_INSERT_SYSTEM_APPROVE_HISTORY = "INSERT INTO [UR_APPROVE_HISTORY] "
			+ "([UR_APPROVE_HISTORY_ID],[UR_ID],[UR_STEP_ID]"
			+ ",[STATUS],[APPROVE_TIME],[REMARK],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY]"
			+ ",[PINCODE],[ENNAME],[ENSURNAME],[USERNAME],[EMAIL],[PHONE],[MOBILE],[TEAM_NAME])"
			+ " VALUES"
			+ " (#{urApproveHistoryId}"
			+ " ,#{urId}"
			+ " ,#{urStepId}"
			+ " ,#{status}"
			+ " ,GETDATE()"
			+ " ,#{remark}"
			+ " ,GETDATE()"
			+ " ,#{createdBy}"
			+ " ,GETDATE()"
			+ " ,#{updatedBy}"
			+ " ,#{pincode}"
			+ " ,#{enname}"
			+ " ,#{ensurname}"
			+ " ,#{username}"
			+ " ,#{email}"
			+ " ,#{phone}"
			+ " ,#{mobile}"
			+ " ,#{teamName})";


	static final String SQL_UPDATE_UR_STEP_COMPLETE = "UPDATE UR_STEP SET "
			+ "STATUS = #{status}"
			+ ",UPDATED_DATE = GETDATE()"
			+ ",UPDATED_BY = #{updatedBy} "
			+ "WHERE UR_STEP_ID = #{urStepId}";


	static final String SQL_UPDATE_UR_COMPLETE = "UPDATE UR SET "
			+ "STATUS = #{status}"
			+ ",UPDATED_DATE = GETDATE()"
			+ ",UPDATED_BY = #{updatedBy} "
			+ "WHERE UR_ID = #{urId}";


	static final String SQL_UPDATE_UR_FOR_USER_COMPLETE = "UPDATE UR_FOR_USER SET "
			+ "UR_STATUS = #{status}"
			+ ",UPDATED_DATE = GETDATE()"
			+ ",UPDATED_BY = #{updatedBy} "
			+ "WHERE UR_ID = #{urId}";
	
	
	
	

//	insert into UR_APPROVE_HISTORY 
//	(UR_APPROVE_HISTORY_ID,UR_ID,UR_STEP_ID,STATUS,APPROVE_TIME,REMARK,CREATED_DATE,CREATED_BY,UPDATED_DATE,UPDATED_BY,PINCODE,ENNAME,ENSURNAME,USERNAME,EMAIL,PHONE,MOBILE)
//	values ('1234','20161114091100001-001','1896fcb4-7ed2-4623-ac5d-eaac66a22721','1',GETDATE(),'SYSTEM AUTO CLOSE',GETDATE(),'SYSTEM_ACIM',GETDATE(),'SYSTEM_ACIM','','','','','','','')
	
//	SELECT ur.UR_ID , UR_STEP.UR_STEP 
//	FROM [AcimDB].[acim].[UR_STEP]
//  inner join UR on UR.UR_ID = UR_STEP.UR_ID
//  Where UR_STEP = 6 and UR_STEP.STATUS = 1 and datediff(day,UR_STEP.updated_date,Getdate())>=10 and ur.STATUS =1
	
	
//	insert into UR_APPROVE_HISTORY 
//	(UR_APPROVE_HISTORY_ID,UR_ID,UR_STEP_ID,STATUS,APPROVE_TIME,REMARK,CREATED_DATE,CREATED_BY,UPDATED_DATE,UPDATED_BY,PINCODE,ENNAME,ENSURNAME,USERNAME,EMAIL,PHONE,MOBILE)
//	values ('1234','20161114091100001-001','1896fcb4-7ed2-4623-ac5d-eaac66a22721','1',GETDATE(),'SYSTEM AUTO CLOSE',GETDATE(),'SYSTEM_ACIM',GETDATE(),'SYSTEM_ACIM','','','','','','','') 
	
//	update UR_STEP set STATUS = '2',UPDATED_DATE = GETDATE(),UPDATED_BY = 'SYSTEM_ACIM' where UR_STEP_ID = '1896fcb4-7ed2-4623-ac5d-eaac66a22721'
	
	// update UR set STATUS = '2',UPDATED_DATE = GETDATE(),UPDATED_BY = 'SYSTEM_ACIM' where UR_ID = '20161114091100001-001'
	 

	
	@Select(SQL_SELECT_USER_BY_USERNAME)
	public String selectUserByUsername(@Param("username") String username)  throws RuntimeException;
	
	
	@Insert(SQL_INSERT_SYSTEM_APPROVE_HISTORY)
	public Integer insertSystemApproveHistory(SystemApproveHistory systemApproveHistory) throws RuntimeException;
	
	
	@Select(SQL_SELECT_UR_CLOSE)
	//public Integer findUrClose()  throws RuntimeException;
	public List<UrClose> selectUrClose(@Param("urStep") String urStep, @Param("urStepStatus") String urStepStatus,  @Param("urDay") String urDay,  @Param("urStatus") String urStatus)  throws RuntimeException;

	@Update(SQL_UPDATE_UR_STEP_COMPLETE)
	public Integer updateUrStep(@Param("status") String status, @Param("updatedBy") String updatedBy,  @Param("urStepId") String urStepId)  throws RuntimeException;

	@Update(SQL_UPDATE_UR_COMPLETE)
	public Integer updateUr(@Param("status") String status, @Param("updatedBy") String updatedBy,  @Param("urId") String urId)  throws RuntimeException;

	@Update(SQL_UPDATE_UR_FOR_USER_COMPLETE)
	public Integer updateUrForUser(@Param("status") String status, @Param("updatedBy") String updatedBy,  @Param("urId") String urId)  throws RuntimeException;
}

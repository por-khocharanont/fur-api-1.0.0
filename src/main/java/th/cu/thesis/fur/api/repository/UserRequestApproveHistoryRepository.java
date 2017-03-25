package th.cu.thesis.fur.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.UrApproveHistory;

public interface UserRequestApproveHistoryRepository {
	
	static final String SQL_INSERT_UR_APPROVE_HISTORY = "INSERT INTO [UR_APPROVE_HISTORY] "
			+ "([UR_APPROVE_HISTORY_ID] "
			+ ",[UR_STEP_ID]"
			+ ",[UR_ID]"
			+ ",[PINCODE]"
			+ ",[ENNAME]"
			+ ",[ENSURNAME]"
			+ ",[USERNAME]"
			+ ",[EMAIL]"
			+ ",[PHONE]"
			+ ",[MOBILE]"
			+ ",[STATUS]"
			+ ",[APPROVE_TIME]"
			+ ",[APPROVE_FILE]"
			+ ",[REMARK]"
			+ ",[CREATED_DATE]"
			+ ",[CREATED_BY]"
			+ ",[UPDATED_DATE]"
			+ ",[UPDATED_BY]"
			+ ",[TEAM_NAME]) VALUES (#{urApproveHistoryId},#{urStepId},#{urId},#{pincode},#{enname},#{ensurname},#{username}"
			+ ",#{email},#{phone},#{mobile},#{status},GETDATE(),#{approveFile},#{remark}"
			+ ",GETDATE(),#{createdBy},GETDATE(),#{updatedBy},#{teamName})";
	
	static final String SQL_SELECT_UR_APPROVE_HISTORY_BY_UR_STEP_ID = "SELECT * FROM UR_APPROVE_HISTORY WHERE UR_STEP_ID = #{urStepId} ORDER BY APPROVE_TIME ASC";
	
	@Insert(SQL_INSERT_UR_APPROVE_HISTORY)
	public void create(UrApproveHistory urApproveHistory) throws RuntimeException;
	
	@Select(SQL_SELECT_UR_APPROVE_HISTORY_BY_UR_STEP_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<UrApproveHistory> getListUrApproveHistoryByUrStepId(@Param("urStepId") String urStepId) throws RuntimeException;

}

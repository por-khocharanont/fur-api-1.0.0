package th.cu.thesis.fur.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.UrStepApprove;

public interface UserRequestStepApproveRepository {
	
	static final String SQL_SELECT_UR_STEP_APPROVE_BY_UR_STEP_ID_AND_USER_ID = "SELECT * FROM UR_STEP_APPROVE WHERE UR_STEP_ID = #{urStepId} AND USERNAME = #{username} ORDER BY SEQUENCE_TEAM ASC, SEQUENCE ASC;";
	
	static final String SQL_SELECT_UR_STEP_APPROVE_BY_UR_STEP_ID = "SELECT * FROM UR_STEP_APPROVE WHERE UR_STEP_ID = #{urStepId} ORDER BY SEQUENCE_TEAM ASC, SEQUENCE ASC";
	
	static final String SQL_SELECT_UR_STEP_APPROVE_BY_UR_STEP_ID_AND_SEQUENCE = "SELECT * FROM UR_STEP_APPROVE WHERE UR_STEP_ID = #{urStepId} "
			+ "AND (SEQUENCE = #{sequence} OR SEQUENCE_TEAM = #{sequence}) ORDER BY SEQUENCE_TEAM ASC, SEQUENCE ASC;";
	
	@Select(SQL_SELECT_UR_STEP_APPROVE_BY_UR_STEP_ID_AND_USER_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public UrStepApprove getUrStepApproveByUrStepIdAndUsername(@Param("urStepId") String urStepId,@Param("username") String username) throws RuntimeException;

	@Select(SQL_SELECT_UR_STEP_APPROVE_BY_UR_STEP_ID)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<UrStepApprove> getListUrStepApproveByUrStepId(@Param("urStepId") String urStepId) throws RuntimeException;
	
	@Select(SQL_SELECT_UR_STEP_APPROVE_BY_UR_STEP_ID_AND_SEQUENCE)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<UrStepApprove> getListUrStepApproveByUrStepIdAndSequence(@Param("urStepId") String urStepId,@Param("sequence") String sequence) throws RuntimeException;


}

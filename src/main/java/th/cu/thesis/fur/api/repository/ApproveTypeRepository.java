package th.cu.thesis.fur.api.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.ApproveType;

public interface ApproveTypeRepository {
	
	static final String SQL_DELETE_BY_APP_ID = "DELETE FROM [APPROVE_TYPE] WHERE APP_ID = #{appId} AND TYPE = #{type}";
	
	static final String SQL_INSERT = "INSERT INTO [APPROVE_TYPE] ([APPROVE_TYPE_ID],[APP_ID],[TYPE],[APPROVE_TYPE],[MINIMUM_APPROVE],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY])"+
			" VALUES"+
			" (#{approveTypeId}"+
			" ,#{appId}"+
			" ,#{type}"+
			" ,#{approveType}"+
			" ,#{minimumApprove}"+
			" ,GETDATE()"+
			" ,#{createdBy}"+
			" ,GETDATE()"+
			" ,#{updatedBy})";
	
	static final String SQL_SELECT_APPROVE_TYPE_BY_APP_ID_AND_TYPE = "SELECT * FROM [APPROVE_TYPE] WHERE APP_ID = #{appId} AND TYPE = #{type}";
	
	@Delete(SQL_DELETE_BY_APP_ID)
	public void deleteByAppIdAndType(@Param("appId") String appId,@Param("type") String type) throws RuntimeException;
	
	@Insert(SQL_INSERT)
	public Integer create(ApproveType approveType) throws RuntimeException;
	
	@Select(SQL_SELECT_APPROVE_TYPE_BY_APP_ID_AND_TYPE)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public ApproveType getApproveTypeByAppIdAndType(@Param("appId") String appId,@Param("type") String type) throws RuntimeException;
}


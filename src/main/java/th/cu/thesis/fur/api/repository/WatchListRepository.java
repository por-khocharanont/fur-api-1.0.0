package th.cu.thesis.fur.api.repository;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.UrApproveHistory;
import th.cu.thesis.fur.api.repository.model.WatchList;
import th.cu.thesis.fur.api.util.WatchListSqlProvider;

public interface WatchListRepository {
	
	static final String SQL_SELECT_UR_STEP_CLOSE_BY_UR_ID ="SELECT UR_STEP_ID FROM UR_STEP WHERE UR_ID = #{urId} AND UR_STEP = #{urStep}";
			
			
	static final String SQL_SELECT_CHK_CLOSE_UR = "SELECT UR_STEP_ID FROM UR "+
												"LEFT OUTER JOIN REQUEST_NO ON REQUEST_NO.[REQUEST_NO] = UR.[REQUEST_NO] "+
												"LEFT OUTER JOIN UR_STEP ON UR_STEP.[UR_ID] = UR.[UR_ID] "+
												"LEFT OUTER JOIN UR_FOR_USER ON UR_FOR_USER.[UR_ID] = UR.[UR_ID] "+
												"WHERE UR.[UR_ID] = #{urId} "+
												"AND UR.[STATUS] = '1' "+
												"AND REQUEST_NO.[USERNAME] = #{username} "+ 
												"AND UR_STEP.[STATUS] = '1' "+
												"AND UR_STEP.[UR_STEP] = '6' "+
												"AND UR_FOR_USER.[UR_STATUS] = '1' ";
	
	static final String SQL_INSERT_UR_APPROVE_HISTORY = "INSERT INTO UR_APPROVE_HISTORY "+
											           "([UR_APPROVE_HISTORY_ID],[UR_STEP_ID],[UR_ID],[PINCODE],[ENNAME],[ENSURNAME],[USERNAME],[EMAIL],[PHONE],[MOBILE],[STATUS],[APPROVE_TIME],[APPROVE_FILE],[REMARK],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY],[TEAM_NAME]) "+
											            " VALUES "+
											                  "(#{urApproveHistoryId} "+
											                  ",#{urStepId} "+
											                   ",#{urId} "+
											                   ",#{pincode} "+
											                   ",#{enname} "+
											                   ",#{ensurname} "+
											                   ",#{username} "+
											                   ",#{email} "+
											                   ",#{phone} "+
											                   ",#{mobile} "+
											                   ",#{status} "+
											                   ",GETDATE() "+
											                   ",#{approveFile} "+
											                   ",#{remark} "+
											                   ",GETDATE() "+
											                   ",#{createdBy} "+
											                   ",GETDATE() "+
											                   ",#{updatedBy} "+
											                   ",#{teamName} "+
											        		   ") ";
	
	static final String SQL_CLOSE_UR = "UPDATE [UR_FOR_USER] "+
									   "SET "+ 
									   "[UR_STATUS] = '2' "+
									   ",[UPDATED_DATE] = GETDATE() "+
									   ",[UPDATED_BY] = #{username} "+ 
									  "WHERE [UR_ID] = #{urId} "+
									  "AND   [UR_STATUS] = '1'; "+

									  "UPDATE [UR] "+
									  "SET  "+
										  "[STATUS] = '2' "+
									     ",[UPDATED_DATE] = GETDATE() "+
									     ",[UPDATED_BY] = #{username}  "+
									  "WHERE [UR_ID] = #{urId} ";
	
	
	@SelectProvider(type = WatchListSqlProvider.class, method="countTotalWatchList")
	public int countTotalWatchList(@Param("userRequestNo") String userRequestNo, @Param("urNo") String urNo,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("urStep") String urStep, @Param("urStatus") String urStatus, @Param("username") String username) throws RuntimeException;
	
	
	@SelectProvider(type = WatchListSqlProvider.class, method="getWatchList")
	@Options(statementType = StatementType.CALLABLE)
	public List<WatchList> findWatchList(@Param("userRequestNo") String userRequestNo, @Param("urNo") String urNo,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("urStep") String urStep, @Param("urStatus") String urStatus, @Param("username") String username,
			@Param("startRow") int startRow,@Param("endRow") int endRow,  @Param("orderBy") String orderBy) throws RuntimeException;
	
	
	@Select(SQL_SELECT_CHK_CLOSE_UR)
	public List<String> checkCloseUr(@Param("urId") String urNo, @Param("username") String username) throws RuntimeException;
	
	@Insert(SQL_INSERT_UR_APPROVE_HISTORY)
	public Integer createUrApproveHistory(UrApproveHistory urApproveHistory) throws RuntimeException;
	
	@Update(SQL_CLOSE_UR)
	public Integer closeUrComplete(@Param("urId") String urNo, @Param("username") String username) throws RuntimeException;

	
	@Select(SQL_SELECT_UR_STEP_CLOSE_BY_UR_ID)
	public String selectCloseUrStepByUrId(@Param("urId") String urId,@Param("urStep") String urStep) throws RuntimeException;
	
}

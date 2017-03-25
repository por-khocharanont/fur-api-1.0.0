package th.cu.thesis.fur.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.StatementType;

import th.cu.thesis.fur.api.model.Organize;
import th.cu.thesis.fur.api.repository.model.Eligible;
import th.cu.thesis.fur.api.repository.model.EligibleOrgAppRoleApp;
import th.cu.thesis.fur.api.util.EligibleSqlProvider;

public interface EligibleRepository {
	
	static final String SQL_DELETE_BY_APP_ID_AND_ORG_CODE = "DELETE FROM [ELIGIBLE] WHERE ORGCODE = #{orgCode} AND "
			+ "APP_ROLE_ID IN (SELECT APP_ROLE_ID FROM [APP_ROLE] WHERE APP_ID = #{appId})";
	
	static final String SQL_SELECT_ORGANIZE_BY_APP_ID = "SELECT * FROM [ORGANIZE] WHERE ORGCODE IN ("
			+ "SELECT ORGCODE FROM ELIGIBLE ELI WHERE ELI.APP_ROLE_ID IN ("
			+ "SELECT APP_ROLE_ID FROM APP_ROLE WHERE APP_ID = #{appId}) ) ";
	
	static final String SQL_SELECT_APP_ROLE_NAME_BY_APP_NAME_AND_ORGCODE = "SELECT * FROM ELIGIBLE ELI "
			+ "JOIN APP_ROLE [ROLE] ON ELI.APP_ROLE_ID = [ROLE].APP_ROLE_ID "
			+ "WHERE ELI.APP_ROLE_ID IN ( "
			+ "SELECT APP_ROLE_ID FROM APP_ROLE WHERE APP_ID = #{appId}) AND ORGCODE = #{orgCode} ORDER BY ELI.CREATED_DATE";
	
	static final String SQL_SELECT_ORGANIZE_BY_ORGCODE = "SELECT * FROM [ORGANIZE] WHERE ORGCODE LIKE '%'+#{orgCode}+'%' ";
	
	static final String SQL_COUNT_ORGANIZE_BY_ORGCODE = "SELECT COUNT(*) FROM [ORGANIZE] WHERE ORGCODE = #{orgCode} ";
	
	static final String SQL_SELECT_ORGANIZE_BY_ORGNAME = "SELECT * FROM [ORGANIZE] WHERE ORGNAME LIKE '%'+#{orgName}+'%' ";
	
	static final String SQL_SELECT_ORGANIZE_BY_ORGDESC = "SELECT * FROM [ORGANIZE] WHERE ORGDESC LIKE '%'+#{orgDesc}+'%' ";
	
	static final String SQL_INSERT = "INSERT INTO [ELIGIBLE] ([ELIGIBLE_ID],[APP_ROLE_ID],[ORGCODE],[CREATED_DATE],[CREATED_BY],[UPDATED_DATE],[UPDATED_BY])"+
									" VALUES"+
									" (#{eligibleId, jdbcType=VARCHAR}"+
									" ,#{appRoleId, jdbcType=VARCHAR}"+
									" ,#{orgcode, jdbcType=VARCHAR}"+
									" ,GETDATE()"+
									" ,#{createdBy, jdbcType=VARCHAR}"+
									" ,GETDATE()"+
									" ,#{updatedBy, jdbcType=VARCHAR})";
	
	static final String SQL_DELETE_BY_ID = "DELETE FROM [ELIGIBLE] WHERE ELIGIBLE_ID = #{eligibleId} ";
	
	static final String SQL_COUNT_BY_APPROLEID_AND_ORGCODE = "SELECT COUNT(*) FROM [ELIGIBLE] WHERE APP_ROLE_ID = #{appRoleId} AND ORGCODE = #{orgCode} ";

	static final String SQL_SELECT_ELIGIBLE_BY_APP_ROLE_ID = "SELECT * FROM [ELIGIBLE] ELI "
									+ "JOIN [ORGANIZE] ORG ON ELI.ORGCODE = ORG.ORGCODE "
									+ "JOIN [APP_ROLE] ROLE ON ELI.APP_ROLE_ID = ROLE.APP_ROLE_ID "
									+ "WHERE ELI.APP_ROLE_ID = #{appRoleId}";
							
	@SelectProvider(type = EligibleSqlProvider.class, method="countTotalEligible")
	public int countTotalEligible(@Param("orgCode") String orgCode, @Param("orgName") String orgName, @Param("orgDesc") String orgDesc,
			@Param("appId") String appId, @Param("appName") String appName, @Param("appType") String appType,
			@Param("appRoleId") String appRoleId, @Param("appRoleName") String appRoleName) throws RuntimeException;
	
	
	@SelectProvider(type = EligibleSqlProvider.class, method="getEligibleList")
	@Options(statementType = StatementType.CALLABLE)
	public List<EligibleOrgAppRoleApp> findEligibleList(@Param("orgCode") String orgCode, @Param("orgName") String orgName, @Param("orgDesc") String orgDesc,
			@Param("appId") String appId, @Param("appName") String appName, @Param("appType") String appType,
			@Param("appRoleId") String appRoleId, @Param("appRoleName") String appRoleName,
			@Param("startRow") int startRow,@Param("endRow") int endRow,  @Param("orderBy") String orderBy) throws RuntimeException;
	
	@Select(SQL_SELECT_ORGANIZE_BY_ORGCODE)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<Organize> findOrganizeByOrgCode(@Param("orgCode") String orgCode)  throws RuntimeException;
	
	@Select(SQL_COUNT_ORGANIZE_BY_ORGCODE)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public Integer countOrganizeByOrgCode(@Param("orgCode") String orgCode) throws RuntimeException;
	
	@Select(SQL_SELECT_ORGANIZE_BY_ORGNAME)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<Organize> findOrganizeByOrgName(@Param("orgName") String orgName)  throws RuntimeException;
	
	@Select(SQL_SELECT_ORGANIZE_BY_ORGDESC)
	@Options(statementType = StatementType.CALLABLE, useCache = true)
	public List<Organize> findOrganizeByOrgDesc(@Param("orgDesc") String orgDesc)  throws RuntimeException;
	
	@Insert(SQL_INSERT)
	public Integer create(Eligible eligible) throws RuntimeException;
	
	@Select(SQL_COUNT_BY_APPROLEID_AND_ORGCODE)
	public Integer countByAppRleIdAndOrgCode(@Param("appRoleId") String appRoleId, @Param("orgCode") String orgCode) throws RuntimeException;
	
	@Delete(SQL_DELETE_BY_ID)
	public Integer deleteById(@Param("eligibleId") String eligibleId)  throws RuntimeException;

	@Delete(SQL_DELETE_BY_APP_ID_AND_ORG_CODE)
	public Integer deleteByAppIdAndOrgCode(@Param("appId") String appId,@Param("orgCode") String orgCode)  throws RuntimeException;
	
	@Select(SQL_SELECT_ELIGIBLE_BY_APP_ROLE_ID)
	public List<Eligible> getEligibleByAppRoleId(@Param("appRoleId") String appRoleId) throws RuntimeException;
	
	@Select(SQL_SELECT_ORGANIZE_BY_APP_ID)
	public List<Organize> getOrganizeListByAppId(@Param("appId") String appId) throws RuntimeException;

	@Select(SQL_SELECT_APP_ROLE_NAME_BY_APP_NAME_AND_ORGCODE)
	public List<Eligible> getAppRoleNameByAppIdAndOrgcode(@Param("appId") String appId,@Param("orgCode") String orgCode) throws RuntimeException;

	
}

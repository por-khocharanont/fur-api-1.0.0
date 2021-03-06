package th.cu.thesis.fur.api.repository;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import th.cu.thesis.fur.api.model.OrgFromAcim;


public interface BatchUpdateOrgRepository {

	static final String SQL_SELECT_ORGANIZE_ORGCODE = "SELECT * FROM ORGANIZE Where ORGCODE = #{orgcode}";
	
	static final String SQL_UPDATE_ORGANIZE = "UPDATE ORGANIZE SET "
			+ "ORGNAME = #{orgname}"
			+ ",ORGDESC = #{orgdesc} "
			+ ",ORGLEVEL = #{orglevel} "
			+ ",HIGHERORG = #{higherorg} "
			+ ",HIGHERORGNAME= #{higherorgname}  "
			+ ",HIGHERORGDESC = #{higherorgdesc} "
			+ ",HIGHERORGLEVEL = #{higherorglevel} "
			+ ",ORG_TYPE = #{orgType} "
			+ "WHERE ORGCODE = #{orgcode}";
	
	static final String SQL_UPDATE_ORGANIZE_ORG_TYPE = "UPDATE ORGANIZE SET "
			+ "ORG_TYPE = #{orgType} "
			+ ",UPDATED_DATE = GETDATE() "
			+ "WHERE ORGCODE = #{orgcode}";
	
	static final String SQL_INSERT_ORGANIZE = "INSERT INTO ORGANIZE "
			+ "([ORGCODE],[ORGNAME],[ORGDESC],[ORGLEVEL],[HIGHERORG],[HIGHERORGNAME] "
			+ ",[HIGHERORGDESC],[HIGHERORGLEVEL],[ORG_TYPE],[CREATED_DATE],[CREATED_BY]"
			+ ",[UPDATED_DATE],[UPDATED_BY]) "
			+ " VALUES"
			+ " (#{orgcode}"
			+ " ,#{orgname}"
			+ " ,#{orgdesc}"
			+ " ,#{orglevel}"
			+ " ,#{higherorg}"
			+ " ,#{higherorgname}"
			+ " ,#{higherorgdesc}"
			+ " ,#{higherorglevel}"
			+ " ,#{orgType}"
			+ " ,GETDATE() "
			+ " ,'SYSTEM-ADMIN' "
			+ " ,GETDATE() "
			+ " ,'SYSTEM-ADMIN') ";
	
	
	@Select(SQL_SELECT_ORGANIZE_ORGCODE)
	//public Integer findUrClose()  throws RuntimeException;
	public OrgFromAcim selectOrg(@Param("orgcode") String orgcode);
	
	@Update(SQL_UPDATE_ORGANIZE)
	//public Integer findUrClose()  throws RuntimeException;
	public Integer UpdateOrganize(OrgFromAcim orgFromAcim);
	
	@Insert(SQL_INSERT_ORGANIZE)
	//public Integer findUrClose()  throws RuntimeException;
	public Integer InsertOrganize(OrgFromAcim orgFromAcim);
	
	@Update(SQL_UPDATE_ORGANIZE_ORG_TYPE)
	//public Integer findUrClose()  throws RuntimeException;
	public Integer UpdateOrganizeOrgType(@Param("orgcode") String orgcode, @Param("orgType") String orgType);
	
	

}

package th.cu.thesis.fur.api.repository;

import org.apache.ibatis.annotations.Update;

import th.cu.thesis.fur.api.model.OrgFromAcim;
import th.cu.thesis.fur.api.rest.model.om.Employee;

public interface BatchUpdateAllUserRepository {
	
	static final String SQL_UPDATE_ALL_USER = "UPDATE [USER] SET "
			+ "[USERNAME] = #{userName} "
			+ ",[EMAIL] = #{email} "
			+ ",[ENNAME] = #{enName} "
			+ ",[ENSURNAME] = #{enSurName} "
			+ ",[ENFULLNAME] = #{enFullName} "
			+ ",[THNAME] = #{thName} "
			+ ",[THSURNAME] = #{thSurName} "
			+ ",[ORGCODE] = #{orgCode} "
			+ ",[ORGNAME] = #{orgName} "
			+ ",[ORGDESC] = #{orgDesc} "
			+ ",[ORGLEVEL] = #{orgLevel} "
			+ ",[FCCODE] = #{fcCode} "
			+ ",[FCNAME] = #{fcName} "
			+ ",[FCDESC] = #{fcDesc} "
			+ ",[FCHCODE] = #{fchCode} "
			+ ",[FCHNAME] = #{fchName} "
			+ ",[FCHDESC] = #{fchDesc} "
			+ ",[SCCODE] = #{scCode} "
			+ ",[SCNAME] = #{scName} "
			+ ",[SCDESC] = #{scDesc} "
			+ ",[SCHCODE] = #{schCode} "
			+ ",[SCHNAME] = #{schName} "
			+ ",[SCHDESC] = #{schDesc} "
			+ ",[DPCODE] = #{dpCode} "
			+ ",[DPNAME] = #{dpName} "
			+ ",[DPDESC] = #{dpDesc} "
			+ ",[DPHCODE] = #{dphCode} "
			+ ",[DPHNAME] = #{dphName} "
			+ ",[DPHDESC] = #{dphDesc} "
			+ ",[BUCODE] = #{buCode} "
			+ ",[BUNAME] = #{buName} "
			+ ",[BUDESC] = #{buDesc} "
			+ ",[BUHCODE] = #{buhCode} "
			+ ",[BUHNAME] = #{buhName} "
			+ ",[BUHDESC] = #{buhDesc} "
			+ ",[COCODE] = #{coCode} "
			+ ",[CONAME] = #{coName} "
			+ ",[PHONE] = #{phone} "
			+ ",[MOBILE] = #{mobile} "
			+ ",[POSITION_ID] = #{positionId} "
			+ ",[POSITION] = #{position} "
			+ ",[MANAGER_PIN] = #{managerPin} "
			+ ",[MANAGER_USERNAME] = #{managerUserName} "
			+ ",[MANAGER_EMAIL] = #{managerEmail} "
			+ ",[MANAGER_ENNAME] = #{managerEnName} "
			+ ",[MANAGER_ENSURNAME] = #{managerEnSurName} "
			+ ",[UPDATED_DATE] = GETDATE() "
			+ ",[UPDATED_BY]  = 'SYSTEM-ADMIN' "
			+ ",[THFULLNAME] =  #{thFullName} "
			+ "WHERE [PINCODE] = #{pin}";

	@Update(SQL_UPDATE_ALL_USER)
	//public Update findUrClose()  throws RuntimeException;
	public Integer UpdateAllUser(Employee employeeList);

}

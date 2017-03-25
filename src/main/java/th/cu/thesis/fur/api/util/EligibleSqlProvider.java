package th.cu.thesis.fur.api.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class EligibleSqlProvider {
	
	public String countTotalEligible(Map<String, Object> params) {
		
		String sql = "";
		
		String orgCode = (String) params.get("orgCode");
		String orgName = (String) params.get("orgName");
		String orgDesc = (String) params.get("orgDesc");
		String appId = (String) params.get("appId");
		String appName = (String) params.get("appName");
		String appRoleId = (String) params.get("appRoleId");
		String appRoleName = (String) params.get("appRoleName");
		String appType = (String) params.get("appType");
		
		sql = "SELECT COUNT(*) FROM [ELIGIBLE] eg "+
				"INNER JOIN [ORGANIZE] org ON eg.[ORGCODE] = org.[ORGCODE] "+
				"INNER JOIN [APP_ROLE] ar ON eg.[APP_ROLE_ID] = ar.[APP_ROLE_ID] "+
				"INNER JOIN [APP] ap ON ar.[APP_ID] = ap.[APP_ID] "+
				"WHERE eg.[ELIGIBLE_ID] = eg.[ELIGIBLE_ID] ";
		
		if(StringUtils.isNotEmpty(orgCode)){
			sql += "AND   org.[ORGCODE] LIKE #{orgCode} ";
		}
		
		if(StringUtils.isNotEmpty(orgName)){
			sql += "AND   org.[ORGNAME] LIKE #{orgName} ";
		}
		
		if(StringUtils.isNotEmpty(orgDesc)){
			sql += "AND   org.[ORGDESC] LIKE #{orgDesc} ";
		}
		
		if(StringUtils.isNotEmpty(appId)){
			sql += "AND   ap.[APP_ID] = #{appId}";
		}
		
		if(StringUtils.isNotEmpty(appName)){
			sql += "AND   ap.[APP_NAME] LIKE #{appName} ";
		}
		
		if(StringUtils.isNotEmpty(appType)){
			sql += "AND   ap.[TYPE] = #{appType} ";
		}
		
		if(StringUtils.isNotEmpty(appRoleId)){
			sql += "AND   ar.[APP_ROLE_ID] = #{appRoleId} ";
		}
		
		if(StringUtils.isNotEmpty(appRoleName)){
			sql += "AND   ar.[APP_ROLE_NAME] LIKE #{appRoleName}";
		}
			
		return sql;
		
		
	}
	
	public String getEligibleList(Map<String, Object> params) {
		
		String sql = "";
		
		String orgCode = (String) params.get("orgCode");
		String orgName = (String) params.get("orgName");
		String orgDesc = (String) params.get("orgDesc");
		String appId = (String) params.get("appId");
		String appName = (String) params.get("appName");
		String appRoleId = (String) params.get("appRoleId");
		String appRoleName = (String) params.get("appRoleName");
		String appType = (String) params.get("appType");
		String orderBy = (String) params.get("orderBy");
		
		sql = "SELECT * FROM ( "+
				"SELECT ROW_NUMBER() OVER(ORDER BY "+orderBy+" ) AS Row, eg.[ELIGIBLE_ID], org.[ORGCODE], org.[ORGNAME], org.[ORGDESC], ap.[APP_NAME], ap.[TYPE], ar.[APP_ROLE_NAME]  FROM [ELIGIBLE] eg "+
				"INNER JOIN [ORGANIZE] org ON eg.[ORGCODE] = org.[ORGCODE] "+
				"INNER JOIN [APP_ROLE] ar ON eg.[APP_ROLE_ID] = ar.[APP_ROLE_ID] "+
				"INNER JOIN [APP] ap ON ar.[APP_ID] = ap.[APP_ID] "+
				"WHERE eg.[ELIGIBLE_ID] = eg.[ELIGIBLE_ID] ";
		
		
		if(StringUtils.isNotEmpty(orgCode)){
			sql += "AND   org.[ORGCODE] LIKE #{orgCode} ";
		}
		
		if(StringUtils.isNotEmpty(orgName)){
			sql += "AND   org.[ORGNAME] LIKE #{orgName} ";
		}
		
		if(StringUtils.isNotEmpty(orgDesc)){
			sql += "AND   org.[ORGDESC] LIKE #{orgDesc} ";
		}
		
		if(StringUtils.isNotEmpty(appId)){
			sql += "AND   ap.[APP_ID]   = #{appId}";
		}
		
		if(StringUtils.isNotEmpty(appName)){
			sql += "AND   ap.[APP_NAME] LIKE #{appName} ";
		}
		
		if(StringUtils.isNotEmpty(appType)){
			sql += "AND   ap.[TYPE] = #{appType} ";
		}
		
		if(StringUtils.isNotEmpty(appRoleId)){
			sql += "AND   ar.[APP_ROLE_ID] = #{appRoleId} ";
		}
		
		if(StringUtils.isNotEmpty(appRoleName)){
			sql += "AND   ar.[APP_ROLE_NAME] LIKE #{appRoleName}";
		}
		
		sql += ") as eligible WHERE eligible.Row between #{startRow} and #{endRow} ";
		
		return sql;
		
	}
	

}

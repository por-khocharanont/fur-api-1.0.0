package th.cu.thesis.fur.api.util;

import java.util.Map;

public class BatchUserReSignProvider {
	public String listTypeApplication(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("				SELECT APP.TYPE");
		stringBuilder.append("				FROM APP ");
		stringBuilder.append("				INNER JOIN APP_ROLE ON  APP.APP_ID = APP_ROLE.APP_ID ");
		stringBuilder.append("				INNER JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID = APP_ROLE.APP_ROLE_ID  ");
		stringBuilder.append("					AND USER_APP_ROLE.USER_ID = (SELECT USER_ID FROM [USER] WHERE USERNAME= #{userName})  ");
		stringBuilder.append("				GROUP BY APP.TYPE  ");
		return stringBuilder.toString();
	}
	public String allApplicationByUser (Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT USER_APP_ROLE.APP_ROLE_ID ");
		stringBuilder.append("FROM USER_APP_ROLE ");
		stringBuilder.append("WHERE USER_APP_ROLE.USER_ID = ( ");
		stringBuilder.append("									SELECT USER_ID ");
		stringBuilder.append("									FROM [USER] ");
		stringBuilder.append("									WHERE PINCODE= #{user.pincode}) 	");
		stringBuilder.append("									AND (SELECT TYPE FROM APP WHERE APP.APP_ID=(SELECT APP_ID FROM APP_ROLE WHERE APP_ROLE.APP_ROLE_ID=USER_APP_ROLE.APP_ROLE_ID))=#{type} 	");
		return stringBuilder.toString();
	}
	
	public String listEmailAdminAcim (Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT EMAIL ");
		stringBuilder.append("FROM [USER] ");
		stringBuilder.append("INNER JOIN ACIM_ROLE ON [USER].USER_ID=ACIM_ROLE.USER_ID ");
		stringBuilder.append("AND ACIM_ROLE.ROLE_CODE='ADMIN-ACIM' ");		
		return stringBuilder.toString();
	}
}

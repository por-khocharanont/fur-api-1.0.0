package th.cu.thesis.fur.api.util;

public class BatchPeriodExpiredProvider {
	
	public String selectExpireToGenTerminate(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT USER_APP_ROLE_ID,APP_ROLE_ID,USER_ID ");
		stringBuilder.append("FROM USER_APP_ROLE  ");
		stringBuilder.append("WHERE DATEDIFF(DAY,END_TIME,GETDATE()) < 0  ");
		stringBuilder.append("		AND PERIOD_TYPE = '2'  ");
		stringBuilder.append("		AND USER_ID NOT IN ( ");
		stringBuilder.append("				SELECT A.USER_ID ");
		stringBuilder.append("				FROM UR_FOR_USER ");
		stringBuilder.append("				INNER JOIN ( ");
		stringBuilder.append("					SELECT APP_ROLE_ID,USER_ID  ");
		stringBuilder.append("					FROM USER_APP_ROLE  ");
		stringBuilder.append("					WHERE DATEDIFF(DAY,END_TIME,GETDATE()) < 0  AND USER_APP_ROLE.PERIOD_TYPE='2' ");
		stringBuilder.append("				) A ON A.USER_ID = (SELECT USER_ID FROM [USER] WHERE [USER].USERNAME = UR_FOR_USER.USERNAME ) ");
		stringBuilder.append("				INNER JOIN UR ON UR.UR_ID = UR_FOR_USER.UR_ID  AND UR.APP_ROLE_ID=A.APP_ROLE_ID ");
		stringBuilder.append("					AND UR.STATUS='1' AND UR_FOR_USER.UR_STATUS = '1'");
		stringBuilder.append("		) ");
		return stringBuilder.toString();
	}
	
	public String listTypeApp(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("				SELECT APP.TYPE ");
		stringBuilder.append("				FROM APP ");
		stringBuilder.append("				INNER JOIN APP_ROLE ON  APP.APP_ID = APP_ROLE.APP_ID ");
		stringBuilder.append("				INNER JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID = APP_ROLE.APP_ROLE_ID  ");
		stringBuilder.append("					AND USER_APP_ROLE.USER_ID = #{userId} AND DATEDIFF(DAY,USER_APP_ROLE.END_TIME,GETDATE()) < 0 ");
		stringBuilder.append("					AND USER_APP_ROLE.PERIOD_TYPE = '2'  ");
					stringBuilder.append("		AND USER_ID NOT IN ( ");
					stringBuilder.append("				SELECT A.USER_ID ");
					stringBuilder.append("				FROM UR_FOR_USER ");
					stringBuilder.append("				INNER JOIN ( ");
					stringBuilder.append("					SELECT APP_ROLE_ID,USER_ID  ");
					stringBuilder.append("					FROM USER_APP_ROLE  ");
					stringBuilder.append("					WHERE DATEDIFF(DAY,END_TIME,GETDATE()) < 0  AND USER_APP_ROLE.PERIOD_TYPE='2' ");
					stringBuilder.append("				) A ON A.USER_ID = (SELECT USER_ID FROM [USER] WHERE [USER].USERNAME = UR_FOR_USER.USERNAME ) ");
					stringBuilder.append("				INNER JOIN UR ON UR.UR_ID = UR_FOR_USER.UR_ID  AND UR.APP_ROLE_ID=A.APP_ROLE_ID ");
					stringBuilder.append("					AND UR.STATUS='1' AND UR_FOR_USER.UR_STATUS = '1'");
					stringBuilder.append("		) ");
		stringBuilder.append("				GROUP BY APP.TYPE  ");
		return stringBuilder.toString();
	}
	public String listRoleApplication(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("				SELECT APP_ROLE.APP_ROLE_ID ");
		stringBuilder.append("				FROM APP ");
		stringBuilder.append("				INNER JOIN APP_ROLE ON  APP.APP_ID = APP_ROLE.APP_ID ");
		stringBuilder.append("				INNER JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID = APP_ROLE.APP_ROLE_ID  ");
		stringBuilder.append("					AND USER_APP_ROLE.USER_ID = #{userId} AND DATEDIFF(DAY,USER_APP_ROLE.END_TIME,GETDATE()) < 0 ");
		stringBuilder.append("					AND USER_APP_ROLE.PERIOD_TYPE = '2'  ");
					stringBuilder.append("		AND USER_ID NOT IN ( ");
					stringBuilder.append("				SELECT A.USER_ID ");
					stringBuilder.append("				FROM UR_FOR_USER ");
					stringBuilder.append("				INNER JOIN ( ");
					stringBuilder.append("					SELECT APP_ROLE_ID,USER_ID  ");
					stringBuilder.append("					FROM USER_APP_ROLE  ");
					stringBuilder.append("					WHERE DATEDIFF(DAY,END_TIME,GETDATE()) < 0  AND USER_APP_ROLE.PERIOD_TYPE='2' ");
					stringBuilder.append("				) A ON A.USER_ID = (SELECT USER_ID FROM [USER] WHERE [USER].USERNAME = UR_FOR_USER.USERNAME ) ");
					stringBuilder.append("				INNER JOIN UR ON UR.UR_ID = UR_FOR_USER.UR_ID  AND UR.APP_ROLE_ID=A.APP_ROLE_ID ");
					stringBuilder.append("					AND UR.STATUS='1' AND UR_FOR_USER.UR_STATUS = '1'");
					stringBuilder.append("		) ");
		stringBuilder.append("					AND APP.TYPE = #{type} ");
		stringBuilder.append("				GROUP BY APP_ROLE.APP_ROLE_ID  ");
		return stringBuilder.toString();
	}
}

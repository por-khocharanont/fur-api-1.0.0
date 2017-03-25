package th.cu.thesis.fur.api.util;

import java.util.Map;

public class BatchUserReOrgProvider {
	
	public String listTypeApplicationWhenChangeOraganize(Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("					SELECT APP.TYPE ");
		stringBuilder.append("					FROM APP ");
		stringBuilder.append("					INNER JOIN APP_ROLE ON  APP.APP_ID = APP_ROLE.APP_ID ");
		stringBuilder.append("					INNER JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID = APP_ROLE.APP_ROLE_ID ");
		stringBuilder.append(" 					AND USER_APP_ROLE.USER_ID = (SELECT USER_ID FROM [USER] WHERE USERNAME= #{userName})");
		stringBuilder.append(" 					AND (USER_APP_ROLE.PERIOD_TYPE='1' OR (USER_APP_ROLE.PERIOD_TYPE='2' AND CAST(USER_APP_ROLE.END_TIME AS DATE)  >= CAST(#{date} AS DATE ) ) ) ");
		stringBuilder.append(" 				LEFT JOIN  (");
		stringBuilder.append(" 									SELECT APP_ROLE.*");
		stringBuilder.append(" 									FROM APP");
		stringBuilder.append(" 									INNER JOIN APP_ROLE ON  APP.APP_ID = APP_ROLE.APP_ID");
		stringBuilder.append(" 									INNER JOIN ELIGIBLE ON  ELIGIBLE.APP_ROLE_ID = APP_ROLE.APP_ROLE_ID ");
		stringBuilder.append(" 										AND ELIGIBLE.ORGCODE = #{newOrgCode} ");
		stringBuilder.append(" 						) NEW ON NEW.APP_ROLE_ID = APP_ROLE.APP_ROLE_ID  ");
		stringBuilder.append("						WHERE NEW.APP_ROLE_ID IS NULL  ");
		stringBuilder.append("					GROUP BY APP.TYPE");
		return stringBuilder.toString();
	}
	
	public String listTypeApplicationWhenChangeOrgtype(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("				SELECT APP.TYPE");
		stringBuilder.append("				FROM APP ");
		stringBuilder.append("				INNER JOIN APP_ROLE ON  APP.APP_ID = APP_ROLE.APP_ID ");
		stringBuilder.append("				INNER JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID = APP_ROLE.APP_ROLE_ID  ");
		stringBuilder.append("					AND USER_APP_ROLE.USER_ID = (SELECT USER_ID FROM [USER] WHERE USERNAME= #{userName})  ");
		stringBuilder.append("					AND (USER_APP_ROLE.PERIOD_TYPE='1' OR (USER_APP_ROLE.PERIOD_TYPE='2' AND CAST(USER_APP_ROLE.END_TIME AS DATE)  >= CAST(#{date} AS DATE) ) ) ");
		stringBuilder.append("				GROUP BY APP.TYPE  ");
		return stringBuilder.toString();
	}
	
	public String DifferentListApplicationWhenChangeOraganize (Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("					SELECT APP_ROLE.APP_ROLE_ID ");
		stringBuilder.append("					FROM APP ");
		stringBuilder.append("					INNER JOIN APP_ROLE ON  APP.APP_ID = APP_ROLE.APP_ID ");
		stringBuilder.append("					INNER JOIN USER_APP_ROLE ON USER_APP_ROLE.APP_ROLE_ID = APP_ROLE.APP_ROLE_ID ");
		stringBuilder.append(" 					AND USER_APP_ROLE.USER_ID = (SELECT USER_ID FROM [USER] WHERE USERNAME= #{userName})");
		stringBuilder.append(" 					AND (USER_APP_ROLE.PERIOD_TYPE='1' OR (USER_APP_ROLE.PERIOD_TYPE='2' AND CAST(USER_APP_ROLE.END_TIME AS DATE)  >= CAST(#{date} AS DATE ) ) ) ");
		stringBuilder.append(" 				LEFT JOIN  (");
		stringBuilder.append(" 									SELECT APP_ROLE.*");
		stringBuilder.append(" 									FROM APP");
		stringBuilder.append(" 									INNER JOIN APP_ROLE ON  APP.APP_ID = APP_ROLE.APP_ID");
		stringBuilder.append(" 									INNER JOIN ELIGIBLE ON  ELIGIBLE.APP_ROLE_ID = APP_ROLE.APP_ROLE_ID ");
		stringBuilder.append(" 										AND ELIGIBLE.ORGCODE = #{newOrgCode} ");
		stringBuilder.append(" 						) NEW ON NEW.APP_ROLE_ID = APP_ROLE.APP_ROLE_ID  ");
		stringBuilder.append("						WHERE NEW.APP_ROLE_ID IS NULL AND APP.TYPE=#{type} ");
		stringBuilder.append("					GROUP BY APP_ROLE.APP_ROLE_ID ");
		return stringBuilder.toString();
	}
	
	public String clearUrStatusOnProcessByUser (Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("UPDATE UR ");
		stringBuilder.append("SET UR.STATUS='3' , UR.REMARK = 'System Reject UR' ,UR.UPDATED_DATE = CURRENT_TIMESTAMP , UR.UPDATED_BY= 'SYSTEM-ADMIN' ");
		stringBuilder.append("WHERE UR.STATUS='1' AND UR_ID IN ( ");
		stringBuilder.append("						SELECT UR.UR_ID ");
		stringBuilder.append("						FROM REQUEST_NO ");
		stringBuilder.append("						INNER JOIN UR ON REQUEST_NO.REQUEST_NO = UR.REQUEST_NO AND UR.STATUS = 1 ");
		stringBuilder.append("						WHERE REQUEST_NO.REQUEST_BY = #{user.pincode} ");
		stringBuilder.append("						) ");
		return stringBuilder.toString();
	}
	
	public String clearUrForUserStatusOnProcessByUser (Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("UPDATE UR_FOR_USER ");
		stringBuilder.append("SET UR_FOR_USER.UR_STATUS = 3 ,UR_FOR_USER.UPDATED_DATE = CURRENT_TIMESTAMP , UR_FOR_USER.UPDATED_BY= 'SYSTEM-ADMIN' ");
		stringBuilder.append("WHERE UR_FOR_USER.UR_STATUS = 1 AND UR_ID IN ( ");
		stringBuilder.append("						SELECT UR.UR_ID ");
		stringBuilder.append("						FROM REQUEST_NO ");
		stringBuilder.append("						INNER JOIN UR ON REQUEST_NO.REQUEST_NO = UR.REQUEST_NO AND UR.STATUS = 1 ");
		stringBuilder.append("						WHERE REQUEST_NO.REQUEST_BY = #{user.pincode} ");
		stringBuilder.append("						) ");
		return stringBuilder.toString();
	}
	
	public String closeSetRejectURGroup(Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("UPDATE UR ");
		stringBuilder.append("SET UR.STATUS='3' , UR.REMARK = 'System Reject UR' ,UR.UPDATED_DATE = CURRENT_TIMESTAMP , UR.UPDATED_BY= 'SYSTEM-ADMIN' ");
		stringBuilder.append("WHERE UR.UR_ID IN ( ");
		stringBuilder.append("						SELECT DATA2.UR_ID ");
		stringBuilder.append("						FROM ( ");
		stringBuilder.append("								SELECT DATA.UR_ID  ");
		stringBuilder.append("										,(SELECT COUNT(*) FROM UR_FOR_USER WHERE UR_FOR_USER.UR_STATUS = 3 AND UR_FOR_USER.UR_ID = DATA.UR_ID )REJECTSUM ");
		stringBuilder.append("										,COUNTPROCESS ");
		stringBuilder.append("										,(SELECT COUNT(*) FROM UR_FOR_USER WHERE  UR_FOR_USER.UR_ID = DATA.UR_ID )ALLSUM ");
		stringBuilder.append("										FROM ( ");
		stringBuilder.append("												SELECT UR.UR_ID ,COUNT(UR.UR_ID) COUNTPROCESS ");
		stringBuilder.append("												FROM REQUEST_NO ");
		stringBuilder.append("												INNER JOIN UR ON UR.REQUEST_NO=REQUEST_NO.REQUEST_NO  ");
		stringBuilder.append("													AND UR.STATUS = 1 AND REQUEST_NO.REQUEST_LIST=2 ");
		stringBuilder.append("												INNER JOIN UR_FOR_USER ON UR_FOR_USER.UR_ID=UR.UR_ID  ");
		stringBuilder.append("												WHERE UR_FOR_USER.UR_STATUS = 1 AND UR_FOR_USER.PINCODE = #{user.pincode} ");
		stringBuilder.append("												GROUP BY UR.UR_ID ");
		stringBuilder.append("										) DATA ");
		stringBuilder.append("								) DATA2 ");
		stringBuilder.append("						WHERE (REJECTSUM+COUNTPROCESS) = ALLSUM ");
		stringBuilder.append("						) ");
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
		stringBuilder.append("									AND (USER_APP_ROLE.PERIOD_TYPE='1' OR (USER_APP_ROLE.PERIOD_TYPE='2' AND CAST(USER_APP_ROLE.END_TIME AS DATE)  >= CAST(#{date} AS DATE ) ) )");
		stringBuilder.append("									AND (SELECT TYPE FROM APP WHERE APP.APP_ID=(SELECT APP_ID FROM APP_ROLE WHERE APP_ROLE.APP_ROLE_ID=USER_APP_ROLE.APP_ROLE_ID))=#{type} 	");
		return stringBuilder.toString();
	}
	
	public String insertBatch (Map<String, Object> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("INSERT INTO [BATCH] ");
		stringBuilder.append("           ([BATCH_ID] ");
		stringBuilder.append("           ,[BATCH_NAME] ");
		stringBuilder.append("           ,[STATUS] ");
		stringBuilder.append("           ,[MESSAGE] ");
		stringBuilder.append("           ,[RUN_TIME] ");
		stringBuilder.append("           ,[CREATED_DATE] ");
		stringBuilder.append("           ,[CREATED_BY] ");
		stringBuilder.append("           ,[UPDATED_DATE] ");
		stringBuilder.append("           ,[UPDATED_BY]) ");
		stringBuilder.append("     VALUES ");
		stringBuilder.append("           (#{batch.batchId} ");
		stringBuilder.append("           ,#{batch.batchName} ");
		stringBuilder.append("           ,#{batch.status} ");
		stringBuilder.append("           ,#{batch.message} ");
		stringBuilder.append("           ,#{batch.runTime} ");
		stringBuilder.append("           ,#{batch.createdDate} ");
		stringBuilder.append("           ,#{batch.createdBy} ");
		stringBuilder.append("           ,#{batch.updatedDate} ");
		stringBuilder.append("           ,#{batch.updatedBy}) ");
		return stringBuilder.toString();
	}
}

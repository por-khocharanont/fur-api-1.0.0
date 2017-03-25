package th.cu.thesis.fur.api.util;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class WatchListSqlProvider {
	
	public static final String UR_STATUS_PROCESS = "1";
	public static final String UR_STATUS_COMPLETE = "2";
	public static final String UR_STATUS_REJECT = "3";
	
	public String countTotalWatchList(Map<String, Object> params) {
			
		String sql = "";
		
		Date startDate = (Date) params.get("startDate");
		Date endDate = (Date) params.get("endDate");
		String urStep = (String) params.get("urStep");
		String urStatus = (String) params.get("urStatus");
		
		if(!urStatus.equals(UR_STATUS_PROCESS)){
			
			sql = "SELECT COUNT(*) FROM ( "+

				"SELECT req.REQUEST_NO, ur.UR_ID, req.REQUEST_DATE, req.ENNAME + ' ' + req.ENSURNAME AS REQUEST_BY, ur.[STATUS], req.USERNAME FROM [REQUEST_NO] req "+
				"LEFT OUTER JOIN [UR] ur on ur.REQUEST_NO = req.REQUEST_NO " +
				"WHERE 1=1 " +
				"AND ur.[STATUS] = #{urStatus} ";
			
			if(startDate==null && endDate==null){
				
				sql += "AND req.REQUEST_DATE >= DateAdd(dd, -30,GETDATE()) AND req.REQUEST_DATE <= GETDATE() ";
			}
		}
		else{
			
			sql = "SELECT COUNT(*) FROM ( "+

				"SELECT req.REQUEST_NO, ur.UR_ID, req.REQUEST_DATE, req.ENNAME + ' ' + req.ENSURNAME AS REQUEST_BY, urStep.UR_STEP, req.USERNAME FROM [REQUEST_NO] req "+
				"LEFT OUTER JOIN [UR] ur on ur.REQUEST_NO = req.REQUEST_NO " +
				"LEFT OUTER JOIN [UR_STEP] urStep on urStep.UR_ID = ur.UR_ID " +
				"WHERE 1=1 " +
				"AND ur.[STATUS] = '1' "+
				"AND urStep.[STATUS] = #{urStatus} ";
		}
		

		sql +=		"AND ur.UR_ID in ( "+

					"SELECT urFor.UR_ID FROM [UR_FOR_USER] urFor WHERE urFor.USERNAME = #{username} "+

				") ";
				
		sql += "AND req.[REQUEST_NO] LIKE #{userRequestNo} ";
		sql += "AND ur.[UR_ID] LIKE #{urNo} ";
		
		if(StringUtils.isNotEmpty(urStep)){
			
			sql += "AND urStep.[UR_STEP] = #{urStep} ";
		}
	
		if(startDate!=null){
			
			sql += "AND req.[REQUEST_DATE] >= CONVERT(smalldatetime, #{startDate}, 103) ";
		}
		if(endDate!=null){
			
			sql += "AND req.[REQUEST_DATE] <= CONVERT(smalldatetime, #{endDate}, 103) ";
		}
		
		sql +=	"UNION ";
		
		
		if(!urStatus.equals(UR_STATUS_PROCESS)){
			
			sql +=	"SELECT req.REQUEST_NO, ur.UR_ID, req.REQUEST_DATE, req.ENNAME + ' ' + req.ENSURNAME AS REQUEST_BY, ur.[STATUS], req.USERNAME FROM [REQUEST_NO] req "+
				"LEFT OUTER JOIN [UR] ur on ur.REQUEST_NO = req.REQUEST_NO "+
				"LEFT OUTER JOIN [UR_STEP] urStep on urStep.UR_ID = ur.UR_ID "+
				"WHERE 1=1 " +
				"AND ur.[STATUS] = #{urStatus} ";
			
			if(startDate == null && endDate == null){
				
				sql += "AND req.REQUEST_DATE >= DateAdd(dd, -30,GETDATE()) AND req.REQUEST_DATE <= GETDATE() ";
			}
		}
		else{
			
			sql +=	"SELECT req.REQUEST_NO, ur.UR_ID, req.REQUEST_DATE, req.ENNAME + ' ' + req.ENSURNAME AS REQUEST_BY, urStep.UR_STEP, req.USERNAME FROM [REQUEST_NO] req "+
				"LEFT OUTER JOIN [UR] ur on ur.REQUEST_NO = req.REQUEST_NO "+
				"LEFT OUTER JOIN [UR_STEP] urStep on urStep.UR_ID = ur.UR_ID "+
				"WHERE 1=1 " +
				"AND ur.[STATUS] = '1' "+
				"AND urStep.[STATUS] = #{urStatus} ";
		}
		
		

		sql +=		"AND ur.UR_ID in ( "+

					"SELECT urFor.UR_ID FROM [UR_FOR_USER] urFor WHERE urFor.USERNAME != #{username} "+

				") "+
				"AND req.USERNAME = #{username} ";
		
		sql += "AND req.[REQUEST_NO] LIKE #{userRequestNo} ";
		sql += "AND ur.[UR_ID] LIKE #{urNo} ";
		
		if(StringUtils.isNotEmpty(urStep)){
			
			sql += "AND urStep.[UR_STEP] = #{urStep} ";
		}
		if(startDate != null){
			
			sql += "AND req.[REQUEST_DATE] >= CONVERT(smalldatetime, #{startDate}, 103) ";
		}
		if(endDate != null){
			
			sql += "AND req.[REQUEST_DATE] <= CONVERT(smalldatetime, #{endDate}, 103) ";
		}
		
		sql += ") AS RquestNo ";
				
			
		return sql;
	}
	
	public String getWatchList(Map<String, Object> params) {
		
		String sql = "";
		
		String userRequestNo = (String) params.get("userRequestNo");
		String urNo = (String) params.get("urNo");
		Date startDate = (Date) params.get("startDate");
		Date endDate = (Date) params.get("endDate");
		String urStep = (String) params.get("urStep");
		String orderBy = (String) params.get("orderBy");
		String urStatus = (String) params.get("urStatus");
		
		
		
		if(urStatus.equals(UR_STATUS_PROCESS)){
			
			sql = "SELECT * FROM ( SELECT ROW_NUMBER() OVER(ORDER BY "+orderBy+" , UR.UR_ID ASC) AS Row, REQUEST_NO, APP_ROLE_NAME,APP_NAME, UR_ID, SUBJECT, REQUEST_DATE, REQUEST_BY, UR_STEP, USERNAME FROM ( "+

				"SELECT req.REQUEST_NO, ur.APP_ROLE_NAME,ur.APP_NAME,ur.UR_ID, req.SUBJECT, req.REQUEST_DATE, req.ENNAME + ' ' + req.ENSURNAME AS REQUEST_BY, urStep.UR_STEP, req.USERNAME FROM [REQUEST_NO] req "+
				"LEFT OUTER JOIN [UR] ur on ur.REQUEST_NO = req.REQUEST_NO "+
				"LEFT OUTER JOIN [UR_STEP] urStep on urStep.UR_ID = ur.UR_ID "+
				"WHERE 1=1 "+
				"AND ur.[STATUS] = '1' "+
				"AND urStep.[STATUS] = #{urStatus} ";
			
		}else{
			
			sql = "SELECT * FROM ( SELECT ROW_NUMBER() OVER(ORDER BY "+orderBy+" ,UR.UR_ID ASC) AS Row, REQUEST_NO, APP_ROLE_NAME,APP_NAME, UR_ID, SUBJECT, REQUEST_DATE, REQUEST_BY, UR_STEP, USERNAME FROM ( "+

				"SELECT req.REQUEST_NO, ur.APP_ROLE_NAME,ur.APP_NAME, ur.UR_ID, req.SUBJECT, req.REQUEST_DATE, req.ENNAME + ' ' + req.ENSURNAME AS REQUEST_BY, ur.[STATUS] AS UR_STEP, req.USERNAME FROM [REQUEST_NO] req "+
				"LEFT OUTER JOIN [UR] ur on ur.REQUEST_NO = req.REQUEST_NO "+
				"WHERE 1=1 "+
				"AND ur.[STATUS] = #{urStatus} ";
			
			if(startDate == null && endDate == null){
				
				sql += "AND req.REQUEST_DATE >= DateAdd(dd, -30,GETDATE()) AND req.REQUEST_DATE <= GETDATE() ";
			}
			
		}
		
		sql +=		"AND ur.UR_ID in ( "+

					"SELECT urFor.UR_ID FROM [UR_FOR_USER] urFor WHERE urFor.USERNAME = #{username} "+

				") ";
		
		sql += "AND req.[REQUEST_NO] LIKE #{userRequestNo} ";
		sql += "AND ur.[UR_ID] LIKE #{urNo} ";
		
		if(StringUtils.isNotEmpty(urStep)){
			
			sql += "AND urStep.[UR_STEP] = #{urStep} ";
		}
			
		if(startDate != null){
			
			sql += "AND req.[REQUEST_DATE] >= CONVERT(smalldatetime, #{startDate}, 103) ";
		}
		if(endDate != null){
			
			sql += "AND req.[REQUEST_DATE] <= CONVERT(smalldatetime, #{endDate}, 103) ";
		}
		
		sql +=	"UNION ";
		
		
		if(urStatus.equals(UR_STATUS_PROCESS)){
			
			sql +=	"SELECT req.REQUEST_NO, ur.APP_ROLE_NAME,ur.APP_NAME, ur.UR_ID, req.SUBJECT, req.REQUEST_DATE, req.ENNAME + ' ' + req.ENSURNAME AS REQUEST_BY, urStep.UR_STEP, req.USERNAME FROM [REQUEST_NO] req "+
					"LEFT OUTER JOIN [UR] ur on ur.REQUEST_NO = req.REQUEST_NO "+
					"LEFT OUTER JOIN [UR_STEP] urStep on urStep.UR_ID = ur.UR_ID "+
					"WHERE 1=1 "+
					"AND ur.[STATUS] = '1' "+
					"AND urStep.[STATUS] = #{urStatus} ";
			
		}else{

			sql +=	"SELECT req.REQUEST_NO, ur.APP_ROLE_NAME,ur.APP_NAME, ur.UR_ID, req.SUBJECT, req.REQUEST_DATE, req.ENNAME + ' ' + req.ENSURNAME AS REQUEST_BY, ur.[STATUS] AS UR_STEP, req.USERNAME FROM [REQUEST_NO] req "+
					"LEFT OUTER JOIN [UR] ur on ur.REQUEST_NO = req.REQUEST_NO "+
					"WHERE 1=1 "+
					"AND ur.[STATUS] = #{urStatus} ";
			
			if(startDate == null && endDate == null){
				
				sql += "AND req.REQUEST_DATE >= DateAdd(dd, -30,GETDATE()) AND req.REQUEST_DATE <= GETDATE() ";
			}
			
		}

		sql +=		"AND ur.UR_ID in ( "+

					"SELECT urFor.UR_ID FROM [UR_FOR_USER] urFor WHERE urFor.USERNAME != #{username} "+

				") ";
				
		sql += "AND req.USERNAME = #{username} ";
		sql += "AND req.[REQUEST_NO] LIKE #{userRequestNo} ";
		sql += "AND ur.[UR_ID] LIKE #{urNo} ";
		
		if(StringUtils.isNotEmpty(urStep)){
			
			sql += "AND urStep.[UR_STEP] = #{urStep} ";
		}
		if(startDate != null){
			
			sql += "AND req.[REQUEST_DATE] >= CONVERT(smalldatetime, #{startDate}, 103) ";
		}
		if(endDate != null){
			
			sql += "AND req.[REQUEST_DATE] <= CONVERT(smalldatetime, #{endDate}, 103) ";
		}
		
		sql += ") AS UR ";
		
		sql += ")  RquestNo WHERE RquestNo.Row >= #{startRow} AND RquestNo.Row <= #{endRow} ";
				
			
		return sql;
	}

}

package th.cu.thesis.fur.api.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;

public class FlowconfigSqlProvider {

	public String getSearchFlowConfig(@Param("flowname") String flowname,@Param("flowtype") String flowtype,@Param("usertype") String usertype,@Param("startRow") int startRow, @Param("endRow") int endRow)
			throws RuntimeException {
		
		StringBuilder sqlBuilder = new StringBuilder() ;
		sqlBuilder.append("SELECT * FROM ( ");
			sqlBuilder.append("SELECT  ROW_NUMBER() OVER (ORDER BY flowname) AS no, app.* FROM ( ");
						sqlBuilder.append(" SELECT FLOW_NAME as 'flowname', ");
							sqlBuilder.append("FLOW_TYPE as 'flowtype', ");
							sqlBuilder.append("USER_TYPE as 'usertype' ");
						sqlBuilder.append("FROM [ACIM].[FLOW] ");
						sqlBuilder.append("WHERE 1=1 ");
						
						if(StringUtils.isNoneBlank(flowname)){
							sqlBuilder.append("AND FLOW_NAME LIKE '"+flowname+"' ");
						}
						if(StringUtils.isNoneBlank(flowtype)){
							sqlBuilder.append("AND FLOW_ID ="+flowtype+" ");
						}
						if(StringUtils.isNoneBlank(usertype)){
							sqlBuilder.append("AND USER_TYPE ="+usertype+" ");
						}
						
			sqlBuilder.append(" ) app");
		sqlBuilder.append(" ) result WHERE result.no BETWEEN "+startRow + " AND " + endRow);
		// TODO Auto-generated method stub
		return sqlBuilder.toString();
	}

	public String getCountSearchFlowConfig(@Param("flowname") String flowname,@Param("flowtype") String flowtype,@Param("usertype") String usertype)
	{
		StringBuilder sqlBuilder = new StringBuilder() ;
		sqlBuilder.append("SELECT COUNT(*) ");
		sqlBuilder.append("FROM [ACIM].[FLOW] ");
		sqlBuilder.append("WHERE 1=1 ");
		
		if(StringUtils.isNoneBlank(flowname)){
			sqlBuilder.append("AND FLOW_NAME LIKE '"+flowname+"' ");
		}
		if(StringUtils.isNoneBlank(flowtype)){
			sqlBuilder.append("AND FLOW_TYPE ="+flowtype+" ");
		}
		if(StringUtils.isNoneBlank(usertype)){
			sqlBuilder.append("AND USER_TYPE ="+usertype+" ");
		}
		
		return sqlBuilder.toString();
	}

}

package th.cu.thesis.fur.api.model;

import java.util.Map;

public class JasperModelInput {
	private String sqlStatement;
	private Map<String,Object> mapVariables;
	public JasperModelInput(){
		
	}
	public JasperModelInput(String statementSql,Map<String, Object> mapVariables) {
		this.sqlStatement = statementSql;
		this.mapVariables = mapVariables;
	}
	public String getSqlStatement() {
		return sqlStatement;
	}
	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}
	public Map<String, Object> getMapVariables() {
		return mapVariables;
	}
	public void setMapVariables(Map<String, Object> mapVariables) {
		this.mapVariables = mapVariables;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JasperModelInput [sqlStatement=");
		builder.append(sqlStatement);
		builder.append(", mapVariables=");
		builder.append(mapVariables);
		builder.append("]");
		return builder.toString();
	}
	
}

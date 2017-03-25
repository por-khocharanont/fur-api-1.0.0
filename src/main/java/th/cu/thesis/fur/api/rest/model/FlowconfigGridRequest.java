package th.cu.thesis.fur.api.rest.model;

public class FlowconfigGridRequest extends CommonGrid {

	private String columnFlowname;
	private String columnFlowtype;
	private String columnUsertype;
	
	

	public FlowconfigGridRequest() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getColumnFlowname() {
		return columnFlowname;
	}



	public void setColumnFlowname(String columnFlowname) {
		this.columnFlowname = columnFlowname;
	}



	public String getColumnFlowtype() {
		return columnFlowtype;
	}



	public void setColumnFlowtype(String columnFlowtype) {
		this.columnFlowtype = columnFlowtype;
	}



	public String getColumnUsertype() {
		return columnUsertype;
	}



	public void setColumnUsertype(String columnUsertype) {
		this.columnUsertype = columnUsertype;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FlowconfigGridRequest [columnFlowname=");
		builder.append(columnFlowname);
		builder.append(", columnFlowtype=");
		builder.append(columnFlowtype);
		builder.append(", columnUsertype=");
		builder.append(columnUsertype);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

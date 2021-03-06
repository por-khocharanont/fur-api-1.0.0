package th.cu.thesis.fur.api.rest.model.om;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NewDataSet")
public class ListEmpOmResponse {
	@XStreamAlias("Permission")
	private Permission permission;
	@XStreamAlias("Table")
	private List<Employee> empList;

	
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public List<Employee> getEmpList() {
		return empList;
	}
	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}
	
	
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ListEmpResignedByDate [Permission=");
		strBuilder.append(permission);
		strBuilder.append(", empList=");
		strBuilder.append(empList);
		strBuilder.append("]");		
		
		return strBuilder.toString();
	}
}

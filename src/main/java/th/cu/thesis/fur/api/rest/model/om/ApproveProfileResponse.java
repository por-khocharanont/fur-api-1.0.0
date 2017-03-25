package th.cu.thesis.fur.api.rest.model.om;



import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NewDataSet")
public class ApproveProfileResponse {
	@XStreamAlias("Permission")
	private Permission permission;
	@XStreamAlias("Table")
	private EmployeeProfile empPro;
	
	
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public EmployeeProfile getEmpPro() {
		return empPro;
	}
	public void setEmpPro(EmployeeProfile empPro) {
		this.empPro = empPro;
	}
	
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("GetApproveProfile [Permission=");
		strBuilder.append(permission);
		strBuilder.append(", empPro=");
		strBuilder.append(empPro);
		strBuilder.append("]");		
		
		return strBuilder.toString();
	}
}

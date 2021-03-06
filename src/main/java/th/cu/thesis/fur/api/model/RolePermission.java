package th.cu.thesis.fur.api.model;

public class RolePermission extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -614655610380540481L;
	private String rolePermissionId;
	private String roleCode;
	private String menuCode;
	private String enable;
	private String actionPermission;
	public RolePermission() {
		super();
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getActionPermission() {
		return actionPermission;
	}
	public void setActionPermission(String actionPermission) {
		this.actionPermission = actionPermission;
	}
	public String getRolePermissionId() {
		return rolePermissionId;
	}
	public void setRolePermissionId(String rolePermissionId) {
		this.rolePermissionId = rolePermissionId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RolePermission [rolePermissionId=");
		builder.append(rolePermissionId);
		builder.append(", roleCode=");
		builder.append(roleCode);
		builder.append(", menuCode=");
		builder.append(menuCode);
		builder.append(", enable=");
		builder.append(enable);
		builder.append(", actionPermission=");
		builder.append(actionPermission);
		builder.append("]");
		return builder.toString();
	}


	
	

}

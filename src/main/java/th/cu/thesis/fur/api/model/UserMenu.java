package th.cu.thesis.fur.api.model;

import java.util.Map;

public class UserMenu {
	
	Map<String , RolePermission> userMenu;

	public Map<String, RolePermission> getUserMenu() {
		return userMenu;
	}

	public void setUserMenu(Map<String, RolePermission> userMenu) {
		this.userMenu = userMenu;
	}
	
	public String getRoleCode(){
		
		String roleCode = "";
		for (Map.Entry<String , RolePermission> entry : userMenu.entrySet())
		{
			roleCode = entry.getValue().getRoleCode();
			break;
		}
		
		
		return roleCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserMenu [userMenu=");
		builder.append(userMenu);
		builder.append("]");
		return builder.toString();
	}
	
	

}

package th.cu.thesis.fur.api.rest.model.om;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NewDataSet")
public class ListUserChangeResponse {
	
	
	
		@XStreamAlias("Permission")
		private Permission permission;
		@XStreamAlias("Table")
		private List<UserChange> userChange;
		
		public Permission getPermission() {
			return permission;
		}
		public void setPermission(Permission permission) {
			this.permission = permission;
		}
		public List<UserChange> getUserChange() {
			return userChange;
		}
		public void setUserChange(List<UserChange> userChange) {
			this.userChange = userChange;
		}
		

	}



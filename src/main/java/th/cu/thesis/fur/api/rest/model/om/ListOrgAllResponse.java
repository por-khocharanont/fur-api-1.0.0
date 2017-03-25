package th.cu.thesis.fur.api.rest.model.om;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NewDataSet")
public class ListOrgAllResponse {
	
	@XStreamAlias("Permission")
	private Permission permission;
	
	@XStreamAlias("Table")
	private List<OrgProfile> orgProfile;

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public List<OrgProfile> getOrgAll() {
		return orgProfile;
	}

	public void setOrgAll(List<OrgProfile> orgProfile) {
		this.orgProfile = orgProfile;
	}
	
	

}

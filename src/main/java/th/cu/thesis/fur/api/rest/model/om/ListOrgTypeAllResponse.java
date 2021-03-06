package th.cu.thesis.fur.api.rest.model.om;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NewDataSet")
public class ListOrgTypeAllResponse {

	@XStreamAlias("Permission")
	private Permission permission;
	
	@XStreamAlias("Table1")
	private List<OrgTypeList> orgTypeList;

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public List<OrgTypeList> getOrgTypeList() {
		return orgTypeList;
	}

	public void setOrgTypeList(List<OrgTypeList> orgTypeList) {
		this.orgTypeList = orgTypeList;
	}
	
	
		
}

package th.cu.thesis.fur.api.model;

import java.util.List;

public class UrDetail extends UserRequestInfo {
	private List<UrForUser> urForUserList;

	public List<UrForUser> getUrForUserList() {
		return urForUserList;
	}
	
	public void setUrForUserList(List<UrForUser> urForUserList) {
		this.urForUserList = urForUserList;
	}
	
	public UrDetail(){
		super();
	}
	
	public UrDetail(UR ur) {
		super(ur);
	}


}

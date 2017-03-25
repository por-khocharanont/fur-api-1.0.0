package th.cu.thesis.fur.api.rest.model.om;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Table")
public class OrgProfile {
	
	@XStreamAlias("ORGCODE")
	private String orgcode;
	@XStreamAlias("ORGNAME")
	private String orgname;
	@XStreamAlias("ORGDESC")
	private String orgdesc;
	@XStreamAlias("ORGLEVEL")
	private String orglevel;
	@XStreamAlias("HIGHERORG")
	private String higherorg;
	@XStreamAlias("HIGHERORGNAME")
	private String higherorgname;
	@XStreamAlias("HIGHERORGDESC")
	private String higherorgdesc;
	@XStreamAlias("HIGHERORGLEVEL")
	private String higherorglevel;
	
	
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getOrgdesc() {
		return orgdesc;
	}
	public void setOrgdesc(String orgdesc) {
		this.orgdesc = orgdesc;
	}
	public String getOrglevel() {
		return orglevel;
	}
	public void setOrglevel(String orglevel) {
		this.orglevel = orglevel;
	}
	public String getHigherorg() {
		return higherorg;
	}
	public void setHigherorg(String higherorg) {
		this.higherorg = higherorg;
	}
	public String getHigherorgname() {
		return higherorgname;
	}
	public void setHigherorgname(String higherorgname) {
		this.higherorgname = higherorgname;
	}
	public String getHigherorgdesc() {
		return higherorgdesc;
	}
	public void setHigherorgdesc(String higherorgdesc) {
		this.higherorgdesc = higherorgdesc;
	}
	public String getHigherorglevel() {
		return higherorglevel;
	}
	public void setHigherorglevel(String higherorglevel) {
		this.higherorglevel = higherorglevel;
	}
	@Override
	public String toString() {
		return "OrgProfile [orgcode=" + orgcode + ", orgname=" + orgname
				+ ", orgdesc=" + orgdesc + ", orglevel=" + orglevel
				+ ", higherorg=" + higherorg + ", higherorgname="
				+ higherorgname + ", higherorgdesc=" + higherorgdesc
				+ ", higherorglevel=" + higherorglevel + "]";
	}
	
	
	

}

package th.cu.thesis.fur.api.model;

import java.util.Date;

public class Organize {

	private String orgcode;
	private String orgname;
	private String orgdesc;
	private String orglevel;
	private String higherorg;
	private String higherorgname;
	private String higherorgdesc;
	private String higherorglevel;
	private String orgType;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
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
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Organize [orgcode=");
		builder.append(orgcode);
		builder.append(", orgname=");
		builder.append(orgname);
		builder.append(", orgdesc=");
		builder.append(orgdesc);
		builder.append(", orglevel=");
		builder.append(orglevel);
		builder.append(", higherorg=");
		builder.append(higherorg);
		builder.append(", higherorgname=");
		builder.append(higherorgname);
		builder.append(", higherorgdesc=");
		builder.append(higherorgdesc);
		builder.append(", higherorglevel=");
		builder.append(higherorglevel);
		builder.append(", orgType=");
		builder.append(orgType);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append("]");
		return builder.toString();
	}

	

}

package th.cu.thesis.fur.api.rest.model;

import java.io.Serializable;

public class WatchListGridRequest extends CommonGrid implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userRequestNo;
	private String urNo;
	private String startDate;
	private String endDate;
	private String urStep;
	private String urStatus;
	public String getUserRequestNo() {
		return userRequestNo;
	}
	public void setUserRequestNo(String userRequestNo) {
		this.userRequestNo = userRequestNo;
	}
	public String getUrNo() {
		return urNo;
	}
	public void setUrNo(String urNo) {
		this.urNo = urNo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getUrStep() {
		return urStep;
	}
	public void setUrStep(String urStep) {
		this.urStep = urStep;
	}
	public String getUrStatus() {
		return urStatus;
	}
	public void setUrStatus(String urStatus) {
		this.urStatus = urStatus;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WatchListGridRequest [userRequestNo=");
		builder.append(userRequestNo);
		builder.append(", urNo=");
		builder.append(urNo);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", urStep=");
		builder.append(urStep);
		builder.append(", urStatus=");
		builder.append(urStatus);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	

}

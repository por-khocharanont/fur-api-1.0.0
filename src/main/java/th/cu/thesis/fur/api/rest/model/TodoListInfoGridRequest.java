package th.cu.thesis.fur.api.rest.model;

import java.util.Date;

public class TodoListInfoGridRequest extends CommonGrid {
	private String username;
	private Date startDate;
	private Date endDate;
	private String urStep;
	private String requestNo;
	private String urId;
	private String type;

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getUrId() {
		return urId;
	}

	public void setUrId(String urId) {
		this.urId = urId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUrStep() {
		return urStep;
	}

	public void setUrStep(String urStep) {
		this.urStep = urStep;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TodoListInfoGridRequest [username=");
		builder.append(username);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", urStep=");
		builder.append(urStep);
		builder.append(", requestNo=");
		builder.append(requestNo);
		builder.append(", urId=");
		builder.append(urId);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}

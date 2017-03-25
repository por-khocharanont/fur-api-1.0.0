package th.cu.thesis.fur.api.model;

import java.util.List;

public class UrStepDetail extends UrStep {
	private String urStepText;
	private String flowText;
	private List<UrStepApprove> urStepApproveList;
	private List<UrApproveHistory> urApproveHistoryList;

	public UrStepDetail(UrStep urStepObj) {
	super(urStepObj);
	}

	public String getUrStepText() {
	return urStepText;
	}

	public void setUrStepText(String urStepText) {
	this.urStepText = urStepText;
	}

	public String getFlowText() {
		return flowText;
	}

	public void setFlowText(String flowText) {
		this.flowText = flowText;
	}

	public List<UrStepApprove> getUrStepApproveList() {
	return urStepApproveList;
	}

	public void setUrStepApproveList(List<UrStepApprove> urStepApproveList) {
	this.urStepApproveList = urStepApproveList;
	}

	public List<UrApproveHistory> getUrApproveHistoryList() {
	return urApproveHistoryList;
	}

	public void setUrApproveHistoryList(List<UrApproveHistory> urApproveHistoryList) {
	this.urApproveHistoryList = urApproveHistoryList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UrStepDetail [urStepText=");
		builder.append(urStepText);
		builder.append(", flowText=");
		builder.append(flowText);
		builder.append(", urStepApproveList=");
		builder.append(urStepApproveList);
		builder.append(", urApproveHistoryList=");
		builder.append(urApproveHistoryList);
		builder.append("]");
		return builder.toString();
	}

}
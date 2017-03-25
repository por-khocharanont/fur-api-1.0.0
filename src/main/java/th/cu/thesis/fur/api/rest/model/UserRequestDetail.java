package th.cu.thesis.fur.api.rest.model;

import java.util.List;

import th.cu.thesis.fur.api.model.RequestNo;
import th.cu.thesis.fur.api.model.UrDetail;
import th.cu.thesis.fur.api.model.UrStepDetail;

public class UserRequestDetail {
	private RequestNo requestNo;
	private UrDetail urDetail;
	private List<UrStepDetail> urStepList;
	private String authenToken;
	private String currentStep;

	public RequestNo getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(RequestNo requestNo) {
		this.requestNo = requestNo;
	}

	public UrDetail getUrDetail() {
		return urDetail;
	}

	public void setUrDetail(UrDetail urDetail) {
		this.urDetail = urDetail;
	}

	public List<UrStepDetail> getUrStepList() {
		return urStepList;
	}

	public void setUrStepList(List<UrStepDetail> urStepList) {
		this.urStepList = urStepList;
	}

	public String getAuthenToken() {
		return authenToken;
	}

	public void setAuthenToken(String authenToken) {
		this.authenToken = authenToken;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

}

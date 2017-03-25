package th.cu.thesis.fur.api.rest.model.om;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Permission")
public class Permission {
	@XStreamAlias("MsgDetail")
	private String msgDetail;
	@XStreamAlias("MsgStatus")
	private String msgStatus;
		
	public String getMsgDetail() {
		return msgDetail;
	}
	public void setMsgDetail(String msgDetail) {
		this.msgDetail = msgDetail;
	}
	public String getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}


	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Permission [msgDetail=");
		strBuilder.append(msgDetail);
		strBuilder.append(", msgStatus=");
		strBuilder.append(msgStatus);
		strBuilder.append("]");		
		
		return strBuilder.toString();
	}
}

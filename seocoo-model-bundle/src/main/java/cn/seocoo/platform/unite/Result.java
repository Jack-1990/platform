package cn.seocoo.platform.unite;

public class Result {

	private String resultCode;
	private String resultMsg;
	private Object resultData;
	private Integer resultStatus;
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public Object getResultData() {
		return resultData;
	}
	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}
	public Integer getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}
	@Override
	public String toString() {
		return "Reslut [resultCode=" + resultCode + ", resultMsg=" + resultMsg
				+ ", resultData=" + resultData + ", resultStatus="
				+ resultStatus + "]";
	}
	
	
}

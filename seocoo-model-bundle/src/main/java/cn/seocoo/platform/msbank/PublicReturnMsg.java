package cn.seocoo.platform.msbank;

/**
 * @author Administrator 公共报文头返回字段
 */
public class PublicReturnMsg {
	private String businessContext;
	private String merchantSeq;
	private String transCode;
	private String gateSeq;
	private String gateTransDate;
	private String gateTransTime;
	private String gateReturnType;
	private String gateReturnCode;
	private String gateReturnMessage;
	private String reserve1;
	private String reserve2;
	private String reserve3;
	private String reserveJson;

	public String getBusinessContext() {
		return businessContext;
	}

	public void setBusinessContext(String businessContext) {
		this.businessContext = businessContext;
	}

	public String getMerchantSeq() {
		return merchantSeq;
	}

	public void setMerchantSeq(String merchantSeq) {
		this.merchantSeq = merchantSeq;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getGateSeq() {
		return gateSeq;
	}

	public void setGateSeq(String gateSeq) {
		this.gateSeq = gateSeq;
	}

	public String getGateTransDate() {
		return gateTransDate;
	}

	public void setGateTransDate(String gateTransDate) {
		this.gateTransDate = gateTransDate;
	}

	public String getGateTransTime() {
		return gateTransTime;
	}

	public void setGateTransTime(String gateTransTime) {
		this.gateTransTime = gateTransTime;
	}

	public String getGateReturnType() {
		return gateReturnType;
	}

	public void setGateReturnType(String gateReturnType) {
		this.gateReturnType = gateReturnType;
	}

	public String getGateReturnCode() {
		return gateReturnCode;
	}

	public void setGateReturnCode(String gateReturnCode) {
		this.gateReturnCode = gateReturnCode;
	}

	public String getGateReturnMessage() {
		return gateReturnMessage;
	}

	public void setGateReturnMessage(String gateReturnMessage) {
		this.gateReturnMessage = gateReturnMessage;
	}

	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

	public String getReserveJson() {
		return reserveJson;
	}

	public void setReserveJson(String reserveJson) {
		this.reserveJson = reserveJson;
	}

}

package cn.seocoo.platform.msbank;

/**
 * 公共报文头请求字段
 */
public class PublicRequestMsg{
	private String businessContext;
	private String merchantNo="";
	private String merchantSeq="";
	private String reserve1="";
	private String reserve2="";
	private String reserve3="";
	private String reserve4="";
	private String reserve5="";
	private String reserveJson="";
	private String securityType="";
	private String sessionId="";
	private String source="";
	private String transCode="";
	private String transDate="";
	private String transTime="";
	private String version="";
	
	public String getBusinessContext() {
		return businessContext;
	}
	public void setBusinessContext(String businessContext) {
		this.businessContext = businessContext;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getMerchantSeq() {
		return merchantSeq;
	}
	public void setMerchantSeq(String merchantSeq) {
		this.merchantSeq = merchantSeq;
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
	public String getReserve4() {
		return reserve4;
	}
	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}
	public String getReserve5() {
		return reserve5;
	}
	public void setReserve5(String reserve5) {
		this.reserve5 = reserve5;
	}
	public String getReserveJson() {
		return reserveJson;
	}
	public void setReserveJson(String reserveJson) {
		this.reserveJson = reserveJson;
	}
	public String getSecurityType() {
		return securityType;
	}
	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
 
	
	
	
}

package cn.seocoo.platform.msbank;

/**
 * 民生银行接口商家下单的信息基类
 */
public class BindOrder{
	private  String amount;                                                                 
	private  String merchantNo;                                         
	private  String merchantSeq;                              
	private  String notifyUrl;   
	private  String orderInfo;                                   
	private  String platformId;                                         
	private  String remark;                                                                  
	private  String selectTradeType;                                             
	private  String transDate;                                                       
	private  String transTime;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSelectTradeType() {
		return selectTradeType;
	}
	public void setSelectTradeType(String selectTradeType) {
		this.selectTradeType = selectTradeType;
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
}

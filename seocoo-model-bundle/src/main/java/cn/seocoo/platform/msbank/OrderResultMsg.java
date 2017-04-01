package cn.seocoo.platform.msbank;

/**
 * @author Administrator
 * 下单最终返回结果
 */
public class OrderResultMsg {
	private String amount;
	private String merchantName;
	private String merchantSeq;
	private String orderInfo;
	private String payInfo;
	private String remark;
	private String picURL;
	private String tradeStatus;
	private String voucherNo;
	
	private  Boolean isSuccess;//ture是訂單成功，false訂單失敗
	private  String failMsg;//錯誤信息

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantSeq() {
		return merchantSeq;
	}

	public void setMerchantSeq(String merchantSeq) {
		this.merchantSeq = merchantSeq;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPicURL() {
		return picURL;
	}

	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}

	public String getVoucherNo()
	{
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo)
	{
		this.voucherNo = voucherNo;
	}

}

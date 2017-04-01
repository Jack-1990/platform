package cn.seocoo.platform.msbank;

/**
 * @author Administrator
 * 查询支付结果接口的返回结果类
 * 
 */
public class PayResultMsg {
    private String merchantName;//商户用于回显商户收款的名字
    private String merchantSeq;//原交易商户订单号
    private String amount;//交易金额 (单位到分)
    private String orderInfo;//订单详情
    private String voucherNo;//凭证号
    private String bankTradeNo;//银行流水号
    private String tradeStatus;//交易结果  	S订单交易成功    E订单失败  R原订单成功，未支付（待支付) C已撤销（理论上不存在) 已关闭    T订单转入退款
    private String remark;//保留域
    private String refNo;//参考号
    private String batchNo;//批次号
    private String cardType;//卡类型
    private String cardNo;//卡号
    
    private String cbCode;//发卡行行号
    private String cardName;//发卡行行名
    private String fee;//交易手续费
    private String transType;//交易类型
    private String cupTermId;//银联终端号
    private String cupTsamNo;//设备序列号
    private String centerInfo;//其他信息
    private String centerSeqId;//微信订单号
    private String bankOrderNo;//收单到微信下单编号
    
    private  Boolean isSuccess;//ture是訂單成功，false訂單失敗
	private  String failMsg;//錯誤信息
    
    
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public String getBankTradeNo() {
		return bankTradeNo;
	}
	public void setBankTradeNo(String bankTradeNo) {
		this.bankTradeNo = bankTradeNo;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCbCode() {
		return cbCode;
	}
	public void setCbCode(String cbCode) {
		this.cbCode = cbCode;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getCupTermId() {
		return cupTermId;
	}
	public void setCupTermId(String cupTermId) {
		this.cupTermId = cupTermId;
	}
	public String getCupTsamNo() {
		return cupTsamNo;
	}
	public void setCupTsamNo(String cupTsamNo) {
		this.cupTsamNo = cupTsamNo;
	}
	public String getCenterInfo() {
		return centerInfo;
	}
	public void setCenterInfo(String centerInfo) {
		this.centerInfo = centerInfo;
	}
	public String getCenterSeqId() {
		return centerSeqId;
	}
	public void setCenterSeqId(String centerSeqId) {
		this.centerSeqId = centerSeqId;
	}
	public String getBankOrderNo() {
		return bankOrderNo;
	}
	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
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
}

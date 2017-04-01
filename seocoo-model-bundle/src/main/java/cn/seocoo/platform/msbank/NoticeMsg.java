package cn.seocoo.platform.msbank;

/**
 * @author Administrator
 * 异步通知返回信息类
 */
public class NoticeMsg {
	private String merchantNo;// 商户号
	private String orderNo;// 商户订单号
	private String platformId;// 平台号
	private String amount;// 交易金额
	private String voucherNo;// 凭证号
	private String bankTradeNo;// 银行流水号
	private String tradeStatus;// 交易结果
	private String reserve;// 保留域
	private String refNo;// 参考号
	private String batchNo;// 批次号
	private String cardType;// 卡类型
	private String cardNo;// 卡号
	private String cbCode;// 发卡行行号
	private String cardName;// 发卡行行名
	private String fee;// 交易手续费
	private String cupTermId;// 银联终端号
	private String cupTsamNo;// 设备序列号
	private String centerInfo;// 其他信息
	private String centerSeqId;// 微信/支付宝订单号
	private String serialNo;// 收单流水号
	private String bankOrderNo;// 收单到微信/支付宝下单编号
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
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
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getBankOrderNo() {
		return bankOrderNo;
	}
	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}
}

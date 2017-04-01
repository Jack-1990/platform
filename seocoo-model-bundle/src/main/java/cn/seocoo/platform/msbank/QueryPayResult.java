package cn.seocoo.platform.msbank;

/**
 *  查询支付结果基类
 *
 */
public class QueryPayResult {
	private String platformId;// 接入平台号
	private String merchantNo;// 民生统一商户号
	private String merchantSeq;// 商户订单号
	private String tradeType;// 支付：1 退款：2
	private String orgvoucherNo;// 原交易凭证号 查询类型是“退款”时必须输入
	private String reserve;// 备注信息

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
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

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getOrgvoucherNo() {
		return orgvoucherNo;
	}

	public void setOrgvoucherNo(String orgvoucherNo) {
		this.orgvoucherNo = orgvoucherNo;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

}

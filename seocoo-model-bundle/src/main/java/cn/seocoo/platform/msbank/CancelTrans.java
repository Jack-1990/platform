package cn.seocoo.platform.msbank;

/**
 * @author Administrator
 * 退款民生请求接口类
 */
public class CancelTrans {
	private String platformId;//接入平台号
	private String merchantNo;//民生统一商户号
	private String merchantSeq;//商户订单号
	private String orderAmount;//退款金额
	private String orderNote;//退款说明
	private String reserve;//退款备注字段
	
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
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderNote() {
		return orderNote;
	}
	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
}

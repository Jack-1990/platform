package cn.seocoo.platform.model;

public class PaymentAccount {

	private int id;
	private String bankCode;//支付银行简称  WeChat/微信，AliPay/支付宝  
	private String bankName;//支付银行名称
	private String groupCode;
	private String merchantCode;
	private String privateKey;//支付宝商户秘钥
	private String partnerId;//支付宝商户 id
	private String sellerId;//支付宝、微信商户账号
	private String appId;//微信appid
	private String appKey;//微信appKey
	private String appSecurity;//微信app秘钥/支付宝私钥
	private String alipay_notify_async_url;//支付宝异步通知URL
	private String wechat_notify_async_url;//微信异步通知URL
	
	public String getAlipay_notify_async_url() {
		return alipay_notify_async_url;
	}
	public void setAlipay_notify_async_url(String alipay_notify_async_url) {
		this.alipay_notify_async_url = alipay_notify_async_url;
	}
	public String getWechat_notify_async_url() {
		return wechat_notify_async_url;
	}
	public void setWechat_notify_async_url(String wechat_notify_async_url) {
		this.wechat_notify_async_url = wechat_notify_async_url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecurity() {
		return appSecurity;
	}
	public void setAppSecurity(String appSecurity) {
		this.appSecurity = appSecurity;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
}

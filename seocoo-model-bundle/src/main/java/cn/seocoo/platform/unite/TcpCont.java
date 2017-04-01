package cn.seocoo.platform.unite;

import com.alibaba.fastjson.annotation.JSONField;

public class TcpCont {

	@JSONField(name="ServiceCode")
	private String serviceCode;
	@JSONField(name="SrcSysID")
	private String srcSysID;
	@JSONField(name="SrcSysPassWord")
	private String SrcSysPassWord;
	@JSONField(name="SrcSysSign")
	private String srcSysSign;
	@JSONField(name="TransactionID")
	private String transactionID;
	
	public TcpCont() {
	}
	public TcpCont(String serviceCode, String srcSysID, String srcSysPassWord,
			String srcSysSign, String transactionID) {
		super();
		this.serviceCode = serviceCode;
		this.srcSysID = srcSysID;
		SrcSysPassWord = srcSysPassWord;
		this.srcSysSign = srcSysSign;
		this.transactionID = transactionID;
	}
	public String getSrcSysPassWord() {
		return SrcSysPassWord;
	}
	public void setSrcSysPassWord(String srcSysPassWord) {
		SrcSysPassWord = srcSysPassWord;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getSrcSysID() {
		return srcSysID;
	}
	public void setSrcSysID(String srcSysID) {
		this.srcSysID = srcSysID;
	}
	public String getSrcSysSign() {
		return srcSysSign;
	}
	public void setSrcSysSign(String srcSysSign) {
		this.srcSysSign = srcSysSign;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	
}

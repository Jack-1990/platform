package cn.seocoo.platform.unite;

import java.util.Date;

public class InteractiveLog {
	private Integer id;
	private String transactionID;
	private String SrcSysID;
	private String ServiceCode;
	private String ip;
	private String mac;
	private String reqMsg;
	private String respMsg;
	private Date modifyDate;
	private Integer costTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getSrcSysID() {
		return SrcSysID;
	}
	public void setSrcSysID(String srcSysID) {
		SrcSysID = srcSysID;
	}
	public String getServiceCode() {
		return ServiceCode;
	}
	public void setServiceCode(String serviceCode) {
		ServiceCode = serviceCode;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getReqMsg() {
		return reqMsg;
	}
	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Integer getCostTime() {
		return costTime;
	}
	public void setCostTime(Integer costTime) {
		this.costTime = costTime;
	}
	@Override
	public String toString() {
		return "InteractiveLog [id=" + id + ", transactionID=" + transactionID
				+ ", SrcSysID=" + SrcSysID + ", ServiceCode=" + ServiceCode
				+ ", ip=" + ip + ", mac=" + mac + ", reqMsg=" + reqMsg
				+ ", respMsg=" + respMsg + ", modifyDate=" + modifyDate
				+ ", costTime=" + costTime + "]";
	}
	
	
	
}

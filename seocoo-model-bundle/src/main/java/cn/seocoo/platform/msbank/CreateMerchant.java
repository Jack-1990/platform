package cn.seocoo.platform.msbank;

/**
 * 民生银行接口商户进件基类
 */
public class CreateMerchant {
	private String txnSeq;
	private String platformId;
	private String operId;
	private String dataSrc;
	private String outMchntId;
	
	private String mchntName;
	private String mchntFullName;
	private String parentMchntId;
	private String acdCode;
	private String province;
	private String city;
	private String address;
	private String licId;
	private String licValidity;
	private String corpName;
	private String idtCard;
	private String contactName;
	private String telephone;
	private String servTel;
	private String identification;
	private String remark;
	private String message;
	private String devType;
	private String isCert;
	private String autoSettle;
	private String cmbcMchntId;
	 
	public String getTxnSeq() {
		return txnSeq;
	}
	public void setTxnSeq(String txnSeq) {
		this.txnSeq = txnSeq;
	}
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getMchntFullName() {
		return mchntFullName;
	}
	public void setMchntFullName(String mchntFullName) {
		this.mchntFullName = mchntFullName;
	}
	public String getDevType() {
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}
	public String getAcdCode() {
		return acdCode;
	}
	public void setAcdCode(String acdCode) {
		this.acdCode = acdCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIsCert() {
		return isCert;
	}
	public void setIsCert(String isCert) {
		this.isCert = isCert;
	}
	public String getLicId() {
		return licId;
	}
	public void setLicId(String licId) {
		this.licId = licId;
	}
	public String getLicValidity() {
		return licValidity;
	}
	public void setLicValidity(String licValidity) {
		this.licValidity = licValidity;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getIdtCard() {
		return idtCard;
	}
	public void setIdtCard(String idtCard) {
		this.idtCard = idtCard;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getServTel() {
		return servTel;
	}
	public void setServTel(String servTel) {
		this.servTel = servTel;
	}
	public String getAutoSettle() {
		return autoSettle;
	}
	public void setAutoSettle(String autoSettle) {
		this.autoSettle = autoSettle;
	}
	public String getParentMchntId() {
		return parentMchntId;
	}
	public void setParentMchntId(String parentMchntId) {
		this.parentMchntId = parentMchntId;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOutMchntId() {
		return outMchntId;
	}
	public void setOutMchntId(String outMchntId) {
		this.outMchntId = outMchntId;
	}

	public String getCmbcMchntId()
	{
		return cmbcMchntId;
	}

	public void setCmbcMchntId(String cmbcMchntId)
	{
		this.cmbcMchntId = cmbcMchntId;
	}
	
}

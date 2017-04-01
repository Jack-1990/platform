package cn.seocoo.platform.model;

import  java.util.Date;
import java.util.List;

public class Merchant {

    private Integer id;
    private String txnSeq;
    private String platformId;
    private String operId;
    private String outMchntId;
    private String mchntName;
    private String parentMchntId;
    private Integer devType;
    private String industryId;
    private String acdCode;
    private String province;
    private String city;
    private String address;
    private String licId;
    private String licIdValidity;
    private String corpName;
    private String idtCard;
    private String contactName;
    private String telephone;
    private String servTel;
    private String identification;
    private Integer autoSettle;
    private String remark;
    private String message;
    private Integer flag;
    private String cmbcMchntId;
    private String groupCode;
    private Date createTime;
    private Date updateTime;
    private String idBeforePic;
    private String idAfterPic;
    private String checkPic1;
    private String checkPic2;
    private String businessCardPic;
	private String isCert;
	private String mchntFullName;
	private String fullName; // 所属集团名称
	private List<PayChannel> payChannel;
	private String merchantLevel ;
	private String cardNumber ;
	private String acctName;
	private String bankNumber;
	private String bankProvince;
	private String bankCity;
	private String bankName;
	private String affiliatedBank;
	private String bankAreaCode;

	//临时字段
	private Integer pay;
	
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
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
    public String getOutMchntId() {
        return outMchntId;
    }
    public void setOutMchntId(String outMchntId) {
         this.outMchntId = outMchntId;
    }
    public String getMchntName() {
        return mchntName;
    }
    public void setMchntName(String mchntName) {
         this.mchntName = mchntName;
    }
    public String getParentMchntId() {
        return parentMchntId;
    }
    public void setParentMchntId(String parentMchntId) {
         this.parentMchntId = parentMchntId;
    }
    public Integer getDevType() {
        return devType;
    }
    public void setDevType(Integer devType) {
         this.devType = devType;
    }
    public String getIndustryId() {
        return industryId;
    }
    public void setIndustryId(String industryId) {
         this.industryId = industryId;
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
    public String getLicId() {
        return licId;
    }
    public void setLicId(String licId) {
         this.licId = licId;
    }
    public String getLicIdValidity() {
        return licIdValidity;
    }
    public void setLicIdValidity(String licIdValidity) {
         this.licIdValidity = licIdValidity;
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
    public String getIdentification() {
        return identification;
    }
    public void setIdentification(String identification) {
         this.identification = identification;
    }
    public Integer getAutoSettle() {
        return autoSettle;
    }
    public void setAutoSettle(Integer autoSettle) {
         this.autoSettle = autoSettle;
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
    public Integer getFlag() {
        return flag;
    }
    public void setFlag(Integer flag) {
         this.flag = flag;
    }
    public String getCmbcMchntId() {
        return cmbcMchntId;
    }
    public void setCmbcMchntId(String cmbcMchntId) {
         this.cmbcMchntId = cmbcMchntId;
    }
    public String getGroupCode() {
        return groupCode;
    }
    public void setGroupCode(String groupCode) {
         this.groupCode = groupCode;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
         this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
         this.updateTime = updateTime;
    }
    public String getIdBeforePic() {
        return idBeforePic;
    }
    public void setIdBeforePic(String idBeforePic) {
         this.idBeforePic = idBeforePic;
    }
    public String getIdAfterPic() {
        return idAfterPic;
    }
    public void setIdAfterPic(String idAfterPic) {
         this.idAfterPic = idAfterPic;
    }
    public String getCheckPic1() {
        return checkPic1;
    }
    public void setCheckPic1(String checkPic1) {
         this.checkPic1 = checkPic1;
    }
    public String getCheckPic2() {
        return checkPic2;
    }
    public void setCheckPic2(String checkPic2) {
         this.checkPic2 = checkPic2;
    }
    public String getBusinessCardPic() {
        return businessCardPic;
    }
    public void setBusinessCardPic(String businessCardPic) {
         this.businessCardPic = businessCardPic;
    }

	public String getIsCert()
	{
		return isCert;
	}

	public void setIsCert(String isCert)
	{
		this.isCert = isCert;
	}

	public String getMchntFullName()
	{
		return mchntFullName;
	}

	public void setMchntFullName(String mchntFullName)
	{
		this.mchntFullName = mchntFullName;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public List<PayChannel> getPayChannel()
	{
		return payChannel;
	}

	public void setPayChannel(List<PayChannel> payChannel)
	{
		this.payChannel = payChannel;
	}
	public Integer getPay() {
		return pay;
	}
	public void setPay(Integer pay) {
		this.pay = pay;
	}

    public String getMerchantLevel() {
        return merchantLevel;
    }

    public void setMerchantLevel(String merchantLevel) {
        this.merchantLevel = merchantLevel;
    }



    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAffiliatedBank() {
        return affiliatedBank;
    }

    public void setAffiliatedBank(String affiliatedBank) {
        this.affiliatedBank = affiliatedBank;
    }

    public String getBankAreaCode() {
        return bankAreaCode;
    }

    public void setBankAreaCode(String bankAreaCode) {
        this.bankAreaCode = bankAreaCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

}

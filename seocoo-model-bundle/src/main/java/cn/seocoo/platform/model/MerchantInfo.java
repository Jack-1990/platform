package cn.seocoo.platform.model;

import  java.util.Date;
import java.util.List;

public class MerchantInfo {

    private Integer id;
    private String merchantCode;
    private String parentUser;
    private String parentMerchantCode;
    private String bank;
    private String level;
    private Double currentTotalProfit;
    private Double totalProfit;
    private Integer certifyStatus;
    private String createUser;
    private Date createTime;
    private Date updateTime;
    private Date submitAuditTime;
    private String levelName;
	private String loginName;
	private String logo;
	private String headPic; 
	private String realName;
	private String flag;
	private String receiptQrCode;

	private List<PayChannel> payChannel; // 对应的支付通道

	private String attrName; // 等级
	private String cardNumber; // 银行卡编号
	private String bankArea;// 开户地址
	private String bankNumber;// 开户行号
	private String affiliatedBank;// 所属银行
	private String ID_before_pic;// 身份证正面照片
	private String ID_after_pic;// 身份证反面照片
	private String bank_before_pic;// 银行卡正面照片
	private String bank_after_pic;// 银行卡反面照片
	private String address;// 用户地址
	private String idNumber;// 身份证号
	private String city ;
	private String province ;
	
	private String bankAreaCode;
    
	// 临时字段 分页
	private Integer currentPage;

	// 直接商户
	private Integer directCount;
	
	//计算交易分润的向上层级数
	private Integer countProfitLevel;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getMerchantCode() {
        return merchantCode;
    }
    public void setMerchantCode(String merchantCode) {
         this.merchantCode = merchantCode;
    }
    public String getParentUser() {
        return parentUser;
    }
    public void setParentUser(String parentUser) {
         this.parentUser = parentUser;
    }
    public String getParentMerchantCode() {
        return parentMerchantCode;
    }
    public void setParentMerchantCode(String parentMerchantCode) {
         this.parentMerchantCode = parentMerchantCode;
    }
    public String getBank() {
        return bank;
    }
    public void setBank(String bank) {
         this.bank = bank;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
         this.level = level;
    }
    public Double getCurrentTotalProfit() {
        return currentTotalProfit;
    }
    public void setCurrentTotalProfit(Double currentTotalProfit) {
         this.currentTotalProfit = currentTotalProfit;
    }
    public Double getTotalProfit() {
        return totalProfit;
    }
    public void setTotalProfit(Double totalProfit) {
         this.totalProfit = totalProfit;
    }
    public Integer getCertifyStatus() {
        return certifyStatus;
    }
    public void setCertifyStatus(Integer certifyStatus) {
         this.certifyStatus = certifyStatus;
    }
    public String getCreateUser() {
        return createUser;
    }
    public void setCreateUser(String createUser) {
         this.createUser = createUser;
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
    public Date getSubmitAuditTime() {
        return submitAuditTime;
    }
    public void setSubmitAuditTime(Date submitAuditTime) {
         this.submitAuditTime = submitAuditTime;
    }
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage)
	{
		this.currentPage = currentPage;
	}

	public Integer getDirectCount()
	{
		return directCount;
	}

	public void setDirectCount(Integer directCount)
	{
		this.directCount = directCount;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public List<PayChannel> getPayChannel()
	{
		return payChannel;
	}

	public void setPayChannel(List<PayChannel> payChannel)
	{
		this.payChannel = payChannel;
	}

	public String getRealName()
	{
		return realName;
	}

	public void setRealName(String realName)
	{
		this.realName = realName;
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public String getAttrName()
	{
		return attrName;
	}

	public void setAttrName(String attrName)
	{
		this.attrName = attrName;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	

	public String getBankArea()
	{
		return bankArea;
	}
	public void setBankArea(String bankArea)
	{
		this.bankArea = bankArea;
	}
	public String getBankNumber()
	{
		return bankNumber;
	}

	public void setBankNumber(String bankNumber)
	{
		this.bankNumber = bankNumber;
	}

	public String getAffiliatedBank()
	{
		return affiliatedBank;
	}

	public void setAffiliatedBank(String affiliatedBank)
	{
		this.affiliatedBank = affiliatedBank;
	}

	public String getID_before_pic()
	{
		return ID_before_pic;
	}

	public void setID_before_pic(String iD_before_pic)
	{
		ID_before_pic = iD_before_pic;
	}

	public String getID_after_pic()
	{
		return ID_after_pic;
	}

	public void setID_after_pic(String iD_after_pic)
	{
		ID_after_pic = iD_after_pic;
	}

	public String getBank_before_pic()
	{
		return bank_before_pic;
	}

	public void setBank_before_pic(String bank_before_pic)
	{
		this.bank_before_pic = bank_before_pic;
	}

	public String getBank_after_pic()
	{
		return bank_after_pic;
	}

	public void setBank_after_pic(String bank_after_pic)
	{
		this.bank_after_pic = bank_after_pic;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getIdNumber()
	{
		return idNumber;
	}

	public void setIdNumber(String idNumber)
	{
		this.idNumber = idNumber;
	}
	public String getBankAreaCode()
	{
		return bankAreaCode;
	}
	public void setBankAreaCode(String bankAreaCode)
	{
		this.bankAreaCode = bankAreaCode;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getProvince()
	{
		return province;
	}
	public void setProvince(String province)
	{
		this.province = province;
	}
	public String getLogo()
	{
		return logo;
	}
	public void setLogo(String logo)
	{
		this.logo = logo;
	}
	public String getHeadPic()
	{
		return headPic;
	}
	public void setHeadPic(String headPic)
	{
		this.headPic = headPic;
	}
	public Integer getCountProfitLevel() {
		return countProfitLevel;
	}
	public void setCountProfitLevel(Integer countProfitLevel) {
		this.countProfitLevel = countProfitLevel;
	}
	public String getReceiptQrCode() {
		return receiptQrCode;
	}
	public void setReceiptQrCode(String receiptQrCode) {
		this.receiptQrCode = receiptQrCode;
	}
	
}

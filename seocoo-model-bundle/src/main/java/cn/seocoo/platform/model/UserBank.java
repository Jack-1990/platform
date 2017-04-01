package cn.seocoo.platform.model;

import java.util.Date;

public class UserBank {

    private Integer id;
    private String merchantCode;
    private String cardNumber;
    private String affiliatedBank;
    private String bankName;
    private String bankArea;
    private String bankAreaCode;
    private String bankAddress;
    private String bankNumber;
    private Integer auditStatus;
    private String createUser;
    private Date createTime;
    private Date updateTime;
    private String province;
    private String city;
    
    private String loginName;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
         this.cardNumber = cardNumber;
    }
    public String getAffiliatedBank() {
        return affiliatedBank;
    }
    public void setAffiliatedBank(String affiliatedBank) {
         this.affiliatedBank = affiliatedBank;
    }
    public String getBankArea() {
        return bankArea;
    }
    public void setBankArea(String bankArea) {
         this.bankArea = bankArea;
    }
    public String getBankAreaCode() {
        return bankAreaCode;
    }
    public void setBankAreaCode(String bankAreaCode) {
         this.bankAreaCode = bankAreaCode;
    }
    public String getBankAddress() {
        return bankAddress;
    }
    public void setBankAddress(String bankAddress) {
         this.bankAddress = bankAddress;
    }
    public String getBankNumber() {
        return bankNumber;
    }
    public void setBankNumber(String bankNumber) {
         this.bankNumber = bankNumber;
    }
    public Integer getAuditStatus() {
        return auditStatus;
    }
    public void setAuditStatus(Integer auditStatus) {
         this.auditStatus = auditStatus;
    }
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
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
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
	
    
}

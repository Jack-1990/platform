package cn.seocoo.platform.model;

import  java.util.Date;

public class UserIdinfo {

    private Integer id;
    private String merchantCode;
    private String realName;
    private String iDNumber;
    private String address;
    private Integer auditStatus;
    private String createUser;
    private Date createTime;
    private Date updateTime;

    
    private String loginName;
    
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
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
         this.realName = realName;
    }
    public String getIDNumber() {
        return iDNumber;
    }
    public void setIDNumber(String iDNumber) {
         this.iDNumber = iDNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
         this.address = address;
    }
    public Integer getAuditStatus() {
        return auditStatus;
    }
    public void setAuditStatus(Integer auditStatus) {
         this.auditStatus = auditStatus;
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
    
}

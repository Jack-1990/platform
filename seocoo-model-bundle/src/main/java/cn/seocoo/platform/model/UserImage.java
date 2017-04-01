package cn.seocoo.platform.model;

import java.util.Date;

public class UserImage {

    private Integer id;
    private String merchantCode;
    private String iD_before_pic;
    private String iD_after_pic;
    private String bank_before_pic;
    private String bank_after_pic;
    private Integer auditStatus;
    private String createUser;
    private Date createTime;
    private Date updateTime;

	private String idBeforeStr;
    private String idAfterStr;
    private String bankBeforeStr;
    private String bankAfterStr;
    
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

	public String getiD_before_pic() {
		return iD_before_pic;
	}

	public void setiD_before_pic(String iD_before_pic) {
		this.iD_before_pic = iD_before_pic;
	}

	public String getiD_after_pic() {
		return iD_after_pic;
	}

	public void setiD_after_pic(String iD_after_pic) {
		this.iD_after_pic = iD_after_pic;
	}

	public String getBank_before_pic() {
		return bank_before_pic;
	}

	public void setBank_before_pic(String bank_before_pic) {
		this.bank_before_pic = bank_before_pic;
	}

	public String getBank_after_pic() {
		return bank_after_pic;
	}

	public void setBank_after_pic(String bank_after_pic) {
		this.bank_after_pic = bank_after_pic;
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


	public String getIdBeforeStr() {
		return idBeforeStr;
	}

	public void setIdBeforeStr(String idBeforeStr) {
		this.idBeforeStr = idBeforeStr;
	}

	public String getIdAfterStr() {
		return idAfterStr;
	}

	public void setIdAfterStr(String idAfterStr) {
		this.idAfterStr = idAfterStr;
	}

	public String getBankBeforeStr() {
		return bankBeforeStr;
	}

	public void setBankBeforeStr(String bankBeforeStr) {
		this.bankBeforeStr = bankBeforeStr;
	}

	public String getBankAfterStr() {
		return bankAfterStr;
	}

	public void setBankAfterStr(String bankAfterStr) {
		this.bankAfterStr = bankAfterStr;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
    
}

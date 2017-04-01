package cn.seocoo.platform.model;

import  java.util.Date;

public class GroupMerchantPay {

    private Integer id;
    private String groupCode;
    private String merchantCode;
    private String merchantUser;
    private String createUser;
    private Date createTime;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
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
	public String getMerchantUser() {
		return merchantUser;
	}
	public void setMerchantUser(String merchantUser) {
		this.merchantUser = merchantUser;
	}
    
}

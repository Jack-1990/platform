package cn.seocoo.platform.model;

import  java.util.Date;

public class MerchantUser {

    private Integer id;
    private String loginName;
    private String nickName;
    private String parentUser;
    private String merchantCode;
    private Date createTime;
    private Date updateTime;
    private String headPic;
    private String terminal;
    private String deviceToken;

    private Integer certifyStatus;
    private String passWord;
    private String levelCode;
    private String levelName;
    private String realName;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
         this.loginName = loginName;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
         this.nickName = nickName;
    }
    public String getParentUser() {
        return parentUser;
    }
    public void setParentUser(String parentUser) {
         this.parentUser = parentUser;
    }
    public String getMerchantCode() {
        return merchantCode;
    }
    public void setMerchantCode(String merchantCode) {
         this.merchantCode = merchantCode;
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
    public String getHeadPic() {
        return headPic;
    }
    public void setHeadPic(String headPic) {
         this.headPic = headPic;
    }
    public String getTerminal() {
        return terminal;
    }
    public void setTerminal(String terminal) {
         this.terminal = terminal;
    }
    public String getDeviceToken() {
        return deviceToken;
    }
    public void setDeviceToken(String deviceToken) {
         this.deviceToken = deviceToken;
    }
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Integer getCertifyStatus() {
		return certifyStatus;
	}
	public void setCertifyStatus(Integer certifyStatus) {
		this.certifyStatus = certifyStatus;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	
    
}

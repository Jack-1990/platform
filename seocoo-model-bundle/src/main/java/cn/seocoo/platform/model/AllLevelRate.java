package cn.seocoo.platform.model;

import java.util.Date;
import java.util.List;

public class AllLevelRate {

    private Integer id;
    private String levelCode;
    private String levelName;
    private String payChannel;
    private String payChannelName;
    private String rate;
    private String bank;
    private String levelPic;
    private Date createTime;
    private Date updateTime;
    
    private List<AllLevelRate> levelList;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getLevelCode() {
        return levelCode;
    }
    public void setLevelCode(String levelCode) {
         this.levelCode = levelCode;
    }
    public String getLevelName() {
        return levelName;
    }
    public void setLevelName(String levelName) {
         this.levelName = levelName;
    }
    public String getPayChannel() {
        return payChannel;
    }
    public void setPayChannel(String payChannel) {
         this.payChannel = payChannel;
    }
    public String getPayChannelName() {
        return payChannelName;
    }
    public void setPayChannelName(String payChannelName) {
         this.payChannelName = payChannelName;
    }
    public String getRate() {
        return rate;
    }
    public void setRate(String rate) {
         this.rate = rate;
    }
    public String getBank() {
        return bank;
    }
    public void setBank(String bank) {
         this.bank = bank;
    }
    public String getLevelPic() {
        return levelPic;
    }
    public void setLevelPic(String levelPic) {
         this.levelPic = levelPic;
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
	public List<AllLevelRate> getLevelList() {
		return levelList;
	}
	public void setLevelList(List<AllLevelRate> levelList) {
		this.levelList = levelList;
	}
    
    
}

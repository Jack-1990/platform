package cn.seocoo.platform.model;

import  java.util.Date;

public class UserUpgrade {

    private Integer id;
    private String applyCode;
    private String fromLevelCode;
    private String toLevelCode;
    private String attrValue;
    private String attrStyle;
    private String description;
    private String merchantCode;
    private String merchantName;
    private String applyUser;
    private Integer applyStatus;
    private Date applyTime;
    private Date finishTime;
    private String orderNumber;
    private Date duesTime;
    
    private Integer upGrade_id;//升级规则id
    
    private String fromLevelName; // 当前等级名称
    private String toLevelName;// 升级等级名称

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getApplyCode() {
        return applyCode;
    }
    public void setApplyCode(String applyCode) {
         this.applyCode = applyCode;
    }
    public String getFromLevelCode() {
        return fromLevelCode;
    }
    public void setFromLevelCode(String fromLevelCode) {
         this.fromLevelCode = fromLevelCode;
    }
    public String getToLevelCode() {
        return toLevelCode;
    }
    public void setToLevelCode(String toLevelCode) {
         this.toLevelCode = toLevelCode;
    }
  
    public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public String getAttrStyle() {
        return attrStyle;
    }
    public void setAttrStyle(String attrStyle) {
         this.attrStyle = attrStyle;
    }

    public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getMerchantCode() {
        return merchantCode;
    }
    public void setMerchantCode(String merchantCode) {
         this.merchantCode = merchantCode;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
         this.merchantName = merchantName;
    }
    public String getApplyUser() {
        return applyUser;
    }
    public void setApplyUser(String applyUser) {
         this.applyUser = applyUser;
    }
    public Integer getApplyStatus() {
        return applyStatus;
    }
    public void setApplyStatus(Integer applyStatus) {
         this.applyStatus = applyStatus;
    }
    public Date getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(Date applyTime) {
         this.applyTime = applyTime;
    }
    public Date getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(Date finishTime) {
         this.finishTime = finishTime;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
         this.orderNumber = orderNumber;
    }
	public String getFromLevelName()
	{
		return fromLevelName;
	}
	public void setFromLevelName(String fromLevelName)
	{
		this.fromLevelName = fromLevelName;
	}
	public String getToLevelName()
	{
		return toLevelName;
	}
	public void setToLevelName(String toLevelName)
	{
		this.toLevelName = toLevelName;
	}
	public Date getDuesTime()
	{
		return duesTime;
	}
	public void setDuesTime(Date duesTime)
	{
		this.duesTime = duesTime;
	}
	public Integer getUpGrade_id() {
		return upGrade_id;
	}
	public void setUpGrade_id(Integer upGrade_id) {
		this.upGrade_id = upGrade_id;
	}
}

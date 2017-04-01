package cn.seocoo.platform.model;

import  java.util.Date;

public class WithdrawCash {

    private Integer id;
    private String merchantCode;
    private String loginName;
    private Double withdrawAmount;
    private Integer withdrawStatus;
    private Date applyTime;
    private String opreator;
    private Date finishTime;
    private String merchantName;
    private String bankName;
    private String cardNumber;
    
	// 临时字段 分页
	private Integer currentPage;

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
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
         this.loginName = loginName;
    }
    public Double getWithdrawAmount() {
        return withdrawAmount;
    }
    public void setWithdrawAmount(Double withdrawAmount) {
         this.withdrawAmount = withdrawAmount;
    }
    public Integer getWithdrawStatus() {
        return withdrawStatus;
    }
    public void setWithdrawStatus(Integer withdrawStatus) {
         this.withdrawStatus = withdrawStatus;
    }
    public Date getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(Date applyTime) {
         this.applyTime = applyTime;
    }
    public String getOpreator() {
        return opreator;
    }
    public void setOpreator(String opreator) {
         this.opreator = opreator;
    }
    public Date getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(Date finishTime) {
         this.finishTime = finishTime;
    }
	public Integer getCurrentPage()
	{
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage)
	{
		this.currentPage = currentPage;
	}
	public String getMerchantName()
	{
		return merchantName;
	}
	public void setMerchantName(String merchantName)
	{
		this.merchantName = merchantName;
	}
	public String getBankName()
	{
		return bankName;
	}
	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}
	public String getCardNumber()
	{
		return cardNumber;
	}
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}
    
    
}

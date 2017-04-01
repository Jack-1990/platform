package cn.seocoo.platform.model;

import  java.util.Date;

/**
 * 用户分润信息类
 * @author Administrator
 *
 */
public class PaymentProfit {

    private Integer id;
    private String orderNumber;//订单号
    private Double totalOrderPrice;//订单总额
    private Double profitAmount;//分润总额
    private Double userProfitPrice;//当前用户得到的分润总额
    private String profitUser;//当前分润的用户
    private Double intoRate;//当前分润用户的进件费率
    private Double tradeRate;//当前分润用户的代理费率
    private String nextUser;//下级用户
    private Double nextUserIntoRate;//下级分润用户的进件费率
    private Double nextUserTradeRate;//下级分润用户的代理费率
    private Double profitRate;//获得分润的费利差
    private Integer profitStatus;//分润状态
    private String merchantCode;//商户编码
    private String loginName;//用户的登录账户
    private Date createTime;//创建时间
    private Date updateTime;//修改时间

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
         this.orderNumber = orderNumber;
    }
    public Double getTotalOrderPrice() {
        return totalOrderPrice;
    }
    public void setTotalOrderPrice(Double totalOrderPrice) {
         this.totalOrderPrice = totalOrderPrice;
    }
    public Double getProfitAmount() {
        return profitAmount;
    }
    public void setProfitAmount(Double profitAmount) {
         this.profitAmount = profitAmount;
    }
    public Double getUserProfitPrice() {
        return userProfitPrice;
    }
    public void setUserProfitPrice(Double userProfitPrice) {
         this.userProfitPrice = userProfitPrice;
    }
    public String getProfitUser() {
        return profitUser;
    }
    public void setProfitUser(String profitUser) {
         this.profitUser = profitUser;
    }
    public Double getTradeRate() {
        return tradeRate;
    }
    public void setTradeRate(Double tradeRate) {
         this.tradeRate = tradeRate;
    }
    public String getNextUser() {
        return nextUser;
    }
    public void setNextUser(String nextUser) {
         this.nextUser = nextUser;
    }
    public Double getNextUserTradeRate() {
        return nextUserTradeRate;
    }
    public void setNextUserTradeRate(Double nextUserTradeRate) {
         this.nextUserTradeRate = nextUserTradeRate;
    }
    public Double getProfitRate() {
        return profitRate;
    }
    public void setProfitRate(Double profitRate) {
         this.profitRate = profitRate;
    }
    public Integer getProfitStatus() {
        return profitStatus;
    }
    public void setProfitStatus(Integer profitStatus) {
         this.profitStatus = profitStatus;
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
	public Double getIntoRate() {
		return intoRate;
	}
	public void setIntoRate(Double intoRate) {
		this.intoRate = intoRate;
	}
	public Double getNextUserIntoRate() {
		return nextUserIntoRate;
	}
	public void setNextUserIntoRate(Double nextUserIntoRate) {
		this.nextUserIntoRate = nextUserIntoRate;
	}
}

package cn.seocoo.platform.model;

import  java.util.Date;

public class MerchantRate {

    private Integer id;
    private String merchantCode;
    private String payChannel;
    private Double intoRate;
    private Double tradeRate;
    private Date createTime;
    private Date updateTime;

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
    public String getPayChannel() {
        return payChannel;
    }
    public void setPayChannel(String payChannel) {
         this.payChannel = payChannel;
    }
    public Double getIntoRate() {
        return intoRate;
    }
    public void setIntoRate(Double intoRate) {
         this.intoRate = intoRate;
    }
    public Double getTradeRate() {
        return tradeRate;
    }
    public void setTradeRate(Double tradeRate) {
         this.tradeRate = tradeRate;
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
}

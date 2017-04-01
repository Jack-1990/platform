package cn.seocoo.platform.model;

import  java.util.Date;

public class UserUpgradeOrder {

    private Integer id;
    private String orderNumber;
    private Double orderPrice;
    private String channel;
    private String paymentChannel;
    private String orderUser;
    private String merchantCode;
    private Date orderDate;
    private String status;
    private Integer upGrade_id;
    private String transSeq;
    private String retnISeq;
    private Date paydate;
    private Date callbackDate;
    private String orderRemark;

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
    public Double getOrderPrice() {
        return orderPrice;
    }
    public void setOrderPrice(Double orderPrice) {
         this.orderPrice = orderPrice;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
         this.channel = channel;
    }
    public String getPaymentChannel() {
        return paymentChannel;
    }
    public void setPaymentChannel(String paymentChannel) {
         this.paymentChannel = paymentChannel;
    }
    public String getOrderUser() {
        return orderUser;
    }
    public void setOrderUser(String orderUser) {
         this.orderUser = orderUser;
    }
    public String getMerchantCode() {
        return merchantCode;
    }
    public void setMerchantCode(String merchantCode) {
         this.merchantCode = merchantCode;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
         this.orderDate = orderDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
         this.status = status;
    }
    public Integer getUpGrade_id() {
        return upGrade_id;
    }
    public void setUpGrade_id(Integer upGrade_id) {
         this.upGrade_id = upGrade_id;
    }
    public String getTransSeq() {
        return transSeq;
    }
    public void setTransSeq(String transSeq) {
         this.transSeq = transSeq;
    }
    public String getRetnISeq() {
        return retnISeq;
    }
    public void setRetnISeq(String retnISeq) {
         this.retnISeq = retnISeq;
    }
    public Date getPaydate() {
        return paydate;
    }
    public void setPaydate(Date paydate) {
         this.paydate = paydate;
    }
    public Date getCallbackDate() {
        return callbackDate;
    }
    public void setCallbackDate(Date callbackDate) {
         this.callbackDate = callbackDate;
    }
    public String getOrderRemark() {
        return orderRemark;
    }
    public void setOrderRemark(String orderRemark) {
         this.orderRemark = orderRemark;
    }
}

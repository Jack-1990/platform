package cn.seocoo.platform.model;

import  java.util.Date;

public class PrintRecord {

    private Integer id;
    private String merchantCode;
    private String boxMac;
    private String orderNumber;
    private Date recordDate;
    private String printIp;
    private String snCode;

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
    public String getBoxMac() {
        return boxMac;
    }
    public void setBoxMac(String boxMac) {
         this.boxMac = boxMac;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
         this.orderNumber = orderNumber;
    }
    public Date getRecordDate() {
        return recordDate;
    }
    public void setRecordDate(Date recordDate) {
         this.recordDate = recordDate;
    }
    public String getPrintIp() {
        return printIp;
    }
    public void setPrintIp(String printIp) {
         this.printIp = printIp;
    }
    public String getSnCode() {
        return snCode;
    }
    public void setSnCode(String snCode) {
         this.snCode = snCode;
    }
}

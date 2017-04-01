package cn.seocoo.platform.model;

import  java.util.Date;

public class PrintFalse {

    private Integer id;
    private String merchantCode;
    private String boxMac;
    private Integer status;
    private Date createTime;

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
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
         this.status = status;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
         this.createTime = createTime;
    }
}

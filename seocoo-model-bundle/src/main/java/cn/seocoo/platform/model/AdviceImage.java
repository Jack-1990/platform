package cn.seocoo.platform.model;

import  java.util.Date;

public class AdviceImage {

    private Integer id;
    private String merchantCode;
    private String picUrl;
    private Integer seq;
    private String jumpUrl;

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
    public String getPicUrl() {
        return picUrl;
    }
    public void setPicUrl(String picUrl) {
         this.picUrl = picUrl;
    }
    public Integer getSeq() {
        return seq;
    }
    public void setSeq(Integer seq) {
         this.seq = seq;
    }
    public String getJumpUrl() {
        return jumpUrl;
    }
    public void setJumpUrl(String jumpUrl) {
         this.jumpUrl = jumpUrl;
    }
}

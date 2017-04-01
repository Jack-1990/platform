package cn.seocoo.platform.model;

import  java.util.Date;

public class RateSku {

    private Integer id;
    private String skuCode;
    private Double setRate;
    private Double intoRate;
    private Integer seq;
    private String groupCode;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getSkuCode() {
        return skuCode;
    }
    public void setSkuCode(String skuCode) {
         this.skuCode = skuCode;
    }
    public Double getSetRate() {
        return setRate;
    }
    public void setSetRate(Double setRate) {
         this.setRate = setRate;
    }
    public Double getIntoRate() {
        return intoRate;
    }
    public void setIntoRate(Double intoRate) {
         this.intoRate = intoRate;
    }
    public Integer getSeq() {
        return seq;
    }
    public void setSeq(Integer seq) {
         this.seq = seq;
    }
    public String getGroupCode() {
        return groupCode;
    }
    public void setGroupCode(String groupCode) {
         this.groupCode = groupCode;
    }
}

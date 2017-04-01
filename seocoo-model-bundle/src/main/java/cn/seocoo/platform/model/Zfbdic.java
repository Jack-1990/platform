package cn.seocoo.platform.model;

import  java.util.Date;

public class Zfbdic {

    private Integer id;
    private String firstCategory;
    private String secondCategory;
    private String thirdCategory;
    private String certificationType;
    private String categoryId;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getFirstCategory() {
        return firstCategory;
    }
    public void setFirstCategory(String firstCategory) {
         this.firstCategory = firstCategory;
    }
    public String getSecondCategory() {
        return secondCategory;
    }
    public void setSecondCategory(String secondCategory) {
         this.secondCategory = secondCategory;
    }
    public String getThirdCategory() {
        return thirdCategory;
    }
    public void setThirdCategory(String thirdCategory) {
         this.thirdCategory = thirdCategory;
    }
    public String getCertificationType() {
        return certificationType;
    }
    public void setCertificationType(String certificationType) {
         this.certificationType = certificationType;
    }
    public String getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
         this.categoryId = categoryId;
    }
}

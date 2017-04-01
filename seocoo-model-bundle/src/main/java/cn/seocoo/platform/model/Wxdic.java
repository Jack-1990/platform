package cn.seocoo.platform.model;

import  java.util.Date;

public class Wxdic {

    private Integer id;
    private String merchantType;
    private String industry;
    private String category;
    private Integer categoryID;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getMerchantType() {
        return merchantType;
    }
    public void setMerchantType(String merchantType) {
         this.merchantType = merchantType;
    }
    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
         this.industry = industry;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
         this.category = category;
    }
    public Integer getCategoryID() {
        return categoryID;
    }
    public void setCategoryID(Integer categoryID) {
         this.categoryID = categoryID;
    }
}

package cn.seocoo.platform.model;


import  java.util.Date;

public class Validatecode {

    private Integer id;
    private String phone;
    private Integer validateCode;
    private Date generateTime;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
         this.phone = phone;
    }
    public Integer getValidateCode() {
        return validateCode;
    }
    public void setValidateCode(Integer validateCode) {
         this.validateCode = validateCode;
    }
    public Date getGenerateTime() {
        return generateTime;
    }
    public void setGenerateTime(Date generateTime) {
         this.generateTime = generateTime;
    }
}

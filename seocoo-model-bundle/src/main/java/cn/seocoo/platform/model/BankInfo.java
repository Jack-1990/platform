package cn.seocoo.platform.model;


public class BankInfo {

    private Integer id;
    private String bankCode;
    private String bankName;
    private Integer bankId;
    private String areaCode;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
         this.bankCode = bankCode;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
         this.bankName = bankName;
    }
    public Integer getBankId() {
        return bankId;
    }
    public void setBankId(Integer bankId) {
         this.bankId = bankId;
    }
    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
         this.areaCode = areaCode;
    }
}

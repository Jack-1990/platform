package cn.seocoo.platform.model;

import  java.util.Date;

public class CreateMerchantLog {

    private Integer id;
    private String service;
    private String txnSeq;
    private String platformId;
    private String operId;
    private String outMchntId;
    private String cmbcMchntId;
    private String respCode;
    private String errorMsg;
    private String message;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getService() {
        return service;
    }
    public void setService(String service) {
         this.service = service;
    }
    public String getTxnSeq() {
        return txnSeq;
    }
    public void setTxnSeq(String txnSeq) {
         this.txnSeq = txnSeq;
    }
    public String getPlatformId() {
        return platformId;
    }
    public void setPlatformId(String platformId) {
         this.platformId = platformId;
    }
    public String getOperId() {
        return operId;
    }
    public void setOperId(String operId) {
         this.operId = operId;
    }
    public String getOutMchntId() {
        return outMchntId;
    }
    public void setOutMchntId(String outMchntId) {
         this.outMchntId = outMchntId;
    }
    public String getCmbcMchntId() {
        return cmbcMchntId;
    }
    public void setCmbcMchntId(String cmbcMchntId) {
         this.cmbcMchntId = cmbcMchntId;
    }
    public String getRespCode() {
        return respCode;
    }
    public void setRespCode(String respCode) {
         this.respCode = respCode;
    }
    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
         this.errorMsg = errorMsg;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
         this.message = message;
    }
}

package cn.seocoo.platform.msbank;

/**
 * 民生银行接口绑定支付通道信息类
 */
public class BindPayChannel {

    private String txnSeq;
    private String platformId;
    private String operId;
    private String outMchntId;
    private String cmbcMchntId;
    private String industryId;
    private String dayLimit;
    private String monthLimit;
    private String fixFeeRate;
    private String specFeeRate;
    private String account;
    private String pbcBankId;
    private String acctName;
    private String message;
    private String apiCode;
    private String operateType;
    private String acctType;
	private String cmbcSignId;
    
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
	public String getIndustryId() {
		return industryId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getDayLimit() {
		return dayLimit;
	}
	public void setDayLimit(String dayLimit) {
		this.dayLimit = dayLimit;
	}
	public String getMonthLimit() {
		return monthLimit;
	}
	public void setMonthLimit(String monthLimit) {
		this.monthLimit = monthLimit;
	}
	public String getFixFeeRate() {
		return fixFeeRate;
	}
	public void setFixFeeRate(String fixFeeRate) {
		this.fixFeeRate = fixFeeRate;
	}
	public String getSpecFeeRate() {
		return specFeeRate;
	}
	public void setSpecFeeRate(String specFeeRate) {
		this.specFeeRate = specFeeRate;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPbcBankId() {
		return pbcBankId;
	}
	public void setPbcBankId(String pbcBankId) {
		this.pbcBankId = pbcBankId;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getApiCode() {
		return apiCode;
	}
	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getCmbcSignId()
	{
		return cmbcSignId;
	}

	public void setCmbcSignId(String cmbcSignId)
	{
		this.cmbcSignId = cmbcSignId;
	}
	
}

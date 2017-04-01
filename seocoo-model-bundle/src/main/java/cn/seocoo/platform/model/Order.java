package cn.seocoo.platform.model;

import  java.util.Date;

public class Order {

    private Integer id;
    private String platformId;
    private String merchantNo;
    private String selectTradeType;
    private String amount;
    private String orderNumber;
    private String merchantSeq;
    private String orderInfo;
    private Date transTime;
    private String notifyUrl;
    private String remark;
    private String payCode;
    private Integer status;
    private String merchantCode;
    private String channel;
    private Date finnishTime;
    private Integer scanStyle;
    private String payChannel;
    
    //付款后 notify_url异步通知接口接受信息
    private String bankTradeNo;//银行流水号
    private String cardNo;//卡号 (前六后四中间*)
    private String cbCode;//发卡行行号
    private String cardName;//发卡行行名
    private String centerInfo;//其他信息
    private String serialNo;//收单流水号
    private String voucherNo;// 收单凭证号
    
	private Boolean isShowTuiKuan; // 是否显示退款
	
	//退款操作信息
	private String orderAmount;//退款金额
	private String orderNote;//退款说明
	private String reserve;//退款备注信息
	private String refundOperator;//退款操作人员
	

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getPlatformId() {
        return platformId;
    }
    public void setPlatformId(String platformId) {
         this.platformId = platformId;
    }
    public String getMerchantNo() {
        return merchantNo;
    }
    public void setMerchantNo(String merchantNo) {
         this.merchantNo = merchantNo;
    }
    public String getSelectTradeType() {
        return selectTradeType;
    }
    public void setSelectTradeType(String selectTradeType) {
         this.selectTradeType = selectTradeType;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
         this.amount = amount;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
         this.orderNumber = orderNumber;
    }
    public String getMerchantSeq() {
        return merchantSeq;
    }
    public void setMerchantSeq(String merchantSeq) {
         this.merchantSeq = merchantSeq;
    }
    public String getOrderInfo() {
        return orderInfo;
    }
    public void setOrderInfo(String orderInfo) {
         this.orderInfo = orderInfo;
    }
    public Date getTransTime() {
        return transTime;
    }
    public void setTransTime(Date transTime) {
         this.transTime = transTime;
    }
    public String getNotifyUrl() {
        return notifyUrl;
    }
    public void setNotifyUrl(String notifyUrl) {
         this.notifyUrl = notifyUrl;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
         this.remark = remark;
    }
    public String getPayCode() {
        return payCode;
    }
    public void setPayCode(String payCode) {
         this.payCode = payCode;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
         this.status = status;
    }
    public String getMerchantCode() {
        return merchantCode;
    }
    public void setMerchantCode(String merchantCode) {
         this.merchantCode = merchantCode;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
         this.channel = channel;
    }
    public Date getFinnishTime() {
        return finnishTime;
    }
    public void setFinnishTime(Date finnishTime) {
         this.finnishTime = finnishTime;
    }
    public Integer getScanStyle() {
        return scanStyle;
    }
    public void setScanStyle(Integer scanStyle) {
         this.scanStyle = scanStyle;
    }
    public String getPayChannel() {
        return payChannel;
    }
    public void setPayChannel(String payChannel) {
         this.payChannel = payChannel;
    }
	public String getBankTradeNo() {
		return bankTradeNo;
	}
	public void setBankTradeNo(String bankTradeNo) {
		this.bankTradeNo = bankTradeNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCbCode() {
		return cbCode;
	}
	public void setCbCode(String cbCode) {
		this.cbCode = cbCode;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCenterInfo() {
		return centerInfo;
	}
	public void setCenterInfo(String centerInfo) {
		this.centerInfo = centerInfo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public Boolean getIsShowTuiKuan()
	{
		return isShowTuiKuan;
	}
	public void setIsShowTuiKuan(Boolean isShowTuiKuan)
	{
		this.isShowTuiKuan = isShowTuiKuan;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderNote() {
		return orderNote;
	}
	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public String getRefundOperator() {
		return refundOperator;
	}
	public void setRefundOperator(String refundOperator) {
		this.refundOperator = refundOperator;
	}

}

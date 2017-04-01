package cn.seocoo.platform.model;

import  java.util.Date;

/**
 * .net插件sn码
 */
public class MerchantNetSn {

    private Integer id;
    private String groupCode;
    private String merchantCode;
    private String snCode;
    private Integer activated;
    private String merchantName;
    

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getGroupCode() {
        return groupCode;
    }
    public void setGroupCode(String groupCode) {
         this.groupCode = groupCode;
    }
    public String getMerchantCode() {
        return merchantCode;
    }
    public void setMerchantCode(String merchantCode) {
         this.merchantCode = merchantCode;
    }
    public String getSnCode() {
        return snCode;
    }
    public void setSnCode(String snCode) {
         this.snCode = snCode;
    }
    public Integer getActivated() {
        return activated;
    }
    public void setActivated(Integer activated) {
         this.activated = activated;
    }
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
}

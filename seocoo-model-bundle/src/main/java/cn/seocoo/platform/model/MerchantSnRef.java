package cn.seocoo.platform.model;


public class MerchantSnRef {

    private Integer id;
    private String groupCode;
    private String merchantCode;
    private String snCode;
    private Integer activated;

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
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
    
}

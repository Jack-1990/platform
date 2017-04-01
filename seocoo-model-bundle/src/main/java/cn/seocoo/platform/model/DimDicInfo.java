package cn.seocoo.platform.model;

import java.util.List;

public class DimDicInfo {

    private Integer id;
    private String code;
    private String attrCode;
    private String attrName;
    private Integer attrValue;
    private String logo ;
    private Integer seq;
    
    private int count ; //计算该等级下的个数
    
    // 等级规则
    private List<Upgrade> upgradeList;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
         this.code = code;
    }
    public String getAttrCode() {
        return attrCode;
    }
    public void setAttrCode(String attrCode) {
         this.attrCode = attrCode;
    }
    public String getAttrName() {
        return attrName;
    }
    public void setAttrName(String attrName) {
         this.attrName = attrName;
    }
    public Integer getAttrValue() {
        return attrValue;
    }
    public void setAttrValue(Integer attrValue) {
         this.attrValue = attrValue;
    }
    public Integer getSeq() {
        return seq;
    }
    public void setSeq(Integer seq) {
         this.seq = seq;
    }
	public List<Upgrade> getUpgradeList()
	{
		return upgradeList;
	}
	public void setUpgradeList(List<Upgrade> upgradeList)
	{
		this.upgradeList = upgradeList;
	}
	public String getLogo()
	{
		return logo;
	}
	public void setLogo(String logo)
	{
		this.logo = logo;
	}
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
    
    
}

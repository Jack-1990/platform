package cn.seocoo.platform.model;

import  java.util.Date;

/**
 * 用户升级信息基类
 * @author Administrator
 *
 */
public class Upgrade {

    private Integer id;
    private String fromLevelCode;//当前会员等级code
    private String toLevelCode;//所要升级到的会员等级
    private String attrValue;//对应达标的属性值
    private String attrStyle;//达标的类型
    private String description;//对应达标的属性描述
    private Date createTime;//创建时间
    private Date updateTime;//修改时间
    private Integer seq;//排序
    

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getFromLevelCode() {
        return fromLevelCode;
    }
    public void setFromLevelCode(String fromLevelCode) {
         this.fromLevelCode = fromLevelCode;
    }
    public String getToLevelCode() {
        return toLevelCode;
    }
    public void setToLevelCode(String toLevelCode) {
         this.toLevelCode = toLevelCode;
    }
    public String getAttrValue() {
        return attrValue;
    }
    public void setAttrValue(String attrValue) {
         this.attrValue = attrValue;
    }
    public String getAttrStyle() {
        return attrStyle;
    }
    public void setAttrStyle(String attrStyle) {
         this.attrStyle = attrStyle;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
         this.description = description;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
         this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
         this.updateTime = updateTime;
    }
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}

package cn.seocoo.platform.model;

import  java.util.Date;

public class RateDimAttr {

    private Integer id;
    private String dimCode;
    private String dimAttrCode;
    private String dimAttrName;
    private Integer seq;
    private String groupCode;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getDimCode() {
        return dimCode;
    }
    public void setDimCode(String dimCode) {
         this.dimCode = dimCode;
    }
    public String getDimAttrCode() {
        return dimAttrCode;
    }
    public void setDimAttrCode(String dimAttrCode) {
         this.dimAttrCode = dimAttrCode;
    }
    public String getDimAttrName() {
        return dimAttrName;
    }
    public void setDimAttrName(String dimAttrName) {
         this.dimAttrName = dimAttrName;
    }
    public Integer getSeq() {
        return seq;
    }
    public void setSeq(Integer seq) {
         this.seq = seq;
    }
    public String getGroupCode() {
        return groupCode;
    }
    public void setGroupCode(String groupCode) {
         this.groupCode = groupCode;
    }
}

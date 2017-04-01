package cn.seocoo.platform.model;

import java.util.List;

public class RateDim {

    private Integer id;
    private String dimCode;
    private String dimName;
    private Integer seq;
    private String groupCode;
    
	private List<RateDimAttr> rateDimAttrList;

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
    public String getDimName() {
        return dimName;
    }
    public void setDimName(String dimName) {
         this.dimName = dimName;
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

	public List<RateDimAttr> getRateDimAttrList()
	{
		return rateDimAttrList;
	}

	public void setRateDimAttrList(List<RateDimAttr> rateDimAttrList)
	{
		this.rateDimAttrList = rateDimAttrList;
	}



}

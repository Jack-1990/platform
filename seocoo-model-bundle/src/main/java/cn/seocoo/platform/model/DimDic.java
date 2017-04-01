package cn.seocoo.platform.model;

public class DimDic {

    private Integer id;
    private String code;
    private String name;
    private Integer seq;

	private DimDicInfo dimDicInfo;

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
    public String getName() {
        return name;
    }
    public void setName(String name) {
         this.name = name;
    }
    public Integer getSeq() {
        return seq;
    }
    public void setSeq(Integer seq) {
         this.seq = seq;
    }

	public DimDicInfo getDimDicInfo()
	{
		return dimDicInfo;
	}

	public void setDimDicInfo(DimDicInfo dimDicInfo)
	{
		this.dimDicInfo = dimDicInfo;
	}

}

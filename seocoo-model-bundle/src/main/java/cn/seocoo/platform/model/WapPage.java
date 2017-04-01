package cn.seocoo.platform.model;

import  java.util.Date;

public class WapPage {

    private Integer id;
    private String pageName;
    private String pageType;
    private String pageLogo;
    private Integer seq;
    private Date createTime;
    private String pageLink;
    private String status;
    
    private String loginName;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getPageName() {
        return pageName;
    }
    public void setPageName(String pageName) {
         this.pageName = pageName;
    }
    public String getPageType() {
        return pageType;
    }
    public void setPageType(String pageType) {
         this.pageType = pageType;
    }
    public Integer getSeq() {
        return seq;
    }
    public void setSeq(Integer seq) {
         this.seq = seq;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
         this.createTime = createTime;
    }
    public String getPageLink() {
        return pageLink;
    }
    public void setPageLink(String pageLink) {
         this.pageLink = pageLink;
    }
	public String getPageLogo() {
		return pageLogo;
	}
	public void setPageLogo(String pageLogo) {
		this.pageLogo = pageLogo;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
    
}

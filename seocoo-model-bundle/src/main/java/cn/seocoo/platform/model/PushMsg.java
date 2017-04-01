package cn.seocoo.platform.model;

import  java.util.Date;

public class PushMsg {

    private Integer id;
    private String msgFrom;
    private String msgCotent;
    private String msgTitle;
    private String target;
    private String terminal;
    private Date createTime;
    private String msgType;
    private String msgUrl;

    //分页
    private Integer beginRow;
    private Integer pageSize;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
    public String getMsgFrom() {
        return msgFrom;
    }
    public void setMsgFrom(String msgFrom) {
         this.msgFrom = msgFrom;
    }
    public String getMsgCotent() {
        return msgCotent;
    }
    public void setMsgCotent(String msgCotent) {
         this.msgCotent = msgCotent;
    }
    public String getMsgTitle() {
        return msgTitle;
    }
    public void setMsgTitle(String msgTitle) {
         this.msgTitle = msgTitle;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
         this.target = target;
    }
    public String getTerminal() {
        return terminal;
    }
    public void setTerminal(String terminal) {
         this.terminal = terminal;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
         this.createTime = createTime;
    }
    public String getMsgType() {
        return msgType;
    }
    public void setMsgType(String msgType) {
         this.msgType = msgType;
    }
	public Integer getBeginRow() {
		return beginRow;
	}
	public void setBeginRow(Integer beginRow) {
		this.beginRow = beginRow;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getMsgUrl() {
		return msgUrl;
	}
	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl;
	}
}

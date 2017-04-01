package cn.seocoo.platform.push;

public class AppPushMsg {

	private String target;//消息接受方
	private String msg; //消息体
	private String msgType; 
	private String terminal;// android,ios
	private  String from ;//消息发送方
	private String merchantCode;// 商家编码
	private String newMsg;//将点哈的数据全部封装到这个里面
	private String msgUrl;//消息链接
	private String msgTitle;//推送消息头
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getNewMsg() {
		return newMsg;
	}
	public void setNewMsg(String newMsg) {
		this.newMsg = newMsg;
	}
	public String getMsgUrl() {
		return msgUrl;
	}
	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
}

package cn.seocoo.platform.unite;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class Message {
	@JSONField(name="SvcCont")
	private List<SvcCont> svcCont;
	@JSONField(name="tcpCont")
	private TcpCont tcpCont;
	public List<SvcCont> getSvcCont() {
		return svcCont;
	}
	public void setSvcCont(List<SvcCont> svcCont) {
		this.svcCont = svcCont;
	}
	public TcpCont getTcpCont() {
		return tcpCont;
	}
	public void setTcpCont(TcpCont tcpCont) {
		this.tcpCont = tcpCont;
	}
	
	
}

package cn.seocoo.platform.unite;

import com.alibaba.fastjson.annotation.JSONField;

public class ResMessage {
	@JSONField(name="SvcCont")
	private ResSvcCont resSvcCont;
	@JSONField(name="tcpCont")
	private TcpCont tcpCont;
	
	/**
	 * @return the resSvcCont
	 */
	public ResSvcCont getResSvcCont() {
		return resSvcCont;
	}
	/**
	 * @param resSvcCont the resSvcCont to set
	 */
	public void setResSvcCont(ResSvcCont resSvcCont) {
		this.resSvcCont = resSvcCont;
	}
	public TcpCont getTcpCont() {
		return tcpCont;
	}
	public void setTcpCont(TcpCont tcpCont) {
		this.tcpCont = tcpCont;
	}
	@Override
	public String toString() {
		return "ResMessage [resSvcCont=" + resSvcCont + ", tcpCont=" + tcpCont
				+ "]";
	}
	
	
}

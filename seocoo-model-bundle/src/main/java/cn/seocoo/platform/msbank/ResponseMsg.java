package cn.seocoo.platform.msbank;

/**
 * 返回密文解密信息类
 *
 */
public class ResponseMsg {

	private String sign;
	private String body;
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}

package cn.seocoo.platform.unite;

import com.alibaba.fastjson.annotation.JSONField;

public class ResSvcCont {

	@JSONField(name="result")
	private String result;

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResSvcCont [result=" + result + "]";
	}
	
}

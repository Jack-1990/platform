package cn.seocoo.platform.unite;

import com.alibaba.fastjson.annotation.JSONField;

public class SvcCont {

	@JSONField(name="PARAMS")
	private String params;

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	
	
}

package cn.seocoo.platform.unite.demo;


import java.util.List;

import cn.seocoo.platform.service.demo.inf.DemoService;
import cn.seocoo.platform.unite.Demo;

import com.tydic.framework.base.web.BaseAction;

public class DemoAction extends BaseAction{
	
	private List<Demo> demoList;
	private DemoService demoService;
	
	public String demo() {

		demoList=demoService.queryAll();
		return "demo";
	}
	public List<Demo> getDemoList() {
		return demoList;
	}
	public void setDemoList(List<Demo> demoList) {
		this.demoList = demoList;
	}
	public DemoService getDemoService() {
		return demoService;
	}
	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}

}

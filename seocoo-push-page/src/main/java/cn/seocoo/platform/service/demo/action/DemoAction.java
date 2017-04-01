package cn.seocoo.platform.service.demo.action;

import java.util.List;
import java.util.Map;

import com.tydic.framework.base.web.BaseAction;

import cn.seocoo.platform.model.SystemConfig;
import cn.seocoo.platform.service.demo.service.inf.DemoService;
import cn.seocoo.platform.service.server.ConnectionPool;
import cn.seocoo.platform.service.server.MsgTcpInfo;
import cn.seocoo.platform.service.systemconfig.service.inf.SystemConfigService;
import cn.seocoo.platform.unite.Demo;

public class DemoAction extends BaseAction{
	private DemoService demoService;
	private List<Demo> demoList;
	private SystemConfigService systemConfigService;
	private List<SystemConfig> systemConfigList;
	
	public String demo(){
//		demoList=demoService.findAll();
//		System.out.println(2222);
//		systemConfigList=systemConfigService.getAllSystemConfig();
//		System.out.println(SystemConfigUtil.getSingleProperty("resourcePath_admin").getPropertyValue());
//		System.out.println("2222");
		
		//在线状态
		 Map<String,MsgTcpInfo>  connections=ConnectionPool.tcpPools;
		 this.request.setAttribute("connections", connections);
		 //消息状态
		 Map msgQuee= ConnectionPool.msgQuee;
		 this.request.setAttribute("msgQuee", msgQuee);
		return "demo";
	}

	public DemoService getDemoService() {
		return demoService;
	}

	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}

	public List<Demo> getDemoList() {
		return demoList;
	}

	public void setDemoList(List<Demo> demoList) {
		this.demoList = demoList;
	}

	public SystemConfigService getSystemConfigService() {
		return systemConfigService;
	}

	public void setSystemConfigService(SystemConfigService systemConfigService) {
		this.systemConfigService = systemConfigService;
	}

	public List<SystemConfig> getSystemConfigList() {
		return systemConfigList;
	}

	public void setSystemConfigList(List<SystemConfig> systemConfigList) {
		this.systemConfigList = systemConfigList;
	}
	
	
}

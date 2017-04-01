package cn.seocoo.platform.service.systemconfig.action;

import java.util.List;

import cn.seocoo.platform.model.SystemConfig;
import cn.seocoo.platform.service.systemconfig.service.inf.SystemConfigService;

public class SystemConfigAction {
	private SystemConfigService systemConfigService;
	private List<SystemConfig> systemConfigList;
	
	public String systemConfig(){
		systemConfigList=systemConfigService.getAllSystemConfig();
		System.out.println("2222");
		return "findAllsuccess";
	}
	
	public SystemConfigService getSystemConfigService(){
		return systemConfigService;
	}
	
	public void setSystemConfigService(SystemConfigService scs){
		this.systemConfigService=scs;
	}
	
	public List<SystemConfig> getSystemConfigList(){
		return systemConfigList;
	}
	
	public void setSystemConfigList(List<SystemConfig> list){
		this.systemConfigList=list;
	}
	
}

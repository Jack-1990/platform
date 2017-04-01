package cn.seocoo.platform.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import cn.seocoo.platform.model.SystemConfig;



public class SystemConfigUtil {
	
	private static Map<String, List<SystemConfig>> systemConfigMap = new HashMap<String, List<SystemConfig>>();

	private static final Logger logger = Logger
			.getLogger(SystemConfigUtil.class);

	/**
	 * 根据参数键获取数据库参数（此方法返回参数集合）
	 * 
	 * @param propertyKey
	 *            参数键
	 * @return 如果存在，返回符合条件的参数集合，否则返回空集合
	 */
	public static List<SystemConfig> getPropertys(String propertyKey) {
		if (!validate(propertyKey)) {
			return Collections.emptyList();
		}
		
		List<SystemConfig> list = systemConfigMap.get(propertyKey);
		if(list == null) {
			return Collections.emptyList();
		}
		
		return list;
	}
	
	/**
	 * 根据参数键获取数据库参数（此方法返回单个系统参数）
	 * 
	 * @param propertyKey
	 *            参数键
	 * @return 如果存在，返回符合条件的系统参数，否则返回null
	 */
	public static SystemConfig getSingleProperty(String propertyKey) {
		if (!validate(propertyKey)) {
			return null;
		}
		List<SystemConfig> systemConfigList = systemConfigMap.get(propertyKey);
		if(CollectionUtils.isEmpty(systemConfigList)){
			return null;
		}
		return systemConfigList.get(0);
	}

	/**
	 * 判断参数键是否不为空
	 * 
	 * @param propertyKey
	 *            参数键
	 * @return 如果参数键为空，返回false，否则返回true
	 */
	private static boolean validate(String propertyKey) {
		if (StringUtils.isBlank(propertyKey)) {
			logger.error("logger is blank");
			return false;
		}
		return true;
	}

	public static Map<String, List<SystemConfig>> getSystemConfigMap() {
		return systemConfigMap;
	}

	public static void setSystemConfigMap(
			Map<String, List<SystemConfig>> systemConfigMap) {
		SystemConfigUtil.systemConfigMap = systemConfigMap;
	}

	/**
	 * 判断集约化开关
	 * @return
	 */
	public static boolean  isOpenGroup(){
		boolean isOpen=false;
		if("1".equals(systemConfigMap.get("GROUP_SWITCH").get(0).getPropertyValue())){
			isOpen=true;
		}
		return isOpen;
	}
}

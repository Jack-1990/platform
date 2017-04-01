package cn.seocoo.platform.dao.systemconfig.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.seocoo.platform.dao.systemconfig.inf.SystemConfigDao;
import cn.seocoo.platform.model.SystemConfig;

import com.tydic.framework.base.dao.EntityManagerImpl;
import com.tydic.framework.base.exception.ServiceException;
import com.tydic.wss.util.PageUtil;

public class SystemConfigDaoImpl extends EntityManagerImpl<SystemConfig, Integer> implements SystemConfigDao{
	
	private static final Logger logger = Logger
			.getLogger(SystemConfigDaoImpl.class);

	public void save(SystemConfig systemConfig) throws ServiceException {
		entityDao.save("SystemConfig.save", systemConfig);
	}

	public void update(SystemConfig systemConfig) throws ServiceException {
		entityDao.update("SystemConfig.update", systemConfig);
	}

	public void delete(int id) throws ServiceException {
		entityDao.remove("SystemConfig.delete", id);
	}

	public SystemConfig getSystemConfig(int id) throws ServiceException {
		return entityDao.findById("SystemConfig.selectById", id);
	}

	public List<SystemConfig> getSystemConfigList(String key)
			throws ServiceException {
		return entityDao.findBy("SystemConfig.selectByKey", key);
	}
	
	public PageUtil searchForPagination(PageUtil page) throws ServiceException {
		page.setTotleRow((Integer) entityDao.findMap(
				"SystemConfig.searchOfTotal", page.getParam()));
		page.setList(entityDao.find("SystemConfig.searchOfLimit",
				page.getParam()));
		return page;
	}

	public List<SystemConfig> getAllSystemConfig() {
		return entityDao.findAll("SystemConfig.selectAll");
	}

	public List<SystemConfig> getSystemConfigByKeys(List<String> keyList)
			throws ServiceException {
		Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
		paramMap.put("keyList", keyList);
		return entityDao.findBy("SystemConfig.getSystemConfigByKeys", paramMap);
	}

	public SystemConfig getSystemConfig(String key, int index)
			throws ServiceException {
		SystemConfig sc = new SystemConfig();
		sc.setPropertyKey(key);
		sc.setPropertyIndex(index);
		return entityDao.findObj("SystemConfig.selectByKeyAndIndex", sc);
	}
	
}

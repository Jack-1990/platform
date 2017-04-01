package cn.seocoo.platform.service.systemconfig.impl;

import java.util.List;

import cn.seocoo.platform.dao.systemconfig.inf.SystemConfigDao;
import cn.seocoo.platform.model.SystemConfig;
import cn.seocoo.platform.service.systemconfig.inf.SystemConfigService;

import com.tydic.framework.base.exception.ServiceException;
import com.tydic.wss.util.PageUtil;


public class SystemConfigServiceImpl implements SystemConfigService{
	
	private SystemConfigDao systemConfigDao;

	@Override
	public void save(SystemConfig systemConfig) throws ServiceException {
		// TODO Auto-generated method stub
		systemConfigDao.save(systemConfig);
	}

	@Override
	public void update(SystemConfig systemConfig) throws ServiceException {
		// TODO Auto-generated method stub
		systemConfigDao.update(systemConfig);
	}

	@Override
	public void delete(int id) throws ServiceException {
		// TODO Auto-generated method stub
		systemConfigDao.delete(id);
	}

	@Override
	public SystemConfig getSystemConfig(int id) throws ServiceException {
		// TODO Auto-generated method stub
		return systemConfigDao.getSystemConfig(id);
	}

	@Override
	public List<SystemConfig> getSystemConfigList(String key)
			throws ServiceException {
		// TODO Auto-generated method stub
		return systemConfigDao.getSystemConfigList(key);
	}

	@Override
	public PageUtil searchForPagination(PageUtil page) throws ServiceException {
		// TODO Auto-generated method stub
		return systemConfigDao.searchForPagination(page);
	}

	@Override
	public List<SystemConfig> getAllSystemConfig() {
		// TODO Auto-generated method stub
		return systemConfigDao.getAllSystemConfig();
	}

	@Override
	public List<SystemConfig> getSystemConfigByKeys(List<String> keyList)
			throws ServiceException {
		// TODO Auto-generated method stub
		return systemConfigDao.getSystemConfigByKeys(keyList);
	}

	@Override
	public SystemConfig getSystemConfig(String key, int index)
			throws ServiceException {
		// TODO Auto-generated method stub
		return systemConfigDao.getSystemConfig(key, index);
	}

	public SystemConfigDao getSystemConfigDao() {
		return systemConfigDao;
	}

	public void setSystemConfigDao(SystemConfigDao systemConfigDao) {
		this.systemConfigDao = systemConfigDao;
	}
	
	
}

package cn.seocoo.platform.service.systemconfig.inf;

import java.util.List;

import cn.seocoo.platform.model.SystemConfig;

import com.tydic.framework.base.exception.ServiceException;
import com.tydic.wss.util.PageUtil;


public interface SystemConfigService {

	/**
	 * 增
	 */
	public void save(SystemConfig systemConfig) throws ServiceException;
	
	/**
	 * 改
	 */
	public void update(SystemConfig systemConfig) throws ServiceException;
	
	/**
	 * 删
	 */
	public void delete(int id) throws ServiceException;
	
	/**
	 * 根据系统参数id获取系统参数
	 * @param id 系统参数id
	 * @return 如果存在，返回符合条件的系统参数，否则返回null
	 */
	public SystemConfig getSystemConfig(int id) throws ServiceException;
	
	/**
	 * 根据系统参数键获取系统参数
	 * @param key 系统参数键
	 * @return 如果存在，返回符合条件的系统参数集合，否则返回空集合
	 */
	public List<SystemConfig> getSystemConfigList(String key) throws ServiceException;
	
	/**
	 * 分页获取系统参数
	 * @param page 分页信息
	 * @return 如果存在，返回包含符合条件的系统参数集合的分页信息，否则返回包含空集合的分页信息
	 * @throws ServiceException
	 */
	public PageUtil searchForPagination(PageUtil page) throws ServiceException;
	
	/**
	 * 获取所有系统参数
	 * @return 如果存在，返回所有系统参数对象集合，否则返回空集合
	 */
	public List<SystemConfig> getAllSystemConfig();
	
	/**
	 *  根据参数键集合查询符合条件的系统参数
	 * @param keyList 参数键集合
	 * @return 如果存在，返回所有系统参数对象集合，否则返回空集合
	 */
	public List<SystemConfig> getSystemConfigByKeys(List<String> keyList) throws ServiceException;
	
	/**
	 * 根据参数键和索引获取系统参数
	 * @param key 参数键
	 * @param index 参数索引
	 * @return 如果存在，返回符合条件的系统参数，否则返回null
	 */
	public SystemConfig getSystemConfig(String key, int index) throws ServiceException;
	
	
	
}

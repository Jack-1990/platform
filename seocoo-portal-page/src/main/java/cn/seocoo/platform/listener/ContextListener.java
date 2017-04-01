package cn.seocoo.platform.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.seocoo.platform.common.util.SpringHelper;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.SystemConfig;
import cn.seocoo.platform.service.systemconfig.inf.SystemConfigService;

import com.danga.MemCached.SockIOPool;
import com.tydic.framework.util.PropertyUtil;


public class ContextListener implements ServletContextListener {

	private static final Logger logger = Logger
			.getLogger(ContextListener.class);

	private ServletContext servletContext;

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent event) {
		servletContext = event.getServletContext();
		//初始化spring
		this.initSpringHelper(event);
		// 初始化数据库系统参数
		initSystemConfig();
		// 初始化全局配置
		initServletConfig();
		// 初始话MemCachedClient配置
		try {
			PropertyUtil.readConfig(this.getClass().getResourceAsStream(
					"/cache.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initMemCachedClient();
	}

	/**
	 * 初始化应用级别参数
	 */
	private void initServletConfig() {
		// 全局保存资源路径
		String resourcePath=SystemConfigUtil.getSingleProperty("RESOURCES_PATH_MANAGE").getPropertyValue();
		servletContext.setAttribute("resourcePath", resourcePath);
	}

	/**
	 * 初始化springHelper <功能详细描述>
	 * 
	 * @param context
	 * @see [类、类#方法、类#成员]
	 */
	public void initSpringHelper(ServletContextEvent event) {
		ApplicationContext springContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(event.getServletContext());
		SpringHelper.getSpringHelper().initApplicationContext(springContext);
	}
	/**
	 * 初始化数据库系统参数
	 */
	private void initSystemConfig() {
		SystemConfigService systemConfigService = (SystemConfigService) SpringHelper
				.getSpringHelper().getBean("systemConfigService");
		// 获取所有的系统配置
		List<SystemConfig> systemConfigList = systemConfigService
				.getAllSystemConfig();
		Map<String, List<SystemConfig>> systemConfigMap = SystemConfigUtil
				.getSystemConfigMap();
		for (SystemConfig systemConfig : systemConfigList) {
			String key = systemConfig.getPropertyKey();
			List<SystemConfig> list = systemConfigMap.get(key);
			if (list == null) {
				list = new ArrayList<SystemConfig>();
				systemConfigMap.put(key, list);
			}
			list.add(systemConfig);
		}
		SystemConfigUtil.setSystemConfigMap(systemConfigMap);
	}

	/**
	 * 初始化memCachedClient
	 */
	private void initMemCachedClient() {
		String servers[] = getServers(PropertyUtil
				.getProperty("wss.cache.list"));
		Integer weights[] = getWeights(PropertyUtil
				.getProperty("wss.cache.list"));
		int initConn = Integer.parseInt(PropertyUtil
				.getProperty("wss.cache.initConn"));
		int minConn = Integer.parseInt(PropertyUtil
				.getProperty("wss.cache.minConn"));
		int maxConn = Integer.parseInt(PropertyUtil
				.getProperty("wss.cache.maxConn"));
		boolean isAliveCheck = Boolean.parseBoolean(PropertyUtil
				.getProperty("wss.cache.isAliveCheck"));
		int maintSleep = Integer.parseInt(PropertyUtil
				.getProperty("wss.cache.maintSleep"));
		boolean nagle = Boolean.parseBoolean(PropertyUtil
				.getProperty("wss.cache.nagl"));
		int socketTO = Integer.parseInt(PropertyUtil
				.getProperty("wss.cache.socketTO"));
		int socketConnectTO = Integer.parseInt(PropertyUtil
				.getProperty("wss.cache.socketConnectTO"));
		int idelTime = Integer.parseInt(PropertyUtil
				.getProperty("wss.cache.idelTime"));
		int busyTime = Integer.parseInt(PropertyUtil
				.getProperty("wss.cache.busyTime"));
		boolean failover = Boolean.parseBoolean(PropertyUtil
				.getProperty("wss.cache.failover"));
		boolean failback = Boolean.parseBoolean(PropertyUtil
				.getProperty("wss.cache.failback"));
		int hashAlg = Integer.parseInt(PropertyUtil
				.getProperty("wss.cache.hashAlg"));
		SockIOPool pool = SockIOPool.getInstance();
		if (pool.isInitialized()) {
			pool.shutDown();
		}
		pool.setServers(servers);
		pool.setWeights(weights);
		pool.setInitConn(initConn);
		pool.setMinConn(minConn);
		pool.setMaxConn(maxConn);
		pool.setAliveCheck(isAliveCheck);
		pool.setMaintSleep(maintSleep);
		pool.setNagle(nagle);
		pool.setSocketTO(socketTO);
		pool.setSocketConnectTO(socketConnectTO);
		pool.setMaxIdle(idelTime);
		pool.setMaxBusyTime(busyTime);
		pool.setFailover(failover);
		pool.setFailback(failback);
		pool.setHashingAlg(hashAlg);
		if (!pool.isInitialized()) {
			pool.initialize();
		}
	}
	/**
	 * 获取memCache服务器路径
	 * @param s 以分号分隔的多个服务器路径，如(134.64.75.8\:11211,10;134.64.75.8\:11212,10;134.64.75.8\:11213)分别是：服务器:端口,权重
	 * @return 服务器数组
	 */
	private String[] getServers(String s) {
		String str[] = s.split(";");
		String sa[] = new String[str.length];
		for (int i = 0; i < str.length; i++) {
			sa[i] = str[i].split(",")[0];
		}
		return sa;
	}

	/**
	 * 获取服务器的权重
	 * @param s 以分号分隔的多个服务器路径，如(134.64.75.8\:11211,10;134.64.75.8\:11212,10;134.64.75.8\:11213)分别是：服务器:端口,权重
	 * @return 服务器的权重数组
	 */
	private Integer[] getWeights(String s) {
		String str[] = s.split(";");
		Integer sa[] = new Integer[str.length];
		for (int i = 0; i < str.length; i++) {
			sa[i] = Integer.valueOf(Integer.parseInt(str[i].split(",")[1]));
		}
		return sa;
	}
}

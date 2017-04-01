package cn.seocoo.platform.service.demo.impl;

import java.util.List;

import cn.seocoo.platform.dao.demo.inf.DemoDao;
import cn.seocoo.platform.service.demo.inf.DemoService;
import cn.seocoo.platform.unite.Demo;


public class DemoServiceImpl implements DemoService{

	private DemoDao demoDao;
	@Override
	public List<Demo> queryAll() {
		// TODO Auto-generated method stub
		return demoDao.queryAll();
	}
	public DemoDao getDemoDao() {
		return demoDao;
	}
	public void setDemoDao(DemoDao demoDao) {
		this.demoDao = demoDao;
	}

}

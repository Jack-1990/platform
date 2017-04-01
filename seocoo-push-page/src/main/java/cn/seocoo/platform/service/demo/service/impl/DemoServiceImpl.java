package cn.seocoo.platform.service.demo.service.impl;

import java.util.List;

import cn.seocoo.platform.service.demo.dao.inf.DemoDao;
import cn.seocoo.platform.service.demo.service.inf.DemoService;
import cn.seocoo.platform.unite.Demo;

public class DemoServiceImpl implements DemoService {
	private DemoDao demoDao;

	@Override
	public List<Demo> findAll() {
		// TODO Auto-generated method stub
		return demoDao.findAll();
	}

	public DemoDao getDemoDao() {
		return demoDao;
	}

	public void setDemoDao(DemoDao demoDao) {
		this.demoDao = demoDao;
	}

}

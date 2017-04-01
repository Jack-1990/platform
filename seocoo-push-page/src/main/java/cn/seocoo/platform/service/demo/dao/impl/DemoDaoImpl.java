package cn.seocoo.platform.service.demo.dao.impl;

import java.util.List;

import com.tydic.framework.base.dao.EntityManagerImpl;

import cn.seocoo.platform.service.demo.dao.inf.DemoDao;
import cn.seocoo.platform.unite.Demo;

public class DemoDaoImpl extends EntityManagerImpl<Demo, Integer> implements DemoDao{

	@Override
	public List<Demo> findAll() {
		// TODO Auto-generated method stub
		 return entityDao.findAll("demo.query");
	}

}

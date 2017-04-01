package cn.seocoo.platform.dao.demo.impl;

import java.util.List;


import cn.seocoo.platform.dao.demo.inf.DemoDao;
import cn.seocoo.platform.unite.Demo;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class DemoDaoImpl  extends EntityManagerImpl<Demo,Integer> implements DemoDao{

	@Override
	public List<Demo> queryAll() {
		// TODO Auto-generated method stub
		return entityDao.find("demo.query", null);
	}

}

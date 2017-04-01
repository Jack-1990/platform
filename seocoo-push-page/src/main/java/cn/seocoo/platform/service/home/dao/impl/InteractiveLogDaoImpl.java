package cn.seocoo.platform.service.home.dao.impl;

import java.util.List;

import com.tydic.framework.base.dao.EntityManagerImpl;

import cn.seocoo.platform.service.home.dao.inf.InteractiveLogDao;
import cn.seocoo.platform.unite.InteractiveLog;

public class InteractiveLogDaoImpl extends EntityManagerImpl<InteractiveLog,Integer> implements InteractiveLogDao{

	@Override
	public List<InteractiveLog> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InteractiveLog find(InteractiveLog iLog) {
		return entityDao.findObj("interactiveLog.query", iLog);
	}

	@Override
	public void add(InteractiveLog iLog) {
		entityDao.save("interactiveLog.insert", iLog);
		
	}

	@Override
	public void update(InteractiveLog iLog) {
		entityDao.update("interactiveLog.update", iLog);
		
	}

	@Override
	public void delete(InteractiveLog iLog) {
		// TODO Auto-generated method stub
		
	}

}

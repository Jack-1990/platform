package cn.seocoo.platform.dao.rateDimAttr.impl;

import java.util.List;
import java.util.Map;

import com.tydic.framework.base.dao.EntityManagerImpl;

import cn.seocoo.platform.dao.rateDimAttr.inf.RateDimAttrDao;
import cn.seocoo.platform.model.RateDimAttr;

public class RateDimAttrDaoImpl extends EntityManagerImpl<RateDimAttr, Integer> implements RateDimAttrDao{

    @Override
    public RateDimAttr queryRateDimAttr(RateDimAttr rateDimAttr){
        return entityDao.findObj("RateDimAttr.queryRateDimAttr", rateDimAttr);
    }

    @Override
    public List<RateDimAttr> queryRateDimAttrList(RateDimAttr rateDimAttr){
        return entityDao.find("RateDimAttr.queryRateDimAttr", rateDimAttr);
    }
    @Override
    public void saveRateDimAttr(RateDimAttr rateDimAttr){
         entityDao.save("RateDimAttr.saveRateDimAttr", rateDimAttr);
    }
    @Override
    public void updateRateDimAttr(RateDimAttr rateDimAttr){
         entityDao.update("RateDimAttr.updateRateDimAttr", rateDimAttr);
    }
    @Override
    public void deleteRateDimAttr(RateDimAttr rateDimAttr){
         entityDao.remove("RateDimAttr.deleteRateDimAttr", rateDimAttr);
    }
    @Override
    public List<RateDimAttr> queryRateDimAttrPage(Map map){
        return entityDao.find("RateDimAttr.queryRateDimAttrPage", map);
    }
    @Override
    public Integer queryRateDimAttrPageCount(Map map){
        return (Integer) entityDao.find("RateDimAttr.queryRateDimAttrPageCount", map).get(0);
    }

	@Override
	public void batchInsertRateDimAttr(List<RateDimAttr> rateDimAttr)
	{
		// TODO Auto-generated method stub
		entityDao.saveBatch("RateDimAttr.batchInsertRateDimAttr", rateDimAttr.toArray());
	}

	@Override
	public List<RateDimAttr> queryRateDimAttrWithLastLevel(RateDimAttr rateDimAttr)
	{
		// TODO Auto-generated method stub
		return entityDao.find("RateDimAttr.queryRateDimAttrWithLastLevel", rateDimAttr);
	}
}

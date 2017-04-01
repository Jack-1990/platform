package cn.seocoo.platform.dao.rateDim.impl;

import java.util.List;
import java.util.Map;

import com.tydic.framework.base.dao.EntityManagerImpl;

import cn.seocoo.platform.dao.rateDim.inf.RateDimDao;
import cn.seocoo.platform.model.RateDim;

public class RateDimDaoImpl extends EntityManagerImpl<RateDim, Integer> implements RateDimDao{

    @Override
    public RateDim queryRateDim(RateDim rateDim){
        return entityDao.findObj("RateDim.queryRateDim", rateDim);
    }

    @Override
    public List<RateDim> queryRateDimList(RateDim rateDim){
        return entityDao.find("RateDim.queryRateDim", rateDim);
    }
    @Override
    public void saveRateDim(RateDim rateDim){
         entityDao.save("RateDim.saveRateDim", rateDim);
    }
    @Override
    public void updateRateDim(RateDim rateDim){
         entityDao.update("RateDim.updateRateDim", rateDim);
    }
    @Override
    public void deleteRateDim(RateDim rateDim){
         entityDao.remove("RateDim.deleteRateDim", rateDim);
    }
    @Override
    public List<RateDim> queryRateDimPage(Map map){
        return entityDao.find("RateDim.queryRateDimPage", map);
    }
    @Override
    public Integer queryRateDimPageCount(Map map){
        return (Integer) entityDao.find("RateDim.queryRateDimPageCount", map).get(0);
    }

	@Override
	public void batchInsertRateDim(List<RateDim> rateDim)
	{
		// TODO Auto-generated method stub
		entityDao.saveBatch("RateDim.batchInsertRateDim", rateDim.toArray());
	}
}

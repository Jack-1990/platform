package cn.seocoo.platform.dao.rateSku.impl;

import java.util.List;
import java.util.Map;

import com.tydic.framework.base.dao.EntityManagerImpl;

import cn.seocoo.platform.dao.rateSku.inf.RateSkuDao;
import cn.seocoo.platform.model.RateSku;

public class RateSkuDaoImpl extends EntityManagerImpl<RateSku, Integer> implements RateSkuDao{

    @Override
    public RateSku queryRateSku(RateSku rateSku){
        return entityDao.findObj("RateSku.queryRateSku", rateSku);
    }

    @Override
    public List<RateSku> queryRateSkuList(RateSku rateSku){
        return entityDao.find("RateSku.queryRateSku", rateSku);
    }
    @Override
    public void saveRateSku(RateSku rateSku){
         entityDao.save("RateSku.saveRateSku", rateSku);
    }
    @Override
    public void updateRateSku(RateSku rateSku){
         entityDao.update("RateSku.updateRateSku", rateSku);
    }
    @Override
    public void deleteRateSku(RateSku rateSku){
         entityDao.remove("RateSku.deleteRateSku", rateSku);
    }
    @Override
    public List<RateSku> queryRateSkuPage(Map map){
        return entityDao.find("RateSku.queryRateSkuPage", map);
    }
    @Override
    public Integer queryRateSkuPageCount(Map map){
        return (Integer) entityDao.find("RateSku.queryRateSkuPageCount", map).get(0);
    }

	@Override
	public void batchInsertRateSku(List<RateSku> rateSku)
	{
		entityDao.saveBatch("RateSku.batchInsertRateSku", rateSku.toArray());
	}
}

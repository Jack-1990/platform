package cn.seocoo.platform.dao.merchantRate.impl;


import java.util.List;
import java.util.Map;

import com.tydic.framework.base.dao.EntityManagerImpl;

import cn.seocoo.platform.dao.merchantRate.inf.MerchantRateDao;
import cn.seocoo.platform.model.MerchantRate;

public class MerchantRateDaoImpl extends EntityManagerImpl<MerchantRate, Integer> implements MerchantRateDao{

    @Override
    public MerchantRate queryMerchantRate(MerchantRate merchantRate){
        return entityDao.findObj("MerchantRate.queryMerchantRate", merchantRate);
    }

    @Override
    public List<MerchantRate> queryMerchantRateList(MerchantRate merchantRate){
        return entityDao.find("MerchantRate.queryMerchantRate", merchantRate);
    }
    @Override
    public void saveMerchantRate(MerchantRate merchantRate){
         entityDao.save("MerchantRate.saveMerchantRate", merchantRate);
    }
    @Override
    public void updateMerchantRate(MerchantRate merchantRate){
         entityDao.update("MerchantRate.updateMerchantRate", merchantRate);
    }
    @Override
    public void deleteMerchantRate(MerchantRate merchantRate){
         entityDao.remove("MerchantRate.deleteMerchantRate", merchantRate);
    }
    @Override
    public List<MerchantRate> queryMerchantRatePage(Map map){
        return entityDao.find("MerchantRate.queryMerchantRatePage", map);
    }
    @Override
    public Integer queryMerchantRatePageCount(Map map){
        return (Integer) entityDao.find("MerchantRate.queryMerchantRatePageCount", map).get(0);
    }

	@Override
	public void batchInsertMerchantRate(List<MerchantRate> merchantRate)
	{
		// TODO Auto-generated method stub
		entityDao.saveBatch("MerchantRate.batchInsertMerchantRate", merchantRate.toArray());
	}
}

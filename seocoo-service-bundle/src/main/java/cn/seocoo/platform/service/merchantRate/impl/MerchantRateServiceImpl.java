package cn.seocoo.platform.service.merchantRate.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.merchantRate.inf.MerchantRateDao;
import cn.seocoo.platform.model.MerchantRate;
import cn.seocoo.platform.service.merchantRate.inf.MerchantRateService;

public class MerchantRateServiceImpl  implements MerchantRateService{

	  private MerchantRateDao merchantRateDao;

    public MerchantRateDao getMerchantRate() {
        return merchantRateDao;
    }
    public void setMerchantRateDao(MerchantRateDao merchantRateDao) {
         this.merchantRateDao = merchantRateDao;
    }

    @Override
    public MerchantRate queryMerchantRate(MerchantRate merchantRate){
        return merchantRateDao.queryMerchantRate(merchantRate);
    }

    @Override
    public List<MerchantRate> queryMerchantRateList(MerchantRate merchantRate){
        return merchantRateDao.queryMerchantRateList(merchantRate);
    }
    @Override
    public void saveMerchantRate(MerchantRate merchantRate){
          merchantRateDao.saveMerchantRate(merchantRate);
    }
    @Override
    public void updateMerchantRate(MerchantRate merchantRate){
        merchantRateDao.updateMerchantRate(merchantRate);
    }
    @Override
    public void deleteMerchantRate(MerchantRate merchantRate){
        merchantRateDao.deleteMerchantRate(merchantRate);
    }
    @Override
    public List<MerchantRate> queryMerchantRatePage(Map map){
        return merchantRateDao.queryMerchantRatePage(map);
    }
    @Override
    public Integer queryMerchantRatePageCount(Map map){
        return merchantRateDao.queryMerchantRatePageCount(map);
    }
	@Override
	public void batchInsertMerchantRate(List<MerchantRate> merchantRate)
	{
		// TODO Auto-generated method stub
		merchantRateDao.batchInsertMerchantRate(merchantRate);
	}
}

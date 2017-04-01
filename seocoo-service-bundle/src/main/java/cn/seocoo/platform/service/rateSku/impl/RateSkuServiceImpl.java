package cn.seocoo.platform.service.rateSku.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.RateSku;
import cn.seocoo.platform.service.rateSku.inf.RateSkuService;
import cn.seocoo.platform.dao.rateSku.inf.RateSkuDao;

public class RateSkuServiceImpl  implements RateSkuService{

	  private RateSkuDao rateSkuDao;

    public RateSkuDao getRateSku() {
        return rateSkuDao;
    }
    public void setRateSkuDao(RateSkuDao rateSkuDao) {
         this.rateSkuDao = rateSkuDao;
    }

    @Override
    public RateSku queryRateSku(RateSku rateSku){
        return rateSkuDao.queryRateSku(rateSku);
    }

    @Override
    public List<RateSku> queryRateSkuList(RateSku rateSku){
        return rateSkuDao.queryRateSkuList(rateSku);
    }
    @Override
    public void saveRateSku(RateSku rateSku){
          rateSkuDao.saveRateSku(rateSku);
    }
    @Override
    public void updateRateSku(RateSku rateSku){
        rateSkuDao.updateRateSku(rateSku);
    }
    @Override
    public void deleteRateSku(RateSku rateSku){
        rateSkuDao.deleteRateSku(rateSku);
    }
    @Override
    public List<RateSku> queryRateSkuPage(Map map){
        return rateSkuDao.queryRateSkuPage(map);
    }
    @Override
    public Integer queryRateSkuPageCount(Map map){
        return rateSkuDao.queryRateSkuPageCount(map);
    }
}

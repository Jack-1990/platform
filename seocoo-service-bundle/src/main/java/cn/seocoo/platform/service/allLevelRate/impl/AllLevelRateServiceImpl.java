package cn.seocoo.platform.service.allLevelRate.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.allLevelRate.inf.AllLevelRateDao;
import cn.seocoo.platform.model.AllLevelRate;
import cn.seocoo.platform.service.allLevelRate.inf.AllLevelRateService;

public class AllLevelRateServiceImpl  implements AllLevelRateService{

	  private AllLevelRateDao allLevelRateDao;

    public AllLevelRateDao getAllLevelRate() {
        return allLevelRateDao;
    }
    public void setAllLevelRateDao(AllLevelRateDao allLevelRateDao) {
         this.allLevelRateDao = allLevelRateDao;
    }

    @Override
    public AllLevelRate queryAllLevelRate(AllLevelRate allLevelRate){
        return allLevelRateDao.queryAllLevelRate(allLevelRate);
    }

    @Override
    public List<AllLevelRate> queryAllLevelRateList(AllLevelRate allLevelRate){
        return allLevelRateDao.queryAllLevelRateList(allLevelRate);
    }
    @Override
    public void saveAllLevelRate(AllLevelRate allLevelRate){
          allLevelRateDao.saveAllLevelRate(allLevelRate);
    }
    @Override
    public void updateAllLevelRate(AllLevelRate allLevelRate){
        allLevelRateDao.updateAllLevelRate(allLevelRate);
    }
    @Override
    public void deleteAllLevelRate(AllLevelRate allLevelRate){
        allLevelRateDao.deleteAllLevelRate(allLevelRate);
    }
    @Override
    public List<AllLevelRate> queryAllLevelRatePage(Map map){
        return allLevelRateDao.queryAllLevelRatePage(map);
    }
    @Override
    public Integer queryAllLevelRatePageCount(Map map){
        return allLevelRateDao.queryAllLevelRatePageCount(map);
    }
    
    public List<AllLevelRate> queryLevelList(AllLevelRate allLevelRate){
        return allLevelRateDao.queryLevelList(allLevelRate);
    }
}

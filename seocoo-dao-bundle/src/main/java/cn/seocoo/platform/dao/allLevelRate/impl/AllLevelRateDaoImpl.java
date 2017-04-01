package cn.seocoo.platform.dao.allLevelRate.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.allLevelRate.inf.AllLevelRateDao;
import cn.seocoo.platform.model.AllLevelRate;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class AllLevelRateDaoImpl extends EntityManagerImpl<AllLevelRate, Integer> implements AllLevelRateDao{

    @Override
    public AllLevelRate queryAllLevelRate(AllLevelRate allLevelRate){
        return entityDao.findObj("AllLevelRate.queryAllLevelRate", allLevelRate);
    }

    @Override
    public List<AllLevelRate> queryAllLevelRateList(AllLevelRate allLevelRate){
        return entityDao.find("AllLevelRate.queryAllLevelRate", allLevelRate);
    }
    @Override
    public void saveAllLevelRate(AllLevelRate allLevelRate){
         entityDao.save("AllLevelRate.saveAllLevelRate", allLevelRate);
    }
    @Override
    public void updateAllLevelRate(AllLevelRate allLevelRate){
         entityDao.update("AllLevelRate.updateAllLevelRate", allLevelRate);
    }
    @Override
    public void deleteAllLevelRate(AllLevelRate allLevelRate){
         entityDao.remove("AllLevelRate.deleteAllLevelRate", allLevelRate);
    }
    @Override
    public List<AllLevelRate> queryAllLevelRatePage(Map map){
        return entityDao.find("AllLevelRate.queryAllLevelRatePage", map);
    }
    @Override
    public Integer queryAllLevelRatePageCount(Map map){
        return (Integer) entityDao.find("AllLevelRate.queryAllLevelRatePageCount", map).get(0);
    }
    
    public List<AllLevelRate> queryLevelList(AllLevelRate allLevelRate){
        return entityDao.find("AllLevelRate.queryLevelList", allLevelRate);
    }
}

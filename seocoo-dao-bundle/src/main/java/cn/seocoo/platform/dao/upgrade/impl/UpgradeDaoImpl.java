package cn.seocoo.platform.dao.upgrade.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Upgrade;
import cn.seocoo.platform.dao.upgrade.inf.UpgradeDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class UpgradeDaoImpl extends EntityManagerImpl<Upgrade, Integer> implements UpgradeDao{

    @Override
    public Upgrade queryUpgrade(Upgrade upgrade){
        return entityDao.findObj("Upgrade.queryUpgrade", upgrade);
    }

    @Override
    public List<Upgrade> queryUpgradeList(Upgrade upgrade){
        return entityDao.find("Upgrade.queryUpgrade", upgrade);
    }
    @Override
    public void saveUpgrade(Upgrade upgrade){
         entityDao.save("Upgrade.saveUpgrade", upgrade);
    }
    @Override
    public void updateUpgrade(Upgrade upgrade){
         entityDao.update("Upgrade.updateUpgrade", upgrade);
    }
    @Override
    public void deleteUpgrade(Upgrade upgrade){
         entityDao.remove("Upgrade.deleteUpgrade", upgrade);
    }
    @Override
    public List<Upgrade> queryUpgradePage(Map map){
        return entityDao.find("Upgrade.queryUpgradePage", map);
    }
    @Override
    public Integer queryUpgradePageCount(Map map){
        return (Integer) entityDao.find("Upgrade.queryUpgradePageCount", map).get(0);
    }
}

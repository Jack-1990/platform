package cn.seocoo.platform.service.upgrade.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Upgrade;
import cn.seocoo.platform.service.upgrade.inf.UpgradeService;
import cn.seocoo.platform.dao.upgrade.inf.UpgradeDao;

public class UpgradeServiceImpl  implements UpgradeService{

	  private UpgradeDao upgradeDao;

    public UpgradeDao getUpgrade() {
        return upgradeDao;
    }
    public void setUpgradeDao(UpgradeDao upgradeDao) {
         this.upgradeDao = upgradeDao;
    }

    @Override
    public Upgrade queryUpgrade(Upgrade upgrade){
        return upgradeDao.queryUpgrade(upgrade);
    }

    @Override
    public List<Upgrade> queryUpgradeList(Upgrade upgrade){
        return upgradeDao.queryUpgradeList(upgrade);
    }
    @Override
    public void saveUpgrade(Upgrade upgrade){
          upgradeDao.saveUpgrade(upgrade);
    }
    @Override
    public void updateUpgrade(Upgrade upgrade){
        upgradeDao.updateUpgrade(upgrade);
    }
    @Override
    public void deleteUpgrade(Upgrade upgrade){
        upgradeDao.deleteUpgrade(upgrade);
    }
    @Override
    public List<Upgrade> queryUpgradePage(Map map){
        return upgradeDao.queryUpgradePage(map);
    }
    @Override
    public Integer queryUpgradePageCount(Map map){
        return upgradeDao.queryUpgradePageCount(map);
    }
}

package cn.seocoo.platform.service.userUpgrade.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.UserUpgrade;
import cn.seocoo.platform.service.userUpgrade.inf.UserUpgradeService;
import cn.seocoo.platform.dao.userUpgrade.inf.UserUpgradeDao;

public class UserUpgradeServiceImpl  implements UserUpgradeService{

	  private UserUpgradeDao userUpgradeDao;

    public UserUpgradeDao getUserUpgrade() {
        return userUpgradeDao;
    }
    public void setUserUpgradeDao(UserUpgradeDao userUpgradeDao) {
         this.userUpgradeDao = userUpgradeDao;
    }

    @Override
    public UserUpgrade queryUserUpgrade(UserUpgrade userUpgrade){
        return userUpgradeDao.queryUserUpgrade(userUpgrade);
    }

    @Override
    public List<UserUpgrade> queryUserUpgradeList(UserUpgrade userUpgrade){
        return userUpgradeDao.queryUserUpgradeList(userUpgrade);
    }
    @Override
    public void saveUserUpgrade(UserUpgrade userUpgrade){
          userUpgradeDao.saveUserUpgrade(userUpgrade);
    }
    @Override
    public void updateUserUpgrade(UserUpgrade userUpgrade){
        userUpgradeDao.updateUserUpgrade(userUpgrade);
    }
    @Override
    public void deleteUserUpgrade(UserUpgrade userUpgrade){
        userUpgradeDao.deleteUserUpgrade(userUpgrade);
    }
    @Override
    public List<UserUpgrade> queryUserUpgradePage(Map map){
        return userUpgradeDao.queryUserUpgradePage(map);
    }
    @Override
    public Integer queryUserUpgradePageCount(Map map){
        return userUpgradeDao.queryUserUpgradePageCount(map);
    }
    @Override
    public List<UserUpgrade> queryUserUpgradeInfos(UserUpgrade userUpgrade){
    	return userUpgradeDao.queryUserUpgradeInfos(userUpgrade);
    }
    public List<UserUpgrade> queryIsnotUpgrade(UserUpgrade userUpgrade){
        return userUpgradeDao.queryIsnotUpgrade(userUpgrade);
    }
    
    public List<UserUpgrade> queryListMust(UserUpgrade userUpgrade){
        return userUpgradeDao.queryListMust(userUpgrade);
    }
}

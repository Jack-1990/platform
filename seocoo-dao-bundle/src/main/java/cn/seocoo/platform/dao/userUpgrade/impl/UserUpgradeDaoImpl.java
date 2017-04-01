package cn.seocoo.platform.dao.userUpgrade.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.UserUpgrade;
import cn.seocoo.platform.dao.userUpgrade.inf.UserUpgradeDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class UserUpgradeDaoImpl extends EntityManagerImpl<UserUpgrade, Integer> implements UserUpgradeDao{

    @Override
    public UserUpgrade queryUserUpgrade(UserUpgrade userUpgrade){
        return entityDao.findObj("UserUpgrade.queryUserUpgrade", userUpgrade);
    }

    @Override
    public List<UserUpgrade> queryUserUpgradeList(UserUpgrade userUpgrade){
        return entityDao.find("UserUpgrade.queryUserUpgrade", userUpgrade);
    }
    @Override
    public void saveUserUpgrade(UserUpgrade userUpgrade){
         entityDao.save("UserUpgrade.saveUserUpgrade", userUpgrade);
    }
    @Override
    public void updateUserUpgrade(UserUpgrade userUpgrade){
         entityDao.update("UserUpgrade.updateUserUpgrade", userUpgrade);
    }
    @Override
    public void deleteUserUpgrade(UserUpgrade userUpgrade){
         entityDao.remove("UserUpgrade.deleteUserUpgrade", userUpgrade);
    }
    @Override
    public List<UserUpgrade> queryUserUpgradePage(Map map){
        return entityDao.find("UserUpgrade.queryUserUpgradePage", map);
    }
    @Override
    public Integer queryUserUpgradePageCount(Map map){
        return (Integer) entityDao.find("UserUpgrade.queryUserUpgradePageCount", map).get(0);
    }
    @Override
    public List<UserUpgrade> queryUserUpgradeInfos(UserUpgrade userUpgrade){
    	return entityDao.find("UserUpgrade.queryUserUpgradeInfos", userUpgrade);
    }
    @Override
    public List<UserUpgrade> queryIsnotUpgrade(UserUpgrade userUpgrade){
        return entityDao.find("UserUpgrade.queryIsnotUpgrade", userUpgrade);
    }
    
    public List<UserUpgrade> queryListMust(UserUpgrade userUpgrade){
        return entityDao.find("UserUpgrade.queryListMust", userUpgrade);
    }
}

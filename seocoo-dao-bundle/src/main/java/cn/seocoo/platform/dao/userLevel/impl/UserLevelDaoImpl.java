package cn.seocoo.platform.dao.userLevel.impl;


import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.userLevel.inf.UserLevelDao;
import cn.seocoo.platform.model.UserLevel;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class UserLevelDaoImpl extends EntityManagerImpl<UserLevel, Integer> implements UserLevelDao{

    @Override
    public UserLevel queryUserLevel(UserLevel userLevel){
        return entityDao.findObj("UserLevel.queryUserLevel", userLevel);
    }

    @Override
    public List<UserLevel> queryUserLevelList(UserLevel userLevel){
        return entityDao.find("UserLevel.queryUserLevel", userLevel);
    }
    @Override
    public void saveUserLevel(UserLevel userLevel){
         entityDao.save("UserLevel.saveUserLevel", userLevel);
    }
    @Override
    public void updateUserLevel(UserLevel userLevel){
         entityDao.update("UserLevel.updateUserLevel", userLevel);
    }
    @Override
    public void deleteUserLevel(UserLevel userLevel){
         entityDao.remove("UserLevel.deleteUserLevel", userLevel);
    }
    @Override
    public List<UserLevel> queryUserLevelPage(Map map){
        return entityDao.find("UserLevel.queryUserLevelPage", map);
    }
    @Override
    public Integer queryUserLevelPageCount(Map map){
        return (Integer) entityDao.find("UserLevel.queryUserLevelPageCount", map).get(0);
    }
}

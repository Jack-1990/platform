package cn.seocoo.platform.service.userLevel.impl;


import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.userLevel.inf.UserLevelDao;
import cn.seocoo.platform.model.UserLevel;
import cn.seocoo.platform.service.userLevel.inf.UserLevelService;

public class UserLevelServiceImpl  implements UserLevelService{

	  private UserLevelDao userLevelDao;

    public UserLevelDao getUserLevel() {
        return userLevelDao;
    }
    public void setUserLevelDao(UserLevelDao userLevelDao) {
         this.userLevelDao = userLevelDao;
    }

    @Override
    public UserLevel queryUserLevel(UserLevel userLevel){
        return userLevelDao.queryUserLevel(userLevel);
    }

    @Override
    public List<UserLevel> queryUserLevelList(UserLevel userLevel){
        return userLevelDao.queryUserLevelList(userLevel);
    }
    @Override
    public void saveUserLevel(UserLevel userLevel){
          userLevelDao.saveUserLevel(userLevel);
    }
    @Override
    public void updateUserLevel(UserLevel userLevel){
        userLevelDao.updateUserLevel(userLevel);
    }
    @Override
    public void deleteUserLevel(UserLevel userLevel){
        userLevelDao.deleteUserLevel(userLevel);
    }
    @Override
    public List<UserLevel> queryUserLevelPage(Map map){
        return userLevelDao.queryUserLevelPage(map);
    }
    @Override
    public Integer queryUserLevelPageCount(Map map){
        return userLevelDao.queryUserLevelPageCount(map);
    }
}

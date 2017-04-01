package cn.seocoo.platform.service.userIdinfo.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.UserIdinfo;
import cn.seocoo.platform.service.userIdinfo.inf.UserIdinfoService;
import cn.seocoo.platform.dao.userIdinfo.inf.UserIdinfoDao;

public class UserIdinfoServiceImpl  implements UserIdinfoService{

	  private UserIdinfoDao userIdinfoDao;

    public UserIdinfoDao getUserIdinfo() {
        return userIdinfoDao;
    }
    public void setUserIdinfoDao(UserIdinfoDao userIdinfoDao) {
         this.userIdinfoDao = userIdinfoDao;
    }

    @Override
    public UserIdinfo queryUserIdinfo(UserIdinfo userIdinfo){
        return userIdinfoDao.queryUserIdinfo(userIdinfo);
    }

    @Override
    public List<UserIdinfo> queryUserIdinfoList(UserIdinfo userIdinfo){
        return userIdinfoDao.queryUserIdinfoList(userIdinfo);
    }
    @Override
    public void saveUserIdinfo(UserIdinfo userIdinfo){
          userIdinfoDao.saveUserIdinfo(userIdinfo);
    }
    @Override
    public void updateUserIdinfo(UserIdinfo userIdinfo){
        userIdinfoDao.updateUserIdinfo(userIdinfo);
    }
    @Override
    public void deleteUserIdinfo(UserIdinfo userIdinfo){
        userIdinfoDao.deleteUserIdinfo(userIdinfo);
    }
    @Override
    public List<UserIdinfo> queryUserIdinfoPage(Map map){
        return userIdinfoDao.queryUserIdinfoPage(map);
    }
    @Override
    public Integer queryUserIdinfoPageCount(Map map){
        return userIdinfoDao.queryUserIdinfoPageCount(map);
    }
}

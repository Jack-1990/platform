package cn.seocoo.platform.dao.userIdinfo.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.UserIdinfo;
import cn.seocoo.platform.dao.userIdinfo.inf.UserIdinfoDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class UserIdinfoDaoImpl extends EntityManagerImpl<UserIdinfo, Integer> implements UserIdinfoDao{

    @Override
    public UserIdinfo queryUserIdinfo(UserIdinfo userIdinfo){
        return entityDao.findObj("UserIdinfo.queryUserIdinfo", userIdinfo);
    }

    @Override
    public List<UserIdinfo> queryUserIdinfoList(UserIdinfo userIdinfo){
        return entityDao.find("UserIdinfo.queryUserIdinfo", userIdinfo);
    }
    @Override
    public void saveUserIdinfo(UserIdinfo userIdinfo){
         entityDao.save("UserIdinfo.saveUserIdinfo", userIdinfo);
    }
    @Override
    public void updateUserIdinfo(UserIdinfo userIdinfo){
         entityDao.update("UserIdinfo.updateUserIdinfo", userIdinfo);
    }
    @Override
    public void deleteUserIdinfo(UserIdinfo userIdinfo){
         entityDao.remove("UserIdinfo.deleteUserIdinfo", userIdinfo);
    }
    @Override
    public List<UserIdinfo> queryUserIdinfoPage(Map map){
        return entityDao.find("UserIdinfo.queryUserIdinfoPage", map);
    }
    @Override
    public Integer queryUserIdinfoPageCount(Map map){
        return (Integer) entityDao.find("UserIdinfo.queryUserIdinfoPageCount", map).get(0);
    }
}

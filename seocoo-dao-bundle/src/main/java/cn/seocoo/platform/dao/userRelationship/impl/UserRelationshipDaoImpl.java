package cn.seocoo.platform.dao.userRelationship.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.UserRelationship;
import cn.seocoo.platform.dao.userRelationship.inf.UserRelationshipDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class UserRelationshipDaoImpl extends EntityManagerImpl<UserRelationship, Integer> implements UserRelationshipDao{

    @Override
    public UserRelationship queryUserRelationship(UserRelationship userRelationship){
        return entityDao.findObj("UserRelationship.queryUserRelationship", userRelationship);
    }

    @Override
    public List<UserRelationship> queryUserRelationshipList(UserRelationship userRelationship){
        return entityDao.find("UserRelationship.queryUserRelationship", userRelationship);
    }
    @Override
    public void saveUserRelationship(UserRelationship userRelationship){
         entityDao.save("UserRelationship.saveUserRelationship", userRelationship);
    }
    @Override
    public void updateUserRelationship(UserRelationship userRelationship){
         entityDao.update("UserRelationship.updateUserRelationship", userRelationship);
    }
    @Override
    public void deleteUserRelationship(UserRelationship userRelationship){
         entityDao.remove("UserRelationship.deleteUserRelationship", userRelationship);
    }
    @Override
    public List<UserRelationship> queryUserRelationshipPage(Map map){
        return entityDao.find("UserRelationship.queryUserRelationshipPage", map);
    }
    @Override
    public Integer queryUserRelationshipPageCount(Map map){
        return (Integer) entityDao.find("UserRelationship.queryUserRelationshipPageCount", map).get(0);
    }
    public List<UserRelationship> queryUser(UserRelationship userRelationship){
        return entityDao.find("UserRelationship.queryUser", userRelationship);
    }
    
    public List<UserRelationship> queryMerCodeEmpty(UserRelationship userRelationship){
        return entityDao.find("UserRelationship.queryMerCodeEmpty", userRelationship);
    }
}

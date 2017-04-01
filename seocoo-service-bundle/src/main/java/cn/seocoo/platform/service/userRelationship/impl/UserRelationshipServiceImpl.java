package cn.seocoo.platform.service.userRelationship.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.UserRelationship;
import cn.seocoo.platform.service.userRelationship.inf.UserRelationshipService;
import cn.seocoo.platform.dao.userRelationship.inf.UserRelationshipDao;

public class UserRelationshipServiceImpl  implements UserRelationshipService{

	  private UserRelationshipDao userRelationshipDao;

    public UserRelationshipDao getUserRelationship() {
        return userRelationshipDao;
    }
    public void setUserRelationshipDao(UserRelationshipDao userRelationshipDao) {
         this.userRelationshipDao = userRelationshipDao;
    }

    @Override
    public UserRelationship queryUserRelationship(UserRelationship userRelationship){
        return userRelationshipDao.queryUserRelationship(userRelationship);
    }

    @Override
    public List<UserRelationship> queryUserRelationshipList(UserRelationship userRelationship){
        return userRelationshipDao.queryUserRelationshipList(userRelationship);
    }
    @Override
    public void saveUserRelationship(UserRelationship userRelationship){
          userRelationshipDao.saveUserRelationship(userRelationship);
    }
    @Override
    public void updateUserRelationship(UserRelationship userRelationship){
        userRelationshipDao.updateUserRelationship(userRelationship);
    }
    @Override
    public void deleteUserRelationship(UserRelationship userRelationship){
        userRelationshipDao.deleteUserRelationship(userRelationship);
    }
    @Override
    public List<UserRelationship> queryUserRelationshipPage(Map map){
        return userRelationshipDao.queryUserRelationshipPage(map);
    }
    @Override
    public Integer queryUserRelationshipPageCount(Map map){
        return userRelationshipDao.queryUserRelationshipPageCount(map);
    }
    public List<UserRelationship> queryUser(UserRelationship userRelationship){
        return userRelationshipDao.queryUser(userRelationship);
    }
    public List<UserRelationship> queryMerCodeEmpty(UserRelationship userRelationship){
        return userRelationshipDao.queryMerCodeEmpty(userRelationship);
    }
}

package cn.seocoo.platform.dao.userImage.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.userImage.inf.UserImageDao;
import cn.seocoo.platform.model.UserImage;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class UserImageDaoImpl extends EntityManagerImpl<UserImage, Integer> implements UserImageDao{

    @Override
    public UserImage queryUserImage(UserImage userImage){
        return entityDao.findObj("UserImage.queryUserImage", userImage);
    }

    @Override
    public List<UserImage> queryUserImageList(UserImage userImage){
        return entityDao.find("UserImage.queryUserImage", userImage);
    }
    @Override
    public void saveUserImage(UserImage userImage){
         entityDao.save("UserImage.saveUserImage", userImage);
    }
    @Override
    public void updateUserImage(UserImage userImage){
         entityDao.update("UserImage.updateUserImage", userImage);
    }
    @Override
    public void deleteUserImage(UserImage userImage){
         entityDao.remove("UserImage.deleteUserImage", userImage);
    }
    @Override
    public List<UserImage> queryUserImagePage(Map map){
        return entityDao.find("UserImage.queryUserImagePage", map);
    }
    @Override
    public Integer queryUserImagePageCount(Map map){
        return (Integer) entityDao.find("UserImage.queryUserImagePageCount", map).get(0);
    }
}

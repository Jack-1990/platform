package cn.seocoo.platform.service.userImage.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.userImage.inf.UserImageDao;
import cn.seocoo.platform.model.UserImage;
import cn.seocoo.platform.service.userImage.inf.UserImageService;

public class UserImageServiceImpl  implements UserImageService{

	  private UserImageDao userImageDao;

    public UserImageDao getUserImage() {
        return userImageDao;
    }
    public void setUserImageDao(UserImageDao userImageDao) {
         this.userImageDao = userImageDao;
    }

    @Override
    public UserImage queryUserImage(UserImage userImage){
        return userImageDao.queryUserImage(userImage);
    }

    @Override
    public List<UserImage> queryUserImageList(UserImage userImage){
        return userImageDao.queryUserImageList(userImage);
    }
    @Override
    public void saveUserImage(UserImage userImage){
          userImageDao.saveUserImage(userImage);
    }
    @Override
    public void updateUserImage(UserImage userImage){
        userImageDao.updateUserImage(userImage);
    }
    @Override
    public void deleteUserImage(UserImage userImage){
        userImageDao.deleteUserImage(userImage);
    }
    @Override
    public List<UserImage> queryUserImagePage(Map map){
        return userImageDao.queryUserImagePage(map);
    }
    @Override
    public Integer queryUserImagePageCount(Map map){
        return userImageDao.queryUserImagePageCount(map);
    }
}

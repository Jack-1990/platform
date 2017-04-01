package cn.seocoo.platform.dao.userImage.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.UserImage;

public interface UserImageDao {

    public UserImage queryUserImage(UserImage userImage);

    public List<UserImage> queryUserImageList(UserImage userImage);

    public void saveUserImage(UserImage userImage);

    public void updateUserImage(UserImage userImage);

    public void deleteUserImage(UserImage userImage);

    public List<UserImage> queryUserImagePage(Map map);

    public Integer queryUserImagePageCount(Map map);
}

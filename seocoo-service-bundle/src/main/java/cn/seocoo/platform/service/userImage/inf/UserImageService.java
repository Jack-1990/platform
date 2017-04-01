package cn.seocoo.platform.service.userImage.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.UserImage;

public interface UserImageService {

    public UserImage queryUserImage(UserImage userImage);

    public List<UserImage> queryUserImageList(UserImage userImage);

    public void saveUserImage(UserImage userImage);

    public void updateUserImage(UserImage userImage);

    public void deleteUserImage(UserImage userImage);

    public List<UserImage> queryUserImagePage(Map map);

    public Integer queryUserImagePageCount(Map map);
}

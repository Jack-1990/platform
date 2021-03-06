package cn.seocoo.platform.service.userIdinfo.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.UserIdinfo;

public interface UserIdinfoService {

    public UserIdinfo queryUserIdinfo(UserIdinfo userIdinfo);

    public List<UserIdinfo> queryUserIdinfoList(UserIdinfo userIdinfo);

    public void saveUserIdinfo(UserIdinfo userIdinfo);

    public void updateUserIdinfo(UserIdinfo userIdinfo);

    public void deleteUserIdinfo(UserIdinfo userIdinfo);

    public List<UserIdinfo> queryUserIdinfoPage(Map map);

    public Integer queryUserIdinfoPageCount(Map map);
}

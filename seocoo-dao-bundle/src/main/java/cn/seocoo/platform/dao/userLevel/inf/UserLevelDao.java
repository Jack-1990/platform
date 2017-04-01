package cn.seocoo.platform.dao.userLevel.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.UserLevel;

public interface UserLevelDao {

    public UserLevel queryUserLevel(UserLevel userLevel);

    public List<UserLevel> queryUserLevelList(UserLevel userLevel);

    public void saveUserLevel(UserLevel userLevel);

    public void updateUserLevel(UserLevel userLevel);

    public void deleteUserLevel(UserLevel userLevel);

    public List<UserLevel> queryUserLevelPage(Map map);

    public Integer queryUserLevelPageCount(Map map);
}

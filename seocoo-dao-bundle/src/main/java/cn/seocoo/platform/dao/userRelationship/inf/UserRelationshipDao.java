package cn.seocoo.platform.dao.userRelationship.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.UserRelationship;

public interface UserRelationshipDao {

    public UserRelationship queryUserRelationship(UserRelationship userRelationship);

    public List<UserRelationship> queryUserRelationshipList(UserRelationship userRelationship);

    public void saveUserRelationship(UserRelationship userRelationship);

    public void updateUserRelationship(UserRelationship userRelationship);

    public void deleteUserRelationship(UserRelationship userRelationship);

    public List<UserRelationship> queryUserRelationshipPage(Map map);

    public Integer queryUserRelationshipPageCount(Map map);

	public List<UserRelationship> queryUser(UserRelationship userRelationship);

	public List<UserRelationship> queryMerCodeEmpty(UserRelationship userRelationship);
}

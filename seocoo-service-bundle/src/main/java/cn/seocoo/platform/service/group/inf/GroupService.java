package cn.seocoo.platform.service.group.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.Group;

public interface GroupService {

    public Group queryGroup(Group group);

    public List<Group> queryGroupList(Group group);

    public void saveGroup(Group group);

    public void updateGroup(Group group);

    public void deleteGroup(Group group);

    public List<Group> queryGroupPage(Map map);

    public Integer queryGroupPageCount(Map map);

	public List<Group> queryCreateId(Group group);

	public List<Group> queryGetChild(Map map);

	public Integer queryGetChildCount(Map map);

	public String queryGetGroupCodes(Map map);
}

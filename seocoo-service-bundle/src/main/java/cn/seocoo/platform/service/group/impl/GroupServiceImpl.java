package cn.seocoo.platform.service.group.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.group.inf.GroupDao;
import cn.seocoo.platform.model.Group;
import cn.seocoo.platform.service.group.inf.GroupService;

public class GroupServiceImpl  implements GroupService{

	  private GroupDao groupDao;

    public GroupDao getGroup() {
        return groupDao;
    }
    public void setGroupDao(GroupDao groupDao) {
         this.groupDao = groupDao;
    }

    @Override
    public Group queryGroup(Group group){
        return groupDao.queryGroup(group);
    }

    @Override
    public List<Group> queryGroupList(Group group){
        return groupDao.queryGroupList(group);
    }
    @Override
    public void saveGroup(Group group){
          groupDao.saveGroup(group);
    }
    @Override
    public void updateGroup(Group group){
        groupDao.updateGroup(group);
    }
    @Override
    public void deleteGroup(Group group){
        groupDao.deleteGroup(group);
    }
    @Override
    public List<Group> queryGroupPage(Map map){
        return groupDao.queryGroupPage(map);
    }
    @Override
    public Integer queryGroupPageCount(Map map){
        return groupDao.queryGroupPageCount(map);
    }
    public List<Group> queryCreateId(Group group){
        return groupDao.queryCreateId(group);
    }
    
    public Integer queryGetChildCount(Map map){
        return groupDao.queryGetChildCount(map);
    }
    public List<Group> queryGetChild(Map map){
        return groupDao.queryGetChild(map);
    }

	@Override
	public String queryGetGroupCodes(Map map)
	{
		// TODO Auto-generated method stub
		return groupDao.queryGetGroupCodes(map);
	}
}

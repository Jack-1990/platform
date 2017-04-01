package cn.seocoo.platform.dao.group.impl;

import java.util.List;
import java.util.Map;

import com.tydic.framework.base.dao.EntityManagerImpl;

import cn.seocoo.platform.dao.group.inf.GroupDao;
import cn.seocoo.platform.model.Group;

public class GroupDaoImpl extends EntityManagerImpl<Group, Integer> implements GroupDao{

    @Override
    public Group queryGroup(Group group){
        return entityDao.findObj("Group.queryGroup", group);
    }

    @Override
    public List<Group> queryGroupList(Group group){
        return entityDao.find("Group.queryGroup", group);
    }
    @Override
    public void saveGroup(Group group){
         entityDao.save("Group.saveGroup", group);
    }
    @Override
    public void updateGroup(Group group){
         entityDao.update("Group.updateGroup", group);
    }
    @Override
    public void deleteGroup(Group group){
         entityDao.remove("Group.deleteGroup", group);
    }
    @Override
    public List<Group> queryGroupPage(Map map){
        return entityDao.find("Group.queryGroupPage", map);
    }
    @Override
    public Integer queryGroupPageCount(Map map){
        return (Integer) entityDao.find("Group.queryGroupPageCount", map).get(0);
    }
    public List<Group> queryCreateId(Group group){
        return entityDao.find("Group.queryCreateId", group);
    }
    
    public Integer queryGetChildCount(Map map){
        return (Integer) entityDao.find("Group.queryGetChildCount", map).get(0);
    }
    public List<Group> queryGetChild(Map map){
        return entityDao.find("Group.queryGetChild", map);
    }

	@Override
	public String queryGetGroupCodes(Map map)
	{
		// TODO Auto-generated method stub
		return (String) entityDao.find("Group.queryGetGroupCodes", map).get(0);
	}
}

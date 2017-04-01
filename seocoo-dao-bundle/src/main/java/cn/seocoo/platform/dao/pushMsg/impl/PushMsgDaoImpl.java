package cn.seocoo.platform.dao.pushMsg.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.pushMsg.inf.PushMsgDao;
import cn.seocoo.platform.model.PushMsg;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class PushMsgDaoImpl extends EntityManagerImpl<PushMsg, Integer> implements PushMsgDao{

    @Override
    public PushMsg queryPushMsg(PushMsg pushMsg){
        return entityDao.findObj("PushMsg.queryPushMsg", pushMsg);
    }

    @Override
    public List<PushMsg> queryPushMsgList(PushMsg pushMsg){
        return entityDao.find("PushMsg.queryPushMsg", pushMsg);
    }
    @Override
    public void savePushMsg(PushMsg pushMsg){
         entityDao.save("PushMsg.savePushMsg", pushMsg);
    }
    
    public Integer InsertPushMsg(PushMsg pushMsg){
    	return (Integer)entityDao.save("PushMsg.insertPushMsg", pushMsg);
    }
    
    @Override
    public void updatePushMsg(PushMsg pushMsg){
         entityDao.update("PushMsg.updatePushMsg", pushMsg);
    }
    @Override
    public void deletePushMsg(PushMsg pushMsg){
         entityDao.remove("PushMsg.deletePushMsg", pushMsg);
    }
    @Override
    public List<PushMsg> queryPushMsgPage(Map map){
        return entityDao.find("PushMsg.queryPushMsgPage", map);
    }
    @Override
    public Integer queryPushMsgPageCount(Map map){
        return (Integer) entityDao.find("PushMsg.queryPushMsgPageCount", map).get(0);
    }
}

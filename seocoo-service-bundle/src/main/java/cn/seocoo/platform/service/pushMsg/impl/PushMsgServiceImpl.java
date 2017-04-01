package cn.seocoo.platform.service.pushMsg.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.pushMsg.inf.PushMsgDao;
import cn.seocoo.platform.model.PushMsg;
import cn.seocoo.platform.service.pushMsg.inf.PushMsgService;

public class PushMsgServiceImpl  implements PushMsgService{

	  private PushMsgDao pushMsgDao;

    public PushMsgDao getPushMsg() {
        return pushMsgDao;
    }
    public void setPushMsgDao(PushMsgDao pushMsgDao) {
         this.pushMsgDao = pushMsgDao;
    }

    @Override
    public PushMsg queryPushMsg(PushMsg pushMsg){
        return pushMsgDao.queryPushMsg(pushMsg);
    }

    @Override
    public List<PushMsg> queryPushMsgList(PushMsg pushMsg){
        return pushMsgDao.queryPushMsgList(pushMsg);
    }
    @Override
    public void savePushMsg(PushMsg pushMsg){
          pushMsgDao.savePushMsg(pushMsg);
    }
    @Override
    public void updatePushMsg(PushMsg pushMsg){
        pushMsgDao.updatePushMsg(pushMsg);
    }
    @Override
    public void deletePushMsg(PushMsg pushMsg){
        pushMsgDao.deletePushMsg(pushMsg);
    }
    @Override
    public List<PushMsg> queryPushMsgPage(Map map){
        return pushMsgDao.queryPushMsgPage(map);
    }
    @Override
    public Integer queryPushMsgPageCount(Map map){
        return pushMsgDao.queryPushMsgPageCount(map);
    }
    
    public Integer InsertPushMsg(PushMsg pushMsg){
    	return pushMsgDao.InsertPushMsg(pushMsg);
    }
}

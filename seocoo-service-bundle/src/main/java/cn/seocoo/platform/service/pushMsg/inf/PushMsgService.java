package cn.seocoo.platform.service.pushMsg.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.PushMsg;

public interface PushMsgService {

    public PushMsg queryPushMsg(PushMsg pushMsg);

    public List<PushMsg> queryPushMsgList(PushMsg pushMsg);

    public void savePushMsg(PushMsg pushMsg);

    public void updatePushMsg(PushMsg pushMsg);

    public void deletePushMsg(PushMsg pushMsg);

    public List<PushMsg> queryPushMsgPage(Map map);

    public Integer queryPushMsgPageCount(Map map);
    
    public Integer InsertPushMsg(PushMsg pushMsg);
}

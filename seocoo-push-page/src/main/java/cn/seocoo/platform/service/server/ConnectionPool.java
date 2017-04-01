package cn.seocoo.platform.service.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.seocoo.platform.push.AppPushMsg;


public class ConnectionPool {

	public static Map<String,MsgTcpInfo> tcpPools=new HashMap<String,MsgTcpInfo>();
	public static Map<String,List<AppPushMsg>> msgQuee=new HashMap<String,List<AppPushMsg>>();
	
	/**
	 * 保存消息
	 * @param pushMsg
	 */
	public static void savePushMsg(AppPushMsg pushMsg){
		//加到队列,等待上线 继续发送
		List<AppPushMsg> pushMsgList=ConnectionPool.msgQuee.get(pushMsg.getTarget());
		if(pushMsgList==null){
			pushMsgList=new ArrayList<AppPushMsg>();
		}
		pushMsgList.add(pushMsg);
		ConnectionPool.msgQuee.put(pushMsg.getTarget(), pushMsgList);
	}
}

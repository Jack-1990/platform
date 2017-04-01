package cn.seocoo.platform.service.server;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

public class ScanConnectionPoolThread extends Thread {
	private static final Logger logger = Logger.getLogger(ScanConnectionPoolThread.class);

	@Override
	public void run() {

		while (true) {
			// 等待
			try {
				Thread.sleep(1 * 60 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 扫描连接池去除假死链接
			logger.debug(
					"=================================================开始扫描连接池假死链接=============================================");
			logger.debug("=================================================连接池共计连接数:" + ConnectionPool.tcpPools.size()
					+ "=============================================");
			int deadCount = 0;
			Iterator it = ConnectionPool.tcpPools.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				Object key = entry.getKey();
				MsgTcpInfo msgTcpInfo = (MsgTcpInfo) entry.getValue();
				long timeInterval = System.currentTimeMillis() - msgTcpInfo.getAliveTime().getTime();
				long waitInterval = 7 * 60 * 1000;
				logger.debug("当前链接:" + msgTcpInfo.getS() + ",最后心跳时间:" + msgTcpInfo.getAliveTime() + ",与当前时间间隔:"
						+ timeInterval);
				if (timeInterval > waitInterval) {
					// 说明当前链接已过期,从连接池中清理
					deadCount++;
					logger.debug("当前链接:" + msgTcpInfo.getS() + "已过期从连接池中清理");
					ConnectionPool.tcpPools.remove(key);
					msgTcpInfo.closedTcpConnection();
				} else {

					logger.debug("当前链接:" + msgTcpInfo.getS() + "未过期,可继续推送消息!");
				}
			}
			logger.debug(
					"=================================================扫描连接池假死链接结束:共清理" + deadCount + "个假死链接,连接池剩余链接:"
							+ ConnectionPool.tcpPools.size() + "=============================================");
		}
	}

}

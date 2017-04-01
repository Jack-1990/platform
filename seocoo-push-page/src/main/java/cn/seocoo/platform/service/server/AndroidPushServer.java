package cn.seocoo.platform.service.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class AndroidPushServer extends Thread {

	private static final Logger logger = Logger.getLogger(AndroidPushServer.class);

	public ServerSocket server = null;
	int port = 8013;

	public void run() {
		server = createServer(port);
		receiveSocket(server);
	}

	/**
	 * 创建消息服务
	 * 
	 * @param port
	 * @return
	 */
	public ServerSocket createServer(int port) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			logger.info("========>push  ggf server start success!");
		} catch (IOException e1) {
			System.out.println("push server start failed!" + e1.getMessage());
		}
		return server;
	}

	/**
	 * 接受消息内容
	 * 
	 * @param server
	 */
	public void receiveSocket(ServerSocket server) {
		Socket s = null;
		while (true) {
			try {
				s = server.accept();
				new Thread(new MsgTcpThread(s)).start();
			} catch (IOException e) {
				logger.debug("receiveSocket error !");
				if (s != null) {
					try {
						logger.debug("error s info:" + s);
						s.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
}

/**
 * 消息处理线程
 * 
 * @author liys
 *
 */
class MsgTcpThread implements Runnable {
	private static final Logger logger = Logger.getLogger(MsgTcpThread.class);
	private Socket s = null;
	private MsgTcpInfo tc = null;

	public MsgTcpThread(Socket s) {
		this.s = s;
	}

	public void run() {
		// 创建消息处理对象
		tc = new MsgTcpInfo(s);
		try {
			// 消息通信
			tc.chat();
		} catch (Exception e) {
			logger.debug("========>chat service error!");
			tc.closedTcpConnection();
		}
	}

}
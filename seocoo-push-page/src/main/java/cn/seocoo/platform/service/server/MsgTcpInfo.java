package cn.seocoo.platform.service.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.constant.PushConstant;
import cn.seocoo.platform.push.AppPushMsg;
import cn.seocoo.platform.unite.Message;

public class MsgTcpInfo {
	private static final Logger logger = Logger
	.getLogger(MsgTcpInfo.class);
	//客户端的socket
	private  Socket s = null;			
	private  BufferedReader br = null;  
	 private PrintStream ps = null;  
    private Date aliveTime=new Date();
	
	public MsgTcpInfo(Socket s) {
		this.s = s;
		createStreamObj();
	}
	/**
	 * 初始化通信流
	 */
	private void createStreamObj(){
		try{
			// 初始化该socket对应的输入流    得到客户端的信息  
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));  
            // 获取该socket对应的输出流  
            ps = new PrintStream(s.getOutputStream());  
		}catch (IOException e){
			e.printStackTrace();
			closedTcpConnection();
		}
	}
	/**
	 * 像指定客户端发送消息
	 * @param object
	 */
	public void send(AppPushMsg pushMsg){
		// 向客户端发出反馈消息  
		logger.debug("向客户端:"+pushMsg.getTarget()+" 发送的消息类型:"+pushMsg.getMsgType()+"  消息内容为:"+pushMsg.getMsg());
		logger.info("向客户端:"+pushMsg.getTarget()+" 发送的消息类型:"+pushMsg.getMsgType()+"  消息内容为:"+pushMsg.getMsg());
		String json=JSONObject.toJSONString(pushMsg);
        ps.println(json);  
       
	}

	/**
	 * tcp 链接保持
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void chat() {
		// TODO Auto-generated method stub
	    String content = null;  
		   try {
			while((content = br.readLine()) != null){  
			       //判断是否结束  
					logger.debug("接受客户端内容:"+content);
					Message msg=JSONObject.parseObject(content, Message.class);
					AppPushMsg pushMsg=JSONObject.parseObject(msg.getSvcCont().get(0).getParams(), AppPushMsg.class);
			
				    //登录
				    if(PushConstant.MESAGE_TYPE_LOGIN.equals(pushMsg.getMsgType())&&PushConstant.MESAGE_TYPE_LOGIN.equals(pushMsg.getMsg())){
				    	//关闭之前链接
				    	MsgTcpInfo connection=ConnectionPool.tcpPools.get(pushMsg.getFrom());
				    	if(connection!=null&&!connection.getS().isClosed()){
				    		logger.debug("即将关闭链接信息:"+connection.s+",链接关闭状态:"+connection.s.isClosed()+",当前链接信息:"+this.s);
				    		logger.debug("----------我是分割线------------------------");
							ConnectionPool.tcpPools.put(pushMsg.getFrom().toLowerCase(),this);
				    		closedConnection(connection);
				    	}else{
				    		logger.debug("----------我是分割线------------------------");
							ConnectionPool.tcpPools.put(pushMsg.getFrom().toLowerCase(),this);
				    	}
				    
						
						logger.debug("TCP连接池链接状态:"+ConnectionPool.tcpPools.get(pushMsg.getFrom()).getS()+",当前链接信息:"+this.s);
				    	//会写ok
				    	pushMsg.setTarget(pushMsg.getFrom());
				    	pushMsg.setMsg("ok");
				    	this.send(pushMsg);
				    	//获取该客户端消息,进行发送
				    	List<AppPushMsg> pushList =ConnectionPool.msgQuee.get(pushMsg.getFrom());
				    	if(pushList!=null){
				    		for (int i = 0; i < pushList.size(); i++) {
					    		this.send(pushList.get(i));
					    		//间隔3s发送
					    		Thread.sleep(3000);
							}
				    	}
				    	ConnectionPool.msgQuee.remove(pushMsg.getFrom());
				    }else if (PushConstant.MESAGE_TYPE_ALIVE.equals(pushMsg.getMsgType())&&PushConstant.MESAGE_TYPE_ALIVE.equals(pushMsg.getMsg())){
				    	//心跳探测
				    	MsgTcpInfo connection=ConnectionPool.tcpPools.get(pushMsg.getFrom());
				    	connection.setAliveTime(new Date());
				    	//返回响应
//				    	pushMsg.setTarget(pushMsg.getFrom());
//				    	pushMsg.setMsg("fine");
//				    	this.send(pushMsg);
				    }
				    	
			  }
		} catch (IOException e) {
			closedTcpConnection();
			e.printStackTrace();
			logger.debug("chat error  close socket connection !");
			return;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	private void closedConnection(MsgTcpInfo connection) {

		
	    try {
	    	logger.debug("ready closed MsgTcpInfo tcp 链接:"+connection.getS());
	    	if(connection.getBr()!=null){
				connection.getBr().close();
		    }
	      	logger.debug("----------我是分割线0------------------------");
			if(connection.getPs()!=null){
				connection.getPs().close();
			}
	    	logger.debug("----------我是分割线1------------------------");
	    	if(connection.getS()!=null){
	    		connection.getS().close();
			}
	    	logger.debug("----------我是分割线2------------------------");
		} catch (Exception e) {
			logger.debug("closed tcp connection error !");
			e.printStackTrace();
		}
	
		
	}
	public void closedTcpConnection(){
		
	    try {
	    	logger.debug("ready closed tcp 链接:"+this.s);
	    	if(this.s!=null){
				s.close();
			}
			if(this.br!=null){
				br.close();
		    }
			if(this.ps!=null){
				ps.close();
			}
		} catch (Exception e) {
			logger.debug("closed tcp connection error !");
			e.printStackTrace();
		}
	}
	public Socket getS() {
		return s;
	}
	public void setS(Socket s) {
		this.s = s;
	}
	public BufferedReader getBr() {
		return br;
	}
	public void setBr(BufferedReader br) {
		this.br = br;
	}
	public PrintStream getPs() {
		return ps;
	}
	public void setPs(PrintStream ps) {
		this.ps = ps;
	}
	public Date getAliveTime() {
		return aliveTime;
	}
	public void setAliveTime(Date aliveTime) {
		this.aliveTime = aliveTime;
	}
	
	
	
}

package cn.seocoo.platform.unite.home;

 

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.SpringHelper;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Message;
import cn.seocoo.platform.unite.TcpCont;

public class HomeAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(HomeAction.class);
	
	public void homeController() throws IOException{
		
		String str=this.readRequestMsg(request);
		//将string 转成对象
		Message msg=JSONObject.parseObject(str, Message.class);
		
		//获取对应的处理对象
		String prefix=msg.getTcpCont().getServiceCode();
		//获取spring对象
		Service service=(Service) SpringHelper.getSpringHelper().getBean(prefix+"Service");
		//调用业务处理类
		Object res=service.sevice(msg.getSvcCont().get(0).getParams());
		//回写结果
		if(res instanceof String){
			//添加头尾信息
			String reslut=callBack((String)res,msg);
			//返回是文本
			this.writeBack(reslut);
		}if(res instanceof File){
			//返回文件
			this.writeBack((File)res);
		}
		
		
	}

	public String callBack(String res,Message msg){
		TcpCont  tcpCont=new TcpCont();
		tcpCont.setSrcSysID(msg.getTcpCont().getSrcSysID());
		tcpCont.setServiceCode(msg.getTcpCont().getServiceCode());
		tcpCont.setSrcSysPassWord(msg.getTcpCont().getSrcSysPassWord());
		tcpCont.setSrcSysSign(msg.getTcpCont().getSrcSysSign());
		tcpCont.setTransactionID(msg.getTcpCont().getTransactionID());
		String header=JSON.toJSONString(tcpCont);
		return BapUtil.getWebSvcContent(res, header);
	}
	
	/**
	 * 解析请求报文
	 * @param param
	 * @return
	 */
	private Map<String, String> parseParam(String param) {
		String[] keyValue=param.split("&");
		
		Map<String,String> map=new HashMap<String, String>();
		for (int i = 0; i < keyValue.length; i++) {
			String value="";
			if(keyValue[i].split("=").length>1){
				value=keyValue[i].split("=")[1];
			}
			map.put(keyValue[i].split("=")[0],value );
		}
		return map;
	}
}

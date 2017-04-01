package cn.seocoo.platform.common.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;


import cn.seocoo.platform.unite.ResMessage;
import cn.seocoo.platform.unite.Result;
import cn.seocoo.platform.unite.TcpCont;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.framework.base.model.BapTcpContent;
import com.tydic.framework.util.ConfigurationFactory;
import com.tydic.framework.util.HttpClientFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BapUtil {
    
    private static Logger logger = Logger.getLogger(BapUtil.class);
    
    /**
     * freemarker模板文件名称
     */
    private static final String FM_BAP = "unite.ftl";
    
    /**
     * freemarker模板文件名称
     */
    private static final String RQ_BAP = "request.ftl";
    /**
     * 生成bap接口所需要的webService报文
     * @param svcContentList 各接口已经转换成json格式的报文集合
     * @param bapTcpContent bap接口控制信息
     * @return 符合规范的报文信息，如果生成出错，返回null
     */
    public static String getWebSvcContent(String content, String header){
        try {
            Configuration cfg = ConfigurationFactory.getInstance();
            Template template = cfg.getTemplate(FM_BAP);
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("content", content);
            root.put("header", header);
            // 将模板和数据模型合并
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(baos));
            template.process(root, bos);
            bos.flush();
            return baos.toString();
        } catch (TemplateException e) {
            logger.error("初始化freemarker模板路径出错", e);
        } catch (IOException e) {
            logger.error("IO异常", e);
        }
        return null;
    }
    
    /**
     * 生成bap接口所需要的webService报文
     * @param svcContentList 各接口已经转换成json格式的报文集合
     * @param bapTcpContent bap接口控制信息
     * @return 符合规范的报文信息，如果生成出错，返回null
     */
    public static String getReqSvcContent(String content, String header){
        try {
            Configuration cfg = ConfigurationFactory.getInstance();
            Template template = cfg.getTemplate(RQ_BAP);
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("content", content);
            root.put("header", header);
            // 将模板和数据模型合并
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(baos));
            template.process(root, bos);
            bos.flush();
            return baos.toString();
        } catch (TemplateException e) {
            logger.error("初始化freemarker模板路径出错", e);
        } catch (IOException e) {
            logger.error("IO异常", e);
        }
        return null;
    }
    
    /**
     * HttpClient发送请求
     * @param url 请求url
     * @param bodyContent 消息体
     * @return 服务器返回内容
     */
    @SuppressWarnings("deprecation")
    public static String httpSendMsg(String url, String bodyContent) throws IOException{
        PostMethod post=null;
        HttpClient client=null;
        try{
            client = HttpClientFactory.getHttpClient();
            client.setConnectionTimeout(20*1000);
            post = new PostMethod(url);
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8"); 
            post.setRequestBody(bodyContent);
            post.addRequestHeader("Content-Type",
                    "application/x-www-form-urlencoded; text/html; charset=utf-8");
            post.addRequestHeader("User-Agent", "Mozilla/4.0");
            long start = System.currentTimeMillis();
            logger.info("请求报文：" + bodyContent);
            client.executeMethod(post);
            String result = new String(post.getResponseBody(),"UTF-8");
            
            if(logger.isInfoEnabled()){
                logger.info("响应的编码是："+post.getResponseCharSet());
                long end = System.currentTimeMillis();
                logger.info("统一接口平台接口响应时间:" + (end-start) / 1000 + "秒");
                logger.info("响应结果:"+result);
            }
            return result;
        }finally{
            if(post!=null){
                post.releaseConnection();
                client=null;
            }
        }
    }
    
	public  static String postWebServiceRequest(String url,String nameSpace,String  method,String requestMsg,Integer timeout) throws ServiceException, MalformedURLException, RemoteException{

		Service service = new Service();
		Call call = null;
		logger.info("探测请求报文："+requestMsg);
		//请求报文
		String in = requestMsg;
		String out = "";
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setTimeout(timeout);
			// 设置命名空间与调用方法
			call.setOperationName(new QName(nameSpace,method));
			out = (String) call.invoke(new Object[] { in });
			logger.info("探测响应报文："+out);
		
		System.out.println("in=\n" + in + "\nout=\n" + out);

		return out;
	
	}
	
	/** 发送json请求
	 *  @param url 
	 *  @param params 
	 *  * @return */
	public static String httpSendJson(String url, String params) {
	    PostMethod method=null;
        HttpClient client=null;
        try{
            client = HttpClientFactory.getHttpClient();
            client.setConnectionTimeout(20*1000);
            method = new PostMethod(url);
			RequestEntity requestEntity = new StringRequestEntity(params,
					"text/xml", "UTF-8");
			method.setRequestEntity(requestEntity);
			method.releaseConnection();
			client.executeMethod(method);
			String responses = method.getResponseBodyAsString();
			return responses;

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
            if(method!=null){
            	method.releaseConnection();
                client=null;
            }
        }
		return null;
	}
	
	/** 发送json请求
	 *  @param url 
	 *  @param params 
	 *  * @return */
	public static void httpSendFile(String url, String params,HttpServletResponse response) {
	    PostMethod method=null;
        HttpClient client=null;
        try{
            client = HttpClientFactory.getHttpClient();
            client.setConnectionTimeout(60*1000);
            method = new PostMethod(url);
			RequestEntity requestEntity = new StringRequestEntity(params,
					"text/xml", "UTF-8");
			method.setRequestEntity(requestEntity);
			method.releaseConnection();
			client.executeMethod(method);
			InputStream inputStream = method.getResponseBodyAsStream(); 
			ServletOutputStream out;
			//设置传输类型
	        response.setContentType("multipart/form-data");
			// 3.通过response获取ServletOutputStream对象(out)
			out = response.getOutputStream();
			int b = 0;
			byte[] buffer = new byte[512];
			while (b != -1) {
				b = inputStream.read(buffer);
				// 4.写到输出流(out)中
				if(b != -1){
					out.write(buffer, 0, b);
				}
			
			}
			inputStream.close();
			out.close();
			out.flush();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
            if(method!=null){
            	method.releaseConnection();
                client=null;
            }
        }
	}

    public static String generateRequestMessage(String content,String setServiceCode){
		
		TcpCont  tcpCont=new TcpCont();
		tcpCont.setSrcSysID("1003");
		tcpCont.setServiceCode(setServiceCode);
		tcpCont.setSrcSysPassWord("1003");
		tcpCont.setSrcSysSign("123456");
		tcpCont.setTransactionID("1322017119413");
		String header=JSON.toJSONString(tcpCont);
		return BapUtil.getReqSvcContent(content, header);
		
	}
    
    public static Result parseRes(String res){
		ResMessage msg=JSONObject.parseObject(res, ResMessage.class);
		Result result = JSONObject.parseObject(msg.getResSvcCont().getResult(), Result.class);		
		return result;
		
	}

    public static void main(String[] args) throws ServiceException, IOException {
    	String url="http://sdk.zyer.cn/SmsService/SmsService.asmx/SendEx";
    	String bodyContent="LoginName=ah10000&Password=81884dab600dc921&SmsKind=808&SendSim=18096695591&ExpSmsId=&MsgContext=123456";
    	
    	String str=httpSendMsg(url, bodyContent);
    	System.out.println(str);
    	
    }
}

package cn.seocoo.platform.common.base;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;



import cn.seocoo.platform.common.memcache.MemCachedClientEnhance;
import cn.seocoo.platform.common.util.SpringHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.odchina.micro.shiro.ShiroUser;
import com.odchina.micro.util.HttpUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.tydic.framework.util.StringUtil;

public class BaseAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {

	private static final long serialVersionUID = -7478111245993929289L;

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	private static final SerializeConfig config;
	
	private static final Logger logger = Logger.getLogger(BaseAction.class);
    
    static {
        config = new SerializeConfig();
        //config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式     
        //config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式 
    }
    
    private static final SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, // 输出空置字段         
        SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null         
        SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null         
        SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null         
        SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
        SerializerFeature.PrettyFormat //格式化
        
        };
    
    //加载memcahe对象
	public static MemCachedClientEnhance memCacheEnhance = null;
	static {
		memCacheEnhance = (MemCachedClientEnhance) SpringHelper
				.getSpringHelper().getBean("memcachedClientEnhance");
	}
    
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	 /**
	  * 返回json格式的报文
	  * @param json 要返回的json报文
	  */
    public void sendMessages(String json) throws IOException {
		response.setContentType("text/json; charset=utf-8"); 
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json);
    }

    public String getParameters(){
    	
    	String str="";
    	Map<String, String> param=HttpUtils.getParams(request);
    	Object s[] = param.keySet().toArray();
		for(int i = 0; i < param.size(); i++) {
			str+=s[i]+"="+param.get(s[i])+"&";
		}
		if(str.endsWith("&")){
			str=str.substring(0,str.length()-1);
		}
    	return str;
    }
    
	 /**
	  * 返回json格式的报文
	  * @param json 要返回的json报文
	  */
   public String Obj2Json(Object object)  {
	   
	   return JSONObject.toJSONString(object, config, features);
   }

   /**
	 * 读取request数据
	 * @param request
	 * @return
	 */
	public String readRequestMsg(HttpServletRequest request){
		java.io.BufferedReader bis = null;
		String line = null;
		String result = "";
		try {
			bis = new java.io.BufferedReader(new java.io.InputStreamReader(
					request.getInputStream(),"UTF-8"));
			while ((line = bis.readLine()) != null) {
				result += line + "\r\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 回写数据
	 * @param responseMsg
	 * @throws IOException
	 */
	public void writeBack(String responseMsg) throws IOException{
		  response.setContentType("text/html; charset=UTF-8");
          // // 给支付平台反馈相应消息
          PrintWriter printWriter = response.getWriter();//
          printWriter.write(responseMsg);
          printWriter.flush();
          printWriter.close();
	}
	/**
	 * 回写文件
	 * @param file
	 */
	public void writeBack(File file) {
		 //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)  
        response.setHeader("Content-Disposition", "attachment;fileName="+file.getName());  
        response.setContentLength((int)file.length());
		ServletOutputStream out;
		try {
			FileInputStream inputStream = new FileInputStream(file);
			 BufferedInputStream buff=new BufferedInputStream(inputStream);
			byte [] b=new byte[1024];//相当于我们的缓存
			// 3.通过response获取ServletOutputStream对象(out)
			out = response.getOutputStream();
			long k=0;//该值用于计算当前实际下载了多少字节
	        while(k<file.length()){
	            int j=buff.read(b,0,1024);
	            k+=j;
	            //将b中的数据写到客户端的内存
	            out.write(b,0,j);

	        }
	        System.out.println("down load size=="+k+"==============");
			inputStream.close();
			out.close();
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 回写流
	 * @param file
	 */
	public void writeBack(InputStream inputStream) {
		 //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
        response.setContentType("multipart/form-data");  
		ServletOutputStream out;
		try {

			// 3.通过response获取ServletOutputStream对象(out)
			out = response.getOutputStream();

			int b = 0;
			byte[] buffer = new byte[512];
			while (b != -1) {
				b = inputStream.read(buffer);
				// 4.写到输出流(out)中
				out.write(buffer, 0, b);
			}
			inputStream.close();
			out.close();
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 获取登录用户信息
	 * @return
	 */
	public static ShiroUser queryCurrentShiroUser() {
			try {
			WebSubject ws = (WebSubject) SecurityUtils.getSubject();
			return (ShiroUser) ws.getPrincipal();
			} catch (Exception e) {
			return null;
			}
		}
	/**
	 * 机构存储类型转换
	 * @param manages
	 * @return
	 */
	public List<String> stringToListOrgan(String manages){
		String[] strs = manages.split(",");
		return Arrays.asList(strs);
	}
	
    /**
	  * 返回json格式的报文
	  * @param json 要返回的json报文
	  */
   public void sendMessages(int rsp_id,String msg) throws IOException {
	   response.setContentType("text/html; charset=utf-8"); 
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write("Auth: " + rsp_id + "\r\n");
      response.getWriter().write("Message: " + msg);
      response.getWriter().close();
  }
   
   /**
	   * 获取HttpServletRequest
	   * 中的值转换为Map
	   * 如果是数组，用逗号分隔。
	   * @param request
	   * @return
	   * @see [类、类#方法、类#成员]
	   */
	  public static Map<String,String> getParameterMap(HttpServletRequest request) {
	      // 参数Map
	      Map properties = request.getParameterMap();
	      // 返回值Map
	      Map returnMap = new HashMap();
	      Iterator entries = properties.entrySet().iterator();
	      Map.Entry entry;
	      String name = "";
	      String value = "";
	      while (entries.hasNext()) {
	          entry = (Map.Entry) entries.next();
	          name = (String) entry.getKey();
	          Object valueObj = entry.getValue();
	          if(null == valueObj){
	              value = "";
	          }else if(valueObj instanceof String[]){
	              String[] values = (String[])valueObj;
	              for(int i=0;i<values.length;i++){
	                  value = values[i] + "|";
	              }
	              value = value.substring(0, value.length()-1);
	          }else{
	              value = valueObj.toString();
	          }
	          returnMap.put(name, value);
	      }
	      String referer=request.getHeader("REFERER");
	      return returnMap;
	  }
 
}

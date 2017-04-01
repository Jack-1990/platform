package cn.seocoo.platform.unite.server.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.UserInfo;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

public class UpdateUserPasswordServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(UpdateUserPasswordServiceImpl.class);

	
	/**
	 * 用户账号密码修改
	 */
	public Object sevice(String param) {
		logger.debug("UpdateUserPassword 请求报文 :"+param);
	    Result reslut=new Result();
	    UserInfo user =JSONObject.parseObject(param, UserInfo.class);
	    reslut.setResultCode(Constant.RESULT_CODE_FAIL);
	    //检验入参
	    String checkMsg=checkParam(user); 
	    if("OK".equals(checkMsg)){
	    	String resourcePath = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
			String url=resourcePath+"/auth!queryUserInfo.do";
			String bodyContent="loginName="+user.getLoginName();
	    	String str="";
			try {
				str = BapUtil.httpSendMsg(url, bodyContent);
			} catch (IOException e) {
			}
	    	JSONObject  jsonObject = JSONObject.parseObject(str);
	    	Map map = (Map)jsonObject;
	    	if(map.get("msg").toString().contains("haveData")){
	    		List<UserInfo> userInfoList=JSONObject.parseArray(map.get("managerInfo").toString(), UserInfo.class);
	    	    //获取用户id进行修改密码
	    		Long userId=userInfoList.get(0).getId();
	    	    String urls=resourcePath+"/auth!updatePassword.do";
    			String bodyContents="id="+userId+"&password="+user.getPassword();
    	    	String strs="";
				try {
					strs = BapUtil.httpSendMsg(urls, bodyContents);
				} catch (IOException e) {
					
				}
    	    	if(strs.contains("SUCCESS")){
    	    		reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
    	    		reslut.setResultMsg("修改成功");
    	    	}else{
    	    		reslut.setResultMsg("修改失败");
    	    	}
	    	}else{
	    		reslut.setResultMsg("用户不存在");
	    	}
	    }else{
			reslut.setResultMsg(checkMsg);
	    }
		String msg = JSON.toJSONString(reslut);
		logger.debug("UpdateUserPassword 返回报文 :"+msg);
		return msg;
	}

	
	/**
	 * 入参检验
	 * @param user
	 * @return
	 */
	private String checkParam(UserInfo user) {
		if(user ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(user.getLoginName())){
			return "用户账号不能为空";
		}
        if(StringUtil.isEmpty(user.getPassword())){
        	return "用户登陆密码不能为空";
		}
        if(user.getPassword().length()<6 || user.getPassword().length()>16){
        	return "用户登陆密码应该在6-16位之间";
		}
		return "OK";
	}

}

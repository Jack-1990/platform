package cn.seocoo.platform.unite.server.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.push.AppPushMsg;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;
 

class AcceptDeviceTokenServiceImpl implements Service {
	private static final Logger logger = Logger.getLogger(AcceptDeviceTokenServiceImpl.class);
	private MerchantUserService merchantUserService;
	
	@Override
	public Object sevice(String param) {
		Result reslut=new Result();
		reslut.setResultCode(Constant.RESULT_CODE_FAIL);
		logger.info("save device token param:"+param);
		
		MerchantUser user=JSONObject.parseObject(param, MerchantUser.class);
		String vilidationMsg=validate(user);
		if(!StringUtil.isEmpty(vilidationMsg))
		{
			reslut.setResultMsg(vilidationMsg);
		}
		else
		{
			//手机登录一个账号后， 在切换到另一个账户 那么这两个账户 都是这个token， 会收到两次消息
			MerchantUser app=new MerchantUser();
			app.setDeviceToken(user.getDeviceToken());
			//app.setMerchantCode(app.getMerchantCode());
			List<MerchantUser> appList=merchantUserService.queryMerchantUserList(app);
			if(appList !=null && appList.size()>0){
				//清空 这个token下其他的用户
				merchantUserService.updateDeviceToken(app);
			}
			String merchantCode=getMerchantCode(user.getLoginName());
			if(StringUtil.isEmpty(merchantCode)){
				reslut.setResultMsg("该用户信息不存在");
			}else{
				user.setMerchantCode(merchantCode);
				user.setUpdateTime(new Date());
				merchantUserService.updateMerchantUser(user);
				
				reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
				reslut.setResultMsg("更新成功");
			}
		}
		String msg=JSON.toJSONString(reslut);
		return msg;
	}
	
	/**
	 * 推送消息
	 */
	public void pushMsg(AppPushMsg pushMsg){
		
		String param = JSONObject.toJSONString(pushMsg);
		String msg = BapUtil.generateRequestMessage(param,"pushLoginInfo");
		String push_url=SystemConfigUtil.getSingleProperty("msg_push_url").getPropertyValue();
		       //push_url="http://192.168.10.49:8092/push/service/home.do";
		BapUtil.httpSendJson(push_url, msg);
	}
	
	/**
	 * @param mergeOrderBaseReq
	 * @return
	 * 校验入参,包括必填性校验
	 */
	public String validate(MerchantUser userInfo){
		String msg="";
		if(userInfo ==null)
		{
			msg="报文解析错误,";
		}
		else{
			if(StringUtil.isEmpty(userInfo.getLoginName()))
			{
				msg+="登录名不能为空,";
			}
			if(StringUtil.isEmpty(userInfo.getDeviceToken()))
			{
				msg+="token值不能为空,";
			}if(StringUtil.isEmpty(userInfo.getTerminal()))
			{
				msg+="设备来源不能为空,";
			}
		}
		return msg;
	}

	
	/**
	 * 获取门店编码
	 * @param loginName
	 * @return
	 */
	public String getMerchantCode(String loginName){
		String merchantCode="";
		MerchantUser user=new MerchantUser();
		user.setLoginName(loginName);
		List<MerchantUser> list=merchantUserService.queryMerchantUserList(user);
		if(list !=null && list.size() >0){
			merchantCode=list.get(0).getMerchantCode();
		}
		return merchantCode;
	}
	
	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}

	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
	}


}

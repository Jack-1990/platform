package cn.seocoo.platform.unite.server.impl;

import java.util.List;
import org.apache.log4j.Logger;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
 

class QueryAppUserInfoServiceImpl implements Service {
	private static final Logger logger = Logger.getLogger(QueryAppUserInfoServiceImpl.class);
	private MerchantUserService merchantUserService;
	
	@Override
	public Object sevice(String param) {
		Result reslut=new Result();
		reslut.setResultCode(Constant.RESULT_CODE_FAIL);
		
		MerchantUser user=JSONObject.parseObject(param, MerchantUser.class);
		String vilidationMsg=validate(user);
		if(!StringUtil.isEmpty(vilidationMsg))
		{
			reslut.setResultMsg(vilidationMsg);
		}
		else
		{
			//获取用户消息
			MerchantUser app=new MerchantUser();
			app.setLoginName(user.getLoginName());
			List<MerchantUser> appList=merchantUserService.queryMerchantUserList(app);
			if(appList !=null && appList.size()>0){
				app = appList.get(0);
			}
			reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
			reslut.setResultData(app);
		}
		String msg=JSON.toJSONString(reslut);
		return msg;
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
				msg+="登录名不能为空";
			}
		}
		return msg;
	}

	
	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}

	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
	}


}

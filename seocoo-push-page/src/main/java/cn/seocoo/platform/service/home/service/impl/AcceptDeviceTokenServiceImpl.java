package cn.seocoo.platform.service.home.service.impl;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.service.home.service.inf.Service;
import cn.seocoo.platform.unite.Result;

public class AcceptDeviceTokenServiceImpl implements Service {

	private static final Logger logger = Logger.getLogger(AcceptDeviceTokenServiceImpl.class);

	@Override
	public Object sevice(String param) {
		logger.debug("save device token param:" + param);
		Result reslut = new Result();

		MerchantUser userInfo = JSONObject.parseObject(param, MerchantUser.class);
		String vilidationMsg = validate(userInfo);
		if (!StringUtil.isEmpty(vilidationMsg)) {
			reslut.setResultMsg(vilidationMsg);
			reslut.setResultCode(Constant.RESULT_CODE_FAIL);
		} else {
			saveDeviceToken(userInfo);
			reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
		}

		String msg = JSON.toJSONString(reslut);
		return msg;
	}

	/**s
	 * @param mergeOrderBaseReq
	 * @return 校验入参,包括必填性校验
	 */
	public String validate(MerchantUser userInfo) {
		String msg = "";
		if (userInfo == null) {
			msg = "报文解析错误,";
		} else {
			if (StringUtil.isEmpty(userInfo.getLoginName())) {
				msg += "登录名不能为空,";
			}
			if (StringUtil.isEmpty(userInfo.getDeviceToken())) {
				msg += "token值不能为空,";
			}
			if (StringUtil.isEmpty(userInfo.getTerminal())) {
				msg += "设备来源不能为空,";
			}
			/*
			 * if(StringUtil.isEmpty(userInfo.getIsBoss())) {
			 * msg+="身份判断标示不能为空,"; }
			 */
		}
		return msg;
	}

	public void saveDeviceToken(MerchantUser userInfo) {
		String param = JSONObject.toJSONString(userInfo);
		String msg = BapUtil.generateRequestMessage(param, "acceptDeviceToken");
		String url = SystemConfigUtil.getSingleProperty("unite_interface_url").getPropertyValue();
		// url="http://127.0.0.1:8085/unite/service/home.do";
		String res = BapUtil.httpSendJson(url, msg);
	}

}

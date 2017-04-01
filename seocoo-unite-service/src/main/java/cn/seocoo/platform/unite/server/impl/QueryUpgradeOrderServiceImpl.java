package cn.seocoo.platform.unite.server.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.odchina.micro.util.DateUtils;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.PaymentAccount;
import cn.seocoo.platform.model.UserUpgradeOrder;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.userUpgradeOrder.inf.UserUpgradeOrderService;
import cn.seocoo.platform.unite.Result;

/**
 * 查询订单信息接口
 */
public class QueryUpgradeOrderServiceImpl implements Service {
	private Logger logger = Logger.getLogger(this.getClass());
	private UserUpgradeOrderService userUpgradeOrderService;

	@Override
	public Object sevice(String param) {
		logger.debug("SubmitOrderServiceImpl提交订单请求参数：" + param);
		Result reslut=new Result();
		// 参数验证
		String validateRes = validateParam(param);

		UserUpgradeOrder order = JSON.parseObject(param, UserUpgradeOrder.class);
		reslut.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("ok".equals(validateRes)) {
			order=userUpgradeOrderService.queryUserUpgradeOrder(order);
			reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
			reslut.setResultData(order);
		}else{
			reslut.setResultCode(Constant.RESULT_CODE_FAIL);
		}
		String res = JSON.toJSONString(reslut);
		return res;
	}

	// 验证参数
	public String validateParam(String param) {
		UserUpgradeOrder order = JSON.parseObject(param, UserUpgradeOrder.class);
		if (order == null) {
			return "param is null";
		}
		if (order.getOrderNumber() == null || "".equals(order.getOrderNumber())) {
			return "订单号不能为空";
		}
 
		return "ok";
	}

	public UserUpgradeOrderService getUserUpgradeOrderService() {
		return userUpgradeOrderService;
	}

	public void setUserUpgradeOrderService(UserUpgradeOrderService userUpgradeOrderService) {
		this.userUpgradeOrderService = userUpgradeOrderService;
	}
	 
}
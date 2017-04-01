package cn.seocoo.platform.unite.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.Order;
import cn.seocoo.platform.service.order.inf.OrderService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

/**
 * 根据时间和商户号 获取订单信息
 *
 */
public class QueryOrderInfoServiceImpl implements Service
{
	private Logger logger = Logger.getLogger(this.getClass());
	private OrderService orderService;

	@Override
	public Object sevice(String param)
	{
		logger.debug("QueryOrderInfoServiceImpl   根据时间和商户号 获取订单信息：" + param);
		// 参数验证
		String validateRes = validateParam(param);
		// 解析Json
		JSONObject params = JSON.parseObject(param);
		Result result = new Result();
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("ok".equals(validateRes))
		{
			int currentPage = params.getString("currentPage") == null ? 0 : Integer.valueOf(params.getString("currentPage")) - 1;
			int beginRow = (currentPage * Constant.PAGESIZE_FIF);
			Map map = new HashMap();
			map.put("merchantCode", params.getString("merchantCode"));
			map.put("startDate", params.getString("startDate"));
			map.put("endDate", params.getString("endDate"));
			map.put("status", 1);// 完成的订单 或者退款订单
			map.put("beginRow", beginRow);// 完成的订单
			map.put("pageSize", Constant.PAGESIZE_FIF);// 完成的订单

			List<Order> orderList = orderService.queryOrderByTime(map);

			if (orderList != null)
			{

				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultData(orderList);
			} else
			{
				result.setResultCode(Constant.RESULT_CODE_FAIL);
			}
		} else
		{
			result.setResultMsg(validateRes);
		}

		String res = JSON.toJSONString(result);
		logger.debug("QueryOrderInfoServiceImpl   根据时间和商户号 获取订单信息：" + res);
		return res;
	}

	// 验证参数
	public String validateParam(String param)
	{
		JSONObject params = JSON.parseObject(param);
		if (params == null)
		{
			return "param is null";
		}
		if (StringUtils.isBlank(params.getString("merchantCode")))
		{
			return "商户号不能为空";
		}
		if (StringUtils.isBlank(params.getString("startDate")))
		{
			return "开始时间不能为空";
		}
		if (StringUtils.isBlank(params.getString("endDate")))
		{
			return "结束时间不能为空";
		}
		return "ok";
	}

	public OrderService getOrderService()
	{
		return orderService;
	}

	public void setOrderService(OrderService orderService)
	{
		this.orderService = orderService;
	}

}

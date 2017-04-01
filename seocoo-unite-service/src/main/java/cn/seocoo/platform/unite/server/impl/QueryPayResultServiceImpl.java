package cn.seocoo.platform.unite.server.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.unite.Result;
import cn.seocoo.platform.model.Order;
import cn.seocoo.platform.service.order.inf.OrderService;
import cn.seocoo.platform.service.unite.inf.Service;
public class QueryPayResultServiceImpl  implements Service{
	private OrderService orderService;
	@Override
	public Object sevice(String param) {
		Order order = JSONObject.parseObject(param, Order.class);
		Result reslut=new Result();
		String validateRes = validate(order);
		if ("ok".equals(validateRes)){
				order=orderService.queryOrder(order);
				if(order!=null){
			 	reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
				reslut.setResultData(order);
				}else{
				reslut.setResultCode(Constant.RESULT_CODE_FAIL);	
				}
			}else{
				reslut.setResultCode(Constant.RESULT_CODE_FAIL);
				reslut.setResultData(validateRes);
			}
		
		 String res = JSON.toJSONString(reslut);
		 return res;
	}
	// 验证参数
	public String validate(Order order)
	{
		if(order==null){
			return "入参错误";
		}else if(StringUtil.isEmpty(order.getMerchantCode())){
			return "商户编码不能为空";
			
		}else if(StringUtil.isEmpty(order.getOrderNumber()))
		{
			return "订单号不能为空";
		}
		return "ok";
	}
	public OrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
}

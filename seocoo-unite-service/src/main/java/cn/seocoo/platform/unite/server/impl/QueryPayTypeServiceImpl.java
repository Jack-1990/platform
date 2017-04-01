package cn.seocoo.platform.unite.server.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.model.PayChannel;
import cn.seocoo.platform.service.payChannel.inf.PayChannelService;
import cn.seocoo.platform.unite.Result;
import cn.seocoo.platform.service.unite.inf.Service;
public class QueryPayTypeServiceImpl  implements Service{
	private PayChannelService payChannelService;
	@Override
	public Object sevice(String param) {
		PayChannel payChannel = JSONObject.parseObject(param, PayChannel.class);
		Result reslut=new Result();
		List<PayChannel> payChannelList=new ArrayList<PayChannel>();
		
		 if(!StringUtil.isEmpty(payChannel.getMerchantCode())){
			 	payChannelList = payChannelService.queryPayChannelList(payChannel);
			 	reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
				reslut.setResultData(payChannelList);
			}else{
				reslut.setResultCode(Constant.RESULT_CODE_FAIL);
				reslut.setResultData("商家编码为必传字段");
			}
		
		 String res = JSON.toJSONString(reslut);
		 return res;
	}
	public PayChannelService getPayChannelService() {
		return payChannelService;
	}
	public void setPayChannelService(PayChannelService payChannelService) {
		this.payChannelService = payChannelService;
	}
}

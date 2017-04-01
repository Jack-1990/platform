package cn.seocoo.platform.unite.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.PushMsg;
import cn.seocoo.platform.service.pushMsg.inf.PushMsgService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.framework.util.StringUtil;

public class QueryPushMsgServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(QueryPushMsgServiceImpl.class);
	private PushMsgService pushMsgService;

	/**
	 * 消息列表查询 --分页
	 */
	public Object sevice(String param) {
		logger.debug("QueryPushMsgServiceImpl 请求报文 :"+param);
		Result result=new Result();
		PushMsg  msg = JSONObject.parseObject(param, PushMsg.class);
		// 参数验证
		String validateRes = validateParam(msg);
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){
			//获取列表
			Map map = new HashMap();
			map.put("target", msg.getTarget());
			map.put("beginRow", msg.getBeginRow() * msg.getPageSize());
			map.put("pageSize", msg.getPageSize());
			
			List<PushMsg> pageList=pushMsgService.queryPushMsgPage(map);
			
			String manageUrl = SystemConfigUtil.getSingleProperty("manage_visit_path").getPropertyValue();
		    for(int i=0;i<pageList.size();i++){
		    	int id=pageList.get(i).getId();
			    String url=manageUrl + "/wap!showPushMsgToUser.do?id="+id;
			    pageList.get(i).setMsgUrl(url);
		    }
			
			result.setResultCode(Constant.RESULT_CODE_SUCCESS);
			result.setResultMsg("查询成功");
			result.setResultData(pageList);
			
		}else{
			result.setResultMsg(validateRes);
		}
		String resultMsg = JSON.toJSONString(result);
		logger.debug("QueryPushMsgServiceImpl 返回报文 :"+resultMsg);
		return resultMsg;
	}

	
	
	/**
	 * 验证参数
	 * @param user
	 * @return
	 */
	public String validateParam(PushMsg wap){
		if(wap ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(wap.getTarget())){
			return "消息接受人不能为空";
		}
		if(StringUtil.isEmpty(wap.getBeginRow().toString())){
			return "当前页不能为空";
		}
		if(StringUtil.isEmpty(wap.getPageSize().toString())){
			return "每页记录数不能为空";
		}
		return "OK";
	}



	public PushMsgService getPushMsgService() {
		return pushMsgService;
	}

	public void setPushMsgService(PushMsgService pushMsgService) {
		this.pushMsgService = pushMsgService;
	}
	
}

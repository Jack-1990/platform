package cn.seocoo.platform.unite.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.WithdrawCash;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.withdrawCash.inf.WithdrawCashService;
import cn.seocoo.platform.unite.Result;

/**
 * 根据商户编号查询 该商户体现信息
 *
 */
public class QueryWithdrawCashInfoServiceImpl implements Service
{
	private Logger logger = Logger.getLogger(this.getClass());

	private WithdrawCashService withdrawCashService;

	@Override
	public Object sevice(String param)
	{
		logger.debug("QueryWithdrawCashInfoServiceImpl 信息：" + param);
		// 解析Json
		WithdrawCash withdrawCash = JSON.parseObject(param, WithdrawCash.class); // 参数验证
		String validateRes = validateParam(withdrawCash);
		Result result = new Result();
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("ok".equals(validateRes))
		{
			int currentPage = withdrawCash.getCurrentPage() == null ? 0 : Integer.valueOf(withdrawCash.getCurrentPage()) - 1;
			int beginRow = (currentPage * Constant.PAGESIZE_FIF);

			Map map=new HashMap();
			map.put("merchantCode", withdrawCash.getMerchantCode());
			map.put("beginRow", beginRow);
			map.put("pageSize", Constant.PAGESIZE_FIF);

			List<WithdrawCash> withdrawCashList = withdrawCashService.queryWithdrawCashPage(map);
	    	
			if (withdrawCashList != null)
			{

				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultData(withdrawCashList);
			} else
			{
				result.setResultCode(Constant.RESULT_CODE_FAIL);
				result.setResultMsg("获取获取失败");
			}
		} else
		{
			result.setResultMsg(validateRes);
		}

		String res = JSON.toJSONString(result);
		logger.debug("QueryWithdrawCashInfoServiceImpl  信息：" + res);
		return res;
	}

	// 验证参数
	public String validateParam(WithdrawCash withdrawCash)
	{

		if (withdrawCash == null)
		{
			return "param is null";
		}
		if (StringUtils.isBlank(withdrawCash.getMerchantCode()))
		{
			return "商户号不能为空";
		}
		return "ok";
	}

	public WithdrawCashService getWithdrawCashService()
	{
		return withdrawCashService;
	}

	public void setWithdrawCashService(WithdrawCashService withdrawCashService)
	{
		this.withdrawCashService = withdrawCashService;
	}



}

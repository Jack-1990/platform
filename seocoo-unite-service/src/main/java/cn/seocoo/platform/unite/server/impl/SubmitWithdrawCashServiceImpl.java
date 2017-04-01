package cn.seocoo.platform.unite.server.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.WithdrawCash;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.withdrawCash.inf.WithdrawCashService;
import cn.seocoo.platform.unite.Result;

/**
 * 申请体现
 *
 */
public class SubmitWithdrawCashServiceImpl implements Service
{
	private Logger logger = Logger.getLogger(this.getClass());

	private WithdrawCashService withdrawCashService;

	private MerchantInfoService merchantInfoService;
	

	@Override
	public Object sevice(String param)
	{
		logger.debug("SubmitWithdrawCashServiceImpl 信息：" + param);
		// 解析Json
		WithdrawCash withdrawCash = JSON.parseObject(param, WithdrawCash.class);
		// 参数验证
		String validateRes = validateParam(withdrawCash);
		Result result = new Result();
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("ok".equals(validateRes))
		{
			// 查询申请人资料
			MerchantInfo merchantInfo = new MerchantInfo();
			merchantInfo.setMerchantCode(withdrawCash.getMerchantCode());
			merchantInfo.setCreateUser(withdrawCash.getLoginName());
			List<MerchantInfo> queryMerchantInfoAndBankAndPic = merchantInfoService.queryMerchantInfoAndBankAndPic(merchantInfo);
			if(queryMerchantInfoAndBankAndPic!=null && queryMerchantInfoAndBankAndPic.size()>0){
				merchantInfo = queryMerchantInfoAndBankAndPic.get(0);
				withdrawCash.setMerchantName(merchantInfo.getRealName());
				withdrawCash.setBankName(merchantInfo.getAffiliatedBank());
				withdrawCash.setCardNumber(merchantInfo.getCardNumber());
			}
			
			withdrawCash.setApplyTime(new Date());
			withdrawCash.setWithdrawStatus(0);// (0、申请中，1、已完成，2、已撤销)
			try
			{
				withdrawCashService.saveWithdrawCash(withdrawCash);// 保存数据
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultData("提现成功");
			} catch (Exception e)
			{
				result.setResultCode(Constant.RESULT_CODE_FAIL);
				result.setResultMsg("提现失败");
			}
		} else
		{
			result.setResultMsg(validateRes);
		}

		String res = JSON.toJSONString(result);
		logger.debug("SubmitWithdrawCashServiceImpl  信息：" + res);
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
			return "申请提现商户编码不能为空";
		}
		if (StringUtils.isBlank(withdrawCash.getLoginName()))
		{
			return "申请提现人不能为空";
		}
		if (withdrawCash.getWithdrawAmount() == null)
		{
			return "申请提现金额不能为空";
		}
		MerchantInfo merchantInfo = new MerchantInfo();
		merchantInfo.setMerchantCode(withdrawCash.getMerchantCode());
		List<MerchantInfo> queryMerchantInfoList = merchantInfoService.queryMerchantInfoList(merchantInfo);
		Double subAmount = -1.0;
		if (queryMerchantInfoList != null && queryMerchantInfoList.size() > 0)
		{
			merchantInfo = queryMerchantInfoList.get(0);
			subAmount = merchantInfo.getCurrentTotalProfit() - withdrawCash.getWithdrawAmount();
		}
		if (subAmount < 0)
		{
			return "申请提现超额";
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

	public MerchantInfoService getMerchantInfoService()
	{
		return merchantInfoService;
	}

	public void setMerchantInfoService(MerchantInfoService merchantInfoService)
	{
		this.merchantInfoService = merchantInfoService;
	}



}

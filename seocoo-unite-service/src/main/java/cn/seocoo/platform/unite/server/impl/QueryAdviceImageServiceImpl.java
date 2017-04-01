package cn.seocoo.platform.unite.server.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.AdviceImage;
import cn.seocoo.platform.service.adviceImage.inf.AdviceImageService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

/**
 * 根据商户编号查询 广告轮播图
 *
 */
public class QueryAdviceImageServiceImpl implements Service
{
	private Logger logger = Logger.getLogger(this.getClass());

	private AdviceImageService adviceImageService;

	@Override
	public Object sevice(String param)
	{
		logger.debug("QueryAdviceImageServiceImpl 信息：" + param);
		// 解析Json
		AdviceImage adviceImage = JSON.parseObject(param, AdviceImage.class); // 参数验证
		String validateRes = validateParam(adviceImage);
		Result result = new Result();
		result.setResultCode(Constant.RESULT_CODE_FAIL);
		// 获取图片地址
		String userpic = SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
		if ("ok".equals(validateRes))
		{
			adviceImage.setMerchantCode("");// 没有商户的概念
			List<AdviceImage> adviceImageList = adviceImageService.queryAdviceImageList(adviceImage);

			if (adviceImageList != null)
			{
				for (AdviceImage adviceImage2 : adviceImageList)
				{
					if(StringUtils.isNotBlank(adviceImage2.getPicUrl())){
						adviceImage2.setPicUrl(userpic + adviceImage2.getPicUrl());
					}
					if(StringUtils.isNotBlank(adviceImage2.getJumpUrl())){
						adviceImage2.setJumpUrl(userpic + adviceImage2.getJumpUrl());
					}
				}

				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultData(adviceImageList);
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
		logger.debug("QueryAdviceImageServiceImpl  信息：" + res);
		return res;
	}

	// 验证参数
	public String validateParam(AdviceImage adviceImage)
	{

		if (adviceImage == null)
		{
			return "param is null";
		}

		return "ok";
	}

	public AdviceImageService getAdviceImageService()
	{
		return adviceImageService;
	}

	public void setAdviceImageService(AdviceImageService adviceImageService)
	{
		this.adviceImageService = adviceImageService;
	}

}

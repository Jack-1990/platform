package cn.seocoo.platform.unite.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.tydic.framework.util.StringUtil;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.DimDicInfo;
import cn.seocoo.platform.model.Upgrade;
import cn.seocoo.platform.service.dimDicInfo.inf.DimDicInfoService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.upgrade.inf.UpgradeService;
import cn.seocoo.platform.unite.Result;

public class QueryUpgradeListServiceImpl implements Service
{
	private static final Logger logger = Logger.getLogger(QueryUpgradeListServiceImpl.class);
	private UpgradeService upgradeService;

	private DimDicInfoService dimDicInfoService;

	/**
	 * 商户信息查询
	 */
	public Object sevice(String param)
	{
		logger.debug("QueryMerchantUserServiceImpl 请求报文 :" + param);
		Result result = new Result();
		Upgrade upgrade = JSON.parseObject(param, Upgrade.class);

		// 参数验证
		String validateRes = validateParam(upgrade);
		result.setResultCode(Constant.RESULT_CODE_FAIL);
		// 获取图片地址
		String userpic = SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
		if ("OK".equals(validateRes))
		{
			// 获取列表
			List<Upgrade> upgradeList = upgradeService.queryUpgradeList(upgrade);
			DimDicInfo dimDicInfo = new DimDicInfo();
			dimDicInfo.setCode("level");
			List<DimDicInfo> dimDicAttrValue = dimDicInfoService.queryDimDicAttrValue(dimDicInfo);
			List<DimDicInfo> dimDicAttrValueList = new ArrayList<DimDicInfo>();
			boolean flag = false ;
			if (upgradeList != null && upgradeList.size() > 0)
			{
				for (DimDicInfo dimDicInfo2 : dimDicAttrValue)
				{
					List<Upgrade> upList = new ArrayList<Upgrade>();
					for (Upgrade up : upgradeList)
					{
						if (StringUtils.equals(dimDicInfo2.getAttrCode(), up.getToLevelCode()))
						{
							flag = true ;
							upList.add(up);
						}
					}
					dimDicInfo2.setLogo(userpic + dimDicInfo2.getLogo());
					dimDicInfo2.setUpgradeList(upList);
					
					if (StringUtils.equals(upgrade.getFromLevelCode(), dimDicInfo2.getAttrCode()))
					{
						flag = true ;
					}
					//将当前等级以外的除去
					if(flag){
						dimDicAttrValueList.add(dimDicInfo2);
					}
				}

				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultMsg("获取成功");
				result.setResultData(dimDicAttrValueList);
			} else
			{
				if(StringUtils.equals(upgrade.getFromLevelCode(), "l001"))
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultData(new ArrayList<Object>());
			}
		} else
		{
			result.setResultMsg(validateRes);
		}
		String msg = JSON.toJSONString(result);
		logger.debug("QueryMerchantUserServiceImpl 返回报文 :" + msg);
		return msg;
	}

	/**
	 * 验证参数
	 * 
	 * @param user
	 * @return
	 */
	public String validateParam(Upgrade upgrade)
	{
		if (upgrade == null)
		{
			return "入参错误";
		}
		if (StringUtil.isEmpty(upgrade.getFromLevelCode()))
		{
			return "会员当前等级不能为空";
		}
		return "OK";
	}

	public UpgradeService getUpgradeService()
	{
		return upgradeService;
	}

	public void setUpgradeService(UpgradeService upgradeService)
	{
		this.upgradeService = upgradeService;
	}

	public DimDicInfoService getDimDicInfoService()
	{
		return dimDicInfoService;
	}

	public void setDimDicInfoService(DimDicInfoService dimDicInfoService)
	{
		this.dimDicInfoService = dimDicInfoService;
	}

}

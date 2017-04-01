package cn.seocoo.platform.unite.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.tydic.framework.util.StringUtil;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.service.dimDicInfo.inf.DimDicInfoService;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

/**
 * APP  我的商户
 *
 */
public class QueryMyMerchantServiceImpl implements Service{
	private Logger logger = Logger.getLogger(this.getClass());
	private MerchantInfoService merchantInfoService;
	private DimDicInfoService dimDicInfoService;
	
	@Override
	public Object sevice(String param){
		logger.debug("QueryMyMerchantServiceImpl 请求参数：" + param);
		MerchantUser user = JSON.parseObject(param, MerchantUser.class);
		// 参数验证
		String validateRes = validateParam(user);
		Result result = new Result();
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){

	    	Map map1=new HashMap();
	    	map1.put("parentMerchantCode", user.getMerchantCode());
			// 2.商户数 (直接商户总数个数 )
			int sumMerNum = merchantInfoService.queryMerchantInfoPageCount(map1);
	    	
			// 3.直接商户带等级
			MerchantInfo merchantInfo = new MerchantInfo();
			merchantInfo.setParentMerchantCode(user.getMerchantCode());
			List<MerchantInfo> merchantInfoList = merchantInfoService.queryMerchantDirectUserAndLevel(merchantInfo);
			String visitPath=SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
			for (MerchantInfo merchantInfo2 : merchantInfoList)
			{
				if (StringUtils.isNotBlank(merchantInfo2.getLogo()))
				{
					merchantInfo2.setLogo(visitPath + merchantInfo2.getLogo());
				}
			}
//			// 4.间接商户
//			List<MerchantUser> merchantChildList = merchantUserService.queryMerchantChildList(user);
	    	
	    	Map mp=new HashMap();
	    	//mp.put("sumAmount", sumAmount);
	    	mp.put("sumMerNum", sumMerNum);
			mp.put("sumDirectMer", merchantInfoList);
	    	
	    	result.setResultCode(Constant.RESULT_CODE_SUCCESS);
	    	result.setResultMsg("查询成功");
	    	result.setResultData(mp);
		} else{
			result.setResultMsg(validateRes);
		}
		String res = JSON.toJSONString(result);
		logger.debug("QueryMyMerchantServiceImpl 返回信息报文：" + res);
		return res;
	 }
	
	
	/**
	 * 验证参数
	 * @param user
	 * @return
	 */
	public String validateParam(MerchantUser user){
		if(user ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(user.getMerchantCode())){
			return "商户号不能为空";
		}
		return "OK";
	}

	public MerchantInfoService getMerchantInfoService()
	{
		return merchantInfoService;
	}

	public void setMerchantInfoService(MerchantInfoService merchantInfoService)
	{
		this.merchantInfoService = merchantInfoService;
	}

	public DimDicInfoService getDimDicInfoService() {
		return dimDicInfoService;
	}

	public void setDimDicInfoService(DimDicInfoService dimDicInfoService) {
		this.dimDicInfoService = dimDicInfoService;
	}

}

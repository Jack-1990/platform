package cn.seocoo.platform.unite.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.DimDicInfo;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.UserIdinfo;
import cn.seocoo.platform.service.dimDicInfo.inf.DimDicInfoService;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.userIdinfo.inf.UserIdinfoService;
import cn.seocoo.platform.unite.Result;


/**
 * 获取我的直接商户
 *
 */
public class QueryDirectMerchantServiceImpl implements Service{
	private Logger logger = Logger.getLogger(this.getClass());
	private MerchantInfoService merchantInfoService;
	private UserIdinfoService userIdinfoService;
	private DimDicInfoService dimDicInfoService;
	
	private MerchantUserService merchantUserService ;
	
	@Override
	public Object sevice(String param){
		logger.debug("QueryMyMerchantServiceImpl 请求参数：" + param);
		MerchantInfo merchantInfo = JSON.parseObject(param, MerchantInfo.class);
		// 参数验证
		String validateRes = validateParam(merchantInfo);
		Result result = new Result();
		result.setResultCode(Constant.RESULT_CODE_FAIL);
		if ("OK".equals(validateRes)){
			int currentPage = merchantInfo.getCurrentPage() == null ? 0 : Integer.valueOf(merchantInfo.getCurrentPage()) - 1;
			int beginRow = (currentPage * Constant.PAGESIZE_FIF);
			Map map=new HashMap();
			map.put("parentMerchantCode",merchantInfo.getMerchantCode());
			// 根据父级 商户号 查询
			map.put("beginRow", beginRow);
			map.put("pageSize", Constant.PAGESIZE_FIF);
			map.put("level", merchantInfo.getLevel());
			if (merchantInfo.getCertifyStatus() != null)
			{
				map.put("certifyStatus", merchantInfo.getCertifyStatus());
			}
			String visitPath = SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
			List<MerchantInfo> merchantInfoList = merchantInfoService.queryMerchantInfoPage(map);
	    	for(int i=0;i<merchantInfoList.size();i++){
	    		String merchantCode=merchantInfoList.get(i).getMerchantCode();
	    		merchantInfoList.get(i).setLoginName(merchantInfoList.get(i).getCreateUser());
	    		//真实姓名
	    		String realName="";
	    		UserIdinfo  idInfo=new UserIdinfo();
	    		idInfo.setMerchantCode(merchantCode);
	    		List<UserIdinfo>  idInfoList=userIdinfoService.queryUserIdinfoList(idInfo);
	    		if(idInfoList !=null && idInfoList.size()>0){
	    			realName=idInfoList.get(0).getRealName();
	    		}
	    		//等级名称
	    		String levelName="";
	    		DimDicInfo di=new DimDicInfo();
				di.setCode(Constant.DIMDIC_LEVEL);
				di.setAttrCode(merchantInfoList.get(i).getLevel());
				List<DimDicInfo>  dimList=dimDicInfoService.queryDimDicInfoList(di);
				if(dimList !=null && dimList.size()>0){
					levelName=dimList.get(0).getAttrName();
				}
				merchantInfoList.get(i).setRealName(realName);
				merchantInfoList.get(i).setLevelName(levelName);
				//添加头像
				MerchantUser merchantUser = new MerchantUser();
				List<MerchantUser> queryMerchantUserList = merchantUserService.queryMerchantUserList(merchantUser);
				for (MerchantUser merchantUser2 : queryMerchantUserList)
				{
					if (StringUtils.equals(merchantInfoList.get(i).getMerchantCode(), merchantUser2.getMerchantCode()))
					{
						merchantInfoList.get(i).setHeadPic(visitPath + merchantUser2.getHeadPic());
					}
				}
	    	}
			
	    	result.setResultCode(Constant.RESULT_CODE_SUCCESS);
	    	result.setResultMsg("查询成功");
			result.setResultData(merchantInfoList);
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
	public String validateParam(MerchantInfo merchantInfo)
	{
		if (merchantInfo == null)
		{
			return "入参错误";
		}
		if (StringUtil.isEmpty(merchantInfo.getMerchantCode()))
		{
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

	public UserIdinfoService getUserIdinfoService() {
		return userIdinfoService;
	}

	public void setUserIdinfoService(UserIdinfoService userIdinfoService) {
		this.userIdinfoService = userIdinfoService;
	}
	public DimDicInfoService getDimDicInfoService() {
		return dimDicInfoService;
	}

	public void setDimDicInfoService(DimDicInfoService dimDicInfoService) {
		this.dimDicInfoService = dimDicInfoService;
	}


	public MerchantUserService getMerchantUserService()
	{
		return merchantUserService;
	}


	public void setMerchantUserService(MerchantUserService merchantUserService)
	{
		this.merchantUserService = merchantUserService;
	}
	
	
	
}

package cn.seocoo.platform.unite.server.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.WapPage;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.wapPage.inf.WapPageService;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.framework.util.StringUtil;

public class QueryWapPageServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(QueryWapPageServiceImpl.class);
	private WapPageService wapPageService;
	private MerchantUserService merchantUserService;

	/**
	 * wap页列表查询
	 */
	public Object sevice(String param) {
		logger.debug("QueryWapPageServiceImpl 请求报文 :"+param);
		Result result=new Result();
		WapPage  wap = JSONObject.parseObject(param, WapPage.class);
		// 参数验证
		String validateRes = validateParam(wap);
		result.setResultCode(Constant.RESULT_CODE_FAIL);
        String loginName=wap.getLoginName();
		if ("OK".equals(validateRes)){
			String merchantCode=getMerchantCode(loginName);
			//获取列表
			String visitPath=SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
			
			wap.setStatus("show");
			List<WapPage> pageList=wapPageService.queryWapPageList(wap);
            //分享的带上分享人信息
			if("share".equals(wap.getPageType())){
            	for(int i=0;i< pageList.size();i++){
            		wap=pageList.get(i);
            		if(!StringUtil.isEmpty(wap.getPageLogo())){
            			pageList.get(i).setPageLogo(visitPath + wap.getPageLogo());
            		}
            		if(!StringUtil.isEmpty(wap.getPageLink())){
            			//带上分享人
            			pageList.get(i).setPageLink(wap.getPageLink() +"?userCode="+loginName+"&merchantCode="+merchantCode);
            		}
            		
            	}
            }
			result.setResultCode(Constant.RESULT_CODE_SUCCESS);
			result.setResultMsg("查询成功");
			result.setResultData(pageList);
			
		}else{
			result.setResultMsg(validateRes);
		}
		String msg = JSON.toJSONString(result);
		logger.debug("QueryWapPageServiceImpl 返回报文 :"+msg);
		return msg;
	}

	
	
	/**
	 * 验证参数
	 * @param user
	 * @return
	 */
	public String validateParam(WapPage wap){
		if(wap ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(wap.getPageType())){
			return "页面类型不能为空";
		}
		if("share".equals(wap.getPageType()) &&StringUtil.isEmpty(wap.getLoginName())){
			return "登陆账号不能为空";
		}
		return "OK";
	}

	/**
	 * 获取门店编码
	 * @param loginName
	 * @return
	 */
	public String getMerchantCode(String loginName){
		String merchantCode="";
		MerchantUser user=new MerchantUser();
		user.setLoginName(loginName);
		List<MerchantUser> list=merchantUserService.queryMerchantUserList(user);
		if(list !=null && list.size() >0){
			merchantCode=list.get(0).getMerchantCode();
		}
		return merchantCode;
	}


	public WapPageService getWapPageService() {
		return wapPageService;
	}

	public void setWapPageService(WapPageService wapPageService) {
		this.wapPageService = wapPageService;
	}

	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}

	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
	}
	
	
}

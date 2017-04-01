package cn.seocoo.platform.unite.server.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.UserImage;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.userImage.inf.UserImageService;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.framework.util.StringUtil;

public class QueryUserImageServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(QueryUserImageServiceImpl.class);
	private UserImageService userImageService;
	private MerchantUserService merchantUserService;

	/**
	 * 商户图片信息查询
	 */
	public Object sevice(String param) {
		logger.debug("QueryUserImageServiceImpl 请求报文 :"+param);
		Result result=new Result();
		UserImage  user = JSONObject.parseObject(param, UserImage.class);
		// 参数验证
		String validateRes = validateParam(user);
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){
			String merchantCode=getMerchantCode(user.getLoginName());
			user.setMerchantCode(merchantCode);
			//获取列表
			List<UserImage> userList=userImageService.queryUserImageList(user);
			if(userList !=null && userList.size() >0){
				user=userList.get(0);
				
				//指定格式返回
				String iDAfter=user.getiD_after_pic();
				String iDBefore=user.getiD_before_pic();
				String bankBefore=user.getBank_before_pic();
				String bankAfter=user.getBank_after_pic();
				
				String visitPath=SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
				if(!StringUtil.isEmpty(iDAfter)){
					iDAfter =visitPath+iDAfter;
	             }
				if(!StringUtil.isEmpty(iDBefore)){
					iDBefore =visitPath+iDBefore;
	             }
				if(!StringUtil.isEmpty(bankBefore)){
					bankBefore =visitPath+bankBefore;
	             }
				if(!StringUtil.isEmpty(bankAfter)){
					bankAfter =visitPath+bankAfter;
	             }
				user.setIdAfterStr(iDAfter);
				user.setIdBeforeStr(iDBefore);
				user.setBankBeforeStr(bankBefore);
				user.setBankAfterStr(bankAfter);
				
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultMsg("获取成功");
				result.setResultData(user);
			}else{
				result.setResultMsg("无此用户的证件照");
			}
		}else{
			result.setResultMsg(validateRes);
		}
		String msg = JSON.toJSONString(result);
		logger.debug("QueryUserImageServiceImpl 返回报文 :"+msg);
		return msg;
	}

	
	
	/**
	 * 验证参数
	 * @param user
	 * @return
	 */
	public String validateParam(UserImage user){
		if(user ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(user.getLoginName())){
			return "用户账号不能为空";
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
	
	


	public UserImageService getUserImageService() {
		return userImageService;
	}

	public void setUserImageService(UserImageService userImageService) {
		this.userImageService = userImageService;
	}

	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}

	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
	}
	
	
}

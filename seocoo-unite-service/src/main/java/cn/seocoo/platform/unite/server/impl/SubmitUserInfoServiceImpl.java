package cn.seocoo.platform.unite.server.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.tydic.framework.util.StringUtil;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.UserBank;
import cn.seocoo.platform.model.UserIdinfo;
import cn.seocoo.platform.model.UserImage;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.userBank.inf.UserBankService;
import cn.seocoo.platform.service.userIdinfo.inf.UserIdinfoService;
import cn.seocoo.platform.service.userImage.inf.UserImageService;
import cn.seocoo.platform.unite.Result;

public class SubmitUserInfoServiceImpl implements Service{
	private Logger logger = Logger.getLogger(this.getClass());
	private UserBankService userBankService;
	private MerchantUserService merchantUserService;
	private UserImageService userImageService;
	private MerchantInfoService merchantInfoService;
	private UserIdinfoService  userIdinfoService; 

	/**
	 * 用户基本信息提交审核
	 */
	public Object sevice(String param){
		logger.debug("SubmitUserInfoServiceImpl 请求参数：" + param);
		MerchantUser user = JSON.parseObject(param, MerchantUser.class);
		// 参数验证
		String validateRes = validateParam(user);
		Result result = new Result();
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){
			String loginName=user.getLoginName();
			String merchantCode=getMerchantCode(loginName);

			MerchantInfo u=new MerchantInfo();
			u.setMerchantCode(merchantCode);
			List<MerchantInfo> uList=merchantInfoService.queryMerchantInfoList(u);
            if(uList !=null && uList.size()>0){
            	if(!StringUtil.isEmpty(uList.get(0).getCertifyStatus().toString())){
            		int status=uList.get(0).getCertifyStatus();
            		if(status ==1 ){
            			result.setResultMsg("信息正在审核，请不要重复提交");
            		}else if(status==2){
            			result.setResultMsg("信息已经审核通过，请不要再次提交");
            		}else{
            			String val="OK";
            			//1.个人姓名身份证号码信息
            			UserIdinfo idinfo=new UserIdinfo();
            			idinfo.setMerchantCode(merchantCode);
            			List<UserIdinfo> idinfoList=userIdinfoService.queryUserIdinfoList(idinfo);
            			if(idinfoList ==null || idinfoList.size()==0){
            				val="个人身份信息未填写";
            			}
            			
            			//2.个人银行卡信息
            			UserBank bank=new UserBank();
            			bank.setMerchantCode(merchantCode);
            			List<UserBank> bankList=userBankService.queryUserBankList(bank);
            			if(bankList ==null || bankList.size()==0){
            				val="个人银行卡信息未填写";
            			}
            			
            			//3.个人银行卡和身份证图片信息
            			UserImage image=new UserImage();
            			image.setMerchantCode(merchantCode);
            			List<UserImage> imageList=userImageService.queryUserImageList(image);
            			if(imageList ==null || imageList.size()==0){
            				val="个人银行卡和身份证图片信息未填写";
            			}
            			
            			if("OK".equals(val)){
            				u.setCertifyStatus(1);
            				u.setSubmitAuditTime(new Date());
            				merchantInfoService.updateMerchantInfo(u);
            				
            				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
        					result.setResultMsg("提交成功");
            			}else{
            				result.setResultMsg(val);
            			}
            		}
				}else{
					result.setResultMsg("该用户审核信息不明确");
				}
            }else{
            	result.setResultMsg("用户不存在");
            }
		} else{
			result.setResultMsg(validateRes);
		}
		String res = JSON.toJSONString(result);
		logger.debug("SubmitUserInfoServiceImpl 返回信息报文：" + res);
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

	public UserBankService getUserBankService() {
		return userBankService;
	}

	public void setUserBankService(UserBankService userBankService) {
		this.userBankService = userBankService;
	}

	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}

	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
	}

	public UserImageService getUserImageService() {
		return userImageService;
	}

	public void setUserImageService(UserImageService userImageService) {
		this.userImageService = userImageService;
	}

	public MerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public UserIdinfoService getUserIdinfoService() {
		return userIdinfoService;
	}

	public void setUserIdinfoService(UserIdinfoService userIdinfoService) {
		this.userIdinfoService = userIdinfoService;
	}
	
	
}

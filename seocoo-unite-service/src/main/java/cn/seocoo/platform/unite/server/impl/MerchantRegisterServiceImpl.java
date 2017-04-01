package cn.seocoo.platform.unite.server.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.odchina.micro.util.StringTools;

import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.QRCodeUtil;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.RateDimAttr;
import cn.seocoo.platform.model.UserRelationship;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.rateDimAttr.inf.RateDimAttrService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.userRelationship.inf.UserRelationshipService;
import cn.seocoo.platform.unite.Result;


public class MerchantRegisterServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(MerchantRegisterServiceImpl.class);
    private MerchantUserService merchantUserService;
    private UserRelationshipService  userRelationshipService;
	private MerchantInfoService merchantInfoService;
	private RateDimAttrService rateDimAttrService;
    
    /**
     * 用户注册
     */
	public Object sevice(String param) {
		    logger.debug("MerchantRegisterServiceImpl 请求报文 :"+param);
		    Result reslut=new Result();
		    MerchantUser user=JSONObject.parseObject(param, MerchantUser.class);
		    //入参检验
		    String checkMsg=checkParam(user);
		    if("OK".equals(checkMsg)){
		    	String role = SystemConfigUtil.getSingleProperty("payMerchantRole").getPropertyValue();
				String resourcePath = SystemConfigUtil.getSingleProperty("user_server_path").getPropertyValue();
		    	//sso查询是否有此用户
				boolean  sso=checkUserSSO(user.getLoginName(),resourcePath);
				//manage查询是否有此用户
				boolean  mana=checkUserManage(user.getLoginName());
				
				if(sso && mana){
					//SSO查询该用户角色是否是50，不是则绑定一个这个角色
		    		String url = resourcePath + "/auth!validateUserRole.do";
					String bodyContent ="loginName=" + user.getLoginName()+"&roleIds=" + role;
					String str =null;
					try {
						str = BapUtil.httpSendMsg(url, bodyContent);
					} catch (IOException e) {
					}
					
					reslut.setResultCode(Constant.RESULT_CODE_FAIL);
					reslut.setResultMsg("此账号已经注册");
				}
				else if(sso && !mana){
					//在manage里面新增
					 savaUser(user);
					 
					//SSO查询该用户角色是否是50，不是则绑定一个这个角色
		    		String url = resourcePath + "/auth!validateUserRole.do";
					String bodyContent ="loginName=" + user.getLoginName()+"&roleIds=" + role;
					String str =null;
					try {
						str = BapUtil.httpSendMsg(url, bodyContent);
					} catch (IOException e) {
					}
					
					 reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
					 reslut.setResultMsg("注册成功");
				}
				else if(!sso && mana){
					//在sso里面新增
					String creator=StringUtil.isEmpty(user.getParentUser())?"auto":user.getParentUser();
		    		// 保存 账号信息
					String url = resourcePath + "/auth!saveRegisterUser.do";
					String bodyContent = "loginName=" + user.getLoginName() + "&password=" + user.getPassWord() + "&phone="+user.getLoginName()
					                    +"&trueName="+user.getLoginName()+"&status=1&roleIds=" + role + "&creator=" + creator;
					String str =null;
					try {
						str = BapUtil.httpSendMsg(url, bodyContent);
					} catch (IOException e) {
						e.printStackTrace();
						reslut.setResultCode(Constant.RESULT_CODE_FAIL);
					}
					if (str.contains(Constant.RESULT_CODE_SUCCESS)){
						reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
						reslut.setResultMsg("注册成功");
					}else{
						reslut.setResultCode(Constant.RESULT_CODE_FAIL);
						reslut.setResultMsg("注册失败");
					}
				}
				else if(!sso && !mana){
					//直接新增
					
					String creator=StringUtil.isEmpty(user.getParentUser())?"auto":user.getParentUser();
		    		//1. sso保存 账号信息
					String url = resourcePath + "/auth!saveRegisterUser.do";
					String bodyContent = "loginName=" + user.getLoginName() + "&password=" + user.getPassWord() + "&phone="+user.getLoginName()
					                    +"&trueName="+user.getLoginName()+"&status=1&roleIds=" + role + "&creator=" + creator;
					String str ="";
					try {
						str = BapUtil.httpSendMsg(url, bodyContent);
					} catch (IOException e) {
						reslut.setResultCode(Constant.RESULT_CODE_FAIL);
					}
					if (str.contains(Constant.RESULT_CODE_SUCCESS)){
						//2.在manage里面新增
						 savaUser(user);
						 
						reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
						reslut.setResultMsg("注册成功");
					}else{
						reslut.setResultCode(Constant.RESULT_CODE_FAIL);
						reslut.setResultMsg("注册失败");
					}
				}
		    }else{
		    	reslut.setResultCode(Constant.RESULT_CODE_FAIL);
				reslut.setResultMsg(checkMsg);
		    }
			String msg = JSON.toJSONString(reslut);
			logger.debug("MerchantRegisterServiceImpl 返回报文 :"+msg);
			return msg;
	}

	
	/**
	 * sso检验账号是否重复
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean checkUserSSO(String userCode,String resourcePath){
		String url = resourcePath + "/auth!validateRegisterUser.do";
		String bodyContent = "loginName=" + userCode;
		String str=null;
		try {
			str = BapUtil.httpSendMsg(url, bodyContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if ("haveUser".equals(str)){
			return true;
		} else{
			return false;
		}
	}
	
	
	
	/**
	 * manage根据用户code检验用户是否存在
	 * @param userCode
	 * @return
	 */
	public boolean checkUserManage(String userCode){
		MerchantUser mu=new MerchantUser();
		mu.setLoginName(userCode);
		List<MerchantUser>  muLiat=merchantUserService.queryMerchantUserList(mu);
		if(muLiat==null || muLiat.size()==0){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 保存用户信息
	 * @param user 
	 */
	public void savaUser(MerchantUser user){
		
		// 初始化商户号 
		String merchantCode = "O" + StringTools.getRandomString(21);
		MerchantInfo info=new MerchantInfo();
		//新用户默认最低级别
		RateDimAttr di=new RateDimAttr();
		di.setDimCode(Constant.DIMDIC_LEVEL);
		List<RateDimAttr>  dimList=rateDimAttrService.queryRateDimAttrWithLastLevel(di);
		if(dimList !=null && dimList.size()>0){
			info.setLevel(dimList.get(0).getDimAttrCode());
		}
		
		if(!StringUtil.isEmpty(user.getParentUser())){
			//代入上级merchantCode
			MerchantUser u=new MerchantUser();
			u.setLoginName(user.getParentUser());
			List<MerchantUser> uList=merchantUserService.queryMerchantUserList(u);
	        if(uList !=null && uList.size()>0){
	        	info.setParentMerchantCode(uList.get(0).getMerchantCode());
	        }
		}
		
		//保存本地
		user.setMerchantCode(merchantCode);
		user.setCreateTime(new Date());
		merchantUserService.saveMerchantUser(user);
		
		//静态二维码 内容
        String receiptQrCode = SystemConfigUtil.getSingleProperty("manage_visit_path").getPropertyValue();
        receiptQrCode += "/codeToPay.do?merchantCode="+merchantCode;
        info.setReceiptQrCode(receiptQrCode);
        
		double profit=0.00;//默认金额为0
		info.setMerchantCode(merchantCode);
		info.setParentUser(user.getParentUser());
		info.setBank("MS");
		info.setCertifyStatus(0);
		info.setCreateTime(new Date());
		info.setCreateUser(user.getLoginName());
		info.setCurrentTotalProfit(profit);
		info.setTotalProfit(profit);
		merchantInfoService.saveMerchantInfo(info);
		
		//保存关系表
		UserRelationship ship=new UserRelationship();
		ship.setLoginName(user.getLoginName());
		List<UserRelationship> shipList=userRelationshipService.queryUserRelationshipList(ship);
		ship.setMerchantCode(merchantCode);
		if(shipList !=null && shipList.size()>0){
			userRelationshipService.updateUserRelationship(ship);
		}else{
			userRelationshipService.saveUserRelationship(ship);
		}
		
		//商家付款码图片地址
	/*	String receiptQrCode=getPicUrl(merchantCode);
		if(!StringUtil.isEmpty(receiptQrCode)){
			MerchantInfo m=new MerchantInfo();
			m.setMerchantCode(merchantCode);
			m.setReceiptQrCode(receiptQrCode);
			merchantInfoService.updateMerchantInfo(m);
		}*/
	}
	
	
	/**
	 * 生成商家付款码图片，并返回图片地址
	 * @return
	 */
	public String getPicUrl(String merchantCode){
		String picUrl="";
		//静态二维码 内容
        String userpic = SystemConfigUtil.getSingleProperty("manage_visit_path").getPropertyValue();
        userpic += "/codeToPay.do?merchantCode="+merchantCode;
        //中间logo
        String logoPath =SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue()+"/images/paylogo.png";
        //图片保存路劲
        String imgSavePath =SystemConfigUtil.getSingleProperty("payQrcode_save_path").getPropertyValue();
        //imgSavePath="E://123/";
        String name=merchantCode+".png";
        boolean isCreate=true;
        try {
			QRCodeUtil.createQrcode(userpic,logoPath,imgSavePath +name,true);
		} catch (Exception e) {
			isCreate=false;
		}
        //图片访问路劲
        String imgvisitPath =SystemConfigUtil.getSingleProperty("payQrcode_visit_path").getPropertyValue();
        picUrl = imgvisitPath +name;
        if(!isCreate){
        	picUrl = "";//异常置空
        }
        return picUrl;
	}
	
	
	
	
	/**
	 * 检验入参
	 * @param user
	 * @return
	 */
	public String checkParam(MerchantUser user) {
		if(user ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(user.getLoginName())){
			return "用户登录名不能为空";
		}
		if(StringUtil.isEmpty(user.getPassWord())){
			return "用户登录密码不能为空";
		}
		/*if(StringUtil.isEmpty(user.getParentUser())){
			return "用户的上级代理用户不能为空";
		}*/
		if(!StringUtil.isEmpty(user.getParentUser())){
			if(user.getParentUser().compareTo(user.getLoginName())==0){
				return "不能发展自己为下线";
			}
			//判断上级代理是否存在
			/*MerchantUser mu=new MerchantUser();
			mu.setLoginName(user.getParentUser());
			List<MerchantUser>  muLiat=merchantUserService.queryMerchantUserList(mu);
			if(muLiat==null || muLiat.size()==0){
				return "上级代理用户不存在";
			}*/
		}
		
		return "OK";
	}

	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}

	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
	}

	public UserRelationshipService getUserRelationshipService() {
		return userRelationshipService;
	}

	public void setUserRelationshipService(
			UserRelationshipService userRelationshipService) {
		this.userRelationshipService = userRelationshipService;
	}


	public MerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public RateDimAttrService getRateDimAttrService() {
		return rateDimAttrService;
	}

	public void setRateDimAttrService(RateDimAttrService rateDimAttrService) {
		this.rateDimAttrService = rateDimAttrService;
	}
	

}

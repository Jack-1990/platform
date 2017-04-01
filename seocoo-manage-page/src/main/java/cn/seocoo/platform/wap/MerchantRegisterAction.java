package cn.seocoo.platform.wap;

import java.io.IOException;
import java.util.List;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.Validatecode;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSONObject;

/**
 * 商户注册
 * @author wangyuan
 *
 */
@SuppressWarnings("serial")
public class MerchantRegisterAction extends BaseAction {
	private MerchantUserService merchantUserService;

	/**
	 * 进入注册首页
	 * @return
	 */
	public  String merchantRegister(){
		String parentUser=request.getParameter("parentUser");
		
		request.setAttribute("parentUser", parentUser);
		return "merchantRegister";
	}
	
	
	/**
	 * 检验账号是否重复
	 * 
	 * @return
	 * @throws IOException 
	 */
	public void checkPhone() throws IOException{
		JSONObject json = new JSONObject();
		String loginName=request.getParameter("phone");
		
		MerchantUser mu=new MerchantUser();
		mu.setLoginName(loginName);
		List<MerchantUser>  muLiat=merchantUserService.queryMerchantUserList(mu);
		if(muLiat !=null && muLiat.size() > 0){
			json.put("resultCode", Constant.RESULT_CODE_FAIL);
		}else{
			json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
		}
		this.sendMessages(json.toJSONString());
	}
	
	
	/**
	 * 手机号注册
	 * @throws IOException 
	 */
	public void register() throws IOException{
		JSONObject json = new JSONObject();
		
		String phone=request.getParameter("phone");
		String verifyCode=request.getParameter("verifyCode");
		String password=request.getParameter("password");
		String parentUser=request.getParameter("parentUser");
		
		//1.检验   手机校验码
		Validatecode validateCode = new Validatecode();
		validateCode.setPhone(phone);
		validateCode.setValidateCode(Integer.valueOf(verifyCode));
		
		String param = JSONObject.toJSONString(validateCode);
		String message = BapUtil.generateRequestMessage(param, "checkValidateCode");
		String url = SystemConfigUtil.getSingleProperty("unite_interface_url").getPropertyValue();
		String res = BapUtil.httpSendJson(url, message);
		Result result = BapUtil.parseRes(res);
		
		//2.短信验证码通过，开始注册
		if ("SUCCESS".equals(result.getResultCode())){
			MerchantUser user =new MerchantUser();
			user.setLoginName(phone);
			user.setPassWord(password);
			user.setParentUser(parentUser);
			
			String params = JSONObject.toJSONString(user);
			String messages = BapUtil.generateRequestMessage(params, "merchantRegister");
			String urls = SystemConfigUtil.getSingleProperty("unite_interface_url").getPropertyValue();
			String ress = BapUtil.httpSendJson(urls, messages);
			Result results = BapUtil.parseRes(ress);
			if ("SUCCESS".equals(results.getResultCode())){
				json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
				json.put("resultMsg", results.getResultMsg());
			}else{
				json.put("resultCode", Constant.RESULT_CODE_FAIL);
				json.put("resultMsg", results.getResultMsg());
			}
		}else{
			json.put("resultCode", Constant.RESULT_CODE_FAIL);
			json.put("resultMsg", "手机验证码错误");
		}
		this.sendMessages(json.toJSONString());
	}
	
	
	
	/**
	 * 发送手机短信
	 * @throws IOException 
	 */
	public void sendMsg() throws IOException{
		JSONObject json = new JSONObject();
		String phone=request.getParameter("phone");
		
		Validatecode validateCode = new Validatecode();
		validateCode.setPhone(phone);
		
		String param = JSONObject.toJSONString(validateCode);
		String message = BapUtil.generateRequestMessage(param, "sendValidateCode");
		String url = SystemConfigUtil.getSingleProperty("unite_interface_url").getPropertyValue();
		String res = BapUtil.httpSendJson(url, message);
		Result result = BapUtil.parseRes(res);
		
		if ("SUCCESS".equals(result.getResultCode())){
			json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
		}else{
			json.put("resultCode", Constant.RESULT_CODE_FAIL);
		}
		this.sendMessages(json.toJSONString());
	}
	
	
	
	/**
	 * 进入注册成功首页
	 * @return
	 */
	public  String registerSuccess(){
		return "registerSuccess";
	}
	
	/**
	 * 进入注册下载app页
	 * @return
	 */
	public  String uploadApp(){
		return "uploadApp";
	}

	
	
	
	
	
	
	

	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}

	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
	}
	
}

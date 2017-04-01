package cn.seocoo.platform.unite.server.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.model.Validatecode;
import cn.seocoo.platform.service.validatecode.inf.ValidatecodeService;
import cn.seocoo.platform.unite.Result;
import cn.seocoo.platform.service.unite.inf.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class CheckValidateCodeServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(CheckValidateCodeServiceImpl.class);
	private ValidatecodeService validatecodeService;

	@Override
	public Object sevice(String param) {
		logger.debug("CheckValidateCodeServiceImpl 请求报文 :"+param);
		Validatecode valiCode =JSONObject.parseObject(param, Validatecode.class);
		Result reslut=new Result();
		reslut.setResultCode(Constant.RESULT_CODE_FAIL);
		
		String checkMsg=checkParam(valiCode); 
		if("OK".equals(checkMsg)){
				int codes=valiCode.getValidateCode();
				//用手机号查询出来 存放的随机码
				Validatecode vCode=new Validatecode();
				String phone=valiCode.getPhone();
				
				vCode.setPhone(phone);
				List<Validatecode> vList=validatecodeService.queryValidatecodeList(vCode);
				if(vList!=null && vList.size()>0){
					if(vList.get(0).getValidateCode()==codes){
						reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
						reslut.setResultMsg("验证通过");
					}else{
						reslut.setResultMsg("验证码不一致");
					}
				}else{
					reslut.setResultMsg("无此手机验证码");
				}
		}else{
			reslut.setResultMsg(checkMsg);
		}
		String msg = JSON.toJSONString(reslut);
		return msg;
	}

	
	/**
	 * 入参检验
	 * @param user
	 * @return
	 */
	private String checkParam(Validatecode valiCode) {
		if(valiCode ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(valiCode.getPhone())){
			return "手机号不能为空";
		}
        if(StringUtil.isEmpty(valiCode.getValidateCode())){
        	return "验证码不能为空";
		}
		return "OK";
	}
	
	
	public ValidatecodeService getValidatecodeService() {
		return validatecodeService;
	}

	public void setValidatecodeService(ValidatecodeService validatecodeService) {
		this.validatecodeService = validatecodeService;
	}

}

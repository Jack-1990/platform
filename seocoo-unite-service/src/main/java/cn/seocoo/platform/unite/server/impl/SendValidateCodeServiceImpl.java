package cn.seocoo.platform.unite.server.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SendShortMessageUtil;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.model.Validatecode;
import cn.seocoo.platform.service.validatecode.inf.ValidatecodeService;
import cn.seocoo.platform.unite.Result;
import cn.seocoo.platform.service.unite.inf.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SendValidateCodeServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(SendValidateCodeServiceImpl.class);
	private ValidatecodeService validatecodeService;

	@Override
	public Object sevice(String param) {
		logger.debug("SendValidateCodeServiceImpl 请求报文 :"+param);
		Result reslut=new Result();
		//生成随机验证码
		int validateCode=(int)(Math.random()*1000+1000);
		String text="【尚果科技】您的验证码是"+validateCode;
		Validatecode valiCode =JSONObject.parseObject(param, Validatecode.class);
		String phone=valiCode.getPhone();
		if(!StringUtil.isEmpty(phone)){
			//将手机产生的随机码存放进数据库 如果该手机有记录就更新，没记录就产生一条
			Validatecode vCode=new Validatecode();
			vCode.setPhone(phone);
			List<Validatecode> vList=validatecodeService.queryValidatecodeList(vCode);
			vCode.setValidateCode(validateCode);
			vCode.setGenerateTime(new Date());
			if(vList.size()>0){
				//如果存在  更新
				validatecodeService.updateValidatecode(vCode);
			}else{
				//如果不存在  插入
				validatecodeService.saveValidatecode(vCode);
			}
			//发送随机验证码到手机
			try{
				SendShortMessageUtil.sendSms(text, phone);
				
				reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
				reslut.setResultMsg("手机验证码已发送");
			}catch(Exception e){
				reslut.setResultCode(Constant.RESULT_CODE_FAIL);
				reslut.setResultMsg("手机验证码发送失败");
			}
		}else{
			reslut.setResultCode(Constant.RESULT_CODE_FAIL);
			reslut.setResultMsg("手机号为空");
		}
		String msg = JSON.toJSONString(reslut);
		return msg;
	}

	
	public ValidatecodeService getValidatecodeService() {
		return validatecodeService;
	}

	public void setValidatecodeService(ValidatecodeService validatecodeService) {
		this.validatecodeService = validatecodeService;
	}


}

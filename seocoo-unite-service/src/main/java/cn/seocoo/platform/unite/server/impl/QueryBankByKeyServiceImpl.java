package cn.seocoo.platform.unite.server.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.BankInfo;
import cn.seocoo.platform.service.bankInfo.inf.BankInfoService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.framework.util.StringUtil;

public class QueryBankByKeyServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(QueryBankByKeyServiceImpl.class);
	private BankInfoService bankInfoService;

	/**
	 * 开户行关键字检索
	 */
	public Object sevice(String param) {
		logger.debug("QueryBankByKeyServiceImpl 请求报文 :"+param);
		Result result=new Result();
		BankInfo  bank = JSONObject.parseObject(param, BankInfo.class);
		
		// 参数验证
		String validateRes = validateParam(bank);
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){
			//获取列表
			List<BankInfo> bankList=bankInfoService.queryBankInfoList(bank);
			
			result.setResultCode(Constant.RESULT_CODE_SUCCESS);
			result.setResultMsg("获取成功");
			result.setResultData(bankList);
			
		}else{
			result.setResultMsg(validateRes);
		}
		
		String msg = JSON.toJSONString(result);
		logger.debug("QueryBankByKeyServiceImpl 返回报文 :"+msg);
		return msg;
	}

	
	
	/**
	 * 验证参数
	 * @param user
	 * @return
	 */
	public String validateParam(BankInfo bank){
		if(bank ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(bank.getAreaCode())){
			return "地区不能为空";
		}
		return "OK";
	}
	
	
	
	public BankInfoService getBankInfoService() {
		return bankInfoService;
	}

	public void setBankInfoService(BankInfoService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}
	
}

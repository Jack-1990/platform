package cn.seocoo.platform.unite.server.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.Bank;
import cn.seocoo.platform.service.bank.inf.BankService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;

public class QueryAffiliatedBankServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(QueryAffiliatedBankServiceImpl.class);
	private BankService bankService;

	/**
	 * 所属银行列表返回，无需参数
	 */
	public Object sevice(String param) {
		logger.debug("QueryAffiliatedBankServiceImpl 请求报文 :"+param);
		Result reslut=new Result();
		
		//获取列表
		List<Bank> bankList=bankService.queryBankList(null);
		
		reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
		reslut.setResultMsg("获取成功");
		reslut.setResultData(bankList);
		
		String msg = JSON.toJSONString(reslut);
		logger.debug("QueryAffiliatedBankServiceImpl 返回报文 :"+msg);
		return msg;
	}

	
	
	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}


	
}

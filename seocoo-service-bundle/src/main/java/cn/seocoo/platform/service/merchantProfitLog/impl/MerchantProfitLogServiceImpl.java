package cn.seocoo.platform.service.merchantProfitLog.impl;

import java.util.List;

import cn.seocoo.platform.dao.merchantProfitLog.inf.MerchantProfitLogDao;
import cn.seocoo.platform.model.MerchantProfitLog;
import cn.seocoo.platform.service.merchantProfitLog.inf.MerchantProfitLogService;

public class MerchantProfitLogServiceImpl implements MerchantProfitLogService {

	private MerchantProfitLogDao merchantProfitLogDao;

	public MerchantProfitLogDao getMerchantProfitLogDao() {
		return merchantProfitLogDao;
	}

	public void setMerchantProfitLogDao(MerchantProfitLogDao merchantProfitLogDao) {
		this.merchantProfitLogDao = merchantProfitLogDao;
	}

	@Override
	public MerchantProfitLog queryMerchantProfitLog(MerchantProfitLog merchantProfitLog) {
		return merchantProfitLogDao.queryMerchantProfitLog(merchantProfitLog);
	}

	@Override
	public List<MerchantProfitLog> queryMerchantProfitLogList(MerchantProfitLog merchantProfitLog) {
		return merchantProfitLogDao.queryMerchantProfitLogList(merchantProfitLog);
	}

	@Override
	public void saveMerchantProfitLog(MerchantProfitLog merchantProfitLog) {
		merchantProfitLogDao.saveMerchantProfitLog(merchantProfitLog);
	}

}

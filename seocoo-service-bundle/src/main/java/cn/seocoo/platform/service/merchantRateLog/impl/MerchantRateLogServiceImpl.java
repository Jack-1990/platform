package cn.seocoo.platform.service.merchantRateLog.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.merchantRateLog.inf.MerchantRateLogDao;
import cn.seocoo.platform.model.MerchantRateLog;
import cn.seocoo.platform.service.merchantRateLog.inf.MerchantRateLogService;

public class MerchantRateLogServiceImpl  implements MerchantRateLogService{

	  private MerchantRateLogDao merchantRateLogDao;

    public MerchantRateLogDao getMerchantRateLog() {
        return merchantRateLogDao;
    }
    public void setMerchantRateLogDao(MerchantRateLogDao merchantRateLogDao) {
         this.merchantRateLogDao = merchantRateLogDao;
    }

    @Override
    public MerchantRateLog queryMerchantRateLog(MerchantRateLog merchantRateLog){
        return merchantRateLogDao.queryMerchantRateLog(merchantRateLog);
    }

    @Override
    public List<MerchantRateLog> queryMerchantRateLogList(MerchantRateLog merchantRateLog){
        return merchantRateLogDao.queryMerchantRateLogList(merchantRateLog);
    }
    @Override
    public void saveMerchantRateLog(MerchantRateLog merchantRateLog){
          merchantRateLogDao.saveMerchantRateLog(merchantRateLog);
    }
    @Override
    public void updateMerchantRateLog(MerchantRateLog merchantRateLog){
        merchantRateLogDao.updateMerchantRateLog(merchantRateLog);
    }
    @Override
    public void deleteMerchantRateLog(MerchantRateLog merchantRateLog){
        merchantRateLogDao.deleteMerchantRateLog(merchantRateLog);
    }
    @Override
    public List<MerchantRateLog> queryMerchantRateLogPage(Map map){
        return merchantRateLogDao.queryMerchantRateLogPage(map);
    }
    @Override
    public Integer queryMerchantRateLogPageCount(Map map){
        return merchantRateLogDao.queryMerchantRateLogPageCount(map);
    }
}

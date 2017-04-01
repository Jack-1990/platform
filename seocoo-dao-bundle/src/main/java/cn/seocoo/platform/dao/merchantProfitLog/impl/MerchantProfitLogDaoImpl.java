package cn.seocoo.platform.dao.merchantProfitLog.impl;


import java.util.List;

import com.tydic.framework.base.dao.EntityManagerImpl;

import cn.seocoo.platform.dao.merchantProfitLog.inf.MerchantProfitLogDao;
import cn.seocoo.platform.model.MerchantProfitLog;

public class MerchantProfitLogDaoImpl extends EntityManagerImpl<MerchantProfitLog, Integer> implements MerchantProfitLogDao{

    @Override
    public MerchantProfitLog queryMerchantProfitLog(MerchantProfitLog merchantProfitLog){
        return entityDao.findObj("MerchantProfitLog.queryMerchantProfitLog", merchantProfitLog);
    }

    @Override
    public List<MerchantProfitLog> queryMerchantProfitLogList(MerchantProfitLog merchantProfitLog){
        return entityDao.find("MerchantProfitLog.queryMerchantProfitLog", merchantProfitLog);
    }
    @Override
    public void saveMerchantProfitLog(MerchantProfitLog merchantProfitLog){
         entityDao.save("MerchantProfitLog.saveMerchantProfitLog", merchantProfitLog);
    }
    
 
}

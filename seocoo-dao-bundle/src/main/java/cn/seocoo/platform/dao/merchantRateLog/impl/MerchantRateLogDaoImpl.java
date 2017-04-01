package cn.seocoo.platform.dao.merchantRateLog.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.merchantRateLog.inf.MerchantRateLogDao;
import cn.seocoo.platform.model.MerchantRateLog;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class MerchantRateLogDaoImpl extends EntityManagerImpl<MerchantRateLog, Integer> implements MerchantRateLogDao{

    @Override
    public MerchantRateLog queryMerchantRateLog(MerchantRateLog merchantRateLog){
        return entityDao.findObj("MerchantRateLog.queryMerchantRateLog", merchantRateLog);
    }

    @Override
    public List<MerchantRateLog> queryMerchantRateLogList(MerchantRateLog merchantRateLog){
        return entityDao.find("MerchantRateLog.queryMerchantRateLog", merchantRateLog);
    }
    @Override
    public void saveMerchantRateLog(MerchantRateLog merchantRateLog){
         entityDao.save("MerchantRateLog.saveMerchantRateLog", merchantRateLog);
    }
    @Override
    public void updateMerchantRateLog(MerchantRateLog merchantRateLog){
         entityDao.update("MerchantRateLog.updateMerchantRateLog", merchantRateLog);
    }
    @Override
    public void deleteMerchantRateLog(MerchantRateLog merchantRateLog){
         entityDao.remove("MerchantRateLog.deleteMerchantRateLog", merchantRateLog);
    }
    @Override
    public List<MerchantRateLog> queryMerchantRateLogPage(Map map){
        return entityDao.find("MerchantRateLog.queryMerchantRateLogPage", map);
    }
    @Override
    public Integer queryMerchantRateLogPageCount(Map map){
        return (Integer) entityDao.find("MerchantRateLog.queryMerchantRateLogPageCount", map).get(0);
    }
}

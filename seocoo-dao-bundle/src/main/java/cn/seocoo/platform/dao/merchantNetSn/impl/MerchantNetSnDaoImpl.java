package cn.seocoo.platform.dao.merchantNetSn.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.MerchantNetSn;
import cn.seocoo.platform.dao.merchantNetSn.inf.MerchantNetSnDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class MerchantNetSnDaoImpl extends EntityManagerImpl<MerchantNetSn, Integer> implements MerchantNetSnDao{

    @Override
    public MerchantNetSn queryMerchantNetSn(MerchantNetSn merchantNetSn){
        return entityDao.findObj("MerchantNetSn.queryMerchantNetSn", merchantNetSn);
    }
    
    @Override
    public MerchantNetSn queryMerchantNetSnBySnCode(MerchantNetSn merchantNetSn){
        return entityDao.findObj("MerchantNetSn.queryMerchantNetSnBySnCode", merchantNetSn);
    }

    @Override
    public List<MerchantNetSn> queryMerchantNetSnList(MerchantNetSn merchantNetSn){
        return entityDao.find("MerchantNetSn.queryMerchantNetSn", merchantNetSn);
    }
    @Override
    public void saveMerchantNetSn(MerchantNetSn merchantNetSn){
         entityDao.save("MerchantNetSn.saveMerchantNetSn", merchantNetSn);
    }
    @Override
    public void updateMerchantNetSn(MerchantNetSn merchantNetSn){
         entityDao.update("MerchantNetSn.updateMerchantNetSn", merchantNetSn);
    }
    @Override
    public void deleteMerchantNetSn(MerchantNetSn merchantNetSn){
         entityDao.remove("MerchantNetSn.deleteMerchantNetSn", merchantNetSn);
    }
    @Override
    public List<MerchantNetSn> queryMerchantNetSnPage(Map map){
        return entityDao.find("MerchantNetSn.queryMerchantNetSnPage", map);
    }
    @Override
    public Integer queryMerchantNetSnPageCount(Map map){
        return (Integer) entityDao.find("MerchantNetSn.queryMerchantNetSnPageCount", map).get(0);
    }
}

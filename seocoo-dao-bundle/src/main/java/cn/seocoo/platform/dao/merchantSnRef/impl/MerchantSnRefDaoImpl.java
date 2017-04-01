package cn.seocoo.platform.dao.merchantSnRef.impl;


import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.merchantSnRef.inf.MerchantSnRefDao;
import cn.seocoo.platform.model.MerchantSnRef;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class MerchantSnRefDaoImpl extends EntityManagerImpl<MerchantSnRef, Integer> implements MerchantSnRefDao{

    @Override
    public MerchantSnRef queryMerchantSnRef(MerchantSnRef merchantSnRef){
        return entityDao.findObj("MerchantSnRef.queryMerchantSnRef", merchantSnRef);
    }

    @Override
    public List<MerchantSnRef> queryMerchantSnRefList(MerchantSnRef merchantSnRef){
        return entityDao.find("MerchantSnRef.queryMerchantSnRef", merchantSnRef);
    }
    @Override
    public void saveMerchantSnRef(MerchantSnRef merchantSnRef){
         entityDao.save("MerchantSnRef.saveMerchantSnRef", merchantSnRef);
    }
    @Override
    public void updateMerchantSnRef(MerchantSnRef merchantSnRef){
         entityDao.update("MerchantSnRef.updateMerchantSnRef", merchantSnRef);
    }
    @Override
    public void deleteMerchantSnRef(MerchantSnRef merchantSnRef){
         entityDao.remove("MerchantSnRef.deleteMerchantSnRef", merchantSnRef);
    }
    @Override
    public List<MerchantSnRef> queryMerchantSnRefPage(Map map){
        return entityDao.find("MerchantSnRef.queryMerchantSnRefPage", map);
    }
    @Override
    public Integer queryMerchantSnRefPageCount(Map map){
        return (Integer) entityDao.find("MerchantSnRef.queryMerchantSnRefPageCount", map).get(0);
    }
    
    @Override
	public List<MerchantSnRef> queryByMerchantSnRef(MerchantSnRef merchantSnRef) {
		// TODO Auto-generated method stub
		return entityDao.find("MerchantSnRef.queryByMerchantSnRef", merchantSnRef);
	}
}

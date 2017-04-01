package cn.seocoo.platform.dao.merchant.impl;

import cn.seocoo.platform.dao.merchant.inf.MerchantDao;
import cn.seocoo.platform.model.Merchant;
import com.tydic.framework.base.dao.EntityManagerImpl;

import java.util.List;
import java.util.Map;

public class MerchantDaoImpl extends EntityManagerImpl<Merchant, Integer> implements MerchantDao{

    @Override
    public Merchant queryMerchant(Merchant merchant){
        return entityDao.findObj("Merchant.queryMerchant", merchant);
    }

    @Override
    public List<Merchant> queryMerchantList(Merchant merchant){
        return entityDao.find("Merchant.queryMerchant", merchant);
    }
    @Override
    public void saveMerchant(Merchant merchant){
         entityDao.save("Merchant.saveMerchant", merchant);
    }
    @Override
    public void updateMerchant(Merchant merchant){
         entityDao.update("Merchant.updateMerchant", merchant);
    }
    @Override
    public void deleteMerchant(Merchant merchant){
         entityDao.remove("Merchant.deleteMerchant", merchant);
    }
    @Override
    public List<Merchant> queryMerchantPage(Map map){
        return entityDao.find("Merchant.queryMerchantPage", map);
    }
    @Override
    public Integer queryMerchantPageCount(Map map){
        return (Integer) entityDao.find("Merchant.queryMerchantPageCount", map).get(0);
    }

	@Override
	public void updateMerchantByOutMchntId(Merchant merchant)
	{
		// TODO Auto-generated method stub
		entityDao.update("Merchant.updateMerchantByOutMchntId", merchant);
	}

    @Override
    public List<Merchant> queryMerchantWithMerchantInfo(Map map) {
        return entityDao.find("Merchant.queryMerchantWithMerchantInfo", map);
    }

    @Override
    public Integer queryMerchantWithMerchantInfoCount(Map map) {
        return   (Integer) entityDao.find("Merchant.queryMerchantWithMerchantInfoCount", map).get(0);
    }
}

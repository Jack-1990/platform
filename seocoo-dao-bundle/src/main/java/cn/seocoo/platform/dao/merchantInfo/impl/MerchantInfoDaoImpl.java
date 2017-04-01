package cn.seocoo.platform.dao.merchantInfo.impl;

import java.util.List;
import java.util.Map;

import com.tydic.framework.base.dao.EntityManagerImpl;

import cn.seocoo.platform.dao.merchantInfo.inf.MerchantInfoDao;
import cn.seocoo.platform.model.MerchantInfo;

public class MerchantInfoDaoImpl extends EntityManagerImpl<MerchantInfo, Integer> implements MerchantInfoDao{

    @Override
    public MerchantInfo queryMerchantInfo(MerchantInfo merchantInfo){
        return entityDao.findObj("MerchantInfo.queryMerchantInfo", merchantInfo);
    }

    @Override
    public List<MerchantInfo> queryMerchantInfoList(MerchantInfo merchantInfo){
        return entityDao.find("MerchantInfo.queryMerchantInfo", merchantInfo);
    }
    @Override
    public void saveMerchantInfo(MerchantInfo merchantInfo){
         entityDao.save("MerchantInfo.saveMerchantInfo", merchantInfo);
    }
    @Override
    public void updateMerchantInfo(MerchantInfo merchantInfo){
         entityDao.update("MerchantInfo.updateMerchantInfo", merchantInfo);
    }
    @Override
    public void deleteMerchantInfo(MerchantInfo merchantInfo){
         entityDao.remove("MerchantInfo.deleteMerchantInfo", merchantInfo);
    }
    @Override
    public List<MerchantInfo> queryMerchantInfoPage(Map map){
        return entityDao.find("MerchantInfo.queryMerchantInfoPage", map);
    }
    @Override
    public Integer queryMerchantInfoPageCount(Map map){
        return (Integer) entityDao.find("MerchantInfo.queryMerchantInfoPageCount", map).get(0);
    }
    
    @Override
    public List<MerchantInfo> queryProfitMerchantInfos(MerchantInfo merchantInfo){
        return entityDao.find("MerchantInfo.queryProfitMerchantInfos", merchantInfo);
    }
    
	@Override
	public List<MerchantInfo> queryMerchantDirectUserAndLevel(MerchantInfo merchantInfo)
	{
		// TODO Auto-generated method stub
		return entityDao.find("MerchantInfo.queryMerchantDirectUserAndLevel", merchantInfo);
	}

	@Override
	public List<MerchantInfo> queryMerchantUserPageWithMerchant(Map map)
	{
		// TODO Auto-generated method stub
		return entityDao.find("MerchantInfo.queryMerchantUserPageWithMerchant", map);
	}

	@Override
	public List<MerchantInfo> queryMerchantInfoAndBankAndPic(MerchantInfo merchantInfo)
	{
		// TODO Auto-generated method stub
		return entityDao.find("MerchantInfo.queryMerchantInfoAndBankAndPic", merchantInfo);
	}
	
	public List<MerchantInfo> queryParentList(MerchantInfo merchantInfo)
	{
		// TODO Auto-generated method stub
		return entityDao.find("MerchantInfo.queryParentList", merchantInfo);
	}
	
    @Override
    public void updateMerchantInfoProfit(MerchantInfo merchantInfo){
	  entityDao.update("MerchantInfo.updateMerchantInfoProfit", merchantInfo);
    }
	    
}

package cn.seocoo.platform.service.merchantSnRef.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.merchantSnRef.inf.MerchantSnRefDao;
import cn.seocoo.platform.model.MerchantSnRef;
import cn.seocoo.platform.service.merchantSnRef.inf.MerchantSnRefService;

public class MerchantSnRefServiceImpl  implements MerchantSnRefService{

	  private MerchantSnRefDao merchantSnRefDao;

    public MerchantSnRefDao getMerchantSnRef() {
        return merchantSnRefDao;
    }
    public void setMerchantSnRefDao(MerchantSnRefDao merchantSnRefDao) {
         this.merchantSnRefDao = merchantSnRefDao;
    }

    @Override
    public MerchantSnRef queryMerchantSnRef(MerchantSnRef merchantSnRef){
        return merchantSnRefDao.queryMerchantSnRef(merchantSnRef);
    }

    @Override
    public List<MerchantSnRef> queryMerchantSnRefList(MerchantSnRef merchantSnRef){
        return merchantSnRefDao.queryMerchantSnRefList(merchantSnRef);
    }
    @Override
    public void saveMerchantSnRef(MerchantSnRef merchantSnRef){
          merchantSnRefDao.saveMerchantSnRef(merchantSnRef);
    }
    @Override
    public void updateMerchantSnRef(MerchantSnRef merchantSnRef){
        merchantSnRefDao.updateMerchantSnRef(merchantSnRef);
    }
    @Override
    public void deleteMerchantSnRef(MerchantSnRef merchantSnRef){
        merchantSnRefDao.deleteMerchantSnRef(merchantSnRef);
    }
    @Override
    public List<MerchantSnRef> queryMerchantSnRefPage(Map map){
        return merchantSnRefDao.queryMerchantSnRefPage(map);
    }
    @Override
    public Integer queryMerchantSnRefPageCount(Map map){
        return merchantSnRefDao.queryMerchantSnRefPageCount(map);
    }
    
	@Override
	public List<MerchantSnRef> queryByMerchantSnRef(MerchantSnRef merchantSnRef) {
		 
		return merchantSnRefDao.queryByMerchantSnRef(merchantSnRef);
	}
}

package cn.seocoo.platform.service.merchantNetSn.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.MerchantNetSn;
import cn.seocoo.platform.service.merchantNetSn.inf.MerchantNetSnService;
import cn.seocoo.platform.dao.merchantNetSn.inf.MerchantNetSnDao;

public class MerchantNetSnServiceImpl  implements MerchantNetSnService{

	  private MerchantNetSnDao merchantNetSnDao;

    public MerchantNetSnDao getMerchantNetSn() {
        return merchantNetSnDao;
    }
    public void setMerchantNetSnDao(MerchantNetSnDao merchantNetSnDao) {
         this.merchantNetSnDao = merchantNetSnDao;
    }

    @Override
    public MerchantNetSn queryMerchantNetSn(MerchantNetSn merchantNetSn){
        return merchantNetSnDao.queryMerchantNetSn(merchantNetSn);
    }
    
    @Override
    public MerchantNetSn queryMerchantNetSnBySnCode(MerchantNetSn merchantNetSn){
    	return merchantNetSnDao.queryMerchantNetSnBySnCode(merchantNetSn);
    }
    
    @Override
    public List<MerchantNetSn> queryMerchantNetSnList(MerchantNetSn merchantNetSn){
        return merchantNetSnDao.queryMerchantNetSnList(merchantNetSn);
    }
    @Override
    public void saveMerchantNetSn(MerchantNetSn merchantNetSn){
          merchantNetSnDao.saveMerchantNetSn(merchantNetSn);
    }
    @Override
    public void updateMerchantNetSn(MerchantNetSn merchantNetSn){
        merchantNetSnDao.updateMerchantNetSn(merchantNetSn);
    }
    @Override
    public void deleteMerchantNetSn(MerchantNetSn merchantNetSn){
        merchantNetSnDao.deleteMerchantNetSn(merchantNetSn);
    }
    @Override
    public List<MerchantNetSn> queryMerchantNetSnPage(Map map){
        return merchantNetSnDao.queryMerchantNetSnPage(map);
    }
    @Override
    public Integer queryMerchantNetSnPageCount(Map map){
        return merchantNetSnDao.queryMerchantNetSnPageCount(map);
    }
}

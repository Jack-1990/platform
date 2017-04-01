package cn.seocoo.platform.service.merchantUser.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.dao.merchantUser.inf.MerchantUserDao;

public class MerchantUserServiceImpl  implements MerchantUserService{

	  private MerchantUserDao merchantUserDao;

    public MerchantUserDao getMerchantUser() {
        return merchantUserDao;
    }
    public void setMerchantUserDao(MerchantUserDao merchantUserDao) {
         this.merchantUserDao = merchantUserDao;
    }

    @Override
    public MerchantUser queryMerchantUser(MerchantUser merchantUser){
        return merchantUserDao.queryMerchantUser(merchantUser);
    }

    @Override
    public List<MerchantUser> queryMerchantUserList(MerchantUser merchantUser){
        return merchantUserDao.queryMerchantUserList(merchantUser);
    }
    @Override
    public void saveMerchantUser(MerchantUser merchantUser){
          merchantUserDao.saveMerchantUser(merchantUser);
    }
    @Override
    public void updateMerchantUser(MerchantUser merchantUser){
        merchantUserDao.updateMerchantUser(merchantUser);
    }
    @Override
    public void deleteMerchantUser(MerchantUser merchantUser){
        merchantUserDao.deleteMerchantUser(merchantUser);
    }
    @Override
    public List<MerchantUser> queryMerchantUserPage(Map map){
        return merchantUserDao.queryMerchantUserPage(map);
    }
    @Override
    public Integer queryMerchantUserPageCount(Map map){
        return merchantUserDao.queryMerchantUserPageCount(map);
    }
    
    public void updateDeviceToken(MerchantUser merchantUser){
        merchantUserDao.updateDeviceToken(merchantUser);
    }
    
    public void updateTokenByLoginName(MerchantUser merchantUser){
        merchantUserDao.updateTokenByLoginName(merchantUser);
    }
}

package cn.seocoo.platform.dao.merchantUser.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.dao.merchantUser.inf.MerchantUserDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class MerchantUserDaoImpl extends EntityManagerImpl<MerchantUser, Integer> implements MerchantUserDao{

    @Override
    public MerchantUser queryMerchantUser(MerchantUser merchantUser){
        return entityDao.findObj("MerchantUser.queryMerchantUser", merchantUser);
    }

    @Override
    public List<MerchantUser> queryMerchantUserList(MerchantUser merchantUser){
        return entityDao.find("MerchantUser.queryMerchantUser", merchantUser);
    }
    @Override
    public void saveMerchantUser(MerchantUser merchantUser){
         entityDao.save("MerchantUser.saveMerchantUser", merchantUser);
    }
    @Override
    public void updateMerchantUser(MerchantUser merchantUser){
         entityDao.update("MerchantUser.updateMerchantUser", merchantUser);
    }
    @Override
    public void deleteMerchantUser(MerchantUser merchantUser){
         entityDao.remove("MerchantUser.deleteMerchantUser", merchantUser);
    }
    @Override
    public List<MerchantUser> queryMerchantUserPage(Map map){
        return entityDao.find("MerchantUser.queryMerchantUserPage", map);
    }
    @Override
    public Integer queryMerchantUserPageCount(Map map){
        return (Integer) entityDao.find("MerchantUser.queryMerchantUserPageCount", map).get(0);
    }
    
    public void updateDeviceToken(MerchantUser merchantUser){
        entityDao.update("MerchantUser.updateDeviceToken", merchantUser);
   }
    
    public void updateTokenByLoginName(MerchantUser merchantUser){
        entityDao.update("MerchantUser.updateTokenByLoginName", merchantUser);
   }
}

package cn.seocoo.platform.dao.groupMerchantPay.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.GroupMerchantPay;
import cn.seocoo.platform.dao.groupMerchantPay.inf.GroupMerchantPayDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class GroupMerchantPayDaoImpl extends EntityManagerImpl<GroupMerchantPay, Integer> implements GroupMerchantPayDao{

    @Override
    public GroupMerchantPay queryGroupMerchantPay(GroupMerchantPay groupMerchantPay){
        return entityDao.findObj("GroupMerchantPay.queryGroupMerchantPay", groupMerchantPay);
    }

    @Override
    public List<GroupMerchantPay> queryGroupMerchantPayList(GroupMerchantPay groupMerchantPay){
        return entityDao.find("GroupMerchantPay.queryGroupMerchantPay", groupMerchantPay);
    }
    @Override
    public void saveGroupMerchantPay(GroupMerchantPay groupMerchantPay){
         entityDao.save("GroupMerchantPay.saveGroupMerchantPay", groupMerchantPay);
    }
    @Override
    public void updateGroupMerchantPay(GroupMerchantPay groupMerchantPay){
         entityDao.update("GroupMerchantPay.updateGroupMerchantPay", groupMerchantPay);
    }
    @Override
    public void deleteGroupMerchantPay(GroupMerchantPay groupMerchantPay){
         entityDao.remove("GroupMerchantPay.deleteGroupMerchantPay", groupMerchantPay);
    }
    @Override
    public List<GroupMerchantPay> queryGroupMerchantPayPage(Map map){
        return entityDao.find("GroupMerchantPay.queryGroupMerchantPayPage", map);
    }
    @Override
    public Integer queryGroupMerchantPayPageCount(Map map){
        return (Integer) entityDao.find("GroupMerchantPay.queryGroupMerchantPayPageCount", map).get(0);
    }
    public void updateByMerchantCode(GroupMerchantPay groupMerchantPay){
        entityDao.update("GroupMerchantPay.updateByMerchantCode", groupMerchantPay);
   }
}

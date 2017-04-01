package cn.seocoo.platform.service.groupMerchantPay.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.GroupMerchantPay;
import cn.seocoo.platform.service.groupMerchantPay.inf.GroupMerchantPayService;
import cn.seocoo.platform.dao.groupMerchantPay.inf.GroupMerchantPayDao;

public class GroupMerchantPayServiceImpl  implements GroupMerchantPayService{

	  private GroupMerchantPayDao groupMerchantPayDao;

    public GroupMerchantPayDao getGroupMerchantPay() {
        return groupMerchantPayDao;
    }
    public void setGroupMerchantPayDao(GroupMerchantPayDao groupMerchantPayDao) {
         this.groupMerchantPayDao = groupMerchantPayDao;
    }

    @Override
    public GroupMerchantPay queryGroupMerchantPay(GroupMerchantPay groupMerchantPay){
        return groupMerchantPayDao.queryGroupMerchantPay(groupMerchantPay);
    }

    @Override
    public List<GroupMerchantPay> queryGroupMerchantPayList(GroupMerchantPay groupMerchantPay){
        return groupMerchantPayDao.queryGroupMerchantPayList(groupMerchantPay);
    }
    @Override
    public void saveGroupMerchantPay(GroupMerchantPay groupMerchantPay){
          groupMerchantPayDao.saveGroupMerchantPay(groupMerchantPay);
    }
    @Override
    public void updateGroupMerchantPay(GroupMerchantPay groupMerchantPay){
        groupMerchantPayDao.updateGroupMerchantPay(groupMerchantPay);
    }
    @Override
    public void deleteGroupMerchantPay(GroupMerchantPay groupMerchantPay){
        groupMerchantPayDao.deleteGroupMerchantPay(groupMerchantPay);
    }
    @Override
    public List<GroupMerchantPay> queryGroupMerchantPayPage(Map map){
        return groupMerchantPayDao.queryGroupMerchantPayPage(map);
    }
    @Override
    public Integer queryGroupMerchantPayPageCount(Map map){
        return groupMerchantPayDao.queryGroupMerchantPayPageCount(map);
    }
    
    public void updateByMerchantCode(GroupMerchantPay groupMerchantPay){
        groupMerchantPayDao.updateByMerchantCode(groupMerchantPay);
    }
}

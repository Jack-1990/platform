package cn.seocoo.platform.dao.userUpgradeOrder.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.UserUpgradeOrder;
import cn.seocoo.platform.dao.userUpgradeOrder.inf.UserUpgradeOrderDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class UserUpgradeOrderDaoImpl extends EntityManagerImpl<UserUpgradeOrder, Integer> implements UserUpgradeOrderDao{

    @Override
    public UserUpgradeOrder queryUserUpgradeOrder(UserUpgradeOrder userUpgradeOrder){
        return entityDao.findObj("UserUpgradeOrder.queryUserUpgradeOrder", userUpgradeOrder);
    }

    @Override
    public List<UserUpgradeOrder> queryUserUpgradeOrderList(UserUpgradeOrder userUpgradeOrder){
        return entityDao.find("UserUpgradeOrder.queryUserUpgradeOrder", userUpgradeOrder);
    }
    @Override
    public void saveUserUpgradeOrder(UserUpgradeOrder userUpgradeOrder){
         entityDao.save("UserUpgradeOrder.saveUserUpgradeOrder", userUpgradeOrder);
    }
    @Override
    public void updateUserUpgradeOrder(UserUpgradeOrder userUpgradeOrder){
         entityDao.update("UserUpgradeOrder.updateUserUpgradeOrder", userUpgradeOrder);
    }
    @Override
    public void deleteUserUpgradeOrder(UserUpgradeOrder userUpgradeOrder){
         entityDao.remove("UserUpgradeOrder.deleteUserUpgradeOrder", userUpgradeOrder);
    }
    @Override
    public List<UserUpgradeOrder> queryUserUpgradeOrderPage(Map map){
        return entityDao.find("UserUpgradeOrder.queryUserUpgradeOrderPage", map);
    }
    @Override
    public Integer queryUserUpgradeOrderPageCount(Map map){
        return (Integer) entityDao.find("UserUpgradeOrder.queryUserUpgradeOrderPageCount", map).get(0);
    }
    @Override
    public void updateOrderByOrderNumber(UserUpgradeOrder userUpgradeOrder){
    	  entityDao.save("UserUpgradeOrder.updateOrderByOrderNumber", userUpgradeOrder);
    }
}

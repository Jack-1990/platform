package cn.seocoo.platform.service.userUpgradeOrder.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.UserUpgradeOrder;
import cn.seocoo.platform.service.userUpgradeOrder.inf.UserUpgradeOrderService;
import cn.seocoo.platform.dao.userUpgradeOrder.inf.UserUpgradeOrderDao;

public class UserUpgradeOrderServiceImpl  implements UserUpgradeOrderService{

	  private UserUpgradeOrderDao userUpgradeOrderDao;

    public UserUpgradeOrderDao getUserUpgradeOrder() {
        return userUpgradeOrderDao;
    }
    public void setUserUpgradeOrderDao(UserUpgradeOrderDao userUpgradeOrderDao) {
         this.userUpgradeOrderDao = userUpgradeOrderDao;
    }

    @Override
    public UserUpgradeOrder queryUserUpgradeOrder(UserUpgradeOrder userUpgradeOrder){
        return userUpgradeOrderDao.queryUserUpgradeOrder(userUpgradeOrder);
    }

    @Override
    public List<UserUpgradeOrder> queryUserUpgradeOrderList(UserUpgradeOrder userUpgradeOrder){
        return userUpgradeOrderDao.queryUserUpgradeOrderList(userUpgradeOrder);
    }
    @Override
    public void saveUserUpgradeOrder(UserUpgradeOrder userUpgradeOrder){
          userUpgradeOrderDao.saveUserUpgradeOrder(userUpgradeOrder);
    }
    @Override
    public void updateUserUpgradeOrder(UserUpgradeOrder userUpgradeOrder){
        userUpgradeOrderDao.updateUserUpgradeOrder(userUpgradeOrder);
    }
    @Override
    public void deleteUserUpgradeOrder(UserUpgradeOrder userUpgradeOrder){
        userUpgradeOrderDao.deleteUserUpgradeOrder(userUpgradeOrder);
    }
    @Override
    public List<UserUpgradeOrder> queryUserUpgradeOrderPage(Map map){
        return userUpgradeOrderDao.queryUserUpgradeOrderPage(map);
    }
    @Override
    public Integer queryUserUpgradeOrderPageCount(Map map){
        return userUpgradeOrderDao.queryUserUpgradeOrderPageCount(map);
    }
    @Override
    public void updateOrderByOrderNumber(UserUpgradeOrder userUpgradeOrder){
    	 userUpgradeOrderDao.updateOrderByOrderNumber(userUpgradeOrder);
    }
}

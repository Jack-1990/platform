package cn.seocoo.platform.dao.userUpgradeOrder.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.UserUpgradeOrder;

public interface UserUpgradeOrderDao {

    public UserUpgradeOrder queryUserUpgradeOrder(UserUpgradeOrder userUpgradeOrder);

    public List<UserUpgradeOrder> queryUserUpgradeOrderList(UserUpgradeOrder userUpgradeOrder);

    public void saveUserUpgradeOrder(UserUpgradeOrder userUpgradeOrder);

    public void updateUserUpgradeOrder(UserUpgradeOrder userUpgradeOrder);

    public void deleteUserUpgradeOrder(UserUpgradeOrder userUpgradeOrder);

    public List<UserUpgradeOrder> queryUserUpgradeOrderPage(Map map);

    public Integer queryUserUpgradeOrderPageCount(Map map);
    
    public void updateOrderByOrderNumber(UserUpgradeOrder userUpgradeOrder);
}

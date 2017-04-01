package cn.seocoo.platform.dao.groupMerchantPay.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.GroupMerchantPay;

public interface GroupMerchantPayDao {

    public GroupMerchantPay queryGroupMerchantPay(GroupMerchantPay groupMerchantPay);

    public List<GroupMerchantPay> queryGroupMerchantPayList(GroupMerchantPay groupMerchantPay);

    public void saveGroupMerchantPay(GroupMerchantPay groupMerchantPay);

    public void updateGroupMerchantPay(GroupMerchantPay groupMerchantPay);

    public void deleteGroupMerchantPay(GroupMerchantPay groupMerchantPay);

    public List<GroupMerchantPay> queryGroupMerchantPayPage(Map map);

    public Integer queryGroupMerchantPayPageCount(Map map);

	public void updateByMerchantCode(GroupMerchantPay groupMerchantPay);
}

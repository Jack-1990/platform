package cn.seocoo.platform.service.merchantUser.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.MerchantUser;

public interface MerchantUserService {

    public MerchantUser queryMerchantUser(MerchantUser merchantUser);

    public List<MerchantUser> queryMerchantUserList(MerchantUser merchantUser);

    public void saveMerchantUser(MerchantUser merchantUser);

    public void updateMerchantUser(MerchantUser merchantUser);

    public void deleteMerchantUser(MerchantUser merchantUser);

    public List<MerchantUser> queryMerchantUserPage(Map map);

    public Integer queryMerchantUserPageCount(Map map);

	public void updateDeviceToken(MerchantUser merchantUser);

	public void updateTokenByLoginName(MerchantUser user);
}

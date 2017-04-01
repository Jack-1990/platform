package cn.seocoo.platform.service.userUpgrade.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.UserUpgrade;

public interface UserUpgradeService {

    public UserUpgrade queryUserUpgrade(UserUpgrade userUpgrade);

    public List<UserUpgrade> queryUserUpgradeList(UserUpgrade userUpgrade);

    public void saveUserUpgrade(UserUpgrade userUpgrade);

    public void updateUserUpgrade(UserUpgrade userUpgrade);

    public void deleteUserUpgrade(UserUpgrade userUpgrade);

    public List<UserUpgrade> queryUserUpgradePage(Map map);

    public Integer queryUserUpgradePageCount(Map map);
    
    public List<UserUpgrade> queryUserUpgradeInfos(UserUpgrade userUpgrade);
 
	public List<UserUpgrade> queryIsnotUpgrade(UserUpgrade userUpgrade);

	public List<UserUpgrade> queryListMust(UserUpgrade userUpgrade);
 
}

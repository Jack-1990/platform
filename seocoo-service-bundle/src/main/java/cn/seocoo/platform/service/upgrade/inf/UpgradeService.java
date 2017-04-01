package cn.seocoo.platform.service.upgrade.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Upgrade;

public interface UpgradeService {

    public Upgrade queryUpgrade(Upgrade upgrade);

    public List<Upgrade> queryUpgradeList(Upgrade upgrade);

    public void saveUpgrade(Upgrade upgrade);

    public void updateUpgrade(Upgrade upgrade);

    public void deleteUpgrade(Upgrade upgrade);

    public List<Upgrade> queryUpgradePage(Map map);

    public Integer queryUpgradePageCount(Map map);
}

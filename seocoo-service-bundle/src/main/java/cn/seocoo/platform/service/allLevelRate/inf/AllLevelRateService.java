package cn.seocoo.platform.service.allLevelRate.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.AllLevelRate;

public interface AllLevelRateService {

    public AllLevelRate queryAllLevelRate(AllLevelRate allLevelRate);

    public List<AllLevelRate> queryAllLevelRateList(AllLevelRate allLevelRate);

    public void saveAllLevelRate(AllLevelRate allLevelRate);

    public void updateAllLevelRate(AllLevelRate allLevelRate);

    public void deleteAllLevelRate(AllLevelRate allLevelRate);

    public List<AllLevelRate> queryAllLevelRatePage(Map map);

    public Integer queryAllLevelRatePageCount(Map map);

	public List<AllLevelRate> queryLevelList(AllLevelRate allLevelRate);
}

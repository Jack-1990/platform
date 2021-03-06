package cn.seocoo.platform.service.rateSku.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.RateSku;

public interface RateSkuService {

    public RateSku queryRateSku(RateSku rateSku);

    public List<RateSku> queryRateSkuList(RateSku rateSku);

    public void saveRateSku(RateSku rateSku);

    public void updateRateSku(RateSku rateSku);

    public void deleteRateSku(RateSku rateSku);

    public List<RateSku> queryRateSkuPage(Map map);

    public Integer queryRateSkuPageCount(Map map);
}

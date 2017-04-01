package cn.seocoo.platform.service.rateDim.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.RateDim;
import cn.seocoo.platform.model.RateDimAttr;
import cn.seocoo.platform.model.RateSku;

public interface RateDimService {

    public RateDim queryRateDim(RateDim rateDim);

    public List<RateDim> queryRateDimList(RateDim rateDim);

    public void saveRateDim(RateDim rateDim);

    public void updateRateDim(RateDim rateDim);

    public void deleteRateDim(RateDim rateDim);

    public List<RateDim> queryRateDimPage(Map map);

    public Integer queryRateDimPageCount(Map map);

	public void batchInsertRate(List<RateDim> rateDim, List<RateDimAttr> rateDimAttr, List<RateSku> rateSku);
}

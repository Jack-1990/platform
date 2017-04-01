package cn.seocoo.platform.dao.rateDim.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.RateDim;

public interface RateDimDao {

    public RateDim queryRateDim(RateDim rateDim);

    public List<RateDim> queryRateDimList(RateDim rateDim);

    public void saveRateDim(RateDim rateDim);

    public void updateRateDim(RateDim rateDim);

    public void deleteRateDim(RateDim rateDim);

    public List<RateDim> queryRateDimPage(Map map);

    public Integer queryRateDimPageCount(Map map);

	public void batchInsertRateDim(List<RateDim> rateDim);

}

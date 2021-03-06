package cn.seocoo.platform.dao.rateDimAttr.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.RateDimAttr;

public interface RateDimAttrDao {

    public RateDimAttr queryRateDimAttr(RateDimAttr rateDimAttr);

    public List<RateDimAttr> queryRateDimAttrList(RateDimAttr rateDimAttr);

    public void saveRateDimAttr(RateDimAttr rateDimAttr);

    public void updateRateDimAttr(RateDimAttr rateDimAttr);

    public void deleteRateDimAttr(RateDimAttr rateDimAttr);

    public List<RateDimAttr> queryRateDimAttrPage(Map map);

    public Integer queryRateDimAttrPageCount(Map map);

	public void batchInsertRateDimAttr(List<RateDimAttr> rateDimAttr);
	
	
	public List<RateDimAttr> queryRateDimAttrWithLastLevel(RateDimAttr rateDimAttr);
}

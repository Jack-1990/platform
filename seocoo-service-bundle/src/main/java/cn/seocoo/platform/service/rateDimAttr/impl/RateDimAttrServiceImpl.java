package cn.seocoo.platform.service.rateDimAttr.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.rateDimAttr.inf.RateDimAttrDao;
import cn.seocoo.platform.model.RateDimAttr;
import cn.seocoo.platform.service.rateDimAttr.inf.RateDimAttrService;

public class RateDimAttrServiceImpl  implements RateDimAttrService{

	  private RateDimAttrDao rateDimAttrDao;

    public RateDimAttrDao getRateDimAttr() {
        return rateDimAttrDao;
    }
    public void setRateDimAttrDao(RateDimAttrDao rateDimAttrDao) {
         this.rateDimAttrDao = rateDimAttrDao;
    }

    @Override
    public RateDimAttr queryRateDimAttr(RateDimAttr rateDimAttr){
        return rateDimAttrDao.queryRateDimAttr(rateDimAttr);
    }

    @Override
    public List<RateDimAttr> queryRateDimAttrList(RateDimAttr rateDimAttr){
        return rateDimAttrDao.queryRateDimAttrList(rateDimAttr);
    }
    @Override
    public void saveRateDimAttr(RateDimAttr rateDimAttr){
          rateDimAttrDao.saveRateDimAttr(rateDimAttr);
    }
    @Override
    public void updateRateDimAttr(RateDimAttr rateDimAttr){
        rateDimAttrDao.updateRateDimAttr(rateDimAttr);
    }
    @Override
    public void deleteRateDimAttr(RateDimAttr rateDimAttr){
        rateDimAttrDao.deleteRateDimAttr(rateDimAttr);
    }
    @Override
    public List<RateDimAttr> queryRateDimAttrPage(Map map){
        return rateDimAttrDao.queryRateDimAttrPage(map);
    }
    @Override
    public Integer queryRateDimAttrPageCount(Map map){
        return rateDimAttrDao.queryRateDimAttrPageCount(map);
    }
	@Override
	public List<RateDimAttr> queryRateDimAttrWithLastLevel(RateDimAttr rateDimAttr)
	{
		// TODO Auto-generated method stub
		return rateDimAttrDao.queryRateDimAttrWithLastLevel(rateDimAttr);
	}
}

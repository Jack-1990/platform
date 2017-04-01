package cn.seocoo.platform.service.dimDic.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.DimDic;
import cn.seocoo.platform.service.dimDic.inf.DimDicService;
import cn.seocoo.platform.dao.dimDic.inf.DimDicDao;

public class DimDicServiceImpl  implements DimDicService{

	  private DimDicDao dimDicDao;

    public DimDicDao getDimDic() {
        return dimDicDao;
    }
    public void setDimDicDao(DimDicDao dimDicDao) {
         this.dimDicDao = dimDicDao;
    }

    @Override
    public DimDic queryDimDic(DimDic dimDic){
        return dimDicDao.queryDimDic(dimDic);
    }

    @Override
    public List<DimDic> queryDimDicList(DimDic dimDic){
        return dimDicDao.queryDimDicList(dimDic);
    }
    @Override
    public void saveDimDic(DimDic dimDic){
          dimDicDao.saveDimDic(dimDic);
    }
    @Override
    public void updateDimDic(DimDic dimDic){
        dimDicDao.updateDimDic(dimDic);
    }
    @Override
    public void deleteDimDic(DimDic dimDic){
        dimDicDao.deleteDimDic(dimDic);
    }
    @Override
    public List<DimDic> queryDimDicPage(Map map){
        return dimDicDao.queryDimDicPage(map);
    }
    @Override
    public Integer queryDimDicPageCount(Map map){
        return dimDicDao.queryDimDicPageCount(map);
    }
}

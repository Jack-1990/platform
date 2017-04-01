package cn.seocoo.platform.service.dimDicInfo.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.DimDicInfo;
import cn.seocoo.platform.service.dimDicInfo.inf.DimDicInfoService;
import cn.seocoo.platform.dao.dimDicInfo.inf.DimDicInfoDao;

public class DimDicInfoServiceImpl  implements DimDicInfoService{

	  private DimDicInfoDao dimDicInfoDao;

    public DimDicInfoDao getDimDicInfo() {
        return dimDicInfoDao;
    }
    public void setDimDicInfoDao(DimDicInfoDao dimDicInfoDao) {
         this.dimDicInfoDao = dimDicInfoDao;
    }

    @Override
    public DimDicInfo queryDimDicInfo(DimDicInfo dimDicInfo){
        return dimDicInfoDao.queryDimDicInfo(dimDicInfo);
    }

    @Override
    public List<DimDicInfo> queryDimDicInfoList(DimDicInfo dimDicInfo){
        return dimDicInfoDao.queryDimDicInfoList(dimDicInfo);
    }
    @Override
    public void saveDimDicInfo(DimDicInfo dimDicInfo){
          dimDicInfoDao.saveDimDicInfo(dimDicInfo);
    }
    @Override
    public void updateDimDicInfo(DimDicInfo dimDicInfo){
        dimDicInfoDao.updateDimDicInfo(dimDicInfo);
    }
    @Override
    public void deleteDimDicInfo(DimDicInfo dimDicInfo){
        dimDicInfoDao.deleteDimDicInfo(dimDicInfo);
    }
    @Override
    public List<DimDicInfo> queryDimDicInfoPage(Map map){
        return dimDicInfoDao.queryDimDicInfoPage(map);
    }
    @Override
    public Integer queryDimDicInfoPageCount(Map map){
        return dimDicInfoDao.queryDimDicInfoPageCount(map);
    }
    
    public List<DimDicInfo> queryDimDicAttrValue(DimDicInfo dimDicInfo){
    	 return dimDicInfoDao.queryDimDicAttrValue(dimDicInfo);
    }
}

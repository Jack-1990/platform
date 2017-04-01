package cn.seocoo.platform.dao.dimDicInfo.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.DimDicInfo;
import cn.seocoo.platform.dao.dimDicInfo.inf.DimDicInfoDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class DimDicInfoDaoImpl extends EntityManagerImpl<DimDicInfo, Integer> implements DimDicInfoDao{

    @Override
    public DimDicInfo queryDimDicInfo(DimDicInfo dimDicInfo){
        return entityDao.findObj("DimDicInfo.queryDimDicInfo", dimDicInfo);
    }

    @Override
    public List<DimDicInfo> queryDimDicInfoList(DimDicInfo dimDicInfo){
        return entityDao.find("DimDicInfo.queryDimDicInfo", dimDicInfo);
    }
    @Override
    public void saveDimDicInfo(DimDicInfo dimDicInfo){
         entityDao.save("DimDicInfo.saveDimDicInfo", dimDicInfo);
    }
    @Override
    public void updateDimDicInfo(DimDicInfo dimDicInfo){
         entityDao.update("DimDicInfo.updateDimDicInfo", dimDicInfo);
    }
    @Override
    public void deleteDimDicInfo(DimDicInfo dimDicInfo){
         entityDao.remove("DimDicInfo.deleteDimDicInfo", dimDicInfo);
    }
    @Override
    public List<DimDicInfo> queryDimDicInfoPage(Map map){
        return entityDao.find("DimDicInfo.queryDimDicInfoPage", map);
    }
    @Override
    public Integer queryDimDicInfoPageCount(Map map){
        return (Integer) entityDao.find("DimDicInfo.queryDimDicInfoPageCount", map).get(0);
    }
    
    public List<DimDicInfo> queryDimDicAttrValue(DimDicInfo dimDicInfo){
        return entityDao.find("DimDicInfo.queryDimDicAttrValue", dimDicInfo);
    }
}

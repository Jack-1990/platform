package cn.seocoo.platform.dao.dimDic.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.DimDic;
import cn.seocoo.platform.dao.dimDic.inf.DimDicDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class DimDicDaoImpl extends EntityManagerImpl<DimDic, Integer> implements DimDicDao{

    @Override
    public DimDic queryDimDic(DimDic dimDic){
        return entityDao.findObj("DimDic.queryDimDic", dimDic);
    }

    @Override
    public List<DimDic> queryDimDicList(DimDic dimDic){
        return entityDao.find("DimDic.queryDimDic", dimDic);
    }
    @Override
    public void saveDimDic(DimDic dimDic){
         entityDao.save("DimDic.saveDimDic", dimDic);
    }
    @Override
    public void updateDimDic(DimDic dimDic){
         entityDao.update("DimDic.updateDimDic", dimDic);
    }
    @Override
    public void deleteDimDic(DimDic dimDic){
         entityDao.remove("DimDic.deleteDimDic", dimDic);
    }
    @Override
    public List<DimDic> queryDimDicPage(Map map){
        return entityDao.find("DimDic.queryDimDicPage", map);
    }
    @Override
    public Integer queryDimDicPageCount(Map map){
        return (Integer) entityDao.find("DimDic.queryDimDicPageCount", map).get(0);
    }
}

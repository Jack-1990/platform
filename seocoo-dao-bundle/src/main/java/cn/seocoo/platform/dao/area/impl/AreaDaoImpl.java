package cn.seocoo.platform.dao.area.impl;


import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.area.inf.AreaDao;
import cn.seocoo.platform.model.Area;
import cn.seocoo.platform.model.AreaInfo;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class AreaDaoImpl extends EntityManagerImpl<Area, Integer> implements AreaDao{

    @Override
    public Area queryArea(Area area){
        return entityDao.findObj("Area.queryArea", area);
    }

    @Override
    public List<Area> queryAreaList(Area area){
        return entityDao.find("Area.queryArea", area);
    }
    
    @Override
    public void saveArea(Area area){
         entityDao.save("Area.saveArea", area);
    }
    @Override
    public void updateArea(Area area){
         entityDao.update("Area.updateArea", area);
    }
    @Override
    public void deleteArea(Area area){
         entityDao.remove("Area.deleteArea", area);
    }
    @Override
    public List<Area> queryAreaPage(Map map){
        return entityDao.find("Area.queryAreaPage", map);
    }
    @Override
    public Integer queryAreaPageCount(Map map){
        return (Integer) entityDao.find("Area.queryAreaPageCount", map).get(0);
    }
    
    @Override
    public List<Area> queryAreaListByPcode(Area area){
        return entityDao.find("Area.queryAreaByPcode", area);
    }
    
	public List<Area> queryMergerName(Area area){
		return entityDao.find("Area.queryMergerName", area);
	}
	
	public List<AreaInfo> queryAreaInfoList(Area area){
		return entityDao.find("Area.queryAreaInfoList", area);
	}

    
}

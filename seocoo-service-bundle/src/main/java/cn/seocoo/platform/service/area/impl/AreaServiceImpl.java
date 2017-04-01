package cn.seocoo.platform.service.area.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.area.inf.AreaDao;
import cn.seocoo.platform.model.Area;
import cn.seocoo.platform.model.AreaInfo;
import cn.seocoo.platform.service.area.inf.AreaService;

public class AreaServiceImpl  implements AreaService{

	  private AreaDao areaDao;

    public AreaDao getArea() {
        return areaDao;
    }
    public void setAreaDao(AreaDao areaDao) {
         this.areaDao = areaDao;
    }

    @Override
    public Area queryArea(Area area){
        return areaDao.queryArea(area);
    }

    @Override
    public List<Area> queryAreaList(Area area){
        return areaDao.queryAreaList(area);
    }
    @Override
    public void saveArea(Area area){
          areaDao.saveArea(area);
    }
    @Override
    public void updateArea(Area area){
        areaDao.updateArea(area);
    }
    @Override
    public void deleteArea(Area area){
        areaDao.deleteArea(area);
    }
    @Override
    public List<Area> queryAreaPage(Map map){
        return areaDao.queryAreaPage(map);
    }
    @Override
    public Integer queryAreaPageCount(Map map){
        return areaDao.queryAreaPageCount(map);
    }
	public AreaDao getAreaDao()
	{
		return areaDao;
	}
    
    public List<Area> queryAreaListByPcode(Area area)
	{
		return areaDao.queryAreaListByPcode(area);  
	}
    
    
	public List<Area> queryMergerName(Area area){
		return areaDao.queryMergerName(area);
	}
	
    public List<AreaInfo> queryAreaInfoList(Area area){
        return areaDao.queryAreaInfoList(area);
    }

}

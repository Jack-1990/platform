package cn.seocoo.platform.service.area.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.Area;
import cn.seocoo.platform.model.AreaInfo;

public interface AreaService {

    public Area queryArea(Area area);

    public List<Area> queryAreaList(Area area);

    public void saveArea(Area area);

    public void updateArea(Area area);

    public void deleteArea(Area area);

    public List<Area> queryAreaPage(Map map);

    public Integer queryAreaPageCount(Map map);
    
    public List<Area> queryAreaListByPcode(Area area);

	public List<Area> queryMergerName(Area area);

	
	public List<AreaInfo> queryAreaInfoList(Area area);
}

package cn.seocoo.platform.service.dimDicInfo.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.DimDicInfo;

public interface DimDicInfoService {

    public DimDicInfo queryDimDicInfo(DimDicInfo dimDicInfo);

    public List<DimDicInfo> queryDimDicInfoList(DimDicInfo dimDicInfo);

    public void saveDimDicInfo(DimDicInfo dimDicInfo);

    public void updateDimDicInfo(DimDicInfo dimDicInfo);

    public void deleteDimDicInfo(DimDicInfo dimDicInfo);

    public List<DimDicInfo> queryDimDicInfoPage(Map map);

    public Integer queryDimDicInfoPageCount(Map map);

	public List<DimDicInfo> queryDimDicAttrValue(DimDicInfo dimDicInfo);
}

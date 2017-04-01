package cn.seocoo.platform.dao.dimDic.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.DimDic;

public interface DimDicDao {

    public DimDic queryDimDic(DimDic dimDic);

    public List<DimDic> queryDimDicList(DimDic dimDic);

    public void saveDimDic(DimDic dimDic);

    public void updateDimDic(DimDic dimDic);

    public void deleteDimDic(DimDic dimDic);

    public List<DimDic> queryDimDicPage(Map map);

    public Integer queryDimDicPageCount(Map map);
}

package cn.seocoo.platform.service.wapPage.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.WapPage;

public interface WapPageService {

    public WapPage queryWapPage(WapPage wapPage);

    public List<WapPage> queryWapPageList(WapPage wapPage);

    public void saveWapPage(WapPage wapPage);

    public void updateWapPage(WapPage wapPage);

    public void deleteWapPage(WapPage wapPage);

    public List<WapPage> queryWapPagePage(Map map);

    public Integer queryWapPagePageCount(Map map);
}

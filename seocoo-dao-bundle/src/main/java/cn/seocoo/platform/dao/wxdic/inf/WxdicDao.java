package cn.seocoo.platform.dao.wxdic.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Wxdic;

public interface WxdicDao {

    public Wxdic queryWxdic(Wxdic wxdic);

    public List<Wxdic> queryWxdicList(Wxdic wxdic);

    public void saveWxdic(Wxdic wxdic);

    public void updateWxdic(Wxdic wxdic);

    public void deleteWxdic(Wxdic wxdic);

    public List<Wxdic> queryWxdicPage(Map map);

    public Integer queryWxdicPageCount(Map map);
}

package cn.seocoo.platform.dao.zfbdic.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Zfbdic;

public interface ZfbdicDao {

    public Zfbdic queryZfbdic(Zfbdic zfbdic);

    public List<Zfbdic> queryZfbdicList(Zfbdic zfbdic);

    public void saveZfbdic(Zfbdic zfbdic);

    public void updateZfbdic(Zfbdic zfbdic);

    public void deleteZfbdic(Zfbdic zfbdic);

    public List<Zfbdic> queryZfbdicPage(Map map);

    public Integer queryZfbdicPageCount(Map map);
}

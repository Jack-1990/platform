package cn.seocoo.platform.dao.qqdic.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Qqdic;

public interface QqdicDao {

    public Qqdic queryQqdic(Qqdic qqdic);

    public List<Qqdic> queryQqdicList(Qqdic qqdic);

    public void saveQqdic(Qqdic qqdic);

    public void updateQqdic(Qqdic qqdic);

    public void deleteQqdic(Qqdic qqdic);

    public List<Qqdic> queryQqdicPage(Map map);

    public Integer queryQqdicPageCount(Map map);
}

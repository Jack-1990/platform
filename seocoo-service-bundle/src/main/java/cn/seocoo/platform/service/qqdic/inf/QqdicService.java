package cn.seocoo.platform.service.qqdic.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Qqdic;

public interface QqdicService {

    public Qqdic queryQqdic(Qqdic qqdic);

    public List<Qqdic> queryQqdicList(Qqdic qqdic);

    public void saveQqdic(Qqdic qqdic);

    public void updateQqdic(Qqdic qqdic);

    public void deleteQqdic(Qqdic qqdic);

    public List<Qqdic> queryQqdicPage(Map map);

    public Integer queryQqdicPageCount(Map map);
}

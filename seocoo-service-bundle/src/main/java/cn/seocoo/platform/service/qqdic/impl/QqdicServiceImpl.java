package cn.seocoo.platform.service.qqdic.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Qqdic;
import cn.seocoo.platform.service.qqdic.inf.QqdicService;
import cn.seocoo.platform.dao.qqdic.inf.QqdicDao;

public class QqdicServiceImpl  implements QqdicService{

	  private QqdicDao qqdicDao;

    public QqdicDao getQqdic() {
        return qqdicDao;
    }
    public void setQqdicDao(QqdicDao qqdicDao) {
         this.qqdicDao = qqdicDao;
    }

    @Override
    public Qqdic queryQqdic(Qqdic qqdic){
        return qqdicDao.queryQqdic(qqdic);
    }

    @Override
    public List<Qqdic> queryQqdicList(Qqdic qqdic){
        return qqdicDao.queryQqdicList(qqdic);
    }
    @Override
    public void saveQqdic(Qqdic qqdic){
          qqdicDao.saveQqdic(qqdic);
    }
    @Override
    public void updateQqdic(Qqdic qqdic){
        qqdicDao.updateQqdic(qqdic);
    }
    @Override
    public void deleteQqdic(Qqdic qqdic){
        qqdicDao.deleteQqdic(qqdic);
    }
    @Override
    public List<Qqdic> queryQqdicPage(Map map){
        return qqdicDao.queryQqdicPage(map);
    }
    @Override
    public Integer queryQqdicPageCount(Map map){
        return qqdicDao.queryQqdicPageCount(map);
    }
}

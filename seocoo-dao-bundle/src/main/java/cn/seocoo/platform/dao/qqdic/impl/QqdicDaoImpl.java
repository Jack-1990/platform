package cn.seocoo.platform.dao.qqdic.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Qqdic;
import cn.seocoo.platform.dao.qqdic.inf.QqdicDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class QqdicDaoImpl extends EntityManagerImpl<Qqdic, Integer> implements QqdicDao{

    @Override
    public Qqdic queryQqdic(Qqdic qqdic){
        return entityDao.findObj("Qqdic.queryQqdic", qqdic);
    }

    @Override
    public List<Qqdic> queryQqdicList(Qqdic qqdic){
        return entityDao.find("Qqdic.queryQqdic", qqdic);
    }
    @Override
    public void saveQqdic(Qqdic qqdic){
         entityDao.save("Qqdic.saveQqdic", qqdic);
    }
    @Override
    public void updateQqdic(Qqdic qqdic){
         entityDao.update("Qqdic.updateQqdic", qqdic);
    }
    @Override
    public void deleteQqdic(Qqdic qqdic){
         entityDao.remove("Qqdic.deleteQqdic", qqdic);
    }
    @Override
    public List<Qqdic> queryQqdicPage(Map map){
        return entityDao.find("Qqdic.queryQqdicPage", map);
    }
    @Override
    public Integer queryQqdicPageCount(Map map){
        return (Integer) entityDao.find("Qqdic.queryQqdicPageCount", map).get(0);
    }
}

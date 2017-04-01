package cn.seocoo.platform.dao.zfbdic.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Zfbdic;
import cn.seocoo.platform.dao.zfbdic.inf.ZfbdicDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class ZfbdicDaoImpl extends EntityManagerImpl<Zfbdic, Integer> implements ZfbdicDao{

    @Override
    public Zfbdic queryZfbdic(Zfbdic zfbdic){
        return entityDao.findObj("Zfbdic.queryZfbdic", zfbdic);
    }

    @Override
    public List<Zfbdic> queryZfbdicList(Zfbdic zfbdic){
        return entityDao.find("Zfbdic.queryZfbdic", zfbdic);
    }
    @Override
    public void saveZfbdic(Zfbdic zfbdic){
         entityDao.save("Zfbdic.saveZfbdic", zfbdic);
    }
    @Override
    public void updateZfbdic(Zfbdic zfbdic){
         entityDao.update("Zfbdic.updateZfbdic", zfbdic);
    }
    @Override
    public void deleteZfbdic(Zfbdic zfbdic){
         entityDao.remove("Zfbdic.deleteZfbdic", zfbdic);
    }
    @Override
    public List<Zfbdic> queryZfbdicPage(Map map){
        return entityDao.find("Zfbdic.queryZfbdicPage", map);
    }
    @Override
    public Integer queryZfbdicPageCount(Map map){
        return (Integer) entityDao.find("Zfbdic.queryZfbdicPageCount", map).get(0);
    }
}

package cn.seocoo.platform.dao.wxdic.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Wxdic;
import cn.seocoo.platform.dao.wxdic.inf.WxdicDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class WxdicDaoImpl extends EntityManagerImpl<Wxdic, Integer> implements WxdicDao{

    @Override
    public Wxdic queryWxdic(Wxdic wxdic){
        return entityDao.findObj("Wxdic.queryWxdic", wxdic);
    }

    @Override
    public List<Wxdic> queryWxdicList(Wxdic wxdic){
        return entityDao.find("Wxdic.queryWxdic", wxdic);
    }
    @Override
    public void saveWxdic(Wxdic wxdic){
         entityDao.save("Wxdic.saveWxdic", wxdic);
    }
    @Override
    public void updateWxdic(Wxdic wxdic){
         entityDao.update("Wxdic.updateWxdic", wxdic);
    }
    @Override
    public void deleteWxdic(Wxdic wxdic){
         entityDao.remove("Wxdic.deleteWxdic", wxdic);
    }
    @Override
    public List<Wxdic> queryWxdicPage(Map map){
        return entityDao.find("Wxdic.queryWxdicPage", map);
    }
    @Override
    public Integer queryWxdicPageCount(Map map){
        return (Integer) entityDao.find("Wxdic.queryWxdicPageCount", map).get(0);
    }
}

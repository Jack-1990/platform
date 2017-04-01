package cn.seocoo.platform.dao.wapPage.impl;


import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.wapPage.inf.WapPageDao;
import cn.seocoo.platform.model.WapPage;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class WapPageDaoImpl extends EntityManagerImpl<WapPage, Integer> implements WapPageDao{

    @Override
    public WapPage queryWapPage(WapPage wapPage){
        return entityDao.findObj("WapPage.queryWapPage", wapPage);
    }

    @Override
    public List<WapPage> queryWapPageList(WapPage wapPage){
        return entityDao.find("WapPage.queryWapPage", wapPage);
    }
    @Override
    public void saveWapPage(WapPage wapPage){
         entityDao.save("WapPage.saveWapPage", wapPage);
    }
    @Override
    public void updateWapPage(WapPage wapPage){
         entityDao.update("WapPage.updateWapPage", wapPage);
    }
    @Override
    public void deleteWapPage(WapPage wapPage){
         entityDao.remove("WapPage.deleteWapPage", wapPage);
    }
    @Override
    public List<WapPage> queryWapPagePage(Map map){
        return entityDao.find("WapPage.queryWapPagePage", map);
    }
    @Override
    public Integer queryWapPagePageCount(Map map){
        return (Integer) entityDao.find("WapPage.queryWapPagePageCount", map).get(0);
    }
}

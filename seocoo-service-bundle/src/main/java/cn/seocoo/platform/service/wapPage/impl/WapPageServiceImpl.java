package cn.seocoo.platform.service.wapPage.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.wapPage.inf.WapPageDao;
import cn.seocoo.platform.model.WapPage;
import cn.seocoo.platform.service.wapPage.inf.WapPageService;

public class WapPageServiceImpl  implements WapPageService{

	  private WapPageDao wapPageDao;

    public WapPageDao getWapPage() {
        return wapPageDao;
    }
    public void setWapPageDao(WapPageDao wapPageDao) {
         this.wapPageDao = wapPageDao;
    }

    @Override
    public WapPage queryWapPage(WapPage wapPage){
        return wapPageDao.queryWapPage(wapPage);
    }

    @Override
    public List<WapPage> queryWapPageList(WapPage wapPage){
        return wapPageDao.queryWapPageList(wapPage);
    }
    @Override
    public void saveWapPage(WapPage wapPage){
          wapPageDao.saveWapPage(wapPage);
    }
    @Override
    public void updateWapPage(WapPage wapPage){
        wapPageDao.updateWapPage(wapPage);
    }
    @Override
    public void deleteWapPage(WapPage wapPage){
        wapPageDao.deleteWapPage(wapPage);
    }
    @Override
    public List<WapPage> queryWapPagePage(Map map){
        return wapPageDao.queryWapPagePage(map);
    }
    @Override
    public Integer queryWapPagePageCount(Map map){
        return wapPageDao.queryWapPagePageCount(map);
    }
}

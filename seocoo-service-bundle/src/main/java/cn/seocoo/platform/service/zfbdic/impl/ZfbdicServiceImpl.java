package cn.seocoo.platform.service.zfbdic.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Zfbdic;
import cn.seocoo.platform.service.zfbdic.inf.ZfbdicService;
import cn.seocoo.platform.dao.zfbdic.inf.ZfbdicDao;

public class ZfbdicServiceImpl  implements ZfbdicService{

	  private ZfbdicDao zfbdicDao;

    public ZfbdicDao getZfbdic() {
        return zfbdicDao;
    }
    public void setZfbdicDao(ZfbdicDao zfbdicDao) {
         this.zfbdicDao = zfbdicDao;
    }

    @Override
    public Zfbdic queryZfbdic(Zfbdic zfbdic){
        return zfbdicDao.queryZfbdic(zfbdic);
    }

    @Override
    public List<Zfbdic> queryZfbdicList(Zfbdic zfbdic){
        return zfbdicDao.queryZfbdicList(zfbdic);
    }
    @Override
    public void saveZfbdic(Zfbdic zfbdic){
          zfbdicDao.saveZfbdic(zfbdic);
    }
    @Override
    public void updateZfbdic(Zfbdic zfbdic){
        zfbdicDao.updateZfbdic(zfbdic);
    }
    @Override
    public void deleteZfbdic(Zfbdic zfbdic){
        zfbdicDao.deleteZfbdic(zfbdic);
    }
    @Override
    public List<Zfbdic> queryZfbdicPage(Map map){
        return zfbdicDao.queryZfbdicPage(map);
    }
    @Override
    public Integer queryZfbdicPageCount(Map map){
        return zfbdicDao.queryZfbdicPageCount(map);
    }
}

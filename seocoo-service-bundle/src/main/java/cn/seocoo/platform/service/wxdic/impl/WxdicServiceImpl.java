package cn.seocoo.platform.service.wxdic.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.Wxdic;
import cn.seocoo.platform.service.wxdic.inf.WxdicService;
import cn.seocoo.platform.dao.wxdic.inf.WxdicDao;

public class WxdicServiceImpl  implements WxdicService{

	  private WxdicDao wxdicDao;

    public WxdicDao getWxdic() {
        return wxdicDao;
    }
    public void setWxdicDao(WxdicDao wxdicDao) {
         this.wxdicDao = wxdicDao;
    }

    @Override
    public Wxdic queryWxdic(Wxdic wxdic){
        return wxdicDao.queryWxdic(wxdic);
    }

    @Override
    public List<Wxdic> queryWxdicList(Wxdic wxdic){
        return wxdicDao.queryWxdicList(wxdic);
    }
    @Override
    public void saveWxdic(Wxdic wxdic){
          wxdicDao.saveWxdic(wxdic);
    }
    @Override
    public void updateWxdic(Wxdic wxdic){
        wxdicDao.updateWxdic(wxdic);
    }
    @Override
    public void deleteWxdic(Wxdic wxdic){
        wxdicDao.deleteWxdic(wxdic);
    }
    @Override
    public List<Wxdic> queryWxdicPage(Map map){
        return wxdicDao.queryWxdicPage(map);
    }
    @Override
    public Integer queryWxdicPageCount(Map map){
        return wxdicDao.queryWxdicPageCount(map);
    }
}

package cn.seocoo.platform.service.adviceImage.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.adviceImage.inf.AdviceImageDao;
import cn.seocoo.platform.model.AdviceImage;
import cn.seocoo.platform.service.adviceImage.inf.AdviceImageService;

public class AdviceImageServiceImpl  implements AdviceImageService{

	  private AdviceImageDao adviceImageDao;

    public AdviceImageDao getAdviceImage() {
        return adviceImageDao;
    }
    public void setAdviceImageDao(AdviceImageDao adviceImageDao) {
         this.adviceImageDao = adviceImageDao;
    }

    @Override
    public AdviceImage queryAdviceImage(AdviceImage adviceImage){
        return adviceImageDao.queryAdviceImage(adviceImage);
    }

    @Override
    public List<AdviceImage> queryAdviceImageList(AdviceImage adviceImage){
        return adviceImageDao.queryAdviceImageList(adviceImage);
    }
    @Override
    public void saveAdviceImage(AdviceImage adviceImage){
          adviceImageDao.saveAdviceImage(adviceImage);
    }
    @Override
    public void updateAdviceImage(AdviceImage adviceImage){
        adviceImageDao.updateAdviceImage(adviceImage);
    }
    @Override
    public void deleteAdviceImage(AdviceImage adviceImage){
        adviceImageDao.deleteAdviceImage(adviceImage);
    }
    @Override
    public List<AdviceImage> queryAdviceImagePage(Map map){
        return adviceImageDao.queryAdviceImagePage(map);
    }
    @Override
    public Integer queryAdviceImagePageCount(Map map){
        return adviceImageDao.queryAdviceImagePageCount(map);
    }

	@Override
	public void batchInsertAdviceImage(List<AdviceImage> adviceImage)
	{
		adviceImageDao.batchInsertAdviceImage(adviceImage);
	}
}

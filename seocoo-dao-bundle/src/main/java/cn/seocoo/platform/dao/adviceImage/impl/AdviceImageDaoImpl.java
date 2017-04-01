package cn.seocoo.platform.dao.adviceImage.impl;

import java.util.List;
import java.util.Map;

import com.tydic.framework.base.dao.EntityManagerImpl;

import cn.seocoo.platform.dao.adviceImage.inf.AdviceImageDao;
import cn.seocoo.platform.model.AdviceImage;

public class AdviceImageDaoImpl extends EntityManagerImpl<AdviceImage, Integer> implements AdviceImageDao{

    @Override
    public AdviceImage queryAdviceImage(AdviceImage adviceImage){
        return entityDao.findObj("AdviceImage.queryAdviceImage", adviceImage);
    }

    @Override
    public List<AdviceImage> queryAdviceImageList(AdviceImage adviceImage){
        return entityDao.find("AdviceImage.queryAdviceImage", adviceImage);
    }
    @Override
    public void saveAdviceImage(AdviceImage adviceImage){
         entityDao.save("AdviceImage.saveAdviceImage", adviceImage);
    }
    @Override
    public void updateAdviceImage(AdviceImage adviceImage){
         entityDao.update("AdviceImage.updateAdviceImage", adviceImage);
    }
    @Override
    public void deleteAdviceImage(AdviceImage adviceImage){
         entityDao.remove("AdviceImage.deleteAdviceImage", adviceImage);
    }
    @Override
    public List<AdviceImage> queryAdviceImagePage(Map map){
        return entityDao.find("AdviceImage.queryAdviceImagePage", map);
    }
    @Override
    public Integer queryAdviceImagePageCount(Map map){
        return (Integer) entityDao.find("AdviceImage.queryAdviceImagePageCount", map).get(0);
    }

	@Override
	public void batchInsertAdviceImage(List<AdviceImage> adviceImage)
	{
		// TODO Auto-generated method stub
		entityDao.saveBatch("AdviceImage.batchInsertAdviceImage", adviceImage.toArray());
	}
}

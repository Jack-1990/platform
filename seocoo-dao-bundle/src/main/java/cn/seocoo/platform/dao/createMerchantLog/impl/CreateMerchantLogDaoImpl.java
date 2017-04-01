package cn.seocoo.platform.dao.createMerchantLog.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.CreateMerchantLog;
import cn.seocoo.platform.dao.createMerchantLog.inf.CreateMerchantLogDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class CreateMerchantLogDaoImpl extends EntityManagerImpl<CreateMerchantLog, Integer> implements CreateMerchantLogDao{

    @Override
    public CreateMerchantLog queryCreateMerchantLog(CreateMerchantLog createMerchantLog){
        return entityDao.findObj("CreateMerchantLog.queryCreateMerchantLog", createMerchantLog);
    }

    @Override
    public List<CreateMerchantLog> queryCreateMerchantLogList(CreateMerchantLog createMerchantLog){
        return entityDao.find("CreateMerchantLog.queryCreateMerchantLog", createMerchantLog);
    }
    @Override
    public void saveCreateMerchantLog(CreateMerchantLog createMerchantLog){
         entityDao.save("CreateMerchantLog.saveCreateMerchantLog", createMerchantLog);
    }
    @Override
    public void updateCreateMerchantLog(CreateMerchantLog createMerchantLog){
         entityDao.update("CreateMerchantLog.updateCreateMerchantLog", createMerchantLog);
    }
    @Override
    public void deleteCreateMerchantLog(CreateMerchantLog createMerchantLog){
         entityDao.remove("CreateMerchantLog.deleteCreateMerchantLog", createMerchantLog);
    }
    @Override
    public List<CreateMerchantLog> queryCreateMerchantLogPage(Map map){
        return entityDao.find("CreateMerchantLog.queryCreateMerchantLogPage", map);
    }
    @Override
    public Integer queryCreateMerchantLogPageCount(Map map){
        return (Integer) entityDao.find("CreateMerchantLog.queryCreateMerchantLogPageCount", map).get(0);
    }
}

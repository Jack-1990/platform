package cn.seocoo.platform.service.createMerchantLog.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.CreateMerchantLog;
import cn.seocoo.platform.service.createMerchantLog.inf.CreateMerchantLogService;
import cn.seocoo.platform.dao.createMerchantLog.inf.CreateMerchantLogDao;

public class CreateMerchantLogServiceImpl  implements CreateMerchantLogService{

	  private CreateMerchantLogDao createMerchantLogDao;

    public CreateMerchantLogDao getCreateMerchantLog() {
        return createMerchantLogDao;
    }
    public void setCreateMerchantLogDao(CreateMerchantLogDao createMerchantLogDao) {
         this.createMerchantLogDao = createMerchantLogDao;
    }

    @Override
    public CreateMerchantLog queryCreateMerchantLog(CreateMerchantLog createMerchantLog){
        return createMerchantLogDao.queryCreateMerchantLog(createMerchantLog);
    }

    @Override
    public List<CreateMerchantLog> queryCreateMerchantLogList(CreateMerchantLog createMerchantLog){
        return createMerchantLogDao.queryCreateMerchantLogList(createMerchantLog);
    }
    @Override
    public void saveCreateMerchantLog(CreateMerchantLog createMerchantLog){
          createMerchantLogDao.saveCreateMerchantLog(createMerchantLog);
    }
    @Override
    public void updateCreateMerchantLog(CreateMerchantLog createMerchantLog){
        createMerchantLogDao.updateCreateMerchantLog(createMerchantLog);
    }
    @Override
    public void deleteCreateMerchantLog(CreateMerchantLog createMerchantLog){
        createMerchantLogDao.deleteCreateMerchantLog(createMerchantLog);
    }
    @Override
    public List<CreateMerchantLog> queryCreateMerchantLogPage(Map map){
        return createMerchantLogDao.queryCreateMerchantLogPage(map);
    }
    @Override
    public Integer queryCreateMerchantLogPageCount(Map map){
        return createMerchantLogDao.queryCreateMerchantLogPageCount(map);
    }
}

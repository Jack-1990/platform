package cn.seocoo.platform.service.createMerchantLog.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.CreateMerchantLog;

public interface CreateMerchantLogService {

    public CreateMerchantLog queryCreateMerchantLog(CreateMerchantLog createMerchantLog);

    public List<CreateMerchantLog> queryCreateMerchantLogList(CreateMerchantLog createMerchantLog);

    public void saveCreateMerchantLog(CreateMerchantLog createMerchantLog);

    public void updateCreateMerchantLog(CreateMerchantLog createMerchantLog);

    public void deleteCreateMerchantLog(CreateMerchantLog createMerchantLog);

    public List<CreateMerchantLog> queryCreateMerchantLogPage(Map map);

    public Integer queryCreateMerchantLogPageCount(Map map);
}

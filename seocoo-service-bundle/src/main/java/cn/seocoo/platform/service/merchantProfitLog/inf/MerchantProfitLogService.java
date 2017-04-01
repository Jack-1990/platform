package cn.seocoo.platform.service.merchantProfitLog.inf;

import java.util.List;

import cn.seocoo.platform.model.MerchantProfitLog;

public interface MerchantProfitLogService {

    public MerchantProfitLog queryMerchantProfitLog(MerchantProfitLog merchantProfitLog);

    public List<MerchantProfitLog> queryMerchantProfitLogList(MerchantProfitLog merchantProfitLog);

    public void saveMerchantProfitLog(MerchantProfitLog merchantProfitLog);
 
}

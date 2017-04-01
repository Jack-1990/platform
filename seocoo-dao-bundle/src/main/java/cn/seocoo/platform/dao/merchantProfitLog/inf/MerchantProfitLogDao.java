package cn.seocoo.platform.dao.merchantProfitLog.inf;

import java.util.List;

import cn.seocoo.platform.model.MerchantProfitLog;

public interface MerchantProfitLogDao {

    public MerchantProfitLog queryMerchantProfitLog(MerchantProfitLog merchantProfitLog);

    public List<MerchantProfitLog> queryMerchantProfitLogList(MerchantProfitLog merchantProfitLog);

    public void saveMerchantProfitLog(MerchantProfitLog merchantProfitLog);
}

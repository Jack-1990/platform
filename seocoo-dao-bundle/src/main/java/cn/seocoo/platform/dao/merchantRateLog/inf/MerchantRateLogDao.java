package cn.seocoo.platform.dao.merchantRateLog.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.MerchantRateLog;

public interface MerchantRateLogDao {

    public MerchantRateLog queryMerchantRateLog(MerchantRateLog merchantRateLog);

    public List<MerchantRateLog> queryMerchantRateLogList(MerchantRateLog merchantRateLog);

    public void saveMerchantRateLog(MerchantRateLog merchantRateLog);

    public void updateMerchantRateLog(MerchantRateLog merchantRateLog);

    public void deleteMerchantRateLog(MerchantRateLog merchantRateLog);

    public List<MerchantRateLog> queryMerchantRateLogPage(Map map);

    public Integer queryMerchantRateLogPageCount(Map map);
}

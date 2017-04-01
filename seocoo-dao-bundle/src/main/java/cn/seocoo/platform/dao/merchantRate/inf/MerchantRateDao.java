package cn.seocoo.platform.dao.merchantRate.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.MerchantRate;

public interface MerchantRateDao {

    public MerchantRate queryMerchantRate(MerchantRate merchantRate);

    public List<MerchantRate> queryMerchantRateList(MerchantRate merchantRate);

    public void saveMerchantRate(MerchantRate merchantRate);

    public void updateMerchantRate(MerchantRate merchantRate);

    public void deleteMerchantRate(MerchantRate merchantRate);

    public List<MerchantRate> queryMerchantRatePage(Map map);

    public Integer queryMerchantRatePageCount(Map map);
    
	public void batchInsertMerchantRate(List<MerchantRate> merchantRate);
}

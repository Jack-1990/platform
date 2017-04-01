package cn.seocoo.platform.dao.merchantInfo.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.MerchantInfo;

public interface MerchantInfoDao {

    public MerchantInfo queryMerchantInfo(MerchantInfo merchantInfo);

    public List<MerchantInfo> queryMerchantInfoList(MerchantInfo merchantInfo);

    public void saveMerchantInfo(MerchantInfo merchantInfo);

    public void updateMerchantInfo(MerchantInfo merchantInfo);

    public void deleteMerchantInfo(MerchantInfo merchantInfo);

    public List<MerchantInfo> queryMerchantInfoPage(Map map);

    public Integer queryMerchantInfoPageCount(Map map);
    
    public List<MerchantInfo> queryProfitMerchantInfos(MerchantInfo merchantInfo);

	public List<MerchantInfo> queryMerchantDirectUserAndLevel(MerchantInfo merchantInfo);

	public List<MerchantInfo> queryMerchantUserPageWithMerchant(Map map);

	public List<MerchantInfo> queryMerchantInfoAndBankAndPic(MerchantInfo merchantInfo);

	public List<MerchantInfo> queryParentList(MerchantInfo merchantInfo);
	
	public void updateMerchantInfoProfit(MerchantInfo merchantInfo);
}

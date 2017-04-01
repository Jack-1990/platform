package cn.seocoo.platform.dao.merchant.inf;

import cn.seocoo.platform.model.Merchant;

import java.util.List;
import java.util.Map;

public interface MerchantDao {

    public Merchant queryMerchant(Merchant merchant);

    public List<Merchant> queryMerchantList(Merchant merchant);

    public void saveMerchant(Merchant merchant);

    public void updateMerchant(Merchant merchant);


    public void deleteMerchant(Merchant merchant);

    public List<Merchant> queryMerchantPage(Map map);

    public Integer queryMerchantPageCount(Map map);

	public void updateMerchantByOutMchntId(Merchant merchant);

	public List<Merchant> queryMerchantWithMerchantInfo(Map map);

    public Integer queryMerchantWithMerchantInfoCount(Map map);
}

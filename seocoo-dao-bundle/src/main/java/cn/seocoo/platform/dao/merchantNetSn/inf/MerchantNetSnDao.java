package cn.seocoo.platform.dao.merchantNetSn.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.MerchantNetSn;

public interface MerchantNetSnDao {

    public MerchantNetSn queryMerchantNetSn(MerchantNetSn merchantNetSn);

    public List<MerchantNetSn> queryMerchantNetSnList(MerchantNetSn merchantNetSn);

    public void saveMerchantNetSn(MerchantNetSn merchantNetSn);

    public void updateMerchantNetSn(MerchantNetSn merchantNetSn);

    public void deleteMerchantNetSn(MerchantNetSn merchantNetSn);

    public List<MerchantNetSn> queryMerchantNetSnPage(Map map);

    public Integer queryMerchantNetSnPageCount(Map map);
    
    public MerchantNetSn queryMerchantNetSnBySnCode(MerchantNetSn merchantNetSn);
}

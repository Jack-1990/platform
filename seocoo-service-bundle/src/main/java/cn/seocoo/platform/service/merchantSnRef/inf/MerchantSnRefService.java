package cn.seocoo.platform.service.merchantSnRef.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.MerchantSnRef;

public interface MerchantSnRefService {

    public MerchantSnRef queryMerchantSnRef(MerchantSnRef merchantSnRef);

    public List<MerchantSnRef> queryMerchantSnRefList(MerchantSnRef merchantSnRef);

    public void saveMerchantSnRef(MerchantSnRef merchantSnRef);

    public void updateMerchantSnRef(MerchantSnRef merchantSnRef);

    public void deleteMerchantSnRef(MerchantSnRef merchantSnRef);

    public List<MerchantSnRef> queryMerchantSnRefPage(Map map);

    public Integer queryMerchantSnRefPageCount(Map map);
    
    public List<MerchantSnRef> queryByMerchantSnRef(MerchantSnRef merchantSnRef);
}

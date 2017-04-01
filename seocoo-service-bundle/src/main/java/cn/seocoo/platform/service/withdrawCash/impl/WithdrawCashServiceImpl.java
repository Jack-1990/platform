package cn.seocoo.platform.service.withdrawCash.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.withdrawCash.inf.WithdrawCashDao;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.WithdrawCash;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.withdrawCash.inf.WithdrawCashService;

public class WithdrawCashServiceImpl  implements WithdrawCashService{

	  private WithdrawCashDao withdrawCashDao;
	private MerchantInfoService merchantInfoService;

    public WithdrawCashDao getWithdrawCash() {
        return withdrawCashDao;
    }
    public void setWithdrawCashDao(WithdrawCashDao withdrawCashDao) {
         this.withdrawCashDao = withdrawCashDao;
    }

	public MerchantInfoService getMerchantInfoService()
	{
		return merchantInfoService;
	}

	public void setMerchantInfoService(MerchantInfoService merchantInfoService)
	{
		this.merchantInfoService = merchantInfoService;
	}
	public WithdrawCashDao getWithdrawCashDao()
	{
		return withdrawCashDao;
	}

	@Override
    public WithdrawCash queryWithdrawCash(WithdrawCash withdrawCash){
        return withdrawCashDao.queryWithdrawCash(withdrawCash);
    }

    @Override
    public List<WithdrawCash> queryWithdrawCashList(WithdrawCash withdrawCash){
        return withdrawCashDao.queryWithdrawCashList(withdrawCash);
    }
    @Override
    public void saveWithdrawCash(WithdrawCash withdrawCash){
		// 先更新用户分润额度

		MerchantInfo merchantInfo = new MerchantInfo();
		merchantInfo.setMerchantCode(withdrawCash.getMerchantCode());
		List<MerchantInfo> queryMerchantInfoList = merchantInfoService.queryMerchantInfoList(merchantInfo);
		if (queryMerchantInfoList != null && queryMerchantInfoList.size() > 0)
		{
			merchantInfo = queryMerchantInfoList.get(0);
		}
		Double subAmount = merchantInfo.getCurrentTotalProfit() - withdrawCash.getWithdrawAmount();
		if (subAmount >= 0)
		{
			merchantInfo.setCurrentTotalProfit(subAmount);
			merchantInfoService.updateMerchantInfo(merchantInfo);
			// 插入体现信息
			withdrawCashDao.saveWithdrawCash(withdrawCash);
		} else
		{
			throw new RuntimeException("申请提现超额");
		}

    }
    @Override
    public void updateWithdrawCash(WithdrawCash withdrawCash){
        withdrawCashDao.updateWithdrawCash(withdrawCash);
    }
    @Override
    public void deleteWithdrawCash(WithdrawCash withdrawCash){
        withdrawCashDao.deleteWithdrawCash(withdrawCash);
    }
    @Override
    public List<WithdrawCash> queryWithdrawCashPage(Map map){
        return withdrawCashDao.queryWithdrawCashPage(map);
    }
    @Override
    public Integer queryWithdrawCashPageCount(Map map){
        return withdrawCashDao.queryWithdrawCashPageCount(map);
    }
}

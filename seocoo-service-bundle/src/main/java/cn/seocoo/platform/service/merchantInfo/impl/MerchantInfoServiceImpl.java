package cn.seocoo.platform.service.merchantInfo.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.merchantInfo.inf.MerchantInfoDao;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;

public class MerchantInfoServiceImpl implements MerchantInfoService {

	private MerchantInfoDao merchantInfoDao;

	public MerchantInfoDao getMerchantInfo() {
		return merchantInfoDao;
	}

	public void setMerchantInfoDao(MerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}

	@Override
	public MerchantInfo queryMerchantInfo(MerchantInfo merchantInfo) {
		return merchantInfoDao.queryMerchantInfo(merchantInfo);
	}

	@Override
	public List<MerchantInfo> queryMerchantInfoList(MerchantInfo merchantInfo) {
		return merchantInfoDao.queryMerchantInfoList(merchantInfo);
	}

	@Override
	public void saveMerchantInfo(MerchantInfo merchantInfo) {
		merchantInfoDao.saveMerchantInfo(merchantInfo);
	}

	@Override
	public void updateMerchantInfo(MerchantInfo merchantInfo) {
		merchantInfoDao.updateMerchantInfo(merchantInfo);
	}

	@Override
	public void deleteMerchantInfo(MerchantInfo merchantInfo) {
		merchantInfoDao.deleteMerchantInfo(merchantInfo);
	}

	@Override
	public List<MerchantInfo> queryMerchantInfoPage(Map map) {
		return merchantInfoDao.queryMerchantInfoPage(map);
	}

	@Override
	public Integer queryMerchantInfoPageCount(Map map) {
		return merchantInfoDao.queryMerchantInfoPageCount(map);
	}

	@Override
	public List<MerchantInfo> queryProfitMerchantInfos(MerchantInfo merchantInfo) {
		return merchantInfoDao.queryProfitMerchantInfos(merchantInfo);
	}

	@Override
	public List<MerchantInfo> queryMerchantDirectUserAndLevel(MerchantInfo merchantInfo) {
		// TODO Auto-generated method stub
		return merchantInfoDao.queryMerchantDirectUserAndLevel(merchantInfo);
	}

	@Override
	public List<MerchantInfo> queryMerchantUserPageWithMerchant(Map map) {
		// TODO Auto-generated method stub
		return merchantInfoDao.queryMerchantUserPageWithMerchant(map);
	}

	@Override
	public List<MerchantInfo> queryMerchantInfoAndBankAndPic(MerchantInfo merchantInfo) {
		// TODO Auto-generated method stub
		return merchantInfoDao.queryMerchantInfoAndBankAndPic(merchantInfo);
	}

	public List<MerchantInfo> queryParentList(MerchantInfo merchantInfo) {
		return merchantInfoDao.queryParentList(merchantInfo);
	}

	public void updateMerchantInfoProfit(MerchantInfo merchantInfo) {
		merchantInfoDao.updateMerchantInfoProfit(merchantInfo);
	}
}

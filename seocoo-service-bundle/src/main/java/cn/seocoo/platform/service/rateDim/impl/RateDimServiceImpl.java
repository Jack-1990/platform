package cn.seocoo.platform.service.rateDim.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.rateDim.inf.RateDimDao;
import cn.seocoo.platform.dao.rateDimAttr.inf.RateDimAttrDao;
import cn.seocoo.platform.dao.rateSku.inf.RateSkuDao;
import cn.seocoo.platform.model.RateDim;
import cn.seocoo.platform.model.RateDimAttr;
import cn.seocoo.platform.model.RateSku;
import cn.seocoo.platform.service.rateDim.inf.RateDimService;

public class RateDimServiceImpl implements RateDimService
{

	private RateDimDao rateDimDao;
	private RateDimAttrDao rateDimAttrDao;
	private RateSkuDao rateSkuDao;

	public RateDimDao getRateDimDao()
	{
		return rateDimDao;
	}

	public void setRateDimDao(RateDimDao rateDimDao)
	{
		this.rateDimDao = rateDimDao;
	}

	public RateDimAttrDao getRateDimAttrDao()
	{
		return rateDimAttrDao;
	}

	public void setRateDimAttrDao(RateDimAttrDao rateDimAttrDao)
	{
		this.rateDimAttrDao = rateDimAttrDao;
	}

	public RateSkuDao getRateSkuDao()
	{
		return rateSkuDao;
	}

	public void setRateSkuDao(RateSkuDao rateSkuDao)
	{
		this.rateSkuDao = rateSkuDao;
	}

	@Override
	public RateDim queryRateDim(RateDim rateDim)
	{
		return rateDimDao.queryRateDim(rateDim);
	}

	@Override
	public List<RateDim> queryRateDimList(RateDim rateDim)
	{
		return rateDimDao.queryRateDimList(rateDim);
	}

	@Override
	public void saveRateDim(RateDim rateDim)
	{
		rateDimDao.saveRateDim(rateDim);
	}

	@Override
	public void updateRateDim(RateDim rateDim)
	{
		rateDimDao.updateRateDim(rateDim);
	}

	@Override
	public void deleteRateDim(RateDim rateDim)
	{
		rateDimDao.deleteRateDim(rateDim);
	}

	@Override
	public List<RateDim> queryRateDimPage(Map map)
	{
		return rateDimDao.queryRateDimPage(map);
	}

	@Override
	public Integer queryRateDimPageCount(Map map)
	{
		return rateDimDao.queryRateDimPageCount(map);
	}

	@Override
	public void batchInsertRate(List<RateDim> rateDim, List<RateDimAttr> rateDimAttr, List<RateSku> rateSku)
	{
		// 先删除后添加
		RateDim rateDim1 = new RateDim();
		rateDimDao.deleteRateDim(rateDim1);
		RateDimAttr rateDimAttr1 = new RateDimAttr();
		rateDimAttrDao.deleteRateDimAttr(rateDimAttr1);
		RateSku rateSku1 = new RateSku();
		rateSkuDao.deleteRateSku(rateSku1);
		// 插入信息
		rateDimDao.batchInsertRateDim(rateDim);
		rateDimAttrDao.batchInsertRateDimAttr(rateDimAttr);
		rateSkuDao.batchInsertRateSku(rateSku);
	}
}

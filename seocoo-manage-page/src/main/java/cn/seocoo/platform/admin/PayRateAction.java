package cn.seocoo.platform.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.DimDic;
import cn.seocoo.platform.model.DimDicInfo;
import cn.seocoo.platform.model.RateDim;
import cn.seocoo.platform.model.RateDimAttr;
import cn.seocoo.platform.model.RateSku;
import cn.seocoo.platform.service.dimDic.inf.DimDicService;
import cn.seocoo.platform.service.dimDicInfo.inf.DimDicInfoService;
import cn.seocoo.platform.service.rateDim.inf.RateDimService;
import cn.seocoo.platform.service.rateDimAttr.inf.RateDimAttrService;
import cn.seocoo.platform.service.rateSku.inf.RateSkuService;

public class PayRateAction extends BaseAction
{
	private static final long serialVersionUID = 1L;

	private DimDicService dimDicService;
	private DimDicInfoService dimDicInfoService;

	private RateDimService rateDimService;
	private RateDimAttrService rateDimAttrService;
	private RateSkuService rateSkuService;

	private RateDim rateDim;
	private RateDimAttr rateDimAttr;
	private RateSku rateSku;

	public String payRate()
	{
		// 用户点击 获取 费率信息
		rateDim = new RateDim();
		List<RateDim> rateDimList = rateDimService.queryRateDimList(rateDim);
		rateDimAttr = new RateDimAttr();
		List<RateDimAttr> rateDimAttrList = rateDimAttrService.queryRateDimAttrList(rateDimAttr);
		List<RateDimAttr> rateDimAttrL = null;
		// 规格和属性的对应关系 是一对多
		for (RateDim rateDim : rateDimList)
		{
			rateDimAttrL = new ArrayList<RateDimAttr>();
			for (RateDimAttr rateDimAttr : rateDimAttrList)
			{
				if (StringUtils.equals(rateDim.getDimCode(), rateDimAttr.getDimCode()))
				{
					rateDimAttrL.add(rateDimAttr);
				}
			}
			rateDim.setRateDimAttrList(rateDimAttrL);
		}
		rateSku = new RateSku();
		List<RateSku> rateSkuList = rateSkuService.queryRateSkuList(rateSku);

		request.setAttribute("rateDimList", rateDimList);
		request.setAttribute("rateSkuList", JSONObject.toJSONString(rateSkuList));
		return "payRate";
	}

	/**
	 * 获取 规格字典信息 集合
	 * 
	 * @throws IOException
	 */
	public void getDimDic() throws IOException
	{
		DimDic dimDic = new DimDic();
		List<DimDic> dimDicList = dimDicService.queryDimDicList(dimDic);
		JSONObject json = new JSONObject();
		json.put("dimDicList", dimDicList);
		this.sendMessages(json.toJSONString());
	}

	/**
	 * 根据字典规格获取字典属性
	 * 
	 * @throws IOException
	 */
	public void getDimDicInfo() throws IOException
	{
		String code = request.getParameter("sku_code");
		DimDicInfo dimDicInfo = new DimDicInfo();
		dimDicInfo.setCode(code);
		List<DimDicInfo> dimDicInfoList = dimDicInfoService.queryDimDicInfoList(dimDicInfo);
		JSONObject json = new JSONObject();
		json.put("dimDicInfoList", dimDicInfoList);
		this.sendMessages(json.toJSONString());
	}

	/**
	 * 保存 费率信息
	 * 
	 * @throws IOException
	 */
	public void savePayRate() throws IOException
	{
		String rateDimJson = request.getParameter("rateDimJson");
		String rateSkuJson = request.getParameter("rateSkuJson");
		// 设置费率信息
		JSONObject rateDimJSON = JSONObject.parseObject(rateDimJson);
		String rate = rateDimJSON.getString("rateDim");
		JSONArray rateDimArray = JSONObject.parseArray(rate);
		ArrayList<RateDim> rateDimList = new ArrayList<RateDim>();
		ArrayList<RateDimAttr> rateDimAttrList = new ArrayList<RateDimAttr>();
		int rateDimIndex = 1;
		for (Object rateDim1 : rateDimArray)
		{
			// 设置规格
			JSONObject rateDimObject = JSONObject.parseObject(rateDim1.toString());
			rateDim = new RateDim();
			rateDim.setDimCode(rateDimObject.getString("dimCodes"));
			rateDim.setDimName(rateDimObject.getString("dimName"));
			rateDim.setSeq(rateDimIndex);
			rateDimList.add(rateDim);

			// 设置属性
			String dimAttrs = rateDimObject.getString("dimAttrs");
			JSONArray dimAttrsArray = JSONObject.parseArray(dimAttrs);
			int dimAttrObIndex = 1;
			for (Object dimAttrOb : dimAttrsArray)
			{
				JSONObject dimAttrObject = JSONObject.parseObject(dimAttrOb.toString());
				rateDimAttr = new RateDimAttr();
				rateDimAttr.setDimCode(rateDimObject.getString("dimCodes"));
				rateDimAttr.setDimAttrCode(dimAttrObject.getString("dimAttrCode"));
				rateDimAttr.setDimAttrName(dimAttrObject.getString("dimAttrName"));
				rateDimAttr.setSeq(dimAttrObIndex);
				rateDimAttrList.add(rateDimAttr);
				dimAttrObIndex++;
			}
			rateDimIndex++;
		}

		// 设置费率信息
		ArrayList<RateSku> rateSkuList = new ArrayList<RateSku>();
		JSONObject rateSkuJsonOb = JSONObject.parseObject(rateSkuJson);
		String rateSkuJ = rateSkuJsonOb.getString("rateSku");
		JSONArray rateSkuJArray = JSONObject.parseArray(rateSkuJ);
		int rateSkuOIndex = 1;
		for (Object rateSkuO : rateSkuJArray)
		{
			JSONObject parseRateSku = JSONObject.parseObject(rateSkuO.toString());
			rateSku = new RateSku();
			rateSku.setSkuCode(parseRateSku.getString("skuCode"));
			rateSku.setIntoRate(parseRateSku.getString("intoRate") == null ? 0 : Double.valueOf(parseRateSku.getString("intoRate")));
			rateSku.setSetRate(parseRateSku.getString("setRate") == null ? 0 : Double.valueOf(parseRateSku.getString("setRate")));
			rateSku.setSeq(rateSkuOIndex);
			rateSkuList.add(rateSku);
			rateSkuOIndex++;
		}
		JSONObject Json = new JSONObject();
		// 保存数据
		try
		{
			rateDimService.batchInsertRate(rateDimList, rateDimAttrList, rateSkuList);
			Json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
		} catch (Exception e)
		{
			e.printStackTrace();
			Json.put("resultCode", Constant.RESULT_CODE_FAIL);
		}
		this.sendMessages(Json.toJSONString());
	}

	public DimDicService getDimDicService()
	{
		return dimDicService;
	}

	public void setDimDicService(DimDicService dimDicService)
	{
		this.dimDicService = dimDicService;
	}

	public DimDicInfoService getDimDicInfoService()
	{
		return dimDicInfoService;
	}

	public void setDimDicInfoService(DimDicInfoService dimDicInfoService)
	{
		this.dimDicInfoService = dimDicInfoService;
	}

	public RateDimService getRateDimService()
	{
		return rateDimService;
	}

	public void setRateDimService(RateDimService rateDimService)
	{
		this.rateDimService = rateDimService;
	}

	public RateDimAttrService getRateDimAttrService()
	{
		return rateDimAttrService;
	}

	public void setRateDimAttrService(RateDimAttrService rateDimAttrService)
	{
		this.rateDimAttrService = rateDimAttrService;
	}

	public RateSkuService getRateSkuService()
	{
		return rateSkuService;
	}

	public void setRateSkuService(RateSkuService rateSkuService)
	{
		this.rateSkuService = rateSkuService;
	}

	public RateSku getRateSku()
	{
		return rateSku;
	}

	public void setRateSku(RateSku rateSku)
	{
		this.rateSku = rateSku;
	}

	public RateDim getRateDim()
	{
		return rateDim;
	}

	public void setRateDim(RateDim rateDim)
	{
		this.rateDim = rateDim;
	}

	public RateDimAttr getRateDimAttr()
	{
		return rateDimAttr;
	}

	public void setRateDimAttr(RateDimAttr rateDimAttr)
	{
		this.rateDimAttr = rateDimAttr;
	}

}

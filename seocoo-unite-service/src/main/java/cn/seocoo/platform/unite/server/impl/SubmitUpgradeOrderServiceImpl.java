package cn.seocoo.platform.unite.server.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.odchina.micro.util.DateUtils;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.DimDicInfo;
import cn.seocoo.platform.model.PaymentAccount;
import cn.seocoo.platform.model.Upgrade;
import cn.seocoo.platform.model.UserUpgrade;
import cn.seocoo.platform.model.UserUpgradeOrder;
import cn.seocoo.platform.service.dimDicInfo.inf.DimDicInfoService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.upgrade.inf.UpgradeService;
import cn.seocoo.platform.service.userUpgrade.inf.UserUpgradeService;
import cn.seocoo.platform.service.userUpgradeOrder.inf.UserUpgradeOrderService;
import cn.seocoo.platform.unite.Result;

/**
 * 提交订单信息接口
 *
 */
public class SubmitUpgradeOrderServiceImpl implements Service {
	private Logger logger = Logger.getLogger(this.getClass());
	private UserUpgradeOrderService userUpgradeOrderService;
	private UpgradeService upgradeService;
	private UserUpgradeService userUpgradeService;
	private DimDicInfoService dimDicInfoService;

	@Override
	public Object sevice(String param) {
		logger.debug("SubmitOrderServiceImpl提交订单请求参数：" + param);
		Result reslut=new Result();
		// 参数验证
		String validateRes = validateParam(param);

		UserUpgradeOrder order = JSON.parseObject(param, UserUpgradeOrder.class);
		reslut.setResultCode(Constant.RESULT_CODE_FAIL);
		if ("ok".equals(validateRes)) {
			String  statusName=getStatusName(order);
			if("OK".equals(statusName)){
				String orderNumber = generateOrderNumber();//生成升级订单号
				order.setOrderNumber(orderNumber);
				order.setOrderDate(new Date());
				order.setStatus("unpay");
				userUpgradeOrderService.saveUserUpgradeOrder(order);
				
				PaymentAccount account=new PaymentAccount();
				String privateKey="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKzW+wJJqLK/2fp9hzaF/dDphmR4qf+rcMagdWVc9GQUIKRPumcQkezgqxRrQprcuDHldHthBYQxV6zWRbrxKG9MCuPsMEj3ByFs/3eSDzQ5yTB5OJBoM0pxAOhg+eB9rblv+yyWxzot9dGpB2uePANDKYpRY9j1fnFkW0d3/O/lAgMBAAECgYBiI39LAUAEnuLKVFReJR7FbJOeoNUGEvZqEdoWv/0UhkkygwK4WrRA6CC761taS1FbI4pMM/7J3KqHvPLC/wORn4mjlCR8DV7pn8+fACCAFj9aaRGa5lXca9YAU/uxHWfAePLh2EjFH1PnBYu8cG2lm6Pf9hQYI4HyoFaiMYPYAQJBANgFYDNAhnk8NjyiSLm2KNpFuFr6nget0HoGzU0fGgdTwIjPNTPoZI5OAKDx+mChm4XovG65n04C902bdpHUI2UCQQDM08SgCEblL1a88eYBtfikVwXqXtS9lQFQa900E/pFayFupbbcus3AuaHM+882p31jGA2FZ/JUjmUFmM46khKBAkEAtwKG56TIyDkMsf3CoyMCJTlf4CPmchb9QgQ1NhsdUAvSV5VEO3+sgSrwOWoHdoozWhU8Xon/vnWg0izdHNqeSQJBALKjx4tjbKGaReYIe3fmg3KhS1F7X8Pw3vKLAKPZAJ/mrYPZF8EvUx/RRuKGg9TBA0SXx8MgQ2OnxUe6W7MNjwECQQCxD5gqsNnh+9esThG8HoarKom7TdX20nfU6P3MKfVRY0BbwqUsVOVYgLQ7Y/YxBen3L3RKrHfvXYVTCmSG6hU1";//支付宝商户秘钥
				String partnerId="2088412523706213";//支付宝商户 id
				String sellerId="leo@seocoo.cn";//支付宝、微信商户账号
				
				String alipay_notify_async_url = SystemConfigUtil.getSingleProperty("ALIPAY_NOTIFY_ASYNC_URL").getPropertyValue();
				
				account.setSellerId(sellerId);
				account.setPrivateKey(privateKey);
				account.setPartnerId(partnerId);
				account.setAlipay_notify_async_url(alipay_notify_async_url);
				
				Map map=new HashMap();
				map.put("order", order);
				map.put("account", account);
				reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
				reslut.setResultData(map);
			}else{
				reslut.setResultCode(Constant.RESULT_CODE_FAIL);
				reslut.setResultMsg(statusName);
			}
		}else{
			reslut.setResultCode(Constant.RESULT_CODE_FAIL);
			reslut.setResultMsg(validateRes);
		}
		String res = JSON.toJSONString(reslut);
		return res;
	}

	
	/**
	 * @return 生成订单号
	 */
	private String generateOrderNumber() {
		return new StringBuffer().append(Constant.PREFIX_UPORDER)
				.append(DateUtils.toString(new Date(), Constant.WECHATDATEFORMAT))
				.append((int) (Math.random() * 9000 + 1000) + "").toString();
	}

	
	/**
	 * 检验是否可以再次提交升级申请
	 * @param merchantCode
	 * @return
	 */
	public String getStatusName(UserUpgradeOrder order){
		String statusName="OK";
		//查询是否已经有申请请求正在处理（包含所有的类型）
		UserUpgrade uug= new UserUpgrade();
		uug.setMerchantCode(order.getMerchantCode());
		List<UserUpgrade> uugList=userUpgradeService.queryListMust(uug);
		if(uugList !=null && uugList.size() > 0){
			uug = uugList.get(0);
			String description="";
			if("member".equals(uug.getAttrStyle())){
				description="会员推广申请";
			}else if("dues".equals(uug.getAttrStyle())){
				description="付费申请";
			}
			statusName ="您已经提交从"+getAttrName(uug.getFromLevelCode())+"升级为"+getAttrName(uug.getToLevelCode())+"的"
			            +description+"，请耐心等待";
		}
		
		//检验此次升级是否可以,防止APP端个人等级未更新
		if("OK".equals(statusName)){
			Upgrade ue =new Upgrade();
			ue.setId(order.getUpGrade_id());
			List<Upgrade> ueList=upgradeService.queryUpgradeList(ue);
			if(ueList !=null && ueList.size() >0){
				UserUpgrade uu= new UserUpgrade();
				uu.setFromLevelCode(ueList.get(0).getFromLevelCode());
				uu.setToLevelCode(ueList.get(0).getToLevelCode());
				uu.setMerchantCode(order.getMerchantCode());
				List<UserUpgrade> uList=userUpgradeService.queryIsnotUpgrade(uu);
				if(uList !=null && uList.size() >0){
				  statusName ="您已经提交从"+getAttrName(uList.get(0).getFromLevelCode())+"升级为"+getAttrName(uList.get(0).getToLevelCode())+"的付费申请，请耐心等待";
				}
			}
		}
		return statusName;
	}
	
	
	/**
	 * 获取等级名称
	 * @param levelCode
	 * @return
	 */
	private String getAttrName(String levelCode) {
		String result="";
		DimDicInfo  info =new DimDicInfo();
		info.setAttrCode(levelCode);
		info.setCode(Constant.DIMDIC_LEVEL);
		List<DimDicInfo> infiList=dimDicInfoService.queryDimDicInfoList(info);
		if(infiList !=null && infiList.size() > 0){
			result =infiList.get(0).getAttrName();
		}
		return result;
	}


	// 验证参数
	public String validateParam(String param) {
		UserUpgradeOrder order = JSON.parseObject(param, UserUpgradeOrder.class);
		if (order == null) {
			return "param is null";
		}
		if (order.getMerchantCode() == null || "".equals(order.getMerchantCode())) {
			return "商家编码不能为空";
		}
		if (order.getOrderPrice()== null || "".equals(order.getOrderPrice())) {
			return "支付金额不能为空";
		}
		if (order.getChannel()== null || "".equals(order.getChannel())) {
			return "下单渠道不能为空";
		}
		if (order.getPaymentChannel()== null || "".equals(order.getPaymentChannel())) {
			return "支付渠道不能为空";
		}
		if (order.getOrderUser()== null || "".equals(order.getOrderUser())) {
			return "下单用户不能为空";
		}
		if (order.getUpGrade_id()== null || "".equals(order.getUpGrade_id())) {
			return "升级参数不能为空";
		}
		return "ok";
	}
	
	
	public UserUpgradeOrderService getUserUpgradeOrderService() {
		return userUpgradeOrderService;
	}

	public void setUserUpgradeOrderService(UserUpgradeOrderService userUpgradeOrderService) {
		this.userUpgradeOrderService = userUpgradeOrderService;
	}

	public UpgradeService getUpgradeService() {
		return upgradeService;
	}

	public void setUpgradeService(UpgradeService upgradeService) {
		this.upgradeService = upgradeService;
	}

	public UserUpgradeService getUserUpgradeService() {
		return userUpgradeService;
	}

	public void setUserUpgradeService(UserUpgradeService userUpgradeService) {
		this.userUpgradeService = userUpgradeService;
	}

	public DimDicInfoService getDimDicInfoService() {
		return dimDicInfoService;
	}

	public void setDimDicInfoService(DimDicInfoService dimDicInfoService) {
		this.dimDicInfoService = dimDicInfoService;
	}
	 
	
}
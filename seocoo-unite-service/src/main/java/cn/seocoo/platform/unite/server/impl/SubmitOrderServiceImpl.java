package cn.seocoo.platform.unite.server.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.odchina.micro.util.DateUtils;
import com.odchina.micro.util.HttpUtils;
import com.odchina.micro.util.PayCoreUtil;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.Merchant;
import cn.seocoo.platform.model.Order;
import cn.seocoo.platform.msbank.BindOrder;
import cn.seocoo.platform.msbank.OrderResultMsg;
import cn.seocoo.platform.msbank.PublicRequestMsg;
import cn.seocoo.platform.msbank.PublicReturnMsg;
import cn.seocoo.platform.msbank.ResponseMsg;
import cn.seocoo.platform.service.merchant.inf.MerchantService;
import cn.seocoo.platform.service.order.inf.OrderService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;
import sun.misc.BASE64Encoder;

/**
 * 提交订单信息接口
 *
 */
public class SubmitOrderServiceImpl implements Service {
	private Logger logger = Logger.getLogger(this.getClass());
	private OrderService orderService;
	private MerchantService merchantService;

	@Override
	public Object sevice(String param) {
		logger.debug("SubmitOrderServiceImpl提交订单请求参数：" + param);
		// 参数验证
		String validateRes = validateParam(param);

		Order order = JSON.parseObject(param, Order.class);
		Result result = new Result();
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("ok".equals(validateRes)) {
			String merchantCode = order.getMerchantCode();
			Merchant merchant = new Merchant();
			merchant.setOutMchntId(merchantCode);
			Merchant merchant2 = merchantService.queryMerchant(merchant);

			String platformId = "";// 平台号
			String cmbcMchntId = "";// 民生商户号

			if (merchant2 != null) {
				platformId = merchant2.getPlatformId();
				cmbcMchntId = merchant2.getCmbcMchntId();
			
			order.setPlatformId(platformId);
			order.setMerchantNo(cmbcMchntId);

			String orderNumber = generateOrderNumber();// 订单号
			order.setOrderNumber(orderNumber);

			String merchantSeq = platformId + DateUtils.toString(new Date(), Constant.DATEONLY) + orderNumber;// 流水号
			order.setMerchantSeq(merchantSeq);
			order.setOrderInfo("订单号" + orderNumber);
			order.setTransTime(new Date());// 交易时间

			Integer scanStyle = order.getScanStyle();// 反扫 将付款码转化为base64位字符串
														// 放到remark中
			if (scanStyle == 1) {
				String remark = getBase64(order.getPayCode());
				order.setRemark(remark);
			}

			order.setStatus(0);// 未支付状态
			order.setChannel("00");// 支付渠道 插件下单

			String selectTradeType = order.getSelectTradeType();
			if (selectTradeType != null) {
				if (selectTradeType.contains("WX")) {
					order.setPayChannel("weixin");
				} else if (selectTradeType.contains("ZFB")) {
					order.setPayChannel("zhifubao");
				}
			}
			orderService.saveOrder(order);

			OrderResultMsg orMsg = submitOrderToMs(getBindOrder(order), scanStyle);
			boolean isSuccess=orMsg.getIsSuccess();
			if (isSuccess) {
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				String resultMsg = orMsg.getRemark();
				resultMsg=resultMsg.substring(resultMsg.indexOf("=") + 1);
				if(resultMsg.endsWith("|"))
				{
					resultMsg=resultMsg.substring(0, resultMsg.length()-1);
				}
				Map<String,String> resultMap=new HashMap<String,String>();
				resultMap.put("qrCodeUrl", resultMsg);
				resultMap.put("orderNumber", order.getOrderNumber());
				
				result.setResultData(resultMap);
			}else{
				result.setResultMsg(orMsg.getFailMsg());
			}  
		 }else{
			 result.setResultMsg("商户不存在");
		 }
		} else {
			result.setResultMsg(validateRes);
		}

		String res = JSON.toJSONString(result);
		logger.debug("SubmitOrderServiceImpl提交订单请求信息报文：" + res);
		return res;
	}

	/**
	 * 提交订单信息给民生银行
	 * 
	 * @param bindOrder
	 * @return
	 */
	public OrderResultMsg submitOrderToMs(BindOrder bindOrder, Integer scanStyle) {

		String context = JSONObject.toJSONString(bindOrder);
		
		logger.info(context);
		
		String sign = PayCoreUtil.getSign(context);
		logger.info("--------------------------------------");
		logger.info("签名：");
		logger.info(sign);

		String signContext = PayCoreUtil.sign(sign, context);
		logger.info("--------------------------------------");
		logger.info("加密前：");
		logger.info(signContext);

		String encryptContext = PayCoreUtil.encrypt(signContext);
		logger.info("--------------------------------------");
		logger.info("加密后：");
		logger.info(encryptContext);

		PublicRequestMsg MerchantMsg = new PublicRequestMsg();
		MerchantMsg.setBusinessContext(encryptContext);

		logger.info("报文信息======" + JSONObject.toJSONString(MerchantMsg) + "\n");
		
		String submitOrderUrl=SystemConfigUtil.getSingleProperty("submitOrder_ms_url").getPropertyValue();
		String str = HttpUtils.sendPost(submitOrderUrl,JSONObject.toJSONString(MerchantMsg));

		logger.info("银行返回=" + str);

		if(str!=null&&!"".equals(str)){
			PublicReturnMsg prm = JSONObject.parseObject(str, PublicReturnMsg.class);
			String gateReturnType = prm.getGateReturnType();
			//gateReturnType为S时，处理返回字段
			if ("S".equals(gateReturnType)) {
				String dncryptContext = PayCoreUtil.dncrypt(prm.getBusinessContext());
				logger.info("--------------------------------------");
				logger.info("解密后：");
				logger.info(dncryptContext);
	
				ResponseMsg rm = JSONObject.parseObject(dncryptContext, ResponseMsg.class);
				String body = rm.getBody();
				logger.info("最终结果：");
				logger.info(body);
	
				OrderResultMsg orm = JSONObject.parseObject(body, OrderResultMsg.class);
				String signChkResult = PayCoreUtil.signCheck(dncryptContext);
				logger.info("--------------------------------------");
				logger.info("验证签名结果：");
				logger.info(signChkResult);
	
				// String remark=orm.getRemark();
				String tradeStatus = orm.getTradeStatus();// E 订单失败 ,R原订单成功，未支付（待支付）
				if ("验签通过".equals(signChkResult) && "R".equals(tradeStatus)) {//验签通过并且返回tradeStatus为R时，返回结果
					orm.setIsSuccess(true);
					return orm;
				} else {
					orm.setIsSuccess(false);
					orm.setFailMsg("验签失败");
					return orm;
				}
			} else {
				OrderResultMsg orm1=new OrderResultMsg();
				orm1.setIsSuccess(false);
				orm1.setFailMsg(prm.getGateReturnMessage());
				return orm1;
			}
		}else{
			OrderResultMsg orm1=new OrderResultMsg();
			orm1.setIsSuccess(false);
			orm1.setFailMsg("网络通讯异常");
			return orm1;
		}

	}

	/**
	 * 转化为民生银行 下单接口类
	 * 
	 * @param order
	 * @return
	 */
	public BindOrder getBindOrder(Order order) {
		BindOrder br = new BindOrder();
		br.setPlatformId(order.getPlatformId());
		br.setMerchantNo(order.getMerchantNo());
		br.setSelectTradeType(order.getSelectTradeType());
		
		/*String amountStr=order.getAmount();
		if(amountStr!=null&&!"".equals(amountStr))
		{
			String amount=Double.parseDouble(amountStr)+"";
			br.setAmount(amount);
		}*/
		br.setAmount(order.getAmount());
		br.setOrderInfo(order.getOrderInfo());
		br.setMerchantSeq(order.getMerchantSeq());
		br.setTransDate(DateUtils.toString(new Date(), Constant.DATEONLY));
		br.setTransTime(DateUtils.toString(new Date(), Constant.TRANSTIME));
		String noticeUrl=SystemConfigUtil.getSingleProperty("notice_ms_url").getPropertyValue();
		br.setNotifyUrl(noticeUrl);
		
		br.setRemark(order.getRemark());
		return br;
	}

	/**
	 * Base64对字符串进行加密
	 * @param str
	 * @return
	 */
	public String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	/**
	 * @return 生成订单号
	 */
	private String generateOrderNumber() {
		return new StringBuffer().append(Constant.PREFIX_ORDER)
				.append(DateUtils.toString(new Date(), Constant.WECHATDATEFORMAT))
				.append((int) (Math.random() * 9000 + 1000) + "").toString();
	}

	// 验证参数
	public String validateParam(String param) {
		Order order = JSON.parseObject(param, Order.class);
		if (order == null) {
			return "param is null";
		}
		if (order.getMerchantCode() == null || "".equals(order.getMerchantCode())) {
			return "商家编码不能为空";
		}
		if (order.getSelectTradeType() == null || "".equals(order.getSelectTradeType())) {
			return "支付方式不能为空";
		}
		if (order.getAmount() == null || "".equals(order.getAmount())) {
			return "支付金额不能为空";
		}
		if (order.getScanStyle() == null || "".equals(order.getScanStyle())) {
			return "扫码方式不能为空";
		} else {
			if (order.getScanStyle() == 1) {
				if (order.getPayCode() == null || "".equals(order.getPayCode())) {
					return "付款码不能为空";
				}
			}
		}
		return "ok";
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public MerchantService getMerchantService() {
		return merchantService;
	}

	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
}
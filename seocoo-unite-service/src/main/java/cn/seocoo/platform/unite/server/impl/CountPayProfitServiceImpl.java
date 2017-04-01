package cn.seocoo.platform.unite.server.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.util.ComputeUtil;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.MerchantRate;
import cn.seocoo.platform.model.Order;
import cn.seocoo.platform.model.PaymentProfit;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantRate.inf.MerchantRateService;
import cn.seocoo.platform.service.paymentProfit.inf.PaymentProfitService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

/**
 * 根据支付的订单计算该订单所产生的分润 的分配金额
 * @author cdw
 *
 */
public class CountPayProfitServiceImpl  implements Service{
	private PaymentProfitService paymentProfitService;
	private MerchantInfoService merchantInfoService;
	private MerchantRateService merchantRateService;
	
	private static final Logger logger = Logger.getLogger(CountPayProfitServiceImpl.class);
	@Override
	public Object sevice(String param) {
		Order order  = JSONObject.parseObject(param, Order.class);
		Result reslut=new Result();
		//入参检验
	    String checkMsg=checkParam(order);
	    if("OK".equals(checkMsg)){
	       String orderNumber=order.getOrderNumber();//订单号
	       String payChannel=order.getPayChannel(); //支付方式：weixin,zhifubao
	       String amount=order.getAmount();//订单金额
	       String merchantCode=order.getMerchantCode();//商户编码
	       
		   MerchantInfo merchantInfo=new MerchantInfo();
	       merchantInfo.setMerchantCode(merchantCode);
	       merchantInfo=merchantInfoService.queryMerchantInfo(merchantInfo);
	       
	       //获取当期系统所规定的计算分润的向上追溯的层级数
	       String countProfitLevel=SystemConfigUtil.getSingleProperty("countProfit_level").getPropertyValue();
	       if(countProfitLevel!=null||!"".equals(countProfitLevel)){
	    	   merchantInfo.setCountProfitLevel(Integer.parseInt(countProfitLevel));
	       }else{
	    	   merchantInfo.setCountProfitLevel(10);
	       }
	     
	       //获取交易商户的上级商户（向上计算规定级数的层级商户+本商户）
	       List<MerchantInfo> mInfos=getParentList(merchantInfo);
	       
	       //计算所需要的上级发展用户的具体分润信息
	        List<PaymentProfit> profitList= CountUserProfits( mInfos,order);
	        paymentProfitService.savePaymentProfitList(profitList);
	       
	        reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
		    reslut.setResultData(profitList);
	    }else{
	    	reslut.setResultCode(Constant.RESULT_CODE_FAIL);
			reslut.setResultMsg(checkMsg);
	    }
		 String res = JSON.toJSONString(reslut);
		 return res;
	}
	
	
	/**
	 * 
	 * 获取本级商户的上级商户list集合（规定的向上的计算层级数）
	 * @param merchantInfo
	 * @return
	 */
	public  List<MerchantInfo> getParentList(MerchantInfo merchantInfo){
		 List<MerchantInfo>  mInfos=new ArrayList<MerchantInfo>();
		 if(merchantInfo!=null){
			 mInfos.add(merchantInfo);
			 int level=merchantInfo.getCountProfitLevel();//获取所有向上计算分润的等级
			 for(int i=0;i<level;i++){
				 String parentMerchantCode=merchantInfo.getParentMerchantCode();//获取的上级商户code
				  if(parentMerchantCode!=null&&!"".equals(parentMerchantCode)){
					  logger.info("========countProfit========"+parentMerchantCode);
					  MerchantInfo mInfo=new MerchantInfo();
				      mInfo.setMerchantCode(parentMerchantCode);
				      MerchantInfo merchantInfo2=merchantInfoService.queryMerchantInfo(mInfo);
				      if(merchantInfo2!=null){
				    	  mInfos.add(merchantInfo2);
				    	  merchantInfo=merchantInfo2;
				      }else{
				    	  break;
				      }
				  }else{
					  break;
				  } 
			 }
		 }
		 return mInfos;
	}
	
	/**
	 * 根据获取的上级的商户信息 来计算其中能获得分润人员获得的具体分润
	 * @param mInfos
	 * @param payChannel 支付通道 （微信支付：weixin，支付宝支付：zhifubao）
	 * @return
	 */
	public List<PaymentProfit> CountUserProfits(List<MerchantInfo> mInfos,Order order){
		 List<PaymentProfit> profitList=new ArrayList<PaymentProfit>();
		 String orderNumber=order.getOrderNumber();//订单号
	     String payChannel=order.getPayChannel(); //支付方式：weixin,zhifubao
	     String amount=order.getAmount();//订单金额
	     String merchantCode=order.getMerchantCode();//商户编码
		
		
		//存放每一个商户对应的费率
		Map<String,MerchantRate> rateMap=new HashMap<String,MerchantRate>();
		for(MerchantInfo info:mInfos){
			MerchantRate rate  =new MerchantRate();
			rate.setMerchantCode(info.getMerchantCode());
			rate.setPayChannel(payChannel);
		    rate=merchantRateService.queryMerchantRate(rate);
		    rateMap.put(rate.getMerchantCode(), rate);
		}
	 
        if(mInfos!=null && mInfos.size()>0){
        	MerchantInfo info1=mInfos.get(0);
            String lowMerchantCode=info1.getMerchantCode();//进行交易的商户编码
			MerchantRate lowCurrentRate=rateMap.get(lowMerchantCode);//进行交易商户的费率
			Double lowIntoRate=lowCurrentRate.getIntoRate();//进行交易商户的进件费率
			Double lowTtradeRate=lowCurrentRate.getTradeRate();//进行交易商户的代理费率
			 
			if(lowIntoRate.doubleValue()!=lowTtradeRate.doubleValue()){
				double lowProfitRate=ComputeUtil.sub(lowIntoRate, lowTtradeRate);//费率差
				lowProfitRate=ComputeUtil.round(lowProfitRate, 2);
				
				Double totalOrderPrice =Double.parseDouble(amount);
				//计算用户所得分润金额
				double lowUserProfitPrice=(totalOrderPrice==null?0:totalOrderPrice.doubleValue())*(lowProfitRate/100);
				lowUserProfitPrice=ComputeUtil.round(lowUserProfitPrice, 2);
				
				if(lowUserProfitPrice>=1){
					PaymentProfit profit=new PaymentProfit();
					profit.setOrderNumber(orderNumber);
					profit.setTotalOrderPrice(totalOrderPrice);
					profit.setMerchantCode(merchantCode);
					profit.setUserProfitPrice(lowUserProfitPrice);
					profit.setProfitUser(lowMerchantCode);
					profit.setIntoRate(lowIntoRate);
					profit.setTradeRate(lowTtradeRate);
					profit.setNextUser(lowMerchantCode);
					profit.setNextUserIntoRate(lowIntoRate);
					profit.setNextUserTradeRate(lowTtradeRate);
					profit.setProfitRate(lowProfitRate);
					profit.setCreateTime(new Date());
					profit.setProfitStatus(0);
					
					profitList.add(profit);
				}
			}
			MerchantInfo nextMinfo=info1;//参加分润计算的下级用户
		
			for(int i=1;i<mInfos.size();i++){
	            String nextMerchantCode=nextMinfo.getMerchantCode();//下级的商户编码
				MerchantRate nextCurrentRate=rateMap.get(nextMerchantCode);//下级商户的费率
				Double nextIntoRate=nextCurrentRate.getIntoRate();//下级商户的进件费率
				Double nextTtradeRate=nextCurrentRate.getTradeRate();//下级商户的代理费率
				Double nextRate=nextIntoRate;//下级商户参加分润的费率
				if(nextIntoRate.doubleValue()!=nextTtradeRate.doubleValue())
					nextRate=nextTtradeRate;
				    
				MerchantInfo info=mInfos.get(i);
	            String selfMerchantCode=info.getMerchantCode();//本级的商户编码
				MerchantRate selfCurrentRate=rateMap.get(selfMerchantCode);//本级商户的费率
				Double selfIntoRate=selfCurrentRate.getIntoRate();//本级商户的进件费率
				Double selfTtradeRate=selfCurrentRate.getTradeRate();//本级商户的代理费率
				Double selfRate=selfIntoRate;//本级商户参加分润的费率
				if(selfIntoRate.doubleValue()!=selfTtradeRate.doubleValue())
					selfRate=selfTtradeRate;
				
				if(nextRate.doubleValue()>selfRate.doubleValue()){
					nextMinfo=info;//将当前用户作为参加分润的下级用户
					
					double profitRate=ComputeUtil.sub(nextRate, selfRate);//费率差
					profitRate=ComputeUtil.round(profitRate, 2);
					
					//订单总额
					Double totalOrderPrice =Double.parseDouble(amount);
					//计算用户所得分润金额
					double userProfitPrice=(totalOrderPrice==null?0:totalOrderPrice.doubleValue())*(profitRate/100);
					userProfitPrice=ComputeUtil.round(userProfitPrice, 2);
					
					if(userProfitPrice>=1){//分润大于等于一分的时候 才算分润
						PaymentProfit profit=new PaymentProfit();
						profit.setOrderNumber(orderNumber);
						profit.setTotalOrderPrice(totalOrderPrice);
						profit.setMerchantCode(merchantCode);
						profit.setUserProfitPrice(userProfitPrice);
						profit.setProfitUser(selfMerchantCode);
						profit.setIntoRate(selfIntoRate);
						profit.setTradeRate(selfTtradeRate);
						profit.setNextUser(nextMerchantCode);
						profit.setNextUserIntoRate(nextIntoRate);
						profit.setNextUserTradeRate(nextTtradeRate);
						profit.setProfitRate(profitRate);
						profit.setCreateTime(new Date());
						profit.setProfitStatus(0);
						
						profitList.add(profit);
					}
				}
			}
        }
		
		return profitList;
	}
	
	
	/**
	 * 检验入参
	 * @param user
	 * @return
	 */
	public String checkParam(Order order) {
		if(order ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(order.getMerchantCode())){
			return "用户商户号不能为空";
		}
		if(StringUtil.isEmpty(order.getAmount())){
			return "订单金额不能为空";
		}
		if(StringUtil.isEmpty(order.getOrderNumber())){
			return "订单号不能为空";
		}
		return "OK";
	}
	
	
	public PaymentProfitService getPaymentProfitService() {
		return paymentProfitService;
	}
	public void setPaymentProfitService(PaymentProfitService paymentProfitService) {
		this.paymentProfitService = paymentProfitService;
	}
	public MerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}
	public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}
	public MerchantRateService getMerchantRateService() {
		return merchantRateService;
	}
	public void setMerchantRateService(MerchantRateService merchantRateService) {
		this.merchantRateService = merchantRateService;
	}
}

package cn.seocoo.platform.job;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.DateUtils;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.MerchantProfitLog;
import cn.seocoo.platform.model.PaymentProfit;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantProfitLog.inf.MerchantProfitLogService;
import cn.seocoo.platform.service.paymentProfit.inf.PaymentProfitService;

/**
 * 计算上一天的交易分润
 */
public class CountDayProfit {
	private static final Logger logger = Logger.getLogger(CountDayProfit.class);

	private PaymentProfitService paymentProfitService;
	private MerchantInfoService merchantInfoService;
	private MerchantProfitLogService merchantProfitLogService;

	public void excute() {
		try {
			logger.info("==============计算上一天的交易分润  CountDayProfit start==================================");
			PaymentProfit paymentProfit = new PaymentProfit();
			List<PaymentProfit> profits = paymentProfitService.queryDayProfits(paymentProfit);
			if (profits != null && profits.size() > 0) {
				for (PaymentProfit profit : profits) {
					String merchantCode = profit.getProfitUser();//得到分润的用户
					Double userProfit = profit.getUserProfitPrice();
					logger.info("merchantCode====" + merchantCode + "=======userProfit=====" + userProfit);

					// 将当天的分润金额添加到对应商户信息中
					MerchantInfo minfo = new MerchantInfo();
					minfo.setMerchantCode(merchantCode);
					minfo.setCurrentTotalProfit(userProfit);
					merchantInfoService.updateMerchantInfoProfit(minfo);

					// 添加分润操作日志信息
					MerchantProfitLog profitLog = new MerchantProfitLog();
					profitLog.setMerchantCode(merchantCode);
					profitLog.setUpdateProfit(userProfit);// 新增的用户分润
					profitLog.setUpdateStyle(1);// 添加分润
					profitLog.setCreateTime(new Date());
					profitLog.setCountTime(DateUtils.parse(DateUtils.getPreDate(new Date())));// 需要计算分润的时间段（这里是指前一天的分润）
					merchantProfitLogService.saveMerchantProfitLog(profitLog);
				}
			}
			logger.info("==============计算上一天的交易分润  CountDayProfit end==================================");

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public MerchantProfitLogService getMerchantProfitLogService() {
		return merchantProfitLogService;
	}

	public void setMerchantProfitLogService(MerchantProfitLogService merchantProfitLogService) {
		this.merchantProfitLogService = merchantProfitLogService;
	}

}

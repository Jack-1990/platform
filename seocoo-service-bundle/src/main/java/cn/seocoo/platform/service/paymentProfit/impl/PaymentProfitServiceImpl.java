package cn.seocoo.platform.service.paymentProfit.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.PaymentProfit;
import cn.seocoo.platform.service.paymentProfit.inf.PaymentProfitService;
import cn.seocoo.platform.dao.paymentProfit.inf.PaymentProfitDao;

public class PaymentProfitServiceImpl  implements PaymentProfitService{

	  private PaymentProfitDao paymentProfitDao;

    public PaymentProfitDao getPaymentProfit() {
        return paymentProfitDao;
    }
    public void setPaymentProfitDao(PaymentProfitDao paymentProfitDao) {
         this.paymentProfitDao = paymentProfitDao;
    }

    @Override
    public PaymentProfit queryPaymentProfit(PaymentProfit paymentProfit){
        return paymentProfitDao.queryPaymentProfit(paymentProfit);
    }

    @Override
    public List<PaymentProfit> queryPaymentProfitList(PaymentProfit paymentProfit){
        return paymentProfitDao.queryPaymentProfitList(paymentProfit);
    }
    @Override
    public void savePaymentProfit(PaymentProfit paymentProfit){
          paymentProfitDao.savePaymentProfit(paymentProfit);
    }
    @Override
    public void updatePaymentProfit(PaymentProfit paymentProfit){
        paymentProfitDao.updatePaymentProfit(paymentProfit);
    }
    @Override
    public void deletePaymentProfit(PaymentProfit paymentProfit){
        paymentProfitDao.deletePaymentProfit(paymentProfit);
    }
    @Override
    public List<PaymentProfit> queryPaymentProfitPage(Map map){
        return paymentProfitDao.queryPaymentProfitPage(map);
    }
    @Override
    public Integer queryPaymentProfitPageCount(Map map){
        return paymentProfitDao.queryPaymentProfitPageCount(map);
    }
    
    @Override
    public Double queryTodayProfit(PaymentProfit paymentProfit){
    	 return paymentProfitDao.queryTodayProfit(paymentProfit);
    }
    
    @Override
    public Double queryYesterdayProfit(PaymentProfit paymentProfit){
    	return paymentProfitDao.queryYesterdayProfit(paymentProfit);
    }
    
    @Override
    public Double queryMonthProfit(PaymentProfit paymentProfit){
    	return  paymentProfitDao.queryMonthProfit(paymentProfit);
    }

	@Override
	public void savePaymentProfitList(List<PaymentProfit> paymentProfits) {
		if(paymentProfits!=null){
			for(PaymentProfit profit:paymentProfits){
				 paymentProfitDao.savePaymentProfit(profit);
			}
		}
	}
	@Override
	 public List<PaymentProfit> queryDayProfits(PaymentProfit paymentProfit){
		 return paymentProfitDao.queryDayProfits(paymentProfit);
	 }
	@Override
	public void updateProfitByOrderNumber(PaymentProfit paymentProfit){
		paymentProfitDao.updateProfitByOrderNumber(paymentProfit);
	}
	
}

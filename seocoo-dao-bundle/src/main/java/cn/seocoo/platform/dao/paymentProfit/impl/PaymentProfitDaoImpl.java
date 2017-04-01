package cn.seocoo.platform.dao.paymentProfit.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.PaymentProfit;
import cn.seocoo.platform.dao.paymentProfit.inf.PaymentProfitDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class PaymentProfitDaoImpl extends EntityManagerImpl<PaymentProfit, Integer> implements PaymentProfitDao{

    @Override
    public PaymentProfit queryPaymentProfit(PaymentProfit paymentProfit){
        return entityDao.findObj("PaymentProfit.queryPaymentProfit", paymentProfit);
    }

    @Override
    public List<PaymentProfit> queryPaymentProfitList(PaymentProfit paymentProfit){
        return entityDao.find("PaymentProfit.queryPaymentProfit", paymentProfit);
    }
    @Override
    public void savePaymentProfit(PaymentProfit paymentProfit){
         entityDao.save("PaymentProfit.savePaymentProfit", paymentProfit);
    }
    @Override
    public void updatePaymentProfit(PaymentProfit paymentProfit){
         entityDao.update("PaymentProfit.updatePaymentProfit", paymentProfit);
    }
    @Override
    public void deletePaymentProfit(PaymentProfit paymentProfit){
         entityDao.remove("PaymentProfit.deletePaymentProfit", paymentProfit);
    }
    @Override
    public List<PaymentProfit> queryPaymentProfitPage(Map map){
        return entityDao.find("PaymentProfit.queryPaymentProfitPage", map);
    }
    @Override
    public Integer queryPaymentProfitPageCount(Map map){
        return (Integer) entityDao.find("PaymentProfit.queryPaymentProfitPageCount", map).get(0);
    }
    @Override
    public Double queryTodayProfit(PaymentProfit paymentProfit){
    	return (Double) entityDao.find("PaymentProfit.queryTodayProfit", paymentProfit).get(0);
    }
    @Override
    public Double queryYesterdayProfit(PaymentProfit paymentProfit){
    	return (Double) entityDao.find("PaymentProfit.queryYesterdayProfit", paymentProfit).get(0);
    }
    @Override
    public Double queryMonthProfit(PaymentProfit paymentProfit){
    	return (Double) entityDao.find("PaymentProfit.queryMonthProfit", paymentProfit).get(0);
    }
    @Override
    public List<PaymentProfit> queryDayProfits(PaymentProfit paymentProfit){
    	  return entityDao.find("PaymentProfit.queryDayProfits", paymentProfit);
    }
    @Override
    public void updateProfitByOrderNumber(PaymentProfit paymentProfit){
    	entityDao.update("PaymentProfit.updateProfitByOrderNumber", paymentProfit);
    }
}

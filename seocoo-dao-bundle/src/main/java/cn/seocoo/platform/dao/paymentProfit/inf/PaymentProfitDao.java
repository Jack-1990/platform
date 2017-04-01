package cn.seocoo.platform.dao.paymentProfit.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.PaymentProfit;

public interface PaymentProfitDao {

    public PaymentProfit queryPaymentProfit(PaymentProfit paymentProfit);

    public List<PaymentProfit> queryPaymentProfitList(PaymentProfit paymentProfit);

    public void savePaymentProfit(PaymentProfit paymentProfit);

    public void updatePaymentProfit(PaymentProfit paymentProfit);

    public void deletePaymentProfit(PaymentProfit paymentProfit);

    public List<PaymentProfit> queryPaymentProfitPage(Map map);

    public Integer queryPaymentProfitPageCount(Map map);
    
    public Double queryTodayProfit(PaymentProfit paymentProfit);
    
    public Double queryYesterdayProfit(PaymentProfit paymentProfit);
    
    public Double queryMonthProfit(PaymentProfit paymentProfit);
    
    public List<PaymentProfit> queryDayProfits(PaymentProfit paymentProfit);
    
    public void updateProfitByOrderNumber(PaymentProfit paymentProfit);
}

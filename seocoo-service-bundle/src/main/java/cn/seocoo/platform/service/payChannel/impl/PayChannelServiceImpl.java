package cn.seocoo.platform.service.payChannel.impl;

import cn.seocoo.platform.dao.payChannel.inf.PayChannelDao;
import cn.seocoo.platform.model.PayChannel;
import cn.seocoo.platform.service.payChannel.inf.PayChannelService;

import java.util.List;
import java.util.Map;

public class PayChannelServiceImpl  implements PayChannelService{

	  private PayChannelDao payChannelDao;

    public PayChannelDao getPayChannel() {
        return payChannelDao;
    }
    public void setPayChannelDao(PayChannelDao payChannelDao) {
         this.payChannelDao = payChannelDao;
    }

    @Override
    public PayChannel queryPayChannel(PayChannel payChannel){
        return payChannelDao.queryPayChannel(payChannel);
    }

    @Override
    public List<PayChannel> queryPayChannelList(PayChannel payChannel){
        return payChannelDao.queryPayChannelList(payChannel);
    }
    @Override
    public void savePayChannel(PayChannel payChannel){
          payChannelDao.savePayChannel(payChannel);
    }
    @Override
    public void updatePayChannel(PayChannel payChannel){
        payChannelDao.updatePayChannel(payChannel);
    }
    @Override
    public void deletePayChannel(PayChannel payChannel){
        payChannelDao.deletePayChannel(payChannel);
    }
    @Override
    public List<PayChannel> queryPayChannelPage(Map map){
        return payChannelDao.queryPayChannelPage(map);
    }
    @Override
    public Integer queryPayChannelPageCount(Map map){
        return payChannelDao.queryPayChannelPageCount(map);
    }

	@Override
	public List<PayChannel> queryPayChannelByGroupCodes(Map map)
	{
		// TODO Auto-generated method stub
		return payChannelDao.queryPayChannelByGroupCodes(map);
	}

    @Override
    public List<PayChannel> queryPayChannelByMerchantCodes(Map map) {
        return payChannelDao.queryPayChannelByMerchantCodes(map);
    }
}

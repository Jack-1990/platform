package cn.seocoo.platform.dao.payChannel.impl;

import cn.seocoo.platform.dao.payChannel.inf.PayChannelDao;
import cn.seocoo.platform.model.PayChannel;
import com.tydic.framework.base.dao.EntityManagerImpl;

import java.util.List;
import java.util.Map;

public class PayChannelDaoImpl extends EntityManagerImpl<PayChannel, Integer> implements PayChannelDao{

    @Override
    public PayChannel queryPayChannel(PayChannel payChannel){
        return entityDao.findObj("PayChannel.queryPayChannel", payChannel);
    }

    @Override
    public List<PayChannel> queryPayChannelList(PayChannel payChannel){
        return entityDao.find("PayChannel.queryPayChannel", payChannel);
    }
    @Override
    public void savePayChannel(PayChannel payChannel){
         entityDao.save("PayChannel.savePayChannel", payChannel);
    }
    @Override
    public void updatePayChannel(PayChannel payChannel){
         entityDao.update("PayChannel.updatePayChannel", payChannel);
    }
    @Override
    public void deletePayChannel(PayChannel payChannel){
         entityDao.remove("PayChannel.deletePayChannel", payChannel);
    }
    @Override
    public List<PayChannel> queryPayChannelPage(Map map){
        return entityDao.find("PayChannel.queryPayChannelPage", map);
    }
    @Override
    public Integer queryPayChannelPageCount(Map map){
        return (Integer) entityDao.find("PayChannel.queryPayChannelPageCount", map).get(0);
    }

	@Override
	public List<PayChannel> queryPayChannelByGroupCodes(Map map)
	{
		// TODO Auto-generated method stub
		return entityDao.find("PayChannel.queryPayChannelByGroupCodes", map);
	}

    @Override
    public List<PayChannel> queryPayChannelByMerchantCodes(Map map) {
        return entityDao.find("PayChannel.queryPayChannelByMerchantCodes", map);
    }
}

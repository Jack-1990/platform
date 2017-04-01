package cn.seocoo.platform.service.payChannel.inf;

import cn.seocoo.platform.model.PayChannel;

import java.util.List;
import java.util.Map;

public interface PayChannelService {

    public PayChannel queryPayChannel(PayChannel payChannel);

    public List<PayChannel> queryPayChannelList(PayChannel payChannel);

    public void savePayChannel(PayChannel payChannel);

    public void updatePayChannel(PayChannel payChannel);

    public void deletePayChannel(PayChannel payChannel);

    public List<PayChannel> queryPayChannelPage(Map map);

    public Integer queryPayChannelPageCount(Map map);

	public List<PayChannel> queryPayChannelByGroupCodes(Map map);

	public List<PayChannel> queryPayChannelByMerchantCodes(Map map);
}

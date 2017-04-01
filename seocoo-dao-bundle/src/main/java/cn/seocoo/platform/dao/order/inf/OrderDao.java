package cn.seocoo.platform.dao.order.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.Order;

public interface OrderDao {

    public Order queryOrder(Order order);

    public List<Order> queryOrderList(Order order);

    public void saveOrder(Order order);

    public void updateOrder(Order order);

    public void deleteOrder(Order order);

    public List<Order> queryOrderPage(Map map);


    public Integer queryOrderPageCount(Map map);
    
    public void updateOrderByMno(Order order);
    
    public void updateTradeByMno(Order order);

	public List<Order> queryOrderByTime(Map map);
}

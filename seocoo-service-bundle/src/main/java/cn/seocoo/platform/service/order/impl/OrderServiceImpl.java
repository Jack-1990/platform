package cn.seocoo.platform.service.order.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.order.inf.OrderDao;
import cn.seocoo.platform.model.Order;
import cn.seocoo.platform.service.order.inf.OrderService;

public class OrderServiceImpl  implements OrderService{

	  private OrderDao orderDao;

    public OrderDao getOrder() {
        return orderDao;
    }
    public void setOrderDao(OrderDao orderDao) {
         this.orderDao = orderDao;
    }

    @Override
    public Order queryOrder(Order order){
        return orderDao.queryOrder(order);
    }

    @Override
    public List<Order> queryOrderList(Order order){
        return orderDao.queryOrderList(order);
    }
    @Override
    public void saveOrder(Order order){
          orderDao.saveOrder(order);
    }
    @Override
    public void updateOrder(Order order){
        orderDao.updateOrder(order);
    }
    @Override
    public void deleteOrder(Order order){
        orderDao.deleteOrder(order);
    }
    @Override
    public List<Order> queryOrderPage(Map map){
        return orderDao.queryOrderPage(map);
    }
    @Override
    public Integer queryOrderPageCount(Map map){
        return orderDao.queryOrderPageCount(map);
    }
    @Override
    public void updateOrderByMno(Order order){
    	orderDao.updateOrderByMno(order);
    }
    @Override
    public void updateTradeByMno(Order order){
    	orderDao.updateTradeByMno(order);
    }

	@Override
	public List<Order> queryOrderByTime(Map map)
	{
		// TODO Auto-generated method stub
		return orderDao.queryOrderByTime(map);
	}
}

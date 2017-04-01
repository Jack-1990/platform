package cn.seocoo.platform.dao.order.impl;

import java.util.List;
import java.util.Map;

import com.tydic.framework.base.dao.EntityManagerImpl;

import cn.seocoo.platform.dao.order.inf.OrderDao;
import cn.seocoo.platform.model.Order;

public class OrderDaoImpl extends EntityManagerImpl<Order, Integer> implements OrderDao{

    @Override
    public Order queryOrder(Order order){
        return entityDao.findObj("Order.queryOrder", order);
    }

    @Override
    public List<Order> queryOrderList(Order order){
        return entityDao.find("Order.queryOrder", order);
    }
    @Override
    public void saveOrder(Order order){
         entityDao.save("Order.saveOrder", order);
    }
    @Override
    public void updateOrder(Order order){
         entityDao.update("Order.updateOrder", order);
    }
    @Override
    public void updateOrderByMno(Order order){
         entityDao.update("Order.updateOrderByMno", order);
    }
  
    @Override
    public void  updateTradeByMno(Order order){
         entityDao.update("Order.updateTradeByMno", order);
    }
    @Override
    public void deleteOrder(Order order){
         entityDao.remove("Order.deleteOrder", order);
    }
    @Override
    public List<Order> queryOrderPage(Map map){
        return entityDao.find("Order.queryOrderPage", map);
    }
    @Override
    public Integer queryOrderPageCount(Map map){
        return (Integer) entityDao.find("Order.queryOrderPageCount", map).get(0);
    }

	@Override
	public List<Order> queryOrderByTime(Map map)
	{
		// TODO Auto-generated method stub
		return entityDao.find("Order.queryOrderByTime", map);
	}
}

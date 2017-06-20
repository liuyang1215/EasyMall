package com.ly.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ly.dao.OrderDao;
import com.ly.dao.ProductDao;
import com.ly.domain.Order;
import com.ly.domain.OrderInfo;
import com.ly.domain.OrderItem;
import com.ly.domain.Product;
import com.ly.domain.SaleInfo;
import com.ly.exception.MsgException;
import com.ly.factory.BasicFactory;
import com.ly.service.OrderService;
import com.ly.utils.DBUtils;
import com.ly.utils.TransactionManager;

public class OrderServiceImpl implements OrderService {
	private ProductDao productDao = BasicFactory.getFactory().getInstance(
			ProductDao.class);
	private OrderDao orderDao = BasicFactory.getFactory().getInstance(
			OrderDao.class);

	@Override
	public void addOrder(Order order, List<OrderItem> items) throws MsgException {
		try {
			//获取开始事务连接
//			TransactionManager.startTran();
			// 1.向orders表中添加一条记录
			orderDao.addOrder(order);
			// 2、循环添加订单项
			for (OrderItem item : items) {
				// 4.1根据商品id查询该商品信息（用来比较库存是否充足）
				Product prod = productDao.findProdById(item.getProduct_id());
				if (prod.getPnum() >= item.getBuynum()) {// 库存充足
					// 从库存中减去本次购买的数量
					productDao.changePnum(prod.getId(),
							prod.getPnum() - item.getBuynum());
					// 将订单项添加到数据库中
					orderDao.addOrderItem(item);
				} else {// 库存不足
					throw new MsgException("商品库存不足！商品id:" + prod.getId()
							+ ",商品名：" + prod.getName());
				}
			}
//			TransactionManager.commitTran();
		} catch (MsgException me) {
//			TransactionManager.rollbackTran();
			throw me;
		} catch (Exception e) {
			e.printStackTrace();
//			TransactionManager.rollbackTran();
		}finally{
//			TransactionManager.release();
		}

	}

	//查询订单老版本
	/*@Override
	public List<OrderInfo> getOrderInfosByUserId(int user_id) {
		List<OrderInfo> returnList = new ArrayList<OrderInfo>();
		// 1、获取当前用户的所有order信息(查询orders表)
		List<Order> orderList = orderDao.findOrdersByUserId(user_id);
		// 2、遍历orderList
		if (orderList != null) {
			for (Order order : orderList) {
				// --创建OrderInfo对象
				OrderInfo orderInfo = new OrderInfo();
				// 将当前订单信息保存到orderInfo中
				orderInfo.setOrder(order);
				Map<Product, Integer> map = new HashMap<Product, Integer>();
				// 查询该订单对应的所有订单项信息
				List<OrderItem> itemList = orderDao.findOrderItemsByOrderId(order.getId());
				if (itemList != null) {
					for (OrderItem item : itemList) {
						Product prod = productDao.findProdById(item.getProduct_id());
						map.put(prod, item.getBuynum());
					}
				}
				// 将map保存到orderInfo
				orderInfo.setMap(map);
				// 将封装信息后orderInfo保存到returnList中
				returnList.add(orderInfo);
			}
		}
		return returnList;
	}*/
	
	//优化版订单查询
	@Override
	public List<OrderInfo> getOrderInfosByUserId(int user_id) {
		//返回当前用户下所有的订单信息（包含订单信息和订单条目信息）
		List<OrderInfo> orderinfoList = new ArrayList<OrderInfo>();
		//返回当前用户下所有订单信息
		List<Order> orderList = orderDao.findOrdersByUserId(user_id);
		//返回当前用户下所有订单条目信息
		List<OrderItem> allOrderitemList = orderDao.findOrderItemsByUserid(user_id);
		for(Order o : orderList){
			OrderInfo info = new OrderInfo();
			info.setOrder(o);
			List<OrderItem> itemList = new ArrayList<OrderItem>();
			for(OrderItem item : allOrderitemList){
				if(item.getOrder_id().equals( o.getId())){
					itemList.add(item);
				}
			}
			info.setOrderItem(itemList);
			orderinfoList.add(info);
		}
		
		return orderinfoList;
	}

	

	@Override
	public void delOrder(String oid) throws MsgException {
		// 1业务规则：只有未支付订单可以删除
		Order order = orderDao.findOrderById(oid);
		if (order.getPaystate() != 0) {
			throw new MsgException("只有未支付的订单可以删除！");
		}
		// 2、将购买的商品数量加回去
		List<OrderItem> itemList = orderDao.findOrderItemsByOrderId(oid);
		if (itemList != null) {
			for (OrderItem item : itemList) {
				Product prod = productDao.findProdById(item.getProduct_id());
				productDao.changePnum(item.getProduct_id(), item.getBuynum() + prod.getPnum());
			}
			// 3、删除该订单对应的订单项
			orderDao.delOrderItemsByOrderId(oid);
		}
		// 4、删除订单
		orderDao.delOrderByOrderId(oid);

	}

	@Override
	public double getMoneyByOrderId(String oid) {
		return orderDao.findOrderById(oid).getMoney();
	}

	@Override
	public void updatePayState(String order_id, int paystate) {
		int state = orderDao.findPayStateByOrderId(order_id);
		if(state==0){
			orderDao.updatePayState(order_id,paystate);
		}
	}

	@Override
	public List<SaleInfo> findAllSales() {
		return orderDao.findAllSales();
	}


}

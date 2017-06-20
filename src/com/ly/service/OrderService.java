package com.ly.service;

import java.util.List;

import com.ly.annotation.Tran;
import com.ly.dao.Dao;
import com.ly.domain.Order;
import com.ly.domain.OrderInfo;
import com.ly.domain.OrderItem;
import com.ly.domain.SaleInfo;
import com.ly.exception.MsgException;

public interface OrderService extends Service {
	/**
	 * 添加订单的方法
	 * @param order：封装订单信息的Order对象
	 * @param items：封装订单项的List<OrderItem>
	 */
	@Tran
	void addOrder(Order order, List<OrderItem> items) throws MsgException;
	/**
	 * 根据用户id查询当前用户所有的订单信息
	 * @param user_id：用户id
	 * @return  该用户所有的额订单信息
	 */
	List<OrderInfo> getOrderInfosByUserId(int user_id);
	/**
	 * 根据订单id删除该订单对应的信息以及该订单购买的商品数量加回去
	 * @param oid订单id
	 */
	@Tran
	void delOrder(String oid) throws MsgException;
	/**
	 * 根据订单id查询订单金额
	 * @param oid
	 * @return 该订单金额
	 */
	double getMoneyByOrderId(String oid);
	/**
	 * 修改订单的支付状态
	 * @param order_id：订单id
	 * @param paystate：付款状态  0：表示未付款，1表示已付款  
	 */
	@Tran
	void updatePayState(String order_id, int paystate);
	/**
	 * 查询销售榜单
	 * @return 全部的商品销售信息
	 */
	List<SaleInfo> findAllSales();
	

}

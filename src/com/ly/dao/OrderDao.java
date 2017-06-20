package com.ly.dao;

import java.util.List;

import com.ly.domain.Order;
import com.ly.domain.OrderItem;
import com.ly.domain.SaleInfo;

public interface OrderDao extends Dao{
	public void addOrder(Order order) ;

	public void addOrderItem(OrderItem item);
	/**
	 * 根据用户id查询该用户所有的订单信息
	 * @param user_id用户id
	 * @return 该用户所有的订单信息（orders）
	 */
	public List<Order> findOrdersByUserId(int user_id);
	/**
	 * 根据订单id查询该订单下所有的订单项信息
	 * @param order_id：订单id
	 * @return 该订单下所有的订单项信息
	 */
	public List<OrderItem> findOrderItemsByOrderId(String order_id);
	/**
	 * 根据订单id查询订单信息
	 * @param oid：订单id
	 * @return  该订单id对应的订单信息
	 */
	public Order findOrderById(String oid);
	/**
	 * 根据订单id删除该订单下所有的订单项
	 * @param oid：订单id
	 */
	public void delOrderItemsByOrderId(String oid);
	/**
	 * 根据订单id删除该订单在orders表中的信息
	 * @param oid：订单id
	 */
	public void delOrderByOrderId(String oid);
	/**
	 * 根据订单id查询该订单的支付状态（悲观锁版本）
	 * @param order_id：订单id
	 * @return  该订单的支付状态  0表示未支付，1表示已支付
	 */
	public int findPayStateByOrderId(String order_id);
	/**
	 *修改订单的支付状态
	 * @param order_id订单id
	 * @param state支付状态    0表示未支付，1表示已支付
	 */
	public void updatePayState(String order_id, int state);
	/**
	 * 查询销售榜单
	 * @return 全部的商品销售信息
	 */
	public List<SaleInfo> findAllSales();
	
	
	public List<OrderItem> findOrderItemsByUserid(int user_id);
}

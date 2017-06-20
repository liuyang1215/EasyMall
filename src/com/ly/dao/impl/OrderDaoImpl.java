package com.ly.dao.impl;

import java.util.List;

import com.ly.dao.OrderDao;
import com.ly.domain.Order;
import com.ly.domain.OrderItem;
import com.ly.domain.SaleInfo;
import com.ly.utils.BeanHandler;
import com.ly.utils.BeanListHandler;
import com.ly.utils.DBUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void addOrder(Order order) {
		String sql= "insert into orders(id,money,receiverinfo,paystate,ordertime,user_id) values(?,?,?,?,?,?)";
		DBUtils.update(sql, order.getId(),order.getMoney(),order.getReceiverinfo(),order.getPaystate(),order.getOrdertime(),order.getUser_id());
	}

	@Override
	public void addOrderItem(OrderItem item) {
		String sql = "insert into orderitem(order_id,product_id,buynum) values(?,?,?)";
		DBUtils.update(sql, item.getOrder_id(),item.getProduct_id(),item.getBuynum());

	}

	@Override
	public List<Order> findOrdersByUserId(int user_id) {
		String sql = "select * from orders where user_id=?";
		return DBUtils.query(sql, new BeanListHandler<Order>(Order.class), user_id);
	}

	@Override
	public List<OrderItem> findOrderItemsByOrderId(String order_id) {
		String sql = "select * from orderitem where order_id = ?";
		return DBUtils.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), order_id);
	}

	@Override
	public Order findOrderById(String oid) {
		String sql = "select * from orders where id=?";
		Order order = DBUtils.query(sql, new BeanHandler<Order>(Order.class), oid);
		return order;
	}

	@Override
	public void delOrderItemsByOrderId(String oid) {
		String sql = "delete from orderitem where order_id=?";
		DBUtils.update(sql, oid);
	}

	@Override
	public void delOrderByOrderId(String oid) {
		String sql = "delete from orders where id = ?";
		DBUtils.update(sql, oid);
	}

	@Override
	public int findPayStateByOrderId(String order_id) {
		String sql = "select * from orders where id =? for update";
		return DBUtils.query(sql, new BeanHandler<Order>(Order.class), order_id).getPaystate();
	}

	@Override
	public void updatePayState(String order_id, int state) {
		String sql = "update orders set paystate=? where id=?";
		DBUtils.update(sql, state,order_id);
	}

	@Override
	public List<SaleInfo> findAllSales() {
		//首先找出所有订单中的商品，然后排除未付款商品，再计算商品卖出数量总和再按降序排列
		String sql = "select pd.id prod_id,pd.name prod_name,sum(oi.buynum) sale_num "+
							"from products pd,orders od,orderitem oi "+
							"where oi.order_id = od.id "+
							"and oi.product_id = pd.id "+
							"and od.paystate =1 "+
							"group by prod_id "+
							"order by sale_num  desc";
		return DBUtils.query(sql, new BeanListHandler<SaleInfo>(SaleInfo.class));
	}

	@Override
	public List<OrderItem> findOrderItemsByUserid(int user_id) {
		String sql = "SELECT oi.*,pd.imgurl,pd.name,pd.price FROM orders od,orderitem oi,products pd WHERE od.id=oi.order_id AND oi.product_id=pd.id AND od.user_id=?";
		return DBUtils.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), user_id);
	}

}

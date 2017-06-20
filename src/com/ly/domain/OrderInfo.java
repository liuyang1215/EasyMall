package com.ly.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OrderInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Order order;
//	private Map<Product,Integer> map;
	private List<OrderItem> orderItem;
	
	public List<OrderItem> getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(List<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
/*	public Map<Product, Integer> getMap() {
		return map;
	}
	public void setMap(Map<Product, Integer> map) {
		this.map = map;
	}*/
}

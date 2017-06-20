package com.ly.listener;

import java.util.HashMap;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ly.domain.Product;

public class CartSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("cartListener运行了！！！！！！！！");
		se.getSession().setAttribute("cart", new HashMap<Product,Integer>());

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("监听器被销毁");
		se.getSession().removeAttribute("cart");
	}

}

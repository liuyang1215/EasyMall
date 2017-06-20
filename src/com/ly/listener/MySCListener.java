package com.ly.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MySCListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
//		System.out.println("监听器被创建");
		sce.getServletContext().setAttribute("app",
				sce.getServletContext().getContextPath());


	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
//		System.out.println("监听器被销毁");
		sce.getServletContext().removeAttribute("app");
	}

}

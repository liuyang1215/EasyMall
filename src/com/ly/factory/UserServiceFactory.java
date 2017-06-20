package com.ly.factory;

import com.ly.service.UserService;
import com.ly.service.impl.UserServiceImpl;
import com.ly.utils.PropUtils;

public class UserServiceFactory {
	private static UserServiceFactory factory = new UserServiceFactory();
	private UserServiceFactory(){};
	public static UserServiceFactory getFactory(){
		return factory;
	}
	public static UserService getUserService(){
		String userServiceImplStr = PropUtils.getProp("UserService");
		Class clz = null;
		try {
			clz = Class.forName(userServiceImplStr);
			return (UserServiceImpl)clz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

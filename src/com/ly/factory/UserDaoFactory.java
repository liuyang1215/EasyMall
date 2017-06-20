package com.ly.factory;


import com.ly.dao.UserDao;
import com.ly.dao.impl.UserDaoImpl;
import com.ly.utils.PropUtils;

public class UserDaoFactory {
	
	private static UserDaoFactory factory = new UserDaoFactory();
	private UserDaoFactory(){};
	public static UserDaoFactory getFactory(){
		return factory;
	}
	public static UserDao getUserDao(){
		/*Properties prop = new Properties();
		prop.load(inStream);*/
		//如果这么设计的话，每次调用该方法都会调用一次配置文件的加载，效率很低。
		String userDaoImplStr = PropUtils.getProp("UserDao");
		Class clz=null;
		try {
			clz = Class.forName(userDaoImplStr);
			return (UserDaoImpl)clz.newInstance();
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

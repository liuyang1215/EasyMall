package com.ly.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanHandler<T> implements ResultSetHandler<T> {
	private Class<T> type;
	public BeanHandler(Class<T> type){
		this.type = type;
	}
	
	@Override
	public T handler(ResultSet rs) throws SQLException {
		// 1. rs是否存在一行结果
		if(rs.next()){
			//2. 创建T对应的对象
			try {
				T t = type.newInstance();
				//3.将结果集rs中的信息->t对象
				BeanInfo bi = Introspector.getBeanInfo(type);
				PropertyDescriptor[] pds = bi.getPropertyDescriptors();
				//将t的所有setXXX方法执行一遍
				for(PropertyDescriptor pd : pds){
					//获取属性的名称
					String pname = pd.getName();
					Method method = pd.getWriteMethod();
					try {//处理从Object继承的属性，例如会去找class列，排除掉不需要的列
						method.invoke(t, rs.getObject(pname));						
					} catch (SQLException e) {
						continue;
					}
				}
				
				return t;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}

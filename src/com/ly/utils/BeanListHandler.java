package com.ly.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeanListHandler<T> implements ResultSetHandler<List<T>> {
	private Class<T> clz;
	public BeanListHandler(Class<T> clz){
		this.clz = clz;
	}
	public List<T> handler(ResultSet rs) throws Exception {
		List<T> list = new ArrayList<T>();
		while(rs.next()){
			T t = clz.newInstance();
			//将结果集中的信息封装到bean对象中
			BeanInfo bi = Introspector.getBeanInfo(clz);
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				//获取属性名称
				String name = pd.getName();//username,nickname
				//获取该属性对应的setter方法
				Method mtd = pd.getWriteMethod();
				Class type = pd.getPropertyType();
				try{//排除实体类中存在的属性在对应的表中找不到对应的列rs.getString("password")
//					System.out.println(type);
					Object obj = null;
					if(type==Integer.TYPE){
						System.out.println("Integer.TYPE:"+Integer.TYPE.getName());
						System.out.println("BigDecimal Type:"+type);
						obj = rs.getInt(name);
					}else{
						obj = rs.getObject(name);
					}
				    mtd.invoke(t, obj);
				}catch (SQLException e) {
					continue;
				}
			}
			//将封装了一条信息bean对象添加到list集合中
//			System.out.println("=======>"+t);
			list.add(t);
		}
		//return list;
		return list.size()==0?null:list;
	}

}

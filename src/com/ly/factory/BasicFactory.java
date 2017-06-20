package com.ly.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.sound.midi.Transmitter;

import com.ly.annotation.Tran;
import com.ly.dao.Dao;
import com.ly.service.Service;
import com.ly.utils.PropUtils;
import com.ly.utils.TransactionManager;

public class BasicFactory {
	private static BasicFactory factory = new BasicFactory();
	private BasicFactory(){};
	public static BasicFactory getFactory(){
		return factory;
	}
	public <T> T  getInstance(Class<T> interfaceClz){
		if(Service.class.isAssignableFrom(interfaceClz)){//Service层
			//读取配置文件config.properties
			String implStr = PropUtils.getProp(interfaceClz.getSimpleName());
			//获取全路径名
			Class clz = null;
			try {
				clz = Class.forName(implStr);
				//获取委托类的对象
				final T t  = (T)clz.newInstance();
				//创建代理类对象
				@SuppressWarnings("unchecked")
				T proxy = (T)Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(),new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						if(method.isAnnotationPresent(Tran.class)){//使用了事务
							try {
								//记录日志
								//细粒度的权限控制
								//控制事务
								TransactionManager.startTran();
								Object resultObject = method.invoke(t, args);
								TransactionManager.commitTran();
								return resultObject;
							}catch(InvocationTargetException ite){
								TransactionManager.rollbackTran();//回滚事务
								ite.getTargetException().printStackTrace();
								//处理自定义异常
								throw ite.getTargetException();
							} catch (Exception e) {
								TransactionManager.rollbackTran();
								e.printStackTrace();
								throw new RuntimeException(e);
							}finally{
								TransactionManager.release();
							}
						}else{//当前业务层方法没有使用事务注解，不需要控制事务
							try{
								return method.invoke(t, args);
                            }catch(InvocationTargetException ite){
                            	ite.getTargetException().printStackTrace();
								//处理自定义异常
								throw ite.getTargetException();
							}catch(Exception e){
								e.printStackTrace();
								throw new RuntimeException(e);
							}finally{
								TransactionManager.release();//为了解决不使用事务时，数据库连接释放的问题
							}
						}
					}
				});
				
				return proxy;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}else if(Dao.class.isAssignableFrom(interfaceClz)){//DAO层
			//读取配置文件config.properties
			String implStr = PropUtils.getProp(interfaceClz.getSimpleName());
			//获取全路径名
			Class clz = null;
			try {
				clz = Class.forName(implStr);
				return (T)clz.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}else{
			throw new RuntimeException("别乱来，只能获取DAO类或Service层的实现类对象哦~");
		}
		return null;
	}
}

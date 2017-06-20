package com.ly.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtils {
	private static ComboPooledDataSource source = new ComboPooledDataSource();
	private DBUtils(){}
	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConn(){
		try {
			if(source == null){
				source = new ComboPooledDataSource();
			}
			return source.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 实现查询
	 * @param sql：sql语句
	 * @param rsh：ResultSetHandler实现类的对象：通常使用BeanHandler和BeanListHandler
	 * @param params:查询条件用到的值（不定个数的参数写法）
	 * @return 
	 * @throws SQLException
	 */
	public static <T> T query(String sql, ResultSetHandler<T> rsh, Object... params){
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try{
			conn = TransactionManager.getConn();
			pstat = conn.prepareStatement(sql);
			//为占位符赋值
			for(int i=1;i<=params.length;i++){
				pstat.setObject(i, params[i-1]);
			}
			//执行查询
			rs = pstat.executeQuery();
			return rsh.handler(rs);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(null,pstat,rs);
		}
		return null;
	}
	
	/**
	 * 实现添加，修改，删除操作
	 * @param sql：sql语句
	 * @param params：保存参数值得数组（不定个数）
	 * @return 影响行数
	 * @throws SQLException
	 */
	public static int update(String sql, Object... params){
		Connection conn = null;
		PreparedStatement pstat = null;
		try{
			conn = TransactionManager.getConn();
			pstat = conn.prepareStatement(sql);
			//为占位符赋值
			for(int i=1;i<=params.length;i++){
				pstat.setObject(i, params[i-1]);
			}
			//执行查询
			return pstat.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(null, pstat, null);//切记不能在此关闭Transaction出传来的Connection
		}
		return 0;
	}
	
	/**
	 * 关闭资源的方法
	 * @param conn
	 * @param stat
	 * @param rs
	 */
	public static void close(Connection conn, Statement stat, ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				rs = null;
			}
		}
		if(stat != null){
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				stat = null;
			}
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				conn = null;
			}
		}
	}

}

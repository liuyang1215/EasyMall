package com.ly.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>() {
		protected Connection initialValue() {
			return DBUtils.getConn();
		};
	};

	private TransactionManager() {}

	public static Connection getConn() {
		return tl.get();
	}

	// 开启事务
	public static void startTran() {
		try {
			tl.get().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 提交事务
	public static void commitTran() {
		try {
			tl.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 回滚事务
	public static void rollbackTran() {
		try {
			tl.get().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 关闭数据库连接
	public static void release() {
		try {
			tl.get().close();
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

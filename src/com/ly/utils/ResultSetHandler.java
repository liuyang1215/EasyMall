package com.ly.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler<T> {

	/**
	 * 从
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	T handler(ResultSet rs) throws Exception;
}

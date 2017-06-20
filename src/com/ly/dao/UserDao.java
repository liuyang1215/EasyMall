package com.ly.dao;


import java.util.List;

import com.ly.domain.User;

public interface UserDao extends Dao{
	/**
	 * 保存用户信息到数据库
	 * @param user
	 */
	public void addUser(User user);
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public User findUserByUsername(String username);
	
	/**
	 * 根据用户名和密码查询用户信息
	 * @param username
	 * @param password
	 * @return
	 */
	public User findUserByUsernameAndPassword(String username, String password);
	
	/**
	 * 模糊查询用户名 
	 * @param key
	 * @return
	 */
	public List<User> findAllByKey(String key);
}

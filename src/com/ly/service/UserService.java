package com.ly.service;

import com.ly.domain.User;

public interface UserService extends Service{
	/**
	 * 注册用户
	 * 
	 * @param user
	 */
	public void registUser(User user);
	
	/**
	 * 根据用户名和密码查询用户信息
	 * 
	 * @param username
	 * @param password
	 */
	public User login(String username, String password);
}

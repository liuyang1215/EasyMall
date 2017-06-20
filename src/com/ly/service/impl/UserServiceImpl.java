package com.ly.service.impl;

import com.ly.dao.UserDao;
import com.ly.domain.User;
import com.ly.exception.MsgException;
import com.ly.factory.BasicFactory;
import com.ly.service.UserService;

public class UserServiceImpl implements UserService {
	UserDao dao = BasicFactory.getFactory().getInstance(UserDao.class);

	/**
	 * 注册用户
	 * 
	 * @param user
	 */
	public void registUser(User user) {
		// 检查用户名是否存在
		if (dao.findUserByUsername(user.getUsername()) != null) {// 用户名已存在
			throw new MsgException("用户名已存在");
		}

		// 注册用户
		dao.addUser(user);
	}

	/**
	 * 根据用户名和密码查询用户信息
	 * 
	 * @param username
	 * @param password
	 */
	public User login(String username, String password) {
		return dao.findUserByUsernameAndPassword(username, password);
	}
	
	public static void main(String[] args) {
		System.out.println(1);
		new UserServiceImpl();
		System.out.println(2);
	}
}

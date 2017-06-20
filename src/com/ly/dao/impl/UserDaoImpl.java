package com.ly.dao.impl;

import java.util.List;

import com.ly.dao.UserDao;
import com.ly.domain.User;
import com.ly.utils.BeanHandler;
import com.ly.utils.BeanListHandler;
import com.ly.utils.DBUtils;

public class UserDaoImpl implements UserDao {
	/**
	 * 保存用户信息到数据库
	 * @param user
	 */
	public void addUser(User user) {
		String sql = "insert into user(username,password,nickname,email) "
				       + " values(?,?,?,?)";
		DBUtils.update(sql, user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail());
		/*//创建QueryRunner对象
		QueryRunner runner = new QueryRunner(DBUtils.getSource());
		try {
			runner.update(sql, user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail());
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
	
	/*public void addUser_old(User user) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			ps = conn.prepareStatement("insert into user values(null, ?, ?, ?, ?)");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getNickname());
			ps.setString(4, user.getEmail());
			int rows = ps.executeUpdate();
			if(rows<=0){
				throw new MsgException("注册失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			DBUtils.close(conn, ps, rs);
		}
	}*/
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public User findUserByUsername(String username) {
		String sql = "select * from user where username=?";
		return DBUtils.query(sql,new BeanHandler<User>(User.class) ,username);
		/*QueryRunner runner = new QueryRunner(DBUtils.getSource());
		try {
			return runner.query(sql, new BeanHandler<User>(User.class),username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;*/
	}
	
	/*
	public User findUserByUsername_old(String username) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			ps = conn.prepareStatement("select * from user where username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			User user = null;
			if(rs.next()){
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setNickname(rs.getString("nickname"));
				user.setEmail(rs.getString("email"));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			DBUtils.close(conn, ps, rs);
		}*/
		
		
	

	/**
	 * 根据用户名和密码查询用户信息
	 * @param username
	 * @param password
	 * @return
	 */
	public User findUserByUsernameAndPassword(String username, String password) {
		String sql = "select * from user where username=? and password=?";
		return DBUtils.query(sql, new BeanHandler<User>(User.class),username,password);
		/*QueryRunner runner = new QueryRunner(DBUtils.getSource());
		try {
			return runner.query(sql, new BeanHandler<User>(User.class),username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;*/
	}

	@Override
	public List<User> findAllByKey(String key) {
		String sql = "select * from user where username like ?";
		return DBUtils.query(sql, new BeanListHandler<User>(User.class),"%"+key+"%");
		
	}
	public static void main(String[] args) {
		UserDao dao = new UserDaoImpl();
		List<User> users = dao.findAllByKey("李");
		for(User u : users){
			System.out.println(u.getUsername());
		}
	}
}

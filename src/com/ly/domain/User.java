package com.ly.domain;

import java.io.Serializable;

import com.ly.exception.MsgException;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String username;
	private String password;
	private String nickname;
	private String email;
	private String role;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 校验数据
	 */
	public void checkData() {
		if(username == null || "".equals(username)){
			throw new MsgException("用户名不能为空");
		}
		if(password == null || "".equals(password)){
			throw new MsgException("密码不能为空");
		}
		if(nickname == null || "".equals(nickname)){
			throw new MsgException("昵称不能为空");
		}
		if(email == null || "".equals(email)){
			throw new MsgException("邮箱不能为空");
		}
		//>邮箱格式校验
		String reg = "^\\w+@\\w+(\\.\\w+)+$";
		if(!email.matches(reg)){
			throw new MsgException("邮箱格式不正确");
		}
	}
	
}

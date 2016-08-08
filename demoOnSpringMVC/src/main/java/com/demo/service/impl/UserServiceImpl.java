package com.demo.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.mapper.UserInfoMapper;
import com.demo.model.UserInfo;
import com.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	public UserInfo getUserById(int id) {
		return userInfoMapper.selectByPrimaryKey(id);
	}

	public List<UserInfo> getUsers() {
		return userInfoMapper.selectAll();
	}

	public int insert(UserInfo userInfo) {
		int result = userInfoMapper.insert(userInfo);
		System.out.println(result);
		return result;
	}

}
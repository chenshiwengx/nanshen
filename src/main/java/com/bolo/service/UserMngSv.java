package com.bolo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolo.dao.UserMapper;
import com.bolo.entity.NanshenUser;
@Service
public class UserMngSv {
	@Autowired
	private UserMapper userMapper ;  
	  	
	public NanshenUser queryUserById(int id) {  
	    return userMapper.queryUserById(id);  
	}
}

package com.bolo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolo.dao.UserServiceMapper;
import com.bolo.entity.User1;
@Service
public class UserServiceImp {
	 @Autowired
	private UserServiceMapper userMapper ;  
	  
	public void setUserMapper(UserServiceMapper userMapper) {
		this.userMapper = userMapper;
	}  
    public User1 queryStudentById(String id) {  
        return userMapper.queryUserByIds(id);  
    }
}

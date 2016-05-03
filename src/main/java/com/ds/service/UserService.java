package com.ds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ds.entity.User;
import com.ds.repository.UserDao;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public void registerUser(User user){
		if(user == null) return;
		userDao.save(user);
	}
	
	public User findOneUser(String username){
		User user = userDao.findByName(username);
		return user;
	}
}

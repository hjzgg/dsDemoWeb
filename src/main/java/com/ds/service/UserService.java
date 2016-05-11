package com.ds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.entity.User;
import com.ds.repository.UserDao;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Transactional
	public void registerUser(User user){
		if(user == null) return;
		userDao.save(user);
	}
	
	@Transactional
	public User findOneUser(String username){
		User user = userDao.findByName(username);
		return user;
	}
}

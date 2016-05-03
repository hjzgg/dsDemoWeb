package com.ds.controller;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ds.entity.User;
import com.ds.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@RequestMapping(value="userLogin")
	@ResponseBody
	public String userLogin(String username, String password){
		JSONObject jsono = new JSONObject();
		User user = service.findOneUser(username);
		if(user != null && user.getPassword().equals(password)){
			jsono.put("success", true);
			jsono.put("message", "login");
		} else {
			jsono.put("success", false);
			if(user == null)
				jsono.put("message", "username");
			else 
				jsono.put("message", "password");
		}
		return jsono.toString();
	}
	
	@RequestMapping(value="userRegister")
	@ResponseBody
	public String userRegister(String username, String password){
		JSONObject jsono = new JSONObject();
		User user = service.findOneUser(username);
		if(user == null) {
			jsono.put("success", true);
			jsono.put("message", "register");
			user = new User();
			user.setUsername(username);
			user.setPassword(password);
		} else {//用户已经存在
			jsono.put("success", false);
			jsono.put("message", "user is exist!");
		}
		return jsono.toString();
	}
}

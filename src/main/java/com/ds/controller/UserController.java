package com.ds.controller;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ds.entity.User;
import com.ds.service.UserService;
import com.ds.utils.DesUtil;
import com.ds.utils.PasswordHelper;

@Controller
public class UserController {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value="userLogin")
	@ResponseBody
	public String userLogin(String username, String password){
		JSONObject jsono = new JSONObject();
		try{
			User user = service.findOneUser(username);
			PasswordHelper ph = new PasswordHelper();
			String newPassword = ph.getEncryptPassword(password);
			if(user != null && user.getPassword().equals(newPassword)){
				String userToken = DesUtil.strEnc(username+password, "1", "2", "3");
				redisTemplate.opsForValue().set(userToken, user);
				jsono.put("success", true);
				jsono.put("userToken", userToken);//将加密过的userToken返回给客户端
				jsono.put("message", "login");
			} else {
				jsono.put("success", false);
				if(user == null)
					jsono.put("message", "username");
				else 
					jsono.put("message", "password");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			jsono.put("success", false);
			jsono.put("message", "inner error.");
		}
		return jsono.toString();
	}
	
	@RequestMapping(value="userRegister")
	@ResponseBody
	public String userRegister(String username, String password){
		JSONObject jsono = new JSONObject();
		try{
			if(username == null || password == null){
				jsono.put("success", false);
				jsono.put("message", "username or password is null.");
				return jsono.toString();
			}
			User user = service.findOneUser(username);
			if(user == null) {
				jsono.put("success", true);
				jsono.put("message", "register");
				user = new User();
				user.setUsername(username);
				//使用shiro的密码加密算法对密码加密
				PasswordHelper ph = new PasswordHelper();
				String newPassword = ph.getEncryptPassword(password);
				user.setPassword(newPassword);
				service.registerUser(user);
			} else {//用户已经存在
				jsono.put("success", false);
				jsono.put("message", "user is exist!");
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			jsono.put("success", false);
			jsono.put("message", "inner error.");
		}
		return jsono.toString();
	}
}

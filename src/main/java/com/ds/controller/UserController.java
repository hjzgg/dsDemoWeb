package com.ds.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;
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
import com.ds.utils.SerializeUtil;
import com.ds.utils.ValidateCode;

@Controller
public class UserController {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value="userLogin")
	@ResponseBody
	public String userLogin(String username, String password, String code, String codeAuth){
		JSONObject jsono = new JSONObject();
		try{
			if(codeAuth != null) {
				//通过key在redis找到后台生成的验证码
				String vcode = (String) redisTemplate.opsForValue().get(codeAuth);
				redisTemplate.delete(codeAuth);
				if(vcode!= null && !vcode.equalsIgnoreCase(code)){
					jsono.put("success", false);
					jsono.put("message", "code is error.");
					return jsono.toString();
				} 
			} else {
				jsono.put("success", false);
				jsono.put("message", "code should require again.");
				return jsono.toString();
			}
			
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
					jsono.put("message", "username is error.");
				else 
					jsono.put("message", "password is error.");
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
	public String userRegister(String username, String password, String email){
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
				user.setEmail(email);
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
	
	@RequestMapping(value="loginCodeImage")
	public void getCodeImage(String codeAuth, HttpServletResponse response){
		if(codeAuth == null) return;
		String randomCode = (String) redisTemplate.opsForValue().get(codeAuth);
		if(randomCode == null) return;
		ValidateCode vCode = (ValidateCode)SerializeUtil.unserialize((byte[])redisTemplate.opsForValue().get(codeAuth+"ValidateCode"));
		//产生图片
		vCode.createCode(randomCode);
		if(vCode == null) return;
		// 设置响应的类型格式为图片格式  
        response.setContentType("image/jpeg");  
        //禁止图像缓存。  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        try {
			vCode.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	@RequestMapping(value="loginCode")
	@ResponseBody
	public String getCode(){
		PrintWriter out = null;
		JSONObject jsono = new JSONObject();
		try {
			ValidateCode vCode = new ValidateCode(55,25,4,80);
			String randomCode = vCode.randomCode();
			String encCode = DesUtil.strEnc(randomCode+System.currentTimeMillis(), "1", "2", "3");
			//存储验证码字符串,过期时间为1分钟
			redisTemplate.opsForValue().set(encCode, randomCode);
			redisTemplate.expire(encCode, 1, TimeUnit.MINUTES);
			//存储验证码生成器,过期时间为1分钟
			redisTemplate.opsForValue().set(encCode+"ValidateCode", SerializeUtil.serialize(vCode));
			redisTemplate.expire(encCode+"ValidateCode", 1, TimeUnit.MINUTES);
			jsono.put("success", true);
			jsono.put("message", encCode);
		} catch (Exception e) {
			e.printStackTrace();
			jsono.put("success", true);
			jsono.put("message", "inner error.");
		} finally{
			if(out != null) {
				out.flush();
				out.close();
			}
		}
		return jsono.toString();
	}
	
	@RequestMapping(value="getUserMsg")
	@ResponseBody
	public String getUserMsg(String userName){
		JSONObject jsono = new JSONObject();
		try{
			User user = service.findOneUser(userName);
			if(user == null) {
				jsono.put("success", false);
				jsono.put("message", "The user doesn't exist.");
			} else {
				if(user.getEmail() == null || user.getEmail().isEmpty()){
					jsono.put("success", false);
					jsono.put("message", "The user doesn't have email.");
				} else {
					jsono.put("success", true);
					jsono.put("message", user.getEmail());
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			jsono.put("success", false);
			jsono.put("message", "inner error.");
		}
		return jsono.toString();
	}
}

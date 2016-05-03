package com.ds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	
//	@Autowired
//	private RedisTemplate<String, String> redisTemplate;
	
	@RequestMapping(value="/hello")
	public String hello(Model model){
		model.addAttribute("spring", "spring");
//		if(redisTemplate.opsForValue().get("user") == null)
//			redisTemplate.opsForValue().set("user", "hjzgg");
//		else 
//			System.out.println(redisTemplate.opsForValue().get("user"));
		return "hello";
	}
	
}

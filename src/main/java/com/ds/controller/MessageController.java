package com.ds.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ds.service.MessageService;
import com.yonyou.uap.entity.response.MessageResponse;

@Controller
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value="sendEmail")
	@ResponseBody
	public String sendEmail(String title, String content, String address, HttpServletRequest request){
		JSONObject jsono = new JSONObject();
		try {
			String ipAndPort = InetAddress.getLocalHost().getHostAddress() + ":" + request.getLocalPort();
			List<MessageResponse> results = messageService.sendEmail(title, content, address, "http://" + ipAndPort + "/dsdemo/");
			jsono.put("success", true);
			jsono.put("message", "send email success.");
		} catch (Exception e) {
			jsono.put("success", false);
			jsono.put("message", "inner error.");
			e.printStackTrace();
		}
		return jsono.toString();
	}
	
}

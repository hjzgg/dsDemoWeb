package com.ds.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AuthorityFilter implements Filter {
	private RedisTemplate<String, Object> redisTemplate;
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		//登录或者注册不拦截
		if(((HttpServletRequest)req).getServletPath().contains("userLogin") || ((HttpServletRequest)req).getServletPath().contains("userRegister")){
			chain.doFilter(req, res);
		} else {
			String userToken = req.getParameter("userToken");
			if(userToken == null) {
				JSONObject jsono = new JSONObject();
				jsono.put("success", false);
				jsono.put("returnLogin", true);
				jsono.put("message", "user isn't login.");
				PrintWriter pw = new PrintWriter(res.getOutputStream());
				pw.print(jsono.toString());
				pw.flush();
				pw.close();
				return;
			} else {
				if(redisTemplate.opsForValue().get(userToken) != null){
					redisTemplate.expire(userToken, 2, TimeUnit.MINUTES);//两分钟后过期
					chain.doFilter(req, res);
				} else {
					JSONObject jsono = new JSONObject();
					jsono.put("success", false);
					jsono.put("returnLogin", true);
					jsono.put("message", "user should login again.");
					PrintWriter pw = new PrintWriter(res.getOutputStream());
					pw.print(jsono.toString());
					pw.flush();
					pw.close();
					return;
				}
			}
		}
	}
	
	@Override
	public void destroy() {
		
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		ServletContext context = config.getServletContext();  
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);  
        redisTemplate = (RedisTemplate<String, Object>) ctx.getBean("redisTemplate");  
	}
}

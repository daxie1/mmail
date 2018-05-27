package com.happymail.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happaymail.common.bean.User;
import com.happymail.common.util.Config;
import com.happymail.common.util.CookieUtil;
import com.happymail.common.util.ServiceResponse;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class IndexController
{
	@RequestMapping("/")
	@ResponseBody
	public String index()
	{
		log.info("web 启动成功:{}", IndexController.class.toString());
		return "hello world";
	}
	
	@RequestMapping("/login")
	public String login()
	{
		return "login";
	}
	@RequestMapping(value="/doLogin",method= {RequestMethod.POST})
	@ResponseBody
	public ServiceResponse doLogin(String username,String password,HttpServletRequest request,HttpServletResponse response)
	{
		ServiceResponse serviceResponse=new ServiceResponse();
		Cookie cookie=CookieUtil.getCookie(request, Config.UserConfig.USER_COOKIE_KEY);
		String sessionid=null;
		if(cookie==null)
		{
			sessionid=request.getSession().getId();
			CookieUtil.addCookie(response, Config.UserConfig.USER_COOKIE_KEY, sessionid);
			serviceResponse.setMessage("没有登录");
		}else
		{
			sessionid=cookie.getValue();
			serviceResponse.setMessage("已经登录");
		}
		User user=new User();
		user.setName(username);
		user.setPassword(password);
		//serviceResponseresponse.setMessage(sessionid+":"+user.toString());
		return serviceResponse;
	}
}

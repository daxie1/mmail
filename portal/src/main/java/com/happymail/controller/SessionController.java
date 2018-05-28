package com.happymail.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.happymail.common.bean.User;
import com.happymail.common.util.JsonUtil;

@Controller
@RequestMapping("/session")
public class SessionController
{
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request)
	{
		ModelAndView model=new ModelAndView();
		Object userJson=request.getSession().getAttribute("user");
		User user=null;
		if(userJson!=null)
		{
			user=JsonUtil.ToObject(userJson.toString(),User.class);
			model.addObject("name", user.getUsername());
			model.addObject("password",user.getPassword());
		}
		model.setViewName("sindex");
		return model;
	}
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam("username")String username,@RequestParam("password")String password,HttpServletRequest request)
	{
		ModelAndView model=new ModelAndView();
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		request.getSession().setAttribute("user", JsonUtil.ToJson(user));
		model.addObject("name", username);
		model.addObject("password",password);
		model.setViewName("slogin");
		return model;
	}
}

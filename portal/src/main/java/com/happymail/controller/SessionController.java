package com.happymail.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String index(HttpServletRequest request,Model model)
	{
		Object userJson=request.getSession().getAttribute("user");
		User user=null;
		if(userJson!=null)
		{
			user=JsonUtil.ToObject(userJson.toString(),User.class);
//			model.addObject("name", user.getUsername());
//			model.addObject("password",user.getPassword());
			model.addAttribute("username", user.getUsername());
			model.addAttribute("password",user.getPassword());
		}
		
		return "sindex";
	}
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam("username")String username,@RequestParam("password")String password,HttpServletRequest request)
	{
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		request.getSession().setAttribute("user", JsonUtil.ToJson(user));
		ModelAndView model=new ModelAndView("slogin");
		model.addObject("usertest", user);
		return model;
	}
}

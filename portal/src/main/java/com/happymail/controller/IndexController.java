package com.happymail.controller;


import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happymail.common.bean.User;
import com.happymail.common.util.Config;
import com.happymail.common.util.CookieUtil;
import com.happymail.common.util.EncryptUtil;
import com.happymail.common.util.JedisUtil;
import com.happymail.common.util.JsonUtil;
import com.happymail.common.util.ResponseCode;
import com.happymail.common.util.ServiceResponse;
import com.happymail.common.util.ValidationUtil;
import com.happymail.serviceinter.IUserService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@Getter
@Setter
public class IndexController
{
	@Resource(name="userService")
	private IUserService userService;
	@RequestMapping("/")
	@ResponseBody
	public String index()
	{
		userService.test();
		return "hello world";
	}
	
	@RequestMapping("/login")
	public String login()
	{
		return "login";
	}
	@RequestMapping(value="/doLogin",method= {RequestMethod.POST})
	@ResponseBody
	public ServiceResponse<User> doLogin(@RequestParam("username")String username,@RequestParam("password")String password,HttpServletRequest request,HttpServletResponse response)
	{
		Cookie cookie=CookieUtil.getCookie(request, Config.UserConfig.USER_COOKIE_KEY);
		String sessionid=null;
		if(cookie==null)
		{
			//验证登录
			User user=new User();
			user.setUsername(username);
			user.setPassword(password);//加密应该放在Service实现里面
			user=userService.validateLogin(user);
			if(user==null)
			{
				return ServiceResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "用户密码错误");
			}
			sessionid=request.getSession().getId();
			CookieUtil.addCookie(response, Config.UserConfig.USER_COOKIE_KEY, sessionid);
			JedisUtil.setex(sessionid, JsonUtil.ToJson(user), 60*30);//设置30分钟过期
			return ServiceResponse.createBySuccessMessage("登录成功");
		}else
		{
			sessionid=cookie.getValue();
			User user=JsonUtil.ToObject(JedisUtil.get(sessionid), User.class);
			return ServiceResponse.createBySuccess("已经登录",user);
		}
	}
	
	@RequestMapping(value="/register",method= {RequestMethod.POST})
	@ResponseBody
	public ServiceResponse<User> registerUser(@Valid @RequestBody User user,BindingResult errors)
	{
		String validMsg=ValidationUtil.getFirstFieldErrorMsg(errors);//验证传入参数
		if(validMsg!=null)
		{
			return ServiceResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),validMsg);
		}
		user.setPassword(EncryptUtil.encodedByMD5(user.getPassword()));//密码加密
		String errMsg=userService.resigter(user);
		if(errMsg==null)
		{
			return ServiceResponse.createBySuccess();
		}else {
			return ServiceResponse.createByErrorMessage(errMsg);
		}
	}
	@RequestMapping(value="/valid")
	@ResponseBody
	public ServiceResponse userValid()
	{
		return ServiceResponse.createBySuccessMessage("成功");
	}
}

package com.happymail.web.common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.happymail.common.util.ResponseCode;

import lombok.extern.log4j.Log4j2;

/**
 * 全局异常处理
 * 防止在用户端显示异常：
 * 1.影响用户体验
 * 2.异常信息会暴露系统信息，有不安全的因素
 * 统一将异常输出为json，不区分是html请求还是json请求
 * @author yu
 *
 */
@Component
@Log4j2
public class ExceptHandler implements HandlerExceptionResolver
{

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex)
	{
		log.error(request.getRequestURI()+"Exception:"+ex);
		ModelAndView modelAndView=new ModelAndView(new FastJsonJsonView());//使用jsonView出事化modelAndView
		
		modelAndView.addObject("status",ResponseCode.ERROR.getCode());
		modelAndView.addObject("msg","程序异常，请联系管理员");
		modelAndView.addObject("data",ex.getMessage());
		return modelAndView;
	}

}

package com.happymail.web.common.Interceptor;



import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.MidiDevice.Info;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

/**
 * 参数验证的拦截器
 * 还要在spring-mvc中配置
 * @author yu
 *
 */
@Log4j2
public class ParamVaildateInterceptor implements HandlerInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
//		HandlerMethod handlerMethod = (HandlerMethod)handler;
//		MethodParameter[] parameters=handlerMethod.getMethodParameters();
//		for(MethodParameter parameter:parameters)
//		{
//			if(parameter.getParameterType()==BindingResult.class)
//			{
//				log.info("test ok");
//				//String[] value=request.getParameterValues(parameter.getParameterName());
//				//log.info(parameter.);
//				Enumeration<String> enu=request.getParameterNames();
//				while(enu.hasMoreElements())
//				{
//					log.info(enu.nextElement());
//				}
//			}
//		}
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{
		System.out.println("afterCompletion");
	}
		

}

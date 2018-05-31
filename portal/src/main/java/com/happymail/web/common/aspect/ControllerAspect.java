package com.happymail.web.common.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 *对控制器拦截的Aop advice
 *使用注解方式
 * @author Administrator
 *
 */
@Component
@Aspect
@Log4j2
public class ControllerAspect
{
	/**
	 * 定义切入点
	 */
	@Pointcut("execution(* com.happymail.com.service.impl.*.*(..))")
	public void pointCut(){}
	
	/**
	 * 验证传入参数
	 * @param joinpoint
	 */
	@Before(value="pointCut()")
	public void validParameters()
	{
		log.error("前置处理测试成功");
	}
}

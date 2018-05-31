package com.happymail.web.common.schedule;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.happymail.common.util.DistributedLockUtil;
import com.happymail.serviceinter.IUserService;

import lombok.extern.log4j.Log4j2;

/**
 * 简单的示例任务调度器
 * @author yu
 *
 */
//@Component
@Log4j2
public class ExampleSchedule
{
	private static final String LOCK_NAME="LOCK_NAME";
	@Resource(name="userService")
	private IUserService userService;
	@Scheduled(cron="0 0/1 * * * ?")//每分钟执行一次（分钟的整数倍）
	public void Test()
	{
		if(DistributedLockUtil.lock(LOCK_NAME))
		{
			log.info("ThreadName:{} 获得锁", Thread.currentThread().getName());
			log.info("<!----------------执行相关操作---------------------------->");
		}else
		{
			log.info("ThreadName:{} 没有获得锁", Thread.currentThread().getName());
		}
		DistributedLockUtil.release(LOCK_NAME);
		log.info("ThreadName:{} 释放锁",Thread.currentThread().getName());
	}
	public IUserService getUserService()
	{
		return userService;
	}
	public void setUserService(IUserService userService)
	{
		this.userService = userService;
	}
}

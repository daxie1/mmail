package com.happymail.web.common.schedule;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
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
@Component
@Log4j2
public class ExampleSchedule
{
	private static final String LOCK_NAME="LOCK_NAME";
	@Resource(name="userService")
	private IUserService userService;
	@Autowired
	private RedissonClient redissonClient;//注入redisson客户端
	public RedissonClient getRedissonClient()
	{
		return redissonClient;
	}
	public void setRedissonClient(RedissonClient redissonClient)
	{
		this.redissonClient = redissonClient;
	}
	//@Scheduled(cron="0 0/1 * * * ?")//每分钟执行一次（分钟的整数倍）
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
	@Scheduled(cron="0 0/1 * * * ?")//每分钟执行一次（分钟的整数倍）
	public void redissonClientLock()
	{
		RLock lock=redissonClient.getLock("yuqian");
		boolean getLock=false;
		try
		{
			if(getLock=lock.tryLock(0, 5, TimeUnit.SECONDS))
			{
				log.info("ThreadName:{},获取锁，成功执行操作",Thread.currentThread().getName());
				log.info("<!----------执行操作------------------!>");
				Thread.sleep(2000);
			}
		} catch (InterruptedException e)
		{
			log.info("ThreadName:{},获取锁失败",Thread.currentThread().getName(),e);
		} finally
		{
			log.info("ThreadName:{},释放锁",Thread.currentThread().getName());
			if(!getLock)
			{
				return;
			}
			lock.unlock();
		}
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

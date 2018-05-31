package com.happymail.common.util;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;

/**
 * 使用Redis实现分布式锁的帮助类
 * @author yu
 *
 */
public class DistributedLockUtil
{
	private DistributedLockUtil() {}
	/**
	 * 加锁成功
	 * 默认加锁时间设置为5秒，防止死锁
	 * @param lockName 需要上锁的
	 * @return
	 */
	public static boolean lock(String lockName)
	{
		//1设置要过期是时间搓 当前时间搓+5000
		String outTime=System.currentTimeMillis()+5000+"";
		Long setResult=JedisUtil.setnx(lockName, outTime);
		//2.成功获取锁，设置锁的有效期与value的值一致，并返回true
		if(setResult!=null&&setResult==1)
		{
			JedisUtil.expire(lockName, 5);//这一步可能会执行，导致死锁
			return true;
		}else
		{
			//3.设置不成功时获取超时时间，若现在的时间已超过锁的有效期，则一样可以使用，更新锁的有效期，也能防止死锁(即使某些原因导致key永久有效)
			Long curentTime=System.currentTimeMillis();
			Long lockTime=Long.parseLong(JedisUtil.get(lockName));
			if(curentTime>=lockTime)//已经超时，重新上锁
			{
				String newLockTime=curentTime+5000+"";//新的有效期
				if(StringUtils.equals(outTime,JedisUtil.getset(lockName, newLockTime)))//获取旧的值，锁是否是旧的值，锁是否安全
				{
					JedisUtil.expire(lockName,5);
					return true;
				}else
				{
					return false;
				}
			}else {
				return false;
			}
		}
	}
	/**
	 * 释放分布式锁
	 * @param lockName
	 * @PreDestroy tomcat shutdown的时候会执行
	 */
	@PreDestroy
	public static void release(String lockName)
	{
		JedisUtil.del(lockName);
	}
}

package com.happymail.common.util;

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
			JedisUtil.expire(lockName, 5);
			return true;
		}else
		{
			//3.设置不成功时获取超时时间，若现在的时间已超过锁的有效期，则一样可以使用，更新锁的有效期
			Long curentTime=System.currentTimeMillis();
			Long lockTime=Long.parseLong(JedisUtil.get(lockName));
			if(curentTime>=lockTime)//已经超时，重新上锁
			{
				String newLockTime=curentTime+5000+"";//新的有效期
				if(outTime.equals(JedisUtil.getset(lockName, newLockTime)))//保险期间判断就的有效期是否一致
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
	 */
	public static void release(String lockName)
	{
		JedisUtil.del(lockName);
	}
}

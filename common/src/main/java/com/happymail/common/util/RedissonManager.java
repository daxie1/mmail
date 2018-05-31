package com.happymail.common.util;
import javax.annotation.PostConstruct;

/**
 * redisson初始化类
 * @author yu
 *
 */
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

//@Component
@Log4j2
public class RedissonManager
{
	private Config config=null;
	private Redisson redisson=null;
	
	private static String redisIp1=PropertiesUtil.getProperty("redis.ip");
	private static String redisPort1=PropertiesUtil.getProperty("redis.port");
	
	//使用了spring容器生成，不需要使用静态代码块
	@PostConstruct
	private void init()
	{
		try
		{
			//使用简单单实例初始化
			config.useSingleServer().setAddress(new StringBuilder().append(redisIp1).append(":").append(redisPort1).toString());
			
			//单主模式
			//config.useMasterSlaveServers().setMasterAddress(new StringBuilder().append(redisIp1).append(":").append(redisPort1).toString());
			
			//主从模式（redis配置文件中配置主从，主服务器负责写，从服务器会自动监听主服务器的状态变化而更新自己。从服务器是只读的，只有主服务器数据发生变化时自身的数据才会变化吧）
			//config.useMasterSlaveServers().setMasterAddress(new StringBuilder().append(redisIp1).append(":").append(redisPort1).toString())
			//.addSlaveAddress(addresses);
			redisson=(Redisson) Redisson.create(config);
			log.info("初始化Redisson结束");
		} catch (Exception e)
		{
			log.error("初始化Redisson失败",e);
		}

	}
	public Redisson getRedssion()
	{
		return redisson;
	}
	
}

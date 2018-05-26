package com.happymail.common.util;


import java.util.Properties;

import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 连接池单机
 * @author yu
 *
 */
@Log4j2
public class RedisPool
{
	private static JedisPool jedisPool=null;
	private RedisPool() {}
	static
	{
		try
		{
			JedisPoolConfig config=new JedisPoolConfig();
			Properties properties=PropertiesUtil.getDefaultProperties();
			config=new JedisPoolConfig();
			config.setMaxIdle(Integer.parseInt(properties.getProperty("redis.pool.maxIdle")));
			config.setMaxTotal(Integer.parseInt(properties.getProperty("redis.pool.maxTotal")));
			config.setMinIdle(Integer.parseInt(properties.getProperty("redis.pool.minIdle")));
			config.setMaxWaitMillis(Integer.parseInt(properties.getProperty("redis.pool.maxWaitMillis")));
			config.setTestOnReturn(Boolean.getBoolean(properties.getProperty("redis.pool.testOnReturn")));
			config.setTestOnBorrow(Boolean.getBoolean(properties.getProperty("redis.pool.testOnBorrow")));
			config.setTestWhileIdle(Boolean.getBoolean(properties.getProperty("redis.pool.testWhileIdle")));
			config.setNumTestsPerEvictionRun(Integer.parseInt(properties.getProperty("redis.pool.numTestsPerEvictionRun")));
			config.setTimeBetweenEvictionRunsMillis(Integer.parseInt(properties.getProperty("redis.pool.timeBetweenEvictionRunsMillis")));
			
			
			jedisPool=new JedisPool(config,properties.getProperty("redis.ip"),Integer.parseInt(properties.getProperty("redis.port")),Integer.parseInt(properties.getProperty("redis.timeout")));
		}catch (Exception e) {
			log.error("init jedis pool errr {}", e.getMessage(), e);
		}
	}
	public static Jedis getjedis()
	{
		return jedisPool.getResource();
	}
	public static JedisPool getJedisPool()
	{
		return jedisPool;
	}
}

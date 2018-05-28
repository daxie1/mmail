package com.happymail.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

/**
 * 分片 分布式redis连接池
 * @author yu
 *
 */
@Log4j2
public class SharedRedisPool
{
	private static ShardedJedisPool pool;
	private SharedRedisPool() {}
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
			
			List<JedisShardInfo> infos=new ArrayList<>(2);//连接的服务器信息
			infos.add(new JedisShardInfo(properties.getProperty("redis.ip"),Integer.parseInt(properties.getProperty("redis.port"))));//测试配置
			infos.add(new JedisShardInfo(properties.getProperty("redis1.ip"),Integer.parseInt(properties.getProperty("redis1.port"))));//测试配置
			pool=new ShardedJedisPool(config,infos,Hashing.MURMUR_HASH,Sharded.DEFAULT_KEY_TAG_PATTERN);//配置分片连接池
		}catch (Exception e) {
			log.error("init jedis pool errr {}", e.getMessage(), e);
		}
	}
	public static ShardedJedis getjedis()
	{
		return pool.getResource();
	}
	public static ShardedJedisPool getJedisPool()
	{
		return pool;
	}
}

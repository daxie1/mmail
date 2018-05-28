package redis.test;

import org.junit.Test;

import com.happymail.common.util.SharedJedisUtil;

public class RedisTest
{
	@Test
	public void redisUtilTest()
	{
		for(int i=1;i<11;i++)
		{
			String key="key"+i;
			String value="value"+i;
			SharedJedisUtil.set(key, value);
		}
	}
}

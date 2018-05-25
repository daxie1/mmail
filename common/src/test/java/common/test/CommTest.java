package common.test;

import java.util.Date;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.happaymail.common.bean.User;

public class CommTest
{
	@Test
	public void jsonTest()
	{
		User user=new User(1,"test","123456",new Date());
		System.out.println(JSON.toJSONString(user));
	}
}

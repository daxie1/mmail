package portal.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.happymail.common.bean.User;
import com.happymail.common.util.JedisUtil;
import com.happymail.common.util.JsonUtil;
import com.happymail.serviceinter.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class CommonTest
{
	@Resource(name="userServiceTest")
	private IUserService userService;
	@Test
	public void userTest()
	{
		User user=new User();
		user.setUsername("test");
		user.setPassword("123456");
		user=userService.validateLogin(user);
		String key="test";
		System.out.println("user validate ok,User:"+user);
		System.out.println("<!-------------------------------------------------------!>");
		JedisUtil.setex(key, JsonUtil.ToJson(user), 60*30);
		System.out.println("User set into redis ok:"+JsonUtil.ToJson(user));
		System.out.println("<!-------------------------------------------------------!>");
		user=JsonUtil.ToObject(JedisUtil.get(key), User.class);
		System.out.println("Get from redis ok:"+user.toString());
	}
}

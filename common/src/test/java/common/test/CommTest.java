package common.test;


import org.junit.Test;
import com.happymail.common.util.PropertiesUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommTest
{
	public void jsonTest()
	{
//		User user=new User(1,"test","123456",new Date());
//		User user1=new User(2,"test2","123456",new Date());
//		List<User> users=new ArrayList<>();
//		users.add(user1);
//		users.add(user);
//		Teacher teacher=new Teacher(1, "teacher", users);
//		Teacher teacher2=new Teacher(2, "teacher2", users);
//		List<Teacher> teachers=new ArrayList<>();
//		teachers.add(teacher);
//		teachers.add(teacher2);
		//System.out.println(JsonUtil.ToJson(teachers));
		//String jsonText="[{\"id\":2,\"name\":\"test2\",\"password\":\"123456\",\"registedDate\":1527257494676},{\"id\":1,\"name\":\"test\",\"password\":\"123456\",\"registedDate\":1527257494676}]";
		//List<User> users2=JsonUtil.ToList(jsonText, User.class);
		//System.out.println(users2.size());
		//String jsonText="[{\"id\":1,\"name\":\"teacher\",\"users\":[{\"id\":2,\"name\":\"test2\",\"password\":\"123456\",\"registedDate\":1527257836804},{\"id\":1,\"name\":\"test\",\"password\":\"123456\",\"registedDate\":1527257836804}]},{\"id\":2,\"name\":\"teacher2\",\"users\":[{\"$ref\":\"$[0].users[0]\"},{\"$ref\":\"$[0].users[1]\"}]}]";
	}
	@Test
	public void propertiesTest()
	{
		//Properties pop = new Properties();
		//pop.setProperty("user.name", "testNew");
		//PropertiesUtil.updateProperties(pop, Config.PropertiesConfig.DEFAULT_PROPERTIES_FILE);
		String name=PropertiesUtil.getProperty("user.name");
		log.error("log text");
		System.out.println(name);
	}
}

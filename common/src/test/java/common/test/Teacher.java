package common.test;

import java.util.List;

import com.happaymail.common.bean.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Teacher
{
	private Integer id;
	private String name;
	private List<User> users;
}

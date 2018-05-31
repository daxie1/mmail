package com.happymail.service.test;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.happymail.common.bean.User;
import com.happymail.common.util.EncryptUtil;
import com.happymail.serviceinter.IUserService;

@Service("userServiceTest")
public class UserServiceTest implements IUserService
{

	@Override
	public User getById(Object key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getByCondition(User t)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(User t)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(User t)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Object key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(User t)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateSelective(User t)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User validateLogin(User user)
	{
		// TODO Auto-generated method stub
		user.setPassword(EncryptUtil.encodedByMD5(user.getPassword()));
		String password=EncryptUtil.encodedByMD5("123456");
		String username="test";
		if(StringUtils.equalsIgnoreCase(username, user.getUsername())&&StringUtils.equalsIgnoreCase(password, user.getPassword()))
		{
			user.setId(1);
			user.setRegistedDate(new Date());
			return user;
		}
		return null;
	}

	@Override
	public String resigter(User user)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeUserInfo(int hour)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void test()
	{
		// TODO Auto-generated method stub

	}

}

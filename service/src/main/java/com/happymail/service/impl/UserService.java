package com.happymail.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happymail.common.bean.User;
import com.happymail.common.util.EncryptUtil;
import com.happymail.dao.UserDao;
import com.happymail.serviceinter.IUserService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Service("userService")
@Transactional
@Setter
@Getter
@Log4j2
public class UserService implements IUserService
{
	//@Autowired
	private UserDao userDao;
	@Override
	public User getById(Object key)
	{
		try
		{
			return userDao.selectByPrimaryKey((Integer)key);
		} catch (Exception e)
		{
			log.error("Get User By id:{} error.Error message:{}",key,e.getMessage(),e);
			return null;
		}
	}

	@Override
	public List<User> getByCondition(User t)
	{
		try
		{
			return userDao.selectByCondition(t);
		} catch (Exception e)
		{
			log.error("Get User By User:{} error.Error message:{}",t,e.getMessage(),e);
			return null;
		}
	}

	@Override
	public int insert(User t)
	{
		try
		{
			return userDao.insert(t);
		} catch (Exception e)
		{
			log.error("Insert User:{} error.Error message:{}",t,e.getMessage(),e);
			return -1;
		}
	}

	@Override
	public int insertSelective(User t)
	{
		try
		{
			return userDao.insertSelective(t);
		} catch (Exception e)
		{
			log.error("Insert User:{} error.Error message:{}",t,e.getMessage(),e);
			return -1;
		}
	}

	@Override
	public int deleteById(Object key)
	{
		try
		{
			return userDao.deleteByPrimaryKey((Integer)key);
		} catch (Exception e)
		{
			log.error("delete User by id:{} error.Error message:{}",key,e.getMessage(),e);
			return -1;
		}
	}

	@Override
	public int update(User t)
	{
		try
		{
			return userDao.updateByPrimaryKey(t);
		} catch (Exception e)
		{
			log.error("update User:{} error.Error message:{}",t,e.getMessage(),e);
			return -1;
		}
	}

	@Override
	public int updateSelective(User t)
	{
		try
		{
			return userDao.updateByPrimaryKeySelective(t);
		} catch (Exception e)
		{
			log.error("update User:{} error.Error message:{}",t,e.getMessage(),e);
			return -1;
		}
	}

	@Override
	public User validateLogin(User user)
	{
		User result=null;
		try
		{
			user.setPassword(EncryptUtil.encodedByMD5(user.getPassword()));
			List<User> users=userDao.selectByCondition(user);
			if(users==null||users.size()==0)
			{
				throw new Exception("用户并不存在");
			}
			if(users.size()>1)
			{
				throw new Exception("用户信息不符合要求");
			}
			return users.get(0);
		}catch (Exception e) 
		{
			log.error("valiation login error :{}", e.getMessage(),e);
		}
		return result;
	}

	@Override
	public String resigter(User user)
	{
		String errMsg=null;
		try
		{
			User cond=new User();
			cond.setUsername(user.getUsername());
			List<User> users=userDao.selectByCondition(cond);
			if(users!=null&&users.size()>0)
			{
				throw new Exception("用户已存在");
			}
			userDao.insertSelective(user);
		} catch (Exception e)
		{
			errMsg=e.getMessage();
			log.error("register user:{}  error:{}", user,e.getMessage(),e);
		}
		return errMsg;
	}

	@Override
	public void closeUserInfo(int hour)
	{
		Date colseDate= DateUtils.addHours(new Date(), -hour);//apache的DateUtil也很好用
	}


}

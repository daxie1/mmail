package com.happymail.dao;

import java.util.List;

import com.happymail.common.bean.User;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);
	List<User> selectByCondition(User record);
}
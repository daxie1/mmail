package com.happymail.serviceinter;

import com.happymail.common.bean.User;

public interface IUserService extends IBaseService<User>
{
	/**
	 * 验证用户是否能登录
	 * @param user
	 * @return 用户的完整信息
	 */
	User validateLogin(User user);
	/**
	 * 用户注册
	 * @param user
	 * @return 成功返回null 失败返回错误信息
	 */
	String resigter(User user);
}

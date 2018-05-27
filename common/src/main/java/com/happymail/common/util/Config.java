package com.happymail.common.util;

public class Config
{
	/**
	 * json 相关配置
	 * @author yu
	 *
	 */
	public static interface JosnConfig
	{
		String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	}
	/**
	 * Properties相关配置
	 * @author yu
	 *
	 */
	public static interface PropertiesConfig
	{
		String DEFAULT_PROPERTIES_FILE="application.properties";
	}
	/**
	 * 
	 * @author yu
	 *
	 */
	public static interface CookieConfig
	{
		/**
		 * ".happymail.com"
		 * Domian
		 */
		String DEFAULT_DOMAIN="happymail.com";
		/**
		 *60*60*24*365
		 *一年 
		 */
		Integer DEAULT_MAX_AGE=60*60*24*365;
		/**
		 *"/"
		 * 保存路径
		 */
		String DEAULT_PATH="/";
	}
	public static interface UserConfig
	{
		String USER_COOKIE_KEY="user_info";
	}
}

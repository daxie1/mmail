package com.happymail.common.util;

import java.util.List;
import com.alibaba.fastjson.JSON;


/**
 * 针对FastJson的封装,默认日期转化为yyyy-MM-dd HH:mm:ss
 * @author yqian
 *
 */
public class JsonUtil
{
	static
	{
		JSON.DEFFAULT_DATE_FORMAT=Config.JosnConfig.DATE_FORMAT;//设置默认的日期格式
	}
	/**
	 * 将bean转化为
	 * @param t
	 * @param clazz
	 * @return
	 */
	public static String ToJson(Object object)
	{
		return JSON.toJSONString(object);
	}
	
	public static<T> T ToObject(String jsonText,Class<T> clazz)
	{
		return JSON.parseObject(jsonText,clazz);
	}
	public static<T> List<T> ToList(String jsonText,Class<T> clazz)
	{
		return JSON.parseArray(jsonText, clazz);
	}
}

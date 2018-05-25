package com.happymail.common.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 针对FastJson的封装
 * @author yqian
 *
 */
public class JosnUtil
{
	/**
	 * 将bean转化为
	 * @param t
	 * @param clazz
	 * @return
	 */
	public static<T> String objectToJson(T t,Class<T> clazz)
	{
		return JSONObject.toJSONString(t,);
	}
}

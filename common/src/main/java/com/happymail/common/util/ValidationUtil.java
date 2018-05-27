package com.happymail.common.util;


import java.util.List;


import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 数据验证工具类
 * @author yu
 *
 */
public class ValidationUtil
{
	public static String getFirstFieldErrorMsg(BindingResult errors)
	{
	     List<FieldError> list = errors.getFieldErrors();
	     if(list!=null&&list.size()>0)
	     {
	    	 return list.get(0).getDefaultMessage();
	     }else
	     {
	    	 return null;
	     }
	}
}

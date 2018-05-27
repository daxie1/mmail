package com.happymail.common.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponse
{
	/**
	 * 操作是否成功
	 */
	private boolean result;
	/**
	 * 返回消息
	 */
	private String message;
	private Object content;
	
}

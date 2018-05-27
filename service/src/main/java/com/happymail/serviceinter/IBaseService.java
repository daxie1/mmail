package com.happymail.serviceinter;

import java.util.List;

public interface IBaseService<T>
{
	T getById(Object key);
	List<T> getByCondition(T t);
	
	/**
	 * 插入完整的一个对象(没有属性值为空)
	 * @param t
	 * @return
	 */
	int insert(T t);
	int insertSelective(T t);
	
	int deleteById(Object key);
	/**
	 * 更新完整属性
	 * @param t
	 * @return
	 */
	int update(T t);
	/**
	 * 更新值不为空的属性
	 * @param t
	 * @return
	 */
	int updateSelective(T t);
}

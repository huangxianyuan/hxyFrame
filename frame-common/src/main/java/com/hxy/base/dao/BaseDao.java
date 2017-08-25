package com.hxy.base.dao;

import com.hxy.base.page.Page;

import java.util.List;
import java.util.Map;

/**
 * 类BaseDao的功能描述:
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 * @auther hxy
 * @date 2017-04-28 11:25:08
 */
public interface BaseDao<T> {
	
	int save(T t);
	
	int save(Map<String, Object> map);
	
	int saveBatch(List<T> list);
	
	int update(T t);
	
	int update(Map<String, Object> map);
	
	int delete(Object id);
	
	int delete(Map<String, Object> map);
	
	int deleteBatch(Object[] ids);

	T queryObject(Object id);
	
	List<T> queryList(Map<String, Object> map);

	List<T> queryListByBean(T t);

	List<T> queryList(Object id);

	int queryTotal(Map<String, Object> map);

	int queryTotal();





}

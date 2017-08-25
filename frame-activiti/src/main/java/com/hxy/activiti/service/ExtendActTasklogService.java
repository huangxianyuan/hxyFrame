package com.hxy.activiti.service;

import com.hxy.activiti.entity.ExtendActTasklogEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-08-04 11:46:48
 */
public interface ExtendActTasklogService {
	
	ExtendActTasklogEntity queryObject(String id);
	
	List<ExtendActTasklogEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ExtendActTasklogEntity extendActTasklog);
	
	void update(ExtendActTasklogEntity extendActTasklog);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	/**
	 * 根据任务id 更改日志
	 * @param extendActTasklogEntity
	 * @return
	 */
	int updateByTaskId(ExtendActTasklogEntity extendActTasklogEntity);
}

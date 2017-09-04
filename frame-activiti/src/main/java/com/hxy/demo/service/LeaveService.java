package com.hxy.demo.service;


import com.hxy.base.page.Page;
import com.hxy.demo.entity.LeaveEntity;

import java.util.List;
import java.util.Map;

/**
 * 请假流程测试
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-31 13:33:23
 */
public interface LeaveService {
	
	LeaveEntity queryObject(String id);
	
	List<LeaveEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(LeaveEntity leave);
	
	void update(LeaveEntity leave);
	
	int delete(String id);
	
	void deleteBatch(String[] ids);

	Page<LeaveEntity> findPage(LeaveEntity leaveEntity, int pageNum);
}

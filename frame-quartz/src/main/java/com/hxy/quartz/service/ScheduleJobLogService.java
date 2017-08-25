package com.hxy.quartz.service;


import com.hxy.quartz.entity.ScheduleJobLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 类ScheduleJobLogService的功能描述:
 * 定时任务日志
 * @auther hxy
 * @date 2017-08-25 16:16:30
 */
public interface ScheduleJobLogService {

	/**
	 * 根据ID，查询定时任务日志
	 */
	ScheduleJobLogEntity queryObject(Long jobId);
	
	/**
	 * 查询定时任务日志列表
	 */
	List<ScheduleJobLogEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存定时任务日志
	 */
	void save(ScheduleJobLogEntity log);
	
}

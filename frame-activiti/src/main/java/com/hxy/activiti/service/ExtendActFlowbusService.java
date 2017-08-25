package com.hxy.activiti.service;

import com.hxy.activiti.entity.ExtendActFlowbusEntity;

import java.util.List;
import java.util.Map;

/**
 * 业务流程关系表与activitiBaseEntity中字段一样
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-08-04 13:56:50
 */
public interface ExtendActFlowbusService {
	
	ExtendActFlowbusEntity queryObject(String id);
	
	void save(ExtendActFlowbusEntity extendActFlowbus);
	
	void update(ExtendActFlowbusEntity extendActFlowbus);
	
	void delete(String id);

	/**
	 * 根据业务id更新
	 * @param extendActFlowbusEntity
	 * @return
	 */
	int updateByBusId(ExtendActFlowbusEntity extendActFlowbusEntity);

	/**
	 * 根据业务id和流程实例id查询
	 * @param instanceId 流程实例id
	 * @param busId 业务id
	 * @return
	 */
	ExtendActFlowbusEntity queryByBusIdInsId(String instanceId,String busId);
	

}

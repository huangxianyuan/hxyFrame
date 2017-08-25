package com.hxy.activiti.service;

import com.hxy.activiti.entity.ExtendActBusinessEntity;

import java.util.List;
import java.util.Map;

/**
 * 业务流程  对应的 业务表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-14 11:09:21
 */
public interface ExtendActBusinessService {
	
	ExtendActBusinessEntity queryObject(String id);
	
	List<ExtendActBusinessEntity> queryList(Map<String, Object> map);

	/**
	 * 根据实体类条件查询 业务树
	 * @param extendActBusinessEntity
	 * @return
	 */
	List<ExtendActBusinessEntity> queryListByBean(ExtendActBusinessEntity extendActBusinessEntity);

	int queryTotal(Map<String, Object> map);

	int delete(String id);
	
	void deleteBatch(String[] ids);

	/**
	 * 保存和更新
	 * @param extendActBusinessEntity
	 * @return
	 */
	int edit(ExtendActBusinessEntity extendActBusinessEntity);

	/**
	 * 只查询流程业务类，不查询根目录和回调
	 * @return
	 */
	List<ExtendActBusinessEntity> queryBusTree();

	/**
	 * 根据extend_act_model中的modelid查询对应的业务
	 * @param modelId
	 * @return
	 */
	ExtendActBusinessEntity queryActBusByModelId(String modelId);

	/**
	 * 根据业务id查询该业务的所有回调
	 * @param parentId
	 * @return
	 */
	List<Map<String,Object>> queryCalBackByPid(String parentId);

	/**
	 * 根据流程key查询
	 * @return
	 */
	ExtendActBusinessEntity queryByActKey(String actKey);


}

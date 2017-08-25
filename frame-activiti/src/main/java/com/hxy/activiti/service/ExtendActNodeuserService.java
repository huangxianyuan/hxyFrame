package com.hxy.activiti.service;

import com.hxy.activiti.entity.ExtendActNodeuserEntity;
import com.hxy.base.page.Page;

import java.util.List;
import java.util.Map;

/**
 * 节点可选人
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-24 13:28:51
 */
public interface ExtendActNodeuserService {
	
	ExtendActNodeuserEntity queryObject(String id);
	
	List<ExtendActNodeuserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ExtendActNodeuserEntity extendActNodeuser);
	
	void update(ExtendActNodeuserEntity extendActNodeuser);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	/**
	 * 根据节点获取节点审批人员信息
	 * @param nodeId
	 */
	 List<ExtendActNodeuserEntity> getNodeUserByNodeId(String nodeId);

}

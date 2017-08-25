package com.hxy.activiti.dao;

import com.hxy.activiti.entity.ExtendActNodesetEntity;
import com.hxy.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 流程节点配置
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-24 13:28:51
 */
@Repository
public interface ExtendActNodesetDao extends BaseDao<ExtendActNodesetEntity> {
    /**
     * 根据nodeId查询节点信息
     * @param nodeId
     * @return
     */
    ExtendActNodesetEntity queryByNodeId(String nodeId);

    /**
     * 根据nodeId和模型id查询节点信息
     * @param nodeId
     * @param modelId
     * @return
     */
    ExtendActNodesetEntity queryByNodeIdModelId(String nodeId,String modelId);

}

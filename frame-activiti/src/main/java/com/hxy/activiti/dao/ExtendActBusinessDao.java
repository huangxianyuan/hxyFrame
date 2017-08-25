package com.hxy.activiti.dao;

import com.hxy.activiti.entity.ExtendActBusinessEntity;
import com.hxy.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 业务流程  对应的 业务表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-14 11:09:21
 */
@Repository
public interface ExtendActBusinessDao extends BaseDao<ExtendActBusinessEntity> {

    /**
     * 只查询流程业务类，不查询根目录和回调
     * @return
     */
    List<ExtendActBusinessEntity> queryBusTree(String type1,String type2);

    /**
     * 根据extend_act_model中的modelid查询对应业务
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

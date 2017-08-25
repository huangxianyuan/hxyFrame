package com.hxy.activiti.dao;

import com.hxy.activiti.entity.ExtendActTasklogEntity;
import com.hxy.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-08-04 11:46:48
 */
@Repository
public interface ExtendActTasklogDao extends BaseDao<ExtendActTasklogEntity> {
    /**
     * 根据任务id 更改日志
     * @param extendActTasklogEntity
     * @return
     */
    int updateByTaskId(ExtendActTasklogEntity extendActTasklogEntity);
}

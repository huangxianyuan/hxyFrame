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

    /**
     * 转办任务时更新任务日志，有可能会存在多次转办，就会产生多条任务日志，所有这里用 taskId+appAction为空 作唯一
     * @param extendActTasklogEntity
     * @return
     */
    int updateByTaskIdOpinion(ExtendActTasklogEntity extendActTasklogEntity);
}

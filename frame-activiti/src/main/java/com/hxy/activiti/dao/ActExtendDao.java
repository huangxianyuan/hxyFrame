package com.hxy.activiti.dao;

import com.hxy.activiti.dto.ProcessTaskDto;
import com.hxy.base.page.Page;
import com.hxy.sys.entity.UserEntity;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 类的功能描述.
 * 直接对activiti的表写sql查询，不通过api查询
 * @Auther hxy
 * @Date 2017/7/31
 */
@Repository
public interface ActExtendDao {
    /**
     * 根据流程key 获取业务可用的流程
     * @param actKey
     * @return
     */
    List<Map<String,Object>> queryFlowsByActKey(String actKey);

    /**
     * 根据节点id分页查询可以选择的审批人
     * @param map key:nodeId 节点id,key:userName 用户姓名 用于模糊查询
     * @return
     */
    List<UserEntity> userWindowPage(Map<String,Object> map);

    /**
     * 转办变更人选择
     * @param userEntity
     * @return
     */
    List<UserEntity> turnWindowList(UserEntity userEntity);

    /**
     * 流程根据业务id查询业务信息
     * @param params key:busId 业务id key:tableName 表名 key:pkName 业务主键
     * @return
     */
    Map<String,Object> queryBusiByBusId(Map<String,Object> params);

    /**
     * 更新当前操作流程的业务表信息
     * @param params
     * @return
     */
    int updateBusInfo(Map<String,Object> params);

    /**
     * 我的待办列表
     * @param params
     * @return
     */
    List<ProcessTaskDto> findMyUpcomingPage(Map<String,Object> params);

    /**
     * 我的已办列表
     * @param params
     * @return
     */
    List<ProcessTaskDto> findMyDoneList(Map<String,Object> params);

    /**
     * 更新当前操作流程的业务表在审批过程中可更改的信息
     * @param params
     * @return
     */
    int updateChangeBusInfo(Map<String,Object> params);

}

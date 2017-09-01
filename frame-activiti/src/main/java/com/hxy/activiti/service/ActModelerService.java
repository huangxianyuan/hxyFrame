package com.hxy.activiti.service;

import com.hxy.activiti.dto.ProcessNodeDto;
import com.hxy.activiti.dto.ProcessTaskDto;
import com.hxy.activiti.entity.ExtendActModelEntity;
import com.hxy.base.page.Page;
import com.hxy.base.utils.Result;
import com.hxy.base.utils.StringUtils;
import com.hxy.sys.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类的功能描述.
 * activiti模型接口类
 * @Auther hxy
 * @Date 2017/7/18
 */

public interface ActModelerService {
    /**
     * 创建模型
     * @param extendActModelEntity
     * @return
     */
    String CreateModeler(ExtendActModelEntity extendActModelEntity) throws  Exception;

    /**
     *获取流程图所有节点和连线
     * @param modelId 模型id
     * @return
     */
    List<Map<String,String>> getflows(String modelId) throws Exception;

    /**
     * 查看流程图片
     * @param modelId
     * @return
     */
    ResponseEntity<byte[]> showFlowImg(String modelId);

    /**
     * 根据流程key 获取业务可用的流程
     * @param actKey
     * @return
     */
    List<Map<String,Object>> queryFlowsByActKey(String actKey);

    /**
     * 获取流程第一个节点信息
     * @param deployId
     * @return
     */
    Result getStartFlowInfo(String deployId) throws IOException;

    /**
     * 根据节点id分页查询可以选择的审批人
     * @param nodeId
     * @param pageNum
     * @param userName
     * @return
     */
    Page<UserEntity> userWindowPage(String nodeId, int pageNum, String userName);

    /**
     * 转办变更人选择 目前做成 可以选择所有人
     * @param pageNum
     * @param userEntity
     * @return
     */
    Page<UserEntity> turnWindowPage(int pageNum,UserEntity userEntity);

    /**
     * 启动流程
     * @param processTaskDto
     */
    void startFlow(ProcessTaskDto processTaskDto) throws Exception;

    /**
     * 根据流程实例id查询实时的流程图
     * @param processInstanceId
     * @return
     */
    InputStream getFlowImgByInstantId(String processInstanceId);

    /**
     * 我的待办列表
     * @param params
     * @param pageNum
     * @return
     */
    Page<ExtendActModelEntity> findMyUpcomingPage(Map<String,Object> params, int pageNum);

    /**
     * 我的代办条数
     * @return
     */
    int myUpcomingCount();


    /**
     * 我的已办列表
     * @param params
     * @param pageNum
     * @return
     */
    Page<ExtendActModelEntity> myDonePage(Map<String,Object> params, int pageNum);



    /**
     * 根据流程节点id 获取流程下一流向节点集合
     * @param processTaskDto
     * @return
     */
    List<ProcessNodeDto> getNextActNodes(ProcessTaskDto processTaskDto);

    /**
     * 根据当前节点,获取下一流向所有的字段变量名
     * @param nodeId 当前节点id
     * @param defId 流程定义id
     * @return
     */
    Set<String> getNextVarNams(String nodeId, String defId);

    /**
     * 办理任务
     * @param processTaskDto
     * @param params
     */
    void doActTask(ProcessTaskDto processTaskDto,Map<String,Object> params) throws Exception;

    /**
     * 驳回到流程上一步
     * @param processTaskDto
     */
    void backPreviousNode(ProcessTaskDto processTaskDto);

    /**
     * 不同意,直接结束流程,业务记录进入可编辑状态，可以修改业务数据后再提交流程
     * @param processTaskDto
     * @param map
     */
    void endFailFolw(ProcessTaskDto processTaskDto,Map<String,Object> map) throws Exception;

    /**
     * 转办
     * @param processTaskDto
     * @param toUserId 被转办人
     */
    void turnToDo(ProcessTaskDto processTaskDto,String toUserId);









}

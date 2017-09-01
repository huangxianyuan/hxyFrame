package com.hxy.activiti.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hxy.activiti.annotation.ActField;
import com.hxy.activiti.annotation.ActTable;
import com.hxy.activiti.dao.ActExtendDao;
import com.hxy.activiti.dao.ExtendActModelDao;
import com.hxy.activiti.dto.ProcessNodeDto;
import com.hxy.activiti.dto.ProcessTaskDto;
import com.hxy.activiti.entity.*;
import com.hxy.activiti.service.*;
import com.hxy.activiti.utils.ActUtils;
import com.hxy.activiti.utils.AnnotationUtils;
import com.hxy.base.common.Constant;
import com.hxy.base.entity.TableInfo;
import com.hxy.base.exception.MyException;
import com.hxy.base.page.Page;
import com.hxy.base.page.PageHelper;
import com.hxy.base.utils.Result;
import com.hxy.base.utils.Utils;
import com.hxy.sys.dao.NoticeUserDao;
import com.hxy.sys.entity.NoticeEntity;
import com.hxy.sys.entity.NoticeUserEntity;
import com.hxy.sys.entity.UserEntity;
import com.hxy.sys.service.NoticeService;
import com.hxy.utils.CodeUtils;
import com.hxy.utils.UserUtils;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 类的功能描述.
 * activiti模型接口实现类
 * @Auther hxy
 * @Date 2017/7/18
 */
@Service
public class ActModelerServiceImpl implements ActModelerService{

    @Resource(name = "repositoryService")
    private RepositoryService repositoryService;

    @Resource(name = "objectMapper")
    private ObjectMapper objectMapper;

    @Autowired
    private ExtendActModelDao extendActModelDao;

    @Autowired
    private ActExtendDao actExtendDao;

    @Autowired
    private ExtendActNodesetService nodesetService;

    @Autowired
    private ExtendActBusinessService businessService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    protected ExtendActTasklogService tasklogService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ExtendActFlowbusService flowbusService;

    @Autowired
    private ExtendActNodefieldService fieldService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeUserDao noticeUserDao;


    @Override
    @Transactional
    public String CreateModeler(ExtendActModelEntity extendActModelEntity) throws Exception {
        //editorInfo
        ObjectNode editorNode = objectMapper.createObjectNode();
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);

        //构建模型
        Model model = repositoryService.newModel();

        //metaInfo 元信息
        ObjectNode metaNode = objectMapper.createObjectNode();
        metaNode.put(ModelDataJsonConstants.MODEL_NAME,extendActModelEntity.getName());
        metaNode.put(ModelDataJsonConstants.MODEL_REVISION,model.getVersion());
        metaNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,extendActModelEntity.getRemark());
        model.setName(extendActModelEntity.getName());
        model.setMetaInfo(metaNode.toString());

        //保存模型
        repositoryService.saveModel(model);
        repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));

        //保存模型扩展表
        extendActModelEntity.setActVersion(model.getVersion());
        extendActModelEntity.setModelId(model.getId());
        extendActModelEntity.setStatus(StringUtils.isEmpty(model.getDeploymentId())? Constant.YESNO.NO.getValue():Constant.YESNO.YES.getValue());
        extendActModelEntity.setDeploymentId(model.getDeploymentId());
        extendActModelDao.save(extendActModelEntity);
        return model.getId();
    }

    @Override
    public  List<Map<String,String>> getflows(String modelId) throws Exception {
        String contextPath = request.getContextPath();
        //转换
        JsonNode jsonNode = objectMapper.readTree(repositoryService.getModelEditorSource(modelId));
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
        //取第一个流程,注：不包括子流程 待开发
        if(bpmnModel.getProcesses().size()<1){
            return null;
        }
        Process process = bpmnModel.getProcesses().get(0);
        Collection<FlowElement> flowElements = process.getFlowElements();
        //取得其中关键数据
        List<Map<String,String>> lists=new ArrayList<>();
        Map<String,String> tempmap=null;
        Map<String, Map<String,String>> allmap=new HashMap<>();
        for (FlowElement flowElement : flowElements) {
            tempmap=new HashMap<>();
            tempmap.put("treeId", flowElement.getId());
            tempmap.put("modelId", modelId);
            if(flowElement instanceof StartEvent){
                tempmap.put("treeName", "开始节点");
                tempmap.put("type", "1");
                tempmap.put("icon", contextPath+"/statics/images/sys/none.png");
            }else if(flowElement instanceof UserTask){
                tempmap.put("type", "2");
                tempmap.put("treeName",flowElement.getName());
                tempmap.put("icon", contextPath+"/statics/images/sys/typeuser.png");
            }else if(flowElement instanceof ExclusiveGateway){
                tempmap.put("type", "3");
                tempmap.put("treeName",flowElement.getName());
                tempmap.put("icon", contextPath+"/statics/images/sys/exclusive.png");
            } else if(flowElement instanceof SequenceFlow){
                tempmap.put("type", "4");
                tempmap.put("treeName",flowElement.getName());
                tempmap.put("icon", contextPath+"/statics/images/sys/sequenceflow.png");
            }else if(flowElement instanceof EndEvent){
                tempmap.put("type", "5");
                if(StringUtils.isNotEmpty(flowElement.getName())){
                    tempmap.put("treeName",flowElement.getName());
                }else{
                    tempmap.put("treeName","结束");
                }
                tempmap.put("icon", contextPath+"/statics/images/sys/endnone.png");
            }
            String pid="0";
            if(flowElement instanceof SequenceFlow){
                pid=((SequenceFlow) flowElement).getSourceRef();
                tempmap.put("tarid", ((SequenceFlow) flowElement).getTargetRef());
                lists.add(tempmap);
            }else{
                List<SequenceFlow> sqlist= ((FlowNode) flowElement).getIncomingFlows();
                if(sqlist!=null&&sqlist.size()>0){
                    SequenceFlow tem1=sqlist.get(0);
                    pid=tem1.getSourceRef();
                }
            }
            tempmap.put("treePid", pid);
            allmap.put(flowElement.getId(),tempmap);
        }
        for(Map<String,String> map:lists){
            String pid=map.get("treePid");
            //如果该元素的父节点不为空 ，且父节点是 分支类型的
            if(allmap.get(pid)!=null&&"3".equals(allmap.get(pid).get("type"))){
                allmap.get(map.get("tarid")).put("treePid", map.get("treeId"));
            }else{
                allmap.remove( map.get("treeId"));
            }
        }
        lists.clear();
        for (Map.Entry<String, Map<String, String>> entry : allmap.entrySet()) {
            String typex=entry.getValue().get("type");
            if("2".equals(typex)){
                entry.getValue().put("treePid", "0");
            }else if("1".equals(typex)){
                continue;
            }
            lists.add(entry.getValue());
        }
        return lists;
    }

    @Override
    public ResponseEntity<byte[]> showFlowImg(String modelId) {
        if(StringUtils.isEmpty(modelId)){
            throw new MyException("流程模型id不能为空!");
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            byte[] bytes = repositoryService.getModelEditorSourceExtra(modelId);
            return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new MyException("流程图片加载失败!");
        }
    }

    @Override
    public List<Map<String,Object>> queryFlowsByActKey(String actKey) {
        return actExtendDao.queryFlowsByActKey(actKey);
    }

    @Override
    public Result getStartFlowInfo(String deployId) throws IOException {
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deployId).singleResult();
        Model model = repositoryService.createModelQuery().deploymentId(deployment.getId()).singleResult();
        byte[] bytes = repositoryService.getModelEditorSource(model.getId());
        JsonNode jsonNode = objectMapper.readTree(bytes);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
        Process process = bpmnModel.getProcesses().get(0);
        List<FlowElement> flowElements = (List<FlowElement>)process.getFlowElements();
        //流程的开始节点
        StartEvent startEvent = null;
        for (FlowElement flowElement : flowElements) {
            if(flowElement instanceof StartEvent){
                startEvent = (StartEvent)flowElement;
                break;
            }
        }
        FlowElement fe = null;
        //获取开始的出口流向
        SequenceFlow sequenceFlow = startEvent.getOutgoingFlows().get(0);
        for (FlowElement flowElement : flowElements) {
            //抛出异常，开始节点后的第一个节点不为userTask
            if(flowElement.getId().equals(sequenceFlow.getTargetRef())){
                if(flowElement instanceof UserTask || flowElement instanceof EndEvent){
                    fe =flowElement;
                    break;
                } else{
                    throw new MyException("流程设计错误，【开始】之后只能是审批节点或结束节点");
                }
            }
        }
        if(fe!=null){
            //查询该节点的类型
            ExtendActNodesetEntity nodesetEntity = nodesetService.queryByNodeIdModelId(fe.getId(), model.getId());
            if(nodesetEntity == null || StringUtils.isEmpty(nodesetEntity.getNodeType())){
                throw new MyException("流程设计错误，【开始】之后只能是审批节点或结束节点");
            }
            return Result.ok().put("nodeId",fe.getId()).put("modelId",model.getId()).put("nodeType",nodesetEntity.getNodeType()).put("nodeAction",nodesetEntity.getNodeAction());
        }
        return null;
    }

    @Override
    public Page<UserEntity> userWindowPage(String nodeId, int pageNum, String userName) {
        PageHelper.startPage(pageNum,Constant.pageSize);
        Map<String,Object> params = new HashMap<>();
        params.put("nodeId",nodeId);
        params.put("userName",userName);
        actExtendDao.userWindowPage(params);
        return PageHelper.endPage();
    }

    @Override
    public Page<UserEntity> turnWindowPage(int pageNum,UserEntity userEntity) {
        PageHelper.startPage(pageNum,Constant.pageSize);
        actExtendDao.turnWindowList(userEntity);
        return PageHelper.endPage();
    }

    /**
     * 方法更改了的表
     * 1.当前业务表流程相关信息
     * 2.流程业务关系表extend_act_flowbus
     * 3.流程任务扩展表extend_act_tasklog
     * @param processTaskDto
     * @throws Exception
     */
    @Override
    @Transactional
    public void startFlow(ProcessTaskDto processTaskDto) throws Exception {
        if(StringUtils.isEmpty(processTaskDto.getActKey())){
            throw new MyException("流程actKey不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getBusId())){
            throw new MyException("业务ID不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getDefId())){
            throw new MyException("流程定义ID不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getNodeType())){
            throw new MyException("节点类型不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getNextUserIds())&&!"5".equals(processTaskDto.getNodeType())){
            throw new MyException("处理人不能为空");
        }
        //查询流程业务关联信息
        ExtendActBusinessEntity businessEntity = businessService.queryByActKey(processTaskDto.getActKey());
        Class<?> clazz = Class.forName(businessEntity.getClassurl());
        ActTable actTable=clazz.getAnnotation(ActTable.class);
        Map<String,Object> params = new HashMap<>();
        params.put(TableInfo.TAB_TABLENAME,actTable.tableName());
        params.put(TableInfo.TAB_PKNAME,actTable.pkName());
        params.put(TableInfo.TAB_ID,processTaskDto.getBusId());
        //查询当前要提交流程的业务记录
        Map<String, Object> busInfo = actExtendDao.queryBusiByBusId(params);
        Method[] methods = clazz.getDeclaredMethods();
        //读取需要判断的条件字段，做为流程变量
        Map<String, Object> variables = new HashMap<String, Object>();
        for (Method method:methods){
            ActField actField = method.getAnnotation(ActField.class);
            if(actField != null && actField.isJudg()){
                String flidName = method.getName().replace("get","");
                variables.put(flidName,busInfo.get(flidName));
            }
        }
        //启动流程并设置启动变量（条件变量）
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processTaskDto.getDefId(), processTaskDto.getBusId(), variables);
        processTaskDto.setInstanceId(processInstance.getId());
        //更新当前业务表
        Date curentTime = new Date();
        Map<String,Object> busParams = new HashMap<>();
        busParams.put("instanceId",processInstance.getId());
        busParams.put("defid",processTaskDto.getDefId());
        busParams.put("startUserId", UserUtils.getCurrentUserId());
        busParams.put("status",Constant.ActStauts.APPROVAL.getValue());
        busParams.put("startTime",curentTime);
        busParams.put(TableInfo.TAB_TABLENAME,actTable.tableName());
        busParams.put(TableInfo.TAB_PKNAME,actTable.pkName());
        busParams.put(TableInfo.TAB_ID,processTaskDto.getBusId());
        actExtendDao.updateBusInfo(busParams);
        //保存任务日志表
        ExtendActTasklogEntity tasklog = new ExtendActTasklogEntity();
        tasklog.setId(Utils.uuid());
        tasklog.setBusId(processTaskDto.getBusId());
        tasklog.setDefId(processTaskDto.getDefId());
        tasklog.setInstanceId(processTaskDto.getInstanceId());
        tasklog.setTaskName("提交");
        tasklog.setDealTime(curentTime);
        tasklog.setDealId(UserUtils.getCurrentUserId());
        tasklog.setAppAction(Constant.ActTaskResult.AGREE.getValue());
        tasklog.setAppOpinion(processTaskDto.getRemark());
        //保存流程业务关系表
        ExtendActFlowbusEntity flowBus = new ExtendActFlowbusEntity();
        flowBus.setId(Utils.uuid());
        flowBus.setBusId(processTaskDto.getBusId());
        flowBus.setDefid(processTaskDto.getDefId());
        flowBus.setInstanceId(processTaskDto.getInstanceId());
        flowBus.setStartTime(curentTime);
        flowBus.setActKey(processTaskDto.getActKey());
        flowBus.setCode((String) busInfo.get("code"));
        flowBus.setStatus(Constant.ActStauts.APPROVAL.getValue());
        flowBus.setStartUserId(UserUtils.getCurrentUserId());
        flowBus.setTableName(actTable.tableName());
        flowbusService.save(flowBus);
        //代理人设置 待完善
        // TODO: 2017/8/4  代理人设置 待完善
        //如果第一个节点是审批节点
        if(Constant.NodeType.EXAMINE.getValue().equals(processTaskDto.getNodeType())){
            List<Task> tasks = taskService.createTaskQuery().processDefinitionId(processTaskDto.getDefId()).processInstanceId(processTaskDto.getInstanceId()).list();
            for (Task task:tasks){
                //设置下一个任务处理人
                taskService.setAssignee(task.getId(),processTaskDto.getNextUserIds());
                //记录任务日志
                ExtendActTasklogEntity tasklogEntity = new ExtendActTasklogEntity();
                tasklogEntity.setId(Utils.uuid());
                tasklogEntity.setBusId(processTaskDto.getBusId());
                tasklogEntity.setDefId(processTaskDto.getDefId());
                tasklogEntity.setInstanceId(processTaskDto.getInstanceId());
                tasklogEntity.setTaskId(task.getId());
                tasklogEntity.setTaskName(task.getName());
                tasklogEntity.setAdvanceId(processTaskDto.getNextUserIds());
                tasklogEntity.setCreateTime(task.getCreateTime());
                tasklogService.save(tasklogEntity);
            }
            //提交之后，更改业务审批状态为审批中，审批结果也为审批中
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put(TableInfo.TAB_TABLENAME, actTable.tableName());
            updateMap.put(TableInfo.TAB_PKNAME, actTable.pkName());
            updateMap.put(TableInfo.TAB_ID, processTaskDto.getBusId());
            updateMap.put("status", Constant.ActStauts.APPROVAL.getValue());
            updateMap.put("actResult",Constant.ActResult.DISAGREE.getValue());
            actExtendDao.updateBusInfo(updateMap);
            //如果第一个节点是结束节点 也就是空流程
        }else if(Constant.NodeType.END.getValue().equals(processTaskDto.getNodeType())){
            tasklog.setAppOpinion("空流程结束");
            //流程完成后，更改当前业务表的流程信息
            busParams.put("status",Constant.ActStauts.END.getValue());
            busParams.put("actResult",Constant.ActResult.AGREE.getValue());
            busParams.put(TableInfo.TAB_TABLENAME,actTable.tableName());
            busParams.put(TableInfo.TAB_PKNAME,actTable.pkName());
            busParams.put(TableInfo.TAB_ID,processTaskDto.getBusId());
            actExtendDao.updateBusInfo(busParams);
            //更新流程业务关系表
            ExtendActFlowbusEntity flowbusEntity = new ExtendActFlowbusEntity();
            flowbusEntity.setBusId(processTaskDto.getBusId());
            flowbusEntity.setStatus(Constant.ActStauts.END.getValue());
            flowbusService.updateByBusId(flowbusEntity);
            //获取下一个人工任务节点，进行回调执行
            ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processTaskDto.getDefId());
            //获取流程定义的全部节点
            List<ActivityImpl> activities = processDefinitionEntity.getActivities();
            for(ActivityImpl node:activities){
                //节点类型为结束节点
                String type = (String)node.getProperty("type");
                if("endEvent".equals(type)){
                    //查询流程配置的回调函数
                    ExtendActNodesetEntity nodesetEntity = nodesetService.queryByNodeId(node.getId());
                    //如果该节点配置有回调函数，则执行回调
                    if(nodesetEntity != null && StringUtils.isNotEmpty(nodesetEntity.getCallBack())){
                        if(StringUtils.isNotEmpty(nodesetEntity.getCallBack())){
                            executeCallback(nodesetEntity.getCallBack(),processTaskDto);
                        }
                    }
                    //流程结束发送通知 待完善
                    // TODO: 2017/8/4 流程结束发送通知 待完善
                }
            }
        }else {
            throw new MyException("流程设计错误!");
        }
        //保存扩展任务日志
        tasklogService.save(tasklog);
    }

    @Override
    public InputStream getFlowImgByInstantId(String processInstanceId) {
        if(StringUtils.isEmpty(processInstanceId)){
            throw new MyException("获取流程图片失败，流程实例不能为空!");
        }
        return ActUtils.getFlowImgByInstantId(processInstanceId);
    }

    @Override
    public Page<ExtendActModelEntity> findMyUpcomingPage(Map<String,Object> params,int pageNum) {
        PageHelper.startPage(pageNum, Constant.pageSize);
        //超级管理员可查看所有待办
        if(!Constant.SUPERR_USER.equals(UserUtils.getCurrentUserId())){
            params.put("dealId",UserUtils.getCurrentUserId());
        }
        actExtendDao.findMyUpcomingPage(params);
        return PageHelper.endPage();
    }

    @Override
    public int myUpcomingCount() {
        Map<String,Object> params = new HashMap<>();
        //超级管理员可查看所有待办
        if(!Constant.SUPERR_USER.equals(UserUtils.getCurrentUserId())){
            params.put("dealId",UserUtils.getCurrentUserId());
        }
        int count = 0;
        List<ProcessTaskDto> myUpcomingPage = actExtendDao.findMyUpcomingPage(params);
        if(myUpcomingPage != null){
            count=myUpcomingPage.size();
        }
        return count;
    }

    @Override
    public Page<ExtendActModelEntity> myDonePage(Map<String, Object> params, int pageNum) {
        PageHelper.startPage(pageNum, Constant.pageSize);
        //超级管理员可查看所有待办
        if(!Constant.SUPERR_USER.equals(UserUtils.getCurrentUserId())){
            params.put("dealId",UserUtils.getCurrentUserId());
        }
        actExtendDao.findMyDoneList(params);
        return PageHelper.endPage();
    }

    @Override
    public List<ProcessNodeDto> getNextActNodes(ProcessTaskDto processTaskDto) {
        if(StringUtils.isEmpty(processTaskDto.getDefId())){
            throw  new MyException("流程定义id不能为空!");
        }
        if(StringUtils.isEmpty(processTaskDto.getTaskId())){
            throw  new MyException("流程任务id不能为空!");
        }
        Task task = taskService.createTaskQuery().taskId(processTaskDto.getTaskId()).singleResult();
        String[] varNames=processTaskDto.getVarName().split(",");
        String[] varValues=processTaskDto.getVarValue().split(",");
        Map<String,Object> elMap = new HashMap<>();
        for (int i=0;i<varNames.length;i++){
            if(StringUtils.isEmpty(varNames[i])){
                continue;
            }
            elMap.put(varNames[i],varValues[i]);
        }
        List<PvmActivity> pvmActivities = ActUtils.getNextActNodes(processTaskDto.getDefId(), task.getTaskDefinitionKey(), elMap);
        List<ProcessNodeDto> listNode = new ArrayList<>();
        ProcessNodeDto processNodeDto= null;
        for (PvmActivity pvm:pvmActivities){
            processNodeDto = new ProcessNodeDto();
            processNodeDto.setNodeId(pvm.getId());
            processNodeDto.setNodeName((String) pvm.getProperty("name"));
            ExtendActNodesetEntity nodeSet = nodesetService.queryByNodeId(pvm.getId());
            processNodeDto.setNodeTypeName(CodeUtils.getCodeName("act_node_type",nodeSet.getNodeType()));
            processNodeDto.setNodeActionName(CodeUtils.getCodeName("act_node_action",nodeSet.getNodeAction()));
            processNodeDto.setNodeAction(nodeSet.getNodeAction());
            processNodeDto.setNodeType(nodeSet.getNodeType());
            listNode.add(processNodeDto);
        }
        return listNode;
    }

    @Override
    public Set<String> getNextVarNams(String nodeId,String defId) {
        if(StringUtils.isEmpty(defId)){
            throw new MyException("流程定义不能为空!");
        }
        if(StringUtils.isEmpty(nodeId)){
            throw new MyException("流程节点不能为空!");
        }
        List<PvmTransition> nextPvmTransitions = ActUtils.getNextPvmTransitions(defId, nodeId);
        List<String> nextIds = new ArrayList<>();
        for (PvmTransition pvmTransition:nextPvmTransitions){
            nextIds.add(pvmTransition.getId());
        }
        if(nextIds.size()<1){
            return null;
        }
        List<ExtendActNodefieldEntity> nodeFields = fieldService.queryByNodes(nextIds);
        //节点可更改的变量字段数组
        Set<String> vars = new HashSet<>();
        for (ExtendActNodefieldEntity nodefield:nodeFields){
            vars.add(nodefield.getFieldName());
        }
        return vars;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void doActTask(ProcessTaskDto processTaskDto,Map<String,Object> map) throws Exception {
        if(StringUtils.isEmpty(processTaskDto.getTaskId())){
            throw new MyException("流程任务id不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getInstanceId())){
            throw new MyException("流程实例id不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getDefId())){
            throw new MyException("流程定义id不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getBusId())){
            throw new MyException("业务id不能为空");
        }
        //根据流程定义id查询流程定义key
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processTaskDto.getDefId()).singleResult();
        Task task = taskService.createTaskQuery().taskId(processTaskDto.getTaskId()).singleResult();
        //保存审批信息
        String remark="";
        if(StringUtils.isNotEmpty(processTaskDto.getRemark())){
            remark=processTaskDto.getRemark();
            taskService.addComment(processTaskDto.getTaskId(),processTaskDto.getInstanceId(),remark);
        }
        //查询流程业务关联信息
        ExtendActBusinessEntity businessEntity = businessService.queryByActKey(processDefinition.getKey());
        ExtendActNodesetEntity nodesetEntity = nodesetService.queryByNodeId(task.getTaskDefinitionKey());

        Class<?> clazz = Class.forName(businessEntity.getClassurl());
        ActTable actTable=clazz.getAnnotation(ActTable.class);
        //更改的业务字段文本描述
        String filedText ="";
        //更改的值不为空
        if(StringUtils.isNotEmpty(nodesetEntity.getChangeFiles())){
            //保存流程更改过的业务记录信息
            filedText=changeFields(actTable,processTaskDto.getBusId(),nodesetEntity.getChangeFiles(),businessEntity.getClassurl(),map);
        }
        //流程变量
        Map<String,Object> elMap = new HashMap<>();
        //获取流程变量
        if(StringUtils.isNotEmpty(processTaskDto.getVarName())){
            String[] varNames=processTaskDto.getVarName().split(",");
            String[] varValues=processTaskDto.getVarValue().split(",");
            for (int i=0;i<varNames.length;i++){
                if(StringUtils.isEmpty(varNames[i])){
                    continue;
                }
                elMap.put(varNames[i],varValues[i]);
            }
        }
        //获取下个节点信息
        List<PvmActivity> pvmActivities = ActUtils.getNextActNodes(processTaskDto.getDefId(), task.getTaskDefinitionKey(), elMap);
        for (PvmActivity pvmActivity:pvmActivities) {
            //下一节点为结束节点时，完成任务更新业务表
            if ("endEvent".equals(pvmActivity.getProperty("type"))) {
                //查询结束节点信息（主要查询回调）
                ExtendActNodesetEntity endNodeSet = nodesetService.queryByNodeId(pvmActivity.getId());
                //提交任务设置流程变量
                taskService.complete(task.getId(), elMap);
                //当前节点为会签节点时
                if (Constant.ActAction.MULIT.getValue().equals(nodesetEntity.getNodeAction())) {
                    //检测任务是否全部提交完
                    //会签是否全部执行完
                    boolean isAllCompleted = true;
                    //查询实例中的任务是否全部提交完
                    List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).list();
                    for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
                        if (historicTaskInstance.getEndTime() == null) {
                            isAllCompleted = false;
                            break;
                        }
                    }
                    //全部执行完，修改业务表
                    if (isAllCompleted) {
                        Map<String, Object> updateMap = new HashMap<>();
                        updateMap.put(TableInfo.TAB_TABLENAME, actTable.tableName());
                        updateMap.put(TableInfo.TAB_PKNAME, actTable.pkName());
                        updateMap.put(TableInfo.TAB_ID, processTaskDto.getBusId());
                        updateMap.put("status", Constant.ActStauts.END.getValue());
                        updateMap.put("actResult",Constant.ActResult.AGREE.getValue());
                        actExtendDao.updateBusInfo(updateMap);
                        ExtendActFlowbusEntity flowbusEntity = new ExtendActFlowbusEntity();
                        flowbusEntity.setStatus(Constant.ActStauts.END.getValue());
                        flowbusEntity.setBusId(processTaskDto.getBusId());
                        flowbusService.updateByBusId(flowbusEntity);
                        //会签节点结束后,执行当前节点上的回调
                        if(StringUtils.isNotEmpty(nodesetEntity.getCallBack())){
                            executeCallback(nodesetEntity.getCallBack(),processTaskDto);
                        }
                        //执行结束节点回调
                        if(StringUtils.isNotEmpty(endNodeSet.getCallBack())){
                            executeCallback(endNodeSet.getCallBack(),processTaskDto);
                        }
                    }
                } else if (Constant.ActAction.APPROVE.getValue().equals(nodesetEntity.getNodeAction())) {
                    //当前节点为普通审批节点
                    Map<String, Object> updateMap = new HashMap<>();
                    updateMap.put(TableInfo.TAB_TABLENAME, actTable.tableName());
                    updateMap.put(TableInfo.TAB_PKNAME, actTable.pkName());
                    updateMap.put(TableInfo.TAB_ID, processTaskDto.getBusId());
                    updateMap.put("status", Constant.ActStauts.END.getValue());
                    updateMap.put("actResult",Constant.ActResult.AGREE.getValue());
                    actExtendDao.updateBusInfo(updateMap);
                    ExtendActFlowbusEntity flowbusEntity = new ExtendActFlowbusEntity();
                    flowbusEntity.setStatus(Constant.ActStauts.END.getValue());
                    flowbusEntity.setBusId(processTaskDto.getBusId());
                    flowbusService.updateByBusId(flowbusEntity);
                    //执行当前节点上的回调
                    if(StringUtils.isNotEmpty(nodesetEntity.getCallBack())){
                        executeCallback(nodesetEntity.getCallBack(),processTaskDto);
                    }
                    //执行结束节点回调
                    if(StringUtils.isNotEmpty(endNodeSet.getCallBack())){
                        executeCallback(endNodeSet.getCallBack(),processTaskDto);
                    }
                }
                //流程结束可以在这里写一些通知信息
                ExtendActFlowbusEntity flowBus = flowbusService.queryByBusIdInsId(processTaskDto.getInstanceId(), processTaskDto.getBusId());
                sendNoticeMsg(flowBus.getStartUserId(),businessEntity);
            } else {
              //下一个节点不为结束节点
                //查询下个节点信息
                ExtendActNodesetEntity nextNode = nodesetService.queryByNodeId((String) pvmActivity.getId());
                //下一级节点为会签节点
                if(Constant.ActAction.MULIT.getValue().equals(nextNode.getNodeAction())){
                    // TODO: 2017/8/10 暂不支持当前节点为会签，下一级节点也为会签
                    //设置会签人员集
                    String[] nextUsers = processTaskDto.getNextUserIds().split(",");
                    Map<String,Object> userMap = new HashMap<>();
                    userMap.put(Constant.ACT_MUIT_LIST_NAME,Arrays.asList(nextUsers));
                    userMap.putAll(elMap);
                    //完成任务并设置流程变量
                    taskService.complete(task.getId(),userMap);
                    //查询流程所有任务
                    List<Task> taskList = taskService.createTaskQuery().processInstanceId(processTaskDto.getInstanceId()).list();
                    for (Task t:taskList){
                        //记录任务日志
                        ExtendActTasklogEntity tasklogEntity = new ExtendActTasklogEntity();
                        tasklogEntity.setId(Utils.uuid());
                        tasklogEntity.setBusId(processTaskDto.getBusId());
                        tasklogEntity.setDefId(processTaskDto.getDefId());
                        tasklogEntity.setInstanceId(processTaskDto.getInstanceId());
                        tasklogEntity.setTaskId(t.getId());
                        tasklogEntity.setTaskName(t.getName());
                        tasklogEntity.setAdvanceId(t.getAssignee());
                        tasklogEntity.setCreateTime(task.getCreateTime());
                        tasklogService.save(tasklogEntity);
                    }
                    //下级节点为普通审批节点
                }else if (Constant.ActAction.APPROVE.getValue().equals(nextNode.getNodeAction())){
                     //完成任务
                    taskService.complete(task.getId(),elMap);
                    //会签是否结束
                    boolean isOver = true;
                    //当前节点为会签节点
                    if(Constant.ActAction.MULIT.getValue().equals(nodesetEntity.getNodeAction())){
                        List<Task> nodeTasks = taskService.createTaskQuery().taskDefinitionKey(nodesetEntity.getNodeId()).processInstanceId(processTaskDto.getInstanceId()).list();
                        if(nodeTasks.size()>0){
                            isOver=false;
                        }
                    }
                    if(isOver){
                        //如果会签已经完成，则记录下一任务日志
                        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processTaskDto.getInstanceId()).list();
                        for (Task t:tasks){
                            //设置下一个任务的办理人
                            // TODO: 2017/8/10 如果是下个节点是并行结果，那么这里需要处理下 待开发
                            taskService.setAssignee(t.getId(), processTaskDto.getNextUserIds());
                            ExtendActTasklogEntity tasklogEntity = new ExtendActTasklogEntity();
                            tasklogEntity.setId(Utils.uuid());
                            tasklogEntity.setBusId(processTaskDto.getBusId());
                            tasklogEntity.setDefId(processTaskDto.getDefId());
                            tasklogEntity.setInstanceId(processTaskDto.getInstanceId());
                            tasklogEntity.setTaskId(t.getId());
                            tasklogEntity.setTaskName(t.getName());
                            tasklogEntity.setAdvanceId(processTaskDto.getNextUserIds());
                            tasklogEntity.setCreateTime(task.getCreateTime());
                            tasklogService.save(tasklogEntity);
                        }
                    }
                }
            }
        }
        //处理任务后，更新任务日志
        ExtendActTasklogEntity tasklogEntity = new ExtendActTasklogEntity();
        tasklogEntity.setTaskId(task.getId());
        tasklogEntity.setDealTime(new Date());
        tasklogEntity.setAppOpinion(remark);
        tasklogEntity.setDealId(UserUtils.getCurrentUserId());
        tasklogEntity.setColumns(filedText.toString());
        tasklogEntity.setAppAction(Constant.ActTaskResult.AGREE.getValue());
        int i = tasklogService.updateByTaskId(tasklogEntity);
        if(i<1){
            throw new MyException("更新任务日志失败");
        }
    }

    /**
     * 发送待办消息
     */
    public void sendNoticeMsg(String userId,ExtendActBusinessEntity businessEntity){
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setTitle("流程通知【"+businessEntity.getName()+"】");
        noticeEntity.setContext("亲，你提交的流程【"+businessEntity.getName()+"】已经审批结束了,请查阅对应模块！");
        noticeEntity.setCreateTime(new Date());
        noticeEntity.setIsUrgent(Constant.YESNO.YES.getValue());
        noticeEntity.setReleaseTimee(new Date());
        noticeEntity.setSoucre(Constant.noticeType.ACT_NOTICE.getValue());
        noticeEntity.setId(Utils.uuid());
        noticeEntity.setStatus(Constant.YESNO.YES.getValue());
        NoticeUserEntity noticeUserEntity = new NoticeUserEntity();
        noticeUserEntity.setId(Utils.uuid());
        noticeUserEntity.setNoticeId(noticeEntity.getId());
        noticeUserEntity.setStatus(Constant.YESNO.NO.getValue());
        noticeUserEntity.setUserId(userId);
        noticeService.save(noticeEntity);
        noticeUserDao.save(noticeUserEntity);
    }


    /**
     * 节点回调方法 执行
     * @param callBack
     * @param processTaskDto
     * @throws Exception
     */
    public void executeCallback(String callBack,ProcessTaskDto processTaskDto) throws Exception {
            int lastIndex = callBack.lastIndexOf(".");
            String methodStr = callBack.substring(lastIndex+1);//方法名
            String classUrl = callBack.substring(0, lastIndex);//类路径
            Class<?> clazz = Class.forName(classUrl);
            Object o = clazz.newInstance();
            //回调方法参数 这里可扩展
            Method method = clazz.getMethod(methodStr, ProcessTaskDto.class);
            method.invoke(o,processTaskDto);

    }

    /**
     * 保存流程更改过的业务记录信息
     * @param actTable
     * @param busId
     * @param changeFieldNames
     * @param classUrl
     * @param map
     */
    public String changeFields(ActTable actTable,String busId,String changeFieldNames,String classUrl,Map<String,Object> map){
        if(StringUtils.isEmpty(changeFieldNames)){
            return "";
        }
        //更改的业务字段文本描述
        StringBuffer filedText =new StringBuffer();
        //查询业务记录
        Map<String,Object> tamap = new HashMap<>();
        tamap.put(TableInfo.TAB_TABLENAME,actTable.tableName());
        tamap.put(TableInfo.TAB_PKNAME,actTable.pkName());
        tamap.put(TableInfo.TAB_ID,busId);
        Map<String, Object> busInfo = actExtendDao.queryBusiByBusId(tamap);
        Map<String,String> textMap = new HashMap<>();
        List<Map<String, Object>> mapList = AnnotationUtils.getActFieldByClazz(classUrl);
        for (Map remap:mapList){
            ActField actField= (ActField) remap.get("actField");
            String keyName = (String) remap.get("keyName");
            if(actField!=null){
                textMap.put(keyName, actField.name());
            }
        }
        //业务可更改的字段
        String[] changeFields = changeFieldNames.split(",");
        //业务可更改的字段和对应更改后的值
        List<TableInfo> fields = new ArrayList<>();
        for (int i =0;i<changeFields.length;i++){
            if(StringUtils.isEmpty(changeFields[i])){
                continue;
            }
            //原值
            Object o = busInfo.get(changeFields[i]);
            //更改后的值
            Object o1 = map.get(changeFields[i]);
            //字段text 例：请假天数
            String text = textMap.get(changeFields[i]);
            if(o instanceof String){
                if(!o.equals(o1)){
                    filedText.append(text+"的原值【"+o+"】,更改后【"+o1+"】;");
                }
            }else if (o instanceof Integer){
                if(((Integer) o).intValue() != Integer.parseInt(((String) o1))){
                    filedText.append(text+"的原值为【"+o+"】,更改为【"+o1+"】;");
                }
            }
            fields.add(new TableInfo(changeFields[i],map.get(changeFields[i])));
        }
        //保存业务更改后的值
        Map<String,Object> params = new HashMap<>();
        params.put(TableInfo.TAB_TABLENAME,actTable.tableName());
        params.put(TableInfo.TAB_PKNAME,actTable.pkName());
        params.put(TableInfo.TAB_ID,busId);
        params.put(TableInfo.TAB_FIELDS,fields);
        int count = actExtendDao.updateChangeBusInfo(params);
        if(count<1){
            throw new MyException("更新业务信息失败");
        }
        return filedText.toString();
    }

    @Override
    public void backPreviousNode(ProcessTaskDto processTaskDto) {
        if(StringUtils.isEmpty(processTaskDto.getTaskId())){
            throw new MyException("任务id不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getDefId())){
            throw new MyException("流程定义id不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getInstanceId())){
            throw new MyException("流程实例不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getInstanceId())){
            throw new MyException("业务id不能为空");
        }
        Task task = taskService.createTaskQuery().taskId(processTaskDto.getTaskId()).singleResult();
        //ActUtils.getProActivityId(processTaskDto.getDefId(),task.getTaskDefinitionKey(),);
    }

    @Override
    @Transactional
    public void endFailFolw(ProcessTaskDto processTaskDto,Map<String,Object> map) throws Exception {
        if(StringUtils.isEmpty(processTaskDto.getTaskId())){
            throw new MyException("任务id不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getDefId())){
            throw new MyException("流程定义id不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getInstanceId())){
            throw new MyException("流程实例不能为空");
        }
        //查询流程业务基本信息
        Task task = taskService.createTaskQuery().taskId(processTaskDto.getTaskId()).singleResult();
        ExtendActNodesetEntity nodesetEntity = nodesetService.queryByNodeId(task.getTaskDefinitionKey());
        ExtendActBusinessEntity actBus = businessService.queryByActKey(ActUtils.findProcessDefinitionEntityByTaskId(task.getId()).getKey());

        ActivityImpl endNode = ActUtils.findActivitiImpl(processTaskDto.getTaskId(), "end");
        ActUtils.turnTransition(processTaskDto.getTaskId(),endNode.getId(),null,processTaskDto.getRemark());
        //更新流程扩展日志表
        ExtendActTasklogEntity taskLog = new ExtendActTasklogEntity();
        Date date = new Date();
        taskLog.setTaskId(processTaskDto.getTaskId());
        taskLog.setAppOpinion(processTaskDto.getRemark());
        taskLog.setDealId(UserUtils.getCurrentUserId());
        taskLog.setDealTime(date);
        taskLog.setAppAction(Constant.ActTaskResult.TURN_DOWN.getValue());
        tasklogService.updateByTaskId(taskLog);
        Class<?> aClass = Class.forName(actBus.getClassurl());
        ActTable actTable = aClass.getAnnotation(ActTable.class);
        //保存流程更改过的业务记录信息
        changeFields(actTable,processTaskDto.getBusId(),nodesetEntity.getChangeFiles(),actBus.getClassurl(),map);
        //更新业务状态信息 为已经审批
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put(TableInfo.TAB_TABLENAME, actTable.tableName());
        updateMap.put(TableInfo.TAB_PKNAME, actTable.pkName());
        updateMap.put(TableInfo.TAB_ID, processTaskDto.getBusId());
        updateMap.put("status", Constant.ActStauts.END.getValue());
        updateMap.put("actResult",Constant.ActResult.NO_AGREE.getValue());
        actExtendDao.updateBusInfo(updateMap);
        ExtendActFlowbusEntity flowbusEntity = new ExtendActFlowbusEntity();
        flowbusEntity.setStatus(Constant.ActStauts.END.getValue());
        flowbusEntity.setBusId(processTaskDto.getBusId());
        flowbusService.updateByBusId(flowbusEntity);
    }

    @Override
    @Transactional
    public void turnToDo(ProcessTaskDto processTaskDto,String toUserId) {
        if(StringUtils.isEmpty(processTaskDto.getTaskId())){
            throw new MyException("任务id不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getDefId())){
            throw new MyException("流程定义id不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getInstanceId())){
            throw new MyException("流程实例不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getInstanceId())){
            throw new MyException("业务id不能为空");
        }
        //转办任务
        Task task = taskService.createTaskQuery().taskId(processTaskDto.getTaskId()).singleResult();
        //转办
        ActUtils.transferAssignee(processTaskDto.getTaskId(),toUserId);
        //记录任务日志 先更新当前任务的日志，再为转办后任务新增一条任务日志
        ExtendActTasklogEntity updateLog = new ExtendActTasklogEntity();
        updateLog.setAppAction(Constant.ActTaskResult.TURN_DO.getValue());
        updateLog.setTaskId(task.getId());
        updateLog.setAppOpinion(processTaskDto.getRemark());
        updateLog.setDealId(UserUtils.getCurrentUserId());
        updateLog.setDealTime(new Date());
        tasklogService.updateByTaskIdOpinion(updateLog);
        //为置办任务新增一条任务日志
        ExtendActTasklogEntity tasklogEntity = new ExtendActTasklogEntity();
        tasklogEntity.setId(Utils.uuid());
        tasklogEntity.setBusId(processTaskDto.getBusId());
        tasklogEntity.setDefId(processTaskDto.getDefId());
        tasklogEntity.setInstanceId(processTaskDto.getInstanceId());
        tasklogEntity.setTaskId(task.getId());
        tasklogEntity.setTaskName(task.getName());
        tasklogEntity.setAdvanceId(toUserId);
        tasklogEntity.setCreateTime(task.getCreateTime());
        tasklogService.save(tasklogEntity);
    }
}

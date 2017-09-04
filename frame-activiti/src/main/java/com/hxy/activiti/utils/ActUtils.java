package com.hxy.activiti.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hxy.base.common.Constant;
import com.hxy.base.exception.MyException;
import com.hxy.base.utils.SpringContextUtils;
import com.hxy.base.utils.StringUtils;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.engine.task.Task;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 类的功能描述.
 * 流程工具类
 * @Auther hxy
 * @Date 2017/7/28
 */
public class ActUtils {
    private static RepositoryService repositoryService = (RepositoryService) SpringContextUtils.getBean("repositoryService");
    private static HistoryService historyService = (HistoryService) SpringContextUtils.getBean("historyService");
    private static RuntimeService runtimeService = (RuntimeService) SpringContextUtils.getBean("runtimeService");
    private static TaskService taskService = (TaskService) SpringContextUtils.getBean("taskService");
    private static ObjectMapper objectMapper = (ObjectMapper) SpringContextUtils.getBean("objectMapper");
    private static ProcessEngineConfiguration processEngineConfiguration = (ProcessEngineConfiguration) SpringContextUtils.getBean("processEngineConfiguration");

    /**
     * 设置会签节点属性
     * 会签相关变量注释：nrOfInstances：实例总数
     *               nrOfActiveInstances：当前活动的，比如，还没完成的，实例数量。 对于顺序执行的多实例，值一直为1
     *               nrOfCompletedInstances：已经完成实例的数目
     *               可以通过execution.getVariable(x)方法获得这些变量
     * @param modelId 模型id
     * @param nodelId 流程对象id
     */
    public static void setMultiInstance(String modelId,String nodelId) throws Exception{
        //获取模型
        byte[] mes = repositoryService.getModelEditorSource(modelId);
        //转换成JsonNode
        JsonNode jsonNode = objectMapper.readTree(mes);
        //转换成BpmnModel
        BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
        BpmnModel bpmnModel = bpmnJsonConverter.convertToBpmnModel(jsonNode);
        //获取物理形态的流程
        Process process = bpmnModel.getProcesses().get(0);
        //获取节点信息
        FlowElement flowElement = process.getFlowElement(nodelId);
        //只有人工任务才可以设置会签节点
        UserTask userTask = (UserTask)flowElement;
        //设置受理人，这里应该和ElementVariable的值是相同的
        userTask.setAssignee("${"+Constant.ACT_MUIT_VAR_NAME+"}");
        //userTask.setOwner("${user}");

        //获取多实例配置
        MultiInstanceLoopCharacteristics characteristics = new MultiInstanceLoopCharacteristics();
        //设置集合变量，统一设置成users
        characteristics.setInputDataItem(Constant.ACT_MUIT_LIST_NAME);
        //设置变量
        characteristics.setElementVariable(Constant.ACT_MUIT_VAR_NAME);
        //设置为同时接收（false 表示不按顺序执行）
        characteristics.setSequential(false);
        //设置条件（暂时处理成，全部会签完转下步）
        characteristics.setCompletionCondition("${nrOfCompletedInstances==nrOfInstances}");

        userTask.setLoopCharacteristics(characteristics);
        //保存
        ObjectNode objectNode = new BpmnJsonConverter().convertToJson(bpmnModel);
        repositoryService.addModelEditorSource(modelId, objectNode.toString().getBytes("utf-8"));
    }

    /**
     * 清空会签属性
     * @param modelId 模型id
     * @param nodelId 流程对象id
     * @throws Exception
     */
    public static void clearMultiInstance(String modelId,String nodelId) throws Exception{
        //获取模型
        byte[] mes = repositoryService.getModelEditorSource(modelId);
        //转换成JsonNode
        JsonNode jsonNode = new ObjectMapper().readTree(mes);
        //转换成BpmnModel
        BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
        BpmnModel bpmnModel = bpmnJsonConverter.convertToBpmnModel(jsonNode);
        //获取物理形态的流程
        Process process = bpmnModel.getProcesses().get(0);
        //获取节点信息
        FlowElement flowElement = process.getFlowElement(nodelId);
        //只有人工任务才可以设置会签节点
        UserTask userTask = (UserTask)flowElement;
        //清空受理人
        userTask.setAssignee("");
        //获取多实例配置
        MultiInstanceLoopCharacteristics characteristics = userTask.getLoopCharacteristics();
        if(characteristics!=null){
            //清空集合
            characteristics.setInputDataItem("");
            //清空变量
            characteristics.setElementVariable("");
            //设置为顺序接收（true 表示不按顺序执行）
            characteristics.setSequential(true);
            //清空条件
            characteristics.setCompletionCondition("");
        }

        //保存
        ObjectNode objectNode = new BpmnJsonConverter().convertToJson(bpmnModel);
        repositoryService.addModelEditorSource(modelId, objectNode.toString().getBytes("utf-8"));
    }

    /**
     * 增加流程连线条件
     * @param modelId 模型id
     * @param nodelId 流程对象id
     * @param condition el 条件表达式
     */
    public static void setSequenceFlowCondition(String modelId,String nodelId ,String condition) throws IOException {
        //获取模型--设置连线条件 到 流程中
        byte[] bytes = repositoryService.getModelEditorSource(modelId);
        JsonNode jsonNode = objectMapper.readTree(bytes);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
        FlowElement flowElement = bpmnModel.getFlowElement(nodelId);
        if(!(flowElement instanceof SequenceFlow)){
            throw new MyException("不是连线，不能设置条件");
        }
        SequenceFlow sequenceFlow = (SequenceFlow)flowElement;
        sequenceFlow.setConditionExpression(condition);
        ObjectNode objectNode = new BpmnJsonConverter().convertToJson(bpmnModel);
        repositoryService.addModelEditorSource(modelId, objectNode.toString().getBytes("utf-8"));
    }

    /**
     * 根据流程实例Id,获取实时流程图片
     * @param processInstanceId
     * @return
     */
    public static InputStream getFlowImgByInstantId(String processInstanceId){
        if(StringUtils.isEmpty(processInstanceId)){
            return null;
        }
        //获取流程图输入流
        InputStream inputStream = null;
        // 查询历史
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (historicProcessInstance.getEndTime() != null) { // 该流程已经结束
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(historicProcessInstance.getProcessDefinitionId()).singleResult();
            inputStream = repositoryService.getResourceAsStream( processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
        } else {
            // 查询当前的流程实例
            ProcessInstance processInstance = runtimeService .createProcessInstanceQuery() .processInstanceId(processInstanceId).singleResult();
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
            ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
            List<String> highLightedFlows = new ArrayList<String>();
            List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
            List<String> historicActivityInstanceList = new ArrayList<String>();
            for (HistoricActivityInstance hai : historicActivityInstances) {
                historicActivityInstanceList.add(hai.getActivityId());
            }
            List<String> highLightedActivities = runtimeService.getActiveActivityIds(processInstanceId);
            historicActivityInstanceList.addAll(highLightedActivities);
            for (ActivityImpl activity : processDefinitionEntity.getActivities()) {
                int index = historicActivityInstanceList.indexOf(activity.getId());
                if (index >= 0 && index + 1 < historicActivityInstanceList.size()) {
                    List<PvmTransition> pvmTransitionList = activity.getOutgoingTransitions();
                    for (PvmTransition pvmTransition : pvmTransitionList) {
                        String destinationFlowId = pvmTransition.getDestination().getId();
                        if (destinationFlowId.equals(historicActivityInstanceList.get(index + 1))) {
                            highLightedFlows.add(pvmTransition.getId());
                        }
                    }
                }
            }
            ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
            List<String> activeActivityIds = new ArrayList<String>();
            List<org.activiti.engine.task.Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
            for (org.activiti.engine.task.Task task : tasks) {
                activeActivityIds.add(task.getTaskDefinitionKey());
            }
            inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds, highLightedFlows, "宋体", "宋体", null,null, 1.0);
        }
        return inputStream;
    }

    /**
     * 根据节点Id取得当前节点的下一流向流程节点,如果Id为空则默认为首节点（条件路由则只返回符合条件的流向）
     * @param defid 流程定义Id
     * @param nodelId 流程节点
     * @param elMap 流程变量el表达式集合
     * @return
     */
    public static List<PvmActivity> getNextActNodes(String defid, String nodelId, Map<String,Object> elMap){
        List<PvmActivity> pvmActivities=new ArrayList<PvmActivity>();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(defid).singleResult();
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinition.getId());
        //获取所有节点
        List<ActivityImpl> activitiList = def.getActivities();
        //然后循环activitiList 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
        for(ActivityImpl activityImpl:activitiList){
            String id = activityImpl.getId();
            if("".equals(nodelId)){
                Object o = activityImpl.getProperties().get("type");
                if(o.equals("startEvent")){
                    //startEvent节点
                    PvmTransition startEvent = activityImpl.getOutgoingTransitions().get(0);
                    List<PvmTransition> pvmTransitions = startEvent.getDestination().getOutgoingTransitions();
                    pvmActivities=getNextActivities(pvmTransitions, elMap);
                    return pvmActivities;
                }
            }else{
                if(nodelId.equals(id)){
                    List<PvmTransition> pvmTransitions=activityImpl.getOutgoingTransitions();
                    pvmActivities=getNextActivities(pvmTransitions, elMap);
                    return pvmActivities;
                }
            }

        }
        return pvmActivities;
    }

    /**
     * 各种情况的下级节点
     * @param pvmTransitions 流向 列表
     * @param elMap 分支节点变量
     * @return
     */
    public static List<PvmActivity> getNextActivities(List<PvmTransition> pvmTransitions,Map<String,Object> elMap){
        List<PvmActivity> pvmActivities=new ArrayList<PvmActivity>();
        for(PvmTransition p:pvmTransitions){
            PvmActivity pvmActivity = p.getDestination();
            String type=(String)pvmActivity.getProperty("type");
            if("userTask".equals(type)){
                //当前接点下一流向为userTask，则加入下一流向
                pvmActivities.add(pvmActivity);
            }else if ("exclusiveGateway".equals(type)){
                List<PvmTransition> outgoingTransitions = pvmActivity.getOutgoingTransitions();
                for(PvmTransition tr1:outgoingTransitions){
                    //获取分支节点流向中的判断el表达式
                    String conditionText=(String)tr1.getProperty("conditionText");
                    //进行解析el表达式
                    ExpressionFactory factory = new ExpressionFactoryImpl();
                    SimpleContext context = new SimpleContext();
                    for(String key:elMap.keySet()){
                        if(conditionText.indexOf(key) != -1){
                            context.setVariable(key, factory.createValueExpression(elMap.get(key), String.class));
                        }
                    }
                    ValueExpression e = factory.createValueExpression(context, conditionText, boolean.class);
                    //判断该流向是否符合传入的参数条件
                    if((Boolean)e.getValue(context)){
                        pvmActivities.add(tr1.getDestination());
                        break;
                    }
                }

            } else if ("parallelGateWay".equals(type)) {
                //并行路由
                List<PvmTransition> outgoingTransitions = pvmActivity.getOutgoingTransitions();
                for(PvmTransition tr2:outgoingTransitions){
                    pvmActivities.add(tr2.getDestination());
                }
            }else if ("endEvent".equals(type)){
                //结束
                pvmActivities.add(pvmActivity);
            }
        }
        return pvmActivities;
    }

    /**
     * 根据流向,取下级所有流向
     * @param pvmTransitions 流向 列表
     * @return
     */
    public static List<PvmTransition> getNextActivities(List<PvmTransition> pvmTransitions){
        List<PvmTransition> pvmActivities = new ArrayList<>();
        for(PvmTransition p:pvmTransitions){
            PvmActivity pvmActivity = p.getDestination();
            String type=(String)pvmActivity.getProperty("type");
            if("userTask".equals(type)){
                pvmActivities.add(p);
            }else{
                //条件分支
                if("exclusiveGateway".equals(type)){
                    List<PvmTransition> outgoingTransitions = pvmActivity.getOutgoingTransitions();
                    pvmActivities.addAll(outgoingTransitions);
                }else{
                    //并行路由
                    List<PvmTransition> outgoingTransitions = pvmActivity.getOutgoingTransitions();
                    pvmActivities.addAll(outgoingTransitions);
                }
            }
        }
        return pvmActivities;
    }

    /***
     * 根据节点id获取下一流向(也就是线)
     * @param defId 流程定义id
     * @param nodelId 节点id
     */
    public static List<PvmTransition> getNextPvmTransitions(String defId,String nodelId){
        List<PvmTransition> pvmTransitions = new ArrayList<>();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(defId).singleResult();
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinition.getId());
        //获取所有节点
        List<ActivityImpl> activitiList = def.getActivities();
        //然后循环activitiList 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
        for(ActivityImpl activityImpl:activitiList){
            String id = activityImpl.getId();
            if(nodelId.equals(id)){
                pvmTransitions=activityImpl.getOutgoingTransitions();
                pvmTransitions = getNextActivities(pvmTransitions);
            }
        }
        return pvmTransitions;
    }

    /*************************回退开始***************************/

    /**
     * 根据任务ID获取对应的流程实例
     *
     * @param taskId 任务ID
     * @return
     * @throws Exception
     */
    public static ProcessInstance findProcessInstanceByTaskId(String taskId)
            throws Exception {
        // 找到流程实例
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery().processInstanceId(
                        findTaskById(taskId).getProcessInstanceId())
                .singleResult();
        if (processInstance == null) {
            throw new MyException("流程实例未找到!");
        }
        return processInstance;
    }

    /**
     * 根据任务ID获得任务实例
     * @param taskId 任务ID
     * @return TaskEntity
     * @throws Exception
     */
    private static TaskEntity findTaskById(String taskId) throws Exception {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(
                taskId).singleResult();
        if (task == null) {
            throw new MyException("任务实例未找到!");
        }
        return task;
    }

    /**
     * 设置任务处理人,根据任务ID
     * @param taskId
     * @param userCode
     */
    public static void setTaskDealerByTaskId(String taskId,String userCode){
        taskService.setAssignee(taskId, userCode);
    }

    /**
     * 根据流程对象Id,查询当前节点Id
     * @param executionId
     * @return
     */
    public static String getActiviIdByExecutionId(String executionId){
        //根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(executionId).singleResult();
        String activitiId = execution.getActivityId();
        return activitiId;
    }

    /**
     * 根据流程实例ID和任务key值查询所有同级任务集合
     *
     * @param processInstanceId
     * @param key
     * @return
     */
    public static List<Task> findTaskListByKey(String processInstanceId, String key) {
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey(key).list();
        return list;
    }

    /**
     * 根据任务ID和节点ID获取活动节点 <br>
     *
     * @param taskId     任务ID
     * @param activityId 活动节点ID <br>
     *                   如果为null或""，则默认查询当前活动节点 <br>
     *                   如果为"end"，则查询结束节点 <br>
     * @return
     * @throws Exception
     */
    public static ActivityImpl findActivitiImpl(String taskId, String activityId)
            throws Exception {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);
        // 获取当前活动节点ID
        if (StringUtils.isEmpty(activityId)) {
            activityId = findTaskById(taskId).getTaskDefinitionKey();
        }
        // 根据流程定义，获取该流程实例的结束节点
        if (activityId.toUpperCase().equals("END")) {
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {
                String type =(String) activityImpl.getProperty("type");
                if(type.equals("endEvent")){
                    return activityImpl;
                }
            }
        }

        // 根据节点ID，获取对应的活动节点
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)
                .findActivity(activityId);

        return activityImpl;
    }
    /**
     * 根据任务ID获取流程定义
     *
     * @param taskId 任务ID
     * @return
     * @throws Exception
     */
    public static ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
            String taskId) throws Exception {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(findTaskById(taskId)
                        .getProcessDefinitionId());

        if (processDefinition == null) {
            throw new Exception("流程定义未找到!");
        }

        return processDefinition;
    }



    /**
     * 根据任务Id,查找当前任务
     * @param taskId 任务Id
     * @return
     */
    public static Task getTaskById(String taskId){
        //当前处理人的任务
        Task currenTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        return currenTask;
    }

    /**
     * 根据流程实例查询正在执行的任务
     * @param processInst
     * @return
     */
    public static List<Task> getTaskByProcessInst(String processInst){
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInst).list();
        return list;
    }

    /**
     * 根据activitiId,流程key获取当前节点
     * @param processDefinitionKey
     * @return
     */
    public static ActivityImpl getCurrenActivitiy(String processDefinitionKey,String activitiId){
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinition.getId());
        List<ActivityImpl> activitiList = def.getActivities();
        for (ActivityImpl activity:activitiList){
            if(activitiId.equals(activity.getId())){
                return activity;
            }
        }
        return null;
    }

    /**
     * 驳回流程
     * @param taskId     当前任务ID
     * @param activityId 驳回节点ID
     * @param variables  流程存储参数
     * @throws Exception
     */
    public static void backProcess(String taskId, String activityId,
                                   Map<String, Object> variables,String comment) throws Exception {
        Map<String,String> map =new HashMap<>();
        if (StringUtils.isEmpty(activityId)) {
            throw new MyException("驳回目标节点ID为空！");
        }
        if (StringUtils.isEmpty(activityId)) {
            throw new MyException("任务iD为空！");
        }
        //根据任务ID获取对应的流程实例
        ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
        //根据任务ID获得任务实例
        TaskEntity taskById = findTaskById(taskId);
        String taskDefinitionKey = taskById.getTaskDefinitionKey();
        map.put(taskDefinitionKey,taskDefinitionKey);
        // 查找所有并行任务节点，同时驳回
        Map<String, Object> parallelGateways = getParallelGateways(taskId);
        //并行开始所有结点
        List<PvmTransition> parallelStart=(List<PvmTransition>)parallelGateways.get("pStart");
        //并行结束所有结点
        List<PvmTransition> parallelEnd=(List<PvmTransition>)parallelGateways.get("pEnd");
        //并行节点开始
        if(parallelStart !=null && parallelStart.size()>0){
            List<PvmActivity> pvmActivities=new ArrayList<PvmActivity>();
            for (PvmTransition pvmTransition:parallelStart){
                PvmActivity destination = pvmTransition.getDestination();
                map.put(destination.getId(),destination.getId());
                pvmActivities.add(destination);
            }
            startParallelGateways(map,processInstance,variables,activityId,comment);
        }else if (parallelEnd !=null && parallelEnd.size()>0){
            //并行节点结束
            List<String> list = new ArrayList<String>();
            for (PvmTransition pvmTransition:parallelEnd){
                PvmActivity destination = pvmTransition.getSource();
                list.add(destination.getId());
            }
            endParallelGateways(map,processInstance,variables,list,comment);
        }else{
            //一般处理
            comment(map,processInstance,variables,activityId,comment);
        }
    }

    /**
     * 根据当前任务ID,查询并行路由的分支
     * @param taskId
     * @return
     */
    public static Map<String,Object> getParallelGateways(String taskId){
        Map<String,Object> map =new HashMap<String, Object>();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //然后根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());
        List<ActivityImpl> activitiList = def.getActivities();
        //根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
        String excId = task.getExecutionId();
        String activitiId=getActiviIdByExecutionId(excId);
        //然后循环activitiList 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
        for(ActivityImpl activityImpl:activitiList){
            String id = activityImpl.getId();
            if(activitiId.equals(id)){
                System.out.println("当前任务："+activityImpl.getProperty("name")); //输出某个节点的某种属性
                List<PvmTransition> outTransitions = activityImpl.getIncomingTransitions();//获取从某个节点出来的所有线路
                List<PvmTransition> inTransitions = activityImpl.getIncomingTransitions();//获取从某个节点流进的所有线路
                //并行开始
                for(PvmTransition tr:outTransitions){
                    PvmActivity ac = tr.getSource(); //获取线路的终点节点
                    Object o = ac.getProperty("type");
                    if("parallelGateway".equals(o)){
                        String gatewayId = ac.getId();
                        String gatewayType = gatewayId.substring(gatewayId
                                .lastIndexOf("_") + 1);
                        if ("START".equals(gatewayType.toUpperCase())) {
                            List<PvmTransition> outgoingTransitions = ac.getOutgoingTransitions();
                            List<PvmTransition> pvmTransitions=new ArrayList<PvmTransition>();
                            outgoingTransitions = forCheckQueryPvm(outgoingTransitions, pvmTransitions);
                            map.put("pStart",outgoingTransitions);
                        }
                    }
                }
                //并行结束
                for(PvmTransition tr:inTransitions){
                    PvmActivity ac = tr.getSource(); //获取线路的终点节点
                    Object o = ac.getProperty("type");
                    if("parallelGateway".equals(o)){
                        String gatewayId = ac.getId();
                        String gatewayType = gatewayId.substring(gatewayId
                                .lastIndexOf("_") + 1);
                        if ("END".equals(gatewayType.toUpperCase())) {
                            List<PvmTransition> ingoingTransitions = ac.getIncomingTransitions();
                            map.put("pStart",ingoingTransitions);
                        }
                    }
                }
                break;
            }
        }
        return map;
    }

    /**
     * 并行路由开始，查询所有正在执行并行任务的节点
     * @param pvmTransitions
     * @param pvmTransitionList
     * @return
     */
    private static List<PvmTransition> forCheckQueryPvm(List<PvmTransition> pvmTransitions,List<PvmTransition> pvmTransitionList){
        for (PvmTransition pvmTransition:pvmTransitions){
            PvmActivity destination = pvmTransition.getDestination();
            String activitid = destination.getId();
            String processDef = destination.getProcessDefinition().getId();
            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskDefinitionKey(activitid).processDefinitionId(processDef)
                    .orderByTaskCreateTime().desc().list().get(0);
            if("completed".equals(historicTaskInstance.getDeleteReason())){
                List<PvmTransition> outgoingTransitions = pvmTransition.getDestination().getOutgoingTransitions();
                for(PvmTransition p:outgoingTransitions){
                    Object type = p.getDestination().getProperty("type");
                    if("parallelGateway".equals(type)){
                        continue;
                    }else {
                        forCheckQueryPvm(outgoingTransitions,pvmTransitionList);
                    }
                }
            }else {
                pvmTransitionList.add(pvmTransition);
                continue;
            }
        }
        return pvmTransitionList;
    }

    /**
     * 并行开始回退处理
     * @param map 要回退的节点Id集合
     * @param processInstance 流程实例
     * @param variables
     * @param activityId
     * @throws Exception
     */
    private static void startParallelGateways(Map map,ProcessInstance processInstance,Map<String, Object> variables,String activityId,String comment) throws Exception {
        List<Task> taskList;
        Iterator it=map.keySet().iterator();
        int i=0;
        while (it.hasNext()){
            String id = (String) it.next();
            taskList = findTaskListByKey(processInstance.getId(), id);
            //第一次回退，正常回退
            if(i==0){
                for (Task task : taskList) {
                    commitProcess(task.getId(), variables, activityId,comment);
                }
            }
            //多次回退，就只完成任务，不更改流向,不设置回退任务处理人
            if(i>0){
                for (Task task : taskList) {
                    parallelgateway_end(task.getId(),variables);
                }
            }
            i++;
        }
    }

    /**
     * 并行开始返回，只执行任务，不做流向转向处理
     *
     * @param taskId     当前任务ID
     * @param variables  流程变量
     * @throws Exception
     */
    private static void parallelgateway_end(String taskId,  Map<String, Object> variables ) throws Exception {
        // 当前节点
        ActivityImpl currActivity = findActivitiImpl(taskId, null);
        // 清空当前流向
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);
        //当前任务
        Task task = getTaskById(taskId);
        task.setDescription("callback");
        taskService.saveTask(task);
        taskService.complete(taskId, variables);
        // 还原以前流向
        restoreTransition(currActivity, oriPvmTransitionList);
    }

    /**
     *并行结束后退处理
     * @param map 要回退的节点Id
     * @param processInstance
     * @param variables
     * @param list
     * @throws Exception
     */
    private static void endParallelGateways(Map map,ProcessInstance processInstance,Map<String, Object> variables,List<String> list,String comment) throws Exception {
        List<Task> taskList = new ArrayList<Task>();
        Iterator it=map.keySet().iterator();
        while (it.hasNext()){
            String id = (String) it.next();
            taskList = findTaskListByKey(processInstance.getId(), id);
            for (Task task : taskList) {
                commitProcess(task.getId(), variables, list,comment);
            }
        }
    }

    /**
     * 一般式的流程回退处理
     * @param map
     * @param processInstance
     * @param variables
     * @param activityId
     * @throws Exception
     */
    private static void comment(Map map,ProcessInstance processInstance,Map<String, Object> variables,String activityId,String comment) throws Exception {
        List<Task> taskList = new ArrayList<Task>();
        Iterator it=map.keySet().iterator();
        while (it.hasNext()){
            String id = (String) it.next();
            taskList = findTaskListByKey(processInstance.getId(), id);
            for (Task task : taskList) {
                commitProcess(task.getId(), variables, activityId,comment);
            }
        }
    }

    /**
     * @param taskId     当前任务ID
     * @param variables  流程变量
     * @param activityId 流程转向执行任务节点ID<br>
     *                   此参数为空，默认为提交操作
     * @throws Exception
     */
    public static void commitProcess(String taskId, Map<String, Object> variables,
                                     String activityId,String comment) throws Exception {
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        // 跳转节点为空，默认提交操作
        if (StringUtils.isEmpty(activityId)) {
            taskService.complete(taskId, variables);
        } else {// 流程转向操作
            turnTransition(taskId, activityId, variables,comment);
        }
    }
    /**
     * @param taskId     当前任务ID
     * @param variables  流程变量
     * @param activityIds 流程转向执行任务节点ID<br>
     *                   此参数为空，默认为提交操作
     * @throws Exception
     */
    private static void commitProcess(String taskId, Map<String, Object> variables,
                                      List<String> activityIds,String comment) throws Exception {
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        // 跳转节点为空，默认提交操作
        if (StringUtils.isEmpty(activityIds)) {
            taskService.complete(taskId, variables);
        } else {// 流程转向操作
            turnTransition(taskId, activityIds, variables,comment);
        }
    }

    /**
     * 流程转向操作
     *
     * @param taskId     当前任务ID
     * @param activityIds 目标节点任务ID
     * @param variables  流程变量
     * @throws Exception
     */
    private static void turnTransition(String taskId, List<String> activityIds, Map<String, Object> variables ,String comment) throws Exception {
        // 当前节点
        ActivityImpl currActivity = findActivitiImpl(taskId, null);
        // 清空当前流向
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

        List<TransitionImpl> transitions=new ArrayList<TransitionImpl>();
        List<ActivityImpl> pointActivitys=new ArrayList<ActivityImpl>();
        for(String activityId:activityIds){
            // 创建新流向
            TransitionImpl newTransition = currActivity.createOutgoingTransition();
            // 目标节点
            ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
            // 设置新流向的目标节点
            newTransition.setDestination(pointActivity);
            transitions.add(newTransition);
            pointActivitys.add(pointActivity);
        }
        // 执行转向任务
        Task currenTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        taskService.addComment(taskId, currenTask.getProcessInstanceId(), comment);
        taskService.complete(taskId, variables);
        // 删除目标节点新流入
        for (int i =0;i<transitions.size();i++){
            pointActivitys.get(i).getIncomingTransitions().remove(transitions.get(i));
        }

        // 还原以前流向
        restoreTransition(currActivity, oriPvmTransitionList);
    }

    /**
     * 流程转向操作
     *
     * @param taskId     当前任务ID
     * @param activityId 目标节点任务ID
     * @param variables  流程变量
     * @throws Exception
     */
    public static void turnTransition(String taskId, String activityId, Map<String, Object> variables ,String comment) throws Exception {
        // 当前节点
        ActivityImpl currActivity = findActivitiImpl(taskId, null);
        // 清空当前流向
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

        // 创建新流向
        TransitionImpl newTransition = currActivity.createOutgoingTransition();
        // 目标节点
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
        // 设置新流向的目标节点
        newTransition.setDestination(pointActivity);

        //查询当前任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(comment==null){
            comment="";
        }
        taskService.addComment(taskId, task.getProcessInstanceId(), comment);
        // 执行转向任务
        task.setDescription("callback");
        taskService.saveTask(task);
        taskService.complete(taskId, variables);

        //顺序会签处理
        dealMultiSequential(task.getProcessInstanceId(), comment, task.getTaskDefinitionKey());
        //设置回退的任务处理人
        setBackTaskDealer(task,activityId);
        // 删除目标节点新流入
        pointActivity.getIncomingTransitions().remove(newTransition);
        // 还原以前流向
        restoreTransition(currActivity, oriPvmTransitionList);
    }

    /**
     * 清空指定活动节点流向
     *
     * @param activityImpl 活动节点
     * @return 节点流向集合
     */
    private static List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
        // 存储当前节点所有流向临时变量
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
        // 获取当前节点所有流向，存储到临时变量，然后清空
        List<PvmTransition> pvmTransitionList = activityImpl
                .getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();

        return oriPvmTransitionList;
    }

    /**
     * 还原指定活动节点流向
     *
     * @param activityImpl         活动节点
     * @param oriPvmTransitionList 原有节点流向集合
     */
    private static void restoreTransition(ActivityImpl activityImpl,
                                          List<PvmTransition> oriPvmTransitionList) {
        // 清空现有流向
        List<PvmTransition> pvmTransitionList = activityImpl
                .getOutgoingTransitions();
        pvmTransitionList.clear();
        // 还原以前流向
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
    }

    /**
     * 顺序会签后退时处理
     * @param instanceId 流程实例
     * @param comment 意见
     * @param preActivityId 上个已经完成任务ID
     */
    public static void dealMultiSequential(String instanceId,String comment,String preActivityId){
        //该流程实例正在运行的任务
        List<Task> runTask = getTaskByProcessInst(instanceId);
        for(Task t:runTask){
            String runActivityId=t.getTaskDefinitionKey();
            //正在运行的任务节点id和上个已经完成的任务节点id相等,则判定为顺序会签
            if(runActivityId.equals(preActivityId)){
                if(comment==null){
                    comment="";
                }
                taskService.addComment(t.getId(), t.getProcessInstanceId(), comment);
                // 执行转向任务
                t.setDescription("callback");
                taskService.saveTask(t);
                taskService.complete(t.getId());
                //递归顺序会签,直到正在运行的任务还是顺序会签
                dealMultiSequential(t.getProcessInstanceId(), comment, t.getTaskDefinitionKey());
            }
        }
    }

    /**
     * 设置回退的任务处理人
     * @param task 当前任务
     * @param activityId 回退节点ID
     */
    public static void setBackTaskDealer(Task task,String activityId){
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId())
                .taskDefinitionKey(activityId).taskDeleteReason("completed").orderByTaskCreateTime().desc().list();
        HistoricTaskInstance historicTask =null;
        if(list != null && list.size()>0){
            historicTask=list.get(0);
            //查询回退后的节点正在运行的任务
            List<Task> taskList = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId())
                    .taskDefinitionKey(activityId).active().list();
            //同一节点下有多个任务，则认定为会签任务
            if(taskList != null && taskList.size()>1){
                for(int i=0;i<taskList.size();i++){
                    //设置会签任务处理人（处理人顺序不管）
                    taskService.setAssignee(taskList.get(i).getId(),list.get(i).getAssignee());
                }
            }else{
                Task taskNew = taskList.get(0);
                //顺序会签流程变量处理人
                String variable =(String) runtimeService.getVariable(taskNew.getExecutionId(), "countersign");
                if(!StringUtils.isEmpty(variable)){
                    //设置下个顺序会签处理人
                    setTaskDealerByTaskId(taskNew.getId(), variable);
                }else{
                    //设置一般回退任务处理人
                    taskService.setAssignee(taskNew.getId(), historicTask.getAssignee());
                }
            }
        }
    }

    /*************************回退结束***************************/

    /**
     * (取上一个任务节点)
     * 根据节点Id取得当前节点的上一流向,分支路由结束点，需要查询历史任务出现过的分支结点，并行路由则随便取一个
     * @param processDefinitionKey 流程定义Key
     * @param processInstanceId 流程实例ID
     * @param preNodeId 上个节点id
     * @return
     */
    public static String getProActivityId(String processDefinitionKey,String activitiId,String processInstanceId,String preNodeId){
        List<PvmActivity> pvmActivities=new ArrayList<PvmActivity>();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinition.getId());
        List<ActivityImpl> activitiList = def.getActivities();
        //然后循环activitiList 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
        for(ActivityImpl activityImpl:activitiList){
            String id = activityImpl.getId();
            if(!StringUtils.isEmpty(activitiId)){
                if(activitiId.equals(id)){
                    List<PvmTransition> pvmTransitions = activityImpl.getIncomingTransitions();
                    for(PvmTransition tr:pvmTransitions){
                        PvmActivity ac = tr.getSource();
                        Object type = ac.getProperty("type");
                        if("userTask".equals(type)){
                            preNodeId=ac.getId();
                            return preNodeId;
                        }else if("exclusiveGateway".equals(type)){
                            PvmActivity exclusiveActiviti = getExclusiveActiviti(ac,processDefinitionKey,processInstanceId);
                            if(exclusiveActiviti !=null){
                                preNodeId=exclusiveActiviti.getId();
                                return preNodeId;
                            }
                        }else{
                            //其它情况则进行递归 并行路由
                            preNodeId = getProActivityId(processDefinitionKey,ac.getId(),processInstanceId,preNodeId);
                        }
                    }
                }
            }
        }
        return preNodeId;
    }

    /**
     * 分支路由结束点，需要查询历史任务出现过的分支结点(取上一个任务节点)
     * @param ac exclusiveGateway节点
     * @param processDefinitionKey
     * @param processInstanceId
     * @return
     */
    public static PvmActivity getExclusiveActiviti(PvmActivity ac,String processDefinitionKey,String processInstanceId){
        //上一个节点为条件路由,则查询任务历史，查询历史最近走过的分支节点
        List<PvmTransition> exclusiveEnds = ac.getIncomingTransitions();
        //保存分支路由所有节点的最新记录
        List<HistoricTaskInstance> taskList=new ArrayList<HistoricTaskInstance>();
        //条件路由结束
        for(PvmTransition p:exclusiveEnds){
            PvmActivity pvmActivity = p.getSource();
            //根据分支路由的节点ID,查询历史任务中最新记录的节点ID
            HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().processDefinitionKey(processDefinitionKey)
                    .processInstanceId(processInstanceId).taskDefinitionKey(pvmActivity.getId()).orderByTaskCreateTime().desc().list().get(0);
            //有记录
            if(taskInstance != null && !StringUtils.isEmpty(taskInstance.getId())){
                taskList.add(taskInstance);
            }
        }

        if(taskList !=null && taskList.size()>1){
            //循环判断那个节点是最新操作的节点  按时间从大
            for(int i=0;i<taskList.size();i++){
                for(int j=i;j<taskList.size();j++){
                    Date d1 = taskList.get(i).getStartTime();
                    Date d2 = taskList.get(j).getStartTime();
                    if(d1.before(d2)){
                        HistoricTaskInstance temp;
                        temp=taskList.get(i);
                        taskList.set(i, taskList.get(j));
                        taskList.set(j, temp);
                    }
                }
            }
        }
        //最新分支节点
        String activityId=taskList.get(0).getTaskDefinitionKey();
        return getCurrenActivitiy(processDefinitionKey, activityId);
    }

    /**
     * 转办流程
     * @param taskId  当前任务节点ID
     * @param userId 被转办人id
     */
    public static boolean transferAssignee(String taskId, String userId) {
        try{
            taskService.setAssignee(taskId, userId);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}

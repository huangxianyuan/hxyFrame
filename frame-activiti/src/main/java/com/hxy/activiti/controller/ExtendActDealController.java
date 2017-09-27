package com.hxy.activiti.controller;

import com.hxy.activiti.dto.ProcessNodeDto;
import com.hxy.activiti.dto.ProcessTaskDto;
import com.hxy.activiti.entity.ExtendActFlowbusEntity;
import com.hxy.activiti.entity.ExtendActModelEntity;
import com.hxy.activiti.entity.ExtendActNodesetEntity;
import com.hxy.activiti.entity.ExtendActTasklogEntity;
import com.hxy.activiti.service.*;
import com.hxy.base.exception.MyException;
import com.hxy.base.page.Page;
import com.hxy.base.utils.Result;
import com.hxy.base.utils.StringUtils;
import com.hxy.base.utils.Utils;
import com.hxy.sys.entity.UserEntity;
import com.hxy.sys.service.OrganService;
import com.hxy.sys.service.RoleService;
import com.hxy.sys.service.UserService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 类的功能描述.
 * 流程办理相关操作
 * @Auther hxy
 * @Date 2017/7/11
 */
@Controller
@RequestMapping("act/deal")
public class ExtendActDealController {
    private Logger log = Logger.getLogger(getClass());

    @Autowired
    ExtendActModelerService extendActModelService;

    @Autowired
    ActModelerService actModelerService;

    @Autowired
    ExtendActNodesetService nodesetService;

    @Autowired
    ExtendActTasklogService tasklogService;

    @Autowired
    TaskService taskService;

    @Autowired
    ExtendActFlowbusService flowbusService;

    /**
     * 列表
     * @param model
     * @param actModelEntity
     * @param request
     * @return
     */
    @RequestMapping("list")
    public String list(Model model,ExtendActModelEntity actModelEntity, HttpServletRequest request){
        int pageNum = Utils.parseInt(request.getParameter("pageNum"), 1);
        Page<ExtendActModelEntity> page = extendActModelService.findPage(actModelEntity, pageNum);
        model.addAttribute("page",page);
        model.addAttribute("model",actModelEntity);
        return "activiti/extendActModelList.jsp";
    }

    /**
     * 根据流程key 获取业务可用的流程
     * @param model
     * @param actKey
     * @param busId
     * @return
     */
    @RequestMapping("queryFlowsByActKey")
    public String queryFlowsByActKey(Model model,String actKey,String busId){
        List<Map<String,Object>> defs = actModelerService.queryFlowsByActKey(actKey);
        model.addAttribute("defList",defs);
        model.addAttribute("busId",busId);
        model.addAttribute("actKey",actKey);
        return "activiti/flowSubmit.jsp";
    }

    /**
     * 获取当前节点可选择的审批人
     * @param model
     * @param actKey
     * @param busId
     * @return
     */
    @RequestMapping("getUsers")
    public String getUsers(Model model,String actKey,String busId){
        List<Map<String,Object>> defs = actModelerService.queryFlowsByActKey(actKey);
        model.addAttribute("defList",defs);
        model.addAttribute("busId",busId);
        model.addAttribute("actKey",actKey);
        return "activiti/flowSubmit.jsp";
    }

    /**
     * 获取流程第一个节点信息
     * @param deployId 部署id
     * @return
     */
    @RequestMapping(value = "getStartFlowInfo",method = RequestMethod.POST)
    @ResponseBody
    public Result getStartFlowInfo(String deployId){
        Result result = new Result();
        try {
            result = actModelerService.getStartFlowInfo(deployId);
        } catch (IOException e) {
            e.printStackTrace();
            result=Result.error("获取第一个节点信息失败!");
        }
        return result;
    }

    /**
     * 流程选择审批人窗口
     * @param nodeId
     * @param user
     * @return
     */
    @RequestMapping(value = "userWindow")
    public String userWindow(String nodeId,String nodeAction,HttpServletRequest request,UserEntity user,Model model){
        int pageNum = Utils.parseInt(request.getParameter("pageNum"), 1);
        Page<UserEntity> mapPage = actModelerService.userWindowPage(nodeId, pageNum, user.getUserName());
        model.addAttribute("page",mapPage);
        model.addAttribute("url","/act/deal/userWindow");
        model.addAttribute("flag",nodeAction);
        model.addAttribute("user",user);
        return "activiti/userWindow.jsp";
    }

    /**
     * 转办变更人选择弹框
     * @param user
     * @return
     */
    @RequestMapping(value = "turnUserWindow")
    public String turnUserWindow(UserEntity user,HttpServletRequest request,Model model){
        int pageNum = Utils.parseInt(request.getParameter("pageNum"), 1);
        Page<UserEntity> mapPage = actModelerService.turnWindowPage(pageNum,user);
        model.addAttribute("page",mapPage);
        //1 为单选 2为复选
        model.addAttribute("flag","1");
        model.addAttribute("url","/act/deal/turnUserWindow");
        model.addAttribute("user",user);
        return "activiti/userWindow.jsp";
    }

    /**
     * 启动流程
     * @param processTaskDto 完成任务dto
     * @return
     */
    @RequestMapping(value = "startFlow",method = RequestMethod.POST)
    @ResponseBody
    public Result startFlow(ProcessTaskDto processTaskDto){
        Result result = null;
        try {
            actModelerService.startFlow(processTaskDto);
            result=Result.ok("提交成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error("提交失败!");
        }
        return result;
    }

    /**
     * 获取实时流程图
     * @param processInstanceId 流程实例
     * @return
     */
    @RequestMapping(value = "showFlowImg",method = RequestMethod.GET)
    @ResponseBody
    public Result showFlowImg(String processInstanceId, HttpServletResponse response){
        try {
            InputStream inputStream = actModelerService.getFlowImgByInstantId(processInstanceId);
            //输出到页面
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 我的待办列表
     * @param model
     * @param code
     * @param busId
     * @param request
     * @return
     */
    @RequestMapping("myUpcoming")
    @RequiresPermissions("act:model:myUpcoming")
    public String myUpcoming(Model model,String code,String busId, HttpServletRequest request){
        int pageNum = Utils.parseInt(request.getParameter("pageNum"), 1);
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        params.put("busId",busId);
        Page<ExtendActModelEntity> page = actModelerService.findMyUpcomingPage(params, pageNum);
        model.addAttribute("page",page);
        model.addAttribute("code",code);
        model.addAttribute("busId",busId);
        return "activiti/myUpcoming.jsp";
    }

    /**
     * 我的已办列表
     * @param model
     * @param code
     * @param busId
     * @param request
     * @return
     */
    @RequestMapping("myDoneList")
    @RequiresPermissions("act:model:myUpcoming")
    public String myDoneList(Model model,String code,String busId, HttpServletRequest request){
        int pageNum = Utils.parseInt(request.getParameter("pageNum"), 1);
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        params.put("busId",busId);
        Page<ExtendActModelEntity> page = actModelerService.myDonePage(params, pageNum);
        model.addAttribute("page",page);
        model.addAttribute("code",code);
        model.addAttribute("busId",busId);
        return "activiti/myDoneList.jsp";
    }

    /**
     * 办理任务Tab
     * @param model
     * @param flag 1为查看审批记录，2为办理任务
     * @param request
     * @return
     */
    @RequestMapping("flowInfoTab")
    public String flowInfoTab(String flag ,ProcessTaskDto processTaskDto,Model model, HttpServletRequest request){
        model.addAttribute("flag",flag);
        model.addAttribute("taskDto",processTaskDto);
        return "activiti/flowInfoTab.jsp";
    }

    /**
     * 流程信息
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "flowInfoHtml",method = RequestMethod.POST)
    public String flowInfoHtml(Model model, HttpServletRequest request,String busId,String instanceId){
        Map<String,Object> params = new HashMap<>();
        params.put("busId",busId);
        List<ExtendActTasklogEntity> tasklogEntities = tasklogService.queryList(params);
        model.addAttribute("taskLogs",tasklogEntities);
        model.addAttribute("instanceId",instanceId);
        return "activiti/taskLogImg.jsp";
    }

    /**
     * 办理任务时查询业务可更改的字段和必要的流程相关信息
     * @param processTaskDto
     * @return
     */
    @RequestMapping(value = "getChangeFileds",method = RequestMethod.POST)
    @ResponseBody
    public Result getChangeFileds(ProcessTaskDto processTaskDto){
        if(StringUtils.isEmpty(processTaskDto.getTaskId())){
            throw new MyException("任务id不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getInstanceId())){
            throw new MyException("流程实例id不能为空");
        }
        if(StringUtils.isEmpty(processTaskDto.getDefId())){
            throw new MyException("流程定义id不能为空");
        }
        Task task = taskService.createTaskQuery().taskId(processTaskDto.getTaskId()).singleResult();
        //查询可更改字段
        ExtendActNodesetEntity nodesetEntity = nodesetService.queryByNodeId(task.getTaskDefinitionKey());
        //查询需要作为流程条件判断的字段
        Set<String> nextVarNams = actModelerService.getNextVarNams(task.getTaskDefinitionKey(),processTaskDto.getDefId());
        String[] changFile={};
        if(!StringUtils.isEmpty(nodesetEntity.getChangeFiles())){
            changFile=nodesetEntity.getChangeFiles().split(",");
        }
        return Result.ok().put("changeFields",changFile).put("vars",nextVarNams);
    }

    /**
     * 办理任务时，获取下一个节点的信息
     * @param processTaskDto
     * @return
     */
    @RequestMapping(value = "getNextActNodes",method = RequestMethod.POST)
    @ResponseBody
    public Result getNextActNodes(ProcessTaskDto processTaskDto){
        List<ProcessNodeDto> nextActNodes = actModelerService.getNextActNodes(processTaskDto);
        return Result.ok().put("nextActNodes",nextActNodes);
    }

    /**
     * 转到审批任务选择下一级审批者页面
     * @param processTaskDto
     * @param model
     * @return
     */
    @RequestMapping(value = "toDoActTaskView")
    public String toDoActTaskView(ProcessTaskDto processTaskDto,Model model){
        Task task = taskService.createTaskQuery().taskId(processTaskDto.getTaskId()).singleResult();
        //查询需要作为流程条件判断的字段
        Set<String> nextVarNams = actModelerService.getNextVarNams(task.getTaskDefinitionKey(),processTaskDto.getDefId());
        //查询可更改字段
        ExtendActNodesetEntity nodesetEntity = nodesetService.queryByNodeId(task.getTaskDefinitionKey());
        //查询流程基本信息
        ExtendActFlowbusEntity flowbus = flowbusService.queryByBusIdInsId(processTaskDto.getInstanceId(), processTaskDto.getBusId());
        model.addAttribute("taskDto",processTaskDto);
        model.addAttribute("nodeSet",nodesetEntity);
        model.addAttribute("flowbus",flowbus);
        return "activiti/doActTask.jsp";
    }

    /**
     * 办理任务
     * @param processTaskDto
     * @return
     */
    @RequestMapping(value = "doActTask",method = RequestMethod.POST)
    @ResponseBody
    public Result doActTask(ProcessTaskDto processTaskDto,HttpServletRequest request){
        Result result = null;
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String,Object> params = new LinkedCaseInsensitiveMap<>();
            for (String key:parameterMap.keySet()){
                params.put(key,parameterMap.get(key)[0]);
            }
            actModelerService.doActTask(processTaskDto,params);
            result=Result.ok("办理任务成功");
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error("办理任务失败");
        }
        return result;
    }

    /**
     * 驳回到任务发起人，重新编辑提交
     * @param processTaskDto
     * @return
     */
    @RequestMapping(value = "backStartUser",method = RequestMethod.POST)
    @ResponseBody
    public Result backStartUser(ProcessTaskDto processTaskDto,HttpServletRequest request){
        Result result = null;
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String,Object> params = new LinkedCaseInsensitiveMap<>();
            for (String key:parameterMap.keySet()){
                params.put(key,parameterMap.get(key)[0]);
            }
            actModelerService.endFailFolw(processTaskDto,params);
            result=Result.ok("驳回到发起人,成功");
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error("驳回到发起人,失败");
        }
        return result;
    }

    /**
     * 转到转办页面
     * @param processTaskDto
     * @param model
     * @return
     */
    @RequestMapping(value = "toTurnToDo")
    public String toTurnToDo(ProcessTaskDto processTaskDto,Model model){
        //查询流程基本信息
        ExtendActFlowbusEntity flowbus = flowbusService.queryByBusIdInsId(processTaskDto.getInstanceId(), processTaskDto.getBusId());
        model.addAttribute("taskDto",processTaskDto);
        model.addAttribute("flowbus",flowbus);
        return "activiti/trunTask.jsp";
    }

    /**
     * 转办
     * @param processTaskDto
     * @return
     */
    @RequestMapping(value = "turnToDo",method = RequestMethod.POST)
    @ResponseBody
    public Result turnToDo(ProcessTaskDto processTaskDto,String toUserId,HttpServletRequest request){
        Result result;
        try {
            actModelerService.turnToDo(processTaskDto,toUserId);
            result=Result.ok("转办任务,成功");
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error("转办任务,失败");
        }
        return result;
    }
















}

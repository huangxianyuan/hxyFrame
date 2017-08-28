package com.hxy.activiti.controller;

import com.hxy.activiti.entity.*;
import com.hxy.activiti.service.*;
import com.hxy.base.common.Constant;
import com.hxy.base.exception.MyException;
import com.hxy.base.page.Page;
import com.hxy.base.utils.JsonUtil;
import com.hxy.base.utils.Result;
import com.hxy.base.utils.Utils;
import com.hxy.dto.UserWindowDto;
import com.hxy.sys.service.OrganService;
import com.hxy.sys.service.RoleService;
import com.hxy.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类的功能描述.
 * 流程模型相关操作
 * @Auther hxy
 * @Date 2017/7/11
 */
@Controller
@RequestMapping("act/model")
public class ExtendActModelController {
    private Logger log = Logger.getLogger(getClass());

    @Autowired
    ExtendActModelerService extendActModelService;

    @Autowired
    ActModelerService actModelerService;

    @Autowired
    ExtendActNodesetService nodesetService;

    @Autowired
    ExtendActNodefieldService fieldService;

    @Autowired
    ExtendActNodeuserService nodeuserService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    OrganService organService;

    @Autowired
    ExtendActBusinessService businessService;

    /**
     * 列表
     * @param model
     * @param actModelEntity
     * @param request
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("act:model:all")
    public String list(Model model,ExtendActModelEntity actModelEntity, HttpServletRequest request){
        int pageNum = Utils.parseInt(request.getParameter("pageNum"), 1);
        Page<ExtendActModelEntity> page = extendActModelService.findPage(actModelEntity, pageNum);
        model.addAttribute("page",page);
        model.addAttribute("model",actModelEntity);
        return "activiti/extendActModelList.jsp";
    }

    /**
     * 编辑弹框
     * @param model
     * @return
     */
    @RequestMapping("info")
    @RequiresPermissions("act:model:all")
    public String info(Model model,String id){
        if(StringUtils.isNotEmpty(id)){
            ExtendActModelEntity extendActModelEntity = extendActModelService.queryObject(id);
            model.addAttribute("model",extendActModelEntity);
            List<ExtendActBusinessEntity> businessEntityList = businessService.queryBusTree();
            model.addAttribute("busTree", JsonUtil.getJsonByObj(businessEntityList));
        }
        return "activiti/modelInfo.jsp";
    }

    /**
     * 保存
     * @param modelEntity
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    @RequiresPermissions("act:model:all")
    public Result save(ExtendActModelEntity modelEntity){
        try {
            String modelId = extendActModelService.save(modelEntity);
            if(StringUtils.isEmpty(modelId)){
                return Result.error();
            }else{
                return Result.ok();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 更新
     * @param modelEntity
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    @RequiresPermissions("act:model:all")
    public Result update(ExtendActModelEntity modelEntity){
        int count = extendActModelService.update(modelEntity);
        if(count>0){
            return Result.ok("更新"+modelEntity.getName()+"成功!");
        }else {
            return Result.error("更新"+modelEntity.getName()+"失败!");
        }
    }

    /**
     * 获取流程图所有节点和连线
     * @param modelId 模型id
     */
    @RequestMapping("flowTree")
    public String flowTree(String modelId,Model model) throws Exception {
        //所有节点
        List<Map<String, String>> flows = actModelerService.getflows(modelId);
        model.addAttribute("flows", JsonUtil.getJsonByObj(flows));
        //所有回调和业务相关设置
        ExtendActBusinessEntity bus = new ExtendActBusinessEntity();
        bus.setType(Constant.ActBusType.BACK.getValue());
        ExtendActBusinessEntity businessEntity = businessService.queryActBusByModelId(modelId);
        List<Map<String, Object>> callBacks = businessService.queryCalBackByPid(businessEntity.getId());
        model.addAttribute("calBacks", JsonUtil.getJsonByObj(callBacks));
        model.addAttribute("writes", JsonUtil.getJsonByObj(businessEntity.getWrites()));
        model.addAttribute("judgs", JsonUtil.getJsonByObj(businessEntity.getJudgs()));
        return "activiti/flowNodeSet.jsp";
    }

    /**
     * 获取节点的扩展设置信息
     * @return
     */
    @RequestMapping("flowSetInfo")
    @ResponseBody
    public Result flowSetInfo(String nodeId,String type){
        if(StringUtils.isEmpty(nodeId)){
            throw new MyException("未获取节点id");
        }
        Result result = new Result();
        ExtendActNodesetEntity query = new ExtendActNodesetEntity();
        query.setNodeId(nodeId);
        ExtendActNodesetEntity nodesetEntity = nodesetService.queryByNodeId(nodeId);
        result.put("nodeSet",nodesetEntity);
        //如果节点类型为审批节点
        if(Constant.NodeType.EXAMINE.getValue().equals(type)){
            List<ExtendActNodeuserEntity> userLists = nodeuserService.getNodeUserByNodeId(nodeId);
            result.put("userList",userLists);
        }
        //节点类型为连线
        if(Constant.NodeType.LINE.getValue().equals(type)){
            Map<String,Object> params = new HashMap<>();
            params.put("nodeId",nodeId);
//            params.put("fieldType","2");
            List<ExtendActNodefieldEntity> fields = fieldService.queryList(params);
            result.put("fields",fields);
        }
        return result;
    }

    /**
     * 选择审批范围弹框
     * @param request
     * @param model
     * @param userWindowDto
     * @return
     */
    @RequestMapping(value = "userAreaSelect")
    public String userAreaSelect(HttpServletRequest request, Model model, UserWindowDto userWindowDto, String type){
        int pageNum = Utils.parseInt(request.getParameter("pageNum"), 1);
        Page<UserWindowDto> page =null;
        if(StringUtils.isEmpty(type)){
            //默认审批类型为用户级别的
            type=Constant.ExamineType.USER.getValue();
        }
        //类型为用户
        if(Constant.ExamineType.USER.getValue().equals(type)){
            page = userService.findPage(userWindowDto, pageNum);
        }else if(Constant.ExamineType.ROLE.getValue().equals(type)){
            //角色
            page = roleService.queryPageByDto(userWindowDto,pageNum);
        }else {
            //组织
            page = organService.queryPageByDto(userWindowDto,pageNum);
        }
        model.addAttribute("page",page);
        model.addAttribute("userWindow",userWindowDto);
        model.addAttribute("type",type);
        return "activiti/userAreaSelect.jsp";
    }

    /**
     * 查看流程图片
     * @param modelId
     * @return
     */
    @RequestMapping(value = "showFlowImg")
    @RequiresPermissions("act:model:all")
    public ResponseEntity<byte[]> showFlowImg(String modelId){
        return actModelerService.showFlowImg(modelId);
    }


    /**
     * 删除模型
     * @param id
     * @return
     */
    @RequestMapping("del")
    @ResponseBody
    @RequiresPermissions("act:model:all")
    public Result del(String id){
        try {
            int count = extendActModelService.delete(id);
            if(count<1){
                return Result.error("删除失败!");
            }else{
                return Result.ok("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 保存节点设置
     * @param nodesetEntity 流程节点配置
     * @return
     */
    @RequestMapping(value = "saveNode",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("act:model:all")
    public Result saveNode(ExtendActNodesetEntity nodesetEntity){
        try {
            ExtendActNodesetEntity nodeSet = nodesetService.saveNode(nodesetEntity);
            return Result.ok("保存成功!").put("nodeSet",nodeSet);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.ok("保存失败!");
        }
    }

    /**
     * 部署流程
     * @param modelId 模型id
     * @return
     */
    @RequestMapping(value = "deploy",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("act:model:all")
    public Result deploy(String modelId){
        try {
            extendActModelService.deploy(modelId);
            return Result.ok();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.ok();
        }
    }








}

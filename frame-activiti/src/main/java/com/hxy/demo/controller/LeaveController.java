package com.hxy.demo.controller;

import com.hxy.base.page.Page;
import com.hxy.base.utils.Result;
import com.hxy.base.utils.StringUtils;
import com.hxy.base.utils.Utils;
import com.hxy.demo.entity.LeaveEntity;
import com.hxy.demo.service.LeaveService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 类的功能描述.
 * 业务树
 * @Auther hxy
 * @Date 2017/7/25
 */
@RequestMapping("demo/leave")
@Controller
public class LeaveController {

    @Autowired
    LeaveService leaveService;

    /**
     * 请假列表
     * @param model
     * @param leaveEntity
     * @param request
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("act:model:all")
    public String list(Model model , LeaveEntity leaveEntity,HttpServletRequest request){
        int pageNum = Utils.parseInt(request.getParameter("pageNum"), 1);
        Page<LeaveEntity> page = leaveService.findPage(leaveEntity, pageNum);
        model.addAttribute("page",page);
        model.addAttribute("leave",leaveEntity);
        return "demo/leave.jsp";
    }

    /**
     * 请假详情
     * @param model
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("info")
    @RequiresPermissions("act:model:all")
    public String info(Model model , String id,HttpServletRequest request){
        if(!StringUtils.isEmpty(id)){
            LeaveEntity leaveEntity = leaveService.queryObject(id);
            model.addAttribute("leave",leaveEntity);
        }
        return "demo/leaveEdit.jsp";
    }

    /**
     * 编辑
     * @param leaveEntity
     * @return
     */
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @RequiresPermissions("act:model:all")
    @ResponseBody
    public Result edit(LeaveEntity leaveEntity){
        if(StringUtils.isEmpty(leaveEntity.getId())){
            leaveService.save(leaveEntity);
        }else {
            leaveService.update(leaveEntity);
        }
        return Result.ok();
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @RequiresPermissions("act:model:all")
    @ResponseBody
    public Result edit(String id){
       if(leaveService.delete(id)<1){
           return Result.error("删除请假条失败");
       }
        return Result.ok("删除请假条成功");
    }








}

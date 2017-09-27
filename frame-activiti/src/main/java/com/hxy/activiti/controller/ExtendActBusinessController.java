package com.hxy.activiti.controller;

import com.hxy.activiti.entity.ExtendActBusinessEntity;
import com.hxy.activiti.entity.ExtendActNodefieldEntity;
import com.hxy.activiti.entity.ExtendActNodesetEntity;
import com.hxy.activiti.entity.ExtendActNodeuserEntity;
import com.hxy.activiti.service.ExtendActBusinessService;
import com.hxy.base.common.Constant;
import com.hxy.base.exception.MyException;
import com.hxy.base.utils.JsonUtil;
import com.hxy.base.utils.Result;
import com.hxy.base.validator.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 类的功能描述.
 * 业务树
 * @Auther hxy
 * @Date 2017/7/25
 */
@RequestMapping("act/bus")
@Controller
public class ExtendActBusinessController {

    @Autowired
    ExtendActBusinessService businessService;

    @RequestMapping("busTree")
    @RequiresPermissions("act:bus:all")
    public String busTree(Model model , ExtendActBusinessEntity businessEntity){
        List<ExtendActBusinessEntity> businessEntityList = businessService.queryListByBean(businessEntity);
        model.addAttribute("busTree", JsonUtil.getJsonByObj(businessEntityList));
        return "activiti/busTree.jsp";
    }

    /**
     * 保存或者更新
     * @param model
     * @param businessEntity
     * @return
     */
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("act:bus:all")
    public Result edit(Model model , ExtendActBusinessEntity businessEntity){
        ValidatorUtils.validateEntity(businessEntity);
        boolean isSave=false;
        if(StringUtils.isEmpty(businessEntity.getId())){
            isSave=true;
        }
        int count = businessService.edit(businessEntity);
        if(count>0){
            ExtendActBusinessEntity bus = businessService.queryObject(businessEntity.getId());
            if(isSave){
                return Result.ok("保存名称【"+businessEntity.getName()+"】成功").put("bus",bus);
            }else {
                return Result.ok("更新名称【"+businessEntity.getName()+"】成功").put("bus",bus);
            }
        }else {
            if(isSave){
                return Result.ok("保存名称【"+businessEntity.getName()+"】失败");
            }else {
                return Result.ok("更新名称【"+businessEntity.getName()+"】失败");
            }
        }
    }

    /**
     * 查看
     * @param id
     * @return
     */
    @RequestMapping(value = "info",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("act:bus:all")
    public Result info(String id, HttpServletRequest request){
        ExtendActBusinessEntity businessEntity = businessService.queryObject(id);
        return Result.ok().put("bus",businessEntity);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("act:bus:all")
    public Result del(String id, HttpServletRequest request){
        int count = businessService.delete(id);
        if(count>0){
            return Result.ok("删除成功");
        }else {
            return Result.error("删除失败");
        }
    }







}

package com.hxy.sys.controller;

import java.util.List;
import java.util.Map;

import com.hxy.activiti.service.ActModelerService;
import com.hxy.base.annotation.SysLog;
import com.hxy.base.common.Constant;
import com.hxy.base.exception.MyException;
import com.hxy.base.utils.MD5;
import com.hxy.sys.entity.UserEntity;
import com.hxy.sys.service.NoticeService;
import com.hxy.sys.service.UserRoleService;
import com.hxy.sys.service.UserService;
import com.hxy.utils.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hxy.base.utils.PageUtils;
import com.hxy.base.utils.Query;
import com.hxy.base.utils.Result;


/**
 * 系统用户表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-05-03 09:41:38
 */
@RestController
@RequestMapping("sys/user")
public class UserController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ActModelerService actModelerService;
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	@SysLog("查看系统用户列表")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<UserEntity> userList = userService.queryList(query);
		int total = userService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		
		return Result.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:user:info")
	@SysLog("查看系统用户信息")
	public Result info(@PathVariable("id") String id){
		UserEntity user = userService.queryObject(id);
		if(user != null){
			user.setPassWord("");
			user.setRoleIdList(userRoleService.queryRoleIdList(user.getId()));
		}
		return Result.ok().put("user", user);
	}

	/**
	 *
	 * 主页用户信息
	 */
	@RequestMapping("/info")
	public Result info(){
		UserEntity user = userService.queryObject(ShiroUtils.getUserId());
		//待办条数
        int myUpcomingCount = actModelerService.myUpcomingCount();
        //我的通知条数
        int myNoticeCount = noticeService.MyNoticeCount();
        return Result.ok().put("user", user).put("myUpcomingCount",myUpcomingCount).put("myNoticeCount",myNoticeCount);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:update")
	@SysLog("新增系统用户")
	public Result save(@RequestBody UserEntity user){
		userService.save(user);
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	@RequiresPermissions("sys:user:update")
	@SysLog("修改系统用户")
	public Result update(@RequestBody UserEntity user){
		user.setPassWord(null);
		userService.update(user);
		
		return Result.ok();
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
	@SysLog("用户修改密码")
	public Result updatePassword(UserEntity user){
        int i = userService.updatePassword(user);
        if(i<1){
            return Result.error("更改密码失败");
        }
        return Result.ok("更改密码成功");
	}

	/**
	 * 禁用、
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	@SysLog("禁用系统用户")
	public Result delete(@RequestBody String[] ids){
		userService.updateBatchStatus(ids, Constant.ABLE_STATUS.NO.getValue());
		return Result.ok();
	}

	/**
	 * 启用、
	 */
	@RequestMapping("/enabled")
	@RequiresPermissions("sys:user:enabled")
	@SysLog("启用系统用户")
	public Result enabled(@RequestBody String[] ids){
		userService.updateBatchStatus(ids, Constant.ABLE_STATUS.YES.getValue());
		return Result.ok();
	}

	/**
	 * 重置密码
	 */
	@RequestMapping("/reset")
	@RequiresPermissions("sys:user:reset")
	@SysLog("重置密码")
	public Result reset(@RequestBody String[] ids){
		userService.resetPassWord(ids);
		return Result.ok("重置密码成功,密码为:"+Constant.DEF_PASSWORD);
	}

}

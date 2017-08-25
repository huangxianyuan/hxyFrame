package com.hxy.sys.controller;

import java.util.List;
import java.util.Map;

import com.hxy.sys.entity.UserRoleEntity;
import com.hxy.sys.service.UserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hxy.base.utils.PageUtils;
import com.hxy.base.utils.Query;
import com.hxy.base.utils.Result;


/**
 * 用户角色关系表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-05-03 10:07:59
 */
@RestController
@RequestMapping("userrole")
public class UserRoleController extends BaseController{
	@Autowired
	private UserRoleService userRoleService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("userrole:list")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<UserRoleEntity> userRoleList = userRoleService.queryList(query);
		int total = userRoleService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userRoleList, total, query.getLimit(), query.getPage());
		
		return Result.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("userrole:info")
	public Result info(@PathVariable("userId") String userId){
		UserRoleEntity userRole = userRoleService.queryObject(userId);
		
		return Result.ok().put("userRole", userRole);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("userrole:save")
	public Result save(@RequestBody UserRoleEntity userRole){
		userRoleService.save(userRole);
		
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("userrole:update")
	public Result update(@RequestBody UserRoleEntity userRole){
		userRoleService.update(userRole);
		
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("userrole:delete")
	public Result delete(@RequestBody String[] userIds){
		userRoleService.deleteBatch(userIds);
		
		return Result.ok();
	}
	
}

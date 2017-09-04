package com.hxy.sys.controller;


import com.hxy.base.annotation.SysLog;
import com.hxy.base.utils.PageUtils;
import com.hxy.base.utils.Query;
import com.hxy.base.utils.Result;
import com.hxy.quartz.entity.ScheduleJobEntity;
import com.hxy.quartz.service.ScheduleJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 类ScheduleJobController的功能描述:
 * 定时任务
 * @auther hxy
 * @date 2017-08-25 16:20:29
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	/**
	 * 定时任务列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:list")
	@SysLog("查看定时任务列表")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<ScheduleJobEntity> jobList = scheduleJobService.queryList(query);
		int total = scheduleJobService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(jobList, total, query.getLimit(), query.getPage());
//		/sys/schedule.html
		return Result.ok().put("page", pageUtil);
	}
	
	/**
	 * 定时任务信息
	 */
	@RequestMapping("/info/{jobId}")
	@RequiresPermissions("sys:schedule:info")
	@SysLog("查看定时任务")
	public Result info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.queryObject(jobId);
		
		return Result.ok().put("schedule", schedule);
	}
	
	/**
	 * 保存定时任务
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:schedule:update")
	@SysLog("新增定时任务")
	public Result save(@RequestBody ScheduleJobEntity scheduleJob){
		scheduleJobService.save(scheduleJob);
		
		return Result.ok();
	}
	
	/**
	 * 修改定时任务
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:schedule:update")
	@SysLog("修改定时任务")
	public Result update(@RequestBody ScheduleJobEntity scheduleJob){

		scheduleJobService.update(scheduleJob);
		
		return Result.ok();
	}
	
	/**
	 * 删除定时任务
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:schedule:delete")
	@SysLog("删除定时任务")
	public Result delete(@RequestBody Long[] jobIds){
		scheduleJobService.deleteBatch(jobIds);
		
		return Result.ok();
	}
	
	/**
	 * 立即执行任务
	 */
	@RequestMapping("/run")
	@RequiresPermissions("sys:schedule:run")
	@SysLog("运行定时任务")
	public Result run(@RequestBody Long[] jobIds){
		scheduleJobService.run(jobIds);
		
		return Result.ok();
	}
	
	/**
	 * 暂停定时任务
	 */
	@RequestMapping("/pause")
	@RequiresPermissions("sys:schedule:pause")
	@SysLog("暂停定时任务")
	public Result pause(@RequestBody Long[] jobIds){
		scheduleJobService.pause(jobIds);
		
		return Result.ok();
	}
	
	/**
	 * 恢复定时任务
	 */
	@RequestMapping("/resume")
	@RequiresPermissions("sys:schedule:resume")
	@SysLog("恢复定时任务")
	public Result resume(@RequestBody Long[] jobIds){
		scheduleJobService.resume(jobIds);
		
		return Result.ok();
	}

}

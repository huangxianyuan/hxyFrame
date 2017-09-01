package com.hxy.sys.controller;

import com.hxy.base.page.Page;
import com.hxy.base.utils.DateUtils;
import com.hxy.base.utils.Result;
import com.hxy.base.utils.Utils;
import com.hxy.sys.entity.NoticeEntity;
import com.hxy.sys.service.NoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 通知
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-08-31 15:59:09
 */
@Controller
@RequestMapping("sys/notice")
public class NoticeController extends BaseController{
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 我的通知列表
	 */
	@RequestMapping("/myList")
	@RequiresPermissions("sys:notice:myList")
	public String list(Model model, NoticeEntity noticeEntity, HttpServletRequest request){
		int pageNum = Utils.parseInt(request.getParameter("pageNum"), 1);
		Page<NoticeEntity> page = noticeService.findMyNoticePage(noticeEntity, pageNum);
		model.addAttribute("page",page);
		model.addAttribute("notice",noticeEntity);
		return "sys/noticeList.jsp";
	}


	/**
	 * 查阅
	 */
	@RequestMapping("/showNotice")
	@RequiresPermissions("sys:notice:showNotice")
	@ResponseBody
	public Result showNotice(String id){
		NoticeEntity notice = noticeService.queryObject(id);
		return Result.ok().put("notice", notice).put("time", DateUtils.format(notice.getReleaseTimee(),"yyyy-MM-dd hh:mm"));
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:notice:save")
	public Result save(@RequestBody NoticeEntity notice){
		noticeService.save(notice);
		
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:notice:update")
	public Result update(@RequestBody NoticeEntity notice){
		noticeService.update(notice);
		
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:notice:delete")
	public Result delete(@RequestBody String[] ids){
		noticeService.deleteBatch(ids);
		
		return Result.ok();
	}
	
}

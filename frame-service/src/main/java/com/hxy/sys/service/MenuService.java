package com.hxy.sys.service;

import com.hxy.sys.entity.MenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 菜单表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-05-03 10:07:59
 */
public interface MenuService {
	
	MenuEntity queryObject(String id);
	
	List<MenuEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	String save(MenuEntity menu);
	
	void update(MenuEntity menu);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	/**
	 *根据登陆用户Id,查询出所有授权菜单 按钮 登陆授权
	 * @param userId
	 * @return
	 */
	List<MenuEntity> queryByUserId(String userId);

	/**
	 * 根据父菜单Id查询菜单
	 * @param parenId
	 * @return
	 */
	List<MenuEntity> queryListParentId(String parenId);

	/**
	 * 获取用户菜单列表 主页查询用户菜单用
	 * @param userId
	 * @return
	 */
	List<MenuEntity> queryListUser(String userId);

	/**
	 * 查询所有不包括按钮 的菜单
	 * @return
	 */
	List<MenuEntity> queryNotButtonnList();






}

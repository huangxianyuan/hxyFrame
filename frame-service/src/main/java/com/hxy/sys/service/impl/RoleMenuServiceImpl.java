package com.hxy.sys.service.impl;

import com.hxy.sys.dao.RoleMenuDao;
import com.hxy.sys.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService {
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Override
	public List<String> queryListByRoleId(String id){
		return roleMenuDao.queryListByRoleId(id);
	}


	@Override
	public void save(Map map){
		roleMenuDao.save(map);
	}


	@Override
	public void delete(String roleId){
		roleMenuDao.delete(roleId);
	}
	
	@Override
	public void deleteBatch(String[] roleIds){
		roleMenuDao.deleteBatch(roleIds);
	}
	
}

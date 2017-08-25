package com.hxy.activiti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.hxy.activiti.dao.ExtendActNodeuserDao;
import com.hxy.activiti.entity.ExtendActNodeuserEntity;
import com.hxy.activiti.service.ExtendActNodeuserService;



@Service("extendActNodeuserService")
public class ExtendActNodeuserServiceImpl implements ExtendActNodeuserService {
	@Autowired
	private ExtendActNodeuserDao extendActNodeuserDao;
	
	@Override
	public ExtendActNodeuserEntity queryObject(String id){
		return extendActNodeuserDao.queryObject(id);
	}
	
	@Override
	public List<ExtendActNodeuserEntity> queryList(Map<String, Object> map){
		return extendActNodeuserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return extendActNodeuserDao.queryTotal(map);
	}
	
	@Override
	public void save(ExtendActNodeuserEntity extendActNodeuser){
		extendActNodeuserDao.save(extendActNodeuser);
	}
	
	@Override
	public void update(ExtendActNodeuserEntity extendActNodeuser){
		extendActNodeuserDao.update(extendActNodeuser);
	}
	
	@Override
	public void delete(String id){
		extendActNodeuserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		extendActNodeuserDao.deleteBatch(ids);
	}

	@Override
	public List<ExtendActNodeuserEntity> getNodeUserByNodeId(String nodeId) {
		return extendActNodeuserDao.getNodeUserByNodeId(nodeId);
	}
}

package com.hxy.activiti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.hxy.activiti.dao.ExtendActNodefieldDao;
import com.hxy.activiti.entity.ExtendActNodefieldEntity;
import com.hxy.activiti.service.ExtendActNodefieldService;



@Service("extendActNodefieldService")
public class ExtendActNodefieldServiceImpl implements ExtendActNodefieldService {
	@Autowired
	private ExtendActNodefieldDao extendActNodefieldDao;
	
	@Override
	public ExtendActNodefieldEntity queryObject(String id){
		return extendActNodefieldDao.queryObject(id);
	}
	
	@Override
	public List<ExtendActNodefieldEntity> queryList(Map<String, Object> map){
		return extendActNodefieldDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return extendActNodefieldDao.queryTotal(map);
	}
	
	@Override
	public void save(ExtendActNodefieldEntity extendActNodefield){
		extendActNodefieldDao.save(extendActNodefield);
	}
	
	@Override
	public void update(ExtendActNodefieldEntity extendActNodefield){
		extendActNodefieldDao.update(extendActNodefield);
	}
	
	@Override
	public void delete(String id){
		extendActNodefieldDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		extendActNodefieldDao.deleteBatch(ids);
	}

	@Override
	public List<ExtendActNodefieldEntity> queryByNodes(List<String> nodes) {
		return extendActNodefieldDao.queryByNodes(nodes);
	}
}

package com.hxy.activiti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hxy.activiti.dao.ExtendActFlowbusDao;
import com.hxy.activiti.entity.ExtendActFlowbusEntity;
import com.hxy.activiti.service.ExtendActFlowbusService;



@Service("extendActFlowbusService")
public class ExtendActFlowbusServiceImpl implements ExtendActFlowbusService {
	@Autowired
	private ExtendActFlowbusDao extendActFlowbusDao;
	
	@Override
	public ExtendActFlowbusEntity queryObject(String id){
		return extendActFlowbusDao.queryObject(id);
	}

	@Override
	public void save(ExtendActFlowbusEntity extendActFlowbus){
		extendActFlowbusDao.save(extendActFlowbus);
	}
	
	@Override
	public void update(ExtendActFlowbusEntity extendActFlowbus){
		extendActFlowbusDao.update(extendActFlowbus);
	}
	@Override
	public void delete(String id){
		extendActFlowbusDao.delete(id);
	}

	@Override
	public int updateByBusId(ExtendActFlowbusEntity extendActFlowbusEntity) {
		return extendActFlowbusDao.updateByBusId(extendActFlowbusEntity);
	}

	@Override
	public ExtendActFlowbusEntity queryByBusIdInsId(String instanceId, String busId) {
		Map<String,Object> params = new HashMap<>();
		params.put("instanceId",instanceId);
		params.put("busId",busId);
		return extendActFlowbusDao.queryByBusIdInsId(params);
	}
}

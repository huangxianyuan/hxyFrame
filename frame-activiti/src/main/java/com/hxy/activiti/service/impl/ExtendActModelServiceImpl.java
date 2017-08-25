package com.hxy.activiti.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hxy.activiti.dao.ExtendActModelDao;
import com.hxy.activiti.dto.ProcessTaskDto;
import com.hxy.activiti.entity.ExtendActModelEntity;
import com.hxy.activiti.service.ActModelerService;
import com.hxy.activiti.service.ExtendActModelerService;
import com.hxy.base.common.Constant;
import com.hxy.base.exception.MyException;
import com.hxy.base.page.Page;
import com.hxy.base.page.PageHelper;
import com.hxy.base.utils.StringUtils;
import com.hxy.base.utils.Utils;
import com.hxy.sys.entity.UserEntity;
import com.hxy.utils.UserUtils;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class ExtendActModelServiceImpl implements ExtendActModelerService {
	@Autowired
	private ExtendActModelDao extendActModelDao;

	@Autowired
	private ActModelerService actModelerService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private ObjectMapper objectMapper;

	
	@Override
	public ExtendActModelEntity queryObject(String id){
		return extendActModelDao.queryObject(id);
	}
	
	@Override
	public List<ExtendActModelEntity> queryList(Map<String, Object> map){
		return extendActModelDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return extendActModelDao.queryTotal(map);
	}
	
	@Override
	public String save(ExtendActModelEntity extendActModel) throws Exception {
        UserEntity currentUser = UserUtils.getCurrentUser();
        extendActModel.setCreateId(currentUser.getId());
		extendActModel.setCreateTime(new Date());
		extendActModel.setId(Utils.uuid());
		extendActModel.setBaid(currentUser.getBaid());
		extendActModel.setBapid(currentUser.getBapid());
		return actModelerService.CreateModeler(extendActModel);
	}
	
	@Override
	public int update(ExtendActModelEntity extendActModel){
		extendActModel.setUpdateId(UserUtils.getCurrentUserId());
		extendActModel.setUpdateTime(new Date());
		return extendActModelDao.update(extendActModel);
	}
	
	@Override
    @Transactional
	public int delete(String id){
        ExtendActModelEntity actmodel = extendActModelDao.queryObject(id);
        Model model = repositoryService.getModel(actmodel.getModelId());
        if(!StringUtils.isEmpty(model.getDeploymentId())){
            //删除部署表
            repositoryService.deleteDeployment(model.getDeploymentId());
        }
        //删除模型表
        repositoryService.deleteModel(model.getId());
        return extendActModelDao.delete(id);
	}
	
	@Override
	public int deleteBatch(String[] ids){
		return extendActModelDao.deleteBatch(ids);
	}

	@Override
	public Page<ExtendActModelEntity> findPage(ExtendActModelEntity extendActModelEntity, int pageNum) {
		PageHelper.startPage(pageNum, Constant.pageSize);
		extendActModelEntity.setCreateId(UserUtils.getCurrentUserId());
		//数据权限控制
		extendActModelEntity.setBaidList(UserUtils.getDateAuth(Constant.DataAuth.BA_DATA.getValue()));
		extendActModelEntity.setBapidList(UserUtils.getDateAuth(Constant.DataAuth.BAP_DATA.getValue()));
		extendActModelDao.queryList(extendActModelEntity);
		return PageHelper.endPage();
	}

	@Override
    @Transactional
	public void deploy(String modelId) throws IOException {
		if(StringUtils.isEmpty(modelId)){
			throw new MyException("流程模型id不能为空!");
		}
		Model model = repositoryService.getModel(modelId);
		//读取editorSource
		JsonNode jsonNode = objectMapper.readTree(repositoryService.getModelEditorSource(modelId));
		//转换editorSource为bpmnModel
		BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
		//获取流程名称
		List<Process> processes = bpmnModel.getProcesses();
		if(processes.size()==0){
			throw new MyException("没有设计流程图!");
		}
		Process process = processes.get(0);
		//设置流程属性
        ExtendActModelEntity extModel = extendActModelDao.getModelAndBusInfo(modelId);
//        String key=StringUtils.toStringByObject(extModel.getActKey())+"_"+modelId;
        String key=StringUtils.toStringByObject(extModel.getActKey());
		process.setId(key);
		process.setName(StringUtils.toStringByObject(extModel.getName()));
		process.setDocumentation(StringUtils.toStringByObject(extModel.getRemark()));
		ObjectNode objectNode = new BpmnJsonConverter().convertToJson(bpmnModel);
		//更新模型信息
		repositoryService.addModelEditorSource(modelId, objectNode.toString().getBytes("utf-8"));
		//更新模型
		model.setName(StringUtils.toStringByObject(extModel.getName()));
		model.setKey(key);
		//metaInfo
		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, StringUtils.toStringByObject(extModel.getName()));
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, model.getVersion());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, StringUtils.toStringByObject(extModel.getRemark()));
		model.setMetaInfo(modelObjectNode.toString());
		//设置流程名称
		String deployName = process.getName();
		//转换bpmnModel为可部署的xml形式
		byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
		Deployment deployment = repositoryService.createDeployment()
				.name(deployName)
				.addString(modelId + ".bpmn20.xml", new String(bpmnBytes, "utf-8"))
				.deploy();
		//更新模型部署id
		model.setDeploymentId(deployment.getId());
		repositoryService.saveModel(model);
		//修改扩展模型状态
        ExtendActModelEntity actModelEntity = new ExtendActModelEntity();
        actModelEntity.setModelId(modelId);
        actModelEntity.setStatus(Constant.YESNO.YES.getValue());
        actModelEntity.setActVersion(model.getVersion());
        actModelEntity.setId(extModel.getId());
        extendActModelDao.update(actModelEntity);
	}

}

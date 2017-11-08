package com.hxy.activiti.service.impl;

import com.hxy.activiti.dao.ExtendActNodefieldDao;
import com.hxy.activiti.dao.ExtendActNodeuserDao;
import com.hxy.activiti.entity.ExtendActNodefieldEntity;
import com.hxy.activiti.entity.ExtendActNodeuserEntity;
import com.hxy.activiti.utils.ActUtils;
import com.hxy.base.common.Constant;
import com.hxy.base.common.Constant.NodeType;
import com.hxy.base.exception.MyException;
import com.hxy.base.utils.Result;
import com.hxy.base.utils.StringUtils;
import com.hxy.base.utils.Utils;
import com.hxy.sys.dao.UserDao;
import com.sun.org.apache.xpath.internal.NodeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hxy.activiti.dao.ExtendActNodesetDao;
import com.hxy.activiti.entity.ExtendActNodesetEntity;
import com.hxy.activiti.service.ExtendActNodesetService;
import org.springframework.transaction.annotation.Transactional;


@Service("extendActNodesetService")
public class ExtendActNodesetServiceImpl implements ExtendActNodesetService {
	@Autowired
	private ExtendActNodesetDao extendActNodesetDao;

	@Autowired
	private ExtendActNodefieldDao nodefieldDao;

	@Autowired
	private ExtendActNodeuserDao nodeuserDao;
	
	@Override
	public ExtendActNodesetEntity queryObject(String id){
		return extendActNodesetDao.queryObject(id);
	}
	
	@Override
	public List<ExtendActNodesetEntity> queryList(Map<String, Object> map){
		return extendActNodesetDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return extendActNodesetDao.queryTotal(map);
	}

	@Override
	public void delete(String id){
		extendActNodesetDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		extendActNodesetDao.deleteBatch(ids);
	}

	@Override
	@Transactional
	public ExtendActNodesetEntity saveNode(ExtendActNodesetEntity actNodeset) throws IOException {
		if(StringUtils.isEmpty(actNodeset.getModelId())){
			throw new MyException("模型id不能为空!");
		}
		if(StringUtils.isEmpty(actNodeset.getNodeId())){
			throw new MyException("节点id不能为空!");
		}
		if(StringUtils.isEmpty(actNodeset.getNodeType())){
			throw new MyException("节点类型不能为空!");
		}
		//节点类型为审批节点
		if(actNodeset.getNodeType().equals(NodeType.EXAMINE.getValue())){
			//设置会签取消会签
			if(Constant.ActAction.MULIT.getValue().equals(actNodeset.getNodeAction())){
				try {
					ActUtils.setMultiInstance(actNodeset.getModelId(),actNodeset.getNodeId());
				} catch (Exception e) {
					e.printStackTrace();
					throw new MyException("设置会签失败");
				}
			}else {
				try {
					ActUtils.clearMultiInstance(actNodeset.getModelId(),actNodeset.getNodeId());
				} catch (Exception e) {
					e.printStackTrace();
					throw new MyException("取消会签失败");
				}
			}
			if(StringUtils.isEmpty(actNodeset.getId())){
				//保存节点信息
                actNodeset.setId(Utils.uuid());
				extendActNodesetDao.save(actNodeset);
			}else {
				//更新
				//保存节点信息
				extendActNodesetDao.update(actNodeset);
				//保存审批用户 先根据nodeId删除节点相关的审批用户
				nodeuserDao.delByNodeId(actNodeset.getNodeId());
			}
            //保存审批用户
            String[] userTypes = actNodeset.getUserTypes();
            String[] userIds = actNodeset.getUserIds();
            List<ExtendActNodeuserEntity> nodeUsers = new ArrayList<>();
            ExtendActNodeuserEntity nodeUser = null;
            if(userIds !=null && userIds.length>0){
				for (int i=0;i<userIds.length;i++){
					nodeUser = new ExtendActNodeuserEntity();
					nodeUser.setId(userIds[i]);
					nodeUser.setUserType(userTypes[i]);
					nodeUser.setNodeId(actNodeset.getNodeId());
					nodeUsers.add(nodeUser);
				}
				nodeuserDao.saveBatch(nodeUsers);
			}

		}
		//分支条件连线
		if(actNodeset.getNodeType().equals(NodeType.LINE.getValue())){
			//保存
			if(StringUtils.isEmpty(actNodeset.getId())){
				//保存节点信息
                actNodeset.setId(Utils.uuid());
				extendActNodesetDao.save(actNodeset);
			}else {
				//更新
				//保存节点信息
				extendActNodesetDao.update(actNodeset);
				//根据nodeId删除所有节点对应的连线条件
				nodefieldDao.delByNodeId(actNodeset.getNodeId());
			}
			//el条件 例如${day>3 && isagree==1}
			StringBuilder condition = new StringBuilder("${");
			if(actNodeset.getJudgList() != null && actNodeset.getJudgList().size() > 0){
				List<ExtendActNodefieldEntity> judgList = new ArrayList<>();
				int sort =0;
				for (ExtendActNodefieldEntity nodefield:actNodeset.getJudgList()){
					if(StringUtils.isEmpty(nodefield.getFieldName()) || StringUtils.isEmpty(nodefield.getRule())){
						continue;
					}
					Map<String, Object> map = tranceCode(nodefield);
					nodefield.setId(Utils.uuid());
					if(!StringUtils.isEmpty(nodefield.getElOperator())){
						condition.append(" "+map.get("elOperator")+" ");
					}
					condition.append(nodefield.getFieldName()).append(map.get("rule")).append(nodefield.getFieldVal());
					nodefield.setNodeId(actNodeset.getNodeId());
					nodefield.setSort(sort+"");
					sort++;
					judgList.add(nodefield);
				}
				nodefieldDao.saveBatch(judgList);
			}
			String judg = condition.append("}").toString();
			//添加条件
			ActUtils.setSequenceFlowCondition(actNodeset.getModelId(),actNodeset.getNodeId(),judg);
		}
		//节点类型为结束
		if(actNodeset.getNodeType().equals(NodeType.END.getValue())){
			if(StringUtils.isEmpty(actNodeset.getId())){
				//保存节点信息
				actNodeset.setId(Utils.uuid());
				extendActNodesetDao.save(actNodeset);
			}else {
				//更新
				extendActNodesetDao.update(actNodeset);
			}
		}
		return actNodeset;
	}

	/**
	 * 将中文字符转换为el运算符
	 * @param extendActNodefieldEntity
	 */
	private Map<String,Object> tranceCode(ExtendActNodefieldEntity extendActNodefieldEntity){
		Map<String,Object> map = new HashMap<>();
		if(extendActNodefieldEntity.getElOperator() != null){
			switch (extendActNodefieldEntity.getElOperator()){
				case "并且":
					map.put("elOperator","&&");break;
				case "或者":
					map.put("elOperator","||");break;
			}
		}
		if(extendActNodefieldEntity.getRule() != null){
			switch (extendActNodefieldEntity.getRule()){
				case "大于":
					map.put("rule",">");break;
				case "大于等于":
					map.put("rule",">=");break;
				case "小于":
					map.put("rule","<");break;
				case "小于等于":
					map.put("rule","<=");break;
				case "等于":
					map.put("rule","==");break;
			}
		}
		return map;
	}

    @Override
    public ExtendActNodesetEntity queryByNodeId(String nodeId) {
        return extendActNodesetDao.queryByNodeId(nodeId);
    }

	@Override
	public ExtendActNodesetEntity queryByNodeIdModelId(String nodeId, String modelId) {
		return extendActNodesetDao.queryByNodeIdModelId(nodeId,modelId);
	}
}

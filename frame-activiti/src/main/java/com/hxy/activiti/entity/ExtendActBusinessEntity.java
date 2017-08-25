package com.hxy.activiti.entity;

import com.hxy.base.entity.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 业务流程  对应的 业务表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-14 11:09:21
 */
public class ExtendActBusinessEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 可写
	 */
	private List<Map<String,Object>> writes;

	/**
	 * 可设置为条件
	 */
	private List<Map<String,Object>> judgs;

	private String id;
	//业务流程名字
	@NotBlank(message = "名称不能为空")
	private String name;
	//流程key
	@NotBlank(message = "流程key不能为空")
	private String actKey;
	//类路径
	private String classurl;
	//1=根节点 2=分组 3=业务类 4=回调
	private String type;
	//父节点id
	@NotBlank(message = "父节点不能为空")
	private String parentId;
	//同一级节点中的序号
	@NotBlank(message = "序号不能为空")
	private String sort;
	//是否展开
	private String open;
	//父节点名称
	private String parentName;

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：业务流程名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：业务流程名字
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：流程key 
	 */
	public void setActKey(String actKey) {
		this.actKey = actKey;
	}
	/**
	 * 获取：流程key 
	 */
	public String getActKey() {
		return actKey;
	}
	/**
	 * 设置：类路径
	 */
	public void setClassurl(String classurl) {
		this.classurl = classurl;
	}
	/**
	 * 获取：类路径
	 */
	public String getClassurl() {
		return classurl;
	}
	/**
	 * 设置：0=根节点 1=分组 2=业务类 3=回调
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：0=根节点 1=分组 2=业务类 3=回调
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：父节点id
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父节点id
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * 设置：同一级节点中的序号
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * 获取：同一级节点中的序号
	 */
	public String getSort() {
		return sort;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<Map<String, Object>> getWrites() {
		return writes;
	}

	public void setWrites(List<Map<String, Object>> writes) {
		this.writes = writes;
	}

	public List<Map<String, Object>> getJudgs() {
		return judgs;
	}

	public void setJudgs(List<Map<String, Object>> judgs) {
		this.judgs = judgs;
	}
}

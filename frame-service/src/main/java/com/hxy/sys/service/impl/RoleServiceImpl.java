package com.hxy.sys.service.impl;

import com.hxy.base.common.Constant;
import com.hxy.base.exception.MyException;
import com.hxy.base.page.Page;
import com.hxy.base.page.PageHelper;
import com.hxy.base.utils.Utils;
import com.hxy.dto.UserWindowDto;
import com.hxy.sys.dao.RoleDao;
import com.hxy.sys.dao.RoleMenuDao;
import com.hxy.sys.dao.UserRoleDao;
import com.hxy.sys.entity.RoleEntity;
import com.hxy.sys.entity.UserEntity;
import com.hxy.sys.service.RoleService;
import com.hxy.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RoleMenuDao roleMenuDao;

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	public RoleEntity queryObject(String id){
		return roleDao.queryObject(id);
	}
	
	@Override
	public List<RoleEntity> queryList(Map<String, Object> map){
		return roleDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return roleDao.queryTotal(map);
	}


	@Override
    @Transactional
	public void save(RoleEntity role) throws Exception{
        UserEntity currentUser = UserUtils.getCurrentUser();
		role.setBapid(currentUser.getBapid());
		role.setBaid(currentUser.getBaid());
		role.setCreateId(currentUser.getId());
        role.setId(Utils.uuid());
        role.setCreateTime(new Date());
		roleDao.save(role);
		saveRtable(role);
	}

	/**
	 * 保存角色与菜单，角色与组织关系表
	 * @param role
	 */
	public void saveRtable(RoleEntity role){
		if(role.getMenuIdList() != null && role.getMenuIdList().size()>0){
			Map map = new HashMap();
			map.put("roleId",role.getId());
			map.put("menuIdList",role.getMenuIdList());
			roleMenuDao.save(map);
		}
		if(role.getOrganIdList() != null && role.getOrganIdList().size()>0){
			Map organ = new HashMap();
			organ.put("role_id",role.getId());
			organ.put("organIdList",role.getOrganIdList());
			roleDao.batchSaveRoleOrgan(organ);
		}
	}

	@Transactional
	@Override
	public void update(RoleEntity role){
		role.setUpdateTime(new Date());
		roleDao.update(role);
		//先删除所有角色菜单关系，再批量保存
		roleMenuDao.delete(role.getId());
		//先删除所有角色组织关系，再批量保存
		roleDao.delRoleOrganByRoleId(role.getId());
		saveRtable(role);
	}
	
	@Override
	public void delete(String id){
		roleDao.delete(id);
	}

	@Transactional
	@Override
	public void deleteBatch(String[] ids) throws Exception{
		roleDao.deleteBatch(ids);
        roleMenuDao.deleteBatch(ids);
		userRoleDao.deleteBatchByRoleId(ids);
	}

	@Override
	public List<RoleEntity> queryByUserId(String userId, String status) {
		return roleDao.queryByUserId(userId,status);
	}

	@Override
	public List<String> queryOrganRoleByRoleId(String roleId) {
		Map params = new HashMap();
		params.put("roleId",roleId);
		params.put("isDel", Constant.YESNO.NO.getValue());
		return roleDao.queryOrganRoleByRoleId(params);
	}

	@Override
	public Page<UserWindowDto> queryPageByDto(UserWindowDto userWindowDto,int pageNum) {
		PageHelper.startPage(pageNum, Constant.pageSize);
		roleDao.queryPageByDto(userWindowDto);
		return PageHelper.endPage();
	}

	@Override
	public int updateBatchStatus(String[] ids, String status) {
		Map<String,Object> params = new HashMap<>();
		params.put("ids",ids);
		params.put("status",status);
		return roleDao.updateBatchStatus(params);
	}
}

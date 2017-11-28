package com.hxy.sys.dao;

import com.hxy.base.dao.BaseDao;
import com.hxy.base.page.Page;
import com.hxy.dto.UserWindowDto;
import com.hxy.sys.entity.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 角色表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-05-03 10:07:59
 */
@Repository
public interface RoleDao extends BaseDao<RoleEntity> {

    /**
     * 根据用户查询角色集合
     * @param userId
     * @return
     */
    List<RoleEntity> queryByUserId(String userId, String status);

    /**
     * 批量保存组织机构与角色关系表
     * @param map
     * @return
     */
    int batchSaveRoleOrgan(Map map);

    /**
     * 根据角色id查询可用的组织机构
     * @param map 参数1:roleId 参数2:isDel
     * @return
     */
    List<String> queryOrganRoleByRoleId(Map map);

    /**
     * 根据角色id删除角色和组织关系表
     * @param roleId
     * @return
     */
    int delRoleOrganByRoleId(String roleId);

    /**
     * 查询角色审批选择范围
     * @return
     */
    List<UserWindowDto> queryPageByDto(UserWindowDto userWindowDto);

    /**
     * 批量更新角色状态
     * @param params key:ids 角色ids
     * @return
     */
    int updateBatchStatus(Map<String,Object> params);

	
}

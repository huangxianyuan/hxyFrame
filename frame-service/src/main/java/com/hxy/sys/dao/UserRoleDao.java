package com.hxy.sys.dao;

import com.hxy.base.dao.BaseDao;
import com.hxy.sys.entity.UserRoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户角色关系表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-05-03 10:07:59
 */
@Repository
public interface UserRoleDao extends BaseDao<UserRoleEntity> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<String> queryRoleIdList(String userId);

    /**
     * 根据用户list批量删除用户角色中间表
     * @param users
     * @return
     */
    int deleteBatchByUserId(String[] users);

    /**
     * 根据角色list批量删除用户角色中间表
     * @param roles
     * @return
     */
    int deleteBatchByRoleId(String[] roles);

	
}

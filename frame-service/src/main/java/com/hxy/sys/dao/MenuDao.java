package com.hxy.sys.dao;

import com.hxy.base.dao.BaseDao;
import com.hxy.sys.entity.MenuEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-05-03 10:07:59
 */
@Repository
public interface MenuDao extends BaseDao<MenuEntity> {

    /**
     *根据登陆用户Id,查询出所有授权菜单
     * @param userId
     * @return
     */
    List<MenuEntity> queryByUserId(String userId);

    /**
     * 根据父菜单Id查询菜单
     * @param parenId
     * @return
     */
    List<MenuEntity> queryListParentId(String parenId);

    /**
     * 查询所有不包括按钮 的菜单
     * @return
     */
    List<MenuEntity> queryNotButtonnList(String[] types);

}

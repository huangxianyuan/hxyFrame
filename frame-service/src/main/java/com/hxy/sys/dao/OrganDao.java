package com.hxy.sys.dao;

import com.hxy.base.page.Page;
import com.hxy.dto.UserWindowDto;
import com.hxy.sys.entity.OrganEntity;
import com.hxy.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组织表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-14 13:42:42
 */
@Repository
public interface OrganDao extends BaseDao<OrganEntity> {
    /**
     * 根据实体条件查询
     * @return
     */
    List<UserWindowDto> queryPageByDto(UserWindowDto userWindowDto);
}

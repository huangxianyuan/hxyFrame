package com.hxy.app.dao;

import com.hxy.app.entity.UserApiEntity;
import com.hxy.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 类UserDao的功能描述:
 * 用户
 * @auther hxy
 * @date 2017-10-16 14:16:30
 */
@Repository
public interface UserApiDao extends BaseDao<UserApiEntity> {

    UserApiEntity queryByMobile(String mobile);
}

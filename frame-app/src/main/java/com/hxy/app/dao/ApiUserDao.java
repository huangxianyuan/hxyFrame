package com.hxy.app.dao;

import com.hxy.app.entity.ApiUserEntity;
import org.springframework.stereotype.Repository;

/**
 * 系统用户表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-05-03 09:41:38
 */
@Repository
public interface ApiUserDao{
    /**
     * 根据id获取用户相关信息
     * @param id
     * @return
     */
    ApiUserEntity userInfo(String id);
}

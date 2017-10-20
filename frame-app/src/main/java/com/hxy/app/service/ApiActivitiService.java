package com.hxy.app.service;

import com.hxy.activiti.entity.ExtendActModelEntity;
import com.hxy.app.annotation.CurrentUser;
import com.hxy.app.entity.ApiUserEntity;
import com.hxy.base.page.Page;

import java.util.Map;

/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/10/20
 */

public interface ApiActivitiService {
    /**
     * 我的代办条数
     * @return
     */
    int myUpcomingCount(String userId);

    /**
     * 我的待办列表
     * @param params
     * @param pageNum
     * @param userId
     * @return
     */
    Page myUpcomingPage(String userId,Map<String,Object> params, int pageNum,int pageSize);

    /**
     * 我的已办列表
     * @param params
     * @param pageNum
     * @param userId
     * @return
     */
    Page myDonePage(String userId,Map<String,Object> params, int pageNum,int pageSize);





}

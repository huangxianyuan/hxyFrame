package com.hxy.app.entity;

import com.hxy.sys.entity.UserEntity;

/**
 * 类的功能描述.
 * 用户信息接口信息
 * @Auther hxy
 * @Date 2017/10/18
 */

public class ApiUserEntity extends UserEntity{

    /**
     * 我的待办条数
     */
    private int myUpcomingCount;
    /**
     * 我的消息条数
     */
    private int myNoticeCount;

    public int getMyUpcomingCount() {
        return myUpcomingCount;
    }

    public void setMyUpcomingCount(int myUpcomingCount) {
        this.myUpcomingCount = myUpcomingCount;
    }

    public int getMyNoticeCount() {
        return myNoticeCount;
    }

    public void setMyNoticeCount(int myNoticeCount) {
        this.myNoticeCount = myNoticeCount;
    }
}

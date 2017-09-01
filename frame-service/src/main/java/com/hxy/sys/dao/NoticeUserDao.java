package com.hxy.sys.dao;

import com.hxy.sys.entity.NoticeUserEntity;
import com.hxy.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 通知和用户关系表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-08-31 15:58:58
 */
@Repository
public interface NoticeUserDao extends BaseDao<NoticeUserEntity> {
    /**
     * 根据通知id和用户id 更新
     * @param noticeUserEntity
     * @return
     */
    int updateByNoticeIdUserId(NoticeUserEntity noticeUserEntity);
}

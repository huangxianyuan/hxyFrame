package com.hxy.sys.dao;

import com.hxy.sys.entity.NoticeEntity;
import com.hxy.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通知
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-08-31 15:59:09
 */
@Repository
public interface NoticeDao extends BaseDao<NoticeEntity> {

    /**
     * 我的通知列表
     * @param noticeEntity
     * @return
     */
    List<NoticeEntity> findMyNotice(NoticeEntity noticeEntity);
}

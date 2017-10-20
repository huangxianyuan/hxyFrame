package com.hxy.app.service.impl;

import com.hxy.app.service.ApiNoticeService;
import com.hxy.base.common.Constant;
import com.hxy.base.exception.MyException;
import com.hxy.base.utils.StringUtils;
import com.hxy.sys.dao.NoticeDao;
import com.hxy.sys.entity.NoticeEntity;
import com.hxy.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/10/20
 */
@Service
public class ApiNoticeServiceImpl implements ApiNoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Override
    public int myNoticeCount(String userId) {
        if(StringUtils.isEmpty(userId)){
            throw new MyException("用户id不能为空!");
        }
        NoticeEntity noticeEntity = new NoticeEntity();
        int count = 0;
        //超级管理员可查看所有通知
        if(!Constant.SUPERR_USER.equals(userId)){
            noticeEntity.setUserId(UserUtils.getCurrentUserId());
        }
        noticeEntity.setShowStatus(Constant.YESNO.NO.getValue());
        List<NoticeEntity> myNotice = noticeDao.findMyNotice(noticeEntity);
        if(myNotice != null){
            count=myNotice.size();
        }
        return count;
    }
}

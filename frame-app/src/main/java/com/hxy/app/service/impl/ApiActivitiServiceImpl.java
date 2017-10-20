package com.hxy.app.service.impl;

import com.hxy.activiti.dao.ActExtendDao;
import com.hxy.activiti.dto.ProcessTaskDto;
import com.hxy.activiti.entity.ExtendActModelEntity;
import com.hxy.app.entity.ApiUserEntity;
import com.hxy.app.service.ApiActivitiService;
import com.hxy.base.common.Constant;
import com.hxy.base.exception.MyException;
import com.hxy.base.page.Page;
import com.hxy.base.page.PageHelper;
import com.hxy.base.utils.StringUtils;
import com.hxy.sys.dao.NoticeDao;
import com.hxy.sys.entity.NoticeEntity;
import com.hxy.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/10/20
 */
@Service
public class ApiActivitiServiceImpl implements ApiActivitiService{
    @Autowired
    private ActExtendDao actExtendDao;

    @Override
    public int myUpcomingCount(String userId) {
        if(StringUtils.isEmpty(userId)){
            throw new MyException("用户id不能为空!");
        }
        Map<String,Object> params = new HashMap<>();
        //超级管理员可查看所有待办
        if(!Constant.SUPERR_USER.equals(userId)){
            params.put("dealId",UserUtils.getCurrentUserId());
        }
        int count = 0;
        List<ProcessTaskDto> myUpcomingPage = actExtendDao.findMyUpcomingPage(params);
        if(myUpcomingPage != null){
            count=myUpcomingPage.size();
        }
        return count;
    }

    @Override
    public Page myUpcomingPage(String userId, Map<String, Object> params, int pageNum,int pageSize) {
        if(StringUtils.isEmpty(userId)){
            throw new MyException("用户id不能为空!");
        }
        PageHelper.startPage(pageNum, pageSize);
        //超级管理员可查看所有待办
        if(!Constant.SUPERR_USER.equals(userId)){
            params.put("dealId",UserUtils.getCurrentUserId());
        }
        actExtendDao.findMyUpcomingPage(params);
        return PageHelper.endPage();
    }

    @Override
    public Page myDonePage(String userId,Map<String, Object> params, int pageNum,int pageSize) {
        if(StringUtils.isEmpty(userId)){
            throw new MyException("用户id不能为空!");
        }
        PageHelper.startPage(pageNum, pageSize);
        //超级管理员可查看所有待办
        if(!Constant.SUPERR_USER.equals(userId)){
            params.put("dealId",UserUtils.getCurrentUserId());
        }
        actExtendDao.findMyDoneList(params);
        return PageHelper.endPage();
    }


}

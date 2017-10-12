package com.hxy.shiro.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 类的功能描述.
 * Shiro权限标签(Velocity版)
 * @Auther hxy
 * @Date 2017/5/9
 */

public class VelocityShiro {

    /**
     * 是否拥有该权限
     * @param permission  权限标识
     * @return   true：是     false：否
     */
    public boolean hasPermission(String permission){
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null && subject.isPermitted(permission)){
            return true;
        }
        return false;
    }
}

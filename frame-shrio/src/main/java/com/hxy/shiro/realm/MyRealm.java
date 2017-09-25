package com.hxy.shiro.realm;

import com.hxy.base.common.Constant;
import com.hxy.base.cache.UserCache;
import com.hxy.sentinelRedis.RedisUtil;
import com.hxy.shiro.redisSession.CachingShiroSessionDao;
import com.hxy.shiro.redisSession.ShiroSession;
import com.hxy.sys.entity.MenuEntity;
import com.hxy.sys.entity.RoleEntity;
import com.hxy.sys.entity.UserEntity;
import com.hxy.sys.service.MenuService;
import com.hxy.sys.service.RoleService;
import com.hxy.sys.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/4/27
 */

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private CachingShiroSessionDao sessionDao;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        UserEntity user = (UserEntity) principals.getPrimaryPrincipal();
        if(user !=null){
            //根据用户id查询该用户所有的角色,并加入到shrio的SimpleAuthorizationInfo
            List<RoleEntity> roles = roleService.queryByUserId(user.getId(), Constant.YESNO.YES.getValue());
            for (RoleEntity role:roles){
                    info.addRole(role.getId());
            }
            //查询所有该用户授权菜单，并加到shrio的SimpleAuthorizationInfo 做菜单按钮权限控件

            Set<String> permissions = new HashSet<>();
            List<MenuEntity> menuEntities=null;
            //超级管理员拥有最高权限
            if(Constant.SUPERR_USER.equals(user.getId())){
                menuEntities = menuService.queryList(new HashedMap());
            }else{
                //普通帐户权限查询
               menuEntities = menuService.queryByUserId(user.getId());
            }
            for (MenuEntity menuEntity:menuEntities){
                if(StringUtils.isNotEmpty(menuEntity.getPermission())){
                    permissions.add(menuEntity.getPermission());
                }
            }
            info.addStringPermissions(permissions);
        }
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userLoginName= (String) token.getPrincipal();
        String passWord=new String((char[]) token.getCredentials());
        UserEntity user = userService.queryByLoginName(userLoginName);
        if(user == null){
            throw new AuthenticationException("帐号不存在");
        }
        if(!passWord.equals(user.getPassWord())){
            throw new AuthenticationException("帐号密码错误");
        }
        if(Constant.ABLE_STATUS.NO.getValue().equals(user.getStatus())){
            throw new AuthenticationException("帐号被禁用,请联系管理员!");
        }
        SimpleAuthenticationInfo sainfo=new SimpleAuthenticationInfo(user,passWord,getName());
        Subject subject= SecurityUtils.getSubject();
        Serializable sessionId = subject.getSession().getId();
        ShiroSession session = (ShiroSession) sessionDao.doReadSessionWithoutExpire(sessionId);
        //用户对应的机构集合
        List<String> baidList = userService.queryBapidByUserIdArray(user.getId());
        //用户对应的部门集合
        List<String> bapidList= userService.queryBaidByUserIdArray(user.getId());
        user.setBapidList(bapidList);
        user.setBaidList(baidList);
        session.setAttribute("user",user);
        sessionDao.update(session);
        /*try {
            RedisUtil.setObject(Constant.USER_CACHE,user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserCache.put(Constant.USER_CACHE,user);*/
        return sainfo;
    }

    /**
     * 移除授权信息
     * @param username
     */
    public void removeUserAuthorizationInfoCache(String username) {
        SimplePrincipalCollection pc = new SimplePrincipalCollection();
        pc.add(username, super.getName());
        super.clearCachedAuthorizationInfo(pc);
    }
}

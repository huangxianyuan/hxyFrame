package com.hxy.shiro.realm;

import com.hxy.base.common.Constant;
import com.hxy.sys.entity.MenuEntity;
import com.hxy.sys.entity.RoleEntity;
import com.hxy.sys.entity.UserEntity;
import com.hxy.sys.service.MenuService;
import com.hxy.sys.service.RoleService;
import com.hxy.sys.service.UserService;
import com.hxy.utils.ShiroUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类的功能描述.
 * shiro 认证
 * @Auther hxy
 * @Date 2017/4/27
 */

public class MyRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        UserEntity user = (UserEntity) principals.getPrimaryPrincipal();
        if(user !=null){
            //根据用户id查询该用户所有的角色,并加入到shiro的SimpleAuthorizationInfo
            List<RoleEntity> roles = roleService.queryByUserId(user.getId(), Constant.YESNO.YES.getValue());
            for (RoleEntity role:roles){
                    info.addRole(role.getId());
            }
            //查询所有该用户授权菜单，并加到shiro的SimpleAuthorizationInfo 做菜单按钮权限控件

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
        UserEntity user = userService.queryByLoginName(userLoginName);
        if(user == null){
            throw new AuthenticationException("帐号密码错误");
        }
        if(Constant.ABLE_STATUS.NO.getValue().equals(user.getStatus())){
            throw new LockedAccountException("帐号被禁用,请联系管理员!");
        }
        //用户对应的机构集合
        List<String> baidList = userService.queryBapidByUserIdArray(user.getId());
        //用户对应的部门集合
        List<String> bapidList= userService.queryBaidByUserIdArray(user.getId());
        user.setBapidList(bapidList);
        user.setBaidList(baidList);
        SimpleAuthenticationInfo sainfo=new SimpleAuthenticationInfo(user,user.getPassWord(), ByteSource.Util.bytes(user.getSalt()),getName());
        return sainfo;
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.algorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }

}

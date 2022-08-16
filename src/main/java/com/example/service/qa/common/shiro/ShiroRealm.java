package com.example.service.qa.common.shiro;

import com.example.service.qa.common.BusinessException;
import com.example.service.qa.service.userinfo.SysMenuService;
import com.example.service.qa.service.userinfo.SysRoleService;
import com.example.service.qa.service.userinfo.UserService;
import com.example.service.qa.exceptions.ErrorCode;
import com.example.service.qa.model.userinfo.CrmClearUser;
import com.example.service.qa.model.userinfo.SysMenuEntity;
import com.example.service.qa.model.userinfo.SysRoleEntity;
import com.example.service.qa.utils.SHA256Util;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description Shiro权限匹配和账号密码匹配
 * @Author sulei
 * @CreateTime
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 授权权限
     * 用户进行权限验证时候Shiro会去缓存中找,如果查不到数据,会执行这个方法去查权限,并放入缓存中
     *
     * @Author Sans
     * @CreateTime 2019/6/12 11:44
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        CrmClearUser sysUserEntity = (CrmClearUser) principalCollection.getPrimaryPrincipal();
        //获取用户ID
        Integer userId = sysUserEntity.getId();
        //这里可以进行授权和处理
        Set<String> rolesSet = new HashSet<>();
        Set<String> permsSet = new HashSet<>();
        //查询角色和权限(这里根据业务自行查询)
        List<SysRoleEntity> sysRoleEntityList = sysRoleService.selectSysRoleByUserId(userId);
        for (SysRoleEntity sysRoleEntity : sysRoleEntityList) {
            rolesSet.add(sysRoleEntity.getRoleName());
            List<SysMenuEntity> sysMenuEntityList = sysMenuService.selectSysMenuByRoleId(sysRoleEntity.getRoleId());
            for (SysMenuEntity sysMenuEntity : sysMenuEntityList) {
                permsSet.add(sysMenuEntity.getPerms());
            }
        }
        //将查到的权限和角色分别传入authorizationInfo中
        authorizationInfo.setStringPermissions(permsSet);
        authorizationInfo.setRoles(rolesSet);
        return authorizationInfo;
    }

    /**
     * 身份认证
     * @Author
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户的输入的账号.
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        CrmClearUser userModel = new CrmClearUser();
        userModel.setUsername(username);
        //通过username从数据库中查找 User对象，如果找到进行验证
        //实际项目中,这里可以根据实际情况做缓存,如果不做,Shiro自己也是有时间间隔机制,2分钟内不会重复执行该方法
        CrmClearUser user = sysUserService.queryClearUser(userModel);

        //判断账号是否存在
        if (user == null) {
            throw new AuthenticationException();
        }
        //使用ldap验证, 并更新部分信息
        if (sysUserService.verifyAndUpdateInfoByLdap(username, password, user)) {
        } else {
            throw new BusinessException(ErrorCode.LDAP_LOGIN_FAILED);
        }
        //判断账号是否被冻结
        if (user.getState().equals("") || user.getState().equals("PROHIBIT")) {
            throw new LockedAccountException();
        }

        //进行验证
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,                                  //用户名
                SHA256Util.sha256(password),                    //密码
//                ByteSource.Util.bytes(user.getSalt()), //设置盐值
                getName());
        //验证成功开始踢人(清除缓存和Session)
//        ShiroUtils.deleteCache(username, true);
        return authenticationInfo;
    }
}



package com.example.service.qa.controller.login;

import com.example.service.qa.service.userinfo.UserService;
import com.example.service.qa.common.BusinessException;
import com.example.service.qa.exceptions.ErrorCode;
import com.example.service.qa.model.response.LoginResponse;
import com.example.service.qa.model.userinfo.CrmClearUser;
import com.example.service.qa.model.userinfo.SysUserRoleEntity;
import com.example.service.qa.service.userinfo.SysUserRoleService;
import com.example.service.qa.utils.SHA256Util;
import com.example.service.qa.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/userLogin")
public class UserLoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @RequestMapping("/login")
    public LoginResponse login(@RequestBody CrmClearUser user) {
        // Map<String, Object> map = new HashMap<>();
        LoginResponse response = new LoginResponse();
        //进行身份验证
        try {
            //验证身份和登陆
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            //验证成功进行登录操作
            subject.login(token);
            response.setLoginName(user.getUsername());
            response.setToken(ShiroUtils.getSession().getId().toString());
        } catch (IncorrectCredentialsException e) {
            throw new BusinessException(ErrorCode.USER_OR_PWD_IS_WRONG);
        } catch (LockedAccountException e) {
            throw new BusinessException(ErrorCode.USER_STATE_IS_FROZEN);
        } catch (AuthenticationException e) {
            throw new BusinessException(ErrorCode.PASSWORD_IS_WRONG);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 注销登录
     */
    @RequestMapping("/logout")
    public LoginResponse logout(@RequestBody CrmClearUser user) {
        ShiroUtils.deleteCache(user.getUsername(), true);
        ShiroUtils.logout();
        return new LoginResponse();
    }

    /**
     * 未登录
     *
     * @Author Sans
     * @CreateTime 2019/6/20 9:22
     */
    @RequestMapping("/unauth")
    public Map<String, Object> unauth() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 500);
        map.put("msg", "未登录");
        return map;
    }

    /**
     * 添加一个用户演示接口
     * 这里仅作为演示不加任何权限和重复查询校验
     *
     * @Author Sans
     * @CreateTime 2020/1/6 9:22
     */
    @RequestMapping("/testAddUser")
    public Map<String, Object> testAddUser() {
        // 设置基础参数
        CrmClearUser sysUser = new CrmClearUser();
        sysUser.setUsername("user1");
        sysUser.setState("NORMAL");
        // 随机生成盐值
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setSalt(salt);
        // 进行加密
        String password = "123456";
        sysUser.setPassword(SHA256Util.sha256(password));
        // 保存用户
        userService.save(sysUser);
        // 保存角色
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setUserId(sysUser.getId()); // 保存用户完之后会把ID返回给用户实体
        sysUserRoleService.save(sysUserRoleEntity);
        // 返回结果
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "添加成功");
        return map;
    }
}

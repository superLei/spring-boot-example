package com.example.service.qa.common.shiro;

import com.example.service.qa.utils.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sulei
 */
public class TokenCheckFilter extends UserFilter {

    /**
     * 判断是否拥有权限 true:认证成功  false:认证失败
     * mappedValue 访问该url时需要的权限
     * subject.isPermitted 判断访问的用户是否拥有mappedValue权限
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 根据请求头拿到token
        String token = getRequestToken(httpRequest);
        // 获取当前用户的token
        String userToken = "";
        userToken = ShiroUtils.getSession().getId().toString();
        // 检查token是否过期
        if (token != null &&  !token.equals(userToken)) {
            httpResponse.setStatus(401);
            try {
                response.getWriter().print("没有权限，请联系管理员授权");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        return true;
    }

    /**
     * 认证失败回调的方法: 如果登录实体为null就保存请求和跳转登录页面,否则就跳转无权限配置页面
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        return false;
    }
    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getRequestedSessionId();
        //如果header中不存在token，则从参数中获取token
        if (!StringUtils.isBlank(token)) {
            return token;
        }
        // 还可以实现从 cookie 获取
        Cookie[] cookies = httpRequest.getCookies();
        if(null==cookies||cookies.length==0){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JSESSIONID")) {
                token = cookie.getValue();
            }
        }
        return token;
    }

}

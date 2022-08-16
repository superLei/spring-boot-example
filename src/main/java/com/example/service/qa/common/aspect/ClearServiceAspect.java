package com.example.service.qa.common.aspect;


import com.example.service.qa.common.BusinessException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Objects;

/**
 * 记录controller层请求日志
 *
 * @author sulei
 * @date 2020-06-26
 */
@Aspect
@Component
public class ClearServiceAspect {

    Logger logger = LoggerFactory.getLogger(ClearServiceAspect.class);

    private static final Gson gson = new GsonBuilder().create();


    // 第一个宽字符 * 匹配 任何返回类型，第二个宽字符 * 匹配 任何方法名，最后的参数 (..) 表达式匹配 任意数量任意类型 的参数，也就是说该切点会匹配类中所有方法的调用
    @Pointcut("execution(* com.example.service.qa.controller.*.*(..)) && !execution(* com.example.service.qa.controller.login.UserLoginController.login(..))")
    public void logAspect() {

    }


    @Around("logAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long startTime = System.currentTimeMillis();
            Object[] args = joinPoint.getArgs();
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Objects.requireNonNull(sra).getRequest();
            // 获取请求中的方法路径
            String servletPath = request.getServletPath();
            String method = request.getMethod();
            String servletParams = "";
            String queryString = request.getQueryString();
            //获取请求参数集合并进行遍历拼接
            if (args.length > 0) {
                if ("POST".equals(method)) {
                    Object object = args[0];
                    servletParams = gson.toJson(object);
                } else if ("GET".equals(method)) {
                    servletParams = queryString;
                }
                servletParams = URLDecoder.decode(servletParams, "utf-8");
            }
            logger.info("requestMethod:{},url:{},params:{}.", method, servletPath, servletParams);
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            logger.info("responseBody:{},elapsed:{}ms.", gson.toJson(result), (endTime - startTime));
            return result;
        } catch (BusinessException e) {
            //业务异常正常返回给端上
            throw new BusinessException(e.getErrorCode(), e.getErrorMessage());
        } catch (Exception e) {
            logger.error(e.toString());
            throw e;
        }
    }

}

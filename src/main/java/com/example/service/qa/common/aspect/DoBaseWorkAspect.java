package com.example.service.qa.common.aspect;

import com.example.service.qa.service.common.BaseManager;
import com.example.service.qa.enums.ResultStatusEnum;
import com.example.service.qa.common.BusinessException;
import com.example.service.qa.model.BaseContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DoBaseWorkAspect {

    @Autowired
    private BaseManager baseManager;

    private final Logger log = LoggerFactory.getLogger(DoBaseWorkAspect.class);

    @Pointcut("@annotation(com.example.service.qa.common.aspect.DoBaseWork)")
    public void doBaseWork() {
    }

    /**
     * @param joinPoint
     * @throws Exception
     */
    @Before("doBaseWork()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        log.info("begin work");
        // 获取 @WebLog 注解的描述信息
        String methodDescription = getAspectLogDescription(joinPoint);
        // 打印描述信息
        log.info("Description : {}", methodDescription);
    }

    @After("doBaseWork()")
    public void doAfter(JoinPoint joinPoint) {

    }

    @Around("doBaseWork()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        log.info("Around work");
        Object result = null;
        Long itemID = -1L;
        BaseContext requestContext = new BaseContext();
        try {
            for (Object object : point.getArgs()) {
                if (object instanceof BaseContext) {
                    // 初始化执行结果记录
                    requestContext = (BaseContext) object;
                    itemID = baseManager.createRecord(requestContext);
                }
            }
            result = point.proceed();
            requestContext.setResult(ResultStatusEnum.SUCCESS.getCode());
            baseManager.updateRecord(itemID, requestContext);
        } catch (Exception e) {
            requestContext.setResult(ResultStatusEnum.FAIL.getCode());
            baseManager.updateRecord(itemID, requestContext);
            log.error("execute error for reason: itemID->" + itemID, e);
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                throw new BusinessException(businessException.getErrorCode(),businessException.getErrorMessage());
            } else {
                throw e;
            }
        }

        return result;
    }

    /**
     * 获取切面注解的描述
     *
     * @param joinPoint 切点
     * @return 描述信息
     * @throws Exception
     */
    public String getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);

        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description.append(method.getAnnotation(DoBaseWork.class).desc());
                    break;
                }
            }
        }
        return description.toString();
    }


}

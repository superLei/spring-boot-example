package com.example.service.qa.common.aspect;

import com.example.service.qa.common.sqllog.register.SqlLoggerHolder;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.example.service.qa.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;


// 拦截器需要实现org.apache.ibatis.plugin.Interceptor接口并指定拦截的方法.
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@SuppressWarnings({"unchecked", "rawtypes"})
@Slf4j
public class SQLInterceptor implements Interceptor {


    /**
     *  拦截方法后执行的逻辑
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
	    boolean flag = Boolean.FALSE;
        try {
            // 拼装sql打印信息
            StringBuilder sbuilder =  new StringBuilder();
            // 获取xml中的一个select/update/insert/delete节点，是一条SQL语句
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object parameter = null;
            // 获取参数，if语句成立，表示sql语句有参数，参数格式是map形式
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
                sbuilder.append(";parameter = " + parameter);
            }
            // 获取到节点的id,即sql语句的id
            String sqlId = mappedStatement.getId();

	        flag = sqlLogBackUp(sqlId, parameter);

	        // BoundSql就是封装myBatis最终产生的sql类
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            // 获取节点的配置
            Configuration configuration = mappedStatement.getConfiguration();
            // 获取到最终的sql语句
            String sql = getSql(configuration, boundSql, sqlId);
            sbuilder.append(";sql = " + sql);
            SqlLoggerHolder.write(sql);
            log.info(sbuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 执行完上面的任务后，不改变原有的sql执行过程
        Object proceed = invocation.proceed();

	    if (flag) {
//		    log.info("---------" + JSONUtil.toJsonStr(proceed));
            log.info("---------" + new Gson().toJson(proceed));
	    }

        return proceed;

    }


	private boolean sqlLogBackUp(String sqlId, Object parameter) {
		boolean flag = Boolean.TRUE;
		try {
			String methodStr = StringUtils.unqualify(sqlId);
			String classStr = sqlId.substring(0, sqlId.lastIndexOf("."));

			Class<?> clazz = Class.forName(classStr);
			Class<?>[] parameterClass = getParameterClass(parameter);

			Optional<Method> first = Optional.ofNullable(clazz.getMethod(methodStr, parameterClass))
					.filter(m -> m.isAnnotationPresent(SqlLogBackUp.class));

			if (!first.isPresent()) {
				flag = Boolean.FALSE;
			}
		} catch (Exception e) {

		}

		return flag;
	}

	private Class<?>[] getParameterClass(Object parameter) {
		Class<?> clazz = parameter.getClass();

		if (parameter.getClass() == ParamMap.class) {
			HashMap p = (ParamMap) parameter;

			Collection values = p.values();
			List list = Lists.newArrayList(values);

			list = (List) Lists.partition(list, list.size() / 2).get(BigDecimal.ZERO.intValue());

			List<Class> collect = (List<Class>) list.stream().map(Object::getClass)
					.collect(Collectors.toList());
			return collect.toArray(new Class[collect.size()]);
		}

		return new Class[]{clazz};
	}

	/**
     返回当前拦截的对象(StatementHandler)的动态代理
     当拦截对象的方法被执行时, 动态代理中执行拦截器intercept方法.
    */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     *  设置属性
     */
    @Override
    public void setProperties(Properties properties) {


    }

    /**
     * 封装了一下sql语句，使得结果返回完整xml路径下的sql语句节点id + sql语句
     */
    public static String getSql(Configuration configuration, BoundSql boundSql, String sqlId) {
        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        str.append(sqlId);
        str.append(":");
        str.append(sql);
        return str.toString();
    }

    /* 如果参数是String，则添加单引号， 如果是日期，则转换为时间格式器并加单引号； 对参数是null和不是null的情况作了处理 */
    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                    DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    /* 进行？的替换 */
    public static String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (!CommonUtils.isEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?",
                        Matcher.quoteReplacement(getParameterValue(parameterObject)));

            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        // 打印出缺失，提醒该参数缺失并防止错位
                        sql = sql.replaceFirst("\\?", "缺失");
                    }
                }
            }
        }
        return sql;
    }


}

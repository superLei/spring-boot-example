//package com.didi.easybuild.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//@MapperScan(basePackages = {"com.hualala.crmclear.mapper.**"}, sqlSessionFactoryRef = "clearSession")
//@EnableTransactionManagement(proxyTargetClass = true)
//public class ClearSqlConfig {
//
//
//    @Primary
//    @Bean("clearSession")
//    public SqlSessionFactory getClearSession(@Qualifier("dynamicDataSource") DataSource clearSource) {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(clearSource);
//
//        try{
//            sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
//            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/hualala/crmclear/mapper/*/*.xml"));
//            return sqlSessionFactoryBean.getObject();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    @Primary
//    @Bean("clearTemplate")
//    public SqlSessionTemplate clearTemplate(@Qualifier("clearSession") SqlSessionFactory sqlSessionFactory){
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}

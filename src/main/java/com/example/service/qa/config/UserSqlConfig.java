package com.example.service.qa.config;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author maxsu
 */
@Configuration
@MapperScan(basePackages = {"com.example.service.qa.dao.userinfo", "com.example.service.qa.dao.clearrecord","com.example.service.qa.dao.redisinfo"}, sqlSessionFactoryRef = "userSession")
public class UserSqlConfig {

    @Value("${spring.datasource2.url}")
    private String url;

    @Value(("${spring.datasource2.username}"))
    private String username;

    @Value(("${spring.datasource2.password}"))
    private String password;

    @Bean("userSource")
    public DataSource getUserSource() {
        HikariDataSource druidDataSource = new HikariDataSource();
        druidDataSource.setJdbcUrl(url);
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }


    @Bean("userSession")
    public SqlSessionFactory getUserSession(@Qualifier("userSource") DataSource userSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(userSource);

        try{
            sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/example/service/qa/dao/userinfo/CrmClearUserMapper.xml"));
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/example/service/qa/dao/clearrecord/CardClearResultMapper.xml"));
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/example/service/qa/dao/redisinfo/ClearRedisRecordMapper.xml"));
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Bean("userTemplate")
    public SqlSessionTemplate userTemplate(@Qualifier("userSession") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    @Bean("userTransaction")
    public DataSourceTransactionManager userTransaction(@Qualifier("userSource") DataSource userSource){
        return new DataSourceTransactionManager(userSource);
    }
}

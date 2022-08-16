package com.example.service.qa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import java.util.Hashtable;

@Configuration
public class LdapConfig {

  @Value("${ldap.url}")
  private String url;

  @Value("${ldap.base}")
  private String base;

  @Value("${ldap.userDn}")
  private String userDn;

  @Value("${ldap.password}")
  private String password;


  @Bean
  public LdapContextSource ldapContextSource() {
    LdapContextSource contextSource = new LdapContextSource();
    Hashtable<String, Object> config = new Hashtable<>();
    contextSource.setUrl(url);
    contextSource.setBase(base);
    contextSource.setUserDn(userDn);
    contextSource.setPassword(password);

    //  解决 乱码 的关键一句
    config.put("java.naming.ldap.attributes.binary", "objectGUID");

    //设置连接超时时间
//    config.put("com.sun.jndi.ldap.connect.timeout", "10000");
//    config.put("com.sun.jndi.ldap.read.timeout","10000");

    contextSource.setBaseEnvironmentProperties(config);
    contextSource.setPooled(true);
    contextSource.setCacheEnvironmentProperties(true);

    contextSource.afterPropertiesSet();

    return contextSource;
  }

  @Bean
  public LdapTemplate buildLdapTemplate(LdapContextSource ldapContextSource) {
    LdapTemplate ldapTemplate = new LdapTemplate(ldapContextSource);
    ldapTemplate.setDefaultTimeLimit(5000);
    ldapTemplate.setDefaultCountLimit(50);
    ldapTemplate.setIgnorePartialResultException(true);
    return ldapTemplate;
  }
}

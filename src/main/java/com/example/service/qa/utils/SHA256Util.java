package com.example.service.qa.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

public class SHA256Util {
    /**  私有构造器 **/
    private SHA256Util(){};
    /**  加密算法 **/
    public final static String HASH_ALGORITHM_NAME = "SHA-256";
    /**  循环次数 **/
    public final static int HASH_ITERATIONS = 2;
    /**  执行加密-采用SHA256和盐值加密 **/
    public static String sha256(String password ) {
        return new SimpleHash(HASH_ALGORITHM_NAME, password, null, HASH_ITERATIONS).toString();
    }
}

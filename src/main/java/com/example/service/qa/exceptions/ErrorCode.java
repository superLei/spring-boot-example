package com.example.service.qa.exceptions;

/**
 * @author maxsu
 */
public class ErrorCode {

    /**
     * 未知异常
     */
    public static final String UNKNOWN_ERROR = "001";


    /************************************************框架错误码****************************************/

    /**
     * 没有登录
     */
    public static final String NO_LOGIN = "1001";

    /**
     * ldap认证失败
     */
    public static final String LDAP_LOGIN_FAILED = "1002";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "1000";

    /**
     * 用户不存在或者密码错误
     */
    public static final String USER_OR_PWD_IS_WRONG = "1003";

    /**
     * 登录失败，该用户已被冻结
     */
    public static final String USER_STATE_IS_FROZEN = "1004";

    /**
     * 用户密码错误
     */
    public static final String PASSWORD_IS_WRONG = "1005";


    public static final String GROUPID_MISSING = "131000";

    public static final String METHOD_NOT_ALLOWED = "131001";

    /**
     * 清除类型值不存在
     */
    public static final String CLEAR_TYPE_NOT_EXIST = "131002";

    /**
     * 会员群体类型
     */
    public static final String USER_TYPE_NOT_EXIST = "131003";

    /**
     * redis key 不存在
     */
    public static final String REDIS_KEY_NOT_EXIST = "131004";

    /**
     * 数据库更新失败
     */
    public static final String DATABASE_UPDATE_ERROR = "131005";

    /**
     *  redis key 设置失败
     * */
    public static final String ADD_REDIS_KEY_ERROR = "131006";

    /**
     *  操作人缺失
     *
     * */
    public static final String OPERATOR_IS_NULL = "131007";

    /**--------------------------------------------业务错误码----------------------------------------------------*/


    /* *************************************导出********************* */
    /**
     * 空文件
     */
    public static final String NULL_FILE = "1212001";

    public static final String DOWNLOAD_ERROR = "1212002";

    public static final String RECORD_NOT_EXISTS = "1212003";
}

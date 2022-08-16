package com.example.service.qa.utils;

import java.text.DecimalFormat;

public class ConstantUtils {

    public static final Integer PAGE_SIZE = 5000;
    // 分页查询清除结果的数量
    public static final Integer RESULT_PAGE_SIZE = 50000;

    public static final Integer ZERO = 0;

    public static final Integer clearPoint = 70;

    public static final String PREFIX = "shopcrm_groupID_";

    public static final String DATASOURCE_TI_DB = "shopcrm_tidb_1";

    /**
     * 执行结果的pageSize
     * */
    public static final Integer EXECUTE_PAGE_SIZE = 12;

    /**
     * 卡模板绑定关系，redis的key前缀
     */
    public static final String REDIS_CARDBIND_RELATION_QUERY = "shopcrm_wechat_card_bind_relation";


    public static final String COLON = ":";

    public static final String SEPARTER = "\r\n";


    public static final String BIND_WECHAT = "The customer has bind wechat card";

    public static final String CUSTOMERID_NULL = "customerIDs is null";

    public static final String TRANSREMARK = "操作人员：系统清除积分";

    /**
     * 三方会员权限信息redis 缓存key前缀
     */
    public static final String TRDMEMBER_AUTHORIZE_KEY = "shopcrm_trdmember_authorize_";

    /**
     * 三方会员使用新微信餐厅redis缓存key
     */
    public static final String TRDMEMBER_NEW_WECHAT_KEY = "shopcrm_trdmember_use_online";

    /**
     * 相对时间值
     */
    public static final Integer RELATIVE_TIME = 0;

    /**
     * 绝对时间值
     */
    public static final Integer ABSOLUTE_TIME = 1;

    public final static DecimalFormat reportDf = new DecimalFormat("###.##");

    /**
     * 定义默认初始化的卡类型等级名称前缀
     */
    public static final String DEFAULT_INIT_LEVEL_NAME_PREFIX = "VIP";

    /**
     * 导出查询分页大小
     */
    public static final Integer EXPORT_QUERY_PAGE_SIZE = 5000;

    /**
     * 导出单文件数据量
     */
    public static final Integer EXPORT_PAGE_DATA_SIZE = 50000;

    /**
     * OSS objectName
     */
    public final static String OSS_OBJECT_NAME = "clear/{0}/{1}";

    /**
     * 本地文件路径
     */
    public final static String WIN_TMP_DIR = "D:/testExcelFiles/{0}.xlsx";
    public final static String WIN_TMP_DIR_ZIP = "D:/testExcelFiles/{0}.zip";

    /**
     * 服务器文件路径
     */
    public final static String LINUX_TMP_DIR = "/tmp/{0}.xlsx";
    public final static String LINUX_TMP_DIR_ZIP = "/tmp/{0}.zip";
}


package com.xxx.handnote.base;

/**
 * Created by niuapp on 2016/3/7 15:40.
 * Project : YuntooApk.
 * Email : *******
 * -->常量类
 */
public class Const {

    public static final boolean testFlag = false; //true  url测试模式
    public static final boolean deBugFlag = true; //true deBug模式 关闭异常捕获，开启 log

    // ============================  网络相关基本参数  ===========================
    public static final int HTTP_MEMORY_CACHE_SIZE = 2 * 1024 * 1024; // 2MB
    public static final int HTTP_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
    public static final String HTTP_DISK_CACHE_DIR_NAME = "yuntoo";
    public static final String USER_AGENT = "yuntoo.com";

    //编码
    public static final String UTF_8 = "UTF-8";

    // 基本url
    public static  String BASE_URL = "http://sjapi.yoolon.com/api/";

    static {
        if (Const.testFlag){
            BASE_URL = "http://sjapi.yoolon.com/api/";//测试
        }else {
            BASE_URL = "http://www.baidu.com/";
        }
    }

    // =========================================================================

    //

    public static final int PAGER_HOME = 0;//主页
    public static final int PAGER_MY = 1;//我的页
    public static final int PAGER_HOME_ALL = 0;//主页 --> 全部
    public static final int PAGER_HOME_GROUP = 1;//我的页 --> 分组

}

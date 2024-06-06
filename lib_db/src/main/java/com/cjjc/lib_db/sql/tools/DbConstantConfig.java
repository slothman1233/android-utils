package com.cjjc.lib_db.sql.tools;


import com.cjjc.lib_tools.util.AppGlobalUtils;
import com.cjjc.lib_tools.util.StringUtil;

/**
 * 数据库文件命名常量
 */
public class DbConstantConfig {
    //数据库保存路径  默认放在缓存目录
    public static String DB_PATH = AppGlobalUtils.getApplication().getCacheDir().getAbsolutePath() + "/DB/";
    //数据库前缀项目名
    private static String PROJECT_NAME = "ARC_DEMO";
    //数据库版本---总库
    private static double DB_VERSION_SUM = 1.0;
    //数据库版本---用户库
    private static double DB_VERSION_USER = 1.0;
    //数据库整体文件夹名
    public static String SUM_DB_DIR_NAME = "HuaYunDb";
    //备份数据库整体文件夹名
    public static String BACK_DB_DIR_NAME = "HuaYunBackDb";
    //用户管理总数据库名称
    public static String USER_MSG_DB_NAME = "HuaYunUserMsg";
    //用户个人数据库名称
    public static String USER_DB_NAME = "HauYunUserDB";
    //记录数据库版本升级的文件名
    public static String RECORD_UPDATE_FILE_NAME = "HuaYunUpdate.txt";


    /**
     * 初始化数据库配置
     *
     * @param dbPath               数据库保存路径
     * @param projectName          数据库前缀项目名
     * @param dbVersionSum         数据库版本---总库
     * @param dbVersionUser        数据库版本---用户库
     * @param sumDbDirName         数据库整体文件夹名
     * @param backDbDirName        备份数据库整体文件夹名
     * @param userMsgDbName        用户管理总数据库名称
     * @param userDbName           用户个人数据库名称
     * @param recordUpdateFileName 记录数据库版本升级的文件名
     */
    public static void initDbPath(String dbPath, String projectName, double dbVersionSum, double dbVersionUser,
                                  String sumDbDirName, String backDbDirName, String userMsgDbName,
                                  String userDbName, String recordUpdateFileName) {
        if (!StringUtil.isBlank(dbPath)) {
            DB_PATH = dbPath;
        }
        if (!StringUtil.isBlank(projectName)) {
            PROJECT_NAME = projectName;
        }
        if (dbVersionSum != 0) {
            DB_VERSION_SUM = dbVersionSum;
        }
        if (dbVersionUser != 0) {
            DB_VERSION_SUM = dbVersionUser;
        }
        if (!StringUtil.isBlank(sumDbDirName)) {
            SUM_DB_DIR_NAME = PROJECT_NAME + sumDbDirName;
        }
        if (!StringUtil.isBlank(backDbDirName)) {
            BACK_DB_DIR_NAME = PROJECT_NAME + backDbDirName;
        }
        if (!StringUtil.isBlank(userMsgDbName)) {
            USER_MSG_DB_NAME = PROJECT_NAME + userMsgDbName + DB_VERSION_SUM + ".db";
        }
        if (!StringUtil.isBlank(userDbName)) {
            USER_DB_NAME = PROJECT_NAME + userDbName + DB_VERSION_USER + ".db";
        }
        if (!StringUtil.isBlank(recordUpdateFileName)) {
            RECORD_UPDATE_FILE_NAME = PROJECT_NAME + recordUpdateFileName;
        }
    }
}
